package com.odtrend.fakeClass;

import com.odtrend.applicaiton.port.out.ErrorLogStoragePort;
import com.odtrend.domain.model.ErrorLog;
import java.util.ArrayList;
import java.util.List;

public class FakeErrorLogStoragePort implements ErrorLogStoragePort {

    public List<ErrorLog> database = new ArrayList<>();

    @Override
    public void save(ErrorLog errorLog) {
        database.add(errorLog);
    }
}
