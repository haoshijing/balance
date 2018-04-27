drop TABLE IF EXISTS t_order;
create table t_order(
  id int primary key auto_increment comment '自动生成的主键id',
  money varchar(200) comment '成交金额',
  payTime varchar(30) comment '支付时间',
  insertTime bigint comment '数据写入时间',
  systemId int comment '业务系统id'
   unique (selfOrderNo)
);

drop TABLE IF EXISTS t_system;
create table t_system(
  id int primary key auto_increment comment '自动生成的主键id',
  name varchar(200) comment '系统名称',
  dbName varchar(30) comment '支付时间',
  dbuserName varchar(30) comment '业务系统数据用户名',
  dbPassword varchar(30) comment '业务系统数据库密码'
);


