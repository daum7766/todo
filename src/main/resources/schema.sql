create table if not exists TODO (
    id bigint auto_increment not null,
    content varchar(50) not null,
    clear boolean default false,
    create_at datetime not null,
    update_at datetime not null,
    primary key (id)
);