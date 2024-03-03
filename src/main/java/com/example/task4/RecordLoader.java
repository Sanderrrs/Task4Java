package com.example.task4;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class RecordLoader {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LoginsRepository loginsRepository;
    @SneakyThrows
    public void saveRecords(List<HashMap<String,String>> records)
    {
        for (HashMap<String,String> record:
                records)
        {
            String username = record.get("username");
            String fio = record.get("fio");
            Date accessDate = new SimpleDateFormat("dd/MM/yyyy").parse(record.get("accessDate"));
            String application = record.get("application");

            // Проверяем, существует ли пользователь
            User user = usersRepository.findByUsername(username);
            if (user == null) {
                user = new User();
                user.setUsername(username);
                user.setFio(fio);
                user = usersRepository.save(user);
            }

            // Создаем запись о входе
            Login loginRecord = new Login();
            loginRecord.setAccessDate(accessDate);
            loginRecord.setUser(user);
            loginRecord.setApplication(application);
            loginsRepository.save(loginRecord);
        }

    }

}
