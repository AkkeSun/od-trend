package com.odtrend.fakeClass;

import com.odtrend.applicaiton.port.out.CrawlingLogStoragePort;
import com.odtrend.domain.model.CrawlingLog;
import java.util.ArrayList;
import java.util.List;

public class FakeCrawlingLogStoragePort implements CrawlingLogStoragePort {

    public List<CrawlingLog> database = new ArrayList<>();

    @Override
    public void save(CrawlingLog crawlingLog) {
        database.add(crawlingLog);
    }
}
