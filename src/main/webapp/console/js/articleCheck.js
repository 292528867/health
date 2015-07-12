/**
 * Created by wade on 15/7/3.
 */

var typeUrl = commonUrl + "discovery/cms/listCategory/";
var articleAddUrl = commonUrl + 'discovery/cms/addInfo/';
var articleSearchUrl = commonUrl + 'discovery/cms/listInfo/';
var articleChangeUrl =commonUrl +'discovery/cms/updateInfo/';
$.get(typeUrl, function (data) {
    console.log(data);
    //  location.reload();
    var datas = '';
    $.each(data.ret_values, function (n, value) {
        datas += "<option value='" + value.id + "'>" + value.title + "</option>";

    });
    $("#type").append(datas);
    $("#type-select").append(datas);
});
if(localStorage.getItem('typeId')!=null) {
    searchArticle(localStorage.getItem('typeId'));
}


function openImgUpload() {
    $('#change-modal').modal('toggle');
}


function articleAdd() {
    var title = $('#title').val();
    var desc = $('#desc').val();
    var pictureUrl = $('#pictureUrl').val();
    var pictureUrl2 = $('#pictureUrl2').val();
    var type = $('#type').val();
    var id = $('#id').val();

    //var htmlInfo = editor.html();
    var htmlInfo = UE.getEditor('htmlInfo').getContent();
    //$('#courseDetail').val(editor.html());

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
    $.ajax({
        url: articleChangeUrl+ type+'/'+id,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            if (response.ret_code == 0) {
                alert('success,不跳转了啊，有BUG 说。。');
                //location.reload();
                $.get(articleSearchUrl + type, function (data) {
                    articles = data.ret_values;
                    location.href=location.href+'#';
                    $('#change-info-box').hide();
                    $('#info-table').show();
                });
            }
            else
                alert(response.err_msg);

        },
        error: function () {
            alert('error');
        }


    });
}

var articles;
function searchArticle(id) {
    if (id.length == 0||id==null) {
        return false;
    }
    $('#allDatas').html('');
    $('#type').val(id);
    localStorage.setItem('typeId',id);
    //$('#type').attr('value', id);
    //$("#type").find("option[text=id]").attr("selected",true);
    $.get(articleSearchUrl + id, function (data) {
        //  location.reload();
        articles = data.ret_values;
        console.log(data);
        var datas = '';
        $.each(data.ret_values, function (n, value) {
            datas += "<tr style='display: none' ><td><input type='checkbox'/></td><td>" +
            value.id +
            "</td>" +
            "<td><a href='#'>" + value.title + "</a></td>" +
            "<td class='am-hide-sm-only'>" + value.description + "</td>" +
            "<td class='am-hide-sm-only'><img src='" + value.pictureUrl + "' style='max-height:80px;' /></td>" +
            "<td><div class='am-btn-toolbar'><div class='am-btn-group am-btn-group-xs'>" +
            "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='changeArticle(" + n + ")'>" +
            "<span class='am-icon-pencil-square-o'></span> 编辑</button>";
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
        $('#change-info-box').hide();
        $('#info-table').show();


    });

}
function changeArticle(id) {
    var article = articles[id];
    data = article;
    $('#id').val(data.id);
    $('#title').val(data.title);
    $('#desc').val(data.description);
    $('#type').val($('#type-select').val()||localStorage.getItem('typeId'));
    $('#index-img-index').attr('src',data.pictureUrl);
    UE.getEditor('htmlInfo').setContent(data.htmlInfo);
    $('#index-img').attr('src',data.pictureUrl);
    $('#pictureUrl').val(data.pictureUrl);
    $('#banner-img-index').attr('src', data.pictureUrl2);
    $('#banner-img').attr('src', data.pictureUrl2);
    $('#pictureUrl2').val(data.pictureUrl2);
    $('#change-info-box').show();
    $('#info-table').hide();
}