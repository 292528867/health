function openAdd() {
    $('#add-modal').modal('open');
}
function openChange(id) {
    var topdata = tops[id];
    console.log(topdata);
    $('#change-modal').modal('open');
    $('#change-name').val(topdata.name);
    $('#change-linkUrl').val(topdata.linkUrl);
    $('#change-picUrl').val(topdata.picUrl);
    $('#change-position').val(topdata.position);
    $('#change-id').val(topdata.id);
    console.log($('#change-id').val())
}
var topAddUrl = commonUrl + 'topline/addTopline/',
    topChangeUrl =commonUrl+'topline/updateTopline/';

function addSub() {
    var name = $('#name').val();
    var linkUrl = $('#linkUrl').val();
    var picUrl = $('#picUrl').val();
    var position = $('#position').val();
    var enabled = $("input[name='enabled']:checked").val();  ;
    $('#type-add').attr('disabled',true);
    if (name.length==0||linkUrl.length==0||picUrl.length==0||position.length==0||enabled.length==0) {
        alert('有字段没有填写');
        $('#type-add').attr('disabled',false);
        return false;
    }
    var json ={
        "name": name,
        "linkUrl": linkUrl,
        "picUrl": picUrl,
        "position": position,
        "enabled": enabled
    };
    json = JSON.stringify(json);
    $.ajax({
        url: topAddUrl,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            if (response.ret_code == 0) {
                alert('success');
                location.reload();
            }
            else{
                alert(response.err_msg);
                $('#type-add').attr('disabled',false);
            }

        },
        error: function () {
            $('#type-add').attr('disabled',false);
            alert('error');
        }


    });
}
function changeSub() {
    var name = $('#change-name').val();
    var linkUrl = $('#change-linkUrl').val();
    var picUrl = $('#change-picUrl').val();
    var position = $('#change-position').val();
    var id = $('#change-id').val();
    $('#change-type-add').attr('disabled',true);
    if (name.length==0||linkUrl.length==0||picUrl.length==0||position.length==0) {
        alert('有字段没有填写');
        $('#change-type-add').attr('disabled',false);
        return false;
    }

    var json ={
        "name": name,
        "linkUrl": linkUrl,
        "picUrl": picUrl,
        "position": position
    };
    json = JSON.stringify(json);
    $.ajax({
        url: topChangeUrl+id,
        type: "POST",
        data: json,
        dataType: "json",
        headers: {"Accept": "application/json", "Content-Type": "application/json; charset=UTF-8"},
        success: function (response) {
            if (response.ret_code == 0) {
                alert('success');
                location.reload();
            }
            else{
                alert(response.err_msg);
                $('#change-type-add').attr('disabled',false);
            }

        },
        error: function () {
            $('#change-type-add').attr('disabled',false);
            alert('error');
        }


    });
}
