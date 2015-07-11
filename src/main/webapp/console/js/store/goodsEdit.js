/**
 * Created by wade on 15/7/3.
 */

var goods;

function openImgUpload() {
    $('#change-modal').modal('toggle');
}


function goodAdd(id) {
    var name = $('#name').val();
    var description = $('#description').val();
    var medicineUrl = $('#medicineUrl').val();
    var picUrl = $('#picUrl').val();
    var price = $('#price').val();
    var id = $('#id').val();
    if (name.length==0||description.length==0||medicineUrl.length==0||picUrl.length==0||price.length==0) {
        alert('有字段没有填写');
        return false;
    }
    var json ={
        "name": name,
        "description": description,
        "medicineUrl": medicineUrl,
        "picUrl": picUrl,
        "price": price
    };
    json = JSON.stringify(json);
    $.ajax({
        url: commonUrl + 'store/updateStore/'+id,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            console.log(response);
            if (response.ret_code == 0) {
                alert('success');
                $.get(commonUrl+'store/listStores', function (data) {
                    goods = data.ret_values;
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

    $.get(commonUrl+'store/listStores', function (data) {
        var datas = '';
        goods = data.ret_values;
        $.each(data.ret_values, function (n, value) {
            datas += "<tr style='display: none' ><td><input type='checkbox'/></td><td>" +
            value.id +
            "</td>" +
            "<td><a href='#'>" + value.name + "</a></td>" +
            "<td class='am-hide-sm-only'>" + value.description + "</td>" +
            "<td class='am-hide-sm-only'><img src='" + value.picUrl + "' style='max-height:80px;' /></td>" +
            "<td><div class='am-btn-toolbar'><div class='am-btn-group am-btn-group-xs'>" +
            "<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='changeGood(" + n + ")'>" +
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

function changeGood(id) {
    //$('#change-info-box').css('display','block');
    var good = goods[id],
    data = good;
    $('#id').val(data.id);
    $('#name').val(data.name);
    $('#description').val(data.description);
    $('#price').val(data.price);
    $('#medicineUrl').val(data.medicineUrl);
    $('#index-img-index').attr('src',data.picUrl);
    $('#index-img').attr('src',data.picUrl);
    $('#picUrl').val(data.picUrl);
}