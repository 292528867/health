/**
 * Created by Jeffrey on 15/7/6.
 */


var initConnection = function (conn) {
    //初始化连接
    conn.init({
        https: false,
        wss: false,
        url: xmppURL,
        //当连接成功时的回调方法
        onOpened: function () {
            handleOpen(conn);
        },
        ////当连接关闭时的回调方法
        //onClosed: function () {
        //    handleClosed();
        //},
        //收到联系人订阅请求的回调方法
        onPresence: function (message) {
            handlePresence(conn, message);
        },
        //收到联系人信息的回调方法
        onRoster : function(message) {
            handleRoster(message);
        },
        //收到文本消息时的回调方法
        onTextMessage: function (message) {
            handleTextMessage(conn, message);
        },
        //异常时的回调方法
        onError: function (message) {
            handleError(conn,message);
        }
    });

    $(function () {
        $(window).bind('beforeunload', function () {
            if (conn) {
                conn.close();
                if (navigator.userAgent.indexOf("Firefox") > 0)
                    return ' ';
                else
                    return '';
            }
        });
    });
};

//处理连接时函数,主要是登录成功后对页面元素做处理
var handleOpen = function (conn) {
    //从连接中获取到当前的登录人注册帐号名
    console.log(conn.context.userId);
    //获取当前登录人的联系人列表
    conn.getRoster({
        success: function (roster) {
            //获取当前登录人的群组列表
            conn.listRooms({
                success: function (rooms) {
                    conn.setPresence();//设置用户上线状态，必须调用
                },
                error: function (e) {

                }
            });
        }
    });

    //启动心跳
    if (conn.isOpened()) {
        conn.heartBeat(conn);
    }
};

//异常情况下的处理方法
var handleError = function (conn,e) {
    conn.stopHeartBeat(conn);
};


//easemobwebim-sdk收到文本消息的回调方法的实现
var handleTextMessage = function (conn,message) {
    var from = message.from;//消息的发送者
    var to = message.to;
    var mestype = message.type;//消息发送的类型是群组消息还是个人消息
    var messageContent = message.data;//文本消息体
    //TODO  根据消息体的to值去定位那个群组的聊天记录
    if (mestype == 'groupchat') {
        appendMsg(from, to, messageContent, mestype,conn, null);
    } else {
        appendMsg(from, to, messageContent, mestype,conn, null);
    }
};

//easemobwebim-sdk中收到联系人订阅请求的处理方法，具体的type值所对应的值请参考xmpp协议规范
var handlePresence = function (conn,e) {
    //（发送者希望订阅接收者的出席信息），即别人申请加你为好友
    if (e.type == 'subscribe') {
        console.log(e.from + "申请添加你为好友！");
        if (e.status) {
            if (e.status.indexOf('resp:true') > -1) {
                agreeAddFriend(conn,e.from);
                return;
            }
        }
        //同意好友请求
        agreeAddFriend(conn,e.from);//e.from用户名
        //反向添加对方好友
        conn.subscribe({
            to : e.from,
            message : "[resp:true]"
        });
        return;
    }
    //(发送者允许接收者接收他们的出席信息)，即别人同意你加他为好友
    if (e.type == 'subscribed') {
        toRoster.push({
            name : e.from,
            jid : e.fromJid,
            subscription : "to"
        });
        return;
    }
    //（发送者取消订阅另一个实体的出席信息）,即删除现有好友
    if (e.type == 'unsubscribe') {
        //单向删除自己的好友信息，具体使用时请结合具体业务进行处理
        delFriend(conn, e.from);
        return;
    }
    //（订阅者的请求被拒绝或以前的订阅被取消），即对方单向的删除了好友
    if (e.type == 'unsubscribed') {
        console.log(e.from + "对方单向的删除了好友");
        delFriend(conn, e.from);
        return;
    }
};

//easemobwebim-sdk中处理出席状态操作
var handleRoster = function(rosterMsg) {
    for (var i = 0; i < rosterMsg.length; i++) {
        var contact = rosterMsg[i];
        if (contact.ask && contact.ask == 'subscribe') {
            continue;
        }
        if (contact.subscription == 'to') {
            toRoster.push({
                name : contact.name,
                jid : contact.jid,
                subscription : "to"
            });
        }
        //app端删除好友后web端要同时判断状态from做删除对方的操作
        if (contact.subscription == 'from') {
            toRoster.push({
                name : contact.name,
                jid : contact.jid,
                subscription : "from"
            });
        }
        if (contact.subscription == 'both') {
            bothRoster.push(contact);
        }
    }
};

//回调方法执行时同意添加好友操作的实现方法
var agreeAddFriend = function(conn,user) {
    conn.subscribed({
        to : user,
        message : "[resp:true]"
    });
};

//回调方法执行时删除好友操作的方法处理
var delFriend = function(conn, user) {
    conn.removeRoster({
        to : user,
        groups : [ 'default' ],
        success : function() {
            conn.unsubscribed({
                to : user
            });
        }
    });
};

//显示聊天记录的统一处理方法
var appendMsg = function (who, contact, message, chattype,conn, local) {
    console.log("--------------------------");
    var appPlatform = null;
    var appKey = null;
    if(conn){
        appPlatform = userMap.get(contact + "#" + conn.context.appKey);
        appKey = conn.context.appKey;
    }
    var divTag = "<div onclick='chooseApp(this)'>";
    if(local){
        divTag = "<div class=\"am-lg-text-right\">"
        appPlatform = "运营同学";
    }


    var messageContent = divTag +
        "<p1>" + (null == appPlatform ? "" : (appPlatform + "  ")) + " <span>"+who+"</span></p1>" +
        "<p2> " + getLoacalTimeString() + "<b></b><br></p2>" +
        "<p3 class='message-content' style='background-color: rgb(235, 235, 235);'> " + message +
        " </p3> " +
        "<div style='display:none'>"+
        "<span class='appkey'>" +  appKey + "</span>"+
        "<span class='doctor'>"+ who +"</span>"+
        "<span class='user'>"+ contact +"</span>"+
        "</div>"+
        "</div>";
    $(".message-body").append(messageContent);
};

var getLoacalTimeString = function getLoacalTimeString() {
    var date = new Date();
    var time = date.getHours() + ":" + date.getMinutes() + ":"
        + date.getSeconds();
    return time;
}

//登录系统时的操作方法
var login = function (user,pass,appkey,conn) {
    //根据用户名密码登录系统
    conn.open({
        apiUrl: apiURL,
        user: user,
        pwd: pass,
        //连接时提供appkey
        appKey: appkey
    });
    return false;
};

var sendText = function() {
    if (textSending) {
        return;
    }
    textSending = true;

    //var msgInput = $("answers").val();

    var msg = $("#answers").val();

    var appKey = $("#answerAppKey").val();
    var doctor = $("#answerDoctor").val();
    var user = $("#answerUser").val();

    if(appKey == null || appKey.length == 0){
        alert("请点击需要回复的消息");
        return;
    }
    if (msg == null || msg.length == 0) {
        alert("请输入回复内容");
        return;
    }

    var connKey = user + "#" + appKey;
    debugger;

    var conn = connMap.get(connKey);
    var to = doctor;
    if (to == null) {
        return;
    }
    var options = {
        to : to,
        msg : msg,
        type : "chat"
    };
    // 群组消息和个人消息的判断分支
    //if (curChatUserId.indexOf(groupFlagMark) >= 0) {
    //    options.type = 'groupchat';
    //    options.to = curRoomId;
    //}
    //easemobwebim-sdk发送文本消息的方法 to为发送给谁，meg为文本消息对象
    conn.sendTextMessage(options);
    //将发送的消息，显示到屏幕上方对话框
    appendMsg("运营", user, msg, "", conn, "local");
    //清空发送框
    $("#answers").val("");
    //当前登录人发送的信息在聊天窗口中原样显示

    setTimeout(function() {
        textSending = false;
    }, 1000);
};