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