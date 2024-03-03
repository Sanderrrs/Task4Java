package com.example.task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class DbTests {

    @Autowired
    ApplicationContext ctx;

    @Autowired
    Task4Application projectRepository;


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LoginsRepository loginsRepository;
    @Test
    void recordLoadTest() throws ParseException {

        var workingRecord = new HashMap<String, String>();
        workingRecord.put("username", "MAO");
        workingRecord.put("fio", "mineev aleksander");
        workingRecord.put("accessDate", "01/12/2023");
        workingRecord.put("application", "value");
        workingRecord.put("fileName", "test.csv");

        RecordLoader fileLoader = ctx.getBean(RecordLoader.class);
        //RecordLoader fileLoader = new RecordLoader();

        fileLoader.saveRecords(Collections.singletonList(workingRecord));
        var user = new User(1L,"MAO","mineev aleksander");

        var login = new Login(1L,new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2023"),user,"value");
        Assertions.assertEquals(user,usersRepository.findAll().get(0));
        Assertions.assertEquals(login,loginsRepository.findAll().get(0));
    }
}
