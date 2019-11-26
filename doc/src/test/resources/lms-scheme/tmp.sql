-- sis




drop table if exists cos_section_user;
create table cos_section_user (
    id bigint not null primary key auto_increment,

    course_id bigint not null,
    section_id bigint not null,
    user_id bigint not null,
    role_id bigint not null,

    type int not null comment '用户身份',

    registry_status int comment '注册状态, 1: 邀请中, 2: 加入',
    registry_origin int comment '注册来源, 1: 管理员添加，2：邀请加入，3：自行加入',

    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL,
    `update_user_id` bigint(20) NOT NULL
) comment '班级用户关联表';



drop table if exists sys_org_user;
create table sys_org_user
(
  id               bigint     not null primary key auto_increment,
  org_id           bigint     not null comment '机构ID',
  user_id          bigint     not null comment '用户ID',
  role_id          bigint     not null comment '角色ID',

  type             int        not null comment '用户身份',

  `create_time`    timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '机构用户角色表';

