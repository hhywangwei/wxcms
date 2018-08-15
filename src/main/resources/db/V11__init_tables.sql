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

--======================================================================================================================
--访问趋势
--======================================================================================================================
CREATE TABLE IF NOT EXISTS ana_visit_trend (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  date_str CHAR(8) COLLATE utf8_bin NOT NULL COMMENT '日期',
  type VARCHAR(10) COLLATE utf8_bin NOT NULL COMMENT 'daily:天,week:周,month:月',
  session_cnt INTEGER NOT NULL COMMENT '打开次数',
  visit_pv INTEGER NOT NULL COMMENT '访问次数',
  visit_uv INTEGER NOT NULL COMMENT '访问人数',
  visit_uv_new INTEGER NOT NULL COMMENT '新用户数',
  stay_time_uv FLOAT(8,4) NOT NULL COMMENT '人均停留时长 (浮点型，单位：秒)',
  stay_time_session FLOAT(8,4) NOT NULL COMMENT '次均停留时长 (浮点型，单位：秒)',
  visit_depth FLOAT(6,4) NOT NULL COMMENT '平均访问深度 (浮点型)',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id(site_id),
  INDEX idx_date_str(date_str),
  INDEX idx_type(type)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;