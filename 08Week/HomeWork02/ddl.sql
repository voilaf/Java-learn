create table t_bill (
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

create table t_bill_details (
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
	index commodity_sell_id(commodity_sell_id)
) comment '订单详情表';
