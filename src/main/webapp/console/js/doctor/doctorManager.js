/**
 * Created by Jeffrey on 15/7/12.
 */
var doctorUrl = commonUrl + "doctor/doctors/apply";
var loadData = function () {
    $.get(doctorUrl, function (data) {
        var datas = "";
        $.each(data, function (n, value) {
            datas += "<tr style='display: none' ><td>" + value.id +
                "</td>" +
                "<td><div><img class='portrait' onclick='imgClick(\""+ value.iconUrl +"\");' " + modalLink(value.iconUrl) + " src='" + value.iconUrl+"' alt=\"X\"/></div></td>" +
                " <td class='am-hide-sm-only'>" + value.iCardName +"</td>" +
                " <td class='am-hide-sm-only'>" + value.qualificationName +"</td>" +
                " <td class='am-hide-sm-only'>" + value.hospital +"</td>" +
                " <td class='am-hide-sm-only'>" + value.department +"</td>" +
                " <td class='am-hide-sm-only'><div><img class='qualification' onclick='imgClick(\""+ value.iCardUrl +"\");' " + modalLink(value.iCardUrl) + " src='" + value.iCardUrl +"' alt='X'/></div></td>" +
                " <td class='am-hide-sm-only'><div><img class='qualification' onclick='imgClick(\""+ value.qualificationUrl +"\");' " + modalLink(value.qualificationUrl) + " src='" + value.qualificationUrl +"' alt='X'/></div></td>" +
                " <td class='am-hide-sm-only'><div><img class='qualification' onclick='imgClick(\""+ value.permitUrl +"\");' " + modalLink(value.permitUrl) + " src='" + value.permitUrl +"' alt='X'/></div></td>" +
                " <td class='am-hide-sm-only'>" + value.tel +"</td>" +
                " <td id='handle" + value.id +"'><div class='am-btn-toolbar'><div class='am-btn-group am-btn-group-xs'>" +
                "<button id='doctor-pass"+ value.id +"' onclick='check(" + value.id +", 1)' class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button'>" +
                "<span class='am-icon-check-circle-o'></span>通过</button>" +
                "<button id='doctor-failure"+ value.id +"' onclick='check(" + value.id +", 0)' class='am-btn am-btn-default am-btn-xs am-text-secondary hc-fail' type='button'>" +
                "<span class='am-icon-ban'></span>不通过</button></div></div>" +
                "</td></tr>"
        });
        $("#datas").append(datas);
        $("#datas tr").fadeIn(300);

    });
}

var modalLink = function (url) {
    var dataModal = "data-am-modal=\"{target: '#doc-modal-1', closeViaDimmer: 0, width: 600, height: 500}\"";
    return null == url || url =="" ? "": dataModal;
};

var imgClick = function (url) {
    $(".modal-img").attr("src", url);
}

$(function () {
    loadData();
});

var check = function (doctorId, isPass) {
    var url = commonUrl + "doctor/check/"+ doctorId;
    var data = {};
    if (0 == isPass) {
        data = {"checked":"fail"};
    } else {
        data = {"checked":"passed"};
    }
    $.ajax({
        url: url,
        type: "POST",
        data: data,
        success: function (data) {
            if (data && isPass != 0 ){
                $("#handle" + doctorId).html("通过");
            }else if (data && isPass == 0) {
                $("#handle" + doctorId).html("不通过");
            }
        }
    })
}

