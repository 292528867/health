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


用户轻问诊发送消息接口
接口地址：http://10.1.64.179:8080/xlab-healthcloud/em/sendTxtMessage
method： POST
RequestBody:
{
    “target_type” : “chatgroups”, 
    “target” : [“{userGroupId}”],          
    “msg” : {
        “type” : “txt”,
        “msg” : “{messages}” 
        },
    “from” : “{用户环信账号}”, 
    “ext” : { 
    }    
}

ResponseBody:
{
    "ret_code": 0,
    "ret_values": "",
    "message": "文本消息发送成功"
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
