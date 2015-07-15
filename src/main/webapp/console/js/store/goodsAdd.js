/**
 * Created by wade on 15/7/3.
 */



function openImgUpload() {
    $('#change-modal').modal('toggle');
}


function goodAdd() {
    var name = $('#name').val();
    var description = $('#description').val();
    var medicineUrl = $('#medicineUrl').val();
    var picUrl = $('#picUrl').val();
    var price = $('#price').val();
    var position = $('#position').val();
    if (name.length==0||description.length==0||medicineUrl.length==0||picUrl.length==0||price.length==0||position.length==0) {
        alert('有字段没有填写');
        return false;
    }
    var json ={
        "name": name,
        "description": description,
        "medicineUrl": medicineUrl,
        "picUrl": picUrl,
        "position": position,
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
