/**
 * Created by wade on 15/7/13.
 */
var article = localStorage.getItem('article');
var commonUrl = 'http://101.231.124.8:45677/xlab-healthcloud/';
var url = commonUrl + 'task/retrievePackageDetail/';
var shareUrl = commonUrl + 'task/sharePackageDetail/';
var articleId = window.location.search.substring(1);
var ids = articleId.split('&');
var userId = ids[0];
articleId = ids[1];

if (userId == 'share') {
  $.get(shareUrl + articleId, function (data) {
    article = data.ret_values;
    $('#detail-img').attr('src', article.pictureUrl);
    $('#details').html(article.detail);
    $('#detail-title').html(article.title);
    $('title').html(article.title);
    $('#detail-count').html(article.clickAmount + '人关注');
    $('#schedule-img').attr('src', article.pictureUrl);
    $('#schedule-img').show();
  });
}

else {
  $.get(url + userId + '/' + articleId, function (data) {
    article = data.ret_values;
    $('#detail-img').attr('src', article.pictureUrl);
    $('#details').html(article.detail);
    $('#detail-title').html(article.title);
    $('title').html(article.title);
    $('#detail-count').html(article.clickAmount + '人关注');
    var string = '';
    if (article.type == 2) {
      $('#blood').show();
      $('#schedule-value').html();
      $.each(article.statementDtos, function (n, value) {
        string += '<div style="margin-bottom: 10px;width: 100%;"><div style="width: 10%;text-align: left;height: 25px;" class="am-vertical-align">' +
          '<img src="i/schedule/check.jpg" alt="" width="25" style="vertical-align: top" class="am-vertical-align-middle"/>' +
          '</div>' +
          '<div style="width: 60%" id="shedule-time">' + new Date(value.time).Format("yyyy-MM-dd hh:mm") + '</div>' +
          '<div style="width: 30%"><span id="schedule-value">' + value.statement + '</span>mmol/L</div></div>' +
          '<hr data-am-widget="divider" style="" class="am-divider am-divider-dashed"/>';
      });
      $('#shedule-box').append(string);
    }
    if (article.type == 3) {
      $('#pressure').show();
      $('#schedule-value').html();
    }


    if (article.complete == 1) {
      var $finish = $('.finish-btn');
      $finish.addClass('finish-btn-ed');
      $finish.html('已完成');
      $('.finish-divider').hide();
      $('.foot-box').hide();
      $('#finish-btn').attr('disabled', true);
    }
    else {
    }
    $('.schedule-foot').show();
    $('#schedule-img').attr('src', article.pictureUrl);
    $('#schedule-img').show();
  });

}

function finishTask() {
//    noticePhone('2222');
  var $finish = $('.finish-btn');
  $finish.button('loading');
  $finish.attr('disabled', true);
  if ($('#getValue').val().length == 0 && article.type == 2) {
    $finish.button('reset');
    $finish.attr('disabled', false);
    $('#error-content').html('请填写内容');
    $('#error-alert').modal('open');
    return false;
  }
  if (article.type == 3 && $('#pressureValue1').val().length == 0) {
    $finish.button('reset');
    $finish.attr('disabled', false);
    $('#error-content').html('请填写内容');
    $('#error-alert').modal('open');
    console.log(1);
    return false;
  }
  if (article.type == 3 && $('#pressureValue2').val().length == 0) {
    $finish.button('reset');
    $finish.attr('disabled', false);
    $('#error-content').html('请填写内容');
    $('#error-alert').modal('open');
    console.log(1);
    return false;
  }
  var content = '';
  if(article.type == 3){
    var pressureValue=new Array();
    pressureValue[0]=$('#pressureValue1').val();
    pressureValue[1]=$('#pressureValue2').val();
    if($('#pressureValue3').val().length!==0)
    pressureValue[2]=$('#pressureValue3').val();
    content = pressureValue.join(',');
  }
  if(article.type == 2){
    content =$('#getValue').val();
  }
  console.log(content);
  $.ajax({
    url: commonUrl + 'task/confirmDetail/' + userId + '/' + articleId + '/',
    type: "POST",
    data: {'content': content},
    dataType: "json",
    success: function (response) {
      if (response.ret_code == 0) {
        activityList({'finish': '1'});
        $('#getValue').attr('disabled', true);
        var $finish = $('.finish-btn');
        $finish.addClass('finish-btn-ed');
        $finish.button('reset');
        $finish.attr('disabled', true);
        $finish.html('已完成');
        $('.foot-box').hide();
        $('.finish-divider').hide();
        location.href = '#finish';
      }
      else {
        $('#finish-btn').button('reset');
        $('#finish-btn').attr('disabled', false);
        $('#error-content').html(response.ret_values);
        $('#error-alert').modal('open');
      }


    },
    error: function () {
      $('#error-content').html('请检查你的网络');
      $('#error-alert').modal('open');
    }


  });
  $finish.button('reset');

}
function activityList() {

}

Date.prototype.Format = function (fmt) { //author: meizz
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}

