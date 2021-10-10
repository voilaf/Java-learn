订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表.

ddl.sql 生成表结构

shardingsphere-proxy 实现

1. proxy 配置
2. 增删改查代码

shardingsphere-jdbc 实现

1. pom.xml 引入
```
		<dependency>
			<groupId>org.apache.shardingsphere</groupId>
			<artifactId>shardingsphere-jdbc-core-spring-boot-starter</artifactId>
			<version>5.0.0-alpha</version>
		</dependency>
```

2. 配置 application.properties
3. 代码共用
