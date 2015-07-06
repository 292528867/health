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
    var tag = $('#tag').val(),
        firstRelatedIds= $('#firstRelatedIds').val()||[],
        otherRelatedIds= $('#otherRelatedIds').val()||[];
    firstRelatedIds = firstRelatedIds.join(',');
    otherRelatedIds = otherRelatedIds.join(',');
    if (title.length==0||desc.length==0||tag.length==0) {
        alert('有字段没有填写');

        return false;
    }
    var json ={
        "title": title,
        "desc": desc,
        "tag":tag,
        "firstRelatedIds":firstRelatedIds,
        "otherRelatedIds":otherRelatedIds
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
    var tag = $('#change-tag').val(),
        firstRelatedIds= $('#change-firstRelatedIds').val()||',',
        otherRelatedIds= $('#change-otherRelatedIds').val()||',';
    firstRelatedIds = firstRelatedIds.join(',');
    otherRelatedIds = otherRelatedIds.join(',');
    if (title.length==0||desc.length==0) {
        alert('有字段没有填写');

        return false;
    }
    var json ={
        "title": title,
        "desc": desc,
        "tag":tag,
        "firstRelatedIds":firstRelatedIds,
        "otherRelatedIds":otherRelatedIds
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