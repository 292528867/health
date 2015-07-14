/**
 * Created by wade on 15/7/13.
 */



var articleList;
var commonUrl = 'http://101.231.124.8:45677/xlab-healthcloud/';
var articleUrl = commonUrl + 'discovery/app/recommand/articles/';
var healthCategoryId = window.location.search.substring(1)||2 ;
var typeUrl = commonUrl + 'discovery/app/recommand/tags/';
var typeTagUrl = commonUrl + 'discovery/app/recommand/tags/';
var typeArticleUrl = commonUrl + 'discovery/app/recommand/tag/articles/';

var mySwiper;

if (sessionStorage.getItem('articleUrl') != null) {
  loadArticles(sessionStorage.getItem('tag'));
}
else {
  inLoad();
}

function inLoad() {
  $('#error-png').hide();
  $('.today-body').removeClass('today-body-banners');
  $.get(articleUrl + healthCategoryId, function (data) {
    var values = data.ret_values;
    articleList = values;
    var bannerTemple = Handlebars.compile($('#bannerBox').html());
    $('#banners').html(bannerTemple(values[0]));
    values.splice(0,1);
    var articleTemple = Handlebars.compile($('#todayBox').html());
    $('#article').html(articleTemple(values));
  });
  $.get(typeUrl + healthCategoryId, function (data) {
    data = data.ret_values;
    var swiperTemple = Handlebars.compile($('#swiperBox').html());
    $('#swipers').html(swiperTemple(data));
    mySwiper = new Swiper('#header', {
      initialSlide: 0,
      freeMode: true,
      slidesPerView: 'auto'
    });

  });
}




function loadArticles(id) {
  $('#error-png').hide();
  $('.today-bar').attr('onclick', 'loadTodayArticles()');
  $('.today-bar').addClass('today-bar-noactive');
  $('#banners').html('');
/*
  if (typeof(mySwiper) != 'undefined') {
    mySwiper.removeAllSlides();
  }
*/
  $.get(typeTagUrl + healthCategoryId + '/' + id, function (data) {
    data = data.ret_values;
    //console.log(data);
    var swiperTemple = Handlebars.compile($('#swiperBox').html());
    $('#swipers').html(swiperTemple(data));
    var swiper = $('.swiper-wrapper').find('.swiper-slide:eq(1)');
    swiper.addClass('swiper-active');
    swiper.attr('onclick', '');
    mySwiper = new Swiper('#header', {
      initialSlide: 0,
      freeMode: true,
      slidesPerView: 'auto'
    });

  });


  $.get(typeArticleUrl + id + '/' + healthCategoryId, function (data) {
    var values = data.ret_values.content;
    articleList = values;
    var articleTemple = Handlebars.compile($('#articlesBox').html());
    $('#banners').html(articleTemple(articleList));
    typeArticleUrl = commonUrl + 'discovery/app/recommand/tag/articles/';
    sessionStorage.setItem('articleUrl', typeArticleUrl);
    sessionStorage.setItem('tag', id);
    if (values.length == 0) {
      $('#error-png').show();
    }
  });


}


function loadTodayArticles() {
  $('#error-png').hide();
  $('.today-bar').attr('onclick', '');
/*
  if (typeof(mySwiper) != 'undefined') {
    mySwiper.removeAllSlides();
  }
*/
  $.get(typeUrl + healthCategoryId, function (data) {
    data = data.ret_values;
    var swiperTemple = Handlebars.compile($('#swiperBox').html());
    $('#swipers').html(swiperTemple(data));
    var swiper = $('.swiper-wrapper').find('.swiper-slide:eq(1)');
    swiper.removeClass('swiper-active');
    $('.today-bar').removeClass('today-bar-noactive');
    mySwiper = new Swiper('#header', {
      initialSlide: 0,
      freeMode: true,
      slidesPerView: 'auto'
    });

  });

  $.get(articleUrl + healthCategoryId, function (data) {
    var values = data.ret_values;
    articleList = values;
    var bannerTemple = Handlebars.compile($('#bannerBox').html());
    $('#banners').html(bannerTemple(values[0]));
    values.splice(0,1);
    var articleTemple = Handlebars.compile($('#todayBox').html());
    $('#article').html(articleTemple(values));
    typeArticleUrl = commonUrl + 'discovery/app/recommand/tag/articles/';
    sessionStorage.removeItem('articleUrl');
  })
}

function getDetails(id) {
  var articleId = articleList[id].id || JSON.parse(localStorage.getItem('articleList')[id].id);
  var clickUrl = commonUrl + 'discovery/app/clickInfo/' + healthCategoryId + '/' + articleId;
  articleList[id].clickCount = articleList[id].clickCount + 1;
  $.get(clickUrl, function (data) {
    localStorage.setItem('article', JSON.stringify(articleList[id]));
    localStorage.setItem('articleList', JSON.stringify(articleList));
    window.location.href = 'detail.html?' + articleId;
  });
}


