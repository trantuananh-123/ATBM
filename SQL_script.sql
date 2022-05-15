create database demo
go
use demo
go
create table users(
	id int  primary key identity(1, 1),
    username varchar(100) unique,
    password varchar(100),
    role int default 2
)
go
insert into users (username, password, role)
values
('admin', 'admin', 1),
('user1', 'user1', 2)
go
create table product (
	id int primary key,
	name varchar(50),
	description text,
	price decimal(4,2)
);
go

select top 1 * from users where username = 'aaaa' or 1=1--' and password = '1'

SELECT * FROM product ORDER BY ID OFFSET 0 ROWS FETCH NEXT 10 ROWS ONLY;

exec sp_addlogin 'user', 'passwd';
exec sp_droplogin 'user';
select name from master..sysdatabases;

insert into users values('hacker', '123', 1);

select * from users;
EXEC xp_cmdshell 'dir *.exe';
select * from master.dbo.sysobjects SO where (SO.type = 'X') order by SO.name;

update users set password = '123' where username = 'admin';