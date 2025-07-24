package com.odtrend.adapter.out.client.gemini;

import lombok.Builder;

import java.util.List;

@Builder
record GeminiContentRequest(
    List<GeminiContentText> parts
) {

    @Builder
    record GeminiContentText(
        String text
    ) {

    }

    static GeminiContentRequest of(String text) {
        return GeminiContentRequest.builder()
            .parts(List.of(GeminiContentText.builder()
                .text(text)
                .build()))
            .build();
    }

}
