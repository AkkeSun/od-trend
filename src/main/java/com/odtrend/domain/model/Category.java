package com.odtrend.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {

    DIGITAL("전자제품"),
    FASHION("패션"),
    SPORTS("스포츠"),
    FOOD("식품"),
    LIFE("생활용품");

    private final String description;

    public String description() {
        return description;
    }
    
    public static List<Category> getCategories() {
        return List.of(Category.values());
    }
}
