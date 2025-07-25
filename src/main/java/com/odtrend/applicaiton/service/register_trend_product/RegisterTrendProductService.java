package com.odtrend.applicaiton.service.register_trend_product;

import static com.odtrend.domain.model.Category.getCategories;

import com.odtrend.applicaiton.port.in.RegisterTrendProductUseCase;
import com.odtrend.applicaiton.port.out.CrawlingProductStoragePort;
import com.odtrend.applicaiton.port.out.ElasticSearchClientPort;
import com.odtrend.applicaiton.port.out.GeminiClientPort;
import com.odtrend.applicaiton.port.out.RecommendStoragePort;
import com.odtrend.domain.model.Category;
import com.odtrend.domain.model.CrawlingProduct;
import com.odtrend.domain.model.ProductRecommend;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
class RegisterTrendProductService implements RegisterTrendProductUseCase {

    private final GeminiClientPort geminiClientPort;
    private final RecommendStoragePort recommendStoragePort;
    private final ElasticSearchClientPort elasticSearchClientPort;
    private final CrawlingProductStoragePort crawlingProductStoragePort;

    @Override
    public void register() {
        LocalDate targetDate = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDateTime start = targetDate.minusWeeks(1).atStartOfDay();
        LocalDateTime end = targetDate.minusDays(1).atTime(LocalTime.of(23, 59, 59));

        if (recommendStoragePort.existsByCheckDate(targetDate)) {
            return;
        }

        for (Category category : getCategories()) {
            List<CrawlingProduct> products = crawlingProductStoragePort.findByRegDateTimeAndCategory(
                start, end, category);

            if (products.isEmpty()) {
                continue;
            }

            log.info("[registerRecommendProduct] {} start - {}", targetDate, products.size());
            List<float[]> embeddings = new ArrayList<>();
            for (CrawlingProduct product : products) {
                float[] embeddingResult = getEmbeddings(0, product);
                if (ObjectUtils.isEmpty(embeddingResult)) {
                    // TODO: add error Log
                    embeddings.clear();
                    break;
                }
                embeddings.add(embeddingResult);
            }

            if (ObjectUtils.isEmpty(embeddings)) {
                continue;
            }

            float[] average = averageEmbeddings(embeddings);
            List<ProductRecommend> recommends = elasticSearchClientPort.findIdByEmbeddingAndCategory(
                    average, category.name())
                .stream()
                .map(id -> ProductRecommend.of(id, targetDate))
                .toList();

            recommendStoragePort.register(recommends);
        }
    }

    private float[] getEmbeddings(int retryCnt, CrawlingProduct product) {
        try {
            return geminiClientPort.embedding(product.getEmbeddingDocument());
        } catch (Exception e) {
            int maxRetryCnt = 5;
            if (retryCnt == maxRetryCnt) {
                return null;
            }
            log.info("[registerProduct] embedding retry ({} - {}) - {}/{}",
                product.getCategory().name(), product.getProductId(), retryCnt, maxRetryCnt);

            sleep();
            return getEmbeddings(retryCnt + 1, product);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private float[] averageEmbeddings(List<float[]> embeddings) {
        int dimension = embeddings.getFirst().length;
        float[] sum = new float[dimension];

        for (float[] vec : embeddings) {
            for (int i = 0; i < dimension; i++) {
                sum[i] += vec[i];
            }
        }

        int count = embeddings.size();
        for (int i = 0; i < dimension; i++) {
            sum[i] /= count;
        }
        return sum;
    }
}
