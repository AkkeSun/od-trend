package com.odtrend.applicaiton.port.out;

import com.odtrend.domain.model.ErrorLog;

public interface ErrorLogStoragePort {

    void save(ErrorLog errorLog);
}
