/**
 * Created by wukai on 15/7/8.
 */
var allPackagesUrl = commonUrl + "hcPackage/listHcPackage";
var onePackageUrl = commonUrl + "hcPackage/findOnePackage/";

$.get(allPackagesUrl, function (data) {
    //  location.reload();
    var datas = '';
    $.each(data.ret_values, function (n, value) {
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
            "<td class='am-hide-sm-only'>" + (value.recommend == 'true' ? '是':'否' )+ "</td>" +
            "<td class='am-hide-sm-only'>" + (value.loops == '1' ? '是':'否' )+ "</td>" +
            "<td class='am-hide-sm-only'>" + (value.needSupplemented == 'true' ? '是':'否' )+ "</td>" +
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

//打开编辑页面
function changePackage(id) {
    $('#change-modal').modal('toggle');
    $('#change-id').val(id);
    $.get(onePackageUrl+id, function (data) {
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


