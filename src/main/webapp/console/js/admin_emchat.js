/**
 * Created by Jeffrey on 15/7/6.
 */

var xmppURL = null;
var apiURL = null;

window.URL = window.URL || window.webkitURL || window.mozURL
    || window.msURL;

var connMap = new Map();
var userMap = new Map();

var emchat = function (user, password, appPlatform, appKey) {
    var conn = connMap.size().toString();
    connMap.put(conn,new Easemob.im.Connection());
    initConnection(connMap.get(conn));
    login(user, password, appKey, connMap.get(conn));
    userMap.put(user.toLowerCase() + "#" + appKey, appPlatform);
}


//定义消息编辑文本域的快捷键，enter和ctrl+enter为发送，alt+enter为换行
//easemobwebim-sdk注册回调函数列表
$(document).ready(function () {
    emchat("Jeffrey01", "1", "高血糖", "xlab#ugyufuy");
});
