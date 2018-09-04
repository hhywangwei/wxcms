--======================================================================================================================
--创建问卷项目表
--======================================================================================================================
CREATE TABLE IF NOT EXISTS que_project (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  title VARCHAR(500) COLLATE utf8_bin NOT NULL COMMENT '项目标题',
  content VARCHAR(1000) COLLATE utf8_bin NOT NULL COMMENT '项目内容',
  que_info_id VARCHAR(32) COLLATE utf8_bin NOT NULL COMMENT '问卷调查表编号id',
  type VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '项目类型',
  create_user VARCHAR(100) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '创建用户',
  update_user VARCHAR(100) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '修改用户',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--======================================================================================================================
--创建问卷调查信息表
--======================================================================================================================
CREATE TABLE IF NOT EXISTS que_info(
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  title VARCHAR(500) COLLATE utf8_bin NOT NULL COMMENT '调查信息标题',
  content VARCHAR(1000) COLLATE utf8_bin NOT NULL COMMENT '调查信息内容',
  state ENUM('WAIT','OPEN','CLOSE') COLLATE utf8_bin NOT NULL COMMENT '调查信息状态',
  organ_id VARCHAR(32) COLLATE utf8_bin NOT NULL COMMENT'组织机构编号',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除',
  create_user VARCHAR(100) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '创建用户',
  update_user VARCHAR(100) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '修改用户',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--======================================================================================================================
--创建问卷用户答题表
--======================================================================================================================
CREATE TABLE IF NOT EXISTS que_answer(
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id VARCHAR(32) COLLATE utf8_bin NOT NULL COMMENT '答题用户编号',
  head_img VARCHAR(500) COLLATE utf8_bin NOT NULL COMMENT '答题用户头像',
  nickname VARCHAR(1000) COLLATE utf8_bin NOT NULL COMMENT '答题用户昵称',
  que_info_id VARCHAR(32) COLLATE utf8_bin NOT NULL COMMENT '问卷调查表编号id',
  organ_id VARCHAR(32) COLLATE utf8_bin NOT NULL COMMENT '组织机构编号',
  answer_time VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '答题用时',
  answer VARCHAR(1000) COLLATE utf8_bin NOT NULL COMMENT '用户提交答案',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除',
  PRIMARY KEY (id),
  UNIQUE KEY uniq_user_que_key (user_id,que_info_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;