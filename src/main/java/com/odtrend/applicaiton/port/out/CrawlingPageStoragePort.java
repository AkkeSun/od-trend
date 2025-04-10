package com.odtrend.applicaiton.port.out;

import com.odtrend.domain.model.CrawlingPage;
import java.util.List;

public interface CrawlingPageStoragePort {

    List<CrawlingPage> findAll();

}
