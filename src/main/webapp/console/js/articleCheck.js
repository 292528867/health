/**
 * Created by wade on 15/7/3.
 */

var typeUrl = commonUrl + "discovery/cms/listCategory/";
var articleAddUrl = commonUrl + 'discovery/cms/addInfo/';
var articleSearchUrl = commonUrl + 'discovery/cms/listInfo/';
var articleChangeUrl =commonUrl +'discovery/cms/updateInfo/';
var page = 0, loads = 0, totalPages = 0;

$.get(typeUrl, function (data) {
    console.log(data);
    //  location.reload();
    var datas = '';
    $.each(data.ret_values, function (n, value) {
        datas += "<option value='" + value.id + "'>" + value.title +codefans_net_CC2PY(value.title)+allCaps(codefans_net_CC2PY(value.title)) + "</option>";

    });
    $("#type").append(datas);
    $("#type-select").append(datas);
    if(localStorage.getItem('typeId')!=null) {
        var item = localStorage.getItem('typeId');
        $('select').find('option[value="'+item+'"]').attr('selected', true);
        console.log(1);
        //searchArticle(item);
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
    var id = $('#id').val();
    $('#formSub').attr('disabled',true);

    //var htmlInfo = editor.html();
    var htmlInfo = UE.getEditor('htmlInfo').getContent();
    //$('#courseDetail').val(editor.html());

    if (title.length == 0 || desc.length == 0 || pictureUrl.length == 0 ||pictureUrl2.length == 0 || htmlInfo.length == 0 || type.length == 0) {
        alert('有字段没有填写');
        $('#formSub').attr('disabled',false);
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
                alert('success。');
                location.reload();
                //$.get(articleSearchUrl + type, function (data) {
                //    articles = data.ret_values;
                //    location.href=location.href+'#';
                //    $('#change-info-box').hide();
                //    $('#info-table').show();
                //});
            }
            else{
                alert(response.err_msg);
                $('#formSub').attr('disabled',false);
            }


        },
        error: function () {
            alert('error');
            $('#formSub').attr('disabled',false);
        }


    });
}

var articles;
var searchCount = 0;
function searchArticle(id) {
    if (id.length == 0||id==null) {
        return false;
    }
    page = 0;
    $('#type').val(id);
    localStorage.setItem('typeId',id);
    loadArticles(id, page);
}


function loadArticles(id,page) {
    $('#info-loading').html('信息(<i class="am-icon-refresh am-icon-spin"></i>正在读取！！！！！)');
    $("#allDatas").html('');
    //var $w = $(window);
    $('html, body').animate({scrollTop: 0}, '500');
    $.get(articleSearchUrl + id+'?page=' + page, function (data) {
        $('#allDatas').html('');
        articles = data.ret_values.content;
        $('#totalElements').html(data.ret_values.totalElements)
        console.log(data);
        var datas = '';
        $.each(data.ret_values.content, function (n, value) {
            datas += "<tr ><td><input type='checkbox'/></td><td>" +
                value.id +
                "</td>" +
                "<td><a href='#'>" + value.title + "</a></td>" +
                "<td class='am-hide-sm-only'>" + value.description + "</td>" +
                    //"<td class='am-hide-sm-only'><img src='" + value.pictureUrl + "' style='max-height:80px;' /></td>" +
                "<td><div class='am-btn-toolbar'><div class='am-btn-group am-btn-group-xs'>" +
                "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='changeArticle(" + n + ")'>" +
                "<span class='am-icon-pencil-square-o'></span> 编辑</button>";
            datas += "" +
                "<button type='button' class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' " +
                "onclick='lookNow(" + n + ")'  >" +
                "<span class='am-icon-trash-o'></span> 预览</button>" +
                "</div></div></td></tr>";
        });
        $("#allDatas").html(datas);
        //customer = null;
        $('#change-info-box').hide();
        $('#info-table').show();
        $('#info-loading').html('信息读取成功！');
        searchCount=1;
        var page = '';
        for (var i=1;i<=data.ret_values.totalPages;i++)
        {
            page += '<li ><a href="javascript:loadArticles('+id+','+(i-1)+')">'+i+'</a></li>';
        }
        $("#pages").html(page);
        $("#pages").find('li:eq('+data.ret_values.number+')').addClass('am-active');

    });

}



function changeArticle(id) {
    var article = articles[id];
    data = article;
    $('#type').find('option').attr('selected',false);
    $('#type').val(localStorage.getItem('typeId'));
    $('#type').find('option[value="'+localStorage.getItem('typeId')+'"]').attr('selected', true);
    console.log(localStorage.getItem('typeId'));
    $('#id').val(data.id);
    $('#title').val(data.title);
    $('#desc').val(data.description);
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
function lookNow(id) {
    var article = articles[id];
    window.open('http://101.231.124.8:45677/xlab-healthcloud/app/detail.html?'+article.id,'','width=400,height=600');
}

function toLook() {
    var title = $('#title').val();
    var desc = $('#desc').val();
    var pictureUrl = $('#pictureUrl').val();
    var pictureUrl2 = $('#pictureUrl2').val();
    var type = $('#type').val();
    var id = $('#id').val();
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
        "desc": desc,
        "id":id
    };
    json = JSON.stringify(json);
    localStorage.setItem('article', json);
    window.open('http://101.231.124.8:45677/xlab-healthcloud/app/check.html','','width=400,height=600');
}