
HomeWork2

按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率

测试了JdbcTemplate的三种插入情况

1、单线程：慢

2、多线程 multiple values：5~6秒

3、多线程 add batch：慢

---

HomeWork9

读写分离 - 动态切换数据源版本 1.0

基于AbstractRoutingDataSource，dao层方法添加Transactional注解，对该注解增加Aop切点，配合readOnly属性选择读或者写库。

---

HomeWork10

读写分离 - 数据库框架版本 2.0

基于 Shardingsphere-jdbc，配置master和slave，自动选择读或者写库。