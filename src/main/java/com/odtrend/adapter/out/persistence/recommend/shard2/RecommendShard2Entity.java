package com.odtrend.adapter.out.persistence.recommend.shard2;

import com.odtrend.domain.model.ProductRecommend;
import com.odtrend.infrastructure.util.SnowflakeGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "PRODUCT_RECOMMEND")
@NoArgsConstructor
class RecommendShard2Entity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "CHECK_DATE")
    private LocalDate checkDate;

    @Column(name = "REG_DATE")
    private LocalDate regDate;

    @Column(name = "REG_DATE_TIME")
    private LocalDateTime regDateTime;

    @Builder
    RecommendShard2Entity(Long id, Long productId, String type, LocalDate checkDate,
        LocalDate regDate, LocalDateTime regDateTime) {
        this.id = id;
        this.productId = productId;
        this.type = type;
        this.checkDate = checkDate;
        this.regDate = regDate;
        this.regDateTime = regDateTime;
    }

    static RecommendShard2Entity of(ProductRecommend recommend) {
        return RecommendShard2Entity.builder()
            .id(SnowflakeGenerator.nextId())
            .productId(recommend.productId())
            .type(recommend.type())
            .checkDate(recommend.checkDate())
            .regDate(recommend.regDate())
            .regDateTime(recommend.regDateTime())
            .build();
    }
}
