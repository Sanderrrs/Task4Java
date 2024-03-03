package com.example.task4;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.text.SimpleDateFormat;

@Service
public class FileScanner {

    List<File> getCsvFiles(String folderPath) {
        List<File> csvFiles = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
            if (files != null) {
                csvFiles.addAll(Arrays.asList(files));
            }
        }
        return csvFiles;
    }

    @SneakyThrows
    List<HashMap<String,String>> getFileRecords(File file) {
        List<HashMap<String,String>> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file.getPath()))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                HashMap<String,String> map = new HashMap<>();
                map.put("username",nextRecord[0]);
                map.put("fio",nextRecord[1]);
                map.put("accessDate",nextRecord[2]);
                map.put("application",nextRecord[3]);
                map.put("fileName",file.getName());
                records.add(map);
            }
        }
        return records;
    }

    List<HashMap<String,String>> getRecords(String folderPath){
        List<HashMap<String,String>> records = new ArrayList<>();
        for (File file:
             getCsvFiles(folderPath)) {
            records.addAll(getFileRecords(file));
        }
        return records;
    }
}

