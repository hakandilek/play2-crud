# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table simple (
  key                       bigint not null,
  name                      varchar(255),
  constraint pk_simple primary key (key))
;

create sequence simple_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists simple;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists simple_seq;

