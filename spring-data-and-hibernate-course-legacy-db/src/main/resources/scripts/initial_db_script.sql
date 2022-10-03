drop database if exists wp;
drop user if exists `wpadmin`@`%`;
drop user if exists `wpuser`@`%`;
create database if not exists wp character set utf8mb4 collate utf8mb4_unicode_ci;
create user if not exists `wpadmin`@`%` identified with mysql_native_password by 'password';
grant select, insert, update, delete, create, drop, references, index, alter, execute, create view, show view,
create routine, alter routine, event, trigger on `wp`.* to `wpadmin`@`%`;
create user if not exists `wpuser`@`%` identified with mysql_native_password by 'password';
grant select, insert, update, delete, show view on `wp`.* to `wpuser`@`%`;
flush privileges;