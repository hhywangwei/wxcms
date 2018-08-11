--======================================================================================================================
--修改ref_detail长度
--======================================================================================================================
ALTER TABLE sns_counter MODIFY COLUMN ref_detail VARCHAR(100) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '关联描述';
ALTER TABLE sns_comment MODIFY COLUMN ref_detail VARCHAR(100) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '关联描述';

--======================================================================================================================
--增加频道路径字段
--======================================================================================================================
ALTER TABLE site_article ADD channel_path VARCHAR(200);

UPDATE site_article INNER JOIN (SELECT id, path_full FROM site_channel) t1 ON site_article.channel_id = t1.id
SET site_article.channel_path = t1.path_full;

ALTER TABLE site_article MODIFY COLUMN channel_path VARCHAR(200) COLLATE utf8_bin NOT NULL COMMENT '频道路径';

ALTER TABLE site_article ADD INDEX idx_channel_path (channel_path);