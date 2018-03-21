create table board(
num int not null primary key,
boardid varchar(1) default '1',
writer varchar(10) not null,
email varchar(30),
subject varchar(50) not null,
passwd varchar(12) not null,
reg_date date not null,
readcount int default 0,
ref int not null,
re_step int not null,
re_level int not null,
content varchar(3000) not null,
ip varchar(20) not null,
filename varchar(30),
filesize int
);

select * from board;


create table userlist(
num int not null primary key,
boardid varchar(1) default '1',
userid varchar(10) not null,
passwd varchar(12) not null,
username varchar(10) not null,
gender varchar(10) not null,
hp varchar(30),
address varchar(200),
email varchar(100),
sns varchar(100),
reg_date date not null,
ref int not null,
bio varchar(3000) not null,
filename varchar(30),
filesize int
);

drop table userlist;

select * from userlist order by num;

delete from follow where myid='admin' or friendid='admin'; 

create sequence BoardSer
start with 1
increment by 1
nomaxvalue;

select rownum run, a.*
from emp a
where rownum > 3;

select * from(
select rownum rnum,b.* from(
select * from board
where boardid = 1 order by ref desc , re_step) b)
where rnum between 1 and 3 ;

select * from(
select rownum rnum,b.* from(
select * from userlist
where boardid = 1 order by ref desc , re_step) b)
where rnum between 1 and 3 ;

select * from(
select rownum rnum,b.* from(
select * from guestbook_message
where boardid = 1 order by ref desc , re_step) b)
where rnum between 1 and 3 ;

create table follow(
  myid varchar2(100) not null ,
  friendid varchar2(100) not null
);

select * from follow;
TRUNCATE TABLE follow;
drop table follow;

create table etcInfo (
  etcid VARCHAR2(100) PRIMARY KEY,
  profilename varchar2(100),
  profilesize int,
  facelink varchar2(200),
  instalink varchar2(200),
  soundlink varchar2(200)
);

SELECT * from etcInfo;
drop table etcInfo;
TRUNCATE TABLE etcInfo;


create table songboard(
sboardid varchar(100) primary key,
snum int not null,
stitle varchar(100) not null,
genre varchar(100) not null,
cfilename varchar(100),
cfilesize int,
sfilename varchar(100) not null,
sfilesize int not null,
sbio varchar(3000) not null,
sreg_date date not null
);
select * from songboard;
drop table songboard;

select e.profilename, f.friendid, u.displayname, s.* from etcInfo e, follow f, userlist u, songboard s
where u.userid=f.friendid and e.etcid=f.friendid and s.sboardid=f.friendid and f.myid='TS' order by s.snum;

create table guestmsg (
gboardid varchar2(100),
gnum int not null,
writer varchar2(100) not null,
gtitle varchar2(100) not null,
gcontent varchar2(3000),
greg_date date not null,
gemail varchar2(100),
ref int not null,
re_step int not null,
re_level int not null
);

select * from songboard order by snum desc;

select * from etcinfo;
drop table guestmsg;
select * from userlist order by num desc;

