drop database if exists orderservice;
drop user if exists `orderadmin`@`%`;
drop user if exists `orderuser`@`%`;
create database if not exists orderservice character set utf8mb4 collate utf8mb4_unicode_ci;
create user if not exists `orderadmin`@`%` identified with mysql_native_password by 'password';
grant select, insert, update, delete, create, drop, references, index, alter, execute, create view, show view,
create routine, alter routine, event, trigger on `orderservice`.* to `orderadmin`@`%`;
create user if not exists `orderuser`@`%` identified with mysql_native_password by 'password';
grant select, insert, update, delete, show view on `orderservice`.* to `orderuser`@`%`;
flush privileges;