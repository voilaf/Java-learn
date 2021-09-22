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
	buy_times int unsigned default 0 comment '购买次数',
	refund_times int unsigned default 0 comment '退款次数',
	total_pay_amount int unsigned default 0 comment '总付款金额',
	total_refund_amount int unsigned default 0 comment '总退款金额',
	version int unsigned default 0 comment '版本号',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	index user_id(user_id)
) comment '用户统计表';

create table user_address (
	id bigint unsigned not null auto_increment primary key,
	user_id bigint unsigned not null comment '用户id',
	contact varchar(20) not null comment '联系方式',
	address varchar(200) not null comment '收获地址',
	default_flag tinyint default 0 not null comment '默认选择标识 0:非选中 1:默认选中',
	status tinyint default 0 comment '状态 0:正常 1:停用',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	index user_id(user_id)
) comment '用户收获地址表';

create table commodity (
	id bigint unsigned not null auto_increment primary key,
	name varchar(50) not null comment '商品名称',
	recommend_sell_id bigint unsigned not null comment '商品推荐出售详情id',
	status tinyint default 0 comment '状态 0:正常 1:删除',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间'
) comment '商品表';

create table specification (
	id bigint unsigned not null auto_increment primary key,
	name varchar(50) not null comment '规格分类名称',
	status tinyint default 0 comment '状态 0:正常 1:删除',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
) comment '规格分类表';

create table commodity_specification (
	id bigint unsigned not null auto_increment primary key,
	commodity_id bigint unsigned not null comment '商品id',
	specification_id bigint unsigned not null comment '规格分类id',
	name varchar(50) not null comment '名称',
	cover_url varchar(255) default '' not null comment '封面图URL',
	status tinyint default 0 comment '状态 0:正常 1:删除',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	index commodity_id(commodity_id)
) comment '商品规格组合表';

create table commodity_sell_list (
	id bigint unsigned not null auto_increment primary key,
	commodity_id bigint unsigned not null comment '商品id',
	commodity_specification_ids varchar(255) not null comment '商品规格组合，json格式，支持1个以上数量',
	name varchar(50) not null comment '名称',
	reserve_nums int unsigned not null default 0 comment '库存数量',
	sell_nums int unsigned not null default 0 comment '已售数量',
	refund_nums int unsigned not null default 0 comment '已退货数量',
	price int unsigned not null default 0 comment '商品单价，单位分',
	cost int unsigned not null default 0 comment '商品成本，单位分',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	index commodity_id(commodity_id)
) comment '商品可出售最小单位表';

create table bill (
	id bigint unsigned not null auto_increment primary key,
	user_id bigint unsigned not null comment '用户id',
	bill_price int unsigned not null comment '订单金额',
	actual_price int unsigned not null comment '实际付款金额',
	refund_price int unsigned not null comment '退款金额',
	contact varchar(20) not null comment '联系方式',
	address varchar(200) not null comment '收获地址',
	refund_reason varchar(255) default '' not null comment '退货原因',
	status tinyint default 0 comment '订单状态 0:待付款 1:已付款 2:取消订单 3:已退款 4:退货中未退款 5:已退货已退款',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	index user_id(user_id)
) comment '订单表';

create table bill_details (
	id bigint unsigned not null auto_increment primary key,
	bill_id bigint unsigned not null comment '订单id',
	commodity_id bigint unsigned not null comment '商品id',
	commodity_sell_id bigint unsigned not null comment '商品规格组合出售id',
	sell_nums int unsigned not null comment '商品数量',
	price int unsigned not null comment '商品单价，单位分',
	total_price int unsigned not null comment '商品总价，单位分',
	create_time datetime not null comment '创建时间',
	index bill_id(bill_id),
	index commodity_id(commodity_id),
	index commodity_sell_id(commodity_sell_id),
) comment '订单详情表';