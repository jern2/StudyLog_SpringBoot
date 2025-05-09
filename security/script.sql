
-- security > script.sql

-- tblMember + tblAuth

select * from tabs;

-- 한 유저 > 다중 권한
-- hong > ROLE_MEMBER
-- test > ROLE_ADMIN
--      > ROLE_MEMBER


-- JPA의 엔티티(Member.java)
create table member (
    seq number primary key,
    username varchar2(50) not null unique,
    password varchar2(100) not null,
    role varchar2(50) not null
);



























