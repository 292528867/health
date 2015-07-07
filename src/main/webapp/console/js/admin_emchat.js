/**
 * Created by Jeffrey on 15/7/6.
 */

var xmppURL = null;
var apiURL = null;

window.URL = window.URL || window.webkitURL || window.mozURL
    || window.msURL;

var connMap = new Map();
var userMap = new Map();

var textSending = false;

var emchat = function (user, password, appPlatform, appKey) {
    var connKey = user.toLowerCase() + "#" + appKey;
    connMap.put(connKey, new Easemob.im.Connection());
    initConnection(connMap.get(connKey));
    login(user, password, appKey, connMap.get(connKey));
    userMap.put(connKey, appPlatform);
}

function submitQuestion(){
    var question = $("#question").val();
    var appName = [];
    var askUrl = commonUrl + "/third/ask";

    //validate
    if(!question.trim()){
        alert("请输入问题");
        return;
    }

    if($("#appNames :checked").length == 0){
        alert("请选择要发送的app");
        return;
    }

    //alert(question);
    $("#appNames :checked").each(function(){
            appName.push($(this).val());
        }
    );

    var json ={
        "question": question,
        "appName": appName,
        "appType": "",
        "department": "",
        "fromUser": ""
    };
    json = JSON.stringify(json);
    $.ajax({
        url: askUrl,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            if (response.ret_code == 0) {
                //alert('提问成功');
                appendMsg("运营", "", question, "", null, "local");
                //location.reload();
                $("#question").val("");
            }
            else
                alert(response.err_msg);
        },
        error: function () {
            alert('error');
        }


    });
}

function chooseApp(para){
    var doctor = $(para).find(".doctor").text();
    var user = $(para).find(".user").text();
    var appkey = $(para).find(".appkey").text();

    $("#answerAppKey").val(appkey);
    $("#answerDoctor").val(doctor);
    $("#answerUser").val(user);

}


//定义消息编辑文本域的快捷键，enter和ctrl+enter为发送，alt+enter为换行
//easemobwebim-sdk注册回调函数列表
$(document).ready(function () {
    emchat("jeffrey01", "1", "高血糖", "xlab#ugyufuy");
    emchat("139334", "c120c8ee8b14b2b13f0bd4f6706df8b8", "薏米医生", "ememed#ememeduserofficial");
    emchat("15021470585", "15021470585", "血糖高管", "mixin716#bsksugar");
    $("#question").val("");
    $("#answers").val("");
});
