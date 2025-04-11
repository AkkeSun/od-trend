package com.odtrend.infrastructure.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GzipUtilImplTest {

    @Nested
    @DisplayName("[compress] 문자열을 압축하는 메소드")
    class Describe_compress {

        @Test
        @DisplayName("[success] 압축된 문자열을 응답한다.")
        void success() {
            // given
            String rawString = "안녕";

            // when
            String result = GzipUtil.compress(rawString);

            // then
            assert result.equals("H4sIAAAAAAAA/3szteN161QAanhpggYAAAA=");
        }
    }

    @Nested
    @DisplayName("[decompress] 압축된 문자열을 압축해제하는 메소드")
    class Describe_decompress {

        @Test
        @DisplayName("[success] 해제된 문자열을 응답한다.")
        void success() {
            // given
            String compressedString = "H4sIAAAAAAAA/3szteN161QAanhpggYAAAA=";

            // when
            String result = GzipUtil.decompress(compressedString);

            // then
            assert result.equals("안녕");
        }
    }
}