基于hmily TCC实现简单分布式事务应用demo

业务ddl

```
create table t_account (
	id int not null auto_increment primary key,
	name varchar(30) default '' not null,
	phone varchar(20) default '' not null
);

create table t_operate_log (
	id int not null auto_increment primary key,
	content varchar(50) default '' not null
);
```

hmily repository mysql ddl 地址：https://github.com/dromara/hmily/blob/e74a3ee5d05c0d2e6c494e38f90495726dc6976e/hmily-repository/hmily-repository-database/hmily-repository-database-mysql/src/main/resources/mysql/schema.sql

---

`dubbo` 服务说明

web: dubbo consumer

user: dubbo provider


hmily tcc 位置

```
com.example.web.manager.impl.AccountManagerImpl#create
```

通过两次请求user服务的不同方法，模拟分布式事务

第一次请求创建账号

第二次请求添加操作日志（50%概率失败）

confirm 方法为空操作，未做实际逻辑

cancel 当操作日志添加失败时，进行补偿操作，删除此次请求添加的账号

