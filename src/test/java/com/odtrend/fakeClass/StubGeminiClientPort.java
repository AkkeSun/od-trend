package com.odtrend.fakeClass;

import com.odtrend.applicaiton.port.out.GeminiClientPort;

public class StubGeminiClientPort implements GeminiClientPort {

    @Override
    public float[] embedding(String document) {
        if (document.contains("오류 상품")) {
            throw new RuntimeException();
        }
        return new float[]{1, 2, 3};
    }
}
