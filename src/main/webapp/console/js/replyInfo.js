//var url = 'http://101.231.124.8:45677/xlab-healthcloud/';
var url = 'http://127.0.0.1:8080/';

$(document).ready(function () {

    //加载医生下拉框信息
    $.ajax({
        'type':"get",
        'url':url+'doctor/query',
        success:function(result){
            console.log(result[0].nickName);
            var str = '';
            for (var i=0;i <result.length ; i++) {
                str += '<option selected value="'+result[i].tel+'">'+result[i].nickName+'</option>';
            }
            console.log(str);
            $('#doctor_select').append(str);
        }
    });


    loadData(1);
    //setInterval("loadData()", 1000 * 3);


});


//加载需要查看的信息
function loadData(currentPage) {
    var filters ={
        "doctorFlag_equal":1
    }
    console.log(url + 'em/query?filters='+JSON.stringify(filters)+'&page=' + currentPage);
    $.ajax({
        type: 'get',
        url: url + 'em?filters='+JSON.stringify(filters)+'&page=' + currentPage,
        success: function (result) {
            var str = '';
            pageStr = '';
            data = result.content;
            totalPages = result.totalPages;
             if(data == '' || data == null){//无数据
                 str += '<span>没有数据</span>';
             }else{
                 for (var i = 0; i < data.length; i++) {
                     str += '<tr>';
                     str += '<td>' + data[i].fromUser + '</td>';
                     str += '<td>' + data[i].toUsers + '</td>';
                     str += '<td>' + data[i].msg + '</td>';
                     str += '<td class="am-hide-sm-only">' + data[i].createdDate + '</td>';
                     str += '<td>' + data[i].targetType + '</td>';
                     str += '<td><div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">';
                     // alert('replyInfo("+data[i].fromUser+","+data[i].toUsers.toString()+")');
                     str += "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick=\"replyInfo('" + data[i].fromUser + "','" + data[i].toUsers + "','"+data[i].id+"')\">";
                     str += '<span class="am-icon-pencil-square-o"></span>回复</button>';
                     str += '</div></div></td></tr>';
                 }
                 //分页
                 pageStr += '<ul class="am-pagination">';
                 var prePage = (currentPage == 1) ? 1 : (currentPage - 1);
                 nextPage = (currentPage == result.totalPages) ? result.totalPages : (currentPage + 1);
                 pageStr += '<li><a href="javascript:loadData(' + prePage + ')">上一页</a></li>';
                 for (var i = 1; i <= totalPages; i++) {
                     console.log(result.number+1);
                     if (i == (result.number+1)){
                         pageStr += '<li class="am-active"><a href="javascript:loadData(' + i + ')">' + i + '</a></li>';
                     } else{
                         pageStr += '<li> <a href="javascript:loadData(' + i + ')">' + i + '</a> </li>';
                     }
                     //pageStr += '<li><a href="javascript:loadData(' + i + ')">' + i + '</a></li>';
                 }
                 pageStr += '<li><a href="javascript:loadData(' + nextPage + ')">下一页</a></li> ';

             }

            $("#replyInfoList").html(str);
            $("#pageDiv").html(pageStr);
        }
    });
}

function replyInfo(fromUser, toUser,msgId) {

    $("#reply-modal").modal('toggle');

    //显示最新的5条聊天纪录
    console.log(url + "em/getTop5Messages?groupId=" + toUser);
    $.ajax({
        'url':url+"em/getTop5Messages?groupId="+toUser,
        'type':'get',
        success: function (data) {
            console.log(data[1].msg);
            var str = '';
            if (data =='' || data == null) {
                str += '<span>没有数据</span>';
            }else {
                for (var i=0; i<data.length; i++) {
                    str += '<tr> ';
                    str += '<td>'+data[i].msg+'</td>';
                    str += '</tr>';
                }
            }
            console.log(str);
            $('#top5Records').html(str);
        }
    });


    //提交回复信息
    $("#type-reply").click(function () {
        var content = $("#content").val();
        //特别要注意 target必须是个数组
        target = [];
        target.push("" + toUser + "");
        message = {
            'target_type': 'chatgroups',
            'target': target,
            'msg': {
                'type': 'txt',
                'msg': content
            },
            'from': $('#doctor_select').val()
        };

        $.ajax({
            'url': url + 'em/replyMessage/'+msgId+'/'+fromUser+'',
            'type': 'POST',
            'data': JSON.stringify(message),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json; charset=UTF-8"
            },
            'dataType': 'json',
            success: function (result) {
                $("#reply-modal").modal('toggle');
                window.location.reload();
            }
        });
    });

}