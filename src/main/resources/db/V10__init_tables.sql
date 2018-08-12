--======================================================================================================================
--增加检查一段文本是否含有违法违规内容字段
--======================================================================================================================
ALTER TABLE site_interaction ADD sec_check ENUM('UNKNOWN','OK','RISKY') COLLATE utf8_bin COMMENT  '内容合法检查';
UPDATE site_interaction SET sec_check = 'UNKNOWN';
ALTER TABLE site_interaction MODIFY COLUMN sec_check ENUM('UNKNOWN','OK','RISKY') COLLATE utf8_bin NOT NULL COMMENT  '内容合法检查';

ALTER TABLE sns_comment ADD sec_check ENUM('UNKNOWN','OK','RISKY') COLLATE utf8_bin COMMENT  '内容合法检查';
UPDATE sns_comment SET sec_check = 'UNKNOWN';
ALTER TABLE sns_comment MODIFY COLUMN sec_check ENUM('UNKNOWN','OK','RISKY') COLLATE utf8_bin NOT NULL COMMENT  '内容合法检查';
