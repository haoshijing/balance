drop TABLE IF EXISTS t_pay;
create table t_pay(
  id int primary key auto_increment comment '自动生成的主键id',
  money varchar(200) comment '成交金额',
  payTime varchar(30) comment '支付时间',
  insertTime bigint comment '数据写入时间',
  systemId int comment '业务系统id',
  syncTime bigint comment '同步时间',
  payId int comment '对应支付系统的id',
  payType varchar(50) comment '支付方式',
  index idx_system_pay(systemId,payId)
);

drop TABLE IF EXISTS t_system;
create table t_system(
  id int primary key auto_increment comment '自动生成的主键id',
  name varchar(200) comment '系统名称',
  dbHost varchar(100) comment '业务数据Host',
  dbName varchar(30) comment '数据库名',
  dbUserName varchar(30) comment '业务系统数据用户名',
  dbPassword varchar(30) comment '业务系统数据库密码'

);

drop TABLE IF EXISTS t_system_total;
create table t_system_total(
  id int primary key auto_increment comment '自动生成的主键id',
  systemId int comment '业务系统id',
  indexAt int comment '所在周',
  yearStr varchar(20) comment '年份',
  money int comment '钱数',
  typeVal int comment '类别',
  index idx_systemId_index_type(systemId,indexAt,type)
);

drop TABLE IF EXISTS t_system_total_month;
create table t_system_total_month(
  id int primary key auto_increment comment '自动生成的主键id',
  systemId int comment '业务系统id',
  month int comment '所在月',
  money int comment '钱数',
  year varchar(20) comment '年份',
  index idx_systemId_month(systemId,month)
);


insert into t_system(`name`,`dbHost`,`dbName`,`dbUserName`,`dbPassword`)
 values ('十三水','106.15.200.15' ,'sanshui','keke','123456');

 drop TABLE IF EXISTS t_admin;
create table t_admin
(
id int primary key auto_increment comment '主键id',
userName varchar(200) comment '用户名',
password varchar(200) comment '密码',
saltPassword varchar(200) comment '加密盐值',
insertTime bigint comment '写入时间',
lastUpdateTime bigint comment '最后修改时间',
status int1 comment '账号状态'
) comment '管理员表';

Insert into t_admin(id,userName ,password ,saltPassword,insertTime,lastUpdateTime ,status)
select
 null , 'admin','77d3b7ed9db7d236b9eac8262d27f6a5','123',  unix_timestamp()*1000, unix_timestamp()*1000,1;



