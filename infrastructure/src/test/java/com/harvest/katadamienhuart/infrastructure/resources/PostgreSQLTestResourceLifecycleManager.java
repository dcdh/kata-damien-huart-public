package com.harvest.katadamienhuart.infrastructure.resources;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

public class PostgreSQLTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {

    private final Logger logger = LoggerFactory.getLogger(PostgreSQLTestResourceLifecycleManager.class);

    private PostgreSQLContainer<?> postgresContainer;

    @Override
    public Map<String, String> start() {
        final Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger);
        postgresContainer = new PostgreSQLContainer<>(
                DockerImageName.parse("debezium/postgres:11-alpine")
                        .asCompatibleSubstituteFor("postgres"))
                .withDatabaseName("sensor")
                .withUsername("username")
                .withPassword("password")
                .waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*", 1));
        postgresContainer.start();
        postgresContainer.followOutput(logConsumer);
        return Map.of(
                "quarkus.datasource.db-kind", "postgresql",
                "quarkus.datasource.jdbc.url", postgresContainer.getJdbcUrl(),
                "quarkus.datasource.username", postgresContainer.getUsername(),
                "quarkus.datasource.password", postgresContainer.getPassword()
        );
    }

    @Override
    public void stop() {
        if (postgresContainer != null) {
            postgresContainer.close();
        }
    }
}
