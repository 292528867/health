/**
 * Created by wade on 15/5/25.
 */
commonUrl="http://10.10.10.219:8080/";
var packageUrl= commonUrl +'hcPackage/listHcPackage',
    bannerListUrl= commonUrl +'banner/listBannerForConsole',
    topicListUrl= commonUrl +'banner/retrieveHealthInfos/',
    addBanner= commonUrl +'banner/addBanner',
    updateBannerUrl= commonUrl +'banner/updateBanner/';


//编辑
function changeType(e) {
    $("#package,#topic").closest(".am-form-group").addClass("am-hide");
    $(".am-modal-bd input,.am-modal-bd select").each(function(){
        if($(this).attr("id")=="package")
             return true;
        $(this).val("");
    });
    if(e){
        var data= eval("("+$(e).closest("tr").attr("data-all")+")");
        console.log(data);
        $("#id").val(data.id);
        $("#title").val(data.title);
        $("#picUrl").val(data.picUrl);
        $("#linkUrl").val(data.linkUrl);
        $("#position").val(data.position);
        $("#bannerTag").val(data.bannerTag);
        $("#bannerType").val(data.bannerType);
        $("#articleId").val(data.articleId);
    }
    $('#change-modal').modal('toggle');


}
/**
 * 获取所有健康包
 */
function getPackageList(){
    $.get(packageUrl, function (groupTypes) {
        console.log(groupTypes);
        var groups = groupTypes.ret_values,
            typeList = '';
        $.each(groups, function (n, group) {
            typeList+='<option value="'+group.id+'">'+group.title+'</option>';
        });
        $("#package").empty().append(typeList);
    });
}

/**
 * 根据包  获取文章
 */
function getTopicList(id){
    $.get(topicListUrl+id, function (groupTypes) {
        console.log(groupTypes);
        var groups = groupTypes.ret_values,
            typeList = '';
        $.each(groups, function (n, group) {
            typeList+='<option value="'+group.id+'">'+group.title+'</option>';
        });
        $("#topic").empty().append(typeList);
    });
}

$("#bannerTag").on("change",function(){
    //如果是计划或者发现的 加载package
    if($(this).val()==0 || $(this).val()==1){
        $("#package,#topic").closest(".am-form-group").removeClass("am-hide");
    }else{
        $("#package,#topic").closest(".am-form-group").addClass("am-hide");
        $("#topic").html("<option></option>");
        $("#articleId").val("");
    }
});

$("#package").on("change",function(){
    $("#topic").html("<option></option>");
    getTopicList($(this).val());
});

getPackageList();

/**
 * 获取 所有banner
 * */
$.post(bannerListUrl, function (groupTypes) {
    console.log(groupTypes);
    var groups = groupTypes.ret_values,
        typeList = '';
    var text="",open="";
    $.each(groups, function (n, group) {

        if(group.bannerType){
            text="二级";
        }else{
            text="一级";
        }
        if(group.enabled){
            open="禁用";
        }else{
            open="启用";
        }
        typeList+='<tr data-all=\''+JSON.stringify(group)+'\'>'+
        '<td>'+group.id+'</td>'+
        '<td><a href="javascript:void(0)" onclick="enabledBanner(this)">'+ open +'</a></td>'+
        '<td>'+ text +'</td>'+
        '<td class="am-hide-sm-only">'+ group.position +'</td>'+
        '<td class="am-hide-sm-only">'+ group.title +'</td>'+
        '<td>'+
        '<div class="am-btn-toolbar">'+
        '<div class="am-btn-group am-btn-group-xs">'+
        '<button class="am-btn am-btn-default am-btn-xs am-text-secondary" type="button"'+
        'onclick="changeType(this)"><span class="am-icon-pencil-square-o"></span> 编辑'+
        '</button>'+
        '</div>'+
        '</div>'+
        '</td>'+
        '</tr>';
    });
    $('#allDatas').empty().append(typeList);
});


/**
 * 提交
 */
function updateBanner(){
    var title=$("#title").val();
    var picUrl=$("#picUrl").val();
    var linkUrl=$("#linkUrl").val();
    var position=$("#position").val();
    var bannerTag=$("#bannerTag").val();
    var bannerType=$("#bannerType").val();

    if(picUrl==""){
        alert("请先上传图片");
    }
    var json ={
        "title": title,
        "picUrl": picUrl,
        "linkUrl":linkUrl,
        "bannerTag":bannerTag,
        "bannerType":bannerType,
        "position":position,
        "enabled":true
    };


    if($("#id").val()){
        json.id = $("#id").val();
    }
    postBanner(json);

}

function enabledBanner(e){
    var param =eval("("+$(e).closest("tr").attr("data-all")+")");
    var json ={
        "title": param.title,
        "picUrl": param.picUrl,
        "linkUrl":param.linkUrl,
        "bannerTag":param.bannerTag,
        "bannerType":param.bannerType,
        "position":param.position,
        "enabled":param.enabled?false:true,
        "id":param.id
    };
    postBanner(json);
}

function postBanner(json){
    var ajaxUrl = addBanner;
    if(json.id){
        ajaxUrl=updateBannerUrl+json.id;
        delete json.id;
    }

    json = JSON.stringify(json);
    $.ajax({
        url: ajaxUrl,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            console.log(response);
            if (response.ret_code == 0) {
                location.reload();
            }
            else
                alert(response.message);

        },
        error: function () {
            alert('error');
        }
    });
}