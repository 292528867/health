/**
 * Created by wukai on 15/7/8.
 */
var allPackagesUrl = commonUrl + "hcPackage/listHcPackage";
var onePackageUrl = commonUrl + "hcPackage/findOnePackage/";
var typeUrl = commonUrl + "discovery/cms/listCategory/";
var updatePackageUrl = commonUrl + "hcPackage/updateHcPackage/";

$.get(typeUrl, function (data) {
    console.log(data);
    //  location.reload();
    var datas = '';
    $.each(data.ret_values, function (n, value) {
        datas += "<option value='" + value.id + "'>" + value.title + "</option>";

    });
    $(".type-select").append(datas);
});

/*if(localStorage.getItem('typeId')!= null) {
    searchPackage(localStorage.getItem('typeId'));
}*/

//打开编辑页面
function changePackage(id) {
    $.get(onePackageUrl+id, function (data) {
        console.log(data);
        $('#change-id').val(id);
        $('#modify-durationUnit').val("day");
        $('#modify-durationCnt').val(data.duration);
        //duration;
        $('#modify-title').val(data.title);
        $('#modify-description').val(data.description);
        $('#modify-detailDescription').val(data.detailDescription);
        if(data.recommend){
            $('#modify-recommend').attr('checked', 'checked');
        }
        $('#modify-recommendValue').val(data.recommendValue);
        if(data.needSupplemented){
            $('#modify-isNeedSupplemented').attr('checked', 'checked');
        }
        if(data.loops == '1'){
            $('#modify-loops').attr('checked', 'checked');
        }
        $('#modify-iconUrl').val(data.icon);
        $('#modify-detailDescriptionIcon').val(data.detailDescriptionIcon);
        $('#modify-smallIcon').val(data.smallIcon);
        $('#icon-img-index').attr('src', data.icon);
        $('#detail-img-index').attr('src', data.detailDescriptionIcon);
        $('#small-icon-img-index').attr('src', data.smallIcon);
        var categoryId = data.healthCategory.id;
        var groupTypeUrl= commonUrl +'discovery/cms/listCategory/groupinfo';
        $.get(groupTypeUrl, function (groupTypes) {
            var groups = groupTypes.ret_values,
                typeList = '',
                categoryList= '';
            $.each(groups, function (n, group) {
                typeList += '<optgroup label=\''+group.type+'\'>';
                $.each(group.categories, function (n, categorie) {
                    if(categorie.id == categoryId){
                        categoryList+= '<option value=\''+categorie.id+'\' selected>'+categorie.title+'</option>';
                    } else {
                        categoryList+= '<option value=\''+categorie.id+'\'>'+categorie.title+'</option>';
                    }

                });
                typeList+=categoryList;
                typeList += '</optgroup>';
            });
            $('.category-select').append(typeList);
            if (!$.AMUI.support.mutationobserver) {
                $('#type').trigger('changed.selected.amui');
            }
        });
    })
}


function searchPackage(typeId){

    localStorage.setItem('typeId', typeId);
    var packagesUrl = commonUrl + "hcPackage/findPackagesByCategoryId/" + typeId;
    if(typeId == null || typeId.length == 0){
        return ;
    }

    $("#allDatas").html("");
    $.get(packagesUrl, function (data) {
        var datas = '';
        //console.log(data.ret_values);
        $.each(data.ret_values, function (n, value) {
            //console.log(value);
            var categoryUrl = onePackageUrl + value.id;
            var categoryTitle = '';
            $.ajax({
                type : "get",
                url : categoryUrl,
                async : false,
                success : function(cData){
                    var hcCategory = cData.healthCategory;
                    categoryTitle = hcCategory.title;
                }
            });

            datas += "<tr style='display: none' ><td><input type='checkbox'/></td><td>" +
                value.id +
                "</td>" +
                "<td><a href='#'>" + categoryTitle + "</a></td>" +
                "<td><a href='#'>" + value.title + "</a></td>" +
                "<td class='am-hide-sm-only'>" + value.description + "</td>" +
                "<td class='am-hide-sm-only'>" + value.duration + "</td>" +
                "<td class='am-hide-sm-only'>" + (value.recommend ? '是':'否' )+ "</td>" +
                "<td class='am-hide-sm-only'>" + (value.loops ? '是':'否' )+ "</td>" +
                "<td class='am-hide-sm-only'>" + (value.needSupplemented ? '是':'否' )+ "</td>" +
                "<td><div class='am-btn-toolbar'><div class='am-btn-group am-btn-group-xs'>" +
                "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='changePackage(" + value.id + ")'>" +
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

}

function openImgUpload() {
    $('#change-modal').modal('toggle');
}

function modifyPackage(){
    var healthCategoryId = $('#type').val();
    var id = $('#change-id').val();
    if(id == null || id.length == 0){
        alert("请选择需要修改的包");
        return;
    }
    console.log(healthCategoryId);
    var durationUnit = $('#modify-durationUnit').val(),
        durationCnt = $('#modify-durationCnt').val(),
        duration,
        title = $('#modify-title').val(),
        description = $('#modify-description').val(),
        detailDescription = $('#modify-detailDescription').val(),
        recommend = $('#modify-recommend').val(),
        recommendValue = $('#modify-recommendValue').val(),
        isNeedSupplemented = $('#modify-isNeedSupplemented').val(),
        loops = $('#modify-loops').val(),
        iconUrl = $('#modify-iconUrl').val(),
        detailDescriptionIcon = $('#modify-detailDescriptionIcon').val(),
        smallIcon = $('#modify-smallIcon').val();
    if(durationUnit == 'week'){
        duration = durationCnt * 7;
    } else if (durationUnit == "day") {
        duration = durationCnt;
    }
    var intReg = new RegExp("^-?\\d+$");
    if(recommendValue && !intReg.test(recommendValue)){
        alert("推荐值请输入数字!");
        $('#modify-recommendValue').val("");
        return;
    }
    var nReg = new RegExp("^\\+?[1-9][0-9]*$");
    if(!nReg.test(durationCnt)){
        alert("请输入正确的任务包时长!");
        $('#modify-durationCnt').val("");
        return;
    }

    if($('#modify-recommend').is(':checked')){
        recommend = true;
    } else {
        recommend = false;
    }
    if($('#modify-isNeedSupplemented').is(':checked')){
        isNeedSupplemented = true;
    } else {
        isNeedSupplemented = false;
    }
    if($('#modify-loops').is(':checked')){
        loops = true;
    } else {
        loops = false;
    }

    //校验字段
    var json ={
        "title": title,
        "description": description,
        "detailDescription": detailDescription,
        "recommend": recommend,
        "recommendValue": recommendValue,
        "isNeedSupplemented": isNeedSupplemented,
        "loops": loops,
        "icon": iconUrl,
        "detailDescriptionIcon": detailDescriptionIcon,
        "duration": duration,
        "smallIcon": smallIcon
    };
    json = JSON.stringify(json);

    var updateUrl = updatePackageUrl + id;
    console.log(json);
    debugger;
    $.ajax({
        url: updateUrl,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            if (response.ret_code == 0) {
                alert('提交成功');
                location.reload();
            }
            else
                alert(response.err_msg);
        },
        error: function () {
            alert('提交失败');
        }


    });
}


