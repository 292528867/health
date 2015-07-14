var articleIdLocal = localStorage.getItem('articleId');
var commonUrl = 'http://101.231.124.8:45677/xlab-healthcloud/';
var url = commonUrl + 'discovery/app/listInfo/';
var articleId = window.location.search.substring(1);
articleId = articleId.split('&')[0];
var article;
$.get(url + articleId, function (data) {
  article = data.ret_values;
  $('#detail-img').attr('src', article.pictureUrl);
  $('#details').html(article.htmlInfo);
  $('#detail-title').html(article.title);
  $('#detail-count').html(article.clickCount + '人阅读');
  setTimeout(load(), 500);
//    $('title').html(article.title);
  var articleHeight = $('#details').height() + 20;
  var height = $(window).height() * 2;
  if (height < articleHeight) {
    $('#details').css('height', height);
    $('.unfold-field').show();
    $('.banner-bg').show();
  }
});
if (articleIdLocal != null && articleIdLocal == articleId) {
  $('.detail-foot').show();
}

function load() {
  $('.data-body').fadeIn();
}
function openArticle() {
  $('#details').css('height', 'auto');
  $('.unfold-field').hide();
  $('.banner-bg').hide();
}
function activityList(id) {
  window.location.href = 'share.html?' + id.share;
}
