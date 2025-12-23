CREATE TABLE users(
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    username VARCHAR(255) NOT NULL,
    password CHAR(60) NOT NULL,
    wins INTEGER NOT NULL DEFAULT 0,
    UNIQUE (username)
);

CREATE TABLE users_roles (
    user_id BINARY(16) NOT NULL,
    role_id VARCHAR(255) NOT NULL,
    primary key (user_id, role_id),
    constraint foreign key (user_id) references users(id) on update cascade on delete cascade,
    constraint foreign key (role_id) references roles(id) on update cascade on delete cascade
);

