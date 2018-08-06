--====================================================================================
--频道增加path和path_full字段
--====================================================================================
ALTER TABLE site_channel ADD path VARCHAR(20) NOT NULL;
ALTER TABLE site_channel ADD path_full VARCHAR(200) NOT NULL;

UPDATE site_channel SET path_full = id;

ALTER TABLE site_channel ADD UNIQUE (path_full);