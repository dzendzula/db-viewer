create table db_connection
(
  id       serial       not null,
  name     varchar(100) not null,
  host     varchar(100) not null,
  port     numeric      not null,
  db_name  varchar(20)  not null,
  username varchar(100) not null,
  password varchar(100) not null
);

create unique index db_connection_id_uindex
  on db_connection (id);

alter table db_connection
  add constraint db_connection_pk
    primary key (id);