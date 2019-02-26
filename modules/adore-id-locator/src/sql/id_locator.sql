/** Clean-up any existing tables */
drop table if exists resolver_0;
drop table if exists resolver_1;
drop table if exists resolver_2;
drop table if exists resolver_3;
drop table if exists resolver_4;
drop table if exists resolver_5;
drop table if exists resolver_6;
drop table if exists resolver_7;
drop table if exists resolver_8;
drop table if exists resolver_9;
drop table if exists resolver_10;
drop table if exists resolver_11;
drop table if exists resolver_12;
drop table if exists resolver_13;
drop table if exists resolver_14;
drop table if exists resolver_15;
drop table if exists resolver_16;
drop table if exists resolver_17;
drop table if exists resolver_18;
drop table if exists resolver_19;
drop table if exists resolver_20;
drop table if exists resolver_21;
drop table if exists resolver_22;
drop table if exists resolver_23;
drop table if exists resolver_24;
drop table if exists resolver_25;
drop table if exists resolver_26;
drop table if exists resolver_27;
drop table if exists resolver_28;
drop table if exists resolver_29;
drop table if exists resolver_30;
drop table if exists resolver_31;
drop table if exists repo_id_map;
drop table if exists load_manager;

/** Initially, create tables without index */
create table resolver_0 (digest binary(16), repo_id int(4));
create table resolver_1 (digest binary(16), repo_id int(4));
create table resolver_2 (digest binary(16), repo_id int(4));
create table resolver_3 (digest binary(16), repo_id int(4));
create table resolver_4 (digest binary(16), repo_id int(4));
create table resolver_5 (digest binary(16), repo_id int(4));
create table resolver_6 (digest binary(16), repo_id int(4));
create table resolver_7 (digest binary(16), repo_id int(4));
create table resolver_8 (digest binary(16), repo_id int(4));
create table resolver_9 (digest binary(16), repo_id int(4));
create table resolver_10 (digest binary(16), repo_id int(4));
create table resolver_11 (digest binary(16), repo_id int(4));
create table resolver_12 (digest  binary(16), repo_id int(4));
create table resolver_13 (digest binary(16), repo_id int(4));
create table resolver_14 (digest  binary(16), repo_id int(4));
create table resolver_15 (digest  binary(16), repo_id int(4));
create table resolver_16 (digest binary(16), repo_id int(4));
create table resolver_17 (digest binary(16), repo_id int(4));
create table resolver_18 (digest binary(16), repo_id int(4));
create table resolver_19 (digest binary(16), repo_id int(4));
create table resolver_20 (digest binary(16), repo_id int(4));
create table resolver_21 (digest binary(16), repo_id int(4));
create table resolver_22 (digest binary(16), repo_id int(4));
create table resolver_23 (digest binary(16), repo_id int(4));
create table resolver_24 (digest binary(16), repo_id int(4));
create table resolver_25 (digest binary(16), repo_id int(4));
create table resolver_26 (digest binary(16), repo_id int(4));
create table resolver_27 (digest  binary(16), repo_id int(4));
create table resolver_28 (digest binary(16), repo_id int(4));
create table resolver_29 (digest binary(16), repo_id int(4));
create table resolver_30 (digest binary(16), repo_id int(4));
create table resolver_31 (digest binary(16), repo_id int(4));

/** Create Index to optimize performance */
create index ind_digest on resolver_0 (digest); 
create index ind_digest on resolver_1 (digest); 
create index ind_digest on resolver_2 (digest); 
create index ind_digest on resolver_3 (digest); 
create index ind_digest on resolver_4 (digest); 
create index ind_digest on resolver_5 (digest); 
create index ind_digest on resolver_6 (digest); 
create index ind_digest on resolver_7 (digest); 
create index ind_digest on resolver_8 (digest); 
create index ind_digest on resolver_9 (digest); 
create index ind_digest on resolver_10 (digest); 
create index ind_digest on resolver_11 (digest); 
create index ind_digest on resolver_12 (digest); 
create index ind_digest on resolver_13 (digest); 
create index ind_digest on resolver_14 (digest); 
create index ind_digest on resolver_15 (digest); 
create index ind_digest on resolver_16 (digest); 
create index ind_digest on resolver_17 (digest); 
create index ind_digest on resolver_18 (digest); 
create index ind_digest on resolver_19 (digest);
create index ind_digest on resolver_20 (digest); 
create index ind_digest on resolver_21 (digest); 
create index ind_digest on resolver_22 (digest); 
create index ind_digest on resolver_23 (digest); 
create index ind_digest on resolver_24 (digest);
create index ind_digest on resolver_25 (digest); 
create index ind_digest on resolver_26 (digest); 
create index ind_digest on resolver_27 (digest); 
create index ind_digest on resolver_28 (digest); 
create index ind_digest on resolver_29 (digest);
create index ind_digest on resolver_30 (digest); 
create index ind_digest on resolver_31 (digest);

/** Load Index into memory */
load index into cache resolver_0;
load index into cache resolver_1;
load index into cache resolver_2;
load index into cache resolver_3;
load index into cache resolver_4;
load index into cache resolver_5;
load index into cache resolver_6;
load index into cache resolver_7;
load index into cache resolver_8;
load index into cache resolver_9;
load index into cache resolver_10;
load index into cache resolver_11;
load index into cache resolver_12;
load index into cache resolver_13;
load index into cache resolver_14;
load index into cache resolver_15;
load index into cache resolver_16;
load index into cache resolver_17;
load index into cache resolver_18;
load index into cache resolver_19;
load index into cache resolver_20;
load index into cache resolver_21;
load index into cache resolver_22;
load index into cache resolver_23;
load index into cache resolver_24;
load index into cache resolver_25;
load index into cache resolver_26;
load index into cache resolver_27;
load index into cache resolver_28;
load index into cache resolver_29;
load index into cache resolver_30;
load index into cache resolver_31;

/** for master db */
create table repo_id_map (
  repo_id  int(4),
  repo_uri varchar(255),
  sdate    datetime,
  fdate    datetime,
  tcount   bigint(20)
);

create table load_manager (
  dbid char(10),
  dbcount int(11),
  status char(1)
) ENGINE=InnoDB;
