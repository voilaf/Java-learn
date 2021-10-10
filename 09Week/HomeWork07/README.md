业务ddl

```
create database db1;
create database db2;

// db1和db2分别执行 create table 语句

drop table if exists t_assets_account;
create table t_assets_account (
	id int unsigned not null auto_increment primary key,
	user_id int unsigned not null comment '用户id', 
	dollar_balance decimal(11, 2) default 0 not null comment '美元余额',
	rmb_balance decimal(11, 2) default 0 not null comment '人民币余额',
	version int unsigned default 0 not null comment '乐观锁控制',
	create_time datetime not null,
	update_time datetime not null,
	unique key `user_id` (`user_id`)
) comment '资产账户表';

drop table if exists t_assets_freeze;
create table t_assets_freeze (
	id int unsigned not null auto_increment primary key,
	user_id int unsigned not null comment '用户id', 
	transfer_amount decimal(11, 2) not null comment '转账金额：美元或者人民币',
	type tinyint not null comment '类型 1: 美元转人民币 2: 人民币转美元',
	status tinyint default 0 not null comment '状态0:未处理 1:commit处理 2:cancel处理',
	distributed_unique_no varchar(50) not null comment '单次分布式事务唯一id，用于cancel和commit操作定位',
	create_time datetime not null,
	update_time datetime not null,
	unique key `user_id_unique_no` (`user_id`, `distributed_unique_no`)
) comment '资产冻结表';


insert into db1.t_assets_account (user_id, dollar_balance, rmb_balance, create_time, update_time) values (1, 10, 100, now(), now());

insert into db2.t_assets_account (user_id, dollar_balance, rmb_balance, create_time, update_time) values (2, 10, 100, now(), now());
```

hmily tcc 处理位置

```
com.example.web.manager.impl.TradeManagerImpl#transfer
```

说明：

tcc1 连接 db1 （userId为1 存储库），完成用户1的处理

tcc2 连接 db2 （userId为2 存储库），完成用户2的处理

tcc1 和 tcc2 完成各自用户的外汇处理，各模拟30%失败情况，

预期效果：不管是否执行异常，两个用户的"人民币及美元账户余额总和"保持不变。

