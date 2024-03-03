package com.example.task4;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
@LogTransformation
public class ApplicationChecker implements Checker {

    @Override
    public HashMap<String, String> checkRecord(HashMap<String, String> record) {
        String application = record.get("application");
        if (!Objects.equals(application, "web") && !Objects.equals(application, "mobile")) {
            application = "other:" + application;
            record.put("application", application);
        }
        return record;
    }
}
