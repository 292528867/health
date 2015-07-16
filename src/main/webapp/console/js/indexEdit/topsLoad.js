/**
 * Created by wade on 15/5/25.
 */

var topListUrl = commonUrl + 'topline/listToplinesForConsole/',
    tops,
    enabledUrl = commonUrl + 'topline/enabledTopline/';


$.get(topListUrl, function (data) {
    var datas = '';
    tops = data.ret_values;
    $.each(data.ret_values, function (n, value) {
        datas += "<tr style='display: none' ><td><input type='checkbox'/></td><td>" +
            value.id +
            "</td>" +
            "<td><a href='#'>" + value.name + "</a></td>" +
            "<td><a href='#'>" + value.linkUrl + "</a></td>" +
            "<td class='am-hide-sm-only'><img src='" + value.picUrl + "' style='max-height:80px;' /></td>" +
            "<td class='am-hide-sm-only'>" + value.enabled + "</td>" +
            "<td><div class='am-btn-toolbar'><div class='am-btn-group am-btn-group-xs'>" +
            "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='openChange(" + n + ")'>" +
            "<span class='am-icon-pencil-square-o'></span> 编辑</button>";
        if (value.enabled) {
            datas += "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='unabledTop(" + n + ")'>" +
                "<span class='am-icon-pencil-square-o'></span> 下架</button>";

        }
        else {
            datas += "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='enabledTop(" + n + ")'>" +
                "<span class='am-icon-pencil-square-o'></span> 启用</button>";
        }
        datas += "</div></div></td></tr>";
    });
    $("#allDatas").append(datas);
    $("#allDatas tr").fadeIn(300);
});

function unabledTop(id) {
    var top = tops[id];
    $.get(enabledUrl + top.id + '?enabled=' + 0, function (response) {
        if (response.ret_code == 0) {
            //alert('success');
            location.reload();
        }
        else {
            alert(response.err_msg);
        }

    })

}

function enabledTop(id) {
    var top = tops[id];
    $.get(enabledUrl + top.id + '?enabled=' + 1, function (response) {
        if (response.ret_code == 0) {
            //alert('success');
            location.reload();
        }
        else {
            alert(response.err_msg);
        }

    })

}