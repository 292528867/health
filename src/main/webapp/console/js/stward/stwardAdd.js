/**
 * Created by wade on 15/7/3.
 */



var stewardAddUrl = commonUrl + "steward/";
$.get(stewardAddUrl, function (data) {
    console.log(data);
    //  location.reload();
    var datas = '';
    $.each(data.content, function (n, value) {
        datas += "<option value='"+value.id+"'>"+value.nickName+"</option>";

    });
    $("#steward").append(datas);
});



var stewardServiceUrl = commonUrl + 'steward/listCustomPackage';
var addServiceUrl = commonUrl + 'services/saveService';
var addStewardUrl = commonUrl + 'steward/saveSteward';

$.get(stewardServiceUrl, function (data) {
    console.log(data);
    //  location.reload();
    var datas = '';
    $.each(data.ret_values.services, function (n, value) {
        datas += "<tr style='display: none' ><td><input type='checkbox'/></td><td>" + value.id +
            "</td>" +
            "<td><a href='#'>" + value.serviceName + "</a></td>" +
            "<td class='am-hide-sm-only'>" + value.serviceDescription + "</td>" +
            "<td class='am-hide-sm-only'>" + value.serviceIntegration + "</td>" +
            "<td class='am-hide-sm-only'>" + value.serviceId + "</td>" +
            "<td class='am-hide-sm-only'>" + value.serviceArea + "</td>" +
            "<td><div class='am-btn-toolbar'><div class='am-btn-group am-btn-group-xs'>" +
            "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='searchCourse(" + value.id + ")'>" +
            "<span class='am-icon-pencil-square-o'></span> 编辑</button>" +
            "</button><button type='button' class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' " +
            "onclick='deleteService(" + value.id + ")'  >" +
            "<span class='am-icon-trash-o'></span> 删除</button></div></div></td></tr>";

    });
    //console.lg(datas);

    $("#allServiceDatas").append(datas);
    $("#allServiceDatas tr").fadeIn(300);
});



$.get(stewardServiceUrl, function (data) {
    console.log(data);
    //  location.reload();
    var datas = '';
    $.each(data.ret_values.steward, function (n, value) {
        datas += "<tr style='display: none' ><td><input type='checkbox'/></td><td>" + value.id +
            "</td>" +
            "<td><a href='#'>" + value.nickName + "</a></td>" +
            "<td class='am-hide-sm-only'>" + value.tel + "</td>" +
            "<td class='am-hide-sm-only'>" + value.height + "</td>" +
            "<td class='am-hide-sm-only'>" + value.weight + "</td>" +
            "<td class='am-hide-sm-only'>" + value.rank + "</td>" +
            "<td class='am-hide-sm-only'>" + value.birthday + "</td>" +
            "<td class='am-hide-sm-only'>" + value.iconUrl + "</td>" +
            "<td class='am-hide-sm-only'>" + value.levelUrl + "</td>" +
            "<td class='am-hide-sm-only'>" + value.location.address + "</td>" +
            "<td><div class='am-btn-toolbar'><div class='am-btn-group am-btn-group-xs'>" +
            "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='searchCourse(" + value.id + ")'>" +
            "<span class='am-icon-pencil-square-o'></span> 编辑</button>" +
            "</button><button type='button' class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' " +
            "onclick='deleteSteward(" + value.id + ")'  >" +
            "<span class='am-icon-trash-o'></span> 删除</button></div></div></td></tr>";

    });
    //console.lg(datas);

    $("#allStewardDatas").append(datas);
    $("#allStewardDatas tr").fadeIn(300);
});




function stewardAdd() {
    var nickName = $('#nickName').val();
    var tel = $('#tel').val();
    var height = $('#height').val();
    var weight = $('#weight').val();
    var age = $('#age').val();
    var sex = $('#sex').val();
    var rank = $('#rank').val();
    var iconUrl = $('#iconUrl').val();
    var levelUrl = $('#levelUrl').val();
    var longitude = $('#longitude').val();
    var latitude = $('#latitude').val();

    if (nickName.length==0||tel.length==0||height.length==0) {
        alert('有字段没有填写');
        return false;
    }
    var location ={
        "longitude":parseFloat(longitude),
        "latitude":parseFloat(latitude)
    }
    var json ={
        "nickName": nickName,
        "tel": tel,
        "height": height,
        "weight": weight,
        "age": age,
        "sex":sex,
        "rank": rank,
        "iconUrl": iconUrl,
        "levelUrl":levelUrl,
        "location":location,
    };
    json = JSON.stringify(json);
    console.log(json);
    $.ajax({
        url: addStewardUrl,
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

function deleteService(servicesId) {
    confirm_ = confirm('确定删除？');
    if (confirm_) {
        $.ajax({
            type: "DELETE",
            url: commonUrl + 'services/' + servicesId,
            success: function (msg) {
                location.reload();
            },
            error: function () {
                alert('删除错误');
            }

        });
    }
};
function deleteSteward(stewardId) {
    confirm_ = confirm('确定删除？');
    if (confirm_) {
        $.ajax({
            type: "DELETE",
            url: commonUrl + 'steward/' + stewardId,
            success: function (msg) {
                location.reload();
            },
            error: function () {
                alert('删除错误');
            }

        });
    }
};

function openImgUpload() {
    $('#change-modal').modal('toggle');
}
