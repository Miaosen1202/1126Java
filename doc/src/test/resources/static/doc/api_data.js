define({ "api": [
  {
    "type": "post",
    "url": "/announce/add",
    "title": "公告添加",
    "name": "announceAdd____",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "topic",
            "description": "<p>标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "assign",
            "description": "<p>分配</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": true,
            "field": "assign.assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.assignId",
            "description": "<p>分配目标ID，assignType=2时为班级ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "fileId",
            "description": "<p>文件id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "publishTime",
            "description": "<p>发布时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "allowComment",
            "description": "<p>是否允许评论</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceDataEdit.java",
    "groupTitle": "Announce"
  },
  {
    "type": "post",
    "url": "/announce/deletes",
    "title": "公告删除",
    "name": "announceDeletes",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>公告ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "[1,2,3]",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例:",
          "content": "{\n\"code\": 200,\n\"entity\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceDataEdit.java",
    "groupTitle": "Announce"
  },
  {
    "type": "get",
    "url": "/announce/get",
    "title": "公告详情",
    "name": "announceGet",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>公告Id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.topic",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.content",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.studyGroupSetId",
            "description": "<p>学习小组集ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.allowComment",
            "description": "<p>允许评论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.publishTime",
            "description": "<p>发布时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.attachmentFileId",
            "description": "<p>附件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity.attachmentFile",
            "description": "<p>讨论话题附件</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.attachmentFile.fileName",
            "description": "<p>附件名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.attachmentFile.fileUrl",
            "description": "<p>附件url</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.assign",
            "description": "<p>分配数组</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.assign.assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级) 3用户</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.ssign.assignId",
            "description": "<p>分配目标ID 班级Id或者用户Id，分配类型为所有时为空</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.author",
            "description": "<p>讨论话题作者</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.author.nickname",
            "description": "<p>讨论话题作者昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.author.avatarUrl",
            "description": "<p>讨论话题作者头像</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.readCountDTO",
            "description": "<p>评论总数未读数统计</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.readCountDTO.replyTotal",
            "description": "<p>评论总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.readCountDTO.replyReadTotal",
            "description": "<p>评论已读总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.readCountDTO.replyNotReadTotal",
            "description": "<p>评论未读总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.sectionList",
            "description": "<p>会话数组 [{班级名称：用户个数}]</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionList.sectionId",
            "description": "<p>会话班级ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.sectionList.sectionName",
            "description": "<p>会话班级名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionList.userCount",
            "description": "<p>会话班级用户个数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userCount",
            "description": "<p>用户总数，所有班级时才有此字段</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceDataQuery.java",
    "groupTitle": "Announce"
  },
  {
    "type": "get",
    "url": "/announce/list",
    "title": "公告列表",
    "name": "announceList",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": false,
            "field": "isRead",
            "description": "<p>是否已读 0:未读,1:已读，2:所有</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "topic",
            "description": "<p>公告标题</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>学习小组Id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.studyGroupId",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.topic",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.publishTime",
            "description": "<p>发布时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isRead",
            "description": "<p>是否已读</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.allowComment",
            "description": "<p>评论，0: 禁止评论, 1: 允许评论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.attachmentFileId",
            "description": "<p>附件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity.readCountDTO",
            "description": "<p>评论数统计节点</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.readCountDTO.replyTotal",
            "description": "<p>总评论数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.readCountDTO.replyNotReadTotal",
            "description": "<p>评论未读数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.createUserNickname",
            "description": "<p>创建者昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.createUserAvatar",
            "description": "<p>创建者头像</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceDataQuery.java",
    "groupTitle": "Announce"
  },
  {
    "type": "post",
    "url": "/announce/modify",
    "title": "公告修改",
    "name": "announceModify",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>公告ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "topic",
            "description": "<p>标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组公告，小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "assign",
            "description": "<p>分配</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": true,
            "field": "assign.assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.assignId",
            "description": "<p>分配目标ID，assignType=2时为班级ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "attachmentFileId",
            "description": "<p>附件id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "fileId",
            "description": "<p>文件ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "publishTime",
            "description": "<p>发布时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "allowComment",
            "description": "<p>是否允许评论</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>公告ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceDataEdit.java",
    "groupTitle": "Announce"
  },
  {
    "type": "get",
    "url": "/announce/pageList",
    "title": "公告列表分页",
    "name": "announcePageList",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": false,
            "field": "isRead",
            "description": "<p>是否已读 0:默认所有,1:已读，2:未读</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "topic",
            "description": "<p>公告标题</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>学习小组Id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageSize",
            "description": "<p>页大小</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.total",
            "description": "<p>总结果数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageSize",
            "description": "<p>页大小</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.list",
            "description": "<p>列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.studyGroupId",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.topic",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.list.content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.publishTime",
            "description": "<p>发布时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.list.isRead",
            "description": "<p>是否已读</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.list.allowComment",
            "description": "<p>评论，0: 禁止评论, 1: 允许评论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.attachmentFileId",
            "description": "<p>附件ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceDataQuery.java",
    "groupTitle": "Announce"
  },
  {
    "type": "post",
    "url": "/announceReply/add",
    "title": "公告回复添加",
    "name": "announceReplyAdd",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "announceId",
            "description": "<p>公告ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>学习小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "announceReplyId",
            "description": "<p>回复ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "fileId",
            "description": "<p>文件ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增回复ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceReplyDataEdit.java",
    "groupTitle": "Announce"
  },
  {
    "type": "post",
    "url": "/announceReply/deletes",
    "title": "公告回复刪除",
    "name": "announceReplyDelete",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例：",
          "content": "[1,2,3]",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>删除ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceReplyDataEdit.java",
    "groupTitle": "Announce"
  },
  {
    "type": "get",
    "url": "/announceReply/list",
    "title": "公告回复列表",
    "name": "announceReplyList",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "studyGroupId",
            "description": "<p>学习小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "announceId",
            "description": "<p>公告ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "content",
            "description": "<p>公告回复内容或作者名</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": false,
            "field": "isRead",
            "description": "<p>是否已读 0:未读,1:已读，2:所有</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>评论ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.announceId",
            "description": "<p>关联公告ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.replyId",
            "description": "<p>回复的回复ID(这个ID表示这是一个回复的回复)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createTime",
            "description": "<p>回复时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.attachmentFileId",
            "description": "<p>回复附件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.fileName",
            "description": "<p>回复附件名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.fileUrl",
            "description": "<p>回复附件URL</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.isRead",
            "description": "<p>0：未读，其他:已读</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>评论用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userNickname",
            "description": "<p>评论用户昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userAvatarUrl",
            "description": "<p>评论用户头像</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceReplyQuery.java",
    "groupTitle": "Announce"
  },
  {
    "type": "post",
    "url": "/announceReply/modify",
    "title": "公告回复修改",
    "name": "announceReplyModify",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>回复ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "attachmentFileId",
            "description": "<p>附件主键Id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "fileId",
            "description": "<p>附件Id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>回复ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceReplyDataEdit.java",
    "groupTitle": "Announce"
  },
  {
    "type": "post",
    "url": "/announce/statusToggle/edit",
    "title": "公告的评论开关",
    "name": "announce_______",
    "group": "Announce",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>公告ID数组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "allowComment",
            "description": "<p>评论开关</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>讨论ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceStatusEdit.java",
    "groupTitle": "Announce"
  },
  {
    "type": "get",
    "url": "/assign/get",
    "title": "分配列表下拉数据",
    "name": "assignGet",
    "group": "Assign",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>作业信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.type",
            "description": "<p>分配类型，1: 所有， 2：section(班级), 3: 用户</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n  \"code\": 200,\n  \"entity\": {\n    \"sections\": [\n      {\n        \"id\": 1,\n        \"name\": \"Section One\",\n        \"type\": 2\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Section Two\",\n        \"type\": 2\n      },\n      {\n        \"id\": 3,\n        \"name\": \"Section Two\",\n        \"type\": 2\n      }\n    ],\n    \"users\": [\n      {\n        \"id\": 2,\n        \"name\": \"222\",\n        \"type\": 3\n      },\n      {\n        \"id\": 3,\n        \"name\": \"33333\",\n        \"type\": 3\n      },\n      {\n        \"id\": 1,\n        \"name\": \"111\",\n        \"type\": 3\n      }\n    ]\n  },\n  \"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assign/AssignDataQuery.java",
    "groupTitle": "Assign"
  },
  {
    "type": "post",
    "url": "/assignmentGroup/add",
    "title": "作业组添加",
    "name": "assignmentGroupAdd",
    "group": "AssignmentGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "{\n\"courseId\":1,\n\"name\": \"assignments\",\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignmentgroup/AssignmentGroupDataEdit.java",
    "groupTitle": "AssignmentGroup"
  },
  {
    "type": "post",
    "url": "/assignmentGroup/moveContent/edit",
    "title": "作业组小项移动",
    "name": "assignmentGroupContentMove",
    "group": "AssignmentGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "sourceAssignmentGroupId",
            "description": "<p>来源组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "sourceAssignmentGroupItemId",
            "description": "<p>来源组内容项ID，不指定则移动来源组下所有内容到目标组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "targetAssignmentGroupId",
            "description": "<p>目标组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "targetAssignmentGroupItemId",
            "description": "<p>来源组内容项ID，不指定则移动来源组下所有内容到目标组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "DEFAULT:0",
              "TOP:1",
              "BOTTOM:2",
              "BEFORE:3",
              "AFTER:4"
            ],
            "optional": false,
            "field": "strategy",
            "description": "<p>移动策略,DEFAULT:目标组无内容时指定，TOP：置顶，BOTTOM：置底，BEFORE：指定之前，AFTER：指定之后</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>移动项ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignmentgroup/AssignmentGroupItemMoveEdit.java",
    "groupTitle": "AssignmentGroup"
  },
  {
    "type": "post",
    "url": "/assignmentGroup/deletes",
    "title": "作业组删除",
    "name": "assignmentGroupDelete",
    "group": "AssignmentGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "ids",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "1",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>删除ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例:",
          "content": "{\n\"code\": 200,\n\"entity\": \"[1, 2, 3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignmentgroup/AssignmentGroupDataEdit.java",
    "groupTitle": "AssignmentGroup"
  },
  {
    "type": "post",
    "url": "/assignmentGroupItem/deletes",
    "title": "作业组小项删除",
    "name": "assignmentGroupItemDelete",
    "group": "AssignmentGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例:",
          "content": "{\n\"code\": 200,\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignmentgroup/AssignmentGroupItemDataEdit.java",
    "groupTitle": "AssignmentGroup"
  },
  {
    "type": "get",
    "url": "/assignmentGroupItem/list",
    "title": "作业组小项列表",
    "name": "assignmentGroupItemList",
    "group": "AssignmentGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "assignmentGroupId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.originId",
            "description": "<p>源ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.originType",
            "description": "<p>源类型</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.status",
            "description": "<p>状态</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.modules",
            "description": "<p>被依赖module</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.assigns",
            "description": "<p>分配列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json 返回值",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"assignmentGroupId\": 1,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"createTime\": 1548243401000,\n\"createUserId\": 2,\n\"endTime\": 1548215072000,\n\"id\": 1,\n\"limitTime\": 1548214407000,\n\"originId\": 1,\n\"originType\": 1,\n\"startTime\": 1547869454000,\n\"updateTime\": 1548243876000,\n\"updateUserId\": 2\n}\n],\n\"id\": 1,\n\"modules\": [\n{\n\"courseId\": 1,\n\"createTime\": 1548174406000,\n\"createUserId\": 2,\n\"id\": 2,\n\"name\": \"开学第2课\",\n\"seq\": 2,\n\"startTime\": 1544760039000,\n\"status\": 0,\n\"updateTime\": 1548174406000,\n\"updateUserId\": 2\n}\n],\n\"originId\": 1,\n\"originType\": 1,\n\"score\": 10,\n\"seq\": 0,\n\"status\": 1,\n\"title\": \"第一章作业\"\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignmentgroup/AssignmentGroupItemDataQuery.java",
    "groupTitle": "AssignmentGroup"
  },
  {
    "type": "post",
    "url": "/assignmentGroupItem/modify",
    "title": "作业组小项编辑",
    "name": "assignmentGroupItemModify",
    "group": "AssignmentGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "status",
            "description": "<p>是否发布</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>修改ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignmentgroup/AssignmentGroupItemDataEdit.java",
    "groupTitle": "AssignmentGroup"
  },
  {
    "type": "get",
    "url": "/assignmentGroup/list",
    "title": "作业组列表",
    "name": "assignmentGroupList",
    "group": "AssignmentGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.seq",
            "description": "<p>排序</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json 返回值",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"courseId\": 1,\n\"id\": 1,\n\"name\": \"作业001\",\n\"seq\": 1\n},\n{\n\"courseId\": 1,\n\"id\": 3,\n\"name\": \"作业003\",\n\"seq\": 3\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignmentgroup/AssignmentGroupDataQuery.java",
    "groupTitle": "AssignmentGroup"
  },
  {
    "type": "post",
    "url": "/assignmentGroup/modify",
    "title": "作业组修改",
    "name": "assignmentGroupModify",
    "group": "AssignmentGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "{\n\"name\": \"assignments\",\n\"weight\": 1\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>修改ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignmentgroup/AssignmentGroupDataEdit.java",
    "groupTitle": "AssignmentGroup"
  },
  {
    "type": "post",
    "url": "/assignment/add",
    "title": "作业添加",
    "name": "assignmentAdd",
    "group": "Assignment",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "assignmentGroupId",
            "description": "<p>作业组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assignmentGroupItemId",
            "description": "<p>修改传过来</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4",
              "5"
            ],
            "optional": true,
            "field": "gradeType",
            "description": "<p>评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "gradeSchemeId",
            "description": "<p>评分方案（gradeType=3,4 时设置）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isIncludeGrade",
            "description": "<p>是否包含到最终成绩统计</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4"
            ],
            "optional": true,
            "field": "submissionType",
            "description": "<p>提交类型, 1: 在线 2: 书面 3: 外部工具 4: 不提交</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "toolUrl",
            "description": "<p>外部工具URL，submissionType=3</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "studyGroupSetId",
            "description": "<p>小组集</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isEmbedTool",
            "description": "<p>是否内嵌工具</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowText",
            "description": "<p>在线提交submission_type=1：文本输入</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowUrl",
            "description": "<p>在线提交submission_type=1：网站地址</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowMedia",
            "description": "<p>在线提交submission_type=1：媒体录音</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowFile",
            "description": "<p>在线提交submission_type=1：文件上传</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "fileLimit",
            "description": "<p>在线提交submission_type=1 &amp; allow_file=1: 上传文件类型限制</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "status",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "notifyUserChange",
            "description": "<p>通知用户内容已更改</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "assign",
            "description": "<p>分配</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "assign.assignId",
            "description": "<p>分配目标</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "assign.assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级), 3: 用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.limitTime",
            "description": "<p>截至时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.startTime",
            "description": "<p>可以参加测验开始日期</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.endTime",
            "description": "<p>可以参加测验结束日期</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignment/AssignmentDataEdit.java",
    "groupTitle": "Assignment"
  },
  {
    "type": "post",
    "url": "/assignment/deletes",
    "title": "作业删除",
    "name": "assignmentDeletes",
    "group": "Assignment",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>删除ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例:",
          "content": "{\n\"code\": 200,\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignment/AssignmentDataEdit.java",
    "groupTitle": "Assignment"
  },
  {
    "type": "get",
    "url": "/assignment/get",
    "title": "作业详情",
    "name": "assignmentGet",
    "group": "Assignment",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>作业信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4",
              "5"
            ],
            "optional": true,
            "field": "entity.gradeType",
            "description": "<p>评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.gradeSchemeId",
            "description": "<p>评分方案</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.isIncludeGrade",
            "description": "<p>包含到总成绩统计里</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4"
            ],
            "optional": true,
            "field": "entity.submissionType",
            "description": "<p>提交类型, 1. 在线、2. 书面、3. 外部工具、 4. 不提交</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.toolUrl",
            "description": "<p>外部工具URL，submission_type=3</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.isEmbedTool",
            "description": "<p>内嵌工具（不在新窗口打开工具）</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.allowText",
            "description": "<p>在线提交submission_type=1：文本输入</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.allowUrl",
            "description": "<p>在线提交submission_type=1：网站地址</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.allowMedia",
            "description": "<p>在线提交submission_type=1：媒体录音</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.allowFile",
            "description": "<p>在线提交submission_type=1：文件上传</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.fileLimit",
            "description": "<p>在线提交submission_type=1&amp;allow_file=1: 上传文件类型限制</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.studyGroupSetId",
            "description": "<p>小组作业，小组集ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Boolean",
            "optional": true,
            "field": "entity.studyGroupEdited",
            "description": "<p>小组可否编辑</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.isGradeEachOne",
            "description": "<p>分别为每位学生指定评分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.status",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.assigns",
            "description": "<p>分配</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.assigns.limitTime",
            "description": "<p>截至日期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.assigns.startTime",
            "description": "<p>可以参加测验开始日期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.assigns.endTime",
            "description": "<p>可以参加测验结束日期</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.assigns.assignTo",
            "description": "<p>分配到对象</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.assigns.assignTo.assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级), 3: 用户</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.assigns.assignTo.target",
            "description": "<p>分配目标ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n\"code\": 200,\n\"entity\": {\n\"allowFile\": 0,\n\"allowMedia\": 0,\n\"allowText\": 0,\n\"allowUrl\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"endTime\": 1548215072000,\n\"id\": 1,\n\"limitTime\": 1548214407000,\n\"originId\": 1,\n\"originType\": 1,\n\"startTime\": 1547869454000,\n}\n],\n\"courseId\": 1,\n\"fileLimit\": \"\",\n\"id\": 1,\n\"isEmbedTool\": 1,\n\"isIncludeGrade\": 1,\n\"score\": 0,\n\"status\": 0,\n\"studyGroupEdited\": true,\n\"title\": \"第一章作业\"\n},\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignment/AssignmentDataQuery.java",
    "groupTitle": "Assignment"
  },
  {
    "type": "post",
    "url": "/assignmentGroup/move",
    "title": "作业组移动",
    "name": "assignmentGroupMove",
    "group": "Assignment",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "assignmentGroupIds",
            "description": "<p>排序列表</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>移动项ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignmentgroup/AssignmentGroupMoveEdit.java",
    "groupTitle": "Assignment"
  },
  {
    "type": "post",
    "url": "/assignmentGroup/weight/edit",
    "title": "作业组权重",
    "name": "assignmentGroupWeight",
    "group": "Assignment",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>排序列表</p>"
          },
          {
            "group": "Parameter",
            "type": "Object",
            "optional": false,
            "field": "weight",
            "description": "<p>权重</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "[{id:1,weight:32},{id:2,weight:26}]",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>移动项ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignmentgroup/AssignmentGroupWeightEdit.java",
    "groupTitle": "Assignment"
  },
  {
    "type": "post",
    "url": "/assignmentSubmit/add",
    "title": "作业提交",
    "name": "assignmentSubmitAdd",
    "group": "Assignment",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "assignmentId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4"
            ],
            "optional": false,
            "field": "replyType",
            "description": "<p>提交方式 1: 文本　2: 文件 3: URL 4: 媒体</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>提交内容 文件/媒体方式为: 内容为fileId,多个fileId用分号分割 其余方式为文本</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "comment",
            "description": "<p>评论</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignment/AssignmentSubmitEdit.java",
    "groupTitle": "Assignment"
  },
  {
    "type": "get",
    "url": "/assignmentSubmitComment/add",
    "title": "作业提交详情页-添加评论",
    "name": "assignmentSubmitCommentAdd",
    "group": "Assignment",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "assignmentId",
            "description": "<p>作业ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "comment",
            "description": "<p>评论体</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignment/AssignmentSubmitCommentDataEdit.java",
    "groupTitle": "Assignment"
  },
  {
    "type": "get",
    "url": "/assignmentSubmitComment/list",
    "title": "作业提交详情评论列表",
    "name": "assignmentSubmitCommentList",
    "group": "Assignment",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "assignmentId",
            "description": "<p>作业ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json 响应描述",
          "content": "{\n  \"code\": 200,\n  \"entity\": [\n    {\n      \"content\": \"这次做完了\",\n      \"id\": 1,\n      \"username\": \"Test Teacher\"\n    }\n  ],\n  \"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignment/AssignmentSubmitCommentDataQuery.java",
    "groupTitle": "Assignment"
  },
  {
    "type": "get",
    "url": "/assignmentSubmit/get",
    "title": "作业提交详情",
    "name": "assignmentSubmitGet",
    "group": "Assignment",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>作业ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.submitTime",
            "description": "<p>提交时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.attachments",
            "description": "<p>附件列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.attachments.fileUrl",
            "description": "<p>附件url</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.attachments.fileName",
            "description": "<p>附件名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json 返回值",
          "content": "{\n\"code\": 200,\n\"entity\": {\n\"assignmentId\": 1,\n\"attachments\": [\n{\n\"courseId\": 1,\n\"fileName\": \"文件名称\",\n\"fileUrl\": \"xsadasdasdwqafhailusefkashfkl.jpg\"\n}\n],\n\"content\": \"12356154984\",\n\"courseId\": 1,\n\"id\": 3,\n\"isDeleted\": 0,\n\"replyType\": 1,\n\"submitTime\": 1548760218000,\n\"userId\": 2\n},\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignment/AssignmentSubmitDataQuery.java",
    "groupTitle": "Assignment"
  },
  {
    "type": "get",
    "url": "/calendarDiscussion/list",
    "title": "日历讨论列表",
    "name": "calendarDiscussionList",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "contextCodes",
            "description": "<p>日历项目 格式:日历类型_ID,日历类型_ID 逗号分隔 (日历类型, 1: 个人 2: 课程 3: 学习小组) 如 1_123,2_123</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "startDate",
            "description": "<p>开始日期 10位单位秒</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "endDate",
            "description": "<p>结束日期 10位单位秒</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>讨论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.id",
            "description": "<p>讨论id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.dueTime",
            "description": "<p>截止时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.sectionName",
            "description": "<p>班级名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.type",
            "description": "<p>讨论类型 1: 普通讨论， 2： 小组讨论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.studyGroupSetId",
            "description": "<p>小组集ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.ownStudyGroupId",
            "description": "<p>隶属小组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.assignTableId",
            "description": "<p>分配ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"id\": 1,\n\"title\": \"2222\",\n\"dueTime\": 1545794105000,\n\"roleType\":1\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/CalendarDicsussionDataQuery.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "post",
    "url": "/calendarEvent/add",
    "title": "日历-事件创建",
    "name": "calendarEventAdd",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "calendarType",
            "description": "<p>日历类型, 1: 个人 2: 课程 3: 学习小组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组Id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题（size=200)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "location",
            "description": "<p>位置</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "address",
            "description": "<p>地址</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "endTime",
            "description": "<p>结束时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isDuplicate",
            "description": "<p>是否复制</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "every",
            "description": "<p>复制间隔</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": true,
            "field": "everyUnit",
            "description": "<p>复制间隔单位 0:day 1:week 2:month</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "duplicateNum",
            "description": "<p>复制副本个数</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isCount",
            "description": "<p>标题是否追加序号</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/EventDataEdit.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "post",
    "url": "/calendarEvent/deletes",
    "title": "日历事件删除",
    "name": "calendarEventDeletes",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "[1,2,3]",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例:",
          "content": "{\n\"code\": 200,\n\"entity\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/EventDataEdit.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "get",
    "url": "/calendarEvent/get",
    "title": "日历事件详情",
    "name": "calendarEventGet",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>事件ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>事件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.calendarType",
            "description": "<p>项目类型 1: 个人 2: 课程 3: 学习小组</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>所属用户id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userName",
            "description": "<p>所属用户名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>所属课程(calendarType=2,3时非空)</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseName",
            "description": "<p>所属课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.studyGroupId",
            "description": "<p>所属小组(calendarType=3时非空)</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.studyGroupName",
            "description": "<p>所属小组名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.location",
            "description": "<p>位置</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.address",
            "description": "<p>地址</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.endTime",
            "description": "<p>结束时间</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/EventDataQuery.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "get",
    "url": "/calendarEvent/list",
    "title": "日历事件列表",
    "name": "calendarEventList",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startDate",
            "description": "<p>开始日期 10位单位秒</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "endDate",
            "description": "<p>结束日期 10位单位秒</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "contextCodes",
            "description": "<p>日历项目 格式:日历类型_ID,日历类型_ID 逗号分隔 (日历类型, 1: 个人 2: 课程 3: 学习小组) 如 1_123,2_123</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>事件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.calendarType",
            "description": "<p>项目类型 1: 个人 2: 课程 3: 学习小组</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>所属用户id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userName",
            "description": "<p>所属用户名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>所属课程(calendarType=2,3时非空)</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseName",
            "description": "<p>所属课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.studyGroupId",
            "description": "<p>所属小组(calendarType=3时非空)</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.studyGroupName",
            "description": "<p>所属小组名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.location",
            "description": "<p>位置</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.address",
            "description": "<p>地址</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.endTime",
            "description": "<p>结束时间</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/EventDataQuery.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "post",
    "url": "/calendarEvent/modify",
    "title": "日历-事件编辑",
    "name": "calendarEventModify",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>日历事件ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "calendarType",
            "description": "<p>日历类型, 1: 个人 2: 课程 3: 学习小组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组Id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题（size=200)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "location",
            "description": "<p>位置</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "address",
            "description": "<p>地址</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "endTime",
            "description": "<p>结束时间</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/EventDataEdit.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "post",
    "url": "/calendarItemCheck/add",
    "title": "日历项目选中记录添加",
    "name": "calendarItemCheckAdd",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": false,
            "field": "format",
            "description": "<p>格式:日历类型_ID (日历类型, 1: 个人 2: 课程 3: 学习小组)  如 [1_123]</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/CalendarUserCheckDataEdit.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "get",
    "url": "/calendarItem/list",
    "title": "日历项目列表",
    "name": "calendarItemList",
    "group": "Calendar",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>项目ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.calendarType",
            "description": "<p>项目类型 1: 个人 2: 课程 3: 学习小组</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>项目标题</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.coverColor",
            "description": "<p>项目前景颜色</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isCheck",
            "description": "<p>是否选中  0:未选中 1:选中</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/CalendarItemDataQuery.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "post",
    "url": "/calendarItem/modify",
    "title": "日历项目前景色修改",
    "name": "calendarItemModify",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "calendarType",
            "description": "<p>日历类型, 1: 个人 2: 课程 3: 小组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "coverColor",
            "description": "<p>前景色</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>修改成功条数</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/CalendarItemDataEdit.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "get",
    "url": "/calendarQuiz/list",
    "title": "日历测验列表",
    "name": "calendarQuizList",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "contextCodes",
            "description": "<p>日历项目 格式:日历类型_ID,日历类型_ID 逗号分隔 (日历类型, 1: 个人 2: 课程 3: 学习小组) 如 1_123,2_123</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "startDate",
            "description": "<p>开始日期 10位单位秒</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "endDate",
            "description": "<p>结束日期 10位单位秒</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>测验</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.id",
            "description": "<p>测验id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.dueTime",
            "description": "<p>截止时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.sectionName",
            "description": "<p>班级名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.assignTableId",
            "description": "<p>分配ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.type",
            "description": "<p>测验类型 2：评分测验(graded quiz)、3：评分调查(graded survey)</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"id\": 1,\n\"title\": \"2222\",\n\"dueTime\": 1545794105000\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/CalendarQuizDataQuery.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "POST",
    "url": "/calendarRes/deletes",
    "title": "日历作业,讨论，测验删除",
    "name": "calendarResDeletes",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "assignTableId",
            "description": "<p>分配主键ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "originType",
            "description": "<p>1: 作业 2:讨论 3:测验</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n\"code\": 200,\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/CalendarAssignmentDataEdit.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "get",
    "url": "/calendarRes/list",
    "title": "日历作业列表",
    "name": "calendarResList",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "contextCodes",
            "description": "<p>日历项目 格式:日历类型_ID,日历类型_ID 逗号分隔 (日历类型, 1: 个人 2: 课程 3: 学习小组) 如 1_123,2_123</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "startDate",
            "description": "<p>开始日期 10位单位秒</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "endDate",
            "description": "<p>结束日期 10位单位秒</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>作业信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.id",
            "description": "<p>作业id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.dueTime",
            "description": "<p>截止时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.roleType",
            "description": "<p>角色类型 2:老师 3:学生</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.assignTableId",
            "description": "<p>分配ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"id\": 1,\n\"title\": \"2222\",\n\"dueTime\": 1545794105000,\n\"roleType\":1\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/CalendarAssignmentDataQuery.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "post",
    "url": "/calendarTodo/add",
    "title": "日历-待办创建",
    "name": "calendarTodoAdd",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "calendarType",
            "description": "<p>日历类型, 1: 个人 2: 课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "courseId",
            "description": "<p>课程ID 所属课程(calendar_type=2时非空)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "content",
            "description": "<p>详细内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "doTime",
            "description": "<p>执行时间</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/UserTodoDataEdit.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "post",
    "url": "/calendarTodo/deletes",
    "title": "日历待办删除",
    "name": "calendarTodoDeletes",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "[1,2,3]",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>删除成功的记录条数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例:",
          "content": "{\n\"code\": 200,\n\"entity\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/UserTodoDataEdit.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "get",
    "url": "/calendarTodo/list",
    "title": "日历待办列表",
    "name": "calendarTodoList",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startDate",
            "description": "<p>开始日期 10位单位秒</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "endDate",
            "description": "<p>结束日期 10位单位秒</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "contextCodes",
            "description": "<p>日历项目 格式:日历类型_ID,日历类型_ID 逗号分隔 (日历类型, 1: 个人 2: 课程 ) 如 1_123,2_123</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>待办ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.calendarType",
            "description": "<p>项目类型 1: 个人 2: 课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>所属用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userName",
            "description": "<p>所属用户名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>所属课程ID(calendarType=2,3时非空)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.courseName",
            "description": "<p>所属课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.doTime",
            "description": "<p>执行时间</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/UserTodoDataQuery.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "post",
    "url": "/calendarTodo/modify",
    "title": "日历-待办编辑",
    "name": "calendarTodoModify",
    "group": "Calendar",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>待办ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "calendarType",
            "description": "<p>日历类型, 1: 个人 2: 课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "courseId",
            "description": "<p>课程ID 所属课程(calendar_type=2时非空)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "content",
            "description": "<p>详细内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "doTime",
            "description": "<p>执行时间</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/UserTodoDataEdit.java",
    "groupTitle": "Calendar"
  },
  {
    "type": "get",
    "url": "/image",
    "title": "文件访问规则",
    "description": "<p>图片/文件地址: http://192.168.6.166/group1/M00/00/0D/wKgFFFyGSkqAYB_9AA12C5JxxdA516.jpg <br/> 图片裁剪: http://192.168.6.166/group1/M00/00/0D/wKgFFFyGSkqAYB_9AA12C5JxxdA516.jpg?s=thumb&amp;a=64x64 <br/> 图偏拉伸: http://192.168.6.166/group1/M00/00/0D/wKgFFFyGSkqAYB_9AA12C5JxxdA516.jpg?s=resize&amp;w=800&amp;h=800 <br/> 图片旋转: http://192.168.6.166/group1/M00/00/0D/wKgFFFyGSkqAYB_9AA12C5JxxdA516.jpg?s=rotate&amp;d=90 <br/></p>",
    "name": "FileImageSASSSS",
    "group": "Common",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/api/FileUploadController.java",
    "groupTitle": "Common"
  },
  {
    "type": "post",
    "url": "/login",
    "title": "登录",
    "description": "<p>登录</p>",
    "name": "Login",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "allowedValues": [
              "1:管理员",
              "2:教师",
              "3:助教",
              "4:学生"
            ],
            "optional": false,
            "field": "roleType",
            "description": "<p>角色类型</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\nusername: \"admin\",\npassword: \"abcdefg\"\nroleType: 1\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "vo",
            "description": "<p>成功登录用户信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "vo.id",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo.username",
            "description": "<p>登录名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo.nickname",
            "description": "<p>昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo.fullName",
            "description": "<p>全称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4"
            ],
            "optional": false,
            "field": "vo.type",
            "description": "<p>用户类型, 1.管理员 2.教师 3.助教 4.学生</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo.email",
            "description": "<p>邮箱</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo.phone",
            "description": "<p>联系电话</p>"
          },
          {
            "group": "Success 200",
            "type": "Url",
            "optional": false,
            "field": "vo.avatarUrl",
            "description": "<p>头像</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "vo.orgs",
            "description": "<p>只有管理员角色返回 用于切换系统使用</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "vo.orgs.id",
            "description": "<p>用户所属机构ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "vo.orgs.name",
            "description": "<p>用户所属机构名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "vo.orgs.treeId",
            "description": "<p>用户所属机构树id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json data",
          "content": "{\n\"code\": 200,\n\"entity\": {\n\"fullName\": \"admin\",\n\"id\": 1,\n\"nickname\": \"admin\",\n\"orgs\": [\n{\n\"id\": 1,\n\"name\": \"sys_root\",\n\"treeId\": \"0001\"\n}\n],\n\"sex\": 0,\n\"status\": 1,\n\"username\": \"admin\"\n},\n\"message\": \"success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/api/LoginApi.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/logout",
    "title": "登出",
    "description": "<p>登出</p>",
    "name": "Logout",
    "group": "Common",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>响应体</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"vo\": \"\",\n\"message\": \"success\"\n}\n<p>",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/api/LoginApi.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/role/list",
    "title": "角色列表",
    "name": "RoleList",
    "group": "Common",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>角色列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>角色ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>角色名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/RoleDataQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/login/get",
    "title": "角色列表",
    "name": "RoleList",
    "group": "Common",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>角色列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>角色ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>角色名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/LoginDataQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "post",
    "url": "/userFile/add",
    "title": "文件添加",
    "description": "<p>添加文件或文件夹；文件分个人、课程、学习小组三种空间，学生可以对个人、学习小组空间进行操作，教师可以对ｇｅ</p>",
    "name": "UserFileAdd",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isDirectory",
            "description": "<p>是否为文件夹</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>文件名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "fileId",
            "description": "<p>文件ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "parentDirectoryId",
            "description": "<p>上级文件夹ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>文件ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileDataEdit.java",
    "groupTitle": "Common"
  },
  {
    "type": "post",
    "url": "/userFile/copy/edit",
    "title": "文件复制",
    "description": "<p>复制文件或文件夹到指定目录下，复制源与目标必须在不同空间内</p>",
    "name": "UserFileCopy",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "source",
            "description": "<p>复制文件ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "target",
            "description": "<p>目标文件夹ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n    \"source\": 11,\n    \"target\": 1\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>复制后新文件（跟目录）ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"entity\": \"11\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileCopyEdit.java",
    "groupTitle": "Common"
  },
  {
    "type": "post",
    "url": "/userFile/deletes",
    "title": "文件删除",
    "description": "<p>删除文件或文件夹</p>",
    "name": "UserFileDelete",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "ids",
            "description": "<p>ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除文件ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"entity\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileDataEdit.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/userFile/get",
    "title": "获取文件信息",
    "description": "<p>获取文件信息</p>",
    "name": "UserFileGet",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>文件列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"entity\": {\nid: 1,\ncourseId: 1,\nfileName: \"abc.txt\",\nfileType: \"word\",\nisDirectory: 0,\n...\n}\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileDataQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/userFile/list",
    "title": "文件夹内容列表",
    "description": "<p>指定parentId时，返回parentId目录的子文件及目录，未指定parentId，返回用户文件列表（包括个人目录、课程目录、学习小组目录）</p>",
    "name": "UserFileList",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "parentId",
            "description": "<p>父目录ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>文件列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>文件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.parentId",
            "description": "<p>父目录ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.fileName",
            "description": "<p>文件名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.fileSize",
            "description": "<p>文件大小</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.fileType",
            "description": "<p>文件类型</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isDirectory",
            "description": "<p>是否为目录</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isSystemLevel",
            "description": "<p>是否系统创建目录</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.spaceType",
            "description": "<p>所属空间，1：课程 2：学习小组 3：个人</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.userId",
            "description": "<p>所属用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.courseId",
            "description": "<p>所属课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.studyGroupId",
            "description": "<p>所属学习小组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.status",
            "description": "<p>状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.treeId",
            "description": "<p>TreeId</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createTime",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.updateTime",
            "description": "<p>最近修改时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createUserId",
            "description": "<p>创建用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.updateUserId",
            "description": "<p>修改用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.createUserName",
            "description": "<p>创建用户名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.updateUserName",
            "description": "<p>修改用户名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"entity\": [{\n        id: 1,\n        courseId: 1,\n        fileName: \"abc.txt\",\n        fileType: \"word\",\n        isDirectory: 0,\n        ...\n    }]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileDataQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "post",
    "url": "/userFile/modify",
    "title": "文件重命名",
    "description": "<p>用户文件、文件夹重命名</p>",
    "name": "UserFileModify",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>文件名称</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n    \"courseId\": 1,\n    \"isDirectory\": 1,\n    \"fileName\": \"tmp\",\n    \"parentId\": 1\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>文件ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"entity\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileDataEdit.java",
    "groupTitle": "Common"
  },
  {
    "type": "post",
    "url": "/userFile/move/edit",
    "title": "文件移动",
    "description": "<p>移动文件或文件夹；移动源与目标必须在同一空间内</p>",
    "name": "UserFileMove",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "source",
            "description": "<p>移动文件ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "target",
            "description": "<p>目标文件夹ID（必须为文件夹）</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n    \"source\": 11,\n    \"target\": 1\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>被移动文件ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"entity\": \"11\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileMoveEdit.java",
    "groupTitle": "Common"
  },
  {
    "type": "post",
    "url": "/userFile/status/edit",
    "title": "文件状态修改",
    "description": "<p>文件状态修改</p>",
    "name": "UserFileStatusEdit",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>文件ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "status",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "accessStrategy",
            "description": "<p>访问限制, 1: 无限制, 2: 限制链接访问, 3: 限制时间访问</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "startTime",
            "description": "<p>限制访问开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "endTime",
            "description": "<p>限制访问结束时间</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>文件ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileStatusEdit.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/announce/linkInfo/query",
    "title": "公告链接信息查询",
    "name": "announceLinkInfo",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>链接信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.id",
            "description": "<p>公告ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/announce/AnnounceLinkInfoQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/assignStudent/get",
    "title": "学生分配信息",
    "name": "assignStudentGet",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>originType_originId</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>作业信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n\"code\": 200,\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/ItemAssignQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/assignment/linkInfo/query",
    "title": "作业链接信息查询",
    "name": "assignmentLinkInfo",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>链接信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.id",
            "description": "<p>作业ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/assignment/AssignmentLinkInfoQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/assignmentSelect/list",
    "title": "作业下拉列表",
    "name": "assignmentSelectList",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>courseId</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>作业信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n\"code\": 200,\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/AssignmentSelectDataQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "POST",
    "url": "/change",
    "title": "切换角色/管理员切换机构",
    "description": "<p>切换角色/管理员切换机构</p>",
    "name": "change",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "orgId",
            "description": "<p>机构ID 机构切换使用</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "2:教师",
              "4:学生"
            ],
            "optional": true,
            "field": "typeId",
            "description": "<p>角色切换使用</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>响应体</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"vo\": \"\",\n\"message\": \"success\"\n}\n<p>",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/api/LoginApi.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/ckupload",
    "title": "文件上传-富文本",
    "description": "<p>富文本文件上传</p>",
    "name": "ckupload",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "optional": false,
            "field": "upload",
            "description": ""
          }
        ]
      }
    },
    "group": "Common",
    "success": {
      "examples": [
        {
          "title": "JSON 返回",
          "content": "<p>\n{\n\"uploaded\": 1,\n\"url\": \"group1/M00/00/0D/wKgFFFyIheGAFz3IAAI36YP4DZ0414.jpg\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/api/FileUploadController.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/userFile/courseFile/query",
    "title": "文件夹、图片查询",
    "description": "<p>查询课程空间或用户空间下所有文件夹、或所有图片文件；文件夹按treeId排序，图片按名称排序</p>",
    "name": "courseFileQuery",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "courseId",
            "description": "<p>课程ID，存在时查询课程空间，不存在时查询用户空间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "queryImage",
            "description": "<p>1: 查询图片类型文件, 0: 查询文件夹</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "name",
            "description": "<p>图片名称（查询图片时，可以指定图片名称过滤）</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>文件列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>文件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.parentId",
            "description": "<p>父目录ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.fileName",
            "description": "<p>文件名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.fileSize",
            "description": "<p>文件大小</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.fileType",
            "description": "<p>文件类型</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isDirectory",
            "description": "<p>是否为目录</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isSystemLevel",
            "description": "<p>是否系统创建目录</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.spaceType",
            "description": "<p>所属空间，1：课程 2：学习小组 3：个人</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.userId",
            "description": "<p>所属用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.courseId",
            "description": "<p>所属课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.studyGroupId",
            "description": "<p>所属学习小组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.status",
            "description": "<p>状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.treeId",
            "description": "<p>TreeId</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createTime",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.updateTime",
            "description": "<p>最近修改时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createUserId",
            "description": "<p>创建用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.updateUserId",
            "description": "<p>修改用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.createUserName",
            "description": "<p>创建用户名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.updateUserName",
            "description": "<p>修改用户名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileDirOrImageQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/discussion/linkInfo/query",
    "title": "讨论链接信息查询",
    "name": "discussionLinkInfo",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>链接信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.id",
            "description": "<p>讨论ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionLinkInfoQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/discussionSelect/list",
    "title": "讨论下拉列表",
    "name": "discussionSelectList",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>courseId</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>作业信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n\"code\": 200,\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/DiscussionSelectDataQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/fileSelect/list",
    "title": "文件下拉列表",
    "name": "fileSelectList",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>courseId</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>作业信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n\"code\": 200,\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/FileSelectDataQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/moduleItemAssign/list",
    "title": "小项分配列表",
    "name": "moduleItemAssignList",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>courseId</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originId",
            "description": "<p>originId</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originType",
            "description": "<p>originType</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>单元项内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>itemId</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级), 3: 用户</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "limitTime",
            "description": "<p>截止时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "endTime",
            "description": "<p>结束时间</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleItemAssignDataQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/module/linkInfo/query",
    "title": "单元链接信息查询",
    "name": "moduleLinkInfo",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>链接信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.id",
            "description": "<p>单元ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleLinkInfoQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "post",
    "url": "/publish/status/edit",
    "title": "内容发布/取消发布公用接口",
    "name": "publishStatusEdit",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originId",
            "description": "<p>资源Id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "status",
            "description": "<p>状态 1:发布 0:未发布</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originType",
            "description": "<p>资源类型 1: 作业, 2：讨论 3: 测验，4: 文件 5: 页面</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>success</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/PublishDataEdit.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/quiz/linkInfo/query",
    "title": "测验链接信息查询",
    "name": "quizLinkInfo",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>链接信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.id",
            "description": "<p>测验ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizLinkInfoQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/quizSelect/list",
    "title": "测验下拉列表",
    "name": "quizSelectList",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>courseId</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>作业信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json",
          "content": "{\n\"code\": 200,\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/QuizSelectDataQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/resource/locked/search",
    "title": "是否锁定",
    "name": "resourceLockedSearch",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originId",
            "description": "<p>资源Id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originType",
            "description": "<p>资源类型 1: 作业, 2：讨论 3: 测验，4: 文件 5: 页面</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>success</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/LockedDataSearch.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/upload",
    "title": "文件上传",
    "description": "<p>文件上传通用接口</p>",
    "name": "upload",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Object",
            "optional": false,
            "field": "file",
            "description": "<p>上传文件对象</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>文件信息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.fileId",
            "description": "<p>文件URL(业务接口上传文件时使用此字段)</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.fileSize",
            "description": "<p>文件大小（单位: byte)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.fileType",
            "description": "<p>文件类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.originName",
            "description": "<p>文件名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "JSON 返回",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"fileId\": \"group1/M00/00/0D/wKgFFFyIhoiAYFKMAAI36YP4DZ0298.jpg\",\n\"fileSize\": 145385,\n\"fileType\": \"jpg\",\n\"id\": 718,\n\"originName\": \"微信图片_20170613104409.jpg\"\n}\n],\n\"message\": \"success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/api/FileUploadController.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/userFile/download/direct",
    "title": "文件下载",
    "description": "<p>下载文件或文件夹，文件夹打包下载</p>",
    "name": "userFileDownload",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>文件、文件夹ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileDownloadEdit.java",
    "groupTitle": "Common"
  },
  {
    "type": "get",
    "url": "/userFile/byName/query",
    "title": "文件检索",
    "description": "<p>文件检索：按名称检索指定根目录下文件</p>",
    "name": "userFileQueryByName",
    "group": "Common",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "rootId",
            "description": "<p>根目录ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>文件列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>文件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.parentId",
            "description": "<p>父目录ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.fileName",
            "description": "<p>文件名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.fileSize",
            "description": "<p>文件大小</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.fileType",
            "description": "<p>文件类型</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isDirectory",
            "description": "<p>是否为目录</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isSystemLevel",
            "description": "<p>是否系统创建目录</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.spaceType",
            "description": "<p>所属空间，1：课程 2：学习小组 3：个人</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.userId",
            "description": "<p>所属用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.courseId",
            "description": "<p>所属课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.studyGroupId",
            "description": "<p>所属学习小组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.status",
            "description": "<p>状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.treeId",
            "description": "<p>TreeId</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createTime",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.updateTime",
            "description": "<p>最近修改时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createUserId",
            "description": "<p>创建用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.updateUserId",
            "description": "<p>修改用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.createUserName",
            "description": "<p>创建用户名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.updateUserName",
            "description": "<p>修改用户名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/file/UserFileByNameQuery.java",
    "groupTitle": "Common"
  },
  {
    "type": "post",
    "url": "/course/conclude/edit",
    "title": "结束课程",
    "description": "<p>结束课程，课程结束后，教师学生不能再对课程进行修改操作</p>",
    "name": "CourseConclude",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseConcludeEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "get",
    "url": "/courseDialog/list",
    "title": "用户在课程下发生的对话查询",
    "name": "CourseDialogList",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "entity.type",
            "description": "<p>类型, 1: 讨论 2: 评分任务评论</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.content",
            "description": "<p>回复内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createTime",
            "description": "<p>回复时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>回复用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.username",
            "description": "<p>回复用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.discussionId",
            "description": "<p>讨论ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.discussionTitle",
            "description": "<p>讨论标题</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/UserDialogQuery.java",
    "groupTitle": "Course"
  },
  {
    "type": "get",
    "url": "/courseDialog/pageList",
    "title": "用户在课程下发生的对话查询分页",
    "name": "CourseDialogPageList",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageSize",
            "description": "<p>页大小</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.total",
            "description": "<p>总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageSize",
            "description": "<p>页大小</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.list",
            "description": "<p>结果列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "entity.list.type",
            "description": "<p>类型, 1: 讨论 2: 评分任务评论</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.content",
            "description": "<p>回复内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.createTime",
            "description": "<p>回复时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.userId",
            "description": "<p>回复用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.username",
            "description": "<p>回复用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.discussionId",
            "description": "<p>讨论ID(type=1: 讨论）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.list.discussionTitle",
            "description": "<p>讨论标题(type=1: 讨论）</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.assignmentGroupItemId",
            "description": "<p>任务项ID(type=2, 评分任务评论）</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.assignmentGroupItemTitle",
            "description": "<p>任务项标题(type=2, 评分任务评论）</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/UserDialogQuery.java",
    "groupTitle": "Course"
  },
  {
    "type": "post",
    "url": "/course/enroll/edit",
    "title": "注册课程",
    "name": "CourseEnrollEdit",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>注册码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/EnrollCourseEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "post",
    "url": "/course/enrollmentInfo/query",
    "title": "使用注册code查询课程信息",
    "name": "CourseEnrollmentInfoQuery",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>开放注册码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>课程信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.publicStatus",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isConclude",
            "description": "<p>是否已结束</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isDeleted",
            "description": "<p>是否已删除</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseEnrollmentInfoQuery.java",
    "groupTitle": "Course"
  },
  {
    "type": "get",
    "url": "/courseMessage/list",
    "title": "用户在课程收到的消息查询",
    "name": "CourseMessageList",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.content",
            "description": "<p>回复内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createTime",
            "description": "<p>回复时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>回复用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.username",
            "description": "<p>回复用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.announceId",
            "description": "<p>公告ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.announceTitle",
            "description": "<p>公告标题</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/UserMessaageQuery.java",
    "groupTitle": "Course"
  },
  {
    "type": "get",
    "url": "/courseMessage/pageList",
    "title": "用户在课程收到的消息查询分页",
    "name": "CourseMessagePageList",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageSize",
            "description": "<p>页大小</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.total",
            "description": "<p>总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageSize",
            "description": "<p>页大小</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.list",
            "description": "<p>结果列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.content",
            "description": "<p>回复内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.createTime",
            "description": "<p>回复时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.userId",
            "description": "<p>回复用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.username",
            "description": "<p>回复用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.announceId",
            "description": "<p>公告ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.announceTitle",
            "description": "<p>公告标题</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/common/UserMessaageQuery.java",
    "groupTitle": "Course"
  },
  {
    "type": "get",
    "url": "/course/public/query",
    "title": "公共课程列表",
    "description": "<p>公共课程查询</p>",
    "name": "CoursePublicQuery",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "filterOpenRegistry",
            "description": "<p>开放注册</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "filterPublic",
            "description": "<p>公开</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>课程列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "entity.visibility",
            "description": "<p>可见性, 1: 公开, 2: 课程专属</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.allowJoin",
            "description": "<p>是否用户可以直接加入</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.allowOpenRegistry",
            "description": "<p>开放注册</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.joinedStudentNum",
            "description": "<p>用户作为学生加入课程下班级的数量</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/PublicCourseQuery.java",
    "groupTitle": "Course"
  },
  {
    "type": "get",
    "url": "/course/publicStatus/query",
    "title": "课程发布状态查询",
    "name": "CoursePublicStatusQuery",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>课程状态</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.status",
            "description": "<p>课程发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeTaskSubmittedCount",
            "description": "<p>已提交评分任务数量</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseStatusQuery.java",
    "groupTitle": "Course"
  },
  {
    "type": "post",
    "url": "/course/add",
    "title": "课程添加",
    "description": "<p>课程添加</p>",
    "name": "courseAdd",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "allowedValues": [
              "{..500}"
            ],
            "optional": false,
            "field": "name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "code",
            "description": "<p>课程编码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "coverColor",
            "description": "<p>前景色</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "alias",
            "description": "<p>用户为课程设置昵称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "status",
            "description": "<p>是否发布: 1. 发布，0. 不发布</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": true,
            "field": "visibility",
            "description": "<p>可见性: 1. 公开，2. 课程，3. 机构(预留)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "endTime",
            "description": "<p>结束时间</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增课程ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseDataEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "post",
    "url": "/course/alias/edit",
    "title": "用户参与课程别名设置",
    "description": "<p>用户为自己参与的课程设置别名</p>",
    "name": "courseAliasEdit",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "alias",
            "description": "<p>别名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "coverColor",
            "description": "<p>颜色</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseAliasEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "post",
    "url": "/course/config/edit",
    "title": "课程配置编辑",
    "description": "<p>课程配置编辑，每个课程只存在一条配置信息</p>",
    "name": "courseConfigEdit",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Object",
            "optional": false,
            "field": "course",
            "description": "<p>课程信息</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "course.id",
            "description": "<p>课程IDlinux</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "course.name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "course.code",
            "description": "<p>课程编码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "course.status",
            "description": "<p>课程发布状态</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": true,
            "field": "course.visibility",
            "description": "<p>可见性: 1. 公开，2. 课程，3. 机构(预留)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "course.description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "course.termId",
            "description": "<p>学期ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "course.startTime",
            "description": "<p>开始时间（时间戳）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "course.endTime",
            "description": "<p>结束时间</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "coverFileUrl",
            "description": "<p>更新封面图地址</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "format",
            "description": "<p>课程格式: 1. 校内, 2. 在线, 3: 混合</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "enableGrade",
            "description": "<p>启用评分</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "gradeSchemeId",
            "description": "<p>评分策略ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "allowViewBeforeStartTime",
            "description": "<p>允许开始时间前访问</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "allowViewAfterEndTime",
            "description": "<p>允许结束时间后访问</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "allowOpenRegistry",
            "description": "<p>开放注册</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "openRegistryCode",
            "description": "<p>开放注册码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "enableHomepageAnnounce",
            "description": "<p>启用课程首页通知</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "homepageAnnounceNumber",
            "description": "<p>课程首页通知数量</p>"
          },
          {
            "group": "Parameter",
            "type": "Object",
            "optional": true,
            "field": "userConfig",
            "description": "<p>用户配置</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "userConfig.allowMarkPostStatus",
            "description": "<p>允许手动将帖子标记为已读</p>"
          },
          {
            "group": "Parameter",
            "type": "Object",
            "optional": true,
            "field": "discussionConfig",
            "description": "<p>讨论配置</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "discussionConfig.allowDiscussionAttachFile",
            "description": "<p>允许学生讨论上传附件</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "discussionConfig.allowStudentCreateDiscussion",
            "description": "<p>允许学生自己创建讨论</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "discussionConfig.allowStudentEditDiscussion",
            "description": "<p>允许学生修改讨论</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowStudentCreateStudyGroup",
            "description": "<p>允许学生创建学习小组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "hideTotalInStudentGradeSummary",
            "description": "<p>在学生评分汇总中隐藏总分</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "hideGradeDistributionGraphs",
            "description": "<p>对学生隐藏评分分布图</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowAnnounceComment",
            "description": "<p>允许公告评论</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "coursePageEditType",
            "description": "<p>课程Page可编辑的类型, 1:教师, 2:教师与学生, 3: 所有人</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "timeZone",
            "description": "<p>时区(预留)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "subAccount",
            "description": "<p>子账号(预留)</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n    \"id\": 1,\n    \"status\": 0\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改课程ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseConfigEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "get",
    "url": "/course/config/query",
    "title": "课程配置查询",
    "description": "<p>课程配置查询</p>",
    "name": "courseConfigQuery",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Object",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "/course/nav/query?courseId=1",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "vo",
            "description": "<p>课程导航配置</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "vo.coverColor",
            "description": "<p>封面颜色</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "vo.alias",
            "description": "<p>用户设置别名</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": true,
            "field": "vo.isFavorite",
            "description": "<p>用户是否添加到了偏好课程中</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "vo.course",
            "description": "<p>课程基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "vo.course.id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo.course.name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo.course.code",
            "description": "<p>课程编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "vo.course.status",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "vo.course.visibility",
            "description": "<p>可见性，1: 公开, 2: 课程专属</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "vo.course.isEnd",
            "description": "<p>是否结束</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "vo.course.coverFileId",
            "description": "<p>封面图ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "vo.course.description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "vo.course.termId",
            "description": "<p>学期ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "vo.course.startTime",
            "description": "<p>开始时间（时间戳）</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "vo.course.endTime",
            "description": "<p>结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "vo.course.homepage",
            "description": "<p>首页</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "vo.course.includePublishIndex",
            "description": "<p>是否包含到公共课程索引中</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "vo.format",
            "description": "<p>课程格式: 1. 校内, 2. 在线, 3: 混合</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.enableGrade",
            "description": "<p>启用评分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "vo.gradeSchemeId",
            "description": "<p>评分策略ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.allowViewBeforeStartTime",
            "description": "<p>允许开始时间前访问</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.allowViewAfterEndTime",
            "description": "<p>允许结束时间后访问</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.allowOpenRegistry",
            "description": "<p>开放注册</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "vo.openRegistryCode",
            "description": "<p>开放注册码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.enableHomepageAnnounce",
            "description": "<p>启用课程首页通知</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "vo.homepageAnnounceNumber",
            "description": "<p>课程首页通知数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "vo.userConfig",
            "description": "<p>用户配置</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.userConfig.allowMarkPostStatus",
            "description": "<p>允许手动将帖子标记为已读</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.discussionConfig.allowDiscussionAttachFile",
            "description": "<p>允许学生讨论上传附件</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.discussionConfig.allowStudentCreateDiscussion",
            "description": "<p>允许学生自己创建讨论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.discussionConfig.allowStudentEditDiscussion",
            "description": "<p>允许学生修改讨论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.allowStudentCreateStudyGroup",
            "description": "<p>允许学生创建学习小组</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.hideTotalInStudentGradeSummary",
            "description": "<p>在学生评分汇总中隐藏总分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.hideGradeDistributionGraphs",
            "description": "<p>对学生隐藏评分分布图</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "vo.allowAnnounceComment",
            "description": "<p>允许公告评论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "vo.coursePageEditType",
            "description": "<p>课程Page可编辑的类型, 1:教师, 2:教师与学生, 3: 所有人</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "vo.timeZone",
            "description": "<p>时区(预留)</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "vo.term",
            "description": "<p>学期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "vo.term.id",
            "description": "<p>学期ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo.term.name",
            "description": "<p>学期</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo.term.code",
            "description": "<p>学期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "vo.term.startTime",
            "description": "<p>学期开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "vo.term.endTime",
            "description": "<p>学期结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "vo.org",
            "description": "<p>机构</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "vo.org.id",
            "description": "<p>机构ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "vo.org.sisId",
            "description": "<p>机构 SIS ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "vo.org.name",
            "description": "<p>机构名</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": true,
            "field": "vo.org.type",
            "description": "<p>机构类型, 1: 学校 2: 非学校</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"vo\": {\n        course: {\n            id: 1\n        }\n    }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseConfigQuery.java",
    "groupTitle": "Course"
  },
  {
    "description": "<p>课程删除</p>",
    "name": "courseDeletes",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "ids",
            "description": "<p>ID列表</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除课程ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseDataEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "post",
    "url": "/course/favorite/edit",
    "title": "用户收藏设置",
    "description": "<p>设置或取消课程Favorite，Favorite课程会出现在Dashboard中</p>",
    "name": "courseFavoriteEdit",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "favorite",
            "description": "<p>设置Favorite状态，0: 取消， 1: 设置</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>课程ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"entity\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseFavoriteEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "get",
    "url": "/course/favorite/query",
    "title": "用户收藏课程查询",
    "description": "<p>查询用户收藏的课程，Dashboard，右侧边烂使用接口</p>",
    "name": "courseFavoriteQuery",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "includeExt",
            "description": "<p>是否返回扩展信息（默认不返回）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isSort",
            "description": "<p>是否排序返回（默认不排序）</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>收藏课程列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.courseAlias",
            "description": "<p>课程别名（用户为自己加入的课程创建的昵称）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.code",
            "description": "<p>课程编码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.description",
            "description": "<p>课程简介</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.status",
            "description": "<p>课程发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.coverColor",
            "description": "<p>课程封面色</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.coverFileUrl",
            "description": "<p>课程封面图URL</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.endTime",
            "description": "<p>结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isConclude",
            "description": "<p>是否结束</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity.term",
            "description": "<p>课程学期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.term.id",
            "description": "<p>课程学期ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.term.name",
            "description": "<p>课程学期名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.term.code",
            "description": "<p>课程学期编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.term.startTime",
            "description": "<p>课程学期开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.term.endTime",
            "description": "<p>课程学期结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity.favoriteExt",
            "description": "<p>扩展信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.favoriteExt.assignmentTodoNumber",
            "description": "<p>作业待办数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.favoriteExt.discussionTodoNumber",
            "description": "<p>讨论待办数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.favoriteExt.quizTodoNumber",
            "description": "<p>测验待办数量</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"entity\": {\n\n    }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseFavoriteQuery.java",
    "groupTitle": "Course"
  },
  {
    "type": "post",
    "url": "/course/homepage/edit",
    "title": "课程首页编辑",
    "name": "courseHomepageEdit",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "homepage",
            "description": "<p>课程首页：ACTIVE_STREAM: 活动流, MODULE：单元, ASSIGNMENTS：作业组, SYLLABUS：大纲, PAGE: 页面</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseHomepageEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "get",
    "url": "/course/joined/query",
    "title": "参与课程查询",
    "description": "<p>查询用户参与的课程（所有课程界面）</p>",
    "name": "courseJoinedQuery",
    "group": "Course",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": "<p>加入的课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.currentEnrollments",
            "description": "<p>当前课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.currentEnrollments.id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.currentEnrollments.name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.currentEnrollments.courseAlias",
            "description": "<p>课程别名（用户为自己加入的课程创建的昵称）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.currentEnrollments.code",
            "description": "<p>课程编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.currentEnrollments.isFavorite",
            "description": "<p>是否收藏</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.currentEnrollments.status",
            "description": "<p>课程发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.currentEnrollments.coverColor",
            "description": "<p>课程封面色</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity.currentEnrollments.term",
            "description": "<p>课程学期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.currentEnrollments.term.id",
            "description": "<p>课程学期ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.currentEnrollments.term.name",
            "description": "<p>课程学期名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.currentEnrollments.term.code",
            "description": "<p>课程学期编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.currentEnrollments.term.startTime",
            "description": "<p>课程学期开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.currentEnrollments.term.endTime",
            "description": "<p>课程学期结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.currentEnrollments.allowOpenRegistry",
            "description": "<p>开放注册</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.currentEnrollments.openRegistryCode",
            "description": "<p>开放注册码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.currentEnrollments.gradeTaskSubmittedCount",
            "description": "<p>已提交评分任务数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.priorEnrollments",
            "description": "<p>已结束的课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.priorEnrollments.id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.priorEnrollments.name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.priorEnrollments.courseAlias",
            "description": "<p>课程别名（用户为自己加入的课程创建的昵称）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.priorEnrollments.code",
            "description": "<p>课程编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.priorEnrollments.isFavorite",
            "description": "<p>是否收藏</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.priorEnrollments.status",
            "description": "<p>课程发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.priorEnrollments.coverColor",
            "description": "<p>课程封面色</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity.priorEnrollments.term",
            "description": "<p>课程学期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.priorEnrollments.term.id",
            "description": "<p>课程学期ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.priorEnrollments.term.name",
            "description": "<p>课程学期名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.priorEnrollments.term.code",
            "description": "<p>课程学期编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.priorEnrollments.term.startTime",
            "description": "<p>课程学期开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.priorEnrollments.term.endTime",
            "description": "<p>课程学期结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.priorEnrollments.allowOpenRegistry",
            "description": "<p>开放注册</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.priorEnrollments.openRegistryCode",
            "description": "<p>开放注册码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.priorEnrollments.gradeTaskSubmittedCount",
            "description": "<p>已提交评分任务数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.futureEnrollments",
            "description": "<p>未开始的课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.futureEnrollments.id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.futureEnrollments.name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.futureEnrollments.courseAlias",
            "description": "<p>课程别名（用户为自己加入的课程创建的昵称）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.futureEnrollments.code",
            "description": "<p>课程编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.futureEnrollments.isFavorite",
            "description": "<p>是否收藏</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.futureEnrollments.status",
            "description": "<p>课程发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.futureEnrollments.coverColor",
            "description": "<p>课程封面色</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity.futureEnrollments.term",
            "description": "<p>课程学期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.futureEnrollments.term.id",
            "description": "<p>课程学期ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.futureEnrollments.term.name",
            "description": "<p>课程学期名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.futureEnrollments.term.code",
            "description": "<p>课程学期编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.futureEnrollments.term.startTime",
            "description": "<p>课程学期开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.futureEnrollments.term.endTime",
            "description": "<p>课程学期结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.futureEnrollments.allowOpenRegistry",
            "description": "<p>开放注册</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.futureEnrollments.openRegistryCode",
            "description": "<p>开放注册码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.futureEnrollments.gradeTaskSubmittedCount",
            "description": "<p>已提交评分任务数量</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"entity\": {\n\n    }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseJoinedQuery.java",
    "groupTitle": "Course"
  },
  {
    "description": "<p>课程修改(非必填参数 ， 如果不修改 ， 可以不传)</p>",
    "name": "courseModify",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "code",
            "description": "<p>课程编码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "alias",
            "description": "<p>用户为课程设置昵称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "coverColor",
            "description": "<p>前景色</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "coverFileId",
            "description": "<p>封面图ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "status",
            "description": "<p>是否发布: 1. 发布，0. 不发布</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": true,
            "field": "visibility",
            "description": "<p>可见性: 1. 公开，2. 课程，3. 机构(预留)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "endTime",
            "description": "<p>结束时间</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": 1,\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改课程ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseDataEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "post",
    "url": "/course/nav/edit",
    "title": "课程导航编辑",
    "description": "<p>课程导航编辑</p>",
    "name": "courseNavEdit",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Object",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": false,
            "field": "navList",
            "description": "<p>导航列表</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "navList.code",
            "description": "<p>菜单Code</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "navList.status",
            "description": "<p>启用状态</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n    \"id\": 1,\n    \"navList\": [{\n         code: \"course.nav.module\",\n         status: 1\n    }]\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改课程ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseNavEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "get",
    "url": "/course/nav/query",
    "title": "课程导航查询",
    "description": "<p>课程导航查询</p>",
    "name": "courseNavQuery",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "status",
            "description": "<p>状态</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "/course/nav/query?courseId=1",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>课程导航菜单列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.code",
            "description": "<p>菜单编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.name",
            "description": "<p>菜单名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.status",
            "description": "<p>状态</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"entity\": [\n            {\n                \"code\": \"course.nav.page\",\n                \"name\": \"Pages\",\n                \"seq\": 1,\n                \"status\": 1\n            }\n    ],\n    \"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseNavQuery.java",
    "groupTitle": "Course"
  },
  {
    "type": "post",
    "url": "/course/statusToggle/edit",
    "title": "课程状态切换",
    "description": "<p>课程状态切换，如果status字段没有传值，则默认是课程状态切换，否则为更新状态到status指定（如果课程下存在提过过评分内容，则不允许撤销发布）</p>",
    "name": "courseStatusToggle",
    "group": "Course",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "status",
            "description": "<p>课程状态</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n    \"id\": 1,\n    \"status\": 0\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改课程ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"success\",\n    \"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/CourseStatusToggleEdit.java",
    "groupTitle": "Course"
  },
  {
    "type": "post",
    "url": "/contentViewRecord/add",
    "title": "评论回复全部标记为已读",
    "name": "contentViewRecordAdd",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originId",
            "description": "<p>讨论ID或者公告ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originType",
            "description": "<p>类型，2：讨论回复 4：公告回复</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/ContentViewRecordDataEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/contentViewRecord/modify",
    "title": "单个评论回复已读未读变更",
    "name": "contentViewRecordModify",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originId",
            "description": "<p>来源ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originType",
            "description": "<p>类型，1：讨论话题 2：讨论回复 3：公告话题 4：公告回复</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/ContentViewRecordDataEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/discussion/add",
    "title": "讨论新建",
    "name": "discussionAdd",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题（size=500)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "type",
            "description": "<p>类型，1: 课程里面创建的为普通讨论， 2：小组里面创建的为小组讨论</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupSetId",
            "description": "<p>学习小组集ID(type=1小组集可有可无)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>讨论话题副本的学习小组ID(type=2指定小组)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "fileId",
            "description": "<p>附件ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isPin",
            "description": "<p>是否置顶</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isGrade",
            "description": "<p>是否评分 分配为所有班级时可选</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowComment",
            "description": "<p>0: 禁止评论, 1: 允许评论</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "score",
            "description": "<p>满分值 (isGrade=1时必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "gradeType",
            "description": "<p>评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分 (isGrade=1时必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "gradeSchemeId",
            "description": "<p>评分方案，评分方式为 3: GPA, 4: Letter时 需要关联 cos_discussion.grade_scheme_id=cos_grade_scheme.id(isGrade=1时必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "status",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assignmentGroupId",
            "description": "<p>作业小组ID (isGrade=1时必填) 新增/更新表cos_assignment_group_item. origin_type=2  并且origin_id=讨论ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": false,
            "field": "assign",
            "description": "<p>分配</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.limitTime",
            "description": "<p>规定截止日期</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.startTime",
            "description": "<p>可以参加测验开始日期</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.endTime",
            "description": "<p>可以参加测验结束日期</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "assign.assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级) 3用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.assignId",
            "description": "<p>分配目标ID 班级Id或者用户Id，分配类型为所有时为空</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionDataEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "get",
    "url": "/discussionConfig/get",
    "title": "讨论设置-学生设置详情",
    "name": "discussionConfigGet",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户Id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.allowStudentCreateDiscussion",
            "description": "<p>学生可创建讨论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.allowStudentEditDiscussion",
            "description": "<p>学生可编辑/删除自己话题和评论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.allowDiscussionAttachFile",
            "description": "<p>允许学生讨论上传附件</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionConfigDataQuery.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/discussionConfig/modify",
    "title": "讨论设置-学生设置",
    "name": "discussionConfigModify______",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>讨论配置ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowStudentCreateDiscussion",
            "description": "<p>学生可创建讨论</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowStudentEditDiscussion",
            "description": "<p>学生可编辑/删除自己话题和评论</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowDiscussionAttachFile",
            "description": "<p>允许学生讨论上传附件</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionConfigDataEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/discussion/copy/edit",
    "title": "讨论复制",
    "description": "<p>讨论复制</p>",
    "name": "discussionCopy",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>讨论Id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionCopyEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/discussion/deletes",
    "title": "讨论删除",
    "name": "discussionDeletes",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "[1,2,3]",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例:",
          "content": "{\n\"code\": 200,\n\"entity\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionDataEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "get",
    "url": "/discussion/get",
    "title": "讨论详情",
    "name": "discussionGet",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>讨论ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.content",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "entity.type",
            "description": "<p>类型，1: 普通讨论， 2： 小组讨论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.studyGroupSetId",
            "description": "<p>学习小组集ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isPin",
            "description": "<p>是否置顶</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pinSeq",
            "description": "<p>置顶顺序</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.allowComment",
            "description": "<p>评论，0: 禁止评论, 1: 允许评论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.status",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.updateTime",
            "description": "<p>更新时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity.attachmentFile",
            "description": "<p>讨论话题附件</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.attachmentFile.fileName",
            "description": "<p>附件名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.attachmentFile.fileUrl",
            "description": "<p>附件url</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.assign",
            "description": "<p>分配数组</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.assign.limitTime",
            "description": "<p>规定截止日期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.assign.startTime",
            "description": "<p>可以参加测验开始日期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.assign.endTime",
            "description": "<p>可以参加测验结束日期</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.assign.assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级) 3用户</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.ssign.assignId",
            "description": "<p>分配目标ID 班级Id或者用户Id，分配类型为所有时为空</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.author",
            "description": "<p>讨论话题作者</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.author.nickname",
            "description": "<p>讨论话题作者昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.author.avatarUrl",
            "description": "<p>讨论话题作者头像</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.readCountDTO",
            "description": "<p>评论总数未读数统计</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.readCountDTO.replyTotal",
            "description": "<p>评论总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.readCountDTO.replyReadTotal",
            "description": "<p>评论已读总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.readCountDTO.replyNotReadTotal",
            "description": "<p>评论未读总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.sectionList",
            "description": "<p>会话数组 [{班级名称：用户个数}]</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionList.sectionId",
            "description": "<p>会话班级ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.sectionList.sectionName",
            "description": "<p>会话班级名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionList.userCount",
            "description": "<p>会话班级用户个数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userCount",
            "description": "<p>用户总数，所有班级时才有此字段</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.maxDueTime",
            "description": "<p>讨论最晚截止时间</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionDataQuery.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "get",
    "url": "/discussion/list",
    "title": "讨论列表",
    "name": "discussionList",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": false,
            "field": "isRead",
            "description": "<p>是否已读 0:未读,1:已读，2:所有</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "title",
            "description": "<p>讨论标题</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.content",
            "description": "<p>讨论内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "entity.type",
            "description": "<p>类型，1: 普通讨论， 2： 小组讨论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.studyGroupSetId",
            "description": "<p>学习小组集ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isPin",
            "description": "<p>是否置顶</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isGrade",
            "description": "<p>是否评分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.enableComment",
            "description": "<p>评论，0: 禁止评论, 1: 允许评论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.status",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.lastActiveTime",
            "description": "<p>最后一次对此讨论的评论时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.allowComment",
            "description": "<p>允许评论</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity.readCountDTO",
            "description": "<p>评论数统计</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.readCountDTO.replyTotal",
            "description": "<p>评论总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.readCountDTO.replyReadTotal",
            "description": "<p>评论已读总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.readCountDTO.replyNotReadTotal",
            "description": "<p>评论未读总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.sectionList",
            "description": "<p>会话数组 [{班级名称：用户个数}]</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.sectionList.sectionId",
            "description": "<p>会话班级ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.sectionList.sectionName",
            "description": "<p>会话班级名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.sectionList.userCount",
            "description": "<p>会话班级用户个数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.userCount",
            "description": "<p>用户总数，所有班级时才有此字段</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.warningVO",
            "description": "<p>讨论时间提醒对象</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.warningVO.warningType",
            "description": "<p>提醒类型 0:不显示 1:Not available until xxx xxx xx,xxxx at xx:xx am/pm 例如 Not available until Jan 4, 2019 at 8:59am 2:Available until xxx xxx xx,xxxx at xx:xx am/pm 例如 Available until Jan 4, 2019 at 8:59am 3:Was locked at xxx xx at xx：xx am/pm 例如 Was locked at Dec 16 at 11:59 pm</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.warningVO.warningTime",
            "description": "<p>提醒时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.maxLimitTime",
            "description": "<p>提醒最大截止时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.groupId",
            "description": "<p>小组ID 学生如果有此字段则跳转到小组详情页</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionDataQuery.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/discussion/modify",
    "title": "讨论修改",
    "name": "discussionModify____",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>讨论ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题（size=500)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "type",
            "description": "<p>类型，1: 普通讨论， 2：小组讨论 时需要创建小组讨论副本</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupSetId",
            "description": "<p>学习小组集ID(type=2指定小组集)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>讨论话题副本的学习小组ID(type=2指定小组)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "attachmentFileId",
            "description": "<p>附件主键ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "fileId",
            "description": "<p>附件fileId</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isPin",
            "description": "<p>是否置顶</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isGrade",
            "description": "<p>是否评分 分配为所有班级时可选</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowComment",
            "description": "<p>0: 禁止评论, 1: 允许评论</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "score",
            "description": "<p>满分值 (isGrade=1时必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "gradeType",
            "description": "<p>评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分 (isGrade=1时必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "gradeSchemeId",
            "description": "<p>评分方案，评分方式为 3: GPA, 4: Letter时 需要关联 cos_discussion.grade_scheme_id=cos_grade_scheme.id(isGrade=1时必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "status",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assignmentGroupItemID",
            "description": "<p>作业小组ID (isGrade=1时必填) 新增/更新表cos_assignment_group_item. origin_type=2  并且origin_id=讨论ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": false,
            "field": "assign",
            "description": "<p>分配 为所有班级时</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.limitTime",
            "description": "<p>规定截止日期</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.startTime",
            "description": "<p>可以参加测验开始日期</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.endTime",
            "description": "<p>可以参加测验结束日期</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "assign.assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级) 3用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assign.assignId",
            "description": "<p>分配目标ID 班级Id或者用户Id，分配类型为所有时为空</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionDataEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "get",
    "url": "/discussion/pageList",
    "title": "讨论分页",
    "name": "discussionPageList",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": false,
            "field": "isRead",
            "description": "<p>是否已读 0:未读,1:已读，2:所有</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "title",
            "description": "<p>讨论标题</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>页大小</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.total",
            "description": "<p>总结果数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageSize",
            "description": "<p>页大小</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.list",
            "description": "<p>讨论列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.list.content",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "entity.list.type",
            "description": "<p>类型，1: 普通讨论， 2： 小组讨论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.studyGroupSetId",
            "description": "<p>学习小组集ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.list.isPin",
            "description": "<p>是否置顶</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.list.isGrade",
            "description": "<p>是否评分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.list.enableComment",
            "description": "<p>评论，0: 禁止评论, 1: 允许评论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.list.status",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.list.allowComment",
            "description": "<p>允许评论</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.replyTotal",
            "description": "<p>评论总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.replyReadTotal",
            "description": "<p>评论已读总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.replyNotReadTotal",
            "description": "<p>评论未读总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.list.sectionList",
            "description": "<p>会话数组 [{班级名称：用户个数}]</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.sectionList.sectionId",
            "description": "<p>会话班级ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.list.sectionList.sectionName",
            "description": "<p>会话班级名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.sectionList.userCount",
            "description": "<p>会话班级用户个数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.userCount",
            "description": "<p>用户总数，所有班级时才有此字段</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionDataQuery.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/discussionReply/add",
    "title": "讨论回复添加",
    "name": "discussionReplyAdd",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "discussionId",
            "description": "<p>讨论ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>学习小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "discussionReplyId",
            "description": "<p>回复ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "fileId",
            "description": "<p>附件Id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增回复ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionReplyDataEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/discussionReply/deletes",
    "title": "讨论回复刪除",
    "name": "discussionReplyDelete",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例：",
          "content": "[1,2,3]",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>删除ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionReplyDataEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "get",
    "url": "/discussionReply/list",
    "title": "讨论回复列表",
    "name": "discussionReplyList",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "studyGroupId",
            "description": "<p>学习小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "discussionId",
            "description": "<p>讨论ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "content",
            "description": "<p>讨论回复内容或作者名</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": false,
            "field": "isRead",
            "description": "<p>是否已读 0:未读,1:已读，2:所有</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>评论ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.discussionId",
            "description": "<p>关联讨论ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.replyId",
            "description": "<p>回复的回复ID(这个ID表示这是一个回复的回复)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createTime",
            "description": "<p>回复时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.attachmentFileId",
            "description": "<p>回复附件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.fileName",
            "description": "<p>回复附件名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.fileUrl",
            "description": "<p>回复附件URL</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.isRead",
            "description": "<p>0：未读，其他:已读</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>评论用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userNickname",
            "description": "<p>评论用户昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userAvatarUrl",
            "description": "<p>评论用户头像</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionReplyQuery.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/discussionReply/modify",
    "title": "讨论回复修改",
    "name": "discussionReplyModify",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>讨论回复ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "discussionId",
            "description": "<p>讨论ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "attachmentFileId",
            "description": "<p>附件Id主键</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "fileId",
            "description": "<p>附件Id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>回复ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionReplyDataEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/discussion/statusToggle/edit",
    "title": "讨论的 评论开关|发布开关|置顶开关",
    "name": "discussion_______________________",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>讨论ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowComment",
            "description": "<p>评论开关</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "status",
            "description": "<p>发布状态</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isPin",
            "description": "<p>置顶</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>讨论ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/DiscussionBaseEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "get",
    "url": "/userConfig/get",
    "title": "讨论设置-我的设置详情",
    "name": "userConfigGet",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户Id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.allowMarkPostStatus",
            "description": "<p>手动将帖子标记为已读</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.coverColor",
            "description": "<p>前景色，格式: #666666(日历使用)</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/UserConfigDataQuery.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "post",
    "url": "/userConfig/modify",
    "title": "讨论设置-我的设置-将帖子手动标记为已读",
    "name": "userConfigModify______________",
    "group": "Discussion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowMarkPostStatus",
            "description": "<p>手动将帖子标记为已读</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "coverColor",
            "description": "<p>前景色，格式: #666666(日历使用)</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/discussion/UserConfigDataEdit.java",
    "groupTitle": "Discussion"
  },
  {
    "type": "get",
    "url": "/gradeDataQuery/gradeQuery/query",
    "title": "批量、单个评分基础信息",
    "name": "GradeDataQuery",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验',</p>"
          },
          {
            "group": "Parameter",
            "type": "Long",
            "optional": true,
            "field": "originId",
            "description": "<p>任务类型ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "gradeType",
            "description": "<p>单一或批量 1：单一 2：批量</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "originName",
            "description": "<p>任务名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "unG",
            "description": "<p>未评分数量</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "graded",
            "description": "<p>已评分数量</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "score",
            "description": "<p>总分值</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "releaseType",
            "description": "<p>个人或小组类型 1:个人 2小组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "SubmissionType",
            "description": "<p>作业内容类型 如果是讨论跟测验是没有的</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>评分的基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验',</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.originId",
            "description": "<p>任务类型ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.gradeType",
            "description": "<p>单一或批量 1：单一 2：批量</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.originName",
            "description": "<p>任务名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.unGGraded",
            "description": "<p>未评分/已评分数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.close",
            "description": "<p>时间列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.close.assignId",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.close.assignType",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.close.courseId",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.close.createTime",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.close.createUserId",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.close.endTime",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.close.id",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.close.limitTime",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.close.originId",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.close.originType",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.close.updateTime",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.close.updateUserId",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.score",
            "description": "<p>总分值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.releaseType",
            "description": "<p>个人或小组类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.assignmentGroupItemId",
            "description": "<p>作业组item</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.submissionType",
            "description": "<p>作业内容类型 如果是讨论跟测验是没有的</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.groupList",
            "description": "<p>小组信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.groupList.Id",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.groupList.groupName//",
            "description": "<p>小组名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/BatchGradeDataQuery.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "get",
    "url": "/gradeDataQuery/batchDownLoadFile/direct",
    "title": "单个文件、批量文件下载",
    "name": "batchDownLoadFile",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验',</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originId",
            "description": "<p>任务类型ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "studyGroupId",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "submitTime",
            "description": "<p>提交时间</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "fileRoute",
            "description": "<p>文件路径</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>内容基本信息</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/BatchDownLoadFileDataQuery.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "get",
    "url": "/gradeDataQuery/gradeAssignmentReply/query",
    "title": "评分查询内容信息",
    "name": "gradeAssignmentReply",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验',</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "originId",
            "description": "<p>任务类型ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "submitTime",
            "description": "<p>提交时间</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>内容基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.content",
            "description": "<p>内容{包括文本内容、URL内容}</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.replyType",
            "description": "<p>1: 文本　2: 文件 3: URL 4: 媒体',</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.attachments",
            "description": "<p>附件List</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.attachments.id",
            "description": "<p>文件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.attachments.fileName",
            "description": "<p>文件名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.attachments.fileType",
            "description": "<p>文件类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.attachments.fileSize",
            "description": "<p>文件大小</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.attachments.fileUrl",
            "description": "<p>文件路径</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.disEntity",
            "description": "<p>讨论lits</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.fileName",
            "description": "<p>文件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.fileUrl",
            "description": "<p>评论附件地址</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.userNickname",
            "description": "<p>用户昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.userAvatarFileId",
            "description": "<p>用户头像文件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.userAvatarName",
            "description": "<p>用户头像名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.userAvatarUrl",
            "description": "<p>用户头像URL</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.isRead",
            "description": "<p>是否已读 0：未读：其他已读</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.discussionId",
            "description": "<p>讨论ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.studyGroupId",
            "description": "<p>学习小组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.discussionReplyId",
            "description": "<p>讨论回复ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.treeId",
            "description": "<p>树ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.disEntity.content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.quizEntity",
            "description": "<p>内容基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.quizEntity.quizObjective",
            "description": "<p>测验选项题</p>"
          },
          {
            "group": "Success 200",
            "optional": false,
            "field": "Long",
            "description": "<p>entity.quizEntity.quizObjective.id;//试题ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.quizEntity.quizObjective.questionId",
            "description": "<p>;//问题ID',</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizObjective.questionsTitle",
            "description": "<p>;//试题题目</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.quizEntity.optionList",
            "description": "<p>选项 排序决定ABCD</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.quizEntity.optionList.optionId",
            "description": "<p>选项ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.quizEntity.optionList.quizQuestionRecordId",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.quizEntity.optionList.questionOptionId",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.optionList.code",
            "description": "<p>题干中占位符</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.optionList.content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.optionList.explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.optionList.isCorrect",
            "description": "<p>选择题：是否为正确选项</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.optionList.seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.optionList.isChoice",
            "description": "<p>是否选中</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.quizObjective.seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.quizObjective.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.quizObjective.gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.quizEntity.quizObjective.quizRecordId",
            "description": "<p>测验记录ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizObjective.correctComment",
            "description": "<p>正确提示</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizObjective.wrongComment",
            "description": "<p>错误提示</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizObjective.generalComment",
            "description": "<p>通用提示</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.questionsId",
            "description": "<p>试题ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.questionsTitle",
            "description": "<p>试题题目</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.answer",
            "description": "<p>我的答案</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.quizRecordId",
            "description": "<p>测验记录ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.correctComment",
            "description": "<p>正确提示</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.wrongComment",
            "description": "<p>错误提示</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.quizEntity.quizSubjectivity.generalComment",
            "description": "<p>通用提示</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.score",
            "description": "<p>总分</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.quizEntity.gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.quizEntity.userId",
            "description": "<p>提交用户</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.quizEntity.graderId",
            "description": "<p>评分用户</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/GradeAssignmentReplyDataQuery.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "post",
    "url": "/gradeCommentEdit/modify",
    "title": "删除评分信息",
    "description": "<p>删除评分信息</p>",
    "name": "gradeCommentEdit",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>评分</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.id",
            "description": "<p>评分内容ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/GradeCommentModify.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "get",
    "url": "/gradeDataQuery/gradeContentInfo/query",
    "title": "单个评分信息",
    "name": "gradeContentInfo",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验',</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "originId",
            "description": "<p>任务类型ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "gradeType",
            "description": "<p>单一或批量 1：单一 2：批量</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "releaseType",
            "description": "<p>个人或小组类型</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>内容基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.originState",
            "description": "<p>任务状态 On time / Late / Missing</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.submittedTime",
            "description": "<p>提交时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.attemptTook",
            "description": "<p>答题用时 只有任务类型是测验的时候显示</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.submissionToView",
            "description": "<p>作业跟讨论 可以提交多次 显示多次提交时间 ；测验不显示【屏蔽】|，|</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/OneByOneGradeDataQuery.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "get",
    "url": "/gradeDataQuery/gradeOneStuGroupQuery/query",
    "title": "查询单个任务的组、学生list",
    "name": "gradeOneStuGroupQuery",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": true,
            "field": "originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验',</p>"
          },
          {
            "group": "Parameter",
            "type": "Long",
            "optional": true,
            "field": "originId",
            "description": "<p>任务类型ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": true,
            "field": "releaseType",
            "description": "<p>个人或小组类型 1:个人 2小组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "sequence",
            "description": "<p>排序：1:按提交时间 2:按照名称音序排序</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": true,
            "field": "gradedStatus",
            "description": "<p>评分状态 2 全部 0未评分 1已评分</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>查询单个任务的组、学生list</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.groupList",
            "description": "<p>组</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.groupList.id",
            "description": "<p>组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.groupList.groupName",
            "description": "<p>组名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.groupList.studentName",
            "description": "<p>学生名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.groupList.studentFile",
            "description": "<p>学生头像</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.studentList",
            "description": "<p>学生list</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.studentList.id",
            "description": "<p>学生ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.studentList.studentName",
            "description": "<p>学生名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.studentList.studentFile",
            "description": "<p>学生头像</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/GradeOneStuGroupDataQuery.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "get",
    "url": "/gradeDataQuery/gradeStatisticsQuery/query",
    "title": "批量、单个评分统计信息",
    "name": "gradeStatisticsQuery",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验',</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "originId",
            "description": "<p>任务类型ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "releaseType",
            "description": "<p>个人或小组类型</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>批量、单个评统计信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.group",
            "description": "<p>有多少组</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.students",
            "description": "<p>有多少学生</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.unG",
            "description": "<p>未评分</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.graded",
            "description": "<p>已评分</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.unSub",
            "description": "<p>未提交</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.submitted",
            "description": "<p>已提交</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.pointsPossible",
            "description": "<p>满分分值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.averageScore",
            "description": "<p>平均分</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.highScore",
            "description": "<p>最高分</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.lowScore",
            "description": "<p>最低分</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.totalStu",
            "description": "<p>统计学生数量</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/BatchGradedDataInfoQuery.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "get",
    "url": "/gradeDataQuery/gradedCommentDataQuery/query",
    "title": "获取评分评论内容信息",
    "name": "gradedCommentDataQuery",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验',</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "originId",
            "description": "<p>任务类型ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>内容基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.originId",
            "description": "<p>来源ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.content",
            "description": "<p>评论内容</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.createTime",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>评论用户</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userFile",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/GradeCommentDataQuery.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "post",
    "url": "/gradedCommentEdit/add",
    "title": "添加评分内容信息",
    "description": "<p>添加评分内容信息</p>",
    "name": "gradedCommentEdit",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "assignmentGroupItemId",
            "description": "<p>作业组item</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originId",
            "description": "<p>来源ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>评论内容</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "isSendGroupUser",
            "description": "<p>小组作业时，评论是否组成员可见</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "releaseType",
            "description": "<p>个人或小组类型</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "gradeType",
            "description": "<p>单一或批量 1：单一 2：批量</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>评论用户</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>评分内容</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/GradeCommentDataEdit.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "post",
    "url": "/gradedEdit/add",
    "title": "添加评分信息",
    "description": "<p>添加评分信息</p>",
    "name": "gradedEdit",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "assignmentGroupItemId",
            "description": "<p>作业组item</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originId",
            "description": "<p>来源ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>评论内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "userId",
            "description": "<p>评论用户</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "isSendGroupUser",
            "description": "<p>小组作业时，评论是否组成员可见</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "score",
            "description": "<p>总分</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "graderId",
            "description": "<p>评分用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "gradeType",
            "description": "<p>单一或批量 1：单一 2：批量</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "releaseType",
            "description": "<p>个人或小组类型</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "isOverWrite",
            "description": "<p>是否覆盖之前的评分 0不覆盖  1覆盖</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "isSetLowestScore",
            "description": "<p>设置最低评分 0不设置  1设置</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>评分</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/GradedDataEdit.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "get",
    "url": "/gradeDataQuery/myGradeBook/query",
    "title": "查询我的评分信息",
    "name": "myGradeBookSearch",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>内容基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.id",
            "description": "<p>评分ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "originId",
            "description": "<p>;任务ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "originType",
            "description": "<p>; 任务类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>题目</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.submissionStatus",
            "description": "<p>提交状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.scores",
            "description": "<p>得分</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.byPercentLetter",
            "description": "<p>比率</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.ranking",
            "description": "<p>排名</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.gradeCount",
            "description": "<p>评论总数</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.type",
            "description": "<p>类型：作业、讨论、测验</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.dueTime",
            "description": "<p>截止时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.subTime",
            "description": "<p>提交时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.closeTime",
            "description": "<p>关闭时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.missionGroup",
            "description": "<p>所属任务组-名称</p>"
          },
          {
            "group": "Success 200",
            "type": "string",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/MyGradeBookDataQuery.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "post",
    "url": "/scoreModify/modify",
    "title": "修改测验评分",
    "description": "<p>修改测验评分</p>",
    "name": "scoreModify",
    "group": "GradeGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": true,
            "field": "originId",
            "description": "<p>来源ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Long",
            "optional": true,
            "field": "userId",
            "description": "<p>评论用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Long",
            "optional": true,
            "field": "studyGroupId",
            "description": "<p>学习小组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "score",
            "description": "<p>分值</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>评分</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.id",
            "description": "<p>评分ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/GradegScoreModify.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "get",
    "url": "/gradeDataQuery/userCourseQuery/search",
    "title": "查询用户下的课程",
    "name": "userCourseQuery",
    "group": "GradeGroup",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>内容基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/UserCourseDataQuery.java",
    "groupTitle": "GradeGroup"
  },
  {
    "type": "get",
    "url": "/section/percentScore/query",
    "title": "班级任务评分百分比查询",
    "description": "<p>如果originType=1是作业的话，判断isIncludeGrade字段是否计入总分 1:是 0:否 只对计入总分的学生任务得分情况进行统计</p>",
    "name": "SectionPercentScoreQuery",
    "group": "Grade",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "sectionId",
            "description": "<p>班级ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200请求成功</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>得分列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.score",
            "description": "<p>分数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.percent",
            "description": "<p>得分比例</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户id</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/SectionPercentScoreQuery.java",
    "groupTitle": "Grade"
  },
  {
    "type": "get",
    "url": "/user/participate/query",
    "title": "用户活动查询",
    "description": "<p>用户在课程内活动列表，按时间排序</p>",
    "name": "UserParticipateQuery",
    "group": "Grade",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>活动列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.originType",
            "description": "<p>活动对象， 1. 作业 2. 讨论 3. 测验 6. 公告  10. 协作文档 11. 网络会议</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.originId",
            "description": "<p>活动对象ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.targetName",
            "description": "<p>活动对象名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.operation",
            "description": "<p>活动类型 1. 查看（协作文档） 2. 创建（Wiki page） 3. 编辑（协作文档） 4. 提交（作业答案、测验答案、讨论回复、公告回复） 5. 参与（网络会议）</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.createTime",
            "description": "<p>创建时间</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/UserParticipateQuery.java",
    "groupTitle": "Grade"
  },
  {
    "type": "get",
    "url": "/user/percentScore/query",
    "title": "用户任务评分百分比查询",
    "description": "<p>如果originType=1是作业的话，判断isIncludeGrade字段是否计入总分 1:是 0:否 只对计入总分的学生任务得分情况进行统计</p>",
    "name": "UserPercentScore",
    "group": "Grade",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200请求成功</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>得分列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>任务名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.score",
            "description": "<p>分数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.percent",
            "description": "<p>得分比例（整数表示百分比）</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isIncludeGrade",
            "description": "<p>是否计入总分</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/UserPercentScoreQuery.java",
    "groupTitle": "Grade"
  },
  {
    "type": "get",
    "url": "/user/submissionSummary/query",
    "title": "用户任务提交数据统计查询",
    "description": "<p>查询学生在课程中任务的提交情况。 返回数据按任务提交记录的时间（lastSubmitTime）进行排序， 如果没提交，按照关闭时间排序</p> <h2>提交状态[按时提交(on time)、迟交(late)、缺交(miss)规则]：</h2><pre>  按是否逾期字段判断是否按时提交，  如果否逾期字段为空，就看当前时间是否过了关闭时间，过了就说明是缺交，没过不统计<br>",
    "name": "UserSubmissionSummaryQuery",
    "group": "Grade",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200请求成功</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>统计列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.groupName",
            "description": "<p>任务所属组名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>任务名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.score",
            "description": "<p>分数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.isGraded",
            "description": "<p>是否已评分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.startTime",
            "description": "<p>任务开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.endTime",
            "description": "<p>任务结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.limitTime",
            "description": "<p>任务截止时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.submitTime",
            "description": "<p>任务提交时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.createTime",
            "description": "<p>任务创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.isOverdue",
            "description": "<p>任务提交是否逾期</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/UserSubmissionSummaryQuery.java",
    "groupTitle": "Grade"
  },
  {
    "type": "get",
    "url": "/grade/book/query",
    "title": "成绩册列表GradeBook",
    "description": "<p>判断任务的提交状态：如果is_graded，is_overdue为空，用当前时间与测验结束日期比较， 如果过了结束日期是缺交状态，如果没过结束日期，是未提交状态，is_graded判断是否评分，is_overdue是否逾期提交</p>",
    "name": "gradeBookQuery",
    "group": "Grade",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sectionId",
            "description": "<p>班级id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>内容基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.userList",
            "description": "<p>班级下的学生列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.userList.id",
            "description": "<p>学生id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userList.fullName",
            "description": "<p>用户全称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userList.email",
            "description": "<p>邮箱</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userList.fileUrl",
            "description": "<p>学生头像</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.gradeTestListQuery",
            "description": "<p>分配给班级下的任务以及班级下所有学生的任务列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeTestListQuery.id",
            "description": "<p>任务id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeTestListQuery.originId",
            "description": "<p>来源id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeTestListQuery.originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.gradeTestListQuery.title",
            "description": "<p>任务名</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeTestListQuery.assignmentGroupId",
            "description": "<p>任务组id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.gradeTestListQuery.name",
            "description": "<p>任务组名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.gradeTestListQuery.isIncludeGrade",
            "description": "<p>作业判断是否包含成绩到总分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeTestListQuery.score",
            "description": "<p>任务总分</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.gradeTestListQuery.isGradeAssignment",
            "description": "<p>属于小组还是个人</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.gradeTestListQuery.submitGradeQuery",
            "description": "<p>学生的得分情况列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.gradeTestListQuery.submitGradeQuery.userId",
            "description": "<p>用户id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeTestListQuery.submitGradeQuery.gradeScore",
            "description": "<p>用户当前任务得分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeTestListQuery.submitGradeQuery.isGraded",
            "description": "<p>是否已评分  0：否 1：是</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.gradeTestListQuery.submitGradeQuery.isOverdue",
            "description": "<p>是否逾期提交</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.gradeTestListQuery.submitGradeQuery.endTime",
            "description": "<p>可以参加测验结束日期</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.gradeTestListQuery.assignsList",
            "description": "<p>任务分配的具体信息列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.gradeTestListQuery.assignsList.assignType",
            "description": "<p>任务类型</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.gradeTestListQuery.assignsList.limitTime",
            "description": "<p>规定截止日期</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.gradeTestListQuery.assignsList.startTime",
            "description": "<p>可以参加测验开始日期</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.gradeTestListQuery.assignsList.endTime",
            "description": "<p>可以参加测验结束日期</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/GradeBookQuery.java",
    "groupTitle": "Grade"
  },
  {
    "type": "get",
    "url": "/grade/data/query",
    "title": "评分任务列表GradeTask",
    "description": "<p>评分任务列表GradeTask查询</p>",
    "name": "gradeDataQuery",
    "group": "Grade",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验',</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>目标任务类型标题</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.score",
            "description": "<p>总分</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.graderIdcount",
            "description": "<p>评分用户个数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.unGraderIdcount",
            "description": "<p>未评分用户个数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.plgs",
            "description": "<p>评论个数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.avgScore",
            "description": "<p>平均分</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.isGradeAssignment",
            "description": "<p>属于组或者个人</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.fplb",
            "description": "<p>分配列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.fplb.originId",
            "description": "<p>分配id</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.fplb.endTime",
            "description": "<p>可以参加测验结束日期</p>"
          },
          {
            "group": "Success 200",
            "type": "date",
            "optional": false,
            "field": "entity.fplb.limitTime",
            "description": "<p>规定截止日期</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.fplb.startTime",
            "description": "<p>可以参加测验开始日期</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/GradeDataQuery.java",
    "groupTitle": "Grade"
  },
  {
    "type": "get",
    "url": "/grade/userBook/query",
    "title": "单个学生的成绩统计概要信息",
    "description": "<p>单个学生的成绩统计概要信息</p>",
    "name": "gradeUserBookQuery",
    "group": "Grade",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userId",
            "description": "<p>学生id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sectionId",
            "description": "<p>班级id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>内容基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.userByIdVo",
            "description": "<p>学生的基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "entity.userByIdVo.id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userByIdVo.fullname",
            "description": "<p>用户全称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userByIdVo.email",
            "description": "<p>邮箱</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userByIdVo.fileUrl",
            "description": "<p>学生头像路径</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.userByIdVo.overdue",
            "description": "<p>逾期提交</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.userByIdVo.unOverdue",
            "description": "<p>未逾期提交</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.userByIdVo.gradeScore",
            "description": "<p>用户下所有任务的得分</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.userByIdVo.score",
            "description": "<p>用户下所有任务的总分</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.userByIdVo.proportionScore",
            "description": "<p>得分/总分的比例</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.ranking",
            "description": "<p>学生当前班级排名</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.taskToTheEnd",
            "description": "<p>任务得分比例从高到底</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.taskToTheEnd.title",
            "description": "<p>任务名</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.askToTheEnd.gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.taskToTheEnd.score",
            "description": "<p>总分</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.taskToTheEnd.proportionScore",
            "description": "<p>比例</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.taskToTheEnd.originId",
            "description": "<p>来源ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.taskToTheEnd.originType",
            "description": "<p>任务类型： 1: 作业 2: 讨论 3: 测验</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.taskLowToHigh",
            "description": "<p>任务得分比例从低到高</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.taskLowToHigh.title",
            "description": "<p>任务名</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.taskLowToHigh.gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.taskLowToHigh.score",
            "description": "<p>总分</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.taskLowToHigh.proportionScore",
            "description": "<p>比例</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "entity.missQuery",
            "description": "<p>缺交个数</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/GradeUserQuery.java",
    "groupTitle": "Grade"
  },
  {
    "type": "get",
    "url": "/section/participate/query",
    "title": "Parcitipation图表统计",
    "description": "<p>班级用户在课程内活动列表</p>",
    "name": "sectionParticipateQuery",
    "group": "Grade",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "sectionId",
            "description": "<p>班级ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>内容基本信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.userParticipateList",
            "description": "<p>活动列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userParticipateList.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userParticipateList.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userParticipateList.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userParticipateList.originType",
            "description": "<p>活动对象， 1. 作业 2. 讨论 3. 测验 6. 公告  10. 协作文档 11. 网络会议</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userParticipateList.originTd",
            "description": "<p>活动对象ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userParticipateList.targetName",
            "description": "<p>活动对象名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userParticipateList.operation",
            "description": "<p>活动类型 1. 查看（协作文档） 2. 创建（Wiki page） 3. 编辑（协作文档） 4. 提交（作业答案、测验答案、讨论回复、公告回复） 5. 参与（网络会议）</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.userParticipateList.createTime",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "entity.userParticipateList.updateTime",
            "description": "<p>修改时间</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/SectionParcitipationQuery.java",
    "groupTitle": "Grade"
  },
  {
    "type": "get",
    "url": "/section/submissionSummary/query",
    "title": "班级下的学生任务提交情况统计",
    "description": "<p>查询班级学生在课程中任务的提交情况。返回数据按任务开始的时间进行排序</p> <h2>任务显示时，时间规则：</h2>   <pre>学生任务的开始时间，如果有多个开始时间，取最早的那个时间</pre> <h2>提交状态[按时提交(on time)、迟交(late)、缺交(miss)规则]：</h2><pre>  按是否逾期字段判断是否按时提交，  如果否逾期字段为空，就看当前时间是否过了关闭时间，过了就说明是缺交，没过不统计<br>",
    "name": "sectionSubmissionSummaryQuery",
    "group": "Grade",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "sectionId",
            "description": "<p>班级ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200请求成功</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>统计列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>任务id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>任务名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.originType",
            "description": "<p>来源类型，1: 作业 2:讨论 3:测验  4: 文件 5：页面 6：公告</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.originId",
            "description": "<p>来源ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.startTime",
            "description": "<p>任务开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.endTime",
            "description": "<p>任务结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.limitTime",
            "description": "<p>任务截止时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isOverdue",
            "description": "<p>任务提交是否逾期</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/grade/SectionSubmissionSummaryQuery.java",
    "groupTitle": "Grade"
  },
  {
    "type": "post",
    "url": "/module/add",
    "title": "单元添加",
    "description": "<p>单元(Module)添加</p>",
    "name": "moduleAdd",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startTime",
            "description": "<p>开始时间(锁定到该时间开始，null表示不锁定,使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "completeStrategy",
            "description": "<p>完成策略（1：完成所有，2：按顺序完成所有，3：完成任意一个）</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": false,
            "field": "prerequisites",
            "description": "<p>先决条件列表</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "prerequisites.preModuleId",
            "description": "<p>前置单元ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": false,
            "field": "requirements",
            "description": "<p>要求列表</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "requirements.moduleItemId",
            "description": "<p>单元项</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4",
              "5"
            ],
            "optional": false,
            "field": "requirements.strategy",
            "description": "<p>完成策略，1：查看，2：标记为完成，3：提交，4：至少得分，5：参与</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "requirements.leastScore",
            "description": "<p>至少得分，strategy=4时填写</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"vo\": \"1\",\n\"message\": \"success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleDataEdit.java",
    "groupTitle": "Module"
  },
  {
    "type": "post",
    "url": "/moduleItem/moveContent/edit",
    "title": "单元内容项移动",
    "description": "<p>单元内容项移动</p>",
    "name": "moduleContentMove",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "sourceModuleId",
            "description": "<p>来源单元ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "sourceModuleItemId",
            "description": "<p>来源单元内容项ID，不指定则移动来源单元下所有内容到目标单元</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "targetModuleId",
            "description": "<p>目标单元ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "targetModuleItemId",
            "description": "<p>配合strategy使用，当strategy为 BEFORE/AFTER 时用于指定单元内容ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "DEFAULT:0",
              "TOP:1",
              "BOTTOM:2",
              "BEFORE:3",
              "AFTER:4"
            ],
            "optional": false,
            "field": "strategy",
            "description": "<p>移动策略,DEFAULT:目标组无内容时指定，TOP：置顶，BOTTOM：置底，BEFORE：指定之前，AFTER：指定之后</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>移动项ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleItemMoveEdit.java",
    "groupTitle": "Module"
  },
  {
    "type": "post",
    "url": "/module/deletes",
    "title": "单元删除",
    "description": "<p>单元删除</p>",
    "name": "moduleDeletes",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "json",
            "optional": false,
            "field": "data",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例：",
          "content": "1",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>删除单元ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"vo\": \"1\",\n\"message\": \"[1,2]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleDataEdit.java",
    "groupTitle": "Module"
  },
  {
    "type": "post",
    "url": "/moduleItem/add",
    "title": "添加单元内容",
    "description": "<p>添加单元内容</p>",
    "name": "moduleItemAdd",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "moduleId",
            "description": "<p>单元ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "originType",
            "description": "<p>来源类型（1: 作业, 2: 测验，3: 文件 4: 页码 5：讨论）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "originIds",
            "description": "<p>来源ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "indentLevel",
            "description": "<p>缩进级别</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增单元项ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleItemEdit.java",
    "groupTitle": "Module"
  },
  {
    "type": "post",
    "url": "/moduleItem/deletes",
    "title": "删除单元内容",
    "description": "<p>删除单元内容</p>",
    "name": "moduleItemDeletes",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "json",
            "optional": false,
            "field": "ids",
            "description": "<p>内容ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例：",
          "content": "1",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>删除列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": \"[1,2]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleItemEdit.java",
    "groupTitle": "Module"
  },
  {
    "type": "post",
    "url": "/moduleItem/indent/edit",
    "title": "缩进操作",
    "description": "<p>单元内容项缩进操作</p>",
    "name": "moduleItemIndent",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>单元项id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "name",
            "description": "<p>单元项名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "indent",
            "description": "<p>缩进量</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>移动项ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleItemIndentEdit.java",
    "groupTitle": "Module"
  },
  {
    "type": "get",
    "url": "/moduleItem/list",
    "title": "单元小项列表",
    "name": "moduleItemList",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "moduleId",
            "description": "<p>单元ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>单元项内容</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>itemId</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.moduleId",
            "description": "<p>所属单元</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.originType",
            "description": "<p>类型，1: 作业, 2: 讨论，3: 测验 4: 文件 5：页面</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.originId",
            "description": "<p>来源ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.status",
            "description": "<p>状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.indentLevel",
            "description": "<p>缩进级别</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"id\": 3,\n\"moduleId\": 2,\n\"originId\": 1,\n\"originType\": 1,\n\"indentLevel\": 1,\n\"score\": 0,\n\"seq\": 1,\n\"status\": 0,\n\"title\": \"第一章作业\",\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleItemDataQuery.java",
    "groupTitle": "Module"
  },
  {
    "type": "get",
    "url": "/module/list",
    "title": "单元列表",
    "name": "moduleList",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>单元列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.startTime",
            "description": "<p>开始时间(解锁时间)</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "entity.completeStrategy",
            "description": "<p>单元要求完成策略，1：完成所有要求， 2： 按顺序完成所有要求， 3：完成任意一个</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.prerequisites",
            "description": "<p>前置单元</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.prerequisites.name",
            "description": "<p>前置单元名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.status",
            "description": "<p>发布状态</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json 返回值",
          "content": "{\n\"courseId\": 1,\n\"id\": 2,\n\"name\": \"开学第2课\",\n\"seq\": 2,\n\"startTime\": 1544760039000,\n\"status\": 0\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleDataQuery.java",
    "groupTitle": "Module"
  },
  {
    "type": "post",
    "url": "/module/modify",
    "title": "单元修改",
    "description": "<p>单元(Module)修改</p>",
    "name": "moduleModify",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startTime",
            "description": "<p>开始时间(锁定到该时间开始）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "completeStrategy",
            "description": "<p>完成策略（1：完成所有要求， 2： 按顺序完成所有要求， 3：完成任意一个）</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": false,
            "field": "prerequisites",
            "description": "<p>先决条件列表</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "prerequisites.preModuleId",
            "description": "<p>前置单元ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": false,
            "field": "requirements",
            "description": "<p>要求列表</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "requirements.moduleItemId",
            "description": "<p>单元项</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4",
              "5"
            ],
            "optional": false,
            "field": "requirements.require",
            "description": "<p>完成要求，1：查看，2：标记为完成，3：提交，4：至少得分，5：参与</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "requirements.leastScore",
            "description": "<p>至少得分，strategy=4 时填写</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"vo\": \"1\",\n\"message\": \"success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleDataEdit.java",
    "groupTitle": "Module"
  },
  {
    "type": "post",
    "url": "/module/move/edit",
    "title": "单元移动",
    "description": "<p>单元移动</p>",
    "name": "moduleMove",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "moduleIds",
            "description": "<p>排序列表</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>移动单元ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModuleMoveEdit.java",
    "groupTitle": "Module"
  },
  {
    "type": "post",
    "url": "/module/status/edit",
    "title": "单元发布/取消",
    "name": "moduleStatusEdit",
    "group": "Module",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "status",
            "description": "<p>状态 1:发布 0:未发布</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>success</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/module/ModulePublishDataEdit.java",
    "groupTitle": "Module"
  },
  {
    "type": "get",
    "url": "/org2/select/query",
    "title": "注册机构下拉列表(无需登录)",
    "name": "Org2SelectQuery",
    "group": "Org",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>机构列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>机构id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.name",
            "description": "<p>机构名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.treeId",
            "description": "<p>机构树Id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.type",
            "description": "<p>机构类型 1学校 2非学校 { &quot;code&quot;: 200, &quot;entity&quot;: [ { &quot;createTime&quot;: 1551894708000, &quot;createUserId&quot;: 1, &quot;id&quot;: 10, &quot;name&quot;: &quot;Test_Account-4_1&quot;, &quot;parentId&quot;: 8, &quot;sisId&quot;: &quot;sa_006&quot;, &quot;treeId&quot;: &quot;0001000100070002&quot;, &quot;type&quot;: 1, &quot;updateTime&quot;: 1551894708000, &quot;updateUserId&quot;: 1 } ], &quot;message&quot;: &quot;common.success&quot; }</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/org/OrgSelectData2Query.java",
    "groupTitle": "Org"
  },
  {
    "type": "get",
    "url": "/org/list",
    "title": "机构列表",
    "name": "OrgList",
    "group": "Org",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "parentId",
            "description": "<p>上级ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>机构列表</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/org/OrgDataQuery.java",
    "groupTitle": "Org"
  },
  {
    "type": "get",
    "url": "/org/select/query",
    "title": "机构下拉列表",
    "name": "OrgSelectQuery",
    "group": "Org",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>机构列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>机构id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.name",
            "description": "<p>机构名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.treeId",
            "description": "<p>机构树Id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.type",
            "description": "<p>机构类型 1学校 2非学校 { &quot;code&quot;: 200, &quot;entity&quot;: [ { &quot;createTime&quot;: 1551894708000, &quot;createUserId&quot;: 1, &quot;id&quot;: 10, &quot;name&quot;: &quot;Test_Account-4_1&quot;, &quot;parentId&quot;: 8, &quot;sisId&quot;: &quot;sa_006&quot;, &quot;treeId&quot;: &quot;0001000100070002&quot;, &quot;type&quot;: 1, &quot;updateTime&quot;: 1551894708000, &quot;updateUserId&quot;: 1 } ], &quot;message&quot;: &quot;common.success&quot; }</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/org/OrgSelectDataQuery.java",
    "groupTitle": "Org"
  },
  {
    "type": "post",
    "url": "/org/add",
    "title": "机构添加",
    "description": "<p>机构添加</p>",
    "name": "orgAdd",
    "group": "Org",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "size": "..100",
            "optional": false,
            "field": "name",
            "description": "<p>学期名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "parentId",
            "description": "<p>上级ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"message\": \"success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/org/OrgDataEdit.java",
    "groupTitle": "Org"
  },
  {
    "type": "get",
    "url": "/orgAdmin/remove/edit",
    "title": "机构管理删除",
    "name": "orgAdminRemove",
    "group": "Org",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "orgUserId",
            "description": "<p>机构用户id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述 { &quot;code&quot;: 200, &quot;message&quot;: &quot;common.success&quot; }</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/org/OrgAdminDataEdit.java",
    "groupTitle": "Org"
  },
  {
    "type": "post",
    "url": "/org/modify",
    "title": "机构修改",
    "description": "<p>机构修改</p>",
    "name": "orgModify",
    "group": "Org",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>机构id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "size": "..100",
            "optional": false,
            "field": "name",
            "description": "<p>学期名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "parentId",
            "description": "<p>上级ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"message\": \"success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/org/OrgDataEdit.java",
    "groupTitle": "Org"
  },
  {
    "type": "post",
    "url": "/courseUser/associateAndJoin/edit",
    "title": "",
    "name": "CourseUserAssociateAndJoin",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>课程班级邀请码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/UserAssoicateAndJoinEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/deletes",
    "title": "课程用户移除",
    "name": "CourseUserDelete",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/inviteHandle/edit",
    "title": "",
    "description": "<p>用户同意或拒绝课程加入邀请</p>",
    "name": "CourseUserInviteHandle",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>邀请码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>状态码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>状态信息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserInviteHandleEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "get",
    "url": "/courseUser/registering/query",
    "title": "",
    "description": "<p>查询课程发布的用户邀请中信息</p>",
    "name": "CourseUserRegisteringQuery",
    "group": "People",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>邀请信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.courseName",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionId",
            "description": "<p>班级ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.sectionName",
            "description": "<p>班级名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.roleId",
            "description": "<p>角色ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.roleName",
            "description": "<p>角色名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.code",
            "description": "<p>邀请码</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserRegisteringQuery.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/resendInvite",
    "title": "重新给用户发送邀请信息",
    "name": "CourseUserResendInvite",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/ResendCourseInvitingEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/role/edit",
    "title": "课程用户角色修改",
    "name": "CourseUserRoleEdit",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "roleId",
            "description": "<p>角色ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserRoleEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/sectionChange/edit",
    "title": "课程用户班级编辑",
    "name": "CourseUserSectionChange",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isAdd",
            "description": "<p>0: 移除 1: 添加</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "sectionId",
            "description": "<p>班级</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserSectionChangeEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/section/edit",
    "title": "用户班级增加\\删除",
    "description": "<p>班级列表为用户变更后的班级，（用户存在班级中时保留，不存在将用户作为学生添加到班级）</p>",
    "name": "CourseUserSectionEdit",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "sectionIds",
            "description": "<p>班级ID列表</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>错误消息/用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserSectionEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/activeToggle/edit",
    "title": "用户启用,禁用操作",
    "name": "courseUserActiveToggle",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "activeStatus",
            "description": "<p>激活状态</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserActiveToggleEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/add",
    "title": "课程用户添加",
    "description": "<p>课程用户添加，可以通过用户邮箱、SIS ID、登录ID(username)来添加，如果用户在系统中已存在，邀请只需要提交userId 如果用户尚不存在系统中，则需要email(邮箱邀请),sisId(SIS ID邀请), username(登录ID邀请)，新用户必须上传nickname</p>",
    "name": "courseUserAdd",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "sectionId",
            "description": "<p>班级</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "roleId",
            "description": "<p>角色</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": false,
            "field": "invites",
            "description": "<p>邀请列表</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "invites.inviteType",
            "description": "<p>邀请方式, 1: 邮箱 2: 登录ID 3: sisId</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "invites.email",
            "description": "<p>用户邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "invites.loginId",
            "description": "<p>用户loginId</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "invites.sisId",
            "description": "<p>SIS ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "invites.nickname",
            "description": "<p>新用户昵称（新用户必填）</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>成功邀请用户数量</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/improve/edit",
    "title": "临时用户信息完善",
    "description": "<p>将临时用户注册称正式用户</p>",
    "name": "courseUserImproveEdit",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>邀请code</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "language",
            "description": "<p>语言</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserInfoImprove.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/inviteInfo/query",
    "title": "用户邀请",
    "description": "<p>通过邮箱点击跳转链接</p>",
    "name": "courseUserInviteQuery",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>邀请code</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": "<p>邀请信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.registryStatus",
            "description": "<p>注册状态</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.isRegistering",
            "description": "<p>是否临时用户</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/UserInviteInfoQuery.java",
    "groupTitle": "People"
  },
  {
    "type": "get",
    "url": "/course/user/query",
    "title": "课程用户查询",
    "description": "<p>查询课程下用户</p>",
    "name": "courseUserQuery",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "roleId",
            "description": "<p>角色</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "name",
            "description": "<p>名称（登陆名或昵称模糊查询）</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.sisId",
            "description": "<p>SIS ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.username",
            "description": "<p>登录名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.nickname",
            "description": "<p>昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.avatarFileUrl",
            "description": "<p>头像地址</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.fullName",
            "description": "<p>用户全名</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isActive",
            "description": "<p>是否激活</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.sectionUserDetailVos",
            "description": "<p>用户所属班级信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "entity.sectionUserDetailVos.registryStatus",
            "description": "<p>在班级注册状态, 1: 已邀请　2: 已加入</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionUserDetailVos.registryOrigin",
            "description": "<p>在班级注册来源, 1: 管理员添加 2: 邀请加入　3: 自行加入</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionUserDetailVos.createTime",
            "description": "<p>在班级注册时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionUserDetailVos.sectionId",
            "description": "<p>班级</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionUserDetailVos.sectionName",
            "description": "<p>班级名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionUserDetailVos.roleId",
            "description": "<p>角色</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionUserDetailVos.roleName",
            "description": "<p>角色名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.lastActivity",
            "description": "<p>最近活跃时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.totalActivity",
            "description": "<p>总活跃时间（暂时不展示该字段）</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserDetailQuery.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/sectionAdd/edit",
    "title": "",
    "description": "<p>将用户添加到班级,角色为学生</p>",
    "name": "courseUserSectionAdd",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "sectionId",
            "description": "<p>班级</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserAddSectionEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/courseUser/sectionRemove/edit",
    "title": "",
    "description": "<p>移除班级用户,用户必须至少保留一个班级</p>",
    "name": "courseUserSectionRemove",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "sectionId",
            "description": "<p>班级</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserRemoveSectionEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "get",
    "url": "/role/courseUserNumber/query",
    "title": "角色列表及个角色课程下用户数量",
    "name": "roleSectionUserNumberQuery",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "roleId",
            "description": "<p>角色ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "roleName",
            "description": "<p>角色名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "userNumber",
            "description": "<p>用户数量</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/SectionRoleAndUserNumberQuery.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/userJoinPending/add",
    "title": "用户接受和拒接邀请接口",
    "description": "<p>用户接受和拒接邀请接口</p>",
    "name": "userJoinPendingAdd",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>邀请Id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程Id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户Id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "sectionId",
            "description": "<p>班级Id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "roleId",
            "description": "<p>角色Id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "registryStatus",
            "description": "<p>邀请状态 0：拒接；1：接受</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\t\"id\": \"2\",\n\t\"courseId\": \"102\",\n\t\"userId\": \"3\",\n\t\"sectionId\": \"3\",\n\t\"roleId\": \"4\",\n\t\"registryStatus\": \"1\"\n}",
        "type": "json"
      },
      {
        "title": "返回示例:",
        "content": "{\n    \"code\": 200,\n    \"message\": \"2\"\n}",
        "type": "json"
      }
    ],
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserJoinPendingEdit.java",
    "groupTitle": "People"
  },
  {
    "type": "get",
    "url": "/userJoinPending/list",
    "title": "班级邀请信息查询",
    "description": "<p>班级邀请信息查询</p>",
    "name": "userJoinPendingList",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "http://localhost:8080/userJoinPending/list?userId=3",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"entity\": [\n        {\n            \"code\": \"CI429894393886259456\",\n            \"course\": {\n                \"id\": 102,\n                \"name\": \"SkrSkrSkr\"\n            },\n            \"courseId\": 102,\n            \"createTime\": 1553206398000,\n            \"id\": 2,\n            \"pRole\": {\n                \"id\": 4,\n                \"roleName\": \"Student\"\n            },\n            \"publicStatus\": 1,\n            \"roleId\": 4,\n            \"section\": {\n                \"id\": 3,\n                \"name\": \"Skr Skr Skr\"\n            },\n            \"sectionId\": 3,\n            \"sectionUserId\": 41,\n            \"updateTime\": 1553206398000,\n            \"userId\": 3\n        },\n        {\n            \"code\": \"CI430287201413352512\",\n            \"course\": {\n                \"id\": 116,\n                \"name\": \"赵秀非测试\"\n            },\n            \"courseId\": 116,\n            \"createTime\": 1553300050000,\n            \"id\": 3,\n            \"pRole\": {\n                \"id\": 4,\n                \"roleName\": \"Student\"\n            },\n            \"publicStatus\": 1,\n            \"roleId\": 4,\n            \"section\": {\n                \"id\": 19,\n                \"name\": \"赵秀非测试\"\n            },\n            \"sectionId\": 19,\n            \"sectionUserId\": 43,\n            \"updateTime\": 1553300050000,\n            \"userId\": 3\n        }\n    ],\n    \"message\": \"成功\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/CourseUserJoinPendingQuery.java",
    "groupTitle": "People"
  },
  {
    "type": "get",
    "url": "/user/list",
    "title": "用户列表",
    "description": "<p>用户列表查询，更新邮箱、登录名、SIS ID精确查找用户</p>",
    "name": "userList",
    "group": "People",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "emails",
            "description": "<p>逗号分隔邮箱列表</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "sisIds",
            "description": "<p>逗号分隔sisId列表</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "usernames",
            "description": "<p>逗号分隔用户登录名列表</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>用户列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.nickname",
            "description": "<p>昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.username",
            "description": "<p>登录名（登录ID）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.email",
            "description": "<p>邮箱</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.sisId",
            "description": "<p>SIS ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.org",
            "description": "<p>机构</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.org.id",
            "description": "<p>机构ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.org.name",
            "description": "<p>机构名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/user/UserDataQuery.java",
    "groupTitle": "People"
  },
  {
    "type": "post",
    "url": "/question/add",
    "title": "测验问题添加",
    "description": "<p>测验问题添加</p>",
    "name": "questionAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizID",
            "description": "<p>测试ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseID",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "groupID",
            "description": "<p>问题组ID   0值表示没有组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "type",
            "description": "<p>问题类型，1：单选， 2:多选， 3：判断题、4、多项下拉题、5、匹配题、6、简答题、7:文件上传题、8:文本题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "correctComment",
            "description": "<p>正确提示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "wrongComment",
            "description": "<p>错误提示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "generalComment",
            "description": "<p>通用提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "isTemplate",
            "description": "<p>是否为问题模板 0：否， 1:是；</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionBankID",
            "description": "<p>为问题模板时表示题库ID  default value 0</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionTemplateId",
            "description": "<p>问题来源模板  default value 0</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "seq",
            "description": "<p>序号   default value 0 （可以随便填写一个数值就行，后台会自动计算序号）</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "options",
            "description": "<p>选择题（单选、多选、判断题用）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.code",
            "description": "<p>题干中占位符</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.content",
            "description": "<p>选项内容（判断题内容传 True/ False）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "options.isCorrect",
            "description": "<p>选择题：是否为正确选项;0，否；1，是；</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "options.seq",
            "description": "<p>选项序号值</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "matchoptions",
            "description": "<p>匹配题（总数必须为偶数并且相邻的2条记录为一组匹配项）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "matchoptions.content",
            "description": "<p>选项内容（匹配项中的一项为空传【null】)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "matchoptions.explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "matchoptions.seq",
            "description": "<p>选择该项的提示</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"quizId\": \"16\",\n\"courseId\": \"1\",\n\"groupID\": \"2\",\n\"type\": \"1\",\n\"title\": \"名称\",\n\"content\": \"内容\",\n\"correctComment\": \"4\",\n\"wrongComment\": \"5\",\n\"generalComment\": \"6\",\n\"isTemplate\": \"7\",\n\"questionBankID\": \"8\",\n\"questionTemplateId\": \"9\",\n\"score\": \"10\",\n\"seq\": \"11\",\n\"options\":[{\"code\":\"1\",\"content\":\"2\",\"explanation\":\"3\",\"isCorrect\":\"4\",\"seq\":\"5\"}]\n<p>\n<p>\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增测验问题ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionBank/add",
    "title": "题库添加",
    "description": "<p>题库添加</p>",
    "name": "questionBankAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseID",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>题库名称</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionBankEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionBank/deletes",
    "title": "依据题库id删除",
    "description": "<p>题库删除</p>",
    "name": "questionBankDelete",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>题库ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionBankEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "description": "<p>测验问题项查找</p>",
    "name": "questionBankFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>题库ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizItemQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/questionBank/find",
    "title": "题库查找",
    "description": "<p>题库查找</p>",
    "name": "questionBankFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>题库ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionBankQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionBank/modify",
    "title": "题库修改",
    "description": "<p>问题选项修改(非必填参数 ， 如果不修改 ， 可以不传)</p>",
    "name": "questionBankModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>题库ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseID",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>题库名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "createTime",
            "description": "<p>题库创建时间</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": 1,\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionBankEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/question/deletes",
    "title": "测验问题删除",
    "description": "<p>测验问题删除</p>",
    "name": "questionDeletes",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>测验问题ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除测验问题ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"[73,74,75]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/question/find",
    "title": "测验问题查找",
    "description": "<p>测验问题查找</p>",
    "name": "questionFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>测验问题ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionGroup/add",
    "title": "试题组添加",
    "description": "<p>试题组添加</p>",
    "name": "questionGroupAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizID",
            "description": "<p>测试ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>组名</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "eachQuestionScore",
            "description": "<p>每个问题的得分</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pickQuestionNumber",
            "description": "<p>挑选问题个数</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionGroupEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionGroup/deletes",
    "title": "依据试题组id删除",
    "description": "<p>试题组删除</p>",
    "name": "questionGroupDelete",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>试题组ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionGroupEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/questionGroup/find",
    "title": "题库查找",
    "description": "<p>题库查找</p>",
    "name": "questionGroupFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>题库ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionGroupQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/questionGroup/list",
    "title": "试题组列表",
    "description": "<p>依据id查询对应的试题组信息</p>",
    "name": "questionGroupList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID列表</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionGroupQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionGroup/modify",
    "title": "试题组修改",
    "description": "<p>问题选项修改(非必填参数 ， 如果不修改 ， 可以不传)</p>",
    "name": "questionGroupModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>试题组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizID",
            "description": "<p>测试ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>组名</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "eachQuestionScore",
            "description": "<p>每个问题的得分</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pickQuestionNumber",
            "description": "<p>挑选问题个数</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": 1,\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionGroupEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/question/list",
    "title": "测验问题列表",
    "description": "<p>测验问题列表</p>",
    "name": "questionList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>问题ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "http://localhost:8080/question/list?id=16",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除测验问题列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"createTime\": 1552085638000,\n\"createUserId\": 1,\n\"id\": 154,\n\"question\": {\n\"content\": \"<p>45464</p>\",\n\"correctComment\": \"\",\n\"courseId\": 1,\n\"createTime\": 1552085638000,\n\"createUserId\": 1,\n\"generalComment\": \"\",\n\"groupId\": 2,\n\"id\": 211,\n\"isTemplate\": 0,\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"0\",\n\"content\": \"555\",\n\"createTime\": 1552085638000,\n\"createUserId\": 1,\n\"explanation\": \"0\",\n\"id\": 245,\n\"isCorrect\": 0,\n\"questionId\": 211,\n\"seq\": 0,\n\"updateTime\": 1552085638000,\n\"updateUserId\": 1\n}\n],\n\"questionBankId\": 0,\n\"questionTemplateId\": 0,\n\"score\": 5,\n\"seq\": 0,\n\"title\": \"Unnamed quiz\",\n\"type\": 1,\n\"updateTime\": 1552085638000,\n\"updateUserId\": 1,\n\"wrongComment\": \"\"\n},\n\"quizId\": 463,\n\"seq\": 1,\n\"targetId\": 211,\n\"type\": 1,\n\"updateTime\": 1552085638000,\n\"updateUserId\": 1\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionMatchOption/add",
    "title": "匹配问题选项添加",
    "description": "<p>匹配问题选项添加</p>",
    "name": "questionMatchOptionAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionID",
            "description": "<p>问题ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionOptionID",
            "description": "<p>匹配选项，空表示是一个错误匹配项</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "seq",
            "description": "<p>排序</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionMatchOptionEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionMatchOption/deletes",
    "title": "依据匹配问题选项id删除",
    "description": "<p>匹配问题选项删除</p>",
    "name": "questionMatchOptionDelete",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>匹配问题选项ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionMatchOptionEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/questionMatchOption/find",
    "title": "匹配问题选项查找",
    "description": "<p>匹配问题选项查找</p>",
    "name": "questionMatchOptionFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>匹配问题选项ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionMatchOptionQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/questionMatchOption/list",
    "title": "匹配问题选项列表",
    "description": "<p>依据id查询对应的匹配问题选项信息</p>",
    "name": "questionMatchOptionList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionMatchOptionQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionMatchOption/modify",
    "title": "匹配问题选项修改",
    "description": "<p>匹配问题选项修改(非必填参数 ， 如果不修改 ， 可以不传)</p>",
    "name": "questionMatchOptionModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionID",
            "description": "<p>问题ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionOptionID",
            "description": "<p>匹配选项，空表示是一个错误匹配项</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "seq",
            "description": "<p>排序</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": 1,\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionMatchOptionEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/question/modify",
    "title": "测验问题修改",
    "description": "<p>测验问题修改(非必填参数 ， 如果不修改 ， 可以不传)</p>",
    "name": "questionModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>测验问题ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizID",
            "description": "<p>测试ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseID",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "groupID",
            "description": "<p>问题组ID    0值表示没有组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "type",
            "description": "<p>问题类型，1：单选， 2:多选， 3：判断题、4、多项下拉题、5、匹配题、6、简答题、7:文件上传题、8:文本题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "correctComment",
            "description": "<p>正确提示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "wrongComment",
            "description": "<p>错误提示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "generalComment",
            "description": "<p>通用提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "isTemplate",
            "description": "<p>是否为问题模板 0：否， 1:是；</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionBankID",
            "description": "<p>为问题模板时表示题库ID  default value 0</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionTemplateId",
            "description": "<p>问题来源模板  default value 0</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "seq",
            "description": "<p>序号   default value 0 （可以随便填写一个数值就行，后台会自动计算序号）</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "options",
            "description": "<p>选择题（单选、多选、判断题用）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.code",
            "description": "<p>题干中占位符</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.content",
            "description": "<p>选项内容（判断题内容传 True/ False）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "options.isCorrect",
            "description": "<p>选择题：是否为正确选项;0，否；1，是；</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "options.seq",
            "description": "<p>选项序号值</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "matchoptions",
            "description": "<p>匹配题（总数必须为偶数并且相邻的2条记录为一组匹配项）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "matchoptions.content",
            "description": "<p>选项内容（匹配项中的一项为空传【null】)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "matchoptions.explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "matchoptions.seq",
            "description": "<p>选项序号值</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": \"99\",\n\"quizId\": \"16\",\n\"courseId\": \"1\",\n\"groupID\": \"2\",\n\"type\": \"1\",\n\"title\": \"名称\",\n\"content\": \"内容\",\n\"correctComment\": \"4\",\n\"wrongComment\": \"5\",\n\"generalComment\": \"6\",\n\"isTemplate\": \"7\",\n\"questionBankID\": \"8\",\n\"questionTemplateId\": \"9\",\n\"score\": \"10\",\n\"seq\": \"11\",\n\"options\":[{\"code\":\"1\",\"content\":\"2\",\"explanation\":\"3\",\"isCorrect\":\"4\",\"seq\":\"5\"}]\n<p>\n<p>\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改测验问题ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"99\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionOption/add",
    "title": "问题选项添加",
    "description": "<p>问题选项添加</p>",
    "name": "questionOptionAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionID",
            "description": "<p>问题ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>题干中占位符</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>选项内容</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isCorrect",
            "description": "<p>选择题：是否为正确选项;0，否；1，是；</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "seq",
            "description": "<p>排序</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionOptionEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionOption/deletes",
    "title": "问题选项删除",
    "description": "<p>依据问题id删除对应id的问题选项</p>",
    "name": "questionOptionDeletes",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>问题选项ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionOptionEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/questionOption/find",
    "title": "问题选项查找",
    "description": "<p>问题选项查找</p>",
    "name": "questionOptionFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>问题选项ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionOptionQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/questionBank/list",
    "title": "题库列表",
    "description": "<p>依据id查询对应的题库信息</p>",
    "name": "questionOptionList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID列表</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionBankQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/questionOption/list",
    "title": "问题选项列表",
    "description": "<p>问题选项列表</p>",
    "name": "questionOptionList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "ids",
            "description": "<p>ID列表</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionOptionQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/questionOption/modify",
    "title": "问题选项修改",
    "description": "<p>问题选项修改(非必填参数 ， 如果不修改 ， 可以不传)</p>",
    "name": "questionOptionModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>问题选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionID",
            "description": "<p>问题ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>题干中占位符</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>选项内容</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isCorrect",
            "description": "<p>选择题：是否为正确选项;0，否；1，是；</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "createTime",
            "description": "<p>问题选项创建时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "updateTime",
            "description": "<p>问题选项更新时间</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": 1,\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuestionOptionEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quiz/add",
    "title": "添加测验",
    "description": "<p>添加测验</p>",
    "name": "quizAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4"
            ],
            "optional": false,
            "field": "type",
            "description": "<p>类型，1：练习测验(practice quiz)、2：评分测验(graded quiz)、3：评分调查(graded survey)、4：非评分调查(ungraded survey)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assignmentGroupId",
            "description": "<p>作业小组ID（type=2,3）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "score",
            "description": "<p>计分值(type=3，一旦学生参加调查，将自动获得满分)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "allowAnonymous",
            "description": "<p>允许匿名提交(type=3,4)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isShuffleAnswer",
            "description": "<p>是否重组答案（学生测验时答案选项将随机排列）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "timeLimit",
            "description": "<p>时间限制（分钟）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowMultiAttempt",
            "description": "<p>允许多次尝试  1：是；0：否</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "attemptNumber",
            "description": "<p>尝试次数</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "scoreType",
            "description": "<p>如果可以多次尝试，该字段表示最终评分规则：1. 取最高分，2. 最低分， 3：平均分</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": false,
            "field": "showReplyStrategy",
            "description": "<p>显示学生的答题记录（正误）策略，0: 不显示 1：每次提交答案后 2：最后一次提交后</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": false,
            "field": "showAnswerStrategy",
            "description": "<p>显示答案策略，0: 不显示 1：每次提交后 2：最后一次提交后</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": true,
            "field": "showAnswerStartTime",
            "description": "<p>显示正确答案开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": true,
            "field": "showAnswerEndTime",
            "description": "<p>显示正确答案结束时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "showQuestionStrategy",
            "description": "<p>显示问题策略, 0: 全部显示, 1: 每页一个</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isLockRepliedQuestion",
            "description": "<p>回答后是否锁定问题</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isNeedAccessCode",
            "description": "<p>是否需要访问码访问</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "accessCode",
            "description": "<p>访问码（配置后学生测验需要先输入该访问码）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isFilterIP",
            "description": "<p>是否过滤访问IP</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "filterIpAddress",
            "description": "<p>过滤IP地发布状态址（只允许指定 IP 范围的计算机访问测验）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1"
            ],
            "optional": false,
            "field": "version",
            "description": "<p>版本号（预留）,每次更新后版本号增加</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "status",
            "description": "<p>发布状态： 1：已发布；0：未发布</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "totalQuestions",
            "description": "<p>问题总数 前端默认传0，后台自动计算</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "totalScore",
            "description": "<p>总分 前端默认传0，后台自动计算</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "assign",
            "description": "<p>分配</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "assign.assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级), 3: 用户</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": true,
            "field": "assign.limitTime",
            "description": "<p>截至时间</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": true,
            "field": "assign.startTime",
            "description": "<p>可以参加测验开始日期</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": true,
            "field": "assign.endTime",
            "description": "<p>可以参加测验结束日期</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\n\t\"courseId\": \"2\",\n\t\"title\": \"名称\",\n\t\"description\": \"描述\",\n\t\"type\": \"3\",\n\t\"assignmentGroupId\": \"4\",\n\t\"score\": \"5\",\n\t\"allowAnonymous\": \"6\",\n\t\"isShuffleAnswer\": \"7\",\n\t\"timeLimit\": \"8\",\n\t\"allowMultiAttempt\": \"9\",\n\t\"attemptNumber\": \"10\",\n\t\"scoreType\": \"11\",\n\t\"showReplyStrategy\": \"12\",\n\t\"showAnswerStrategy\": \"13\",\n\t\"showAnswerStartTime\": \"2018-12-18 11:31:15\",\n\t\"showAnswerEndTime\": \"2018-12-18 11:31:15\",\n\t\"showQuestionStrategy\": \"14\",\n\t\"isLockRepliedQuestion\": \"15\",\n\t\"isNeedAccessCode\": \"16\",\n\t\"accessCode\": \"17\",\n\t\"isFilterIP\": \"18\",\n\t\"filterIpAddress\": \"19\",\n\t\"version\": \"20\",\n\t\"status\": \"21\",\n\t\"totalQuestions\": \"22\",\n\t\"totalScore\": \"22\",\n\t\"assign\":[{\"assignId\":\"2\",\"assignType\":\"1\",\"originId\":\"2\",\"limitTime\":\"2019-03-07 13:08:24\",\"startTime\":\"2019-03-07 09:08:24\",\"endTime\":\"2019-03-07 13:08:24\"}]\n\n\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增测验ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"159\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quiz/deletes",
    "title": "测验删除",
    "description": "<p>测验删除</p>",
    "name": "quizDeletes",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>测验问题ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除测验问题ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"[49]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "description": "<p>题库查找</p>",
    "name": "quizFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>题库ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizStudentQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizInfor/get",
    "title": "测验信息查找",
    "description": "<p>测验信息查找</p>",
    "name": "quizInforFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>测验ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "http://localhost:8080/quizInfor/get?data=492",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": {\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 427,\n\"originId\": 492,\n\"originType\": 3\n}\n],\n\"createTime\": 1552408640000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 492,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 0,\n\"title\": \"Unnamed quiz\",\n\"totalQuestions\": 0,\n\"totalScore\": 23,\n\"type\": 1,\n\"updateTime\": 1552408640000,\n\"updateUserId\": 1,\n\"version\": 20\n},\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizInforQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizItem/add",
    "title": "测验问题项添加",
    "description": "<p>测验问题项添加</p>",
    "name": "quizItemAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizId",
            "description": "<p>测验ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "type",
            "description": "<p>类别，1：问题，2：问题组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "targetId",
            "description": "<p>问题/问题组ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增内容项ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizItemEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizItem/deletes",
    "title": "测验问题项删除",
    "name": "quizItemDeletes",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "ids",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "[1, 2, 3]",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>删除ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": \"[1, 2, 3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizItemEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizItem/modify",
    "title": "测验问题项修改",
    "description": "<p>测验问题项修改（内容项类型，所属测验禁止修改）</p>",
    "name": "quizItemModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizId",
            "description": "<p>测验ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "type",
            "description": "<p>类别，1：问题，2：问题组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "targetId",
            "description": "<p>问题/问题组ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增内容项ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizItemEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizItem/move/edit",
    "title": "测验内容项移动",
    "name": "quizItemMove",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizId",
            "description": "<p>测验ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "operate",
            "description": "<p>操作类型：1、top:移动到顶部；2、end,移动到底部；4、before 在前</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionType",
            "description": "<p>问题类型：1. 问题 2. 问题组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "moveId",
            "description": "<p>被移动的问题或问题组的ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "beforeId",
            "description": "<p>移动到那个ID前面，如果操作类型是1、2该项填写moveId</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>移动项ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizItemMoveEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizItem/open/edit",
    "title": "学生是否能答题",
    "name": "quizItemOpen",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "accessCode",
            "description": "<p>访问码（配置后学生测验需要先输入该访问码）</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\t\"id\": \"159\"\n\n\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "status",
            "description": "<p>1:允许答题；2：表示处于锁定状态；3：表示访问码错误 4：表示答题次数已经达到限制；5：IP地址不在设定范围内；6：引用模块处于未解锁状态； 7：当前时间未在答题限定时间内；</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n    \"code\": 200,\n    \"message\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizIsOpen.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizItem/show/edit",
    "title": "学生是否能看正误和答案",
    "name": "quizItemShow",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>测验ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "status",
            "description": "<p>1:表示允许看正误和看答案； 2：允许看正误； 3：表示不允许看正误和看答案 4：教师设置不允许看正误,就不能看答案 5:设置每次提交答案后才能看看正误，但还没提交答案</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionRecordShow.java",
    "groupTitle": "Quiz"
  },
  {
    "description": "<p>依据id查询对应id的测验信息</p>",
    "name": "quizList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID列表</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "/quizInfor/list?id=52",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "<p>\n{\"code\":200,\"entity\":[{\"accessCode\":\"17\",\"allowAnonymous\":6,\"allowMultiAttempt\":9,\"assignmentGroupId\":4,\"attemptNumber\":10,\"courseId\":2,\"createTime\":1551110721000,\"createUserId\":2,\"description\":\"描述\",\"filterIpAddress\":\"19\",\"id\":52,\"isFilterIp\":18,\"isLockRepliedQuestion\":15,\"isNeedAccessCode\":16,\"isShuffleAnswer\":7,\"score\":5,\"scoreType\":11,\"showAnswerEndTime\":1545103875000,\"showAnswerStartTime\":1545103875000,\"showAnswerStrategy\":13,\"showQuestionStrategy\":14,\"showReplyStrategy\":12,\"status\":21,\"timeLimit\":8,\"title\":\"名称\",\"totalQuestions\":22,\"totalScore\":23,\"type\":3,\"updateTime\":1551110721000,\"updateUserId\":2,\"version\":20}],\"message\":\"common.success\"}",
          "type": "json"
        }
      ]
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizInforQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quiz/modify",
    "title": "测验修改",
    "description": "<p>测验修改</p>",
    "name": "quizModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>描述</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4"
            ],
            "optional": false,
            "field": "type",
            "description": "<p>类型，1：练习测验(practice quiz)、2：评分测验(graded quiz)、3：评分调查(graded survey)、4：非评分调查(ungraded survey)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "assignmentGroupId",
            "description": "<p>作业小组ID（type=2,3）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "score",
            "description": "<p>计分值(type=3，一旦学生参加调查，将自动获得满分)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "allowAnonymous",
            "description": "<p>允许匿名提交(type=3,4)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isShuffleAnswer",
            "description": "<p>是否重组答案（学生测验时答案选项将随机排列）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "timeLimit",
            "description": "<p>时间限制（分钟）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowMultiAttempt",
            "description": "<p>允许多次尝试  1：是；0：否</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "attemptNumber",
            "description": "<p>尝试次数</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "scoreType",
            "description": "<p>如果可以多次尝试，该字段表示最终评分规则：1. 取最高分，2. 最低分， 3：平均分</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": false,
            "field": "showReplyStrategy",
            "description": "<p>显示学生的答题记录（正误）策略，0: 不显示 1：每次提交答案后 2：最后一次提交后</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1",
              "2"
            ],
            "optional": false,
            "field": "showAnswerStrategy",
            "description": "<p>显示答案策略，0: 不显示 1：每次提交后 2：最后一次提交后</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": true,
            "field": "showAnswerStartTime",
            "description": "<p>显示正确答案开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": true,
            "field": "showAnswerEndTime",
            "description": "<p>显示正确答案结束时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "showQuestionStrategy",
            "description": "<p>显示问题策略, 0: 全部显示, 1: 每页一个</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isLockRepliedQuestion",
            "description": "<p>回答后是否锁定问题</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isNeedAccessCode",
            "description": "<p>是否需要访问码访问</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "accessCode",
            "description": "<p>访问码（配置后学生测验需要先输入该访问码）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isFilterIP",
            "description": "<p>是否过滤访问IP</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "filterIpAddress",
            "description": "<p>过滤IP地发布状态址（只允许指定 IP 范围的计算机访问测验）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1"
            ],
            "optional": false,
            "field": "version",
            "description": "<p>版本号（预留）,每次更新后版本号增加</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "status",
            "description": "<p>发布状态： 1：已发布；0：未发布</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "totalQuestions",
            "description": "<p>问题总数 前端传后端返回值</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "totalScore",
            "description": "<p>总分 前端传后端返回值</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "assign",
            "description": "<p>分配</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "assign.assignType",
            "description": "<p>分配类型，1: 所有， 2：section(班级), 3: 用户</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": true,
            "field": "assign.limitTime",
            "description": "<p>截至时间</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": true,
            "field": "assign.startTime",
            "description": "<p>可以参加测验开始日期</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": true,
            "field": "assign.endTime",
            "description": "<p>可以参加测验结束日期</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\t\"id\": \"159\",\n\t\"courseId\": \"2\",\n\t\"title\": \"名称\",\n\t\"description\": \"描述\",\n\t\"type\": \"3\",\n\t\"assignmentGroupId\": \"4\",\n\t\"score\": \"5\",\n\t\"allowAnonymous\": \"6\",\n\t\"isShuffleAnswer\": \"7\",\n\t\"timeLimit\": \"8\",\n\t\"allowMultiAttempt\": \"9\",\n\t\"attemptNumber\": \"10\",\n\t\"scoreType\": \"11\",\n\t\"showReplyStrategy\": \"12\",\n\t\"showAnswerStrategy\": \"13\",\n\t\"showAnswerStartTime\": \"2018-12-18 11:31:15\",\n\t\"showAnswerEndTime\": \"2018-12-18 11:31:15\",\n\t\"showQuestionStrategy\": \"14\",\n\t\"isLockRepliedQuestion\": \"15\",\n\t\"isNeedAccessCode\": \"16\",\n\t\"accessCode\": \"17\",\n\t\"isFilterIP\": \"18\",\n\t\"filterIpAddress\": \"19\",\n\t\"version\": \"20\",\n\t\"status\": \"21\",\n\t\"totalQuestions\": \"22\",\n\t\"totalScore\": \"22\",\n\t\"assign\":[{\"assignId\":\"2\",\"assignType\":\"1\",\"originId\":\"2\",\"limitTime\":\"2019-03-07 13:08:24\",\"startTime\":\"2019-03-07 09:08:24\",\"endTime\":\"2019-03-07 13:08:24\"}]\n\n\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增测验ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"159\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quiz/pageList",
    "title": "测验列表分页学生端查询",
    "name": "quizPageList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "title",
            "description": "<p>标题（可选填）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>描述（可选填）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID（必填）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageSize",
            "description": "<p>页大小</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "http://localhost:8080//quiz/pageList?courseId=1",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>结果 { &quot;code&quot;: 200, &quot;entity&quot;: { &quot;list&quot;: [ { &quot;accessCode&quot;: &quot;&quot;, &quot;allowAnonymous&quot;: 0, &quot;allowMultiAttempt&quot;: 0, &quot;assigns&quot;: [], &quot;courseId&quot;: 1, &quot;createTime&quot;: 1552593460000, &quot;createUserId&quot;: 1, &quot;description&quot;: &quot;&quot;, &quot;filterIpAddress&quot;: &quot;&quot;, &quot;id&quot;: 622, &quot;isFilterIp&quot;: 0, &quot;isLockRepliedQuestion&quot;: 0, &quot;isNeedAccessCode&quot;: 0, &quot;isShuffleAnswer&quot;: 0, &quot;showAnswerStrategy&quot;: 0, &quot;showQuestionStrategy&quot;: 0, &quot;showReplyStrategy&quot;: 0, &quot;status&quot;: 1, &quot;title&quot;: &quot;Unnamed quiz&quot;, &quot;totalQuestions&quot;: 0, &quot;totalScore&quot;: 23, &quot;type&quot;: 1, &quot;updateTime&quot;: 1552593460000, &quot;updateUserId&quot;: 1, &quot;version&quot;: 20 }, { &quot;accessCode&quot;: &quot;&quot;, &quot;allowAnonymous&quot;: 0, &quot;allowMultiAttempt&quot;: 0, &quot;assigns&quot;: [], &quot;courseId&quot;: 1, &quot;createTime&quot;: 1552590878000, &quot;createUserId&quot;: 1, &quot;description&quot;: &quot;&quot;, &quot;filterIpAddress&quot;: &quot;&quot;, &quot;id&quot;: 595, &quot;isFilterIp&quot;: 0, &quot;isLockRepliedQuestion&quot;: 0, &quot;isNeedAccessCode&quot;: 0, &quot;isShuffleAnswer&quot;: 0, &quot;showAnswerStrategy&quot;: 0, &quot;showQuestionStrategy&quot;: 0, &quot;showReplyStrategy&quot;: 0, &quot;status&quot;: 1, &quot;title&quot;: &quot;1231322&quot;, &quot;totalQuestions&quot;: 0, &quot;totalScore&quot;: 23, &quot;type&quot;: 1, &quot;updateTime&quot;: 1552590878000, &quot;updateUserId&quot;: 1, &quot;version&quot;: 20 } ], &quot;pageIndex&quot;: 1, &quot;pageSize&quot;: 20, &quot;total&quot;: 2 }, &quot;message&quot;: &quot;成功&quot; }</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizStudentQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizInfor/pageList",
    "title": "测验分页信息显示",
    "name": "quizPageList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "title",
            "description": "<p>标题（可选填）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>描述（可选填）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID（必填）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>页大小</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "/quizInfor/pageList?courseId=1",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "<p>\n{\n\"code\": 200,\n\"entity\": {\n\"list\": [\n{\n\"accessCode\": \"cc\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 97,\n\"originId\": 161,\n\"originType\": 3\n}\n],\n\"attemptNumber\": 4,\n\"createTime\": 1551451128000,\n\"description\": \"<p>测试修改2</p>\",\n\"filterIpAddress\": \"\",\n\"id\": 161,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"score\": 34,\n\"scoreType\": 1,\n\"showAnswerEndTime\": 1551628920000,\n\"showAnswerStartTime\": 1551456120000,\n\"showAnswerStrategy\": 2,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 2,\n\"status\": 0,\n\"timeLimit\": 4,\n\"title\": \"测试修改234323\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"type\": 3,\n\"updateTime\": 1551462301000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"endTime\": 1553702400000,\n\"id\": 90,\n\"originId\": 160,\n\"originType\": 3\n}\n],\n\"createTime\": 1551450731000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 160,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 0,\n\"title\": \"ererwer\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551450731000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 87,\n\"originId\": 156,\n\"originType\": 3\n}\n],\n\"createTime\": 1551439963000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 156,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 0,\n\"title\": \"12313\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551439963000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"endTime\": 1551283320000,\n\"id\": 85,\n\"originId\": 154,\n\"originType\": 3,\n\"startTime\": 1549382402000\n}\n],\n\"createTime\": 1551375762000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 154,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 0,\n\"title\": \"rr\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551375762000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 84,\n\"originId\": 153,\n\"originType\": 3\n}\n],\n\"createTime\": 1551375223000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 153,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 1,\n\"title\": \"rreer\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551379732000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 81,\n\"originId\": 150,\n\"originType\": 3\n}\n],\n\"createTime\": 1551373536000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 150,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 0,\n\"title\": \"rtrt\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551380139000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 80,\n\"originId\": 149,\n\"originType\": 3\n}\n],\n\"createTime\": 1551372736000,\n\"description\": \"<p>qweqwe</p>\",\n\"filterIpAddress\": \"\",\n\"id\": 149,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 1,\n\"title\": \"qweqwe\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551375334000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"endTime\": 1551196800000,\n\"id\": 78,\n\"limitTime\": 1551110400000,\n\"originId\": 146,\n\"originType\": 3,\n\"startTime\": 1550592000000\n}\n],\n\"createTime\": 1551366461000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 146,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 1,\n\"title\": \"ww\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551375427000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"endTime\": 1551196800000,\n\"id\": 77,\n\"originId\": 145,\n\"originType\": 3,\n\"startTime\": 1550592000000\n}\n],\n\"createTime\": 1551366447000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 145,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 0,\n\"title\": \"ww\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551366447000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 76,\n\"originId\": 144,\n\"originType\": 3,\n\"startTime\": 1550592000000\n}\n],\n\"createTime\": 1551366425000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 144,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 1,\n\"title\": \"ww\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551375688000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"endTime\": 1551283200000,\n\"id\": 75,\n\"limitTime\": 1551196800000,\n\"originId\": 143,\n\"originType\": 3,\n\"startTime\": 1551110400000\n}\n],\n\"createTime\": 1551363736000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 143,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 0,\n\"title\": \"测试\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551363736000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"17\",\n\"allowAnonymous\": 6,\n\"allowMultiAttempt\": 9,\n\"assignmentGroupId\": 4,\n\"assigns\": [\n{\n\"assignId\": 2,\n\"assignType\": 1,\n\"courseId\": 1,\n\"endTime\": 1546924104000,\n\"id\": 74,\n\"limitTime\": 1546906104000,\n\"originId\": 142,\n\"originType\": 3,\n\"startTime\": 1546823304000\n}\n],\n\"attemptNumber\": 10,\n\"createTime\": 1551359243000,\n\"description\": \"描述\",\n\"filterIpAddress\": \"19\",\n\"id\": 142,\n\"isFilterIp\": 18,\n\"isLockRepliedQuestion\": 15,\n\"isNeedAccessCode\": 16,\n\"isShuffleAnswer\": 7,\n\"score\": 5,\n\"scoreType\": 11,\n\"showAnswerEndTime\": 1545103875000,\n\"showAnswerStartTime\": 1545103875000,\n\"showAnswerStrategy\": 13,\n\"showQuestionStrategy\": 14,\n\"showReplyStrategy\": 12,\n\"status\": 21,\n\"timeLimit\": 8,\n\"title\": \"名称\",\n\"totalQuestions\": 22,\n\"totalScore\": 22,\n\"type\": 3,\n\"updateTime\": 1551359243000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 73,\n\"originId\": 136,\n\"originType\": 3\n}\n],\n\"createTime\": 1551351477000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 136,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 1,\n\"title\": \"123123\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551351477000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 72,\n\"originId\": 135,\n\"originType\": 3\n}\n],\n\"createTime\": 1551351451000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 135,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 0,\n\"title\": \"123123\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551351451000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 71,\n\"originId\": 134,\n\"originType\": 3\n}\n],\n\"createTime\": 1551349880000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 134,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 22,\n\"title\": \"uu\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551349880000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 70,\n\"originId\": 133,\n\"originType\": 3\n}\n],\n\"createTime\": 1551349239000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 133,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 22,\n\"title\": \"12123\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551349239000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"id\": 69,\n\"originId\": 132,\n\"originType\": 3\n}\n],\n\"createTime\": 1551349225000,\n\"description\": \"\",\n\"filterIpAddress\": \"\",\n\"id\": 132,\n\"isFilterIp\": 0,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 0,\n\"isShuffleAnswer\": 0,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 22,\n\"title\": \"12123\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"updateTime\": 1551349225000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"tt\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 1,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"endTime\": 1551110400000,\n\"id\": 68,\n\"limitTime\": 1551024000000,\n\"originId\": 127,\n\"originType\": 3,\n\"startTime\": 1550937600000\n}\n],\n\"attemptNumber\": 7,\n\"createTime\": 1551349086000,\n\"description\": \"<p>1312</p>\",\n\"filterIpAddress\": \"333\",\n\"id\": 127,\n\"isFilterIp\": 1,\n\"isLockRepliedQuestion\": 1,\n\"isNeedAccessCode\": 1,\n\"isShuffleAnswer\": 1,\n\"scoreType\": 2,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 1,\n\"showReplyStrategy\": 0,\n\"status\": 22,\n\"timeLimit\": 2,\n\"title\": \"1213\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"type\": 1,\n\"updateTime\": 1551349086000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"uu\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"endTime\": 1551283200000,\n\"id\": 67,\n\"limitTime\": 1551196800000,\n\"originId\": 117,\n\"originType\": 3,\n\"startTime\": 1551024000000\n}\n],\n\"createTime\": 1551348589000,\n\"description\": \"<p>测试专用1</p>\",\n\"filterIpAddress\": \"123\",\n\"id\": 117,\n\"isFilterIp\": 1,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 1,\n\"isShuffleAnswer\": 1,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 22,\n\"timeLimit\": 7,\n\"title\": \"测试专用1\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"type\": 1,\n\"updateTime\": 1551348589000,\n\"updateUserId\": 2,\n\"version\": 20\n},\n{\n\"accessCode\": \"uu\",\n\"allowAnonymous\": 0,\n\"allowMultiAttempt\": 0,\n\"assigns\": [\n{\n\"assignType\": 1,\n\"courseId\": 1,\n\"endTime\": 1551283200000,\n\"id\": 66,\n\"limitTime\": 1551196800000,\n\"originId\": 116,\n\"originType\": 3,\n\"startTime\": 1551024000000\n}\n],\n\"createTime\": 1551348569000,\n\"description\": \"<p>测试专用1</p>\",\n\"filterIpAddress\": \"123\",\n\"id\": 116,\n\"isFilterIp\": 1,\n\"isLockRepliedQuestion\": 0,\n\"isNeedAccessCode\": 1,\n\"isShuffleAnswer\": 1,\n\"showAnswerStrategy\": 0,\n\"showQuestionStrategy\": 0,\n\"showReplyStrategy\": 0,\n\"status\": 22,\n\"timeLimit\": 7,\n\"title\": \"测试专用1\",\n\"totalQuestions\": 2,\n\"totalScore\": 23,\n\"type\": 1,\n\"updateTime\": 1551348569000,\n\"updateUserId\": 2,\n\"version\": 20\n}\n],\n\"pageIndex\": 1,\n\"pageSize\": 20,\n\"total\": 25\n},\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizInforQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizItem/list",
    "title": "测验问题项列表",
    "description": "<p>依据id查询对应的测验问题项信息</p>",
    "name": "quizQuestionList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "quizId",
            "description": "<p>测验ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "accessCode",
            "description": "<p>访问码 (如果没有设置可以为空)</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "http://localhost:8080/quizItem/list?quizId=16",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551455180000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 105,\n\"isCorrect\": 4,\n\"questionId\": 91,\n\"seq\": 5,\n\"updateTime\": 1551455180000,\n\"updateUserId\": 2\n}\n],\n\"question\": {\n\"content\": \"内容\",\n\"correctComment\": \"4\",\n\"courseId\": 1,\n\"createTime\": 1551455180000,\n\"createUserId\": 2,\n\"generalComment\": \"6\",\n\"groupId\": 2,\n\"id\": 91,\n\"isTemplate\": 7,\n\"questionBankId\": 8,\n\"questionTemplateId\": 9,\n\"score\": 10,\n\"seq\": 11,\n\"title\": \"名称\",\n\"type\": 1,\n\"updateTime\": 1551455180000,\n\"updateUserId\": 2,\n\"wrongComment\": \"5\"\n}\n},\n{\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551455233000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 106,\n\"isCorrect\": 4,\n\"questionId\": 93,\n\"seq\": 5,\n\"updateTime\": 1551455233000,\n\"updateUserId\": 2\n}\n],\n\"question\": {\n\"content\": \"内容\",\n\"correctComment\": \"4\",\n\"courseId\": 1,\n\"createTime\": 1551455233000,\n\"createUserId\": 2,\n\"generalComment\": \"6\",\n\"groupId\": 2,\n\"id\": 93,\n\"isTemplate\": 7,\n\"questionBankId\": 8,\n\"questionTemplateId\": 9,\n\"score\": 10,\n\"seq\": 11,\n\"title\": \"名称\",\n\"type\": 1,\n\"updateTime\": 1551455233000,\n\"updateUserId\": 2,\n\"wrongComment\": \"5\"\n}\n},\n{\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551455658000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 107,\n\"isCorrect\": 4,\n\"questionId\": 94,\n\"seq\": 5,\n\"updateTime\": 1551455658000,\n\"updateUserId\": 2\n}\n],\n\"question\": {\n\"correctComment\": \"4\",\n\"courseId\": 1,\n\"createTime\": 1551455658000,\n\"createUserId\": 2,\n\"generalComment\": \"6\",\n\"groupId\": 2,\n\"id\": 94,\n\"isTemplate\": 7,\n\"questionBankId\": 8,\n\"questionTemplateId\": 9,\n\"score\": 10,\n\"seq\": 11,\n\"title\": \"名称\",\n\"type\": 1,\n\"updateTime\": 1551455658000,\n\"updateUserId\": 2,\n\"wrongComment\": \"5\"\n}\n},\n{\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551455689000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 108,\n\"isCorrect\": 4,\n\"questionId\": 95,\n\"seq\": 5,\n\"updateTime\": 1551455689000,\n\"updateUserId\": 2\n}\n],\n\"question\": {\n\"content\": \"内容\",\n\"courseId\": 1,\n\"createTime\": 1551455688000,\n\"createUserId\": 2,\n\"generalComment\": \"6\",\n\"groupId\": 2,\n\"id\": 95,\n\"isTemplate\": 7,\n\"questionBankId\": 8,\n\"questionTemplateId\": 9,\n\"score\": 10,\n\"seq\": 11,\n\"title\": \"名称\",\n\"type\": 1,\n\"updateTime\": 1551455688000,\n\"updateUserId\": 2,\n\"wrongComment\": \"5\"\n}\n},\n{\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551455720000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 109,\n\"isCorrect\": 4,\n\"questionId\": 96,\n\"seq\": 5,\n\"updateTime\": 1551455720000,\n\"updateUserId\": 2\n}\n],\n\"question\": {\n\"content\": \"内容\",\n\"correctComment\": \"4\",\n\"courseId\": 1,\n\"createTime\": 1551455720000,\n\"createUserId\": 2,\n\"generalComment\": \"6\",\n\"groupId\": 2,\n\"id\": 96,\n\"isTemplate\": 7,\n\"questionBankId\": 8,\n\"questionTemplateId\": 9,\n\"score\": 10,\n\"seq\": 11,\n\"title\": \"名称\",\n\"type\": 1,\n\"updateTime\": 1551455720000,\n\"updateUserId\": 2\n}\n},\n{\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551455749000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 110,\n\"isCorrect\": 4,\n\"questionId\": 97,\n\"seq\": 5,\n\"updateTime\": 1551455749000,\n\"updateUserId\": 2\n}\n],\n\"question\": {\n\"content\": \"内容\",\n\"correctComment\": \"4\",\n\"courseId\": 1,\n\"createTime\": 1551455749000,\n\"createUserId\": 2,\n\"groupId\": 2,\n\"id\": 97,\n\"isTemplate\": 7,\n\"questionBankId\": 8,\n\"questionTemplateId\": 9,\n\"score\": 10,\n\"seq\": 11,\n\"title\": \"名称\",\n\"type\": 1,\n\"updateTime\": 1551455749000,\n\"updateUserId\": 2,\n\"wrongComment\": \"5\"\n}\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizItemQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionMatchOptionRecord/add",
    "title": "匹配类问题选项选择记录添加",
    "description": "<p>匹配类问题选项选择记录添加</p>",
    "name": "quizQuestionMatchOptionRecordAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionMatchOptionId",
            "description": "<p>匹配类问题选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizQuestionRecordId",
            "description": "<p>问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizQuestionOptionRecordId",
            "description": "<p>选项ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionMatchOptionRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionMatchOptionRecord/deletes",
    "title": "依据匹配类问题选项选择记录id删除",
    "description": "<p>匹配类问题选项选择记录删除</p>",
    "name": "quizQuestionMatchOptionRecordDelete",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>匹配类问题选项选择记录ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionMatchOptionRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizQuestionMatchOptionRecord/find",
    "title": "匹配类问题选项选择记录查找",
    "description": "<p>匹配类问题选项选择记录查找</p>",
    "name": "quizQuestionMatchOptionRecordFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>匹配类问题选项选择记录ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionMatchOptionRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizQuestionMatchOptionRecord/list",
    "title": "匹配类问题选项选择记录列表",
    "description": "<p>依据id查询对应的匹配类问题选项选择记录信息</p>",
    "name": "quizQuestionMatchOptionRecordList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID列表</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionMatchOptionRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionMatchOptionRecord/modify",
    "title": "匹配类问题选项选择记录修改",
    "description": "<p>匹配类问题选项选择记录修改(非必填参数 ， 如果不修改 ， 可以不传)</p>",
    "name": "quizQuestionMatchOptionRecordModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>匹配类问题选项选择记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionMatchOptionId",
            "description": "<p>匹配类问题选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizQuestionRecordId",
            "description": "<p>问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizQuestionOptionRecordId",
            "description": "<p>选项ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": 1,\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionMatchOptionRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionOptionRecord/add",
    "title": "测验问题选项记录添加",
    "description": "<p>测验问题选项记录添加</p>",
    "name": "quizQuestionOptionRecordAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizQuestionRecordId",
            "description": "<p>问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionOptionId",
            "description": "<p>问题选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>题干中占位符</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "isCorrect",
            "description": "<p>选择题：是否为正确选项</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "seq",
            "description": "<p>排序：如果是重排序选项，则是重排序后的选项顺序</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "isChoice",
            "description": "<p>是否选中</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionOptionRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionRecord/deletes",
    "title": "依据测验问题记录id删除",
    "description": "<p>测验问题选项记录删除</p>",
    "name": "quizQuestionOptionRecordDelete",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>测验问题记录ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除测验问题记录ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"[49]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionOptionRecord/deletes",
    "title": "依据测验问题选项记录id删除",
    "description": "<p>测验问题选项记录删除</p>",
    "name": "quizQuestionOptionRecordDelete",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>测验问题选项记录ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除测验问题选项记录ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionOptionRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizQuestionOptionRecord/find",
    "title": "测验问题选项记录查找",
    "description": "<p>测验问题选项记录查找</p>",
    "name": "quizQuestionOptionRecordFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>测验问题选项记录ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionOptionRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizQuestionOptionRecord/list",
    "title": "测验问题选项记录列表",
    "description": "<p>依据id查询对应的测验问题选项记录信息</p>",
    "name": "quizQuestionOptionRecordList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID列表</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionOptionRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionOptionRecord/modify",
    "title": "测验问题选项记录修改",
    "description": "<p>测验问题选项记录修改(非必填参数 ， 如果不修改 ， 可以不传)</p>",
    "name": "quizQuestionOptionRecordModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>测验问题选项记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizQuestionRecordId",
            "description": "<p>问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionOptionId",
            "description": "<p>问题选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>题干中占位符</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "isCorrect",
            "description": "<p>选择题：是否为正确选项</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "seq",
            "description": "<p>排序：如果是重排序选项，则是重排序后的选项顺序</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "isChoice",
            "description": "<p>是否选中</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": 1,\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionOptionRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionRecord/add",
    "title": "测验答题问题记录添加",
    "description": "<p>测验问题记录添加</p>",
    "name": "quizQuestionRecordAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizRecordId",
            "description": "<p>测验记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionId",
            "description": "<p>问题ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "groupId",
            "description": "<p>问题组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "type",
            "description": "<p>问题类型</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "correctComment",
            "description": "<p>正确提示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "wrongComment",
            "description": "<p>错误提示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "generalComment",
            "description": "<p>通用提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "options",
            "description": "<p>选择题（单选、多选、判断题用）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "options.questionOptionId",
            "description": "<p>问题选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.code",
            "description": "<p>题干中占位符</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.content",
            "description": "<p>选项内容（判断题内容传 True/ False）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "options.isCorrect",
            "description": "<p>选择题：是否为正确选项;0，否；1，是；</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "options.seq",
            "description": "<p>排序；</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "options.isChoice",
            "description": "<p>是否选中: 0，否；1，是；</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "matchoptions",
            "description": "<p>匹配题（总数必须为偶数并且相邻的2条记录为一组匹配项）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "matchoptions.questionMatchOptionId",
            "description": "<p>匹配类问题选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "matchoptions.quizQuestionRecordId",
            "description": "<p>问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "matchoptions.quizQuestionOptionRecordId",
            "description": "<p>选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "matchoptions.content",
            "description": "<p>选项内容（匹配项中的一项为空传【null】)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "matchoptions.seq",
            "description": "<p>排序；</p>"
          },
          {
            "group": "Parameter",
            "type": "Object",
            "optional": true,
            "field": "reply",
            "description": "<p>简答、填空题</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "reply.quizQuestionRecordId",
            "description": "<p>问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "reply.reply",
            "description": "<p>回答内容</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"quizRecordId\": \"16\",\n\"questionId\": \"16\",\n\"groupID\": \"2\",\n\"type\": \"1\",\n\"title\": \"名称\",\n\"content\": \"内容\",\n\"correctComment\": \"4\",\n\"wrongComment\": \"5\",\n\"generalComment\": \"6\",\n\"isTemplate\": \"7\",\n\"questionBankID\": \"8\",\n\"questionTemplateId\": \"9\",\n\"score\": \"10\",\n\"gradeScore\": \"10\",\n\"seq\": \"11\",\n\"options\":[{\"questionOptionId\":\"1\",\"code\":\"1\",\"content\":\"2\",\"explanation\":\"3\",\"isCorrect\":\"4\",\"seq\":\"5\"}]\n<p>\n<p>\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"4\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "description": "<p>测验问题选项记录查找</p>",
    "name": "quizQuestionRecordFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>测验问题选项记录ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizQuestionReplyRecord/find",
    "title": "填空类问题回答查找",
    "description": "<p>填空类问题回答查找</p>",
    "name": "quizQuestionRecordFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>填空类问题回答ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionReplyRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "description": "<p>测验问题选项记录查找</p>",
    "name": "quizQuestionRecordFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>测验问题选项记录ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizTestQuestionRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizQuestionRecord/list",
    "title": "学生查看正误测验问题选项记录列表",
    "description": "<p>依据quizRecordId查询对应的所有测验问题选项记录信息</p>",
    "name": "quizQuestionRecordList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "optional": false,
            "field": "String",
            "description": "<p>quizRecordId quizRecordID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "http://localhost:8080//quizQuestionRecord/list?quizRecordId=16",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"correctComment\": \"4\",\n\"createTime\": 1551458697000,\n\"createUserId\": 2,\n\"generalComment\": \"6\",\n\"gradeScore\": 10,\n\"groupId\": 2,\n\"id\": 4,\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551458697000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 1,\n\"isCorrect\": 4,\n\"questionOptionId\": 1,\n\"quizQuestionRecordId\": 16,\n\"seq\": 5,\n\"updateTime\": 1551460677000,\n\"updateUserId\": 2\n},\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551458835000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 2,\n\"isCorrect\": 4,\n\"questionOptionId\": 1,\n\"quizQuestionRecordId\": 16,\n\"seq\": 5,\n\"updateTime\": 1551460677000,\n\"updateUserId\": 2\n}\n],\n\"questionId\": 16,\n\"quizRecordId\": 16,\n\"score\": 10,\n\"seq\": 11,\n\"title\": \"名称\",\n\"type\": 1,\n\"updateTime\": 1551458697000,\n\"updateUserId\": 2,\n\"wrongComment\": \"5\"\n},\n{\n\"correctComment\": \"4\",\n\"createTime\": 1551458835000,\n\"createUserId\": 2,\n\"generalComment\": \"6\",\n\"gradeScore\": 10,\n\"groupId\": 2,\n\"id\": 5,\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551458697000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 1,\n\"isCorrect\": 4,\n\"questionOptionId\": 1,\n\"quizQuestionRecordId\": 16,\n\"seq\": 5,\n\"updateTime\": 1551460677000,\n\"updateUserId\": 2\n},\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551458835000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 2,\n\"isCorrect\": 4,\n\"questionOptionId\": 1,\n\"quizQuestionRecordId\": 16,\n\"seq\": 5,\n\"updateTime\": 1551460677000,\n\"updateUserId\": 2\n}\n],\n\"questionId\": 16,\n\"quizRecordId\": 16,\n\"score\": 10,\n\"seq\": 11,\n\"title\": \"名称\",\n\"type\": 1,\n\"updateTime\": 1551458937000,\n\"updateUserId\": 2,\n\"wrongComment\": \"5\"\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionRecord/modify",
    "title": "测验答题问题记录修改",
    "description": "<p>测验问题选项记录修改</p>",
    "name": "quizQuestionRecordModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>测验问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizRecordId",
            "description": "<p>测验记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "questionId",
            "description": "<p>问题ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "groupId",
            "description": "<p>问题组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "type",
            "description": "<p>问题类型</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "correctComment",
            "description": "<p>正确提示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "wrongComment",
            "description": "<p>错误提示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "generalComment",
            "description": "<p>通用提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "gradeScore",
            "description": "<p>得分</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "options",
            "description": "<p>选择题（单选、多选、判断题用）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "options.questionOptionId",
            "description": "<p>问题选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.code",
            "description": "<p>题干中占位符</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.content",
            "description": "<p>选项内容（判断题内容传 True/ False）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "options.explanation",
            "description": "<p>选择该项的提示</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "options.isCorrect",
            "description": "<p>选择题：是否为正确选项;0，否；1，是；</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "options.seq",
            "description": "<p>排序；</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "options.isChoice",
            "description": "<p>是否选中: 0，否；1，是；</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "matchoptions",
            "description": "<p>匹配题（总数必须为偶数并且相邻的2条记录为一组匹配项）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "matchoptions.questionMatchOptionId",
            "description": "<p>匹配类问题选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "matchoptions.quizQuestionRecordId",
            "description": "<p>问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "matchoptions.quizQuestionOptionRecordId",
            "description": "<p>选项ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "matchoptions.content",
            "description": "<p>选项内容（匹配项中的一项为空传【null】)</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "matchoptions.seq",
            "description": "<p>排序；</p>"
          },
          {
            "group": "Parameter",
            "type": "Object",
            "optional": true,
            "field": "quizQuestionReplyRecord",
            "description": "<p>简答、填空题</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "quizQuestionReplyRecord.quizQuestionRecordId",
            "description": "<p>问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "quizQuestionReplyRecord.reply",
            "description": "<p>回答内容</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": \"5\",\n\"quizRecordId\": \"16\",\n\"questionId\": \"16\",\n\"groupID\": \"2\",\n\"type\": \"1\",\n\"title\": \"名称\",\n\"content\": \"内容\",\n\"correctComment\": \"4\",\n\"wrongComment\": \"5\",\n\"generalComment\": \"6\",\n\"isTemplate\": \"7\",\n\"questionBankID\": \"8\",\n\"questionTemplateId\": \"9\",\n\"score\": \"10\",\n\"gradeScore\": \"10\",\n\"seq\": \"11\",\n\"options\":[{\"questionOptionId\":\"1\",\"code\":\"1\",\"content\":\"2\",\"explanation\":\"3\",\"isCorrect\":\"4\",\"seq\":\"5\"}]\n<p>\n<p>\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"5\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionReplyRecord/add",
    "title": "填空类问题回答添加",
    "description": "<p>填空类问题回答添加</p>",
    "name": "quizQuestionReplyRecordAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizQuestionRecordId",
            "description": "<p>问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "reply",
            "description": "<p>回答内容</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionReplyRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionReplyRecord/deletes",
    "title": "填空类问题回答id删除",
    "description": "<p>填空类问题回答删除</p>",
    "name": "quizQuestionReplyRecordDelete",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>测验问题记录ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除填空类问题回答ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionReplyRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizQuestionReplyRecord/list",
    "title": "填空类问题回答列表",
    "description": "<p>依据id查询对应的填空类问题回答信息</p>",
    "name": "quizQuestionReplyRecordList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID列表</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除填空类问题回答列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionReplyRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizQuestionReplyRecord/modify",
    "title": "填空类问题回答修改",
    "description": "<p>填空类问题回答修改(非必填参数 ， 如果不修改 ， 可以不传)</p>",
    "name": "quizQuestionReplyRecordModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>填空类问题回答ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizQuestionRecordId",
            "description": "<p>问题记录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "reply",
            "description": "<p>回答内容</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": 1,\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizQuestionReplyRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizQuestionTestRecord/list",
    "title": "学生答题测验问题选项记录列表",
    "description": "<p>依据quizRecordId查询对应的所有测验问题选项记录信息</p>",
    "name": "quizQuestionTestRecordList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "optional": false,
            "field": "String",
            "description": "<p>quizRecordId quizRecordID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "http://localhost:8080/quizQuestionTestRecord/list?quizRecordId=16",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除问题选项列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"correctComment\": \"4\",\n\"createTime\": 1551458697000,\n\"createUserId\": 2,\n\"generalComment\": \"6\",\n\"gradeScore\": 10,\n\"groupId\": 2,\n\"id\": 4,\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551458697000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 1,\n\"isCorrect\": 4,\n\"questionOptionId\": 1,\n\"quizQuestionRecordId\": 16,\n\"seq\": 5,\n\"updateTime\": 1551460677000,\n\"updateUserId\": 2\n},\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551458835000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 2,\n\"isCorrect\": 4,\n\"questionOptionId\": 1,\n\"quizQuestionRecordId\": 16,\n\"seq\": 5,\n\"updateTime\": 1551460677000,\n\"updateUserId\": 2\n}\n],\n\"questionId\": 16,\n\"quizRecordId\": 16,\n\"score\": 10,\n\"seq\": 11,\n\"title\": \"名称\",\n\"type\": 1,\n\"updateTime\": 1551458697000,\n\"updateUserId\": 2,\n\"wrongComment\": \"5\"\n},\n{\n\"correctComment\": \"4\",\n\"createTime\": 1551458835000,\n\"createUserId\": 2,\n\"generalComment\": \"6\",\n\"gradeScore\": 10,\n\"groupId\": 2,\n\"id\": 5,\n\"matchoptions\": [],\n\"options\": [\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551458697000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 1,\n\"isCorrect\": 4,\n\"questionOptionId\": 1,\n\"quizQuestionRecordId\": 16,\n\"seq\": 5,\n\"updateTime\": 1551460677000,\n\"updateUserId\": 2\n},\n{\n\"code\": \"1\",\n\"content\": \"2\",\n\"createTime\": 1551458835000,\n\"createUserId\": 2,\n\"explanation\": \"3\",\n\"id\": 2,\n\"isCorrect\": 4,\n\"questionOptionId\": 1,\n\"quizQuestionRecordId\": 16,\n\"seq\": 5,\n\"updateTime\": 1551460677000,\n\"updateUserId\": 2\n}\n],\n\"questionId\": 16,\n\"quizRecordId\": 16,\n\"score\": 10,\n\"seq\": 11,\n\"title\": \"名称\",\n\"type\": 1,\n\"updateTime\": 1551458937000,\n\"updateUserId\": 2,\n\"wrongComment\": \"5\"\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizTestQuestionRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizRecord/add",
    "title": "测验答题记录添加",
    "description": "<p>测验答题记录添加</p>",
    "name": "quizRecordAdd",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizId",
            "description": "<p>测验ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizVersion",
            "description": "<p>提交时与测验版本一致</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "isSubmit",
            "description": "<p>是否确认提交  1：是；0：否</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "isLastTime",
            "description": "<p>是否最后一次提交  1：是；0：否  (后端处理，前端每次传0）</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": false,
            "field": "startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": false,
            "field": "submitTime",
            "description": "<p>提交时间</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": false,
            "field": "dueTime",
            "description": "<p>截至时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "testerId",
            "description": "<p>测验人ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"quizId\": \"13\",\n\"quizVersion\": \"1\",\n\"isSubmit\": \"0\",\n\"isLastTime\": \"0\",\n\"startTime\": \"2019-03-01 16:44:57\",\n\"submitTime\": \"2019-03-01 16:44:57\",\n\"dueTime\": \"2019-03-01 16:44:57\",\n\"testerId\": \"2\"\n<p>\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>新增问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizRecord/deletes",
    "title": "测验记录id删除",
    "description": "<p>测验记录删除</p>",
    "name": "quizRecordDelete",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>测验问题记录ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除填空类问题回答ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1,2,3]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizRecord/find",
    "title": "测验记录查找",
    "description": "<p>测验记录查找</p>",
    "name": "quizRecordFind",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>测验记录ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"[1]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizRecord/list",
    "title": "测验答题记录列表",
    "description": "<p>依据id查询对应的测验记录信息</p>",
    "name": "quizRecordList",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "quizId",
            "description": "<p>测验ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "http://localhost:8080//quizRecord/list?quizId=13",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除测验记录列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"createTime\": 1551462661000,\n\"createUserId\": 2,\n\"dueTime\": 1551429897000,\n\"id\": 1,\n\"isLastTime\": 0,\n\"isSubmit\": 0,\n\"quizId\": 13,\n\"quizVersion\": 1,\n\"startTime\": 1551429897000,\n\"submitTime\": 1551429897000,\n\"testerId\": 2,\n\"updateTime\": 1551462797000,\n\"updateUserId\": 2\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quizRecord/modify",
    "title": "测验答题记录修改",
    "description": "<p>测验答题记录修改(非必填参数 ， 如果不修改 ， 可以不传)</p>",
    "name": "quizRecordModify",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>测验答题记录Id</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizId",
            "description": "<p>测验ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "quizVersion",
            "description": "<p>提交时测验版本</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "isSubmit",
            "description": "<p>是否确认提交  1：是；0：否</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "isLastTime",
            "description": "<p>是否最后一次提交  1：是；0：否</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": false,
            "field": "startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": false,
            "field": "submitTime",
            "description": "<p>提交时间</p>"
          },
          {
            "group": "Parameter",
            "type": "datetime",
            "optional": false,
            "field": "dueTime",
            "description": "<p>截至时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "testerId",
            "description": "<p>测验人ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": \"1\",\n\"quizId\": \"13\",\n\"quizVersion\": \"1\",\n\"isSubmit\": \"0\",\n\"isLastTime\": \"0\",\n\"startTime\": \"2019-03-01 16:44:57\",\n\"submitTime\": \"2019-03-01 16:44:57\",\n\"dueTime\": \"2019-03-01 16:44:57\",\n\"testerId\": \"2\"\n<p>\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改问题选项ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"[49]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizRecordEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "get",
    "url": "/quizScoreRecord/get",
    "title": "测验答题记录分数展示列表",
    "description": "<p>依据测验id查询当前登录用户的测验分数信息</p>",
    "name": "quizScoreRecord",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>测验ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "http://localhost:8080/quizScoreRecord/get?data=2",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述 { &quot;code&quot;: 200, &quot;entity&quot;: { &quot;attemps&quot;: 2, &quot;attempsAvailable&quot;: 6, &quot;currentScore&quot;: 8, &quot;isGradedAll&quot;: false, &quot;keptScore&quot;: 8, &quot;quizId&quot;: 1106, &quot;testerId&quot;: 1, &quot;timeLimit&quot;: 8 }, &quot;message&quot;: &quot;成功&quot; }</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizScoreRecordQuery.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "post",
    "url": "/quiz/publish/edit",
    "title": "更新测验发布状态",
    "name": "quizpublishStatus",
    "group": "Quiz",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>测验信息ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "status",
            "description": "<p>发布状态： 1：已发布；0：未发布</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n    \"id\": \"1\",\n\t\"status\": \"1\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>测验ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/quiz/QuizPublishStatusEdit.java",
    "groupTitle": "Quiz"
  },
  {
    "type": "",
    "url": "/sis/import/edit",
    "title": "SIS 数据导入",
    "description": "<p>导入 sis 数据到系统</p>",
    "name": "SisImport",
    "group": "SIS",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "isFullBatchUpdate",
            "description": "<p>是否全量更新</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "termId",
            "description": "<p>学期ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "zipFileId",
            "description": "<p>zip文件fileId, zipFileId 与 csvFileIds 必填一个，同时存在时 zip 文件优先</p>"
          },
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": true,
            "field": "csvFileIds",
            "description": "<p>csv文件列表fileId</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/sis/SisImportEdit.java",
    "groupTitle": "SIS"
  },
  {
    "type": "get",
    "url": "/sisImport/error/download/direct",
    "title": "",
    "name": "SisImportErrorDownload",
    "group": "SIS",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "batchCode",
            "description": "<p>批次号</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/sis/SisImportErrorDownload.java",
    "groupTitle": "SIS"
  },
  {
    "type": "get",
    "url": "/sisImportError/list",
    "title": "导入错误信息列表",
    "name": "SisImportErrorList",
    "group": "SIS",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "batchCode",
            "description": "<p>导入批次号</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>相应消息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>错误列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.batchCode",
            "description": "<p>导入批次号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.fileName",
            "description": "<p>文件名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.errorCode",
            "description": "<p>错误代码 1: 不识别文件名称 2: 文件格式错误 3. 字段类型不匹配 4. 字段值为空 5. 字段值格式错误 6. 字段关联值不存在 7. 字段值重复</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.rowNumber",
            "description": "<p>错误所在行</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.fieldName",
            "description": "<p>字段名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.fieldValue",
            "description": "<p>字段值</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/sis/SisImportErrorDataQuery.java",
    "groupTitle": "SIS"
  },
  {
    "type": "get",
    "url": "/sisImportError/list",
    "title": "导入错误信息列表分页",
    "name": "SisImportErrorPageList",
    "group": "SIS",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "batchCode",
            "description": "<p>导入批次号</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页结果展示数量</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>相应消息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>分页信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.total",
            "description": "<p>总结果数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageSize",
            "description": "<p>每页结果展示数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.list",
            "description": "<p>错误列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.batchCode",
            "description": "<p>导入批次号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.fileName",
            "description": "<p>文件名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.errorCode",
            "description": "<p>错误代码 1: 不识别文件名称 2: 文件格式错误 3. 字段类型不匹配 4. 字段值为空 5. 字段值格式错误 6. 字段关联值不存在 7. 字段值重复</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.rowNumber",
            "description": "<p>错误所在行</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.list.fieldName",
            "description": "<p>字段名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.list.fieldValue",
            "description": "<p>字段值</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/sis/SisImportErrorDataQuery.java",
    "groupTitle": "SIS"
  },
  {
    "type": "get",
    "url": "/sisImport/list",
    "title": "用户导入记录列表",
    "description": "<p>返回数据按导入时间倒序排序</p>",
    "name": "SisImportList",
    "group": "SIS",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>导入记录列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>记录ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.batchCode",
            "description": "<p>导入批次号</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.orgId",
            "description": "<p>导入数据根机构</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.startTime",
            "description": "<p>导入开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.endTime",
            "description": "<p>导入结束时间，无结束时间时表示导入未完成</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isFullBatchUpdate",
            "description": "<p>是否全量更新</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isOverrideUiChange",
            "description": "<p>是否覆盖UI更新</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.totalNumber",
            "description": "<p>成功导入记录总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.orgNumber",
            "description": "<p>成功导入机构数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.termNumber",
            "description": "<p>成功导入学期数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseNumber",
            "description": "<p>成功导入课程数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionNumber",
            "description": "<p>成功导入班级数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.sectionUserNumber",
            "description": "<p>成功导入班级用户数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.studyGroupSetNumber",
            "description": "<p>成功导入学习小组集数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.studyGroupNumber",
            "description": "<p>成功导入学习小组数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.studyGroupUserNumber",
            "description": "<p>成功导入学习小组用户数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.errorNumber",
            "description": "<p>总错误数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.importFiles",
            "description": "<p>导入文件列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.importFiles.fileName",
            "description": "<p>文件名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.importFiles.fileId",
            "description": "<p>文件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.errors",
            "description": "<p>错误列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.errors.fileName",
            "description": "<p>错误所在文件</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.errors.errorCode",
            "description": "<p>错误码，  1: 不识别文件名称 2: 文件格式错误 3. 字段类型不匹配 4. 字段值为空 5. 字段值格式错误 6. 字段关联值不存在 7. 字段值重复</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.errors.rowNumber",
            "description": "<p>错误所在行</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.errors.fieldName",
            "description": "<p>错误字段名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.errors.fieldValue",
            "description": "<p>错误字段值</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/sis/SisImportDataQuery.java",
    "groupTitle": "SIS"
  },
  {
    "type": "get",
    "url": "/sisImport/pageList",
    "title": "用户导入记录列表分页",
    "description": "<p>返回数据按导入时间倒序排序</p>",
    "name": "SisImportPageList",
    "group": "SIS",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "entity.pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "entity.pageSize",
            "description": "<p>每页结果展示数量</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>导入记录分页信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.total",
            "description": "<p>总结果数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pageSize",
            "description": "<p>每页结果展示数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.list",
            "description": "<p>记录列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.id",
            "description": "<p>记录ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.batchCode",
            "description": "<p>导入批次号</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.orgId",
            "description": "<p>导入数据根机构</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.startTime",
            "description": "<p>导入开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.endTime",
            "description": "<p>导入结束时间，无结束时间时表示导入未完成</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.list.isFullBatchUpdate",
            "description": "<p>是否全量更新</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.list.isOverrideUiChange",
            "description": "<p>是否覆盖UI更新</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.totalNumber",
            "description": "<p>成功导入记录总数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.orgNumber",
            "description": "<p>成功导入机构数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.termNumber",
            "description": "<p>成功导入学期数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.courseNumber",
            "description": "<p>成功导入课程数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.sectionNumber",
            "description": "<p>成功导入班级数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.sectionUserNumber",
            "description": "<p>成功导入班级用户数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.studyGroupSetNumber",
            "description": "<p>成功导入学习小组集数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.studyGroupNumber",
            "description": "<p>成功导入学习小组数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.studyGroupUserNumber",
            "description": "<p>成功导入学习小组用户数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.errorNumber",
            "description": "<p>总错误数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.list.importFiles",
            "description": "<p>导入文件列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.importFiles.fileName",
            "description": "<p>文件名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.importFiles.fileId",
            "description": "<p>文件ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.list.errors",
            "description": "<p>错误列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.list.errors.fileName",
            "description": "<p>错误所在文件</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.errors.errorCode",
            "description": "<p>错误码，  1: 不识别文件名称 2: 文件格式错误 3. 字段类型不匹配 4. 字段值为空 5. 字段值格式错误 6. 字段关联值不存在 7. 字段值重复</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.list.errors.rowNumber",
            "description": "<p>错误所在行</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.list.errors.fieldName",
            "description": "<p>错误字段名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.list.errors.fieldValue",
            "description": "<p>错误字段值</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/sis/SisImportDataQuery.java",
    "groupTitle": "SIS"
  },
  {
    "type": "get",
    "url": "/sis/importProcess/query",
    "title": "用户导入任务进度查询",
    "name": "SisImportProcessQuery",
    "group": "SIS",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>相应消息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>处理进度</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.batchCode",
            "description": "<p>最近导入任务批次号，如果批次号未返回，表示用户没有执行过导入任务</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.percent",
            "description": "<p>处理进度（整数表示百分比）</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/sis/SisImportProcessQuery.java",
    "groupTitle": "SIS"
  },
  {
    "type": "get",
    "url": "/sis/templateDownload/direct",
    "title": "SIS导入模板下载",
    "description": "<p>返回模板文件流</p>",
    "name": "SisTemplateDownload",
    "group": "SIS",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/sis/SisTemplateDownload.java",
    "groupTitle": "SIS"
  },
  {
    "name": "SectionAdd",
    "group": "Section",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>班级名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "endTime",
            "description": "<p>结束时间</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>班级ID</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/section/SectionDataEdit.java",
    "groupTitle": "Section"
  },
  {
    "name": "SectionDelete",
    "group": "Section",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>班级名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "endTime",
            "description": "<p>结束时间</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>班级ID</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/section/SectionDataEdit.java",
    "groupTitle": "Section"
  },
  {
    "type": "get",
    "url": "/section/list",
    "title": "课程班级列表",
    "name": "SectionList",
    "group": "Section",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>班级列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>班级ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>班级名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.startTime",
            "description": "<p>班级开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.endTime",
            "description": "<p>班级结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.users",
            "description": "<p>学生列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.users.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3",
              "4"
            ],
            "optional": false,
            "field": "entity.users.roleId",
            "description": "<p>角色ID, 1: 管理员 2: 教师 3: TA 4: 学生</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "entity.users.registryStatus",
            "description": "<p>状态, 1: 未接受 2： 已加入</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/section/SectionDataQuery.java",
    "groupTitle": "Section"
  },
  {
    "name": "SectionModify",
    "group": "Section",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>班级ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>班级名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "endTime",
            "description": "<p>结束时间</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>班级ID</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/section/SectionDataEdit.java",
    "groupTitle": "Section"
  },
  {
    "type": "get",
    "url": "/section/people/query",
    "title": "班级及用户列表",
    "name": "SectionPeopleQuery",
    "group": "Section",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>班级列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>班级ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.startTime",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.endTime",
            "description": "<p>结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.teacherUsers",
            "description": "<p>教师用户列表（暂无实现）</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.activeUsers",
            "description": "<p>已激活用户列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.activeUsers.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.activeUsers.roleId",
            "description": "<p>用户角色ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.activeUsers.roleName",
            "description": "<p>用户角色名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "entity.activeUsers.registryStatus",
            "description": "<p>注册状态（1: 邀请中，2:　已加入）</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.activeUsers.registryOrigin",
            "description": "<p>注册来源（1: 管理员添加，2: 邀请加入 3: 自行注册）</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.pendingUsers",
            "description": "<p>邀请中用户列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pendingUsers.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.pendingUsers.roleId",
            "description": "<p>用户角色ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.pendingUsers.roleName",
            "description": "<p>用户角色名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": false,
            "field": "entity.pendingUsers.registryStatus",
            "description": "<p>注册状态（1: 邀请中，2:　已加入）</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": false,
            "field": "entity.pendingUsers.registryOrigin",
            "description": "<p>注册来源（1: 管理员添加，2: 邀请加入 3: 自行注册）</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/section/SectionPeopleDataQuery.java",
    "groupTitle": "Section"
  },
  {
    "type": "get",
    "url": "/studyGroup/joined/query",
    "title": "用户加入的小组列表",
    "name": "StudyGroupJoinedQuery",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>小组列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.sisId",
            "description": "<p>小组SIS ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.studyGroupSetId",
            "description": "<p>小组集ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.isStudentGroup",
            "description": "<p>是否学生组</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.maxMemberNumber",
            "description": "<p>成员数量限制</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.leaderId",
            "description": "<p>组长ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.joinType",
            "description": "<p>学生加入小组类型, 1: 无限制 2: 仅限邀请</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupJoinedQuery.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupUser/assign/edit",
    "title": "随机分配学生到小组",
    "description": "<p>分配学生，限制成员在相同班级时需要：1. 组为空且无成员数量限制 2. 待分配学生班级数量不超过组数量</p>",
    "name": "StudyGroupUserAssignEdit",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "studyGroupSetId",
            "description": "<p>小组集ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "isLimitMemberInSameSection",
            "description": "<p>限制组成员在相同班级</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>消息描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>小组集ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupUserAssignEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroup/add",
    "title": "学习小组添加",
    "description": "<p>如果是学生创建学习小组，则学生自动加入小组</p>",
    "name": "studyGroupAdd",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>小组名</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupSetId",
            "description": "<p>所属小组集</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "groupMemberNumber",
            "description": "<p>小组成员数量</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2"
            ],
            "optional": true,
            "field": "joinType",
            "description": "<p>加入类型，1：无限制 2: 仅限邀请</p>"
          },
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": true,
            "field": "inviteUserIds",
            "description": "<p>创建小组后同时添加用户到小组内</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>小组ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupDataEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroup/deletes",
    "title": "学习小组删除",
    "name": "studyGroupDelete",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "[1, 2, 3]",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>ID列表</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupDataEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "get",
    "url": "/studyGroup/get",
    "title": "学习小组",
    "name": "studyGroupGet",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>小组ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>小组</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.maxMemberNumber",
            "description": "<p>成员数量限制</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.joinType",
            "description": "<p>加入方式, 1: 无限制 2: 仅限邀请</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.memberNumber",
            "description": "<p>当前成员数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.leaderId",
            "description": "<p>组长ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.course",
            "description": "<p>所属课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.course.name",
            "description": "<p>所属课程名称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupDataQuery.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "get",
    "url": "/studyGroup/list",
    "title": "学习小组列表",
    "name": "studyGroupList",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studyGroupSetId",
            "description": "<p>小组集ID，参数不存在时，返回课程下所有学习小组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.maxMemberNumber",
            "description": "<p>成员数量限制</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.joinType",
            "description": "<p>加入方式, 1: 无限制 2: 仅限邀请</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.memberNumber",
            "description": "<p>当前成员数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.leaderId",
            "description": "<p>组长ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity.groupUsers",
            "description": "<p>小组成员</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.groupUsers.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.groupUsers.isLeader",
            "description": "<p>是否为组长</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.groupUsers.username",
            "description": "<p>用户登录名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.groupUsers.nickname",
            "description": "<p>用户昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.studyGroupSet",
            "description": "<p>所属组集</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.studyGroupSet.name",
            "description": "<p>组集名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.studyGroupSet.allowSelfSignup",
            "description": "<p>允许学生自行注册</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.studyGroupSet.isSectionGroup",
            "description": "<p>班级小组</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupDataQuery.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroup/modify",
    "title": "学习小组编辑",
    "name": "studyGroupModify",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>小组名</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "maxMemberNumber",
            "description": "<p>小组成员数量（空表示不限制）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": true,
            "field": "inviteUserIds",
            "description": "<p>创建小组后同时添加用户到小组内</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>小组ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupDataEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupSet/add",
    "title": "学习小组集添加",
    "name": "studyGroupSetAdd",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>小组集名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowSelfSignup",
            "description": "<p>允许学生自行注册</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isSectionGroup",
            "description": "<p>限制小组成员在相同班级</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "leaderSetStrategy",
            "description": "<p>组长设置策略： 1: 手动指定； 2: 第一个加入学生； 3: 随机设置</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "groupMemberNumber",
            "description": "<p>小组成员数量</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "createNGroup",
            "description": "<p>立即创建小组数量</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>小组集ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupSetDataEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupSet/copy/edit",
    "title": "学习小组集克隆",
    "name": "studyGroupSetCopy",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>小组集ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>新小组集名称</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新小组集ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupSetCopyEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupSet/deletes",
    "title": "学习小组集删除",
    "name": "studyGroupSetDelete",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>组集ID列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求示例:",
          "content": "[1,2,3]",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>ID列表</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupSetDataEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "get",
    "url": "/studyGroupSet/list",
    "title": "学习小组集列表",
    "name": "studyGroupSetList",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>学习小组集列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.allowSelfSignup",
            "description": "<p>是否允许学生自行加入</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.isSectionGroup",
            "description": "<p>是否是班级小组集（小组学生需要在相同班级）</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.isStudentGroup",
            "description": "<p>是否是学生小组集（学生可以创建小组）</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "entity.leaderSetStrategy",
            "description": "<p>组长设置策略，1: 手动指定； 2: 第一个加入学生； 3: 随机设置</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "entity.groupMemberNumber",
            "description": "<p>组成员数量限制</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.createTime",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.updateTime",
            "description": "<p>更新时间</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupSetDataQuery.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupSet/modify",
    "title": "学习小组集编辑",
    "name": "studyGroupSetModify",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>小组集ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>小组集名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "allowSelfSignup",
            "description": "<p>允许学生自行注册</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": true,
            "field": "isSectionGroup",
            "description": "<p>限制小组成员在相同班级</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "allowedValues": [
              "1",
              "2",
              "3"
            ],
            "optional": true,
            "field": "leaderSetStrategy",
            "description": "<p>组长设置策略： 1: 手动指定； 2: 第一个加入学生； 3: 随机设置</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "groupMemberNumber",
            "description": "<p>小组成员数量</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>小组集ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupSetDataEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupSetUser/alternative/query",
    "title": "学习小组待添加学生列表",
    "name": "studyGroupSetUserAlternativeSearch",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "studyGroupSetId",
            "description": "<p>小组集</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "username",
            "description": "<p>登录名称过滤</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>用户列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.username",
            "description": "<p>用户登录名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.nickname",
            "description": "<p>用户昵称</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupSetAlternativeUserQuery.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupUser/add",
    "title": "学习小组学生添加",
    "name": "studyGroupUserAdd",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>学生ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "studyGroupId",
            "description": "<p>学习小组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupUserEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupUser/deletes",
    "title": "学习小组学生移除",
    "name": "studyGroupUserDelete",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "studyGroupId",
            "description": "<p>学习小组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "userId",
            "description": "<p>学生ID，参数没有时表示从小组中移除自己</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupUserEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupUser/leader/edit",
    "title": "学习小组组长设置",
    "name": "studyGroupUserLeaderEdit",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "leaderId",
            "description": "<p>Leader用户ID，为空时移除小组Leader</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "studyGroupId",
            "description": "<p>学习小组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupUserLeaderEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupUser/list",
    "title": "学习小组学生列表",
    "name": "studyGroupUserList",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "studyGroupId",
            "description": "<p>学习小组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>用户列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.studyGroupSetId",
            "description": "<p>小组集ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.studyGroupId",
            "description": "<p>小组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.username",
            "description": "<p>用户登录名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.nickname",
            "description": "<p>用户昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.avatarFileUrl",
            "description": "<p>用户头像</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.isLeader",
            "description": "<p>是否为组长</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupUserQuery.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/studyGroupUser/move/edit",
    "title": "移动小组用户",
    "name": "studyGroupUserUserMove",
    "group": "StudyGroup",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "targetGroupId",
            "description": "<p>移动目标小组</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "userId",
            "description": "<p>移动用户，参数没有时表示操作自己</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupUserMoveEdit.java",
    "groupTitle": "StudyGroup"
  },
  {
    "type": "post",
    "url": "/term/add",
    "title": "学期添加",
    "description": "<p>学期添加</p>",
    "name": "termAdd",
    "group": "Term",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "size": "..50",
            "optional": false,
            "field": "name",
            "description": "<p>学期名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "sisId",
            "description": "<p>学期SIS ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "termStartTime",
            "description": "<p>学期开始时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "termEndTime",
            "description": "<p>学期截止时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studentStartTime",
            "description": "<p>学生访问生效时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studentEndTime",
            "description": "<p>学生访问截止时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "teacherStartTime",
            "description": "<p>教师访问生效时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "teacherEndTime",
            "description": "<p>教师访问截止时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "taStartTime",
            "description": "<p>助教访问生效时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "taEndTime",
            "description": "<p>助教访问截止时间(使用毫秒值）</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"vo\": \"1\",\n\"message\": \"success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/term/TermDataEdit.java",
    "groupTitle": "Term"
  },
  {
    "type": "post",
    "url": "/term/deletes",
    "title": "学期删除",
    "description": "<p>学期删除</p>",
    "name": "termDeletes",
    "group": "Term",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>学期ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/term/TermDataEdit.java",
    "groupTitle": "Term"
  },
  {
    "type": "get",
    "url": "/term/list",
    "title": "学期列表",
    "name": "termList",
    "group": "Term",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>单元列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>学期ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.code",
            "description": "<p>编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.coursesCount",
            "description": "<p>课程数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.isDefault",
            "description": "<p>是否默认</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.startTime",
            "description": "<p>学期开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.endTime",
            "description": "<p>学期结束时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.termConfigs",
            "description": "<p>人员访问时间配置</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.termConfigs.role_id",
            "description": "<p>人员角色 1:教师 2:助教 3:学生</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.termConfigs.start_time",
            "description": "<p>人员访问生效时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.termConfigs.end_time",
            "description": "<p>人员访问截止时间</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json 返回值",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"id\": 1,\n\"code\": \"000\",\n\"coursesCount\": 1,\n\"isDefault\": 1,\n\"name\": \"Default\",\n\"startTime\": 1546621122000,\n\"endTime\": 1546621132000,\n\"termConfigs\": [\n{\n{\n\"start_time\": 1549087685000,\n\"role_id\": 1,\n\"end_time\": 1550815701000,\n},\n{\n\"start_time\": 1549087685000,\n\"role_id\": 2,\n\"end_time\": 1550815701000,\n},\n{\n\"start_time\": 1549087685000,\n\"role_id\": 3,\n\"end_time\": 1550815701000,\n}\n]\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/term/TermDataQuery.java",
    "groupTitle": "Term"
  },
  {
    "type": "post",
    "url": "/term/modify",
    "title": "学期修改",
    "description": "<p>学期修改</p>",
    "name": "termModify",
    "group": "Term",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>学期id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "size": "..50",
            "optional": false,
            "field": "name",
            "description": "<p>学期名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "sisId",
            "description": "<p>学期SIS ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "termStartTime",
            "description": "<p>学期开始时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "termEndTime",
            "description": "<p>学期截止时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studentStartTime",
            "description": "<p>学生访问生效时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "studentEndTime",
            "description": "<p>学生访问截止时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "teacherStartTime",
            "description": "<p>教师访问生效时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "teacherEndTime",
            "description": "<p>教师访问截止时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "taStartTime",
            "description": "<p>助教访问生效时间(使用毫秒值）</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "taEndTime",
            "description": "<p>助教访问截止时间(使用毫秒值）</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"vo\": \"1\",\n\"message\": \"success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/term/TermDataEdit.java",
    "groupTitle": "Term"
  },
  {
    "type": "get",
    "url": "/termSelect/list",
    "title": "学期下拉列表",
    "description": "<p>系统中只有学校类型机构有学期：用户在机构A（机构A为学校）、或在机构B（机构B为机构A的下级机构），返回学期列表为机构A的学期列表</p>",
    "name": "termSelectList",
    "group": "Term",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>单元列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>学期ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.code",
            "description": "<p>编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.coursesCount",
            "description": "<p>课程数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.isDefault",
            "description": "<p>是否默认</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.startTime",
            "description": "<p>学期开始时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.endTime",
            "description": "<p>学期结束时间</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "json 返回值",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"id\": 1,\n\"code\": \"000\",\n\"coursesCount\": 1,\n\"isDefault\": 1,\n\"name\": \"Default\",\n\"startTime\": 1546621122000,\n\"endTime\": 1546621132000,\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "String"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/term/TermSelectDataQuery.java",
    "groupTitle": "Term"
  },
  {
    "type": "get",
    "url": "/todo/pageList",
    "title": "待办todo列表",
    "description": "<p>TODO列表</p>",
    "name": "todoPageList",
    "group": "UserTodo",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "courseId",
            "description": "<p>课程ID 课程首页必填，dashboard页为空</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "curl -i /todo/pageList?pageIndex=1&pageSize=5",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>响应体</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list",
            "description": "<p>资源列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.dataType",
            "description": "<p>数据类型 1:待打分任务 2:日历待办 3待提交任务</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.calendarType",
            "description": "<p>日历类型, 1: 个人 2: 课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.doTime",
            "description": "<p>日历待办执行时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.courseId",
            "description": "<p>课程Id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.courseName",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.originType",
            "description": "<p>任务类型 1: 作业 2:讨论 3:测验</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.originId",
            "description": "<p>任务ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.toBeScoredTotal",
            "description": "<p>待打分数量</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.pageIndex",
            "description": "<p>当前页</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.pageSize",
            "description": "<p>每页数据</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.total",
            "description": "<p>总页数</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/TodoDataQuery.java",
    "groupTitle": "UserTodo"
  },
  {
    "type": "get",
    "url": "/upcoming/pageList",
    "title": "待办upcoming列表",
    "description": "<p>upcoming列表</p>",
    "name": "upcomingPageList",
    "group": "UserTodo",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "courseId",
            "description": "<p>课程ID 课程首页必填，dashboard页为空</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "curl -i /upcoming/pageList?pageIndex=1&pageSize=5",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>响应体</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list",
            "description": "<p>资源列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.dataType",
            "description": "<p>数据类型 1:课程任务 2:日历待办</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.calendarType",
            "description": "<p>日历类型, 1: 个人 2: 课程</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.doTime",
            "description": "<p>日历待办执行时间</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.courseId",
            "description": "<p>课程Id</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.courseName",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.originType",
            "description": "<p>任务类型 1: 作业 2:讨论 3:测验</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.originId",
            "description": "<p>任务ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity.list.assign",
            "description": "<p>学生：我的分配 教师：最早的分配</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.pageIndex",
            "description": "<p>当前页</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.pageSize",
            "description": "<p>每页数据</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.total",
            "description": "<p>总页数</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/calender/UpComingDataQuery.java",
    "groupTitle": "UserTodo"
  },
  {
    "type": "post",
    "url": "/user/signUp/edit",
    "title": "用户注册",
    "name": "UserRegistry",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>登录名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "orgId",
            "description": "<p>机构ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "firstName",
            "description": "<p>First name</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "lastName",
            "description": "<p>Last name</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "phone",
            "description": "<p>联系方式</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>新用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/user/UserSignUpEdit.java",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/userSetting/portrait/edit",
    "title": "用户头像修改",
    "name": "emailIsExist",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "avatarFileId",
            "description": "<p>用户头像文件url</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/user/UserPortraitEdit.java",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/userEmail/add",
    "title": "添加邮箱地址",
    "description": "<p>添加邮箱地址</p>",
    "name": "userEmailAdd",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户关联邮件地址</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\n\t\"email\": \"hjl123456@126.com\",\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增测验ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"159\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/user/UserEmailEdit.java",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/userEmail/deletes",
    "title": "邮箱地址删除",
    "description": "<p>邮箱地址删除</p>",
    "name": "userEmailDeletes",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>邮箱地址ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>邮箱地址ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"[49]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/user/UserEmailEdit.java",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/userEmail/modify",
    "title": "邮箱地址修改",
    "description": "<p>邮箱地址修改</p>",
    "name": "userEmailModify",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户关联邮件地址</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\t\"id\": \"159\",\n\"email\": \"hjl123456@126.com\",\n\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>新增测验ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n    \"code\": 200,\n    \"message\": \"159\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/user/UserEmailEdit.java",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/userLink/deletes",
    "title": "用户介绍链接删除",
    "description": "<p>用户介绍链接删除</p>",
    "name": "userLinkDeletes",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "ids",
            "description": "<p>用户介绍链接ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "[1, 2, 3]",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除用户介绍链接ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"[49]\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/user/UserLinkEdit.java",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/userSetting/modify",
    "title": "用户信息修改",
    "description": "<p>用户首页设置</p>",
    "name": "userSettingModify",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "firstName",
            "description": "<p>第一名字</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "lastName",
            "description": "<p>姓氏</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "fullName",
            "description": "<p>用户全称，用于评分展示</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>登录名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>自我介绍</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "phone",
            "description": "<p>联系电话</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "title",
            "description": "<p>头衔</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "oldPassword",
            "description": "<p>旧密码；</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "password",
            "description": "<p>新密码；</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "language",
            "description": "<p>使用语言</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "timeZone",
            "description": "<p>时区</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "email",
            "description": "<p>用户关联邮件地址</p>"
          },
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": true,
            "field": "links",
            "description": "<p>用户介绍链接</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "links.id",
            "description": "<p>连接ID (如果是添加ID为null)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "links.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "links.url",
            "description": "<p>地址</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "{\n\"id\": 1,\n\"name\": \"Thinking In Java\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>修改测验问题ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"vo\": \"1\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/user/UserSettingEdit.java",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/course/publicRegistry/edit",
    "title": "加入公共课程",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/PublicCourseRegistryEdit.java",
    "group": "_home_zhangzhen_workspace_wd_wd_lms_platform_src_main_java_com_wdcloud_lms_business_course_PublicCourseRegistryEdit_java",
    "groupTitle": "_home_zhangzhen_workspace_wd_wd_lms_platform_src_main_java_com_wdcloud_lms_business_course_PublicCourseRegistryEdit_java",
    "name": "PostCoursePublicregistryEdit"
  },
  {
    "type": "post",
    "url": "/studyGroup/join/edit",
    "title": "用户加入小组",
    "name": "JoinStudyGroup",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/studygroup/StudyGroupJoinEdit.java",
    "group": "_home_zhangzhen_workspace_wd_wd_lms_platform_src_main_java_com_wdcloud_lms_business_studygroup_StudyGroupJoinEdit_java",
    "groupTitle": "_home_zhangzhen_workspace_wd_wd_lms_platform_src_main_java_com_wdcloud_lms_business_studygroup_StudyGroupJoinEdit_java"
  },
  {
    "type": "post",
    "url": "/adminUser/restPassword/edit",
    "title": "重置密码",
    "description": "<p>重置密码</p>",
    "name": "____",
    "group": "admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/AdminUseRestPasswordEdit.java",
    "groupTitle": "admin"
  },
  {
    "type": "post",
    "url": "/adminUser/removeAvatar/edit",
    "title": "清除用户头像",
    "description": "<p>清除用户头像</p>",
    "name": "______",
    "group": "admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/AdminUseRemoveAvatarEdit.java",
    "groupTitle": "admin"
  },
  {
    "type": "get",
    "url": "/adminUser/add",
    "title": "用户添加",
    "description": "<p>管理员用户添加</p>",
    "name": "_______",
    "group": "admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "firstName",
            "description": "<p>firstName</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "lastName",
            "description": "<p>lastName</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "loginId",
            "description": "<p>登录名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "orgId",
            "description": "<p>机构ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "orgTreeId",
            "description": "<p>机构TreeId</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "email",
            "description": "<p>邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "sisId",
            "description": "<p>SIS ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/AdminUseDataEdit.java",
    "groupTitle": "admin"
  },
  {
    "type": "get",
    "url": "/addAdmin/add",
    "title": "添加管理员",
    "description": "<p>添加管理员</p>",
    "name": "addAdminAdd",
    "group": "admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "orgId",
            "description": "<p>机构ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "orgTreeId",
            "description": "<p>机构树</p>"
          },
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "users",
            "description": "<p>机构树</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/AddAdminDataEdit.java",
    "groupTitle": "admin"
  },
  {
    "type": "get",
    "url": "/adminCourse/pageList",
    "title": "课程列表分页查询接口",
    "description": "<p>课程列表分页查询接口</p>",
    "name": "adminCoursePageList",
    "group": "admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "allowedValues": [
              "course",
              "teacher"
            ],
            "optional": false,
            "field": "type",
            "description": "<p>查询类型</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "termId",
            "description": "<p>学期ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageIndex",
            "description": "<p>页码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>条数</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity",
            "description": "<p>收藏课程列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.name",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.courseAlias",
            "description": "<p>课程别名（用户为自己加入的课程创建的昵称）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.code",
            "description": "<p>课程编码</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"message\": \"success\",\n\"entity\": {\n<p>\n}\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/course/AdminCourseDataQuery.java",
    "groupTitle": "admin"
  },
  {
    "type": "post",
    "url": "/adminUser/deletes",
    "title": "用户删除",
    "name": "adminUserDelete",
    "group": "admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>用户</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/AdminUseDataEdit.java",
    "groupTitle": "admin"
  },
  {
    "type": "get",
    "url": "/adminUser/get",
    "title": "管理员用户详情查询",
    "description": "<p>管理员用户详情查询</p>",
    "name": "adminUserGet",
    "group": "admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "data",
            "description": "<p>id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/AdminUseDataQuery.java",
    "groupTitle": "admin"
  },
  {
    "type": "post",
    "url": "/adminUser/modify",
    "title": "用户编辑",
    "name": "adminUserModify",
    "group": "admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>用户</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "firstName",
            "description": "<p>firstName</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "lastName",
            "description": "<p>lastName</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nickname",
            "description": "<p>显示名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户email</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>消息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/AdminUseDataEdit.java",
    "groupTitle": "admin"
  },
  {
    "type": "get",
    "url": "/adminUserOrg/list",
    "title": "管理员用户查询",
    "description": "<p>管理员用户查询</p>",
    "name": "adminUserOrgList",
    "group": "admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.sisId",
            "description": "<p>SIS ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.username",
            "description": "<p>登录名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.nickname",
            "description": "<p>昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.fullname",
            "description": "<p>用户全名</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/AdminUseOrgDataQuery.java",
    "groupTitle": "admin"
  },
  {
    "type": "get",
    "url": "/adminUser/pageList",
    "title": "管理员用户查询",
    "description": "<p>管理员用户查询</p>",
    "name": "adminUserPageList",
    "group": "admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageIndex",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>分页条数</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "q",
            "description": "<p>搜索条件 (full name搜索)</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.sisId",
            "description": "<p>SIS ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.username",
            "description": "<p>登录名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.nickname",
            "description": "<p>昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.fullname",
            "description": "<p>用户全名</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/AdminUseDataQuery.java",
    "groupTitle": "admin"
  },
  {
    "type": "get",
    "url": "/orgAdmin/list",
    "title": "机构管理员列表(含子机构)",
    "description": "<p>机构管理员列表</p>",
    "name": "orgAdminList",
    "group": "admin",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": true,
            "field": "entity",
            "description": "<p>结果</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.sisId",
            "description": "<p>SIS ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.username",
            "description": "<p>登录名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.nickname",
            "description": "<p>昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.fullname",
            "description": "<p>用户全名</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/people/OrgAdminDataQuery.java",
    "groupTitle": "admin"
  },
  {
    "type": "post",
    "url": "/org/deletes",
    "title": "机构删除",
    "description": "<p>机构删除</p>",
    "name": "orgDeletes",
    "group": "org",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>机构id</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/org/OrgDataEdit.java",
    "groupTitle": "org"
  },
  {
    "type": "post",
    "url": "/resShare/add",
    "title": "资源分享",
    "description": "<p>资源分享</p>",
    "name": "ShareAdd",
    "group": "resource",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "license",
            "description": "<p>版权</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "type",
            "description": "<p>资源类别</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "description",
            "description": "<p>简介</p>"
          },
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "tags",
            "description": "<p>标签</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "thumbnailUrl",
            "description": "<p>缩略图</p>"
          },
          {
            "group": "Parameter",
            "type": "Number[]",
            "optional": false,
            "field": "grade",
            "description": "<p>年级/等级</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity",
            "description": "<p>新增ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/resources/ResourceShareEdit.java",
    "groupTitle": "resource"
  },
  {
    "type": "get",
    "url": "/resAssignment",
    "title": "作业列表",
    "description": "<p>作业列表</p>",
    "name": "resAssignment",
    "group": "resource",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "curl -i /resAssignment?courseId=1",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>响应体</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list",
            "description": "<p>资源列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.title",
            "description": "<p>作业名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"courseId\": 1,\n\"createTime\": 1543520449000,\n\"createUserId\": 1,\n\"description\": \"大纲\",\n\"id\": 1,\n\"isIncludeCount\": 0,\n\"score\": 10,\n\"title\": \"第一章作业\",\n\"updateTime\": 1543520449000,\n\"updateUserId\": 1\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/resources/ResourceAssignmentQuery.java",
    "groupTitle": "resource"
  },
  {
    "type": "get",
    "url": "/resource/resAssignment/query",
    "title": "作业列表",
    "description": "<p>作业列表</p>",
    "name": "resAssignment",
    "group": "resource",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "curl -i /resAssignment?courseId=1",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>响应体</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list",
            "description": "<p>资源列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.title",
            "description": "<p>作业名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"courseId\": 1,\n\"createTime\": 1543520449000,\n\"createUserId\": 1,\n\"description\": \"大纲\",\n\"id\": 1,\n\"isIncludeCount\": 0,\n\"score\": 10,\n\"title\": \"第一章作业\",\n\"updateTime\": 1543520449000,\n\"updateUserId\": 1\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/resources/ResourceDiscussionQuery.java",
    "groupTitle": "resource"
  },
  {
    "description": "<p>我的课程</p>",
    "name": "resCourse",
    "group": "resource",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "vo",
            "description": "<p>删除课程ID列表字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"code\": \"yy\",\n\"createTime\": 1543503875000,\n\"createUserId\": 1,\n\"description\": \"一门外国语\",\n\"id\": 1,\n\"isEnd\": 0,\n\"name\": \"大学英语\",\n\"status\": 1,\n\"updateTime\": 1543503875000,\n\"updateUserId\": 1\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/resources/ResourceCourseQuery.java",
    "groupTitle": "resource"
  },
  {
    "type": "get",
    "url": "/resCourse/nav",
    "title": "课程导航",
    "description": "<p>课程导航</p>",
    "name": "resCourseNav",
    "group": "resource",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "/resCourse/nav?courseId=1",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": "<p>课程导航配置</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.navList",
            "description": "<p>课程导航列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.navList.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.navList.code",
            "description": "<p>Code</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.navList.seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.navList.status",
            "description": "<p>状态</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": {\n\"courseId\": 1,\n\"navList\": [\n{\n\"code\": \"course.nav.page\",\n\"name\": \"Pages\"\n}\n]\n},\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/resources/ResourceCourseNavQuery.java",
    "groupTitle": "resource"
  },
  {
    "type": "get",
    "url": "/resModule/list",
    "title": "单元列表",
    "description": "<p>单元列表</p>",
    "name": "resModule",
    "group": "resource",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "curl -i /resModule/list?courseId=1",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>响应体</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity..name",
            "description": "<p>资源类别</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity..itemsCount",
            "description": "<p>星级评价</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n    \"code\": 200,\n    \"entity\": [\n        {\n            \"completeStrategy\": 1,\n            \"courseId\": 1,\n            \"createTime\": 1543421076000,\n            \"createUserId\": 1,\n            \"id\": 1,\n            \"itemsCount\": 2,\n            \"name\": \"赵秀非测试1\",\n            \"startTime\": 1543392163000,\n            \"updateTime\": 1543421076000,\n            \"updateUserId\": 1\n        },\n        {\n            \"completeStrategy\": 1,\n            \"courseId\": 1,\n            \"createTime\": 1543421076000,\n            \"createUserId\": 1,\n            \"id\": 2,\n            \"itemsCount\": 2,\n            \"name\": \"赵秀非测试2\",\n            \"startTime\": 1543392211000,\n            \"updateTime\": 1543421076000,\n            \"updateUserId\": 1\n        },\n        {\n            \"courseId\": 1,\n            \"createTime\": 1543421947000,\n            \"createUserId\": 1,\n            \"id\": 3,\n            \"itemsCount\": 0,\n            \"name\": \"赵秀非测试333\",\n            \"updateTime\": 1543421947000,\n            \"updateUserId\": 1\n        }\n    ],\n    \"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/resources/ResourceModuleDataQuery.java",
    "groupTitle": "resource"
  },
  {
    "type": "get",
    "url": "/resource/resPage/query课程导航查询",
    "title": "",
    "description": "<p>课程导航查询</p>",
    "name": "resPage",
    "group": "resource",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Object",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "/resPage?courseId=1",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": "<p>课程导航配置</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.courseId",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "entity.navList",
            "description": "<p>课程导航列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.navList.name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.navList.code",
            "description": "<p>Code</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.navList.seq",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.navList.status",
            "description": "<p>状态</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": {\n\"courseId\": 1,\n\"navList\": [\n{\n\"code\": \"course.nav.page\",\n\"name\": \"Pages\",\n\"seq\": 1,\n\"status\": 1\n},\n{\n\"code\": \"course.nav.assignment\",\n\"name\": \"Assignments\",\n\"seq\": 0,\n\"status\": 1\n},\n{\n\"code\": \"course.nav.discussion\",\n\"name\": \"Discussions\",\n\"seq\": 0,\n\"status\": 1\n},\n{\n\"code\": \"course.nav.quiz\",\n\"name\": \"Quizzes\",\n\"seq\": 0,\n\"status\": 1\n},\n{\n\"code\": \"course.nav.module\",\n\"name\": \"Modules\",\n\"seq\": 0,\n\"status\": 1\n}\n]\n},\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/resources/ResourcePageQuery.java",
    "groupTitle": "resource"
  },
  {
    "type": "get",
    "url": "/resource/resQuiz/query",
    "title": "测验列表",
    "description": "<p>测验列表</p>",
    "name": "resQuiz",
    "group": "resource",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "courseId",
            "description": "<p>课程ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "curl -i /resQuiz?courseId=1",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>响应体</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list",
            "description": "<p>资源列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.score",
            "description": "<p>分值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.title",
            "description": "<p>名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.itemsCount",
            "description": "<p>包含小项数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"entity\": [\n{\n\"courseId\": 1,\n\"createTime\": 1543521917000,\n\"createUserId\": 1,\n\"description\": \"备注下\",\n\"id\": 1,\n\"isShuffleAnswer\": 0,\n\"itemsCount\": 2,\n\"score\": 20,\n\"status\": 0,\n\"title\": \"我叫测验\",\n\"updateTime\": 1543521917000,\n\"updateUserId\": 1\n}\n],\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/resources/ResourceQuizQuery.java",
    "groupTitle": "resource"
  },
  {
    "type": "get",
    "url": "/resSearch/get",
    "title": "资源详情",
    "description": "<p>资源详情</p>",
    "name": "resSearchGet",
    "group": "resource",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>资源ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "data=1",
          "type": "String"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Object",
            "optional": true,
            "field": "entity",
            "description": "<p>课程信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.id",
            "description": "<p>课程ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.courseName",
            "description": "<p>课程名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.courseTitle",
            "description": "<p>课程标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": true,
            "field": "entity.courseDesc",
            "description": "<p>课程描述</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.teacherGroupId",
            "description": "<p>课程所属教师组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.status",
            "description": "<p>课程状态，1：启用，0：禁用</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "0",
              "1"
            ],
            "optional": false,
            "field": "entity.deleteStatus",
            "description": "<p>课程删除状态，1：未删除，0：删除</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": true,
            "field": "entity.sort",
            "description": "<p>课程排序</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Example:",
          "content": "{\n\"code\": 200,\n\"entity\": {\n\"createTime\": 1541794674000,\n\"description\": \"我是自愿描述,资源描述哈哈哈哈哈哈哈哈哈\",\n\"fileUrl\": \"https://images2017.cnblogs.com/blog/400318/201711/400318-20171130152521433-1802989395.png\",\n\"grade\": 14,\n\"id\": 1,\n\"license\": \"public\",\n\"name\": \"张三的礼物\",\n\"originType\": \"res_type.course\",\n\"starRating\": 2,\n\"tags\": [\n{\n\"name\": \"测试1\"\n},\n{\n\"name\": \"测试2\"\n}\n]\n},\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/resources/ResourceSearchDataQuery.java",
    "groupTitle": "resource"
  },
  {
    "type": "get",
    "url": "/resSearch/pageList",
    "title": "资源查询分页",
    "description": "<p>资源查询分页,//TODO 作者信息未查询</p>",
    "name": "resSearchPageList",
    "group": "resource",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "allowedValues": [
              "{\"res_type.course\"",
              "\"res_type.assignment\"",
              "\"res_type.discussion\"",
              "\"res_type.page\"",
              "\"res_type.quiz\"",
              "\"res_type.module\"",
              "\"res_type.document\"",
              "\"res_type.video\"",
              "\"res_type.audio\"",
              "\"res_type.image\"}"
            ],
            "optional": false,
            "field": "type",
            "description": "<p>资源类别,可传入多个,用<code>,</code>分割</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "allowedValues": [
              "{0到16}"
            ],
            "optional": false,
            "field": "grade",
            "description": "<p>年级/等级,可传入多个,用<code>,</code>分割</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "curl -i /resSearch/pageList?name=张&grade=1,3&type=res_type.course",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "allowedValues": [
              "200",
              "500"
            ],
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>响应描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity",
            "description": "<p>响应体</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list",
            "description": "<p>资源列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.id",
            "description": "<p>资源id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.originType",
            "description": "<p>资源类别</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "entity.list.starRating",
            "description": "<p>星级评价</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.thumbnailUrl",
            "description": "<p>缩略图URL</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.name",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.list.author",
            "description": "<p>作者</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.pageIndex",
            "description": "<p>当前页</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.pageSize",
            "description": "<p>每页数据</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "entity.total",
            "description": "<p>总页数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回示例:",
          "content": "{\n\"code\": 200,\n\"entity\": {\n\"list\": [\n{\n\"createTime\": 1541794674000,\n\"author\": \"张三\",\n\"id\": 1,\n\"name\": \"张三的礼物\",\n\"originType\": \"res_type.course\",\n\"starRating\": 2,\n\"thumbnailUrl\": \"https://images2017.cnblogs.com/blog/400318/201711/400318-20171130152521433-1802989395.png\"\n}\n],\n\"pageIndex\": 1,\n\"pageSize\": 20,\n\"total\": 1\n},\n\"message\": \"common.success\"\n}\n<p>",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/resources/ResourceSearchDataQuery.java",
    "groupTitle": "resource"
  },
  {
    "type": "post",
    "url": "/userSetting/exist/edit",
    "title": "邮件地址是否存在",
    "name": "emailIsExist",
    "group": "user",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "email",
            "description": "<p>用户关联邮件</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>响应码，200为处理成功，其他处理失败</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "status",
            "description": "<p>0:表示不存在；1或者大于1的值：表示存在；</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/user/EmailIsExist.java",
    "groupTitle": "user"
  },
  {
    "description": "<p>用户设置信息查找</p>",
    "name": "userSettingFind",
    "group": "user",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "请求示例:",
        "content": "http://localhost:8080/userSetting/get?data=2",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>业务码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>code描述</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应示例：",
          "content": "{\n\"code\": 200,\n\"entity\": {\n\"createTime\": 1551993830000,\n\"createUserId\": 1,\n\"description\": \"6\",\n\"email\": \"15\",\n\"emails\": [\n{\n\"createTime\": 1550712545000,\n\"createUserId\": 2,\n\"email\": \"1@126.com\",\n\"id\": 6,\n\"isDefault\": 0,\n\"updateTime\": 1550712545000,\n\"updateUserId\": 2,\n\"userId\": 2\n},\n{\n\"createTime\": 1550713082000,\n\"createUserId\": 2,\n\"email\": \"2@126.com\",\n\"id\": 10,\n\"isDefault\": 0,\n\"updateTime\": 1550713082000,\n\"updateUserId\": 2,\n\"userId\": 2\n}\n],\n\"fullName\": \"4\",\n\"id\": 2,\n\"isRegistering\": 0,\n\"language\": \"10\",\n\"links\": [\n{\n\"createTime\": 1550713090000,\n\"createUserId\": 2,\n\"id\": 1,\n\"name\": \"name\",\n\"updateTime\": 1550713090000,\n\"updateUserId\": 2,\n\"url\": \"url\",\n\"userId\": 2\n}\n],\n\"nickname\": \"student\",\n\"orgId\": 1,\n\"password\": \"9\",\n\"phone\": \"7\",\n\"sex\": 0,\n\"status\": 1,\n\"timeZone\": \"11\",\n\"title\": \"8\",\n\"updateTime\": 1552094423000,\n\"updateUserId\": 1,\n\"username\": \"5\"\n},\n\"message\": \"common.success\"\n}",
          "type": "json"
        }
      ]
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "/home/zhangzhen/workspace/wd/wd-lms/platform/src/main/java/com/wdcloud/lms/business/user/UserSettingQuery.java",
    "groupTitle": "user"
  }
] });
