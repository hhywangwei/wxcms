--======================================================================================================================
--小程序概况趋势
--======================================================================================================================
CREATE TABLE IF NOT EXISTS ana_summary_trend (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  date_str CHAR(8) COLLATE utf8_bin NOT NULL COMMENT '日期',
  visit_total INTEGER NOT NULL COMMENT '累计用户数',
  share_pv INTEGER NOT NULL COMMENT '转发次数',
  share_uv INTEGER NOT NULL COMMENT '转发人数',
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_site_id(site_id),
  INDEX idx_date_str(date_str)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;