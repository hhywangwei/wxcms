--======================================================================================================================
--初始化组织机构
--======================================================================================================================
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '统计局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '民政局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '机关事务局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '物价局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '商务局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '旅游局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '计生委', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '科技局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '卫生局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '人防办', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '建设局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '房产局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '市容局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '政府办', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '司法局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '农林水务局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '信息办', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '工商局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '国土局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '区行政服务中心', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '发改委', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '地税局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '药监局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '执法局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '工信委', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '人社局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '安监局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '审计局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '团区委', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '妇联', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '科协', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '工商联', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '档案局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '残联', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '纪委', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '人武部', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '法院', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '检察院', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '区委办', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '流管办', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '台办', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '接待办', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '组织部', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '宣传部', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '政法委', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '信访局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '老干部局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '党校', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '总工会', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '湓浦街道', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '甘棠街道', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '人民路街道', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '白水湖街道', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '金鸡坡街道', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '国税局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '公安局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '工业园办', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '城投公司', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '教体局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '环保局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '步管办', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '文新局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '财政局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '法制办', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '监察局', 'root', now() FROM site_info LIMIT 1;
INSERT INTO site_organization (id, site_id, name, parent_id, create_time)
SELECT REPLACE(UUID(),"-",""), id, '民宗局', 'root', now() FROM site_info LIMIT 1;
