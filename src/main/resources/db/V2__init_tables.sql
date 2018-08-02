--===========================================================================
--组织机构
--===========================================================================
CREATE TABLE IF NOT EXISTS site_organization (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  parent_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '上级组织机构编号',
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  name VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '组织机构名称',
  icon VARCHAR(200) COLLATE utf8_bin COMMENT '组织机构图标',
  show_order INTEGER DEFAULT 9999 COMMENT '显示排序',
  remark VARCHAR(200) COLLATE utf8_bin COMMENT '备注',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id (site_id),
  INDEX idx_parent_id (parent_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--==========================================================================
--政民互动
--===========================================================================
CREATE TABLE IF NOT EXISTS site_interaction (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  user_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '用户编号',
  nickname VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '昵称',
  head_img VARCHAR(200) COLLATE utf8_bin COMMENT '用户头像',
  organ_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '组织机构编号',
  organ_name VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '组织机构名称',
  title VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '标题',
  action ENUM('CONSULT', 'COMPLAINT', 'SUGGEST') COLLATE utf8_bin NOT NULL COMMENT '互动类型',
  content VARCHAR(2000) COLLATE utf8_bin NOT NULL COMMENT '互动内容',
  images VARCHAR(1000) COLLATE utf8_bin COMMENT '照片',
  read_count INTEGER DEFAULT 0 NOT NULL COMMENT '阅读数量',
  good_count INTEGER DEFAULT 0 NOT NULL COMMENT '点赞数',
  comment_count INTEGER DEFAULT 0 NOT NULL COMMENT  '评论数',
  is_open TINYINT DEFAULT 0 NOT NULL COMMENT '1:公开',
  is_top TINYINT DEFAULT 0 NOT NULL COMMENT '1:置顶',
  show_order INTEGER DEFAULT 9999 NOT NULL COMMENT '显示排序',
  reply VARCHAR(1000) COLLATE utf8_bin COMMENT '回复',
  state ENUM('WAIT', 'HANDING', 'REPLY', 'REFUSE') COLLATE utf8_bin NOT NULL COMMENT '状态',
  form_id VARCHAR(128) COLLATE utf8_bin COMMENT '微信formId',
  reply_time DATETIME COMMENT '回复时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id (site_id),
  INDEX idx_user_id (user_id),
  INDEX idx_organ_id (organ_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--==============================================================================
--评论
--=============================================================================
CREATE TABLE IF NOT EXISTS sns_comment (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  user_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '用户编号',
  nickname VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '昵称',
  head_img VARCHAR(200) COLLATE utf8_bin COMMENT '用户头像',
  ref_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '关联编号',
  ref_detail VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '关联描述',
  content VARCHAR(2000) COLLATE utf8_bin NOT NULL COMMENT '评论内容',
  images VARCHAR(1000) COLLATE utf8_bin COMMENT '照片',
  state ENUM('WAIT', 'PASS', 'REFUSE') COLLATE utf8_bin NOT NULL COMMENT '状态',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id (site_id),
  INDEX idx_user_id (user_id),
  INDEX idx_ref_id (ref_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=============================================================================
--点赞
--=============================================================================
CREATE TABLE IF NOT EXISTS sns_good (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '用户编号',
  nickname VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '昵称',
  head_img VARCHAR(200) COLLATE utf8_bin COMMENT '用户头像',
  ref_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '关联编号',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uniq_user_id_ref_id (user_id, ref_id),
  INDEX idx_user_id (user_id),
  INDEX idx_ref_id (ref_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===============================================================================
--阅读
--===============================================================================
CREATE TABLE IF NOT EXISTS sns_read (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  openid VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '微信openid',
  ref_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '关联编号',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uniq_openid_ref_id (openid, ref_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;