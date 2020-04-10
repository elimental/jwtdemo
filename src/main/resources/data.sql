insert into roles (id, name, created, updated, status) values ('b5f8163e-79b5-11ea-bc55-0242ac130003','ROLE_ADMIN',
'2020-04-08 00:00:00','2020-04-08 00:00:00','ACTIVE');
insert into roles (id, name, created, updated, status) values ('b5f818d2-79b5-11ea-bc55-0242ac130003','ROLE_USER',
'2020-04-08 00:00:00','2020-04-08 00:00:00','ACTIVE');

insert into users (id, username, password, first_name, last_name, email, created, updated, status)
values ('1d36fe84-7b21-11ea-bc55-0242ac130003','admin','$2y$12$2h0l.NMC.6cwileIN/cZMOxyExVYecdR/xX52oZsFuQa6Blpq5RGS',
'dima','ivanov','dima@mail.ru','2020-04-08 00:00:00','2020-04-08 00:00:00','ACTIVE');
insert into users (id, username, password, first_name, last_name, email, created, updated, status)
values ('1d3700c8-7b21-11ea-bc55-0242ac130003','user','$2y$12$ijinQQfC3cgJ6WBloX60NuMHni.NwF/KodDB516uPlqmaTSbGvpDW',
'vasya','sidorov','vasya@mail.ru','2020-04-08 00:00:00','2020-04-08 00:00:00','ACTIVE');

insert into user_roles (user_id, role_id) values ('1d36fe84-7b21-11ea-bc55-0242ac130003','b5f8163e-79b5-11ea-bc55-0242ac130003');
insert into user_roles (user_id, role_id) values ('1d3700c8-7b21-11ea-bc55-0242ac130003','b5f818d2-79b5-11ea-bc55-0242ac130003');