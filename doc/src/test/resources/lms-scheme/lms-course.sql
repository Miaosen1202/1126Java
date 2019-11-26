drop table if exists sys_term;
create table sys_term
(
  id               bigint       not null primary key auto_increment,
  name             varchar(256) not null comment '学期名称',
  code             varchar(64)  not null comment '学期编码',
  start_time       datetime,
  end_time         datetime,
  org_id           bigint       not null comment '机构ID',
  org_tree_id      varchar(256) not null comment '机构TREE ID',
  sis_id           varchar(256) comment 'SIS ID',

  is_default       int          not null default 0 comment '是否为默认学期',

  `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '学期';

drop table if exists sys_term_config;
create table sys_term_config
(
  id               bigint    not null primary key auto_increment,
  term_id          bigint    not null,
  role_id          bigint    not null,
  start_time       datetime,
  end_time         datetime,

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '各角色在学期中生效时间';


drop table if exists cos_course;
create table cos_course
(
  id                    bigint       not null primary key auto_increment,
  sis_id                varchar(128),
  `name`                varchar(500) not null comment '名称',
  `code`                varchar(200) comment '课程编码',
  description           text comment '描述',
  `status`              int          not null default 0 comment '发布状态',
  publish_time          datetime comment '最近发布时间',
  cover_file_id         bigint comment '课程封面ID',
  `type`                int comment '预留字段',
  visibility            int          not null default 2 comment '可见性, 1: 公开, 2: 课程专属',
  term_id               bigint       not null comment '学期',
  progress              int comment '课程进度，1: 未开始 2: 进行中 3: 已结束',
  create_mode           int comment '创建方式, 1: 导入  2: 教师创建',
  start_time            datetime comment '开始时间（优先学期设置）',
  end_time              datetime comment '结束时间（优先学期设置）',
  homepage              varchar(20)  not null default 'MODULE' comment '课程首页：ACTIVE_STREAM, MODULE, ASSIGNMENTS, SYLLABUS, PAGE',
  is_concluded          int          not null default 0 comment '是否结束',
  concluded_time        datetime comment '关闭时间',
  include_publish_index int          not null default 0 comment '是否包含到公共课程索引',
  allow_join            int          not null default 0 comment '允许学生直接加入',

  org_id                bigint       not null comment '机构ID',
  language              varchar(20) comment '语言',
  is_deleted            int                   default 0 comment '是否删除',
  `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`      bigint(20) NOT NULL,
  `update_user_id`      bigint(20) NOT NULL
) comment '课程';


drop table if exists cos_course_config;
create table cos_course_config
(
  id                                  bigint        not null primary key auto_increment,
  course_id                           bigint unique not null comment '课程ID',
  time_zone                           varchar(20) comment '时区（预留字段）',
  sub_account                         varchar(64) comment '子账号（预留字段）',

  file_storage_limit                  bigint comment '课程内文件上传限制，单位byte（预留）',
  format                              int comment '格式, 1: 校内， 2：在线， 3：混合式',

  enable_grade                        int comment '启用课程评分方案',
  grade_scheme_id                     bigint comment '课程评分方案ID',

  allow_view_before_start_time        int           not null default 0 comment '是否允许开始日期前访问',
  allow_view_after_end_time           int           not null default 0 comment '是否允许结束日期后访问',
  allow_open_registry                 int           not null default 0 comment '开放注册，开放注册后学生可以通过加密链接或Code加入课程（课程为专属时）',
  open_registry_code                  varchar(128) comment '开放注册Code',
  enable_homepage_announce            int           not null default 0 comment '首页显示最新公告',
  homepage_announce_number            int comment '首页显示公告数量',

  -- remove
  --     allow_mark_post_status int not null default 0 comment '允许手动将帖子标记为已读',
  -- 	  allow_discussion_attach_file int not null default 0 comment '允许学生讨论上传附件',
  --     allow_student_create_discussion int not null default 0 comment '学生可创建讨论',
  --     allow_student_edit_discussion int not null default 0 comment '学生可编辑/删除自己话题和评论',

  allow_student_create_study_group    int           not null default 0 comment '学生可以创建学习小组',
  hide_total_in_student_grade_summary int           not null default 0 comment '在学生评分汇总中隐藏总分',
  hide_grade_distribution_graphs      int           not null default 0 comment '对学生隐藏评分分布图',
  allow_announce_comment              int           not null default 1 comment '允许公告评论',
  course_page_edit_type               int                    default 1 comment '课程Page可编辑的类型, 1:教师, 2:教师与学生, 3: 所有人',

  `create_time`                       timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`                       timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`                    bigint(20) NOT NULL,
  `update_user_id`                    bigint(20) NOT NULL
) comment '课程配置';


drop table if exists cos_course_nav;
create table cos_course_nav
(
  id               bigint    not null primary key auto_increment,
  course_id        bigint    not null comment '课程ID',
  name             varchar(128) comment '菜单名',
  code             varchar(128) comment '菜单Code',
  seq              int       not null default 0 comment '菜单排序',
  status           int       not null default 1 comment '状态, 1: 启用, 0: 禁用',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '课程导航菜单配置';


drop table if exists cos_course_user;
create table cos_course_user
(
  id               bigint    not null primary key auto_increment,
  course_id        bigint    not null,
  user_id          bigint    not null,
  is_favorite      int                default 0 comment '收藏课程：收藏的课程将在Dashboard中显示',
  cover_color      varchar(10) comment '课程前景色，格式: #666666',
  course_alias     varchar(500) comment '课程别名',
  seq              int       not null default 0 comment '排序',
  is_active        int       not null default 1 comment '是否激活',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL,

  unique key (course_id, user_id)
) comment '课程用户';

drop table if exists cos_course_user_join_pending;
create table cos_course_user_join_pending
(
  id              bigint              not null primary key auto_increment,
  user_id         bigint              not null,
  course_id       bigint              not null,
  section_id      bigint              not null,
  role_id         bigint              not null,
  section_user_id bigint              not null,
  public_status   int                 not null comment '课程发布状态,已发布后,用户登录系统,如果存在未处理的加入课程消息,会提示用户',
  code            varchar(256) unique not null comment '邀请码',

  `create_time`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  index(course_id
)
  ) comment '课程添加用户,且由于课程未发布,还没有发送邀请给用户的记录';


-- 	初始化数据： announcement, assignment, discussion, grade, people, page, file, outcome, quizzes, module,
--    			conferences, collaboration, attendance

drop table if exists cos_page;
create table cos_page
(
  id               bigint    not null primary key auto_increment,
  course_id        bigint    not null,
  title            varchar(256) comment '标题',
  content          text comment '内容',
  url              varchar(256) comment '页面查看地址',
  editor_type      int comment '编辑类型：1. 教师 2. 教师与学生 3. 任何人',
  study_group_id   bigint comment '小组Page，小组ID',
  todo_time        datetime comment 'todo时间',
  is_front_page    int       not null default 0 comment '是否为FrontPage',
  `status`         int       not null default 0 comment '发布状态',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '页面';


-- ------------------------ 讨论 ----------------------------
drop table if exists cos_discussion;
create table cos_discussion
(
  id                          bigint       not null primary key auto_increment,
  course_id                   bigint       not null comment '课程ID',
  title                       varchar(500) not null comment '标题',
  content                     text comment '内容',

  type                        int          not null default 1 comment '1: 普通讨论， 2： 小组讨论',
  study_group_set_id          bigint comment '学习小组集ID',
  study_group_id              bigint comment '学习小组ID',
  --     parent_id bigint comment '父ID',

  is_pin                      int          not null default 0 comment '是否置顶',
  pin_seq                     int          not null default 0 comment '置顶顺序',

  --     start_time datetime comment '讨论开始时间',
  --     end_time datetime comment '讨论截止时间',
  status                      int          not null default 0 comment '发布状态',
  seq                         int          not null default 0 comment '排序',

  is_grade                    int          not null default 0 comment '是否评分',
  score                       int          not null default 0 comment '分值',
  grade_type                  int comment '评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分',
  grade_scheme_id             bigint comment '评分方案',

  allow_like                  int          not null default 1 comment '允许点赞',
  allow_comment               int          not null default 1 comment '允许评论',
  is_comment_before_see_reply int          not null default 0 comment '是否必须在评论后才能查看其他回复',
  attachment_file_id          bigint comment '附件文件ID',

  last_active_time            datetime comment '最近活动时间',

  is_deleted                  int          not null default 0 comment '是否删除',
  `create_time`               timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`               timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`            bigint(20) NOT NULL,
  `update_user_id`            bigint(20) NOT NULL
) comment '课堂讨论';

drop table if exists cos_discussion_config;
create table cos_discussion_config
(
  id                              bigint        not null primary key auto_increment,
  course_id                       bigint unique not null comment '课程ID',
  user_id                         bigint        not null comment '用户',

  allow_discussion_attach_file    int           not null default 0 comment '允许学生讨论上传附件',
  allow_student_create_discussion int           not null default 0 comment '学生可创建讨论',
  allow_student_edit_discussion   int           not null default 0 comment '学生可编辑/删除自己话题和评论',

  `create_time`                   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`                   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`                bigint(20) NOT NULL,
  `update_user_id`                bigint(20) NOT NULL
) comment '课程配置';



drop table if exists cos_user_config;
create table cos_user_config
(
  id                     bigint        not null primary key auto_increment,
  user_id                bigint unique not null comment '用户配置',
  allow_mark_post_status int           not null default 0 comment '手动将帖子标记为已读',
  cover_color            varchar(10) comment '前景色，格式: #666666(日历使用)',

  `create_time`          timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`          timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`       bigint(20),
  `update_user_id`       bigint(20)
) comment '用户课程相关配置';



drop table if exists cos_discussion_reply;
create table cos_discussion_reply
(
  id                  bigint       not null primary key auto_increment,
  discussion_id       bigint       not null comment '讨论ID',
  study_group_id      bigint comment '学习小组ID',
  discussion_reply_id bigint       not null comment '回复的回复ID(这个ID表示这是一个回复的回复)',
  tree_id             varchar(128) not null comment '树ID',
  content             text comment '内容',
  is_deleted          int          not null default 0 comment '删除标志',
  attachment_file_id  bigint comment '附件文件ID',
  `role_id`           bigint(20) DEFAULT NULL,
  `create_time`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`    bigint(20) NOT NULL,
  `update_user_id`    bigint(20) NOT NULL
);

drop table if exists cos_discussion_subscribe;
create table cos_discussion_subscribe
(
  id               bigint    not null primary key auto_increment,
  discussion_id    bigint    not null comment '讨论ID',
  user_id          bigint    not null comment '订阅用户ID',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '讨论订阅';


drop table if exists cos_content_view_record;
create table cos_content_view_record
(
  id               bigint    not null primary key auto_increment,
  user_id          bigint    not null comment '用户',
  origin_type      int       not null comment '类型，2：讨论话题 6：公告话题 7：讨论回复 8：公告回复 9: 评分讨论',
  origin_id        bigint    not null comment '来源ID',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '讨论，公告及回复阅读记录';


-- ------------------------ Setion(班级) --------------------
drop table if exists cos_section;
create table cos_section
(
  id               bigint       not null primary key auto_increment,
  sis_id           varchar(128),
  course_id        bigint       not null comment '课程ID',
  name             varchar(200) not null comment '名称',
  is_default       int          not null default 0 comment '是否默认课程',
  start_time       datetime comment '开始时间',
  end_time         datetime comment '结束时间',
  is_limit         int          not null default 0 comment '用户只能在时间范围内参与课程练习（预留）',

  `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '班级/单元: 将学生分到不同组中';

drop table if exists cos_section_user;
create table cos_section_user
(
  id               bigint    not null primary key auto_increment,

  course_id        bigint    not null,
  section_id       bigint    not null,
  user_id          bigint    not null,
  role_id          bigint    not null,
  registry_status  int comment '注册状态, 1: 邀请中, 2: 加入',
  registry_origin  int comment '注册来源, 1: 管理员添加，2：邀请加入，3：自行加入',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '班级用户关联表';


-- ------------------------ 测验、作业、讨论、公告等分配记录 ----------------------------
drop table if exists cos_assign;
create table cos_assign
(
  id               bigint    not null primary key auto_increment,
  course_id        bigint    not null comment '课程ID',

  origin_type      int comment '来源类型，1: 作业 2: 讨论 3: 测验 6: 公告',
  origin_id        bigint comment '来源ID',
  assign_type      int comment '分配类型，1: 所有， 2：section(班级), 3: 用户',
  assign_id        bigint comment '分配目标ID',

  limit_time       datetime comment '规定截止日期',
  start_time       datetime comment '可以参加测验开始日期',
  end_time         datetime comment '可以参加测验结束日期',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '测验、作业、讨论、公告等分配到班级的记录';


-- ------------------------ 测验、作业、讨论、公告等分配到个人记录 ----------------------------
drop table if exists cos_assign_user;
CREATE TABLE `cos_assign_user`
(
  `id`             bigint(20) NOT NULL AUTO_INCREMENT,
  `course_id`      bigint(20) NOT NULL COMMENT '课程ID',
  `origin_type`    int(11) DEFAULT NULL COMMENT '来源类型，1: 作业 2:讨论 3:测验  4: 文件 5：页面 6：公告',
  `origin_id`      bigint(20) DEFAULT NULL COMMENT '来源ID',
  `user_id`        bigint(20) DEFAULT NULL COMMENT '被分配到的用户ID',
  `limit_time`     datetime           DEFAULT NULL COMMENT '规定截止日期',
  `start_time`     datetime           DEFAULT NULL COMMENT '可以参加测验开始日期',
  `end_time`       datetime           DEFAULT NULL COMMENT '可以参加测验结束日期',
  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) COMMENT='测验、作业、讨论、公告等分配到个人的记录';

-- drop table if exists cos_assign_to;
-- create table cos_assign_to (
--     id bigint not null primary key auto_increment,
--
--     assign_id bigint not null,
--     assign_type int comment '分配类型，1: 所有， 2：section(班级), 3: 用户',
--     target bigint comment '分配目标ID'
-- );


-- ------------------------ 作业 ----------------------------
drop table if exists cos_assignment;
create table cos_assignment
(
  id                 bigint       not null primary key auto_increment,
  course_id          bigint       not null comment '课程ID',
  title              varchar(500) not null comment '标题',
  description        text comment '描述',
  score              int          not null default 0 comment '分值',

  grade_type         int comment '评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分',
  grade_scheme_id    bigint comment '评分方案',
  is_include_grade   int          not null default 1 comment '包含到总成绩统计里',

  submission_type    int comment '提交类型, 1. 在线、2. 书面、3. 外部工具、 4. 不提交',
  tool_url           varchar(256) comment '外部工具URL，submission_type=3',
  is_embed_tool      int          not null default 1 comment '内嵌工具（不在新窗口打开工具）',
  allow_text         int          not null default 0 comment '在线提交submission_type=1：文本输入',
  allow_url          int          not null default 0 comment '在线提交submission_type=1：网站地址',
  allow_media        int          not null default 0 comment '在线提交submission_type=1：媒体录音',
  allow_file         int          not null default 0 comment '在线提交submission_type=1：文件上传',
  file_limit         varchar(256) not null default "" comment '在线提交submission_type=1&allow_file=1: 上传文件类型限制',
  --    sub_submission_type int comment '子提交类型,在线: 文本、URL、媒体、文件',
  --    submission_file_type varchar(128) comment '回答提交文件类型',

  study_group_set_id bigint comment '小组作业，小组集ID',
  is_grade_each_one  int comment '分别为每位学生指定评分(预留)',

  status             int          not null default 0 comment '发布状态',

  `create_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`   bigint(20) NOT NULL,
  `update_user_id`   bigint(20) NOT NULL
) comment '作业';


-- ------------------------ 作业小组/Assignments ----------------------------
drop table if exists cos_assignment_group;
create table cos_assignment_group
(
  id               bigint       not null primary key auto_increment,
  course_id        bigint       not null comment '课程ID',
  name             varchar(200) not null comment '小组名称',
  weight           decimal(10, 2) comment '权重',
  seq              int          not null default 0 comment '排序',

  `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '作业小组';

drop table if exists cos_assignment_group_item;
create table cos_assignment_group_item
(
  id                  bigint    not null primary key auto_increment,
  assignment_group_id bigint    not null comment '组ID',
  title               varchar(512) comment '目标任务类型标题(更新由触发器维护)',
  score               int comment '目标任务类型分值(更新由触发器维护)',
  status              int comment '目标任务类型发布状态(更新由触发器维护)',
  origin_type         int comment '任务类型： 1: 作业 2: 讨论 3: 测验',
  origin_id           bigint    not null comment '来源ID',
  seq                 int       not null default 0 comment '排序',

  `create_time`       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`    bigint(20) NOT NULL,
  `update_user_id`    bigint(20) NOT NULL,

  UNIQUE KEY `origin_id_and_type` (`origin_id`,`origin_type`)
) comment '作业小组内任务项';


-- ------------------------ 单元 ----------------------------
drop table if exists cos_module;
create table cos_module
(
  id                bigint    not null primary key auto_increment,
  course_id         bigint    not null comment '课程ID',
  name              varchar(200) comment '名称',
  start_time        datetime comment '开始时间',
  complete_strategy int comment '单元要求完成策略，1：完成所有要求， 2： 按顺序完成所有要求， 3：完成任意一个',

  status            int       not null default 0 comment '发布状态',
  seq               int       not null default 0 comment '排序',

  `create_time`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`  bigint(20) NOT NULL,
  `update_user_id`  bigint(20) NOT NULL
) comment '课程单元';

drop table if exists cos_module_requirement;
create table cos_module_requirement
(
  id               bigint    not null primary key auto_increment,
  module_id        bigint    not null comment '单元ID',
  module_item_id   bigint    not null comment '单元项ID',
  strategy         int       not null comment '1: view, 2: mark done, 3: submit, 4: score least; 5: contribute(参与)',
  least_score      int comment '至少得分',
  seq              int       not null default 0 comment '顺序',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '课程单元要求';

drop table if exists cos_module_prerequisite;
create table cos_module_prerequisite
(
  id               bigint    not null primary key auto_increment,
  module_id        bigint    not null comment '单元ID',
  pre_module_id    bigint    not null comment '前置单元ID',
  seq              int       not null default 0 comment '排序',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '单元相关前置单元';


drop table if exists cos_module_item;
create table cos_module_item
(
  id               bigint    not null primary key auto_increment,
  module_id        bigint    not null comment '单元ID',
  title            varchar(512) comment '目标任务类型标题(更新由触发器维护)',
  score            int comment '目标任务类型分值(更新由触发器维护)',
  status           int comment '目标任务类型发布状态(更新由触发器维护)',
  origin_type      int       not null comment '类型, 1: 作业, 2：讨论 3: 测验，4: 文件 5: 页面 13: 文本标题 14: 外部链接',
  origin_id        bigint    not null comment '来源ID',
  indent_level     int       not null default 0 comment '缩进级别',
  seq              int       not null default 0 comment '排序',
  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '单元项';

drop table if exists cos_module_user;
CREATE TABLE `cos_module_user`
(
  `id`             bigint(20) NOT NULL primary key AUTO_INCREMENT,
  `module_id`      bigint(20) NOT NULL COMMENT '单元ID',
  `course_id`      bigint(20) NOT NULL COMMENT '课程ID',
  `user_id`        bigint(20) DEFAULT NULL COMMENT '被分配到的用户ID',
  `status`         int(11) NOT NULL DEFAULT '0' COMMENT '完成状态，0：未完成；1：完成；2：过期（由前端生成数据时处理，后端不做处理，依据状态和过期时间判断）；',
  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) COMMENT='课程用户单元，教师创建后用于分配给具体的用户';

drop table if exists cos_module_item_user;
CREATE TABLE `cos_module_item_user`
(
  `id`             bigint(20) NOT NULL  primary key AUTO_INCREMENT,
  `module_id`      bigint(20) NOT NULL COMMENT '单元ID',
  `module_item_id` bigint(20) NOT NULL COMMENT '单元项ID',
  `user_id`        bigint(20) DEFAULT NULL COMMENT '被分配到的用户ID',
  `status`         int(11) NOT NULL DEFAULT '0' COMMENT '完成状态，0：未完成；1：完成；2：过期（由前端生成数据时处理，后端不做处理，依据状态和过期时间判断）；',
  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
)  COMMENT='用户单元项，依据单元项具体分配情况分配给具体的用户';


drop table if exists cos_grade_scheme;
create table cos_grade_scheme
(
  id               bigint       not null primary key auto_increment,
  course_id        bigint       not null comment '课程ID',
  name             varchar(128) not null comment '名称',
  is_default       int          not null default 0 comment '是否默认评分方案',

  `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '评分方案';

drop table if exists cos_grade_scheme_item;
create table cos_grade_scheme_item
(
  id              bigint      not null primary key auto_increment,
  grade_scheme_id bigint      not null comment '方案ID',

  code            varchar(10) not null comment 'Code',
  range_start     decimal(5, 2) comment '百分比下限，最小值0',
  range_end       decimal(5, 2) comment '百分比上限，最大值100'

) comment '评分方案项';


-- ------------------------- group set -----------------------
drop table if exists cos_study_group_set;
create table cos_study_group_set
(
  id                  bigint       not null primary key auto_increment,
  sis_id              varchar(128),
  course_id           bigint       not null comment '课程ID',
  name                varchar(256) not null comment '名称',
  allow_self_signup   int          not null default 0 comment '是否允许学生自行加入',
  is_section_group    int          not null default 0 comment '是否是班级小组（小组学生需要在相同班级）',
  leader_set_strategy int          not null default 1 comment '组长设置策略，1: 手动指定； 2: 第一个加入学生； 3: 随机设置',
  group_member_number int comment '组成员限制',
  is_student_group    int          not null default 0 comment '是否学生组，课程允许学生创建分组时，学生创建分组所属组集',

  `create_time`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`    bigint(20) NOT NULL,
  `update_user_id`    bigint(20) NOT NULL
) comment '学习小组集';

drop table if exists cos_study_group;
create table cos_study_group
(
  id                 bigint       not null primary key auto_increment,
  sis_id             varchar(128),
  course_id          bigint       not null comment '课程ID',
  study_group_set_id bigint       not null comment '所属学习小组集ID',
  is_student_group   int          not null default 0 comment '是否学生组，课程允许学生创建分组时，学生创建分组所属组集',

  name               varchar(300) not null comment '小组名称',
  max_member_number  int comment '成员数量限制',
  leader_id          bigint comment '组长ID',
  join_type          int          not null default 1 comment '学生加入小组类型, 1: 无限制 2: 仅限邀请',

  `create_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`   bigint(20) NOT NULL,
  `update_user_id`   bigint(20) NOT NULL
) comment '学习小组';


drop table if exists cos_study_group_user;
create table cos_study_group_user
(
  id                 bigint    not null primary key auto_increment,
  course_id          bigint    not null comment '课程ID',
  study_group_set_id bigint    not null comment '组集ID',
  study_group_id     bigint    not null comment '组ID',
  user_id            bigint    not null comment '用户ID',
  is_leader          int       not null default 0 comment '是否为组长',
  cover_color        varchar(10) comment '前景色，格式: #666666(日历使用)',

  `create_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`   bigint(20) NOT NULL,
  `update_user_id`   bigint(20) NOT NULL
) comment '小组成员';



drop table if exists cos_announce;
create table cos_announce
(
  id                          bigint        not null primary key auto_increment,
  course_id                   bigint        not null comment '课程ID',
  study_group_id              bigint comment '小组公告，小组ID',
  topic                       varchar(1024) not null comment '主题',
  content                     text comment '内容',
  publish_time                datetime comment '指定日期才发布',
  allow_comment               int           not null default 0 comment '允许评论',
  is_comment_before_see_reply int           not null default 0 comment '限制回复后才能查看评论',
  like_strategy               int           not null default 0 comment '点赞策略，0：不允许 1：允许 2：只有评分者才能点赞',
  is_order_by_like            int           not null default 0 comment '按赞数排序',


  -- remove
  --     like_count int not null default 0 comment '点赞数量',
  view_count                  int           not null default 0 comment '浏览数量',
  attachment_file_id          bigint comment '附件关联文件ID',

  -- add
  is_deleted                  int           not null default 0 comment '删除状态',


  `create_time`               timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`               timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`            bigint(20) NOT NULL,
  `update_user_id`            bigint(20) NOT NULL
) comment '公告';


drop table if exists cos_announce_reply;
create table cos_announce_reply
(
  id                 bigint    not null primary key auto_increment,
  announce_id        bigint    not null comment '公告ID',
  study_group_id     bigint comment '学习小组ID',
  content            text comment '内容',
  view_count         int       not null default 0 comment '浏览数量',
  is_deleted         int       not null default 0 comment '删除状态',
  announce_reply_id  bigint comment '回复的ID',
  attachment_file_id bigint comment '附件关联文件ID',

  `create_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`   bigint(20) NOT NULL,
  `update_user_id`   bigint(20) NOT NULL
) comment '公告评论';


-- ------------------------ 测验 ----------------------------
drop table if exists cos_quiz;
create table cos_quiz
(
  id                       bigint       not null primary key auto_increment,
  course_id                bigint       not null comment '课程ID',
  title                    varchar(500) not null comment '标题',
  description              text comment '描述',
  type                     int comment '测验类型, 1：练习测验(practice quiz)、2：评分测验(graded quiz)、3：评分调查(graded survey)、4：非评分调查(ungraded survey)',
  assignment_group_id      bigint comment '作业小组ID（type=2,3）',
  score                    int comment '计分值(type=3)',
  allow_anonymous          int comment '允许匿名提交(type=3,4)',

  is_shuffle_answer        int          not null default 0 comment '是否重组答案',
  time_limit               int comment '时间限制，单位分钟',
  allow_multi_attempt      int comment '允许多次尝试',
  attempt_number           int comment '尝试次数',
  score_type               int comment '多次尝试时的计分规则，1：最高得分，2：最近一次得分，3：平均分',

  show_reply_strategy      int          not null default 0 comment '显示学生的回答是否正确策略，0: 不显示 1：每次提交答案后 2：最后一次提交后',

  show_answer_strategy     int          not null default 0 comment '显示正确答案策略，0: 不显示 1：每次提交后 2：最后一次提交后',
  show_answer_start_time   datetime comment '显示正确答案开始时间',
  show_answer_end_time     datetime comment '显示正确答案结束时间',

  show_question_strategy   int          not null default 0 comment '显示问题策略, 0: 全部显示, 1: 每页一个',
  is_lock_replied_question int          not null default 0 comment '回答后锁定问题',

  is_need_access_code      int          not null default 0 comment '是否需要访问码访问',
  access_code              varchar(64) comment '访问码',
  is_filter_ip             int          not null default 0 comment '是否过滤访问IP',
  filter_ip_address        varchar(128) comment '过滤IP地址',
  version                  int          not null default 1 comment '版本号（预留）,每次更新后版本号增加',

  status                   int          not null default 0 comment '发布状态',
  total_questions          int          NOT NULL DEFAULT '0' COMMENT '问题总数',
  total_score              int          NOT NULL DEFAULT '0' COMMENT '总分',
  `create_time`            timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`            timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`         bigint(20) NOT NULL,
  `update_user_id`         bigint(20) NOT NULL
) comment '测验';


drop table if exists cos_quiz_item;
create table cos_quiz_item
(
  id               bigint    not null primary key auto_increment,
  quiz_id          bigint    not null comment '测验ID',

  type             int       not null comment '类型，1. 问题 2. 问题组',
  target_id        bigint    not null comment '问题/问题组ID',
  seq              int       not null default 0 comment '排序',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '测验问题项';


-- ------------------------ 问题类 ----------------------------
-- 题库：题库跟随课程，题库中包含了多个问题模板
drop table if exists cos_question_bank;
create table cos_question_bank
(
  id               bigint       not null primary key auto_increment,
  course_id        bigint       not null comment '课程ID',
  name             varchar(256) not null comment '题库名称',

  `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '题库';

-- drop table if exists cos_question_template;
-- create table cos_question_template (
--     id bigint not null primary key auto_increment,
--     question_bank_id bigint not null comment '题库ID',
--     course_id bigint not null comment '课程ID',
--     type int comment '问题类型，1：单选， 2:多选， 3：判断题、4、多项下拉题、5、匹配题、6、简答题、7:文件上传题、8:文本题',
--     title text comment '标题',
--     correct_comment text comment '正确提示',
--     wrong_comment text comment '错误提示',
--     general_comment text comment '通用提示',
--     explanation text comment '解析',
--
--     score int not null default 0 comment '分值',
--     seq int not null default 0 comment '排序',
--
--     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--     `create_user_id` bigint(20) NOT NULL,
--     `update_user_id` bigint(20) NOT NULL
-- ) comment '问题模板';

--
-- drop table if exists cos_question_template_option;
-- create table cos_question_template_option (
--     id bigint not null primary key auto_increment,
--     question_template_id bigint not null comment '问题ID',
--     code varchar(256) comment '题干中占位符',
--     content text comment '内容',
--     explanation text comment '选择该项的提示',
--     is_correct int not null default 0 comment '选择题：是否为正确选项',
--     seq int comment '排序',
--
--     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--     `create_user_id` bigint(20) NOT NULL,
--     `update_user_id` bigint(20) NOT NULL
-- ) comment '问题选项';
--
-- drop table if exists cos_question_template_match_option;
-- create table cos_question_template_match_option (
--     id bigint not null primary key auto_increment,
--     question_template_id bigint not null comment '问题ID',
--     question_template_option_id bigint comment '匹配选项，空表示是一个错误匹配项',
--     content text comment '内容',
--     seq int comment '排序',
--
--     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--     `create_user_id` bigint(20) NOT NULL,
--     `update_user_id` bigint(20) NOT NULL
-- ) comment '匹配类问题选项： 匹配题时右边选项列表';


drop table if exists cos_question;
create table cos_question
(
  id                   bigint    not null primary key auto_increment,
  course_id            bigint    not null comment '课程ID',
  group_id             bigint comment '问题组ID',
  type                 int comment '问题类型，1：单选， 2:多选， 3：判断题、4、多项下拉题、5、匹配题、6、简答题、7:文件上传题、8:文本题',
  title                varchar(512) comment '标题',
  content              text comment '内容',
  correct_comment      text comment '正确提示',
  wrong_comment        text comment '错误提示',
  general_comment      text comment '通用提示',
  is_template          int       not null default 0 comment '是否为问题模板',
  question_bank_id     bigint comment '为问题模板时表示题库ID',
  question_template_id bigint comment '问题来源模板',

  score                int       not null default 0 comment '分值',
  seq                  int       not null default 0 comment '排序',

  `create_time`        timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`        timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`     bigint(20) NOT NULL,
  `update_user_id`     bigint(20) NOT NULL
) comment '测验问题表';

drop table if exists cos_question_option;
create table cos_question_option
(
  id               bigint    not null primary key auto_increment,
  question_id      bigint    not null comment '问题ID',
  code             varchar(256) comment '题干中占位符',
  content          text comment '内容',
  explanation      text comment '选择该项的提示',
  is_correct       int       not null default 0 comment '选择题：是否为正确选项',
  seq              int       not null default 0 comment '排序',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '问题选项';

drop table if exists cos_question_match_option;
create table cos_question_match_option
(
  id                 bigint    not null primary key auto_increment,
  question_id        bigint    not null comment '问题ID',
  question_option_id bigint comment '匹配选项，空表示是一个错误匹配项',
  content            text comment '内容',
  seq                int       not null default 0 comment '排序',

  `create_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`   bigint(20) NOT NULL,
  `update_user_id`   bigint(20) NOT NULL
) comment '匹配类问题选项： 匹配题时右边选项列表';



drop table if exists cos_question_group;
create table cos_question_group
(
  id                   bigint       not null primary key auto_increment,
  quiz_id              bigint       not null,
  name                 varchar(256) not null comment '组名',
  each_question_score  int comment '组内每个问题得分',
  pick_question_number int comment '挑选问题个数',

  `create_time`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`     bigint(20) NOT NULL,
  `update_user_id`     bigint(20) NOT NULL
) comment '试题组：测验时从组中抽取n个问题，每个问题得分相同';


-- ------------------------ 测验答题记录，详细记录下每次答题时的问题及选项位置，测试修改后也能重新查看

drop table if exists cos_quiz_record;
create table cos_quiz_record
(
  id               bigint    not null primary key auto_increment,
  quiz_id          bigint    not null comment '测验ID',
  quiz_version     int comment '提交时测验版本',
  is_submit        int                default 0 comment '是否确认提交',
  is_last_time     int                default 0 comment '是否最后一次提交（最后一次提交后不再允许进行提交）',
  start_time       datetime  not null comment '开始时间',
  submit_time      datetime comment '提交时间',
  due_time         datetime comment '提交截至时间，开始测验时根据测验的时间限制计算',
  tester_id        bigint    not null comment '测验人ID',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '测验记录';

drop table if exists cos_quiz_question_record;
create table cos_quiz_question_record
(
  id               bigint    not null primary key auto_increment,
  quiz_record_id   bigint    not null comment '测验记录ID',
  question_id      bigint    not null comment '问题ID',
  group_id         bigint comment '问题组ID',

  type             int comment '问题类型，1：单选， 2:多选， 3：判断题、4、多项下拉题、5、匹配题、6、简答题、7:文件上传题、8:文本题',
  title            text comment '标题',
  `content`        text COMMENT '内容',
  correct_comment  text comment '正确提示',
  wrong_comment    text comment '错误提示',
  general_comment  text comment '通用提示',
  score            int       not null default 0 comment '分值',
  seq              int       not null default 0 comment '排序',
  grade_score      int comment '得分',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '测验问题记录';

drop table if exists cos_quiz_question_option_record;
create table cos_quiz_question_option_record
(
  id                      bigint    not null primary key auto_increment,
  quiz_question_record_id bigint    not null comment '问题记录ID',
  question_option_id      bigint    not null comment '问题选项ID',
  code                    varchar(256) comment '题干中占位符',
  content                 text comment '内容',
  explanation             text comment '选择该项的提示',
  is_correct              int       not null default 0 comment '选择题：是否为正确选项',
  seq                     int       not null default 0 comment '排序：如果是重排序选项，则是重排序后的选项顺序',

  is_choice               int comment '是否选中',

  `create_time`           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`        bigint(20) NOT NULL,
  `update_user_id`        bigint(20) NOT NULL
) comment '测验问题选项记录';

drop table if exists cos_quiz_question_reply_record;
create table cos_quiz_question_reply_record
(
  id                      bigint    not null primary key auto_increment,
  quiz_question_record_id bigint    not null comment '问题记录ID',
  reply                   text comment '回答内容',

  `create_time`           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`        bigint(20) NOT NULL,
  `update_user_id`        bigint(20) NOT NULL
) comment '填空类问题回答';

drop table if exists cos_quiz_question_match_option_record;
create table cos_quiz_question_match_option_record
(
  id                             bigint    not null primary key auto_increment,
  question_match_option_id       bigint    not null comment '匹配类问题选项',
  content                        text comment '内容',
  quiz_question_record_id        bigint    not null comment '问题记录ID',
  quiz_question_option_record_id bigint    not null comment '选项ID',

  `create_time`                  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`                  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`               bigint(20) NOT NULL,
  `update_user_id`               bigint(20) NOT NULL
) comment '匹配类问题选项选择记录';


-- ----------------------------- 单元进度
drop table if exists cos_module_progression_item;
create table cos_module_progression_item
(
  id               bigint    not null primary key auto_increment,
  user_id          bigint    not null comment '用户ID',
  course_id        bigint    not null comment '课程',
  module_id        bigint    not null comment '课程单元',
  module_item_id   bigint    not null comment '单元项ID',
  is_finished      int comment '是否完成',

  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '单元项处理进度';


-- ---------------------------- 作业评分


drop table if exists cos_assignment_reply;
create table cos_assignment_reply
(
  id               bigint    not null primary key auto_increment,
  course_id        bigint    not null,
  assignment_id    bigint    not null comment '作业ID',
  user_id          bigint    not null comment '提交用户',
  study_group_id   bigint comment '小组作业时，组ID',
  submit_time      datetime comment '提交时间',
  --   submission_type int comment '提交类型, 1. 在线、2. 书面、3. 外部工具、 4. 不提交',
  reply_type       int comment '1: 文本　2: 文件 3: URL 4: 媒体',
  content          text comment '回答内容',

  is_deleted       int       not null default 0 comment '是否删除',
  `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '作业回答';

drop table if exists cos_assignment_reply_attachment;
create table cos_assignment_reply_attachment
(
  id                  bigint    not null primary key auto_increment,
  assignment_id       bigint    not null comment '作业ID',
  assignment_reply_id bigint    not null comment '作业回答ID',
  file_id             bigint    not null comment '提交文件ID',

  `create_time`       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`    bigint(20) NOT NULL,
  `update_user_id`    bigint(20) NOT NULL
) comment '作业回答附件';


-- 评分
drop table if exists cos_grade;
create table cos_grade
(
  id                       bigint    not null primary key auto_increment,
  course_id                bigint    not null,
  assignment_group_item_id bigint    not null comment '作业组item',
  origin_type              int comment '任务类型： 1: 作业 2: 讨论 3: 测验',
  origin_id                bigint    not null comment '来源ID',
  score_type               int comment '多次尝试时的计分规则，1：最高得分，2：最近一次得分，3：平均分',
  score                    int comment '总分',
  grade_score              int comment '最终得分',
  current_score            int comment '最后一次得分',
  is_graded                int comment '是否已全部评分，0：否；1：是',
  time_limit               int comment '最后一次完成该任务所用时间',
  record_id                bigint comment '记录最终分数的记录id，只有score_type=1、2时需要',
  attemps                  int comment '已尝试次数',
  attemps_available        int comment '剩余尝试次数',
  need_grade               int(11) NOT NULL DEFAULT '0' COMMENT '是否为评分任务',
  is_overdue               int(11) NOT NULL DEFAULT '0' COMMENT '是否逾期提交,是否逾期只以第一次提交时时间判断',
  last_submit_time         datetime  NOT NULL COMMENT '最近提交时间',
  submit_count             int(11) NOT NULL DEFAULT '0' COMMENT '提交次数',
  user_id                  bigint    not null comment '提交用户',
  grader_id                bigint    not null comment '评分用户',
  `create_time`            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`         bigint(20) NOT NULL,
  `update_user_id`         bigint(20) NOT NULL
) comment '评分表';

-- 评分历史记录表
drop table if exists cos_history_grade;
create table cos_history_grade
(
  id                       bigint    not null primary key auto_increment,
  course_id                bigint    not null,
  assignment_group_item_id bigint    not null comment '作业组item',
  origin_type              int comment '任务类型： 1: 作业 2: 讨论 3: 测验',
  origin_id                bigint    not null comment '来源ID',

  score                    int comment '总分',
  grade_score              int comment '得分',
  user_id                  bigint    not null comment '提交用户',
  grader_id                bigint    not null comment '评分用户',
  `create_time`            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`         bigint(20) NOT NULL,
  `update_user_id`         bigint(20) NOT NULL
) comment '评分评分历史记录表';



drop table if exists cos_grade_comment;
create table cos_grade_comment
(
  id                       bigint    not null primary key auto_increment,
  course_id                bigint    not null,
  assignment_group_item_id bigint    not null comment '作业组item',
  origin_type              int comment '任务类型： 1: 作业 2: 讨论 3: 测验',
  origin_id                bigint    not null comment '来源ID',
  content                  text comment '评论内容',
  user_id                  bigint    not null comment '评论用户',
  study_group_id           bigint comment '所属学习小组',
  attachment_file_id       bigint comment '附件ID',
  is_send_group_user       int       not null default 0 comment '小组作业时，评论是否组成员可见(预留)',

  is_deleted               int       not null default 0 comment '是否删除',
  `create_time`            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`         bigint(20) NOT NULL,
  `update_user_id`         bigint(20) NOT NULL
) comment '作业项评论';



drop table if exists cos_user_submit_record;
create table cos_user_submit_record
(
  id                       bigint    not null primary key auto_increment,
  course_id                bigint    not null comment '课程',
  user_id                  bigint    not null comment '提交用户',
  study_group_id           bigint comment '学习小组',
  assignment_group_item_id bigint comment '关联作业组项',
  origin_type              int comment '任务类型： 1: 作业 2: 讨论 3: 测验',
  origin_id                bigint    not null comment '来源ID',
  last_submit_time         datetime  not null comment '最近提交',
  submit_count             int       not null default 1 comment '提交次数',
  is_graded                int       not null default 0 comment '是否已评分',
  `grade_score`            int(11) DEFAULT NULL COMMENT '得分',
  need_grade               int       not null default 0 comment '是否为评分任务',

  is_overdue               int       not null default 0 comment '是否逾期提交,是否逾期只以第一次提交时时间判断',

  `create_time`            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id`         bigint(20) NOT NULL,
  `update_user_id`         bigint(20) NOT NULL
) comment '用户作业组项提交记录（相同内容多次提交只更新submit_count）';



drop table if exists cos_user_participate;
create table cos_user_participate
(
  id            bigint    not null primary key auto_increment,
  course_id     bigint comment '参与对象关联课程',
  user_id       bigint    not null comment '活动用户',
  origin_type   int       not null comment '活动对象类别: 活动对象， 1. 作业 2. 讨论 3. 测验 6. 公告  10. 协作文档 11. 网络会议',
  origin_id     bigint    not null comment '活动对象ID',
  operation     int       not null comment '活动类型： 1. 查看（协作文档） 2. 创建（Wiki page） 3. 编辑（协作文档） 4. 提交（作业答案、测验答案、讨论回复、公告回复） 5. 参与（网络会议）',
  target_name   varchar(256) comment '活动对象名称',

  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) comment '学生用户活动: 参与网络会议，查看、编辑协作文档，公告、讨论评论，测验提交，作业提交，创建Page';


-- 程序初始化
drop table if exists cos_course_registry_code_bank;
create table cos_course_registry_code_bank
(
  id      bigint     not null primary key auto_increment,
  code    varchar(4) not null unique comment '4位注册码(大写字母、数字组成，去除字母I、O，数字0,1)',
  is_used int        not null default 0 comment '是否已使用'
) engine MyISAM comment '课程注册码库，程序CourseRegistryCodeGenerator.java预先生成';
-- alter table cos_course_registry_code_bank engine = MyISAM;

-- 日历项目用户选中标记
drop table if exists cos_calendar_user_check;
CREATE TABLE `cos_calendar_user_check`
(
  `id`            bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id`       bigint(20) NOT NULL COMMENT '用户id',
  `calendar_type` int(11) NOT NULL COMMENT '日历类型, 1: 个人 2: 课程 3: 学习小组',
  `check_id`      bigint(20) NOT NULL COMMENT '选中ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=348 DEFAULT CHARSET=utf8 COMMENT='日历项目用户选中标记';



drop table if exists cos_assignment_group_item_change_record;

create table cos_assignment_group_item_change_record
(
  id                        bigint not null primary key auto_increment,
  course_id                 bigint not null,
  assignment_grouop_item_id bigint comment '任务项ID',
  title                     varchar(500) comment '任务项标题',
  content                   text comment '任务项内容',

  origin_id                 bigint,
  origin_type               int,
  op_type                   int comment '操作类型， 1： 创建 2: 更新内容 3: 更新StartTime, 4: 更新EndTime 5: 更新DueTime',

  create_user_id            bigint,
  create_time               timestamp default current_timestamp
) comment '评分项变更记录';

alter table cos_assignment
  add show_score_type int comment '1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分';
-- UPDATE cos_assignment SET show_score_type = 2 where show_score_type is NULL;

alter table cos_course
  add is_weight_grade int comment '0：评分时不涉及权重；1：评分时涉及权重';

drop table if exists gensee_live;
CREATE TABLE gensee_live
(
  id                               BIGINT       NOT NULL COMMENT 'ID' auto_increment,
  course_id                        BIGINT       NOT NULL COMMENT '课程ID',
  title                            VARCHAR(1024) COMMENT '标题',
  description                      TEXT COMMENT '描述',
  instructor                       BIGINT       NOT NULL COMMENT '主讲人',
  start_time                       timestamp    NOT NULL COMMENT '开始时间',
  end_time                         timestamp NULL DEFAULT NULL COMMENT '结束时间',
  gensee_live_id                   VARCHAR(128) NOT NULL COMMENT '直播ID',
  gensee_organizer_join_url        VARCHAR(1024) COMMENT '组织者加入URL',
  gensee_panelist_join_url         VARCHAR(1024) COMMENT '嘉宾加入URL',
  gensee_attendee_join_url         VARCHAR(1024) COMMENT '普通参加者加入URL(带token)',
  gensee_number                    VARCHAR(1024) COMMENT '直播编号',
  gensee_attendee_a_short_join_url VARCHAR(1024) COMMENT '普通参加者加入URL(不带token)',
  gensee_organizer_token           VARCHAR(1024) COMMENT '组织者口令',
  gensee_panelist_token            VARCHAR(1024) COMMENT '嘉宾口令',
  gensee_attendee_token            VARCHAR(1024) COMMENT '普通参加者口令',
  is_deleted                       INT          NOT NULL DEFAULT 0 COMMENT '是否删除 0：否；1：是',
  create_time                      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time                      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id                   bigint(20) NOT NULL,
  update_user_id                   bigint(20) NOT NULL,
  PRIMARY KEY (id)
) COMMENT = '直播表';

drop table if exists gensee_user_vod_history;
CREATE TABLE gensee_user_vod_history
(
  id                BIGINT        NOT NULL COMMENT 'ID' auto_increment,
  gensee_vodId      VARCHAR(1024) NOT NULL COMMENT '点播ID',
  gensee_uid        VARCHAR(1024) NOT NULL COMMENT '用户ID',
  gensee_start_time timestamp NULL DEFAULT NULL COMMENT '加入时间',
  gensee_leave_time timestamp NULL DEFAULT NULL COMMENT '离开时间',
  gensee_name       VARCHAR(128) COMMENT '姓名',
  gensee_duration   INT COMMENT '观看时长 单位为毫秒',
  gensee_ip         VARCHAR(128) COMMENT 'IP地址',
  gensee_area       VARCHAR(128) COMMENT '区域',
  gensee_device     INT COMMENT '终端类型 值说明：  0  PC  1  Mac  2  Linux  4  Ipad  8  Iphone  16  Andriod Pad  32  Andriod Phone  132  IPad(PlayerSDK)  136  IPhone(PlayerSDK)  144  Andriod Pad(PlayerSDK)  256  Andriod Phone(PlayerSDK)  0xED  Mobile  237 移动设备（以前版本的移动端的playersdk和app）  21 小程序IOS端  22 小程序安卓端  23 小程序sdk IOS端  24小程序sdk 安卓端  其他值为 Unknown',
  create_time       timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time       timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id    bigint(20) NOT NULL,
  update_user_id    bigint(20) NOT NULL,
  PRIMARY KEY (id)
) COMMENT = '点播用户访问记录表 ';

drop table if exists gensee_user_live_history;
CREATE TABLE gensee_user_live_history
(
  id                BIGINT    NOT NULL COMMENT 'ID' auto_increment,
  gensee_webcast_id VARCHAR(1024) COMMENT '直播ID',
  gensee_nickname   VARCHAR(128) COMMENT '昵称',
  gensee_join_time  timestamp NULL DEFAULT NULL COMMENT '加入时间',
  gensee_leave_time timestamp NULL DEFAULT NULL COMMENT '离开时间',
  gensee_ip         VARCHAR(128) COMMENT 'IP地址',
  gensee_uid        BIGINT COMMENT '用户ID',
  gensee_area       VARCHAR(128) COMMENT '区域',
  gensee_mobile     VARCHAR(128) COMMENT '手机',
  gensee_company    VARCHAR(128) COMMENT '公司',
  gensee_join_type  BIGINT COMMENT '加入终端类型 值说明：  0  PC客户端  1  PC Web端  2  PC Web端(http流)  3  IPAD Web端  4  IPHONE Web端  5  APAD Web端  6  APHONE Web端  7  IPAD APP端  8  IPHONE APP端  9  APAD APP端  10 APHONE APP端  11 MAC 客户端  12  电话端  16  PLAYER SDK  IOS端  17  PLAYER SDK  安卓端  21 小程序IOS端  22 小程序安卓端  23 小程序sdk IOS端  24小程序sdk 安卓端',
  create_time       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id    bigint(20) NOT NULL,
  update_user_id    bigint(20) NOT NULL,
  PRIMARY KEY (id)
) COMMENT = '直播用户访问记录表 ';

drop table if exists gensee_vod;
CREATE TABLE gensee_vod
(
  id                       BIGINT        NOT NULL COMMENT 'ID' auto_increment,
  gensee_id                VARCHAR(1024) NOT NULL COMMENT '点播ID',
  gensee_subject           VARCHAR(1024) COMMENT '主题',
  gensee_password          VARCHAR(128) COMMENT '观看口令 允许为空',
  gensee_description       VARCHAR(3072) COMMENT '描述',
  gensee_created_time      timestamp NULL DEFAULT NULL COMMENT '创建时间',
  gensee_attendee_join_url VARCHAR(1024) COMMENT '加入URL',
  gensee_webcast_id        VARCHAR(1024) NOT NULL COMMENT '直播ID 指明该点播是由哪个直播录制生成的。',
  gensee_screenshot        VARCHAR(1024) COMMENT '',
  gensee_creator           VARCHAR(1024) COMMENT '创建该点播的用户ID (点播的所有者)',
  gensee_number            VARCHAR(128) COMMENT '点播的编号',
  gensee_record_id         VARCHAR(128) COMMENT '该点播使用的录制件ID',
  gensee_record_start_time timestamp NULL DEFAULT NULL COMMENT '录制开始时间',
  gensee_record_end_time   timestamp NULL DEFAULT NULL COMMENT '录制结束时间',
  gensee_gr_type           INT COMMENT '0、3、4录制件1多媒体2文档',
  gensee_duration          BIGINT COMMENT '内容时长 单位是毫秒。',
  gensee_convert_result    INT COMMENT '0转换中 1转换成功 -1 失败',
  gensee_speaker_info      VARCHAR(1024) COMMENT '演讲人信息',
  create_time              timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time              timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id           bigint(20) NOT NULL,
  update_user_id           bigint(20) NOT NULL,
  PRIMARY KEY (id)
) COMMENT = '点播表 ';
drop table if exists cos_module_external_url;
create table cos_module_external_url
(
  id               bigint       not null primary key auto_increment,
  url              varchar(300) not null comment 'web链接',
  page_name        varchar(300) not null comment '页面名称',
  status           int          not null default 0 comment '发布状态',
  `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '单元模块-外部链接表';

drop table if exists cos_module_text_header;
create table cos_module_text_header
(
  id               bigint       not null primary key auto_increment,
  text             varchar(300) not null comment '标题名称',
  status           int          not null default 0 comment '发布状态',
  `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL,
  `update_user_id` bigint(20) NOT NULL
) comment '单元模块-文本标题表';

drop table if exists cos_syllabus;
CREATE TABLE `cos_syllabus` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `course_id` bigint(20) DEFAULT NULL COMMENT '课程ID',
                              `comments` text COMMENT '评论内容',
                              `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              `create_user_id` bigint(20) NOT NULL,
                              `update_user_id` bigint(20) NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
