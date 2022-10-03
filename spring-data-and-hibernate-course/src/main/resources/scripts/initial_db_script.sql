drop database if exists bookdb;
drop user if exists `bookadmin`@`%`;
drop user if exists `bookuser`@`%`;
create database if not exists bookdb character set utf8mb4 collate utf8mb4_unicode_ci;
create user if not exists `bookadmin`@`%` identified with mysql_native_password by 'password';
grant select, insert, update, delete, create, drop, references, index, alter, execute, create view, show view,
create routine, alter routine, event, trigger on `bookdb`.* to `bookadmin`@`%`;
create user if not exists `bookuser`@`%` identified with mysql_native_password by 'password';
grant select, insert, update, delete, show view on `bookdb`.* to `bookuser`@`%`;
flush privileges;