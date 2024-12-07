CREATE TABLE audit_log
(
    id           UUID PRIMARY KEY NOT NULL DEFAULT GEN_RANDOM_UUID(), -- ИД записи
    event_date   TIMESTAMP,                                           -- Дата-время создания события
    create_date  TIMESTAMP        NOT NULL DEFAULT now(),             -- Дата-время создания записи
    event_type   UUID             NOT NULL,                           -- Тип события
    user_id      UUID,                                                -- ИД пользователя из события
    entity_type  UUID             NOT NULL,                           -- Тип сущности события
    entity_id    VARCHAR(255)     NOT NULL,                           -- ИД сущности события
    event_source UUID             NOT NULL,                           -- Источник события
    event_data   JSONB            NOT NULL,                           -- Тело события
    delete_date  TIMESTAMP,                                           -- Дата-время удаления записи

    CONSTRAINT fk_event_type FOREIGN KEY (event_type) REFERENCES event_type(id) ON DELETE CASCADE,
    CONSTRAINT fk_entity_type FOREIGN KEY (entity_type) REFERENCES entity_type(id) ON DELETE CASCADE,
    CONSTRAINT fk_event_source FOREIGN KEY (event_source) REFERENCES event_source(id) ON DELETE CASCADE
);
