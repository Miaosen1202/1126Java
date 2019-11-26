

#用户接收邀请处理
---------------
## 流程
	1. 查看邮件,点击加入链接,链接中包含课程id与邀请码
        /course/invite?courseId=1&code=xxx
	2. 用户进入邀请页面,
	    如果当前没有登录
	        用户不是临时用户:重定向到登录页面,登录后首页出现邀请提示
	        用户是临时用户,用户选择新建用户或关联到已有用户
	            新建用户:
	               跳转到新用户完善密码,语言界面,用户设置好密码与语言后登录用户,首页出现邀请提示
	            关联到已有用户:
	                跳转到登录界面,用户登录后,关联邀请邮箱到登录用户,并添加用户到课程,删除添加时创建的临时用户
	                
	    如果当前已登录:
	        邀请用户不为临时用户,跳转到登录页面,用户登录后首页出现邀请提示
	        邀请用户为临时用户,提示用户是否关联当前邮箱并加入课程
	            否:跳转到新用户完善密码,语言界面,用户设置好密码与语言后登录用户,首页出现邀请提示
	            是:关联邀请邮箱到登录用户,并添加用户到课程,删除添加时创建的临时用户
       
## api
    1. /courseUser/invite/query 用户邀请接口
       req:
            code
       resp:
            userId
            registryStatus pending/joined
            isRegistering 是否临时用户
            
       -- done
       
    2. /courseUser/improve 完善临时用户信息接口
        req:
            userId
            code
            password
            language
        resp:
            userId
        -- 信息处理
        
    3. /courseUser/inviteHandle/edit
        req:
            code
            isAccept
        resp:
            userId
    
    4. /courseUser/associateAndJoin/edit
        req:
            code
        resp:
            userId
        
        -- done
        
    5. /courseUser/registering/query 查询待处理课程邀请
        resp:
            [{
                courseId,
                code
                roleId
                sectionId
            }]       
        -- done