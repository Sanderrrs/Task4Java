package com.example.task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@SpringBootTest
class Task4ApplicationTests {

    @Autowired
    ApplicationContext ctx;
    @Test
    void fileScannerTest() {
        String folderPath = ".\\src\\test\\java\\com\\example\\task4";
        FileScanner fileScanner = ctx.getBean(FileScanner.class);
        var records = fileScanner.getRecords(folderPath);
        var targerRecords = new HashMap<String,String>();

        targerRecords.put("username","MAO");
        targerRecords.put("fio","mineev aleksandrs");
        targerRecords.put("accessDate","01/11/2024");
        targerRecords.put("application","value");
        targerRecords.put("fileName","test.csv");
        Assertions.assertEquals(Collections.singletonList(targerRecords),records);
    }

    @Test
    void applicationTest() {

        var workingRecord = new HashMap<String,String>();
        workingRecord.put("username","MAO");
        workingRecord.put("fio","mineev aleksandrs");
        workingRecord.put("accessDate","01/11/2024");
        workingRecord.put("application","value");
        workingRecord.put("fileName","test.csv");

        var records = Collections.singletonList(workingRecord);

        var applicationChecker = new ApplicationChecker();

        var checkedRecord = applicationChecker.checkRecord(records.get(0));

        Assertions.assertEquals("other:value",checkedRecord.get("application"));
    }

    @Test
    void nullDateCheckerTest() {

        var workingRecord = new HashMap<String,String>();
        workingRecord.put("username","MAO");
        workingRecord.put("fio","mineev aleksandrs");
        workingRecord.put("accessDate","");
        workingRecord.put("application","value");
        workingRecord.put("fileName","test.csv");

        var records = Collections.singletonList(workingRecord);

        var dateChecker = new DateChecker();

        var checkedRecord = dateChecker.checkRecord(records.get(0));

        Assertions.assertNull(checkedRecord);
    }

    @Test
    void nameCheckerTest() {

        var workingRecord = new HashMap<String,String>();
        workingRecord.put("username","MAO");
        workingRecord.put("fio","mineev aleksander");
        workingRecord.put("accessDate","");
        workingRecord.put("application","value");
        workingRecord.put("fileName","test.csv");

        var records = Collections.singletonList(workingRecord);

        var nameChecker = new NameChecker();

        var checkedRecord = nameChecker.checkRecord(records.get(0));

        Assertions.assertEquals("Mineev Aleksander",checkedRecord.get("fio"));


    }




}
