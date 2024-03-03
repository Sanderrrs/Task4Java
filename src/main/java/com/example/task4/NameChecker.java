package com.example.task4;

import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@LogTransformation
public class NameChecker implements Checker{

    @Override
    public HashMap<String,String> checkRecord(HashMap<String,String> record) {
        String fio = record.get("fio");
        fio = WordUtils.capitalizeFully(fio);
        record.put("fio",fio);
        return record;
    }
}
