/**
 * Created by wade on 15/7/3.
 */

var typeUrl = commonUrl + "discovery/cms/listCategory/";
var articleAddUrl = commonUrl + 'discovery/cms/addInfo/';
var articleSearchUrl = commonUrl + 'discovery/cms/listInfo/';
var articleChangeUrl =commonUrl +'discovery/cms/updateInfo/';
if(localStorage.getItem('typeId')!=null) {
    searchArticle(localStorage.getItem('typeId'));
}


function openImgUpload() {
    $('#change-modal').modal('toggle');
}


function goodAdd() {
    var name = $('#name').val();
    var description = $('#description').val();
    var medicineUrl = $('#medicineUrl').val();
    var pictureUrl = $('#pictureUrl').val();
    var price = $('#price').val();
    if (name.length==0||description.length==0||medicineUrl.length==0||pictureUrl.length==0||price.length==0) {
        alert('有字段没有填写');
        return false;
    }
    var json ={
        "name": name,
        "description": description,
        "medicineUrl": medicineUrl,
        "pictureUrl": pictureUrl,
        "price": price
    };
    json = JSON.stringify(json);
    $.ajax({
        url: commonUrl + 'store/addStore',
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            console.log(response);
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

    });

}
function changeArticle(id) {
    //$('#change-info-box').css('display','block');
    var article = articles[id];
    data = article;
    $('#id').val(data.id);
    $('#title').val(data.title);
    $('#desc').val(data.description);
    $('#type').val($('#type-select').val()||localStorage.getItem('typeId'));

    //editor.html(data.htmlInfo);
    $('#index-img-index').attr('src',data.pictureUrl);
    UE.getEditor('htmlInfo').setContent(data.htmlInfo);
    $('#index-img').attr('src',data.pictureUrl);
    $('#pictureUrl').val(data.pictureUrl);
    $('#banner-img-index').attr('src', data.pictureUrl2);
    $('#banner-img').attr('src', data.pictureUrl2);
    $('#pictureUrl2').val(data.pictureUrl2);



}