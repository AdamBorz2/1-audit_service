CREATE TABLE entity_type
(
    id          UUID PRIMARY KEY NOT NULL,              -- ИД записи
    name        VARCHAR(100)     NOT NULL,              -- Имя типа сущности
    description TEXT             NOT NULL,              -- Описание типа сущности
    enabled     BOOLEAN          NOT NULL DEFAULT true, -- Флаг включения/выключения аудита событий данного типа
    delete_date TIMESTAMP                               -- Дата-время удаления записи
);

INSERT INTO entity_type (id, name, description, enabled, delete_date)
VALUES (gen_random_uuid(), 'USER', 'События, связанные с пользователями', true, NULL),
       (gen_random_uuid(), 'TASK', 'События, связанные с задачами', true, NULL),
       (gen_random_uuid(), 'DIRECTION', 'События, связанные с направлениями', true, NULL),
       (gen_random_uuid(), 'TOPIC', 'События, связанные с темами', true, NULL),
       (gen_random_uuid(), 'REVIEW', 'События, связанные с ревью', true, NULL),
       (gen_random_uuid(), 'FILE', 'События, связанные с файлами', true, NULL),
       (gen_random_uuid(), 'NOTIFICATION', 'События, связанные с уведомлениями', true, NULL),
       (gen_random_uuid(), 'ACHIEVEMENT', 'События, связанные с достижениями', true, NULL);
