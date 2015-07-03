/**
 * Created by wade on 15/7/3.
 */
var addTypeUrl = commonUrl + 'discovery/cms/addCategory';
var changeTypeUrl = commonUrl + 'discovery/cms/updateCategory/';
function openAddType() {
    $('#add-modal').modal('toggle');
}
function addType() {
    var title = $('#title').val();
    var desc = $('#desc').val();
    if (title.length==0||desc.length==0) {
        alert('有字段没有填写');

        return false;
    }
    var json ={
        "title": title,
        "desc": desc
    };
    json = JSON.stringify(json);
    $.ajax({
        url: addTypeUrl,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            if (response.ret_code == 0) {
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


function changeTypeSub() {
    var title = $('#change-title').val();
    var desc = $('#change-desc').val();
    var id = $('#change-id').val();
    if (title.length==0||desc.length==0) {
        alert('有字段没有填写');

        return false;
    }
    var json ={
        "title": title,
        "desc": desc
    };
    json = JSON.stringify(json);
    $.ajax({
        url: changeTypeUrl+id,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            if (response.ret_code == 0) {
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

function openChange(id) {


}