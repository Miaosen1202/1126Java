-- 公共表

-- table sys_dictionary:
--     id
--     code
--     tree_id
--
-- table sys_dictionary_value:
--     id
--     attr_id
--     name
--     locale
--
--
--     course.nav          '导航', 'zh'
--     course.nav          'Nav', 'en'

use lms;





drop table if exists sys_dictionary;
create table sys_dictionary (
    id bigint not null primary key auto_increment,
    code varchar(64) not null comment '编码',
    name varchar(64) not null comment '默认名称',
    parent_id bigint not null default -1 comment '父字典项ID',
    tree_id varchar(128) not null comment '树编码',

    status int not null default 1 comment '状态',
    is_sys_config int not null default 0 comment '是否系统配置字典项，系统配置为基础数据用户不可编辑',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL,

    unique index ind_tree_id(tree_id),
    unique index ind_code_locale(code)
) comment '字典表';

drop table if exists sys_dictionary_value;
create table sys_dictionary_value (
    id bigint not null primary key auto_increment,
    dictionary_id bigint not null,
    code varchar(64) not null comment '编码',
    name varchar(64) not null comment '名称',
    locale varchar(20) not null default '' comment '区域'
) comment '字典区域值配置';

insert into sys_dictionary (id, name, code, parent_id, tree_id, create_user_id, update_user_id)
values
    (1, 'Course Navigation', 'course.nav', -1, '0001', 1, 1),
    (2, 'Home', 'course.nav.home', 1, '00010001', 1, 1),
    (3, 'Announcements', 'course.nav.announcement', 1, '00010002', 1, 1),
    (4, 'Assignments', 'course.nav.assignment', 1, '00010003', 1, 1),
    (5, 'Discussions', 'course.nav.discussion', 1, '00010004', 1, 1),
    (6, 'Grades', 'course.nav.grade', 1, '00010005', 1, 1),
    (7, 'People', 'course.nav.people', 1, '00010006', 1, 1),
    (8, 'Pages', 'course.nav.page', 1, '00010007', 1, 1),
    (9, 'Files', 'course.nav.file', 1, '00010008', 1, 1),
    (10, 'Syllabus', 'course.nav.syllabus', 1, '00010009', 1, 1),
    (11, 'Outcomes', 'course.nav.outcomes', 1, '00010010', 1, 1),
    (12, 'Quizzes', 'course.nav.quiz', 1, '00010011', 1, 1),
    (13, 'Modules', 'course.nav.module', 1, '00010012', 1, 1),
    (14, 'Conferences', 'course.nav.conference', 1, '00010013', 1, 1),
    (15, 'Collaboration', 'course.nav.collaboration', 1, '00010014', 1, 1),
    (16, 'Attendance', 'course.nav.attendance', 1, '00010015', 1, 1),
    (17, 'Language', 'language', -1, '0002', 1, 1),
    (18, 'English', 'language.en', -1, '00020001', 1, 1),
    (19, '中文', 'language.zh', -1, '00020002', 1, 1);

insert into sys_dictionary_value (dictionary_id, name, code, locale) values
    (2, 'Home', 'course.nav.home', 'en'),
    (3, 'Announcements', 'course.nav.announcement', 'en'),
    (4, 'Assignments', 'course.nav.assignment', 'en'),
    (5, 'Discussions', 'course.nav.discussion', 'en'),
    (6, 'Grades', 'course.nav.grade', 'en'),
    (7, 'People', 'course.nav.people', 'en'),
    (8, 'Pages', 'course.nav.page', 'en'),
    (9, 'Files', 'course.nav.file', 'en'),
    (10, 'Syllabus', 'course.nav.syllabus', 'en'),
    (11, 'Outcomes', 'course.nav.outcomes', 'en'),
    (12, 'Quizzes', 'course.nav.quiz', 'en'),
    (13, 'Modules', 'course.nav.module', 'en'),
    (14, 'Conferences', 'course.nav.conference', 'en'),
    (15, 'Collaboration', 'course.nav.collaboration', 'en'),
    (16, 'Attendance', 'course.nav.attendance', 'en'),

    (2, '主页', 'course.nav.home', 'zh'),
    (3, '公告', 'course.nav.announcement', 'zh'),
    (4, '作业', 'course.nav.assignment', 'zh'),
    (5, '讨论', 'course.nav.discussion', 'zh'),
    (6, '评分', 'course.nav.grade', 'zh'),
    (7, '人员', 'course.nav.people', 'zh'),
    (8, '页面', 'course.nav.page', 'zh'),
    (9, '文件', 'course.nav.file', 'zh'),
    (10, '大纲', 'course.nav.syllabus', 'zh'),
    (11, '结果', 'course.nav.outcomes', 'zh'),
    (12, '测验', 'course.nav.quiz', 'zh'),
    (13, '单元', 'course.nav.module', 'zh'),
    (14, '会议', 'course.nav.conference', 'zh'),
    (15, '协作', 'course.nav.collaboration', 'zh'),
    (16, 'Attendance', 'course.nav.attendance', 'zh');

-- 	初始化数据： announcement, assignment, discussion, grade, people, page, file, outcome, quizzes, module,
--    			conferences, collaboration, attendance


drop table if exists sys_user;
create table sys_user (
    id bigint not null primary key auto_increment,
    org_id bigint not null comment '机构ID',
    org_tree_id varchar(128) not null comment '机构tree_id',
    username varchar(128) comment '登录名',
    password varchar(128) comment '登录密码',
    nickname varchar(128) comment '昵称，用于讨论、消息、评论等',
    first_name varchar(64),
    last_name varchar(64),
    full_name varchar(128) comment '用户全称，用于评分展示(由 first_name及 last_name拼接)',
    sex int not null default 0 comment '性别, 0: 未知, 1: 男, 2: 女',
    sis_id varchar(128) comment 'SIS ID',
    email varchar(128) comment '邮箱',
    phone varchar(20) comment '联系电话',
    title varchar(128) DEFAULT NULL COMMENT '头衔',
    avatar_user_file_id bigint comment '头像文件：关联sys_user_file表主键',
    avatar_file_id varchar(128) comment '头像文件',
    description text comment '简介',
    is_registering int not null default 0 comment '是否注册中用户,注册中用户为课程用户添加时的临时用户,不能正常使用',

    language varchar(20) comment '使用语言（预留）',
    time_zone varchar(20) comment '时区（预留）',

    last_login_time datetime comment '最近登录时间',

    status int not null default 1 comment '启用状态',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20),
    `update_user_id` bigint(20)
) comment '用户表';
# delete
# from sys_user
# where id in (1, 2, 3);
# insert into sys_user(id, org_id, org_tree_id, username, password, nickname, full_name, create_user_id, update_user_id)
# values (1, 1, '0001', 'admin', '202cb962ac59075b964b07152d234b70', 'admin', 'admin', 1, 1);
# insert into sys_user(id, org_id, org_tree_id, username, password, nickname, full_name, create_user_id, update_user_id)
# values (2, 1, '0001', 'teacher', '202cb962ac59075b964b07152d234b70', 'teacher', 'teacher', 1, 1);
# insert into sys_user(id, org_id, org_tree_id, username, password, nickname, full_name, create_user_id, update_user_id)
# values (3, 1, '0001', 'student', '202cb962ac59075b964b07152d234b70', 'student', 'student', 1, 1);

drop table if exists sys_user_email;
create table sys_user_email (
  id bigint not null primary key auto_increment,
  user_id bigint not null,
  email varchar(128) unique not null comment '用户关联邮件',
  is_default int not null default 0 comment '是否默认邮箱(可用于登录)',

  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '用户';

drop table if exists sys_user_link;
create table sys_user_link (
    id bigint not null primary key auto_increment,
    user_id bigint not null,
    name varchar(128) not null comment '名称',
    url varchar(128) not null comment '地址',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
) comment '用户介绍链接';

drop table if exists sys_org;
create table sys_org (
                       id               bigint not null primary key auto_increment,
                       sis_id           varchar(128),
                       parent_id        bigint not null    default -1 comment '上级机构ID',
                       tree_id          varchar(128) not null unique comment '机构树ID',
                       name             varchar(128) not null comment '机构名称',
                       description      text comment '机构描述',
                       type             int                default 2 comment '机构类型: 1. 学校, 2. 非学校',
                       language         varchar(20) comment '机构使用语言（预留）',
                       time_zone        varchar(20) comment '机构时区（预留）',

                       `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       `create_user_id` bigint(20) NOT NULL,
                       `update_user_id` bigint(20) NOT NULL
) comment '机构';
# delete
# from sys_org
# where id = 1;
# insert into sys_org(id, parent_id, tree_id, name, description, create_user_id, update_user_id)
# values (1, -1, '0001', 'sys_root', '系统根目录', 1, 1);
drop table if exists sys_org_user;
create table sys_org_user
(
  id               bigint     not null primary key auto_increment,
  org_id           bigint     not null comment '机构ID',
  user_id          bigint     not null comment '用户ID',
  role_id          bigint     not null comment '角色ID',
  `create_time`    timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '机构用户角色表';
# delete
# from sys_org_user
# where id = 1;
# INSERT INTO sys_org_user (id, `org_id`, `user_id`, `role_id`, `create_user_id`, `update_user_id`)
# VALUES (1, 1, 1, 1, 1, 1);
drop table if exists sys_event;
create table sys_event (
    id bigint not null primary key auto_increment,
    calendar_type int not null comment '日历类型, 1: 个人 2: 课程 3: 学习小组',

    user_id bigint not null comment '所属用户',
    course_id bigint comment '所属课程(calendar=2,3时非空)',
    study_group_id bigint comment '所属小组(calendar=3时非空)',

    title varchar(200) comment '标题',
    description text comment '描述',
    address varchar(128) comment '地址',
    location varchar(128) comment '位置',
    start_time datetime comment '开始时间',
    end_time datetime comment '结束时间',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
) comment '事件';

drop table if exists sys_event_config;
-- create table sys_event_config (
--     id bigint not null primary key auto_increment,
--     event_id bigint not null,
--     section_id bigint comment '班级ID',
--     start_time datetime comment '开始时间',
--     end_time datetime comment '结束时间'
-- ) comment '事件时间配置';


drop table if exists sys_user_todo;
create table sys_user_todo (
    id bigint not null primary key auto_increment,
    calendar_type int not null comment '日历类型, 1: 个人 2: 课程',
    user_id bigint not null comment '所属用户',
    course_id bigint comment '所属课程(calendar_type=2时非空)',
    do_time datetime not null comment '执行时间',
    title varchar(200) comment '标题',
    content text comment '内容',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
) comment '用户代办';


drop table if exists sys_user_file;
create table sys_user_file (
    id bigint not null primary key auto_increment,

    space_type int comment '文件所属空间, 1: 课程 2: 小组 3: 个人(不同空间有不同的配额)',
    dir_usage int not null default 0 comment '用来描述二级文件夹用途，　0: other 1: unfiled（课程内容相关附件，用户讨论、公告回复附件，小组内容相关附件）2: profile(存放用户头像文件) 3: submission（存放用户作业回答附件）',

    course_id bigint comment '文件所属课程',
    study_group_id bigint comment '文件所属学习小组',
    user_id bigint comment '文件所属用户',

    is_directory int not null default 0 comment '是否为文件夹',
    is_system_level int not null default 0 comment '是否系统级别目录（教师学生不能删除）',
    allow_upload int not null default 1 comment '文件夹是否允许用户单独上传文件与创建文件夹',

    file_name varchar(512) not null default '' comment '文件名称',
    file_type varchar(32) comment '文件类型（文件大类型：图片、音频、视频、文档...）',
    file_size bigint comment '文件大小，单位byte',
    file_url varchar(512) comment '文件URL',

    tree_id varchar(128) not null default '' comment '树ID',
    parent_id bigint not null default -1 comment '父id，根节点为-1',

    access_strategy int not null default 1 comment '学生访问策略, 1: 无限制 2: 只能通过链接查询 3: 限制访问时间',
    start_time datetime comment '访问开始时间（access_strategy=3）',
    end_time datetime comment '访问结束时间（access_strategy=3）',

    status int not null default 0 comment '发布状态',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20),
    `update_user_id` bigint(20)
) comment '用户文件，课程下相关文件都需要记录到此表中，并且可以创建文件夹；文件大小、类型受管理员配置限制';
drop table if exists sys_user_e_portfolio;
create table sys_user_e_portfolio (
id bigint(20) not null primary key auto_increment,
user_id bigint(20) not null comment '用户id',
name varchar(500) not null comment '名称',
total_page int(11) NOT NULL DEFAULT '0' COMMENT '子页面数量',
status int(11) not null default  '0' comment '预留字段',
type int(11) DEFAULT NULL comment '预留字段',
visibility int(11) not null default  '2' comment '可见性, 1: 公开, 2: 仅自己可见，3、朋友可见',
create_time timestamp not null default  CURRENT_TIMESTAMP,
update_time timestamp not null default  CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
create_user_id bigint(20) not null,
update_user_id bigint(20) not null
)comment='电子学档栏目基本信息';
drop table if exists sys_user_e_portfolio_column;
create table sys_user_e_portfolio_column (
 id bigint(20) not null primary key auto_increment,
 e_portfolio_id bigint(20) not null comment '电子学档id',
 name varchar(500) not null comment '名称',
 cover_color varchar(10) DEFAULT '#0e38b1' comment '电子学档背景色，格式: #666666',
 seq int(11) not null default  '2147483647' comment '排序',
 status int(11) not null default  '0' comment '预留字段',
 type int(11) DEFAULT NULL comment '预留字段',
 visibility int(11) not null default  '1' comment '预留字段：可见性, 1: 公开, 2: 仅自己可见，3、朋友可见',
 create_time timestamp not null default  CURRENT_TIMESTAMP,
 update_time timestamp not null default  CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 create_user_id bigint(20) not null,
 update_user_id bigint(20) not null
)comment='电子学档栏目信息';
drop table if exists sys_user_e_portfolio_page;
create table sys_user_e_portfolio_page (
 id bigint(20) not null primary key auto_increment,
 e_portfolio_column_id bigint(20) not null comment '电子学档id',
 name varchar(500) not null comment '名称',
 seq int(11) not null default  '2147483647' comment '排序',
 status int(11) not null default  '0' comment '预留字段',
 type int(11) DEFAULT NULL comment '预留字段',
 visibility int(11) not null default  '1' comment '预留字段：可见性, 1: 公开, 2: 仅自己可见，3、朋友可见',
 create_time timestamp not null default  CURRENT_TIMESTAMP,
 update_time timestamp not null default  CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 create_user_id bigint(20) not null,
 update_user_id bigint(20) not null
)comment='电子学档子页信息';
drop table if exists sys_user_e_portfolio_content;
create table sys_user_e_portfolio_content (
   id bigint(20) not null primary key auto_increment,
   e_portfolio_page_id bigint(20) not null comment '电子学档栏目id',
   content MEDIUMTEXT COMMENT '内容',
   create_time timestamp not null default  CURRENT_TIMESTAMP,
   update_time timestamp not null default  CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_user_id bigint(20) not null,
   update_user_id bigint(20) not null
)comment='电子学档栏目内容信息';

drop table if exists sys_user_e_portfolio_page_file;
create table sys_user_e_portfolio_page_file(
  id bigint(20) not null primary key auto_increment,
  e_portfolio_page_id bigint(20) not null comment '电子学档栏目id',
  user_file_id bigint(20) not null comment '文件id',
  create_time timestamp not null default  CURRENT_TIMESTAMP,
  update_time timestamp not null default  CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id bigint(20) not null,
  update_user_id bigint(20) not null
)comment='电子学档页和文件中间表';

drop table if exists sys_login_record;
create table sys_login_record (
  id bigint not null primary key auto_increment,
  user_id bigint not null comment '登录用户',
  username varchar(128) not null comment '用户名',
  ip varchar(40) not null comment '登录IP',

  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) comment '登录日志';
drop table if exists sys_operation_record;
create table sys_operation_record
(
  id            bigint        not null primary key auto_increment,
  user_id       bigint        not null comment '用户ID',
  path          varchar(1024) not null comment '路径',
  body          text          not null comment '请求体',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP
) ENGINE = MYISAM
  DEFAULT CHARSET = utf8 COMMENT '操作日志';
# DELETE
# FROM `lms`.`sys_user`
# WHERE `id` = 99;
# DELETE
# FROM `lms`.`cos_section`
# WHERE `id` = 99;
# DELETE
# FROM `lms`.`cos_assignment`
# WHERE `id` = 99;
# DELETE
# FROM `lms`.`cos_course`
# WHERE `id` = 99;
# DELETE
# FROM `lms`.`cos_assign`
# WHERE `id` in (99, 100, 101);
# INSERT INTO `lms`.`sys_user` (id, `org_id`, `org_tree_id`, `username`, `password`, `nickname`, `first_name`,
#                               `last_name`, `full_name`, `sex`, `sis_id`, `email`, `phone`, `avatar_file_id`,
#                               `description`, `is_registering`, `language`, `time_zone`, `last_login_time`, `title`,
#                               `status`, `create_time`, `update_time`, `create_user_id`, `update_user_id`)
# VALUES (99, 99, '0001', '老师', null, '我是老师', null, null, '我的全名叫老师', 0, null, null, null, null, null, 0, null, null, null,
#         null, 1, '2019-03-06 15:55:38', '2019-03-29 10:27:24', 1, 1);
# INSERT INTO `lms`.`cos_section` (id, `sis_id`, `course_id`, `name`, `start_time`, `end_time`, `is_limit`, `create_time`,
#                                  `update_time`, `create_user_id`, `update_user_id`)
# VALUES (99, null, 99, '99课程班级', null, null, 0, '2019-03-28 20:57:52', '2019-02-28 20:58:33', 1, 1);
# INSERT INTO `lms`.`cos_assignment` (id, `course_id`, `title`, `description`, `score`, `grade_type`, `grade_scheme_id`,
#                                     `is_include_grade`, `submission_type`, `tool_url`, `is_embed_tool`, `allow_text`,
#                                     `allow_url`, `allow_media`, `allow_file`, `file_limit`, `study_group_set_id`,
#                                     `is_grade_each_one`, `status`, `create_time`, `update_time`, `create_user_id`,
#                                     `update_user_id`)
# VALUES (99, 99, '99课程作业', '作业内容是啥呢', 0, null, null, 1, null, null, 1, 0, 0, 0, 0, '', null, null, 1,
#         '2019-03-04 16:50:32', '2019-03-04 17:00:36', 1, 1);
# INSERT INTO `lms`.`cos_course` (id, `sis_id`, `name`, `code`, `description`, `status`, `cover_file_id`, `type`,
#                                 `visibility`, `term_id`, `progress`, `create_mode`, `start_time`, `end_time`,
#                                 `homepage`, `is_concluded`, `include_publish_index`, `org_id`, `is_deleted`,
#                                 `create_time`, `update_time`, `create_user_id`, `update_user_id`)
# VALUES (99, null, '99课程', null, null, 1, null, null, null, 1, null, null, null, null, 'MODULE', 0, 0, 1, 0,
#         '2019-02-28 20:56:36', '2019-02-28 20:56:36', 1, 1);
# INSERT INTO `lms`.`cos_assign` (id, `course_id`, `origin_type`, `origin_id`, `assign_type`, `assign_id`, `limit_time`,
#                                 `start_time`, `end_time`, `create_time`, `update_time`, `create_user_id`,
#                                 `update_user_id`)
# VALUES (99, 99, 1, 99, 1, null, null, null, null, '2019-03-04 17:37:29', '2019-03-04 17:37:29', 1, 1);
# INSERT INTO `lms`.`cos_assign` (id, `course_id`, `origin_type`, `origin_id`, `assign_type`, `assign_id`, `limit_time`,
#                                 `start_time`, `end_time`, `create_time`, `update_time`, `create_user_id`,
#                                 `update_user_id`)
# VALUES (100, 99, 1, 99, 2, 99, null, null, null, '2019-03-04 17:37:29', '2019-03-04 17:37:29', 1, 1);
# INSERT INTO `lms`.`cos_assign` (id, `course_id`, `origin_type`, `origin_id`, `assign_type`, `assign_id`, `limit_time`,
#                                 `start_time`, `end_time`, `create_time`, `update_time`, `create_user_id`,
#                                 `update_user_id`)
# VALUES (101, 99, 1, 99, 3, 99, null, null, null, '2019-03-04 17:37:29', '2019-03-04 17:37:29', 1, 1);
#
# INSERT INTO `lms`.`cos_section_user` (id, `course_id`, `section_id`, `user_id`, `role_id`, `registry_status`,
#                                       `registry_origin`, `create_time`, `update_time`, `create_user_id`,
#                                       `update_user_id`)
# VALUES (99, 99, 99, 2, 2, 2, 4, '2019-01-04 14:26:46', '2019-03-01 20:20:52', 2, 2)
#

drop table if exists oss_file_info;
create table oss_file_info
(
  id                bigint auto_increment
    primary key,
  file_id           varchar(64)                         not null,
  origin_name       varchar(128)                        null,
  file_size         bigint                              not null,
  file_type         varchar(16)                         not null,
  created_time      timestamp default CURRENT_TIMESTAMP null,
  convert_need      int       default '0'               null
    comment '是否转换 0不转换1转换',
  convert_status    int       default '0'               not null
    comment '转换状态 -1:失败 1:成功',
  convert_type      varchar(8)                          null,
  convert_count     int       default '0'               null
    comment '是否次数',
  convert_time      timestamp                           null,
  convert_result    varchar(512)                        null,
  convert_error_msg varchar(4096)                       null
    comment '转换异常'
)
  comment 'oss文件信息';
CREATE INDEX oss_file_info_file_id_index ON oss_file_info (file_id);





