show user;

alter session set "_ORACLE_SCRIPT"=true;
create user springboot identified by java1234;
grant connect, resource, dba to spring;
alter user spring default tablespace users;