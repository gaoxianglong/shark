#sequenceid的sql
CREATE TABLE shark_sequenceid(
	s_id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
	s_type INT NOT NULL COMMENT '类型',
	s_useData BIGINT NOT NULL COMMENT '申请占位数量',
	PRIMARY KEY (s_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#不分库分表场景下的读写分离sql
CREATE TABLE userinfo_test(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键',
	userName VARCHAR(20) NOT NULL COMMENT '类型',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#片名连续的库内分片sql
CREATE DATABASE db_0000;
CREATE TABLE userinfo_test_0000(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE userinfo_test_0001(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0000(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0001(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE DATABASE db_0001;
CREATE TABLE userinfo_test_0002(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE userinfo_test_0003(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0002(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0003(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#非片名连续的库内分片sql
CREATE DATABASE db_0000;
CREATE TABLE userinfo_test_0000(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE userinfo_test_0001(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0000(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0001(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE DATABASE db_0001;
CREATE TABLE userinfo_test_0000(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE userinfo_test_0001(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0000(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0001(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#片名连续的一库一片sql
CREATE DATABASE db_0000;
CREATE TABLE userinfo_test_0000(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0000(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE DATABASE db_0001;
CREATE TABLE userinfo_test_0001(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0001(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#非片名连续的一库一片sql
CREATE DATABASE db_0000;
CREATE TABLE userinfo_test(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE DATABASE db_0001;
CREATE TABLE userinfo_test(
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userinfo_test_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userinfo_test_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#other sql,适用于非片名连续的一库一片模式下
CREATE DATABASE db_0000;
CREATE TABLE message_info(
	message_id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
	userinfo_test_id BIGINT NOT NULL COMMENT '路由条件',
	message VARCHAR(80) NOT NULL COMMENT '消息',
	PRIMARY KEY (message_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE DATABASE db_0001;
CREATE TABLE message_info(
	message_id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
	userinfo_test_id BIGINT NOT NULL COMMENT '路由条件',
	message VARCHAR(80) NOT NULL COMMENT '消息',
	PRIMARY KEY (message_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;