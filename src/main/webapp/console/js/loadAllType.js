/**
 * Created by wade on 15/5/25.
 */
var url = commonUrl + "discovery/cms/listCategory/";
$.get(url, function (data) {
    console.log(data);
    //  location.reload();
    var datas = '';
    $.each(data.ret_values, function (n, value) {
        datas += "<tr style='display: none' ><td><input type='checkbox'/></td><td>" +
        value.id +
        "</td>" +
        "<td><a href='#'>" + value.title + "</a></td>" +
        "<td class='am-hide-sm-only'>" + value.tag + "</td>" +
        "<td class='am-hide-sm-only'>" + value.description + "</td>" +
        "<td><div class='am-btn-toolbar'><div class='am-btn-group am-btn-group-xs'>" +
        "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='changeType(" + value.id + ")'>" +
        "<span class='am-icon-pencil-square-o'></span> 编辑</button>" ;
        datas += "</button>" +
/*
        "<button type='button' class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' " +
        "onclick='deleteCourse(" + value.id + ")'  >" +
        "<span class='am-icon-trash-o'></span> 删除</button>" +
*/
        "</div></div></td></tr>";
    });
    $("#allDatas").append(datas);
    $("#allDatas tr").fadeIn(300);
    //customer = null;
});
function changeType(id) {
    $('#change-modal').modal('toggle');
    $.get(url+id, function (data) {
        data = data.ret_values;
        console.log(data);
        $('#change-title').val(data.title);
        $('#change-desc').val(data.description);
        $('#change-id').val(data.id);

    })
}
function deleteCourse(course_id) {
    confirm_ = confirm('确定删除？');
    if (confirm_) {
        $.ajax({
            type: "DELETE",
            url: commonUrl + 'course/' + course_id,
            success: function (msg) {
                location.reload();
            },
            error: function () {
                alert('已经有人选择了课程');
            }

        });
    }
};
