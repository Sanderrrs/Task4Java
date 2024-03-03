package com.example.task4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.*;

@SpringBootApplication
public class Task4Application {


    public static void main(String[] args) {
        // пример использования
        ApplicationContext ctx = SpringApplication.run(Task4Application.class, args);

        String folderPath = "C:\\Users\\amineev\\IdeaProjects\\Task4\\src\\test\\testFiles";
        FileScanner fileScanner = ctx.getBean(FileScanner.class);
        var records = fileScanner.getRecords(folderPath);

        if (records.isEmpty()) {
            System.out.println("No CSV files found in the specified folder.");
        } else {
            System.out.println("CSV files found");
        }
        var checkers = ctx.getBeansOfType(Checker.class).values();

        Iterator<HashMap<String, String>> recordIterator = records.iterator();
        while (recordIterator.hasNext()) {
            var record = recordIterator.next();
            for (Checker checker : checkers) {
                var newRecord = checker.checkRecord(record);
                if (newRecord == null) {
                    recordIterator.remove();
                } else {
                    record = newRecord;
                }
            }
        }
        RecordLoader fileLoader = ctx.getBean(RecordLoader.class);

        fileLoader.saveRecords(records);
    }

}
