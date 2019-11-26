# 一些临时设计表


机构的设计。
机构ID作为通用字段存储到每个表，将来分库可以通过机构作为key。文件存储系统也需要通过机构来划分。

学习小组集合(group set): study_group_set
	name
	start_time
    end_time
    config_prior 		配置优先（起止时间覆盖学期及课程时间设置）
    allow_self_signup	允许自行注册，并且学生可以自己切换所属小组
    is_section_group	班级小组集（成员需要在相同班级）
    leader_config_type  小组组长设置类型： 1：手动指定， 2：第一个加入学生为组长， 3：随机学生设置为组长

学习小组(group): study_group
	id
	study_group_set_id
	name
    member_number 			小组成员数限制

学习小组用户: relation_study_group_user
	id
    study_group_set_id
    study_group_id
    user_id


page:
    id
    course_id
    title
    content
    editor_type
    url
    add_student_todo
    todo_time

试题组：question_group
    id
    quiz_id
    name
    each_question_score
    pick_question_number

小组下可以有： Page、Discussion、

-------------------------------------------------
## section(班级): 每个课程有一与课程同名的默认班级
    id
    course_id
    name
    

## event
    sys_event
        id
        title
        invalidate_set_time_for_section
        start_time
        end_time
        description
        position
        address
    sys_event_time
        id
        section_id
        start_time
        end_time

## todo_list
    id
    time
    calendar_id
    title
    content

-------------------------------------------------
## org
    id
    name
    description
    type            学校/教育机构
    language        语言(预留)
    time_zone       时区(预留)

## user
    id
    username        登录名
    password        密码
    nickname        用于讨论、消息、评论等
    fullname        用于评分展示
    type            用户类型: 1.管理员, 2: 教师, 3: 助教, 4: 学生
    status          状态
    
    email           邮箱
    phone           电话
    org_id          所属组织
    avatar_url      头像地址
    description     介绍
    language        语言(预留)
    time_zone       时区(预留)

## rel_user_role
    
## user_links
    -- 人物介绍链接
    id
    user_id
    title               标题
    url                 地址
    
## user_notify_config
    -- 接受通知配置(预留)
    id
    user_id
    notify_type
    notify_method
    
    

## student
    id
    name

## Calendar 日历


## announce 通知
    id
    course_id
    topic       主题
    description 
    -- post_to         班级(所有/当前课程班级)
    -- attachment
    start_time      发布时间
    allow_comment   允许评论   
    post_before_see_reply       看见回帖之前回复
    
### announce_attachment
### announce_post
    id
    announce_id
    section_id
    
### announce_comment
    -- 通知评论
    id
    announce_id
    content
    like_count      点赞数
    is_delete       删除状态
    
### redis - announce_unread_comment     未读通知回复
    announce_id         通知ID
    reply_id            回复ID
    


小组作业项：
    作业
    讨论
    测验

单元包含项：
    作业
    讨论
    测验
    文件
    页面
    文本标题
    外部 URL
    外部工具
    
   










----------------------------------------------------------------------------------
学生账户（通过学校使用LMS平台邀请、创建的账户）
    邮件邀请

个人学生账户（不是通过学校使用LMS平台）
    学生自行创建



------------------------------------------------------
作业、讨论分配为小组作业（小组集）时，必须是全部班级可用的


作业小组（Assignments）
   作业
   讨论
   测验
       
小组集
    作业
    讨论

班级
    作业（多班级）
    讨论（班级，小组集不共存）
    公告
    测验（多班级）

module
    作业
    讨论
    测验
    文件
    页面
    标题
    URL
    TOOL
    
个人

公告-Announcement
    post-to:   班级
    
    
    
    
对于需要特殊处理的字典属性：程序中添加枚举类，不放入字典表中








---------------------------------
测验 & 问题 & 题库

新建问题时会生成一个试题模板记录，试题模板保存到题库中，可以进行复用；
测验添加问题时可以从试题库中引用问题，并且在测验中对试题进行修改，不影响试题模板。


----------------------------------


-- 用户  角色  权限   菜单
    用户可以关联到不同班级
    用户可以关联到不同机构
    每个用户在不同班级内都可以有不同角色：在班级1是教师、班级2是学生
    

