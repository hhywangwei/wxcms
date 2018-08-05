--===============================================================================
--微信小程序测试
--===============================================================================
CREATE TABLE IF NOT EXISTS wx_small_tester (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '微信appid',
  wechatid VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '测试者微信编号',
  user_str VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '测试者微信用户标识',
  remark VARCHAR(128) COLLATE utf8_bin COMMENT '测试者微信用户标识',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id (site_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;