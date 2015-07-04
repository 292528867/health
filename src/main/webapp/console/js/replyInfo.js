var url = 'http://127.0.0.1:8080';

$(document).ready(function () {

    //加载需要查看的信息
    $.ajax({
        type: 'get',
        url: url+'em/query',
        success: function (data) {
            var str = '';
            for (var i = 0; i < data.length; i++) {
                str += '<tr>';
                str += '<td>" + data[i].fromUser + "</td>';
                str += '<td>" + data[i].toUsers.toString() + "</td>';
                str += '<td>" + data[i].msg + "</td>';
                str += '<td class="am-hide-sm-only">" + data[i].createdDate + "</td>';
                str += '<td>" + data[i].targetType + "</td>';
                str += '<td><div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">';
                // alert('replyInfo("+data[i].fromUser+","+data[i].toUsers.toString()+")');
                str += '<button class="am-btn am-btn-default am-btn-xs am-text-secondary" type="button" onclick="replyInfo("+data[i].fromUser+","+data[i].toUsers.toString()+")">';
                str += '<span class="am-icon-pencil-square-o"></span>回复</button>';
                str += '</div></div></td></tr>';
            }
            $("#replyInfoList").html(str);
        }
    });

});


function replyInfo(fromUser, toUser) {
    alert(fromUser);
    var fromUser = "yu";
    toUser = '["1111"]';
    content = $("#content").html();
    message = {
        'target_type': 'users',
        'target': fromUser,
        'msg': {
            'type': 'txt',
            'msg': '1111'
        },
        'from': toUser
    };

    $.ajax({
        'url': url+'/em/sendMessage',
        'type': 'POST',
        'data': JSON.stringify(message),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json; charset=UTF-8"
        },
        'dataType': 'json',
        success: function (result) {
            alert(1);
        }
    });
}