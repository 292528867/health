/**
 * Created by wade on 15/7/3.
 */

var typeUrl = commonUrl + "discovery/cms/listCategory";
var articleAddUrl = commonUrl + 'discovery/cms/addInfo/';
$.get(typeUrl, function (data) {
    console.log(data);
    //  location.reload();
    var datas = '';
    $.each(data.ret_values, function (n, value) {
        datas += "<option value='"+value.id+"'>"+value.title+"</option>";

    });
    $("#type").append(datas);
});


function openImgUpload() {
    $('#change-modal').modal('toggle');
}


function articleAdd() {
    var title = $('#title').val();
    var desc = $('#desc').val();
    var pictureUrl = $('#pictureUrl').val();
    var type = $('#type').val();

    var htmlInfo = editor.html();
    //$('#courseDetail').val(editor.html());

    if (title.length==0||desc.length==0||pictureUrl.length==0||htmlInfo.length==0||type.length==0) {
        alert('有字段没有填写');
        return false;
    }
    articleAddUrl += type;
    var json ={
        "title": title,
        "pictureUrl": pictureUrl,
        "htmlInfo": htmlInfo,
        "desc": desc
    };
    json = JSON.stringify(json);
    $.ajax({
        url: articleAddUrl,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            if (response.ret_code == 0) {
                alert('success');
                location.reload();
            }
            else
                alert(response.err_msg);

        },
        error: function () {
            alert('error');
        }


    });
}
