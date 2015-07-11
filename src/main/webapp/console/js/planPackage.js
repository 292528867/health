/**
 * Created by wukai on 15/7/8.
 */
var packageAddUrl = commonUrl + "hcPackage/addHcPackage";
function addPackage(){
    var healthCategoryId = $('#type').val();
    console.log(healthCategoryId);
    var durationUnit = $('#durationUnit').val(),
        durationCnt = $('#durationCnt').val(),
        duration,
        title = $('#title').val(),
        description = $('#description').val(),
        recommend = $('#recommend').val(),
        recommendValue = $('#recommendValue').val(),
        isNeedSupplemented = $('#isNeedSupplemented').val(),
        loops = $('#loops').val(),
        iconUrl = $('#iconUrl').val(),
        detailDescriptionIcon = $('#detailDescriptionIcon').val();
    if(durationUnit == 'week'){
        duration = durationCnt * 7;
    } else if (durationUnit == "day") {
        duration = durationCnt;
    }
    var intReg = new RegExp("^-?\\d+$");
    if(recommendValue && !intReg.test(recommendValue)){
        alert("推荐值请输入数字!");
        $('#recommendValue').val("");
        return;
    }
    var nReg = new RegExp("^\\+?[1-9][0-9]*$");
    if(!nReg.test(durationCnt)){
        alert("请输入正确的任务包时长!");
        $('#durationCnt').val("");
        return;
    }

    if($('#recommend').is(':checked')){
        recommend = true;
    } else {
        recommend = false;
    }
    if($('#isNeedSupplemented').is(':checked')){
        isNeedSupplemented = true;
    } else {
        isNeedSupplemented = false;
    }
    if($('#loops').is(':checked')){
        loops = "1";
    } else {
        loops = "0";
    }

    //校验字段
    var json ={
        "title": title,
        "description": description,
        "recommend": recommend,
        "recommendValue": recommendValue,
        "isNeedSupplemented": isNeedSupplemented,
        "loops": loops,
        "icon": iconUrl,
        "detailDescriptionIcon": detailDescriptionIcon,
        "duration": duration
    };
    json = JSON.stringify(json);

    var addUrl = packageAddUrl + "/" + healthCategoryId;
    console.log(json);
    $.ajax({
        url: addUrl,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            if (response.ret_code == 0) {
                alert('提交成功');
                //location.reload();
                clearHtml();
            }
            else
                alert(response.err_msg);
        },
        error: function () {
            alert('提交失败');
        }


    });
}

function clearHtml(){
    $('#durationCnt').val("");
    $('#duration').val("");
    $('#title').val("");
    $('#description').val("");
    $('#detailDescription').val("");
    $('#recommend').val("");
    $('#recommend').removeAttr("checked");
    $('#recommend').removeAttr("value");
    $('#recommendValue').val("");
    $('#isNeedSupplemented').val("");
    $('#isNeedSupplemented').removeAttr("checked");
    $('#isNeedSupplemented').removeAttr("value");
    $('#loops').val("");
    $('#loops').removeAttr("checked");
    $('#loops').removeAttr("value");
    $('#iconUrl').val("");
    $('#detailDescriptionIcon').val("");
    $('#detail-img').attr('src', '');
}
