CREATE TABLE operations(
    id VARCHAR(255) PRIMARY KEY
);

CREATE TABLE roles(
    id VARCHAR(255) PRIMARY KEY
);

CREATE TABLE roles_operations(
    role_id VARCHAR(255) NOT NULL,
    operation_id VARCHAR(255) NOT NULL,
    primary key (role_id, operation_id),
    constraint foreign key (role_id) references roles(id) on update cascade on delete cascade,
    constraint foreign key (operation_id) references operations(id) on update cascade on delete cascade
);

