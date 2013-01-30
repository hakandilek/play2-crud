# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table sample (
  key                       bigint not null,
  name                      varchar(255),
  constraint pk_sample primary key (key))
;

create sequence sample_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists sample;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists sample_seq;

