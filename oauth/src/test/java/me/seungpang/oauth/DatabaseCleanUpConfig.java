package me.seungpang.oauth;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DatabaseCleanUpConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean
    public DatabaseCleanUp databaseCleanup() {
        return new DatabaseCleanUp(entityManager);
    }
}
