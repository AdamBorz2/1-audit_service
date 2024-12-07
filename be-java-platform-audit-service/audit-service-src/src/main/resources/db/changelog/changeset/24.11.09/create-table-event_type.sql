CREATE TABLE event_type
(
    id          UUID PRIMARY KEY NOT NULL, -- ИД записи
    name        VARCHAR(100)     NOT NULL, -- Имя типа источника
    description TEXT             NOT NULL, -- Описание типа источника
    delete_date TIMESTAMP                  -- Дата-время удаления записи
);

INSERT INTO event_type (id, name, description, delete_date)
VALUES (gen_random_uuid(), 'CREATE', 'Создание сущности', NULL),
       (gen_random_uuid(), 'UPDATE', 'Обновление сущности', NULL),
       (gen_random_uuid(), 'DELETE', 'Удаление сущности', NULL),
       (gen_random_uuid(), 'VIEW', 'Просмотр сущности', NULL),
       (gen_random_uuid(), 'APPROVE', 'Утверждение действия', NULL),
       (gen_random_uuid(), 'REJECT', 'Отклонение действия', NULL),
       (gen_random_uuid(), 'UPLOAD', 'Загрузка файла', NULL),
       (gen_random_uuid(), 'DOWNLOAD', 'Скачивание файла', NULL);