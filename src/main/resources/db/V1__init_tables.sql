-------------------------------------------------------------------------
--创建业务数据表
-------------------------------------------------------------------------

--========================================================================
--系统管理员表
--========================================================================
CREATE TABLE IF NOT EXISTS base_sys (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  username VARCHAR(30) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  password VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '密码',
  name VARCHAR(30) COLLATE utf8_bin COMMENT '姓名',
  head_img VARCHAR(300) COLLATE utf8_bin COMMENT '头像',
  phone VARCHAR(20) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '联系电话',
  email VARCHAR(50) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '邮件地址',
  roles VARCHAR(300) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '权限角色',
  is_manager TINYINT DEFAULT 0 NOT NULL COMMENT '1=超级用户',
  is_enable TINYINT DEFAULT 1 NOT NULL COMMENT '1=有效',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '1=删除',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--初始化系统管理员
INSERT INTO base_sys(id, username, password, name, roles, is_manager, is_enable, is_delete, update_time, create_time)
SELECT "1", "admin", "12345678", "admin", "ROLE_SYS", 1, 1, 0, now(), now() FROM DUAL
WHERE NOT EXISTS (SELECT * FROM base_sys WHERE id = "1");

--========================================================================
--上传文件
--========================================================================
CREATE TABLE IF NOT EXISTS base_upload (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '上传用户编号',
  user_type ENUM('CLIENT','MANAGE','SYS') COLLATE utf8_bin NOT NULL COMMENT '用类型',
  url VARCHAR(500) COLLATE utf8_bin NOT NULL COMMENT '图片访问URL',
  path VARCHAR(300) COLLATE utf8_bin NOT NULL COMMENT '保持路径',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--========================================================================
--站点表
--========================================================================
CREATE TABLE IF NOT EXISTS site_info (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(30) COLLATE utf8_bin NOT NULL COMMENT '站点名称',
  phone VARCHAR(20) COLLATE utf8_bin COMMENT '联系电话',
  contact VARCHAR(15) COLLATE utf8_bin COMMENT '联系人',
  province VARCHAR(15) COLLATE utf8_bin NOT NULL COMMENT '省份编号',
  province_name VARCHAR(15) COLLATE utf8_bin DEFAULT  '' COMMENT '省份',
  city VARCHAR(15) COLLATE utf8_bin NOT NULL COMMENT '城市编号',
  city_name VARCHAR(15) COLLATE utf8_bin DEFAULT '' COMMENT '城市',
  county VARCHAR(15) COLLATE utf8_bin NOT NULL COMMENT '县编号',
  county_name VARCHAR(15) COLLATE utf8_bin DEFAULT '' COMMENT '县',
  address VARCHAR(30) COLLATE utf8_bin NOT NULL COMMENT '所在地址',
  location VARCHAR(50) COLLATE utf8_bin COMMENT '地理坐标',
  icon VARCHAR(300) COLLATE utf8_bin COMMENT '图标',
  images VARCHAR(2000) COLLATE utf8_bin COMMENT '显示图片',
  summary VARCHAR(200) COLLATE utf8_bin DEFAULT '' COMMENT '描述摘要',
  detail VARCHAR(2000) COLLATE utf8_bin DEFAULT'' NOT NULL COMMENT '描述',
  state ENUM('WAIT','OPEN','CLOSE') COLLATE utf8_bin NOT NULL COMMENT '站点状态',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--========================================================================
--站点管理员
--=========================================================================
CREATE TABLE IF NOT EXISTS site_manager (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '关联站点编号',
  username VARCHAR(30) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  password VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '密码',
  name VARCHAR(40) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '姓名',
  head_img VARCHAR(300) COLLATE utf8_bin COMMENT '头像',
  phone VARCHAR(20) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '联系电话',
  roles VARCHAR(300) COLLATE utf8_bin COMMENT '权限角色',
  is_manager TINYINT DEFAULT 0 NOT NULL COMMENT '1:超级用户' ,
  is_enable TINYINT DEFAULT 1 NOT NULL COMMENT '1:有效',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT  '1:删除',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uniq_username (username),
  INDEX idx_site_id (site_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--==========================================================================
--站点通知
--==========================================================================
CREATE TABLE IF NOT EXISTS site_notice (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  title VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '消息标题',
  type VARCHAR(20) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '消息类型',
  content VARCHAR(300) COLLATE utf8_bin NOT NULL COMMENT '消息内容',
  uri VARCHAR(200) COLLATE utf8_bin COMMENT 'uri',
  source VARCHAR(100) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '来源',
  is_read TINYINT DEFAULT 0 NOT NULL COMMENT '是否读',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  read_time DATETIME COMMENT '读时间',
  PRIMARY KEY (id),
  INDEX idx_site_id(site_id),
  UNIQUE KEY uniq_site_id_source(site_id, source)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=========================================================================
--站点微信Token
--=========================================================================
CREATE TABLE IF NOT EXISTS site_wx_token (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '微信appid',
  access_token CHAR(200) COLLATE utf8_bin NOT NULL COMMENT '微信公众号access token',
  refresh_token VARCHAR(200) COLLATE utf8_bin NOT NULL COMMENT '微信公众号refresh token',
  expires_time DATETIME NOT NULL COMMENT '过期时间',
  update_time DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id),
  INDEX idx_site_id(site_id),
  UNIQUE KEY uniq_appid (appid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=========================================================================
--站点关联公众号认证信息
--=========================================================================
CREATE TABLE IF NOT EXISTS site_wx_authorized (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '管理公众号appid',
  nickname CHAR(200) COLLATE utf8_bin NOT NULL COMMENT '授权方昵称',
  head_img VARCHAR(200) COLLATE utf8_bin NOT NULL COMMENT '授权方头像',
  service_type_info TINYINT NOT NULL COMMENT '',
  verify_type_info TINYINT NOT NULL COMMENT '授权方认证类型，-1代表未认证，0代表微信认证',
  username VARCHAR(200) COLLATE utf8_bin NULL COMMENT '小程序的原始ID',
  name  VARCHAR(200) COLLATE utf8_bin NULL COMMENT '小程序的主体名称',
  business_info VARCHAR(200) COLLATE utf8_bin NULL COMMENT '用以了解以下功能的开通状况',
  mini_program_info VARCHAR(3000) COLLATE utf8_bin NULL COMMENT '可根据这个字段判断是否为小程序类型授权',
  qrcode_url VARCHAR(500) COLLATE utf8_bin NULL COMMENT '二维码图片的URL，开发者最好自行也进行保存',
  authorization_info VARCHAR(3000) COLLATE utf8_bin NULL COMMENT '授权信息',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id(site_id),
  UNIQUE KEY uniq_appid (appid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=========================================================================
--取消关联站点小程序配置
--=========================================================================
CREATE TABLE IF NOT EXISTS site_wx_unauthorized (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '管理公众号appid',
  nickname CHAR(200) COLLATE utf8_bin NOT NULL COMMENT '授权方昵称',
  head_img VARCHAR(200) COLLATE utf8_bin NOT NULL COMMENT '授权方头像',
  service_type_info TINYINT NOT NULL COMMENT '',
  verify_type_info TINYINT NOT NULL COMMENT '授权方认证类型，-1代表未认证，0代表微信认证',
  username VARCHAR(200) COLLATE utf8_bin NULL COMMENT '小程序的原始ID',
  name  VARCHAR(200) COLLATE utf8_bin NULL COMMENT '小程序的主体名称',
  business_info VARCHAR(200) COLLATE utf8_bin NULL COMMENT '用以了解以下功能的开通状况',
  mini_program_info VARCHAR(3000) COLLATE utf8_bin NULL COMMENT '可根据这个字段判断是否为小程序类型授权',
  qrcode_url VARCHAR(500) COLLATE utf8_bin NULL COMMENT '二维码图片的URL，开发者最好自行也进行保存',
  authorization_info VARCHAR(3000) COLLATE utf8_bin NULL COMMENT '授权信息',
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_site_id(site_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--用户
--===========================================================================
CREATE TABLE IF NOT EXISTS site_user (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  appid VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '微信appid',
  openid VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '微信openid',
  unionid VARCHAR(50) COLLATE utf8_bin COMMENT '微信unionid',
  username VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  password VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '密码',
  name VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '姓名',
  nickname VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '昵称',
  phone VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '联系电话',
  sex VARCHAR(2) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '性别',
  head_img VARCHAR(200) COLLATE utf8_bin COMMENT '图像',
  province VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '省份',
  city VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '城市',
  country VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '县',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uniq_username (username),
  UNIQUE KEY uniq_openid (openid),
  INDEX idx_site_id (site_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--站点频道
--===========================================================================
CREATE TABLE IF NOT EXISTS site_channel (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  parent_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '上级编号',
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  name VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '频道名称',
  icon VARCHAR(200) COLLATE utf8_bin COMMENT '频道图标',
  template VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '显示模板',
  show_order INTEGER DEFAULT 9999 COMMENT '显示排序',
  state ENUM('WAIT','OPEN','CLOSE') COLLATE utf8_bin NOT NULL COMMENT '频道状态',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id (site_id),
  INDEX idx_parent_id (parent_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--文章分类
--===========================================================================
CREATE TABLE IF NOT EXISTS  site_article_catalog (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  name VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  icon VARCHAR(200) COLLATE utf8_bin COMMENT '分类显示图标',
  show_order INTEGER DEFAULT 9999 NOT NULL COMMENT '显示排序',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id (site_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--文章
--===========================================================================
CREATE TABLE IF NOT EXISTS site_article (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  channel_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '频道编号',
  title VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '标题',
  short_title VARCHAR(30) COLLATE utf8_bin COMMENT '短标题',
  sub_title VARCHAR(50) COLLATE utf8_bin COMMENT '子标题',
  author VARCHAR(20) COLLATE utf8_bin COMMENT '作者',
  origin VARCHAR(50) COLLATE utf8_bin COMMENT '来源',
  keywords VARCHAR(200) COLLATE utf8_bin COMMENT '关键字',
  catalogs VARCHAR(200) COLLATE utf8_bin COMMENT '文章分类',
  tag VARCHAR(30) COLLATE utf8_bin COMMENT '文章标签',
  summary VARCHAR(200) COLLATE utf8_bin COMMENT '文章摘要',
  content TEXT COMMENT '文章内容',
  image VARCHAR(200) COLLATE utf8_bin COMMENT '图片',
  is_comment TINYINT DEFAULT 0 NOT NULL COMMENT '是否可以评论',
  template VARCHAR(30) COLLATE utf8_bin DEFAULT 'default' COMMENT '显示模板',
  state ENUM('REEDIT','RELEASE','CLOSE') COLLATE utf8_bin NOT NULL COMMENT '文章状态',
  show_order INTEGER DEFAULT 9999 COMMENT '显示排序',
  is_top  TINYINT DEFAULT 0 NOT NULL COMMENT '是否顶置',
  read_count INTEGER DEFAULT 0 NOT NULL COMMENT '阅读次数',
  good_count INTEGER DEFAULT 0 NOT NULL COMMENT '点赞次数',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id (site_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=========================================================================
--微信小程序消息模板
--=========================================================================
CREATE TABLE IF NOT EXISTS wx_small_msg_template (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  appid VARCHAR(64) COLLATE utf8_bin NOT NULL COMMENT '微信appid',
  global_id VARCHAR(64) COLLATE utf8_bin NOT NULL COMMENT '微信模板编号',
  call_key VARCHAR(64) COLLATE utf8_bin NOT NULL COMMENT '程序调用key',
  template_id VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '公众号中微信模板编号',
  remark VARCHAR(200) COMMENT '备注',
  title VARCHAR(100) COMMENT '标题',
  content VARCHAR(1000) COMMENT '模板内容',
  example VARCHAR(1000) COMMENT '例子',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_appid(appid),
  UNIQUE KEY uniq_appid_call_key (appid, call_key)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--========================================================================
--小程序发送微信模板消息
--========================================================================
CREATE TABLE IF NOT EXISTS wx_msg_message (
  id CHAR(32) NOT NULL,
  appid VARCHAR(100) COLLATE utf8_bin NOT NULL COMMENT '微信appid',
  openid CHAR(32) NOT NULL COMMENT '接收用户openid',
  call_key VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '调用模板key',
  form_id VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '消息发送form_id',
  page VARCHAR(200) COLLATE utf8_bin COMMENT '跳转页面',
  title VARCHAR(100) COLLATE utf8_bin DEFAULT "" COMMENT '标题',
  content VARCHAR(1000) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '内容',
  color VARCHAR(32) COLLATE utf8_bin COMMENT '颜色',
  emphasis_keyword VARCHAR(20) COLLATE utf8_bin COMMENT '放大关键字',
  state ENUM('WAIT', 'SUCCESS', 'FAIL') COLLATE utf8_bin NOT NULL COMMENT '状态',
  error VARCHAR(300) COMMENT '错误信息',
  retry TINYINT DEFAULT 0 NOT NULL COMMENT '重复发送次数',
  create_time TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_appid(appid),
  INDEX idx_openid(openid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=========================================================================
--微信小程序服务域名配置
--=========================================================================
CREATE TABLE IF NOT EXISTS wx_small_domain (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  request_domain VARCHAR(500) COLLATE utf8_bin NOT NULL COMMENT '请求合法域名',
  wsrequest_domain VARCHAR(500) COLLATE utf8_bin NOT NULL COMMENT 'websocket合法域名',
  upload_domain VARCHAR(500) COLLATE utf8_bin NOT NULL COMMENT '上传请求合法域名',
  download_domain VARCHAR(500) COLLATE utf8_bin NOT NULL COMMENT '下载请求合法域名',
  web_view_domain VARCHAR(500) COLLATE utf8_bin NOT NULL COMMENT '业务域名',
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--初始配置信息
INSERT INTO wx_small_domain (id, request_domain, wsrequest_domain, upload_domain, download_domain, web_view_domain)
SELECT '10000', 'https://api.tuoshecx.com', 'wss://api.tuoshecx.com', 'https://api.tuoshecx.com', 'https://api.tuoshecx.com', 'https://tuoshecx.com' FROM DUAL
WHERE NOT EXISTS (SELECT * FROM wx_small_domain WHERE id = '10000');

--=========================================================================
--微信小程序模板配置
--=========================================================================
CREATE TABLE IF NOT EXISTS wx_small_configure (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  template_id INTEGER NOT NULL COMMENT '小程序模板编号',
  ext VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '小程序ext',
  ext_pages VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '小程序ext_pages',
  pages VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '小程序pages',
  window VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '小程序window',
  tab_bar VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '小程序tab_bar',
  debug TINYINT DEFAULT 0 NOT NULL COMMENT '是否调试',
  remark VARCHAR(200) COLLATE utf8_bin COMMENT '备注',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uniq_template_id (template_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--初始缺省配置
INSERT INTO wx_small_configure (id, template_id, ext, ext_pages, pages, window, tab_bar, debug, create_time)
SELECT '1000', -1, '', '', '', '', '', 0, now() FROM DUAL
WHERE NOT EXISTS (SELECT * FROM wx_small_configure WHERE id = '1000');

--=========================================================================
--微信小程序发布
--=========================================================================
CREATE TABLE IF NOT EXISTS wx_small_deploy (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号' ,
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '微信appid',
  is_set_domain TINYINT DEFAULT 0 NOT NULL COMMENT '是否设置服务器域',
  template_id INTEGER NOT NULL COMMENT '小程序模板编号',
  state ENUM('WAIT', 'COMMIT', 'AUDIT', 'PASS', 'REFUSE', 'RELEASE') COLLATE utf8_bin NOT NULL COMMENT '发布状态',
  remark VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL COMMENT '备注',
  audit_id INTEGER DEFAULT -1 COMMENT '审核编号',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id (site_id),
  INDEX idx_appid (appid),
  UNIQUE KEY uniq_appid_template_id(appid, template_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--微信小程序发布日志
--============================================================================
CREATE TABLE IF NOT EXISTS wx_small_deploy_log (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  deploy_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '发布编号',
  action ENUM('SET_DOMAIN', 'COMMIT', 'AUDIT', 'AUDIT_REFUSE', 'AUDIT_PASS', 'RELEASE' ) COLLATE utf8_bin NOT NULL COMMENT '发布动作',
  message VARCHAR(800) COLLATE utf8_bin NOT NULL COMMENT '消息',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--微信小程序审核配置
--===========================================================================
CREATE TABLE IF NOT EXISTS wx_small_audit_configure (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  site_id CHAR(32) COLLATE utf8_bin NOT NULL COMMENT '站点编号',
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '微信appid',
  title VARCHAR(50) COLLATE utf8_bin  NOT NULL COMMENT '标题',
  address VARCHAR(200) COLLATE utf8_bin COMMENT '页面地址',
  tag VARCHAR(50) COLLATE utf8_bin COMMENT '标签',
  first_id INTEGER COMMENT '第一分类编号',
  first_class VARCHAR(50) COLLATE utf8_bin COMMENT '第一分类',
  second_id INTEGER COMMENT '第二分类编号',
  second_class VARCHAR(50) COLLATE utf8_bin COMMENT '第二分类',
  third_id INTEGER COMMENT '第三分类编号',
  third_class VARCHAR(50) COLLATE utf8_bin COMMENT '第三分类',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_site_id (site_id),
  INDEX idx_appid (appid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--初始审核缺省信息
INSERT INTO wx_small_audit_configure (id, site_id, appid, title, address, tag, first_id, first_class, second_id, second_class, create_time)
SELECT '1', '*', '*', '首页', 'pages/index/index', '营销', 150, '生活服务', '666', '休闲娱乐', now()
WHERE NOT EXISTS (SELECT * FROM wx_small_audit_configure WHERE id = '1');