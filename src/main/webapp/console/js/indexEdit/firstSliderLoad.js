/**
 * Created by wade on 15/5/25.
 */
var allTypeUrl = commonUrl + "discovery/cms/listCategory/",
    groupTypeUrl= commonUrl +'discovery/cms/listCategory/groupinfo';

/*
$.get(allTypeUrl, function (data) {
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
*/
/*
        "<button type='button' class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' " +
        "onclick='deleteCourse(" + value.id + ")'  >" +
        "<span class='am-icon-trash-o'></span> 删除</button>" +
*//*

        "</div></div></td></tr>";
    });
    $("#allDatas").append(datas);
    $("#allDatas tr").fadeIn(300);
    //customer = null;
});
*/

//编辑
function changeType(id) {
    $('#change-modal').modal('toggle');
    $.get(allTypeUrl+id, function (data) {
        data = data.ret_values;
        //console.log(data);
        $('#change-title').val(data.title);
        $('#change-tag').val(data.tag);
        $('#change-desc').val(data.description);
        $('#change-id').val(data.id);
        var $changeFirst = $('#change-firstRelatedIds'),
            firstRelatedIds=data.firstRelatedIds.split(','),
            $changeSecond = $('#change-secondRelatedIds'),
            secondRelatedIds =data.secondRelatedIds.split(',');

        var $selectedOne = changeSelected($changeFirst, firstRelatedIds),
            $selectedTwo = changeSelected($changeSecond, secondRelatedIds);

        if (!$.AMUI.support.mutationobserver) {
            $selectedOne.trigger('changed.selected.amui');
            $selectedTwo.trigger('changed.selected.amui');
        }


    })
}
//更改select选项框
function changeSelected($change, relatedIds) {
    $change.find('option').each(function () {
        $(this).attr('selected', false);
    });
    for (var n in  relatedIds) {
        //console.log(relatedIds[n]);
        var $selected = $change.find('option[value="' + relatedIds[n] + '"]');
        $selected.attr('selected', true);
    }
    return $selected;
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

$.get(groupTypeUrl, function (groupTypes) {

    var groups = groupTypes.ret_values,
        typeList = '',
        categorieList= '';
    $.each(groups, function (n, group) {
        typeList += '<optgroup label=\''+group.type+'\'>';
        $.each(group.categories, function (n, categorie) {
            categorieList+= '<option value=\''+categorie.id+'\'>'+categorie.title+'</option>'
        });
        typeList+=categorieList;
        typeList += '</optgroup>';
    });
    $('.type-select').append(typeList);
    $('#firstRelatedIds').on('change', changeSecond);
    $('#change-firstRelatedIds').on('change', changeSecondChange);
});

//一级关联分类选择好以后，对二级关联进行不可选操作
function changeSecond() {
    var $selected = $('#secondRelatedIds');
    var categories = $(this).val();
    if (categories) {
        var options = $selected.find('option');
        for (var n in options) {
            options[n].disabled=false;
        }
        for (var i in categories) {
            var $disabled = $selected.find('option[value="'+categories[i]+'"]');
            $disabled[0].disabled = true;
        }
        if (!$.AMUI.support.mutationobserver) {
            $selected.trigger('changed.selected.amui');
        }
    }
}
function changeSecondChange() {
    var $selected = $('#change-secondRelatedIds');
    var categories = $(this).val();
    if (categories) {
        var options = $selected.find('option');
        for (var n in options) {
            options[n].disabled=false;
        }
        for (var i in categories) {
            var $disabled = $selected.find('option[value="'+categories[i]+'"]');
            $disabled[0].disabled = true;
        }
        if (!$.AMUI.support.mutationobserver) {
            $selected.trigger('changed.selected.amui');
        }
    }
}