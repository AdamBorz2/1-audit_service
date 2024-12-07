CREATE TABLE event_source
(
    id          UUID PRIMARY KEY NOT NULL, -- ИД записи
    source_type UUID             NOT NULL, -- Тип источника событий
    value       VARCHAR(255)     NOT NULL, -- Значение
    delete_date TIMESTAMP,                 -- Дата-время удаления записи

    CONSTRAINT fk_source_type FOREIGN KEY (source_type) REFERENCES source_type (id) ON DELETE CASCADE
);