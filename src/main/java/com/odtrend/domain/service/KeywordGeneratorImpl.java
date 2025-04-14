package com.odtrend.domain.service;

import static com.odtrend.adapter.in.init_stop_word.InitStopWordScheduler.stopWords;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class KeywordGeneratorImpl implements KeywordGenerator {

    private final static Komoran komoran;

    static {
        komoran = new Komoran(DEFAULT_MODEL.FULL);
        komoran.setUserDic(
            KeywordGeneratorImpl.class.getClassLoader()
                .getResource("user-dictionary.txt").getPath());
    }

    @Override
    public List<String> generateKeywords(String input) {
        Set<String> result = new HashSet<>(Arrays.asList(input.split(" ")));
        result.addAll(komoran.analyze(input).getMorphesByTags("NNG", "NNP"));

        return result.stream()
            .map(String::toLowerCase)
            .map(word -> word.replaceAll("[^a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]", ""))
            .filter(word -> !stopWords.contains(word))
            .filter(word -> !word.matches("-?\\d+(\\.\\d+)?"))
            .filter(StringUtils::hasText)
            .toList();
    }
}
