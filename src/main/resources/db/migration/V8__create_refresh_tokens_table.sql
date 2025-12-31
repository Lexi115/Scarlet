CREATE TABLE refresh_tokens
(
    token_id        bigint primary key auto_increment,
    user_id         binary(16)   not null,
    token_hash      varchar(255) not null,
    expiration_date datetime     not null,
    is_revoked      bool default false,
    unique index idx_token_hash (token_hash),
    index idx_user_id (user_id),
    constraint foreign key (user_id) references users (id) on delete cascade on update cascade
)