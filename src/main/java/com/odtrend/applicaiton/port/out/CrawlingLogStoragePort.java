package com.odtrend.applicaiton.port.out;

import com.odtrend.domain.model.CrawlingLog;

public interface CrawlingLogStoragePort {

    void saveAll(CrawlingLog crawlingLog);
}
