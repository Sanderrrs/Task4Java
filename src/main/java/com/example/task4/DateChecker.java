package com.example.task4;

import com.opencsv.CSVWriter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

@Component
@LogTransformation
public class DateChecker implements Checker {

    @SneakyThrows
    @Override
    public HashMap<String, String> checkRecord(HashMap<String, String> record) {
        String date = record.get("accessDate");
        if (date.isEmpty()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter("./error.csv",true))) {
                {
                    writer.writeNext(new String[]{"No access_date"
                            , record.get("fileName")
                            , record.get("fio")
                            , record.get("username")});
                }
            }
            return null;
        }
        return record;
    }
}
