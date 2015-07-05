var url = 'http://127.0.0.1:8990/';

$(document).ready(function () {

    loadData();

});



//加载需要查看的信息
function loadData(){
    alert(1111);
    $.ajax({
        type: 'get',
        url: url+'em/query',
        success: function (data) {
            var str = '';
            for (var i = 0; i < data.length; i++) {
                str += '<tr>';
                str += '<td>'+data[i].fromUser+'</td>';
                str += '<td>' + data[i].toUsers + '</td>';
                str += '<td>' + data[i].msg + '</td>';
                str += '<td class="am-hide-sm-only">' + data[i].createdDate + '</td>';
                str += '<td>' + data[i].targetType + '</td>';
                str += '<td><div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">';
                // alert('replyInfo("+data[i].fromUser+","+data[i].toUsers.toString()+")');
                str += "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick=\"replyInfo('"+data[i].fromUser+"','"+data[i].toUsers+"')\">";
                str += '<span class="am-icon-pencil-square-o"></span>回复</button>';
                str += '</div></div></td></tr>';
            }
            $("#replyInfoList").html(str);
        }
    });
}

function replyInfo(fromUser, toUser) {

    $("#reply-modal").modal('toggle');


    //提交回复信息
    $("#type-reply").click(function() {
        var content = $("#content").val();
        //特别要注意 target必须是个数组
        target =[];
        target.push(""+fromUser+"");
        message = {
            'target_type': 'users',
            'target': target,
            'msg': {
                'type': 'txt',
                'msg': content
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
                $("#reply-modal").modal('toggle');
            }
        });
    });

}