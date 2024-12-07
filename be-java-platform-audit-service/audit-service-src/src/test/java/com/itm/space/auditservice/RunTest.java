package com.itm.space.auditservice;

import org.junit.jupiter.api.Test;

import static com.itm.space.auditservice.initializer.KafkaInitializer.kafkaContainer;
import static com.itm.space.auditservice.initializer.PostgresInitializer.postgreSQLContainer;

class RunTest extends BaseIntegrationTest {

    @Test
    void postgreSQLContainerIsRunning() {
        postgreSQLContainer.isRunning();
    }

    @Test
    void kafkaContainerIsRunning() {
        kafkaContainer.isRunning();
    }
}