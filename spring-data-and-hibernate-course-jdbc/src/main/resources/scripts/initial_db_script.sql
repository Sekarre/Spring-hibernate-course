drop database if exists bookdb2;
drop user if exists `bookadmin`@`%`;
drop user if exists `bookuser`@`%`;
create database if not exists bookdb2 character set utf8mb4 collate utf8mb4_unicode_ci;
create user if not exists `bookadmin`@`%` identified with mysql_native_password by 'password';
grant select, insert, update, delete, create, drop, references, index, alter, execute, create view, show view,
create routine, alter routine, event, trigger on `bookdb2`.* to `bookadmin`@`%`;
create user if not exists `bookuser`@`%` identified with mysql_native_password by 'password';
grant select, insert, update, delete, show view on `bookdb2`.* to `bookuser`@`%`;
flush privileges;