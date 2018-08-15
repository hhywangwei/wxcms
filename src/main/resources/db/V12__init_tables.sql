--======================================================================================================================
--修改小程序配置
--======================================================================================================================
ALTER TABLE wx_small_configure MODIFY COLUMN ext VARCHAR(3000) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '小程序ext';
ALTER TABLE wx_small_configure MODIFY COLUMN ext_pages VARCHAR(3000) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '小程序ext_pages';
ALTER TABLE wx_small_configure MODIFY COLUMN pages VARCHAR(3000) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '小程序pages';
ALTER TABLE wx_small_configure MODIFY COLUMN window VARCHAR(3000) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '小程序window';
ALTER TABLE wx_small_configure MODIFY COLUMN tab_bar VARCHAR(3000) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '小程序tab_bar';