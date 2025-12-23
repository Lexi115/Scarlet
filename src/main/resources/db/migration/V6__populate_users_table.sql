SET @default_uuid = (UUID_TO_BIN('00000000-0000-0000-0000-000000000000'));

INSERT INTO users(id, username, password) VALUES (@default_uuid, 'admin', 'pass');

INSERT INTO users_roles(user_id, role_id) VALUES (@default_uuid, 'ADMIN'), (@default_uuid, 'DEFAULT');