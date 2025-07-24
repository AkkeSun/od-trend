package com.odtrend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odtrend.applicaiton.port.in.RegisterCrawlingProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected RegisterCrawlingProductUseCase registerProductUseCase;
}
