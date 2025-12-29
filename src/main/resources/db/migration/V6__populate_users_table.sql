SET @uuid = (UUID_TO_BIN('00000000-0000-0000-0000-000000000000'));
SET @username = 'admin';
SET @password = '$2a$12$qmyypmrbRrizb4nr6GGLmOuPFJO0KbssfTxpWFomSj50JYoyQlMKm';
INSERT INTO users(id, username, password) VALUES (@uuid, @username, @password);
INSERT INTO users_roles(user_id, role_id) VALUES (@uuid, 'ADMIN'), (@uuid, 'DEFAULT');