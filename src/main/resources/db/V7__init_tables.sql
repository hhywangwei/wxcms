--======================================================================================================================
--增加管理员与用户绑定字段
--======================================================================================================================
ALTER TABLE site_manager ADD user_id CHAR(32) COLLATE utf8_bin COMMENT '关联用户编号';
ALTER TABLE site_manager ADD nickname VARCHAR(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户昵称';


--======================================================================================================================
--修改用户不的正确字段类型
--======================================================================================================================
ALTER TABLE site_user MODIFY COLUMN name VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '姓名';
ALTER TABLE site_user MODIFY COLUMN nickname VARCHAR(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户昵称';

--======================================================================================================================
--文章操作日志
--======================================================================================================================
CREATE TABLE IF NOT EXISTS site_article_log (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  article_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '文章编号',
  title VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '标题',
  manager_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '操作员编号',
  username VARCHAR(30) COLLATE utf8_bin NOT NULL COMMENT '操作员用户名',
  action VARCHAR(30) COLLATE utf8_bin NULL COMMENT '操作动作',
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_article_id(article_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;