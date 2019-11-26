

-- sis
-- sys_sis_import 带批次号，每次导入批次号不同, sys_sis_import_file 记录每次导入的文件列表, sys_sis_import_error 记录每次导入的错误信息
-- 其他具体导入表，数据只存在一份，每次导入时只在该数据做增改操作
drop table if exists sys_sis_import;
create table sys_sis_import (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导入批次号',
  org_id bigint not null comment '导入数据归属机构',
  start_time datetime not null comment '导入任务开始时间',
  end_time datetime  comment '导入任务结束时间',
  is_full_batch_update int not null default 0 comment '是否全量更新',
  is_override_ui_change int not null default 0 comment '是否覆盖',

  total_number int not null default 0 comment '成功变更(增加、更新)记录总数量',
  org_number int not null default 0 comment '成功变更(增加、更新)机构数量',
  term_number int not null default 0 comment '成功变更(增加、更新)学期数量',
  user_number int not null default 0 comment '成功变更用户数量',
  course_number int not null default 0 comment '成功变更(增加、更新)课程数量',
  section_number int not null default 0 comment '成功变更(增加、更新)班级数量',
  section_user_number int not null default 0 comment '成功变更(增加、更新)班级用户数量',
  study_group_set_number int not null default 0 comment '成功变更(增加、更新)学习小组集数量',
  study_group_number int not null default 0 comment '成功变更(增加、更新)学习小组数量',
  study_group_user_number int not null default 0 comment '成功变更(增加、更新)学习小组用户数量',

  error_number int not null default 0 comment '总错误数量',

  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) not null comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment 'SIS 导入记录';

drop table if exists sys_sis_import_file;
create table sys_sis_import_file (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导入批次号',

  file_name varchar(256) not null comment '文件名称',
  file_id varchar(256) not null comment '文件ID',

  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment '导入文件列表';

drop table if exists sys_sis_import_error;
create table sys_sis_import_error (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导入批次号',

  file_name varchar(256) not null comment '文件名称',
  error_code int not null comment '错误代码, 1: 不识别文件名称 2: 文件格式错误 3. 字段类型不匹配 4. 字段值为空 5. 字段值格式错误 6. 字段关联值不存在 7. 字段值重复',
--   error_msg varchar(64) comment '错误描述',
  row_number bigint comment '错误所在行',
  field_name varchar(256) comment '字段名称',
  field_value varchar(256) comment '字段值',

  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment 'SIS 导入错误记录';


-- sis 数据记录表，如果本次导入数据对该记录有影响，则会更新数据 batch_code
-- sis 系统导入的数据都会同步到这些记录表中（包括接口导入的）
-- 记录表中只记录有效数据，对于数据格式错误，关联信息缺失的记录不会进行记录
-- 对应 accounts.csv
drop table if exists sys_sis_import_org;
create table sys_sis_import_org (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导致该数据变更的最近导入批次号',
  target_id bigint comment '导入后系统机构ID',
  org_tree_id varchar(128) comment '系统机构tree_id',

  account_id varchar(128) not null comment 'sis 机构ID',
  parent_account_id varchar(128) comment 'sis 父机构ID（为空时机构将在导入用户所属机构下）',
  name varchar(200) not null,

  operation varchar(20) not null comment '操作: active, deleted',
  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) not null comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment 'SIS导入机构记录表';

-- 对应 terms.csv
drop table if exists sys_sis_import_term;
create table sys_sis_import_term (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导致该数据变更的最近导入批次号',
  target_id bigint comment '导入后系统学期ID',
  org_tree_id varchar(128) comment '学期所属机构tree_id',


  term_id varchar(128) not null comment 'SIS学期ID',
  name varchar(200) not null comment '学期名称',
  start_date varchar(128),
  end_date varchar(128),

  operation varchar(20) not null comment '操作: active, deleted',
  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) not null comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment 'SIS导入学期记录表';

-- 对应 user.csv
drop table if exists sys_sis_import_user;
create table sys_sis_import_user (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导致该数据变更的最近导入批次号',
  target_id bigint comment '导入后系统用户ID',
  org_tree_id varchar(128) comment '所属机构tree_id',

  user_id varchar(128) not null comment 'SIS ID',
  login_id varchar(128) not null comment '登录用户名',
  password varchar(128) comment '登录密码',
  first_name varchar(128) comment '',
  last_name varchar(128) comment '',
  full_name varchar(128) comment '用户全称',
  sortable_name varchar(128) comment '排序名称',
  short_name varchar(128) comment '用户简称 nickname',
  account_id varchar(128) comment 'SIS 机构ID',
  email varchar(128) comment '邮箱信息',

  operation varchar(20) not null comment '操作: active, deleted',
  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) not null comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment 'SIS导入用户记录表';

-- 对应 courses.csv
drop table if exists sys_sis_import_course;
create table sys_sis_import_course (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导致该数据变更的最近导入批次号',
  target_id bigint comment '导入后系统用户ID',
  org_tree_id varchar(128) comment '所属机构tree_id',

  course_id varchar(128) not null comment 'SIS 课程ID',
  short_name varchar(128) not null comment '课程代码',
  long_name varchar(128) not null comment '课程名称',

  account_id varchar(128) comment 'SIS机构ID',
  term_id varchar(128) comment 'SIS学期ID',

  start_date varchar(128) comment '课程开始时间',
  end_date varchar(128) comment '课程结束时间',

  course_format varchar(20) comment '课程格式: online, on_campus, blended',

  operation varchar(20) not null comment '操作: active, deleted, completed',
  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) not null comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment 'SIS导入用户记录表';

-- 对应 sections.csv
drop table if exists sys_sis_import_section;
create table sys_sis_import_section (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导致该数据变更的最近导入批次号',
  target_id bigint comment '导入后系统用户ID',
  org_tree_id varchar(128) comment '所属机构tree_id',


  section_id varchar(128) not null comment 'SIS班级ID',
  name varchar(200) not null comment '班级名称',

  course_id varchar(128) not null comment 'SIS课程ID',
  start_date varchar(128) comment '课程开始时间',
  end_date varchar(128) comment '课程结束时间',

  operation varchar(20) not null comment '操作: active, deleted',
  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) not null comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
);

-- 对应 enrollments.csv
drop table if exists sys_sis_import_section_user;
create table sys_sis_import_section_user (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导致该数据变更的最近导入批次号',
  target_id bigint comment '导入后系统用户ID',
  org_tree_id varchar(128) comment '所属机构tree_id',

  course_id varchar (128) not null comment 'SIS 课程ID',
  user_id varchar(128) not null comment 'SIS 用户ID',
  section_id varchar(128) not null comment 'SIS 班级ID',
  role varchar(128) not null comment 'SIS 角色: student, ta, teacher, observer',

  operation varchar(20) not null comment '操作: active, completed, inactive, deleted',
  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) not null comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment 'SIS 班级成员';

-- 对应 group_categories.csv
drop table if exists sys_sis_import_study_group_set;
create table sys_sis_import_study_group_set (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导致该数据变更的最近导入批次号',
  target_id bigint comment '导入后系统用户ID',
  org_tree_id varchar(128) comment '所属机构tree_id',

  group_category_id varchar(128) not null comment 'SIS 小组集ID',
  category_name varchar(128) not null comment '小组集名称',
  course_id varchar(128) comment '小组集所属课程ID',

  operation varchar(20) comment '操作: active, deleted',
  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) not null comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment 'SIS 学习小组集';

-- 对应 groups.csv
drop table if exists sys_sis_import_study_group;
create table sys_sis_import_study_group (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导致该数据变更的最近导入批次号',
  target_id bigint comment '导入后系统用户ID',
  org_tree_id varchar(128) comment '所属机构tree_id',


  group_id varchar(128) comment 'SIS 小组ID',
  group_category_id varchar(128) comment 'SIS 小组类型',
  name varchar (128) not null comment 'SIS 小组名称',

  operation varchar(20) not null comment '操作: active, deleted',
  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) not null comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment 'SIS 学习小组';

-- 对应 groups_membership.csv
drop table if exists sys_sis_import_study_group_user;
create table sys_sis_import_study_group_user (
  id bigint not null primary key auto_increment,
  batch_code varchar(128) not null comment '导致该数据变更的最近导入批次号',
  target_id bigint comment '导入后系统用户ID',

  group_id varchar(128) not null comment 'SIS 小组ID',
  user_id varchar(128) not null comment 'SIS 用户ID',

  operation varchar(20) comment '操作: active, deleted',
  op_user_id bigint comment '导入任务执行用户',
  op_user_org_tree_id varchar(128) not null comment '执行任务用户所在机构 tree_id',

  create_time datetime not null default CURRENT_TIMESTAMP
) comment 'SIS 学习小组成员';