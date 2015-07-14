/**
 * Created by wade on 15/7/13.
 */



var articleList;
var commonUrl = 'http://101.231.124.8:45677/xlab-healthcloud/';
var articleUrl = commonUrl + 'discovery/app/recommand/articles/';
var healthCategoryId = window.location.search.substring(1) || 2;
var typeUrl = commonUrl + 'discovery/app/recommand/tags/';
var typeTagUrl = commonUrl + 'discovery/app/recommand/tags/';
var typeArticleUrl = commonUrl + 'discovery/app/recommand/tag/articles/';

var mySwiper;
var size = 6, page = 0, loads = 0,totalPages= 0,number= 0,flag=0;


if (sessionStorage.getItem('articleUrl') != null) {
  loadArticles(sessionStorage.getItem('tag'));
}
else {
  inLoad();
}

function inLoad() {
  $('#error-png').hide();
  $('.today-body').removeClass('today-body-banners');
  page = 0, loads = 0,totalPages= 0,number= 0,flag=1;
  $.get(articleUrl + healthCategoryId, function (data) {
    var values = data.ret_values;
    articleList = values;
    var bannerTemple = Handlebars.compile($('#bannerBox').html());
    $('#banners').html(bannerTemple(values[0]));
    values.splice(0, 1);
    var articleTemple = Handlebars.compile($('#todayBox').html());
    $('#article').html(articleTemple(values));
  });
  $.get(typeUrl + healthCategoryId, function (data) {
    data = data.ret_values;
    if (typeof(mySwiper) != 'undefined') {
      mySwiper.removeAllSlides();
    }
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
  page = 0, loads = 0,totalPages= 0,number= 0,flag=0;
  $('#banner-scroll').hide();
  $('#error-png').hide();
  $('.today-bar').attr('onclick', 'loadTodayArticles()');
  $('.today-bar').addClass('today-bar-noactive');
  $('#banner-scroll').show();
  $('#banners').html('');
  $('#article').html('');
  /*
   if (typeof(mySwiper) != 'undefined') {
   mySwiper.removeAllSlides();
   }
   */
  $.get(typeTagUrl + healthCategoryId + '/' + id, function (data) {
    data = data.ret_values;
    //console.log(data);
    if (typeof(mySwiper) != 'undefined') {
      mySwiper.removeAllSlides();
    }
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


  loadTypeArticles(id);
  typeArticleUrl = commonUrl + 'discovery/app/recommand/tag/articles/';
  sessionStorage.setItem('articleUrl', typeArticleUrl);
  sessionStorage.setItem('tag', id);


}


function loadTodayArticles() {
  page = 0, loads = 0,totalPages= 0,number= 0,flag=1;
  $('#error-png').hide();
  $('.today-bar').attr('onclick', '');
  $('#banner-scroll').hide();
  /*
   if (typeof(mySwiper) != 'undefined') {
   mySwiper.removeAllSlides();
   }
   */
  $.get(typeUrl + healthCategoryId, function (data) {
    data = data.ret_values;
    if (typeof(mySwiper) != 'undefined') {
      mySwiper.removeAllSlides();
    }
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
    values.splice(0, 1);
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


function loadTypeArticles(id) {
  $.get(typeArticleUrl + id + '/' + healthCategoryId + '?page=' + page + '&size=' + size, function (data) {
    var values = data.ret_values.content;
    articleList = values;
    var articleTemple = Handlebars.compile($('#articlesBox').html());
    if(flag===0)
    $('#banners').append(articleTemple(articleList));
    if (values.length == 0) {
      $('#error-png').show();
      $('#banner-scroll').hide();
    }
    page ++;
    loads = 1;
    number = data.ret_values.number+1;
    totalPages = data.ret_values.totalPages;
    if(number===totalPages){
      $('#banner-scroll').hide();
    }
  });
}


$(document).ready(function () {
  var totalheight = 0;
  var documentHeight = 0;
  $(window).scroll(function () {
    var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)

    //console.log("滚动条到顶部的垂直高度: "+$(document).scrollTop());
    //console.log("页面的文档高度 ："+$(document).height());
    //console.log('浏览器的高度：'+$(window).height());
    //alert(maxnum);
    totalheight = parseFloat($(window).height()) + parseFloat(srollPos);
    documentHeight = ($(document).height());
    if (documentHeight <= totalheight && loads === 1&&number<totalPages&&flag===0) {
      loads = 0;
      loadTypeArticles(sessionStorage.getItem('tag'));
    }
  });

})
