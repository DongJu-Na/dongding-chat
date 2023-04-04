INSERT INTO "USER" ("create_date", "modified_date", "email", "name", "picture") VALUES ('2022-01-01 00:00:00', '2022-01-01 00:00:00', 'test@test.com', 'Test User', '');

DELETE FROM posts;
INSERT INTO  posts (title, content) VALUES ('post one', 'content of post one');