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
    var inTypeId = window.location.search.substring(1);
    if(inTypeId.length!=0) {
        $('select').find('option').eq(inTypeId).attr('selected', true);
    }
});


function openImgUpload() {
    $('#change-modal').modal('toggle');
}


function articleAdd() {
    var title = $('#title').val();
    var desc = $('#desc').val();
    var pictureUrl = $('#pictureUrl').val();
    var pictureUrl2 = $('#pictureUrl2').val();
    var type = $('#type').val();

    var htmlInfo = UE.getEditor('htmlInfo').getContent();
    //$('#courseDetail').val(editor.html());
    $('#formSub').attr('disabled',true);
    if (title.length==0||desc.length==0||pictureUrl.length==0||pictureUrl2.length==0||htmlInfo.length==0||type.length==0) {
        alert('有字段没有填写');
        $('#formSub').attr('disabled',false);
        return false;
    }
    var json ={
        "title": title,
        "pictureUrl": pictureUrl,
        "pictureUrl2": pictureUrl2,
        "htmlInfo": htmlInfo,
        "desc": desc
    };
    json = JSON.stringify(json);
    $.ajax({
        url: articleAddUrl + type,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            if (response.ret_code == 0) {
                alert('success');
                location.reload();
            }
            else{
                alert(response.err_msg);
                $('#formSub').attr('disabled',false);
            }

        },
        error: function () {
            $('#formSub').attr('disabled',false);
            alert('error');
        }


    });
}
function toLook() {
    var title = $('#title').val();
    var desc = $('#desc').val();
    var pictureUrl = $('#pictureUrl').val();
    var pictureUrl2 = $('#pictureUrl2').val();
    var type = $('#type').val();
    var htmlInfo = UE.getEditor('htmlInfo').getContent();
    if (title.length == 0 || desc.length == 0 || pictureUrl.length == 0 ||pictureUrl2.length == 0 || htmlInfo.length == 0 || type.length == 0) {
        alert('有字段没有填写');
        return false;
    }
    var json = {
        "title": title,
        "pictureUrl": pictureUrl,
        "pictureUrl2": pictureUrl2,
        "htmlInfo": htmlInfo,
        "desc": desc
    };
    json = JSON.stringify(json);
    localStorage.setItem('article', json);
    window.open('http://101.231.124.8:45677/xlab-healthcloud/app/check.html','','width=400,height=600');
}