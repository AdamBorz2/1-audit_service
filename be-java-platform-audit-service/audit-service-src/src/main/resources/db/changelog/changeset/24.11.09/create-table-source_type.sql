CREATE TABLE source_type
(
    id          UUID PRIMARY KEY NOT NULL, -- ИД записи
    name        VARCHAR(100)     NOT NULL, -- Имя типа источника
    description TEXT             NOT NULL, -- Описание типа источника
    delete_date TIMESTAMP                 -- Дата-время удаления записи
);

INSERT INTO source_type (id, name, description, delete_date)
VALUES (gen_random_uuid(), 'KAFKA_TOPIC', 'Источником события является топик Kafka', NULL),
       (gen_random_uuid(), 'API', 'Источником события является внешний API', NULL),
       (gen_random_uuid(), 'SCHEDULER', 'Источником события является планировщик задач', NULL),
       (gen_random_uuid(), 'MANUAL', 'Событие создано вручную', NULL);