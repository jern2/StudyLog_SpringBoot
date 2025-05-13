
-- oauth2 > script.sql

select * from tabs;

create table tblUser (
    seq number primary key,
    username varchar2(100) not null,
    name varchar2(100) not null,    --네이버/구글 회원명
    email varchar2(100) not null,   --네이버/구글 계정
    age number not null,
    address varchar2(100) not null
);
create sequence seqUser;

drop table tblUser;
drop sequence seqUser;

select * from tblUser;








