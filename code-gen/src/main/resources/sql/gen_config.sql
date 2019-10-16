CREATE TABLE `gen_config` (
  `config_key` varchar(64) NOT NULL COMMENT '配置key',
  `config_value` varchar(256) DEFAULT NULL COMMENT '配置值',
  `type` tinyint(2) DEFAULT NULL COMMENT '配置类型',
  PRIMARY KEY (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
