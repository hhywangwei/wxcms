--===============================================================================
--文章等计数器
--===============================================================================
CREATE TABLE IF NOT EXISTS sns_counter (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  ref_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '关联编号',
  ref_detail VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '关联描述',
  read_count INTEGER DEFAULT 0 NOT NULL COMMENT '阅读数量',
  good_count INTEGER DEFAULT 0 NOT NULL COMMENT '点赞数',
  comment_count INTEGER DEFAULT 0 NOT NULL COMMENT  '评论数',
  share_count INTEGER DEFAULT 0 NOT NULL COMMENT  '分享数',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY unq_ref_id (ref_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=============================================================================
--删除文章计数字段
--=============================================================================
ALTER TABLE site_article DROP COLUMN read_count, DROP COLUMN good_count;

--=============================================================================
--删除政民互动计数字段
--=============================================================================
ALTER TABLE site_interaction DROP COLUMN read_count, DROP COLUMN good_count, DROP COLUMN comment_count;