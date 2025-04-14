package com.odtrend.adapter.in.init_stop_word;

import com.odtrend.applicaiton.port.in.FindStopWordUseCase;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitStopWordScheduler {

    public static List<String> stopWords;
    private final FindStopWordUseCase findStopWordUseCase;

    @PostConstruct
    public void init() {
        stopWords = findStopWordUseCase.findStopWord();
    }

    @Scheduled(fixedRate = 3600000)
    void initStopWords() {
        stopWords = findStopWordUseCase.findStopWord();
    }
}
