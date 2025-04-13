package com.odtrend.infrastructure.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;

public class KeywordUtil {
    private static final List<String> stopwords = Arrays.asList(
        "남자", "여자", "남성", "여성", "남", "여", "성인", "어린이", "아동", "키즈", "주니어", "유아","오늘출발","츠","소량",
        "봄", "여름", "가을", "겨울", "계절", "사계절", "간절기", "긴팔", "반팔", "티셔츠", "티","세미","공용","우",
        "회","면","흰색","블랙", "전사이즈", "사이즈", "주", "밋", "클로", "데이", "원데이", "단", "식물","즈","굿",
        "옷", "의류", "의상", "의복", "옷장", "상의", "하의", "바지", "치마", "스커트","예약발송","예약","직송", "걸",
        "스타일", "코디", "룩", "패션", "제품", "아이템", "신상", "명품", "브랜드", "정품","북","업무용","사무용","인강용","세대","사무","인",
        "멋진", "예쁜", "깔끔한", "심플한", "고급스러운", "편안한", "편한", "이쁜", "세련된", "기본", "베이직", "new", "2025", "룸", "아이", "인용"
    );

    private final static Komoran komoran;

    static {
        komoran = new Komoran(DEFAULT_MODEL.FULL);
        komoran.setUserDic(KeywordUtil.class.getClassLoader().getResource("user-dictionary.txt").getPath());
    }

    public static List<String> getKeywords (String input) {
        Set<String> result = new HashSet<>(Arrays.asList(input.split(" ")));
        result.addAll(komoran.analyze(input).getMorphesByTags("NNG", "NNP"));

        return result.stream()
            .map(String::toLowerCase)
            .map(word -> word.replaceAll("[^a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]", ""))
            .filter(word -> !stopwords.contains(word))
            .toList();
    }
}
