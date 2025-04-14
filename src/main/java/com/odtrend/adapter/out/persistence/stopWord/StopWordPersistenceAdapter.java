package com.odtrend.adapter.out.persistence.stopWord;

import com.odtrend.applicaiton.port.out.StopWordStoragePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StopWordPersistenceAdapter implements StopWordStoragePort {

    private final StopWordRepository repository;
    
    @Override
    public List<String> findAll() {
        return repository.findAllNames();
    }
}
