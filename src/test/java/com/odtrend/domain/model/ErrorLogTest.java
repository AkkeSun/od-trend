package com.odtrend.domain.model;

import static com.odtrend.infrastructure.exception.ErrorCode.Product_Nomalizer_Error;

import com.odtrend.infrastructure.exception.CustomBusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ErrorLogTest {

    @Nested
    @DisplayName("[of] ErrorLog 객체를 생성하는 메소드")
    class Describe_of {

        @Test
        @DisplayName("[success] CustomBusinessException 으로 ErrorLog 객체를 생성한다.")
        void success() {
            // given
            ErrorLog errorLog = ErrorLog.of(new CustomBusinessException(Product_Nomalizer_Error),
                "domain", "description");

            // when
            assert errorLog.errorCode() == Product_Nomalizer_Error.getCode();
            assert errorLog.errorMessage().equals(Product_Nomalizer_Error.getMessage());
            assert errorLog.description().equals("description");
            assert errorLog.domain().equals("domain");
            assert errorLog.regDateTime() != null;
        }

        @Test
        @DisplayName("[success] Exception 으로 ErrorLog 객체를 생성한다.")
        void successWithNullValues() {
            // given
            ErrorLog errorLog = ErrorLog.of(new Exception("error"), "domain", "description");

            // when
            assert errorLog.errorCode() == 9999;
            assert errorLog.errorMessage().equals("error");
            assert errorLog.description().equals("description");
            assert errorLog.domain().equals("domain");
            assert errorLog.regDateTime() != null;
        }
    }
}