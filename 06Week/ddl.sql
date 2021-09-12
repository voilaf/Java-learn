create table user (
	id bigint unsigned not null auto_increment primary key,
	name varchar(50) default '' not null comment '姓名',
	phone varchar(20) not null comment '手机号码',
	avatar_url varchar(100) default '' not null comment '头像',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间'
) comment '用户表';

create table user_statistics (
	id bigint unsigned not null auto_increment primary key,
	user_id bigint unsigned not null comment '用户id',
	buy_times int unsigned default 0 comment '用户购买次数',
	refund_times int unsigned default 0 comment '用户退款次数',
	total_pay_amount int unsigned default 0 comment '用户总付款金额',
	total_refund_amount int unsigned default 0 comment '用户总退款金额',
	version int unsigned default 0 comment '更新版本',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	index user_id(user_id)
) comment '用户统��表';

create table user_address (
	id bigint unsigned not null auto_increment primary key,
	user_id bigint unsigned not null comment '用户id',
	contact varchar(20) not null comment '用户联系方式',
	address varchar(200) not null comment '用户收货地址',
	default_flag tinyint default 0 not null comment '默认选择地址 0:不选中 1:优先选中',
	status tinyint default 0 comment '状态 0:正常 1:废弃',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	index user_id(user_id)
) comment '用户地址表';

create table commodity (
	id bigint unsigned not null auto_increment primary key,
	name varchar(50) not null comment '商品名称',
	price int not null default 0 comment '商品价格，单位:分',
	cover_url varchar(255) default '' not null comment '封面图',
	description varchar(255) default '' not null comment '商品描述',
	status tinyint default 0 comment '状态 0:正常 1:废弃',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间'
) comment '商品表';

create table commodity_update_log (
	id bigint unsigned not null auto_increment primary key,
	commodity_id bigint unsigned not null comment '商品id',
	old_info varchar(255) not null comment 'json格式，修改前内容',
	new_info varchar(255) not null comment 'json格式，修改后内容',
	create_time datetime not null comment '创建时间',
	index commodity_id(commodity_id)
) comment '商品信息修改记录表';

create table bill (
	id bigint unsigned not null auto_increment primary key,
	user_id bigint unsigned not null comment '用户id',
	bill_amount int unsigned not null comment '订单初始总金额，单位：分',
	actual_amount int unsigned not null comment '订单真实付款金额，单位：分',
	refund_amount int unsigned not null comment '订单已退款总额，单位：分',
	contact varchar(20) not null comment '用户联系方式',
	address varchar(200) not null comment '用户收货地址',
	status tinyint default 0 comment '订单状态0:待付款 1:已付款 2:取消付款 3:已退款',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	index user_id(user_id)
) comment '订单表';

create table bill_details (
	id bigint unsigned not null auto_increment primary key,
	bill_id bigint unsigned not null comment '订单id',
	commodity_id bigint unsigned not null comment '商品id',
	quantity int unsigned not null comment '商品数量',
	amount int unsigned not null comment '总金额，单位：分',
	create_time datetime not null comment '创建时间',
	index bill_id(bill_id)
) comment '订单详情表';
