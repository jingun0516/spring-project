-- DDL, DML
INSERT INTO member (name) VALUES ('징겅');
INSERT INTO member (name) VALUES ('라미');
INSERT INTO member (name) VALUES ('징겅2');
INSERT INTO member (name) VALUES ('라미2');

INSERT INTO article (title, content, created_at, updated_at)
VALUES ('제목1', '내용1', now(), now()),
       ('제목2', '내용2', now(), now()),
       ('제목3', '내용3', now(), now());