-- 资源模块相关表
use lms;

drop table if exists res_resource;
create table res_resource (
    id bigint not null primary key auto_increment,
    org_id bigint comment '资源所属机构ID',
    origin_id bigint comment '来源ID，来源有课程、作业、测验、讨论',
    origin_type int not null comment '类型，1：课程，2：作业，3：测验，4：讨论',
    author_id bigint not null comment '分享人ID、即作者',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
) comment '资源表';
-- 查询步骤 查询9年级 为2的9次方 以此类推
-- 查询多个年级 查询9年级和10年级 为2的9次方+2的10次方
-- create table res_resource_share (
--    id bigint not null primary key auto_increment,
--    type varchar(20) not null comment '分享类型: 公共、组、consortiums',
-- ) comment '资源分享';

-- /*
-- 资源分享类型：无（私有）、公告、组、组集合(consortiums)、All of Lorcrux
-- To share the resource with your entire account, select the All of [account] checkbox.
--       If you share to your entire account, the resource will be shared to all groups within the account (including groups you may not belong to)
-- A consortium is a collective of several institutions,
-- which is different than a Commons group.
-- */

drop table if exists res_resource_update_log;
create table res_resource_update_log (
    id bigint not null primary key auto_increment,
    resource_name varchar(200) not null comment '资源名称',
    operation_type int not null comment '操作类型，5：第一次分享，4：更新',
    share_range int not null comment '分享范围，1：自己，2：机构，3：公开',
    origin_type int not null comment '类型，1：课程，2：作业，3：测验，4：讨论',
    status int not null comment '状态，1：正在进行，2：完成，3：失败',
    user_id bigint not null comment '分享人id',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
) comment '资源更新记录';

drop table if exists res_resource_imported_log;
create table res_resource_imported_log (
     id bigint not null primary key auto_increment,
     resource_name varchar(200) not null comment '资源的名称',
     origin_type int not null comment '类型，1：课程，2：作业，3：测验，4：讨论',
     course_name varchar(200) not null comment '课程名称',
     operation_type int not null comment '操作类型，3：导入，4：更新',
     status int not null comment '状态，1：正在进行，2：完成，3：失败',
     user_id bigint not null comment '导入人id',

     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     `create_user_id` bigint(20) NOT NULL,
     `update_user_id` bigint(20) NOT NULL
) comment '资源导入日志';

drop table if exists res_resource_admin_operation_log;
create table res_resource_admin_operation_log (
    id bigint not null primary key auto_increment,
    resource_name varchar(200) not null comment '资源的名称',
    share_range int not null comment '分享范围',
    author_id bigint not null comment '分享人ID',
    is_see int not null default 0 comment '是否被分享人查看',
    operation_type int not null comment '操作类型，1：编辑，2：移除',
    origin_type int not null comment '类型，1：课程，2：作业，3：测验，4：讨论',
    admin_user_name varchar(200) not null comment '资源的名称',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
) comment '管理员操作日志';

drop table if exists res_resource_file;
create table res_resource_file (
    id bigint not null primary key auto_increment,
    resource_id bigint not null comment '资源ID',
    version_id bigint not null comment '版本ID',
    type int not null comment '资源类型，1：封面，2：数据，3：附件',
    file_name varchar(512) not null default '' comment '文件名称',
    file_type varchar(10) not null comment '文件类型',
    file_size bigint not null default -1 comment '文件大小, 单位byte',
    file_url varchar(512) not null default '' comment '路径',
    thumbnail_url varchar(512) comment '缩略图',
    thumbnail_origin_type int comment '缩略图来源，1：上传，2：文件内获取（视频、图片），3：资源库获取',
    thumbnail_origin_id bigint comment '缩略图来源ID（如果引用的资源库文件）',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
) comment '资源文件';

drop table if exists res_resource_review;
create table res_resource_review (
    id bigint not null primary key auto_increment,
    resource_id bigint not null,
    content varchar(1000) comment '内容',
    star_rating int not null default 0 comment '星级',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
) comment '资源评论';

drop table if exists res_resource_tag;
create table res_resource_tag (
    id bigint not null primary key auto_increment,
    resource_id bigint not null comment '资源ID',
    name varchar(100) not null comment '标签名',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
) comment '资源标签';

drop table if exists res_resource_favorite;
create table res_resource_favorite (
    id bigint not null primary key auto_increment,
    resource_id bigint not null comment '资源ID',
    user_id bigint not null comment '收藏人ID',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
)comment '资源收藏表';

drop table if exists res_resource_import;
create table res_resource_import(
    id bigint not null primary key auto_increment,
    resource_id bigint not null comment '资源ID',
    version_id bigint not null comment '版本ID',
    user_id bigint not null comment '导入人ID',
    course_id bigint not null comment '课程ID',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
)comment '资源和导入信息中间表';

drop table if exists res_resource_update;
create table res_resource_update(
  id bigint not null primary key auto_increment,
  name varchar(500) not null comment '资源名称',
  resource_id bigint not null comment '资源ID',
  version_id bigint not null comment '版本ID',
  description text not null comment '描述',
  licence int not null comment '版权',
  grade int comment '分级信息: 0~15 每个年级占对应索引的一个bit e.g.: 5~8年级 二进制为:100100000 十进制为:288.
  查询时年级对应索引位置1 按位&操作 e.g.: 查询8年级 where grade_id & 256',
  share_range int not null comment '分享范围，1：自己，2：机构，3：公开',
  import_count int default 0 comment '资源被导入的次数',

  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
)comment '资源和更新信息中间表';

drop table if exists res_resource_share_setting;
create table res_resource_share_setting(
    id bigint not null primary key auto_increment,
    org_id bigint not null comment '机构ID',
    share_range int not null comment '分享范围，2：机构，3：公开',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
)comment '资源分享设置';

drop table if exists res_resource_version;
create table res_resource_version(
  id bigint not null primary key auto_increment,
  resource_id bigint not null comment '资源ID',
  description text  comment '描述',
  version timestamp not null default current_timestamp comment '版本，用时间作为版本',

  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
)comment '资源版本';

drop table if exists res_resource_version_message;
create table res_resource_version_message(
  id bigint not null primary key auto_increment,
  resource_id bigint not null comment '资源ID',
  is_remind int not null default 1 comment '是否需要提醒，0：不需要，1：需要',
  user_id bigint not null comment '导入资源人的ID',

  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
)comment '资源版本消息';

drop table if exists res_course_import_assignment_group;
create table res_course_import_assignment_group(
   id bigint not null primary key auto_increment,
   course_id bigint not null comment '课程ID',
   assignment_group_id bigint not null comment '小组ID',

   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   `create_user_id` bigint(20) NOT NULL,
   `update_user_id` bigint(20) NOT NULL
)comment '课程和导入小组中间表';

drop table if exists res_resource_import_generation;
create table res_resource_import_generation(
    id bigint not null primary key auto_increment,
    resource_import_id bigint not null comment '被导入的资源记录id',
    new_id bigint not null comment '新生成的对象id',
    origin_id bigint not null comment '原对象id',
    origin_type int not null comment '原对象类型',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
)comment '资源导入和生成对象中间表'
