获取用户已加入计划列表接口：

    接口地址：http://10.1.64.179:8080/xlab-healthcloud/hcPackage/checked/{userId}
    method: GET
    ResponseBody:
{
    "ret_code": 0,
    "ret_values": [
        {
            "id": 1,
            "createdDate": 1435981576000,
            "lastModifiedDate": 1435981576000,
            "title": "7天拯救肩周炎",
            "description": "美丽好心情",
            "detailDescription": "",
            "duration": 7,
            "icon": "http://www.people.com.cn/h/pic/20150708/78/12578659605057167830.jpg",
            "detailDescriptionIcon": "",
            "recommend": true,
            "recommendValue": 1,
            "clickAmount": 0,
            "joinAmount": 0,
            "sex": "",
            "supplemented": "",
            "loops": "0",
            "healthCategory": "",
            "cycleLimit": 0,
            "smaillIcon": "http://7xk3mz.com2.z0.glb.qiniucdn.com/healthinfo-icon-1436357471981",
            "needSupplemented": false,
            "new": false
        }
    ],
    "message": "订阅计划列表获取成功！"
}

获取分类下计划包并标记用户是否加入
    接口地址：http://10.1.64.179:8080/xlab-healthcloud/hcPackage/listPackage/{classifitionId}/{userId}
    method:GET
    ResponseBody:
{
    "ret_code": 0,
    "ret_values": [
        {
            "id": 1,
            "title": "7天拯救肩周炎",
            "description": "美丽好心情",
            "detailDescription": "",
            "duration": 7,
            "icon": "http://www.people.com.cn/h/pic/20150708/78/12578659605057167830.jpg",
            "detailDescriptionIcon": "",
            "recommend": true,
            "recommendValue": 1,
            "clickAmount": 0,
            "joinAmount": 0,
            "sex": "",
            "supplemented": "",
            "loops": "0",
            "healthCategory": "",
            "cycleLimit": 0,
            "isJoin": true,
            "needSupplemented": false
        },
        {
            "id": 3,
            "title": "慢性病",
            "description": "慢性病",
            "detailDescription": "",
            "duration": 7,
            "icon": "http://www.people.com.cn/h/pic/20150708/78/12578659605057167830.jpg",
            "detailDescriptionIcon": "",
            "recommend": true,
            "recommendValue": 3,
            "clickAmount": 0,
            "joinAmount": 0,
            "sex": "",
            "supplemented": "",
            "loops": "0",
            "healthCategory": "",
            "cycleLimit": 0,
            "isJoin": false,
            "needSupplemented": false
        },
        {
            "id": 4,
            "title": "孕检",
            "description": "孕检",
            "detailDescription": "",
            "duration": 7,
            "icon": "http://www.people.com.cn/h/pic/20150708/78/12578659605057167830.jpg",
            "detailDescriptionIcon": "",
            "recommend": true,
            "recommendValue": 4,
            "clickAmount": 0,
            "joinAmount": 0,
            "sex": "",
            "supplemented": "",
            "loops": "0",
            "healthCategory": "",
            "cycleLimit": 0,
            "isJoin": false,
            "needSupplemented": false
        }
    ],
    "message": "成功"
}

获取分类信息
接口地址：http://10.1.64.179:8080/xlab-healthcloud/hcPackage/listPackageInfo
method：GET
ResponseBody:
{
    "ret_code": 0,
    "ret_values": [
        {
            "id": 1,
            "title": "办公室减肥计划",
            "iconUrl": "http://7xk3mz.com2.z0.glb.qiniucdn.com/healthinfo-icon-1435981270967"
        },
        {
            "id": 2,
            "title": "氧气美女养成大法",
            "iconUrl": "http://7xk3mz.com2.z0.glb.qiniucdn.com/healthinfo-icon-1435981270967"
        }
    ],
    "message": "成功"
}
加入包计划接口
 接口地址：http://10.1.64.179:8080/xlab-healthcloud/plan/join/{userId}/{packageId}
method：POST
ResponseBody:
{
    "ret_code": 0,
    "ret_values": "",
    "message": "加入成功！"
}

获取app菜单接口
接口地址：http://10.1.64.179:8080/xlab-healthcloud/menu/findMenus
method：GET/POST
ResponseBody:
{
    "ret_code": 0,
    "ret_values": [
        {
            "id": 1,
            "title": "首页",
            "appMenuType": 0,
            "icon": "",
            "menuSort": 1,
            "new": false
        },
        {
            "id": 2,
            "title": "计划",
            "appMenuType": 1,
            "icon": "",
            "menuSort": 2,
            "new": false
        }
    ],
    "message": "获取成功！"
}

取消选定健康包接口
接口地址：http://10.1.64.179:8080/xlab-healthcloud/plan/cansel/{userId}/{packageId}
method: DELETE
ResponseBody:
{
    "ret_code": 0,
    "ret_values": "",
    "message": "取消成功！"
}
健康包关注自增接口
接口地址：http://10.1.64.179:8080/xlab-healthcloud/hcPackage/packageClick/{packageId}
method:GET
ResponseBody:
{
    "ret_code": 0,
    "ret_values": "",
    "message": "关注成功！"
}

医生端接口
医生登陆接口
接口地址：http://10.1.64.179:8080/xlab-healthcloud/doctor/hclogin
method：POST
RequestBody:
{
 "tel":"{tel}",
 "code":"{code}",
 "appPlatform":"{appPlatform}"
}
ResponseBody:

{
    "ret_code": 0,
    "ret_values": {
        "id": 1,
        "height": 0,
        "weight": 0,
        "age": 0,
        "tel": "17721013012",
        "iconUrl": "",
        "nickName": "",
        "sex": "Unkown",
        "birthday": "",
        "createdDate": 1436683174000,
        "lastModifiedDate": 1436683174000,
        "recordUrl": "",
        "appPlatform": "Ios",
        "iCardName": "",
        "qualificationUrl": "",
        "qualificationName": "",
        "permitUrl": "",
        "checked": "0",
        "hospital": "",
        "department": "",
        "valid": "0",
        "integral": 0,
        "new": false
    },
    "message": "成功"
}
注:返回结果中valid为是否填写是否允许进入首页枚举 0 表示无效，1表示有效，
    ​checked为认证审核枚举 0表示

 医生基本信息更新接口
接口地址：http://10.1.64.179:8080/xlab-healthcloud/doctor/updateBaseInfo/{doctorId}
method：POST
RequestBody:
 {
    "iconUrl": "www.baidu.com",
    "sex": "Male",
    "iCardName": "xxxxxx",
    "hospital": "上海第七人民医院",
    "department": "儿科",
    "qualificationName": "医师",
   "age":56,
   "weight":56,
   "height":170
}
ResponseBody:


{
    "ret_code": 0,
    "ret_values": {
        "id": 1,
        "height": 170,
        "weight": 56,
        "age": 56,
        "tel": "17721013012",
        "iconUrl": "www.baidu.com",
        "nickName": "",
        "sex": "Male",
        "birthday": "",
        "createdDate": 1436683174000,
        "lastModifiedDate": 1436683325211,
        "recordUrl": "",
        "appPlatform": "Ios",
        "iCardName": "xxxxxx",
        "qualificationUrl": "",
        "permitUrl": "",
        "checked": "undo",
        "hospital": "上海第七人民医院",
        "department": "儿科",
       "qualificationName": "医师",
        "valid": "valid",
        "integral": 0,
        "new": false
    },
    "message": "更新信息成功！"
}


医生资格认证接口更新接口
接口地址：http://10.1.64.179:8080/xlab-healthcloud/doctor/certification/{doctorId}
method:POST
Request Headers:{"Content-Type":"multipart/form-data"}
RequestBody:文件表单
       "icon":{icon}
       "iCard":{iCard}
       "qualification":{qualification}
       "permit":{permit}

ResponseBody:
{
    "ret_code": 0,
    "ret_values": "",
    "message": "上传认证成功！"
}


医生回复消息接口
接口地址：http://10.1.64.179:8080/xlab-healthcloud/em/replyMessage/{msg_id}/{userTel} 
method：POST
RequestBody:
{
    “target_type” : “chatgroups”, 
    “target” : [“82121541407474076”],                               
    “msg” : {
        “type” : “txt”,
        “msg” : “hello from rest” 
        },
    “from” : “{doctor环信账号}”, 
    “ext” : { 
    }    
}
ResponseBody:
{
    "ret_code": 0,
    "ret_values": "",
    "message": "消息发送成功"
}

医生抢单接口
接口地址：http://10.1.64.179:8080/xlab-healthcloud/em/rushOrder/{userTel}/{doctorTel}
method：POST/GET
RequestBody: null
ResponseBody:
{
    "ret_code": 0,
    "ret_values": {
        "userId":1,
        "groupId: "82547740730458536",
        "userTel":"13901694939"
    },
    "message": "消息发送成功"
}


医生查询订单接口
接口地址：http://10.1.64.179:8080/xlab-healthcloud/em/doctorOrders/{doctorId}
method：GET
RequestBody: null
ResponseBody:
{
    "ret_code": 0,
    "ret_values": {
        "orders": [
            {
                "id": 31,
                "createdDate": 1436878794000,
                "lastModifiedDate": 1436882734000,
                "user": {
                    "id": 110,
                    "height": 170,
                    "weight": 57,
                    "age": 1,
                    "tel": "15021470585",
                    "iconUrl": "",
                    "nickName": "",
                    "sex": "Male",
                    "birthday": "",
                    "createdDate": 1436868409000,
                    "lastModifiedDate": 1436868429000,
                    "hcPackages": "",
                    "hcs": [],
                    "valid": 1,
                    "appPlatform": "Android",
                    "groupId": "82908400114991516",
                    "integrals": 0,
                    "inviteCode": "n027",
                    "inviteUrl": "",
                    "byInviteCode": "",
                    "new": false
                },
                "messages": {
                    "id": 262,
                    "createdDate": 1436878794000,
                    "lastModifiedDate": 1436878794000,
                    "fromUser": "15021470585",
                    "toUser": "82908400114991516",
                    "toUsers": [
                        "82908400114991516"
                    ],
                    "msg": "东方红",
                    "fileUrl": "",
                    "isReplied": false,
                    "messageType": "txt",
                    "chatName": "",
                    "targetType": "chatgroups",
                    "ext": "",
                    "doctorFlag": true,
                    "isShowForDoctor": 0,
                    "groupId": "",
                    "new": false
                },
                "doctor": "",
                "questionStatus": "processing",
                "pushCount": 4,
                "new": false
            },
            {
                "id": 41,
                "createdDate": 1436881260000,
                "lastModifiedDate": 1436943724000,
                "user": "",
                "messages": {
                    "id": 278,
                    "createdDate": 1436881260000,
                    "lastModifiedDate": 1436943724000,
                    "fromUser": "15802132098",
                    "toUser": "82106668153835928",
                    "toUsers": [
                        "82106668153835928"
                    ],
                    "msg": "hello from rest",
                    "fileUrl": "",
                    "isReplied": true,
                    "messageType": "txt",
                    "chatName": "",
                    "targetType": "chatgroups",
                    "ext": "",
                    "doctorFlag": true,
                    "isShowForDoctor": 0,
                    "groupId": "",
                    "new": false
                },
                "doctor": "",
                "questionStatus": "done",
                "pushCount": 5,
                "new": false
            }
        ],
        "newQuestion": ""
    },
    "message": "success"
}
参数说明：
ret_values中的问题对象按照问题的状态和提问时间升序排列
createdDate: 提问时间
user: 提问的用户信息
messages: 提问的问题对象
	id: messageId 医生回复问题的接口需要用到该值
	msg: 问题内容
	toUser: 用户chatGroupId 医生回复问题时，需要发送至该chatGroupId
	toUsers: 环信api需要，本接口中不需要处理
questionStatus: 问题状态。processing为等待回答，done为已回答


医生切换问题接口
给医生返回一个新的问题（问题队列中，推送次数最少的问题）
接口地址: http://10.1.64.179:8080/xlab-healthcloud/em/getNewQuestion
method：GET
RequestBody:null
ResponseBody:
{
    "ret_code": 0,
    "ret_values": {
        "id": 88,
        "createdDate": 1436947431000,
        "lastModifiedDate": 1436947431000,
        "user": {
            "id": 106,
            "height": 160,
            "weight": 51,
            "age": 27,
            "tel": "13727791297",
            "iconUrl": "",
            "nickName": "",
            "sex": "Female",
            "birthday": "",
            "createdDate": 1436864657000,
            "lastModifiedDate": 1436864700000,
            "hcPackages": "",
            "hcs": [],
            "valid": 1,
            "appPlatform": "Ios",
            "groupId": "82892284881797536",
            "integrals": 0,
            "inviteCode": "p769",
            "inviteUrl": "",
            "byInviteCode": "",
            "new": false
        },
        "messages": {
            "id": 381,
            "createdDate": 1436947431000,
            "lastModifiedDate": 1436947431000,
            "fromUser": "13727791297",
            "toUser": "82892284881797536",
            "toUsers": [
                "82892284881797536"
            ],
            "msg": "抢单测试来一个7",
            "fileUrl": "",
            "isReplied": false,
            "messageType": "txt",
            "chatName": "",
            "targetType": "chatgroups",
            "ext": "",
            "doctorFlag": true,
            "isShowForDoctor": 0,
            "groupId": "",
            "new": false
        },
        "doctor": "",
        "questionStatus": "newQuestion",
        "pushCount": 0,
        "new": false
    },
    "message": "success"
}

参数说明：
ret_values中只有一个问题对象
createdDate: 提问时间
user: 提问的用户信息
messages: 提问的问题对象
	id: messageId 医生回复问题的接口需要用到该值
	msg: 问题内容
	toUser: 用户chatGroupId 医生回复问题时，需要发送至该chatGroupId
	toUsers: 环信api需要，本接口中不需要处理
questionStatus: 问题状态。newQuestion为新问题

进入问诊页面接口
 http://101.231.124.8:45677/xlab-healthcloud/em/toInterrogation/{userTel}
请求方式：get
 返回结果说明：
   lastQuestionStatus：  最新问题状态 0进入回答页面 1进入等待页面
   content:  例子提示
   list :当天的聊天记录
   groupId:用户的群组id

{
    "ret_code": 0,
    "ret_values": {
        "lastQuestionStatus": 0,
        "content": "我40岁，糖尿病7年，血糖一直偏高，空腹血糖一直在9左右，餐后血糖13左右。一直在吃阿卡波糖片，前段时间换了药，不但血糖没有降低，反而出现了心慌、胸闷、气短的症状。现在不知道要怎么办，需要打胰岛素吗？",
            "list": [
            {
                "id": 355,
                "createdDate": 1436932261000,
                "lastModifiedDate": 1436932261000,
                "fromUser": "17091959006",
                "toUser": "82121541407474076",
                "toUsers": [
                    "82121541407474076"
                ],
                "msg": "adfasf",
                "fileUrl": "",
                "isReplied": true,
                "messageType": "txt",
                "chatName": "",
                "targetType": "chatgroups",
                "ext": "",
                "doctorFlag": false,
                "isShowForDoctor": 0,
                "groupId": "",
                "new": false
            }
        ],
        "groupId": "82121541407474076"
    },
    "message": ""
}

轻问诊用户或者医生聊天历史记录查询接口
http://101.231.124.8:45677/xlab-healthcloud/em/queryRecords?groupId=82889700230037920&page=0&type=user
请求方式：get
参数说明：type  user用户查询聊天记录（有提示语） doctor 医生查询历史记录 page＝0 第一页
返回参数说明：isReplied true 回复的 false 未回答

{
    "ret_code": 0,
    "ret_values": [
        {
            "id": 310,
            "createdDate": 1436884715000,
            "lastModifiedDate": 1436884715000,
            "fromUser": "15800691691",
            "toUser": "82889700230037920",
            "toUsers": [
                "82889700230037920"
            ],
            "msg": "抢单测试来一个10",
            "fileUrl": "",
            "isReplied": false,
            "messageType": "txt",
            "chatName": "",
            "targetType": "chatgroups",
            "ext": "",
            "doctorFlag": true,
            "isShowForDoctor": 0,
            "groupId": "",
            "new": false
        }
    ],
    "message": ""
}

轻问诊用户发送消息接口
http://101.231.124.8:45677/xlab-healthcloud/em/sendTxtMessage
请求方式：post
参数说明：
{
    "target_type" : "chatgroups",
    "target" : ["82121541407474076"], //用户的环信聊天组ID groupId
    "msg" : {
        "type" : "txt",
        "msg" : "hello from rest4"  //发送消息内容
        },
    "from" : "15000367081",  //用户环信账号
    "ext" : {
    }
}
 返回结果
{
    "ret_code": 0,
    "ret_values": {
        "waiting": "此刻我们已从专业团队中预约了最符合您情况的专家为您解答困惑，为了您的健康，给专家留一小会时间吧~"
    },
    "message": "文本消息发送成功"
}
说明：waiting 进入等待页面的提示语

id获取用户信息接口
接口地址：http://101.231.124.8:45677/xlab-healthcloud/user/findUser/{userId}
method: GET
RequestBody: null
ResponseBody:
{
    "ret_code": 0,
    "ret_values": {
        "id": 8,
        "height": 170,
        "weight": 62,
        "age": 19,
        "tel": "15021470583",
        "iconUrl": "",
        "nickName": "",
        "sex": "Male",
        "birthday": "",
        "createdDate": 1435927518000,
        "lastModifiedDate": 1436341795000,
        "hcPackages": "",
        "hcs": [],
        "valid": 1,
        "appPlatform": "Android",
        "groupId": "",
        "integrals": 0,
        "inviteCode": "A785",
        "inviteUrl": "http://7xk3mz.com2.z0.glb.qiniucdn.com/healthinfo-icon-1436850530261",
        "byInviteCode": "",
        "new": false
    },
    "message": "用户信息获取成功！"
}


id获取医生信息接口
接口地址：http://101.231.124.8:45677/xlab-healthcloud/doctor/findDoctor/{doctorId}
method: GET
RequestBody: null
ResponseBody:
{
    "ret_code": 0,
    "ret_values": {
        "id": 8,
        "height": 0,
        "weight": 0,
        "age": 0,
        "tel": "13795286610",
        "iconUrl": "http://7xk3mz.com2.z0.glb.qiniucdn.com/1437010159576icon.jpg",
        "nickName": "",
        "sex": "Unkown",
        "birthday": "",
        "createdDate": 1436869272000,
        "lastModifiedDate": 1437010185000,
        "iCardUrl": "http://7xk3mz.com2.z0.glb.qiniucdn.com/1437010160116iCard.jpg",
        "appPlatform": "Ios",
        "iCardName": "顾",
        "qualificationUrl": "http://7xk3mz.com2.z0.glb.qiniucdn.com/1437010160720qualification.jpg",
        "qualificationName": "第一",
        "permitUrl": "http://7xk3mz.com2.z0.glb.qiniucdn.com/1437010162174permit.jpg",
        "checked": 3,
        "hospital": "第一医院",
        "department": "第一课时",
        "valid": 1,
        "integral": 0,
        "new": false
    },
    "message": "医生信息获取成功！"
}

