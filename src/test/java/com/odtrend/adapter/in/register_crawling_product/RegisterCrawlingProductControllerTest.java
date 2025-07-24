package com.odtrend.adapter.in.register_crawling_product;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.odtrend.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class RegisterCrawlingProductControllerTest extends ControllerTestSupport {


    @Nested
    @DisplayName("[registerProduct] 크롤링한 상품을 등록하는 API")
    class Describe_registerProduct {

        @Test
        @DisplayName("[success] API 를 호출했을 떄 200 코드를 응답한다.")
        void success() throws Exception {
            // when
            ResultActions actions = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            actions.andExpect(status().isOk())
                .andDo(print());
        }

    }

}