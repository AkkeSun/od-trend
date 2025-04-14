package com.odtrend.applicaiton.service.find_stop_word;

import com.odtrend.applicaiton.port.in.FindStopWordUseCase;
import com.odtrend.applicaiton.port.out.StopWordStoragePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FindStopWordService implements FindStopWordUseCase {

    private final StopWordStoragePort stopWordStoragePort;

    @Override
    public List<String> findStopWord() {
        return stopWordStoragePort.findAll();
    }
}
