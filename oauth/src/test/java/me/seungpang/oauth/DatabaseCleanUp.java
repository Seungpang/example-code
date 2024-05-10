package me.seungpang.oauth;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.Type;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class DatabaseCleanUp {

    private final EntityManager entityManager;
    private final List<String> tableNames;

    public DatabaseCleanUp(final EntityManager entityManager) {
        this.entityManager = entityManager;
        this.tableNames = entityManager.getMetamodel()
                .getEntities()
                .stream()
                .map(Type::getJavaType)
                .map(javaType -> javaType.getAnnotation(Table.class))
                .map(Table::name)
                .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNamedQuery(String.format("SET REFERENTIAL_INTEGRITY %s", "FALSE"))
                .executeUpdate();
        for (final String tableName : tableNames) {
            entityManager.createNativeQuery(String.format("TRUNCATE TABLE %s", tableName)).executeUpdate();
            entityManager.createNativeQuery(String.format("ALTER TABLE %s ALTER COLUMN id RESTART WITH 1", tableName)).executeUpdate();
        }
        entityManager.createNativeQuery(String.format("SET REFERENTIAL_INTEGRITY %s", "TRUE"))
                .executeUpdate();
    }
}
