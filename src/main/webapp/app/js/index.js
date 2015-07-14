/**
 * Created by wade on 15/7/13.
 */

window.onload = function () {
}


var articleList;
var articleItem;
var commonUrl = 'http://101.231.124.8:45677/xlab-healthcloud/';

var articleUrl = commonUrl + 'discovery/app/recommand/articles/';
var healthCategoryId = window.location.search.substring(1)||2 ;
var typeUrl = commonUrl + 'discovery/app/recommand/tags/';
var typeTagUrl = commonUrl + 'discovery/app/recommand/tags/';
var typeArticleUrl = commonUrl + 'discovery/app/recommand/tag/articles/';

var articleBack;
var bannerBack = $('.banner-today');
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
    var values = data.ret_values, $article = $('.article');
    articleList = values;
    var u = '';
    var clickCount = 0;
    $.each(values, function (n, value) {
      if (n === 0) {
        $('.banner-today-img').css('background', 'url("' + value.pictureUrl2 + '") 50% 50% / cover no-repeat');
        $('.banner-today-title').html(value.title);
        $('.banner-today-click').html('<img src="i/icon/read.png" alt="阅读"/>' + value.clickCount + '阅读');
        $('.banner-today-click').attr('id', 'click' + value.id);
        $('.banner-today-img').attr('onclick', 'getDetails(\'' + n + '\');');
        $('.banner-today').show();
        //console.log('getDetails('+healthCategoryId+'/'+value.id+');');
      }
      else {
        $article.find('.article-img').attr('src', value.pictureUrl);
        $article.find('li').css('display', 'block');
        $article.find('.article-title').html(value.title);
//          if(value.clicked) {
//            $article.find('.article-title').css('color','#afafaf');
//          }
//          else  {
//            $article.find('.article-title').css('color','#383838');
//          }
        $article.find('.article-description').html(value.desc);
        $article.find('.article-clickCount').html('<img src="i/icon/read-grey.png" alt="阅读"/>' + value.clickCount + '阅读');
        $article.find('.article-clickCount').attr('id', 'click' + value.id);
        $article.find('li').attr('onclick', 'getDetails(\'' + n + '\')');
        u = u + $article.html();
      }
    });
    //$('.article  li:first').siblings().remove();
    articleBack = $article.find('li:eq(0)').detach();
    $('#article').append(u).show();


  });
}


function getDetails(id) {
  var articleId = articleList[id].id || JSON.parse(localStorage.getItem('articleList')[id].id);
  var clickUrl = commonUrl + 'discovery/app/clickInfo/' + healthCategoryId + '/' + articleId;
  articleList[id].clickCount = articleList[id].clickCount + 1;
  $.get(clickUrl, function (data) {
    localStorage.setItem('article', JSON.stringify(articleList[id]));
    localStorage.setItem('articleList', JSON.stringify(articleList));
    document.cookie = "statuscookie=1";
    window.location.href = 'detail.html?' + articleId;
  });
}


function loadArticles(id) {
  $('#error-png').hide();
  $('.today-body').addClass('today-body-banners');
  typeArticleUrl = typeArticleUrl + id + '/' + healthCategoryId;
  $('#banners').html(bannerBack.hide());
  var $article = $('.article');
  if (typeof(articleBack) == 'undefined' || articleBack.length == 0) {
    articleBack = $article.find('li:eq(0)').detach();
  }
  $('#article').html('');
  if (typeof(mySwiper) != 'undefined') {
    mySwiper.removeAllSlides();
  }
  $.get(typeTagUrl + healthCategoryId + '/' + id, function (data) {
    data = data.ret_values;
    //console.log(data);
    $('.swiper-wrapper').html('<div class="swiper-slide" style="width: 100px;"></div>');
    var types = '';
    $.each(data, function (n, value) {
      types += '<div class=\'swiper-slide\' ' +
      'onclick=\'loadArticles(' + value.id + ')\'>' + value.tag + '</div>';
    });
    $('.swiper-wrapper').append(types);
    var swiper = $('.swiper-wrapper').find('.swiper-slide:eq(1)');
    swiper.addClass('swiper-active');
//      sessionStorage.setItem('onclick',  swiper.attr('onclick'));
    swiper.attr('onclick', '');
    $('.today-bar').attr('onclick', 'loadTodayArticles()');
    $('.today-bar').addClass('today-bar-noactive');
    mySwiper = new Swiper('#header', {
      initialSlide: 0,
      freeMode: true,
      slidesPerView: 'auto'
    });

  });


  $.get(typeArticleUrl, function (data) {
//      console.log(data.ret_values);
    var values = data.ret_values.content;
    articleList = values;
    var u = '';
    $.each(values, function (n, value) {
      $('.banner-today-img').css('background', 'url("' + value.pictureUrl2 + '") 50% 50% / cover no-repeat');
      $('.banner-today-title').html(value.title);
      $('.banner-today-click').attr('id', 'click' + value.id);
      $('.banner-today-click').html('<img src="i/icon/read.png" alt="阅读"/>' + value.clickCount + '阅读');
      $('.banner-today-img').attr('onclick', 'getDetails(\'' + n + '\');');
      $('.banner-today').show();
      u = u + $('#banners').html();
    });
    bannerBack = $('#banners').find('.banner-today:eq(0)').detach();
    $('#banners').append(u).show();
    typeArticleUrl = commonUrl + 'discovery/app/recommand/tag/articles/';
    sessionStorage.setItem('articleUrl', typeArticleUrl);
    sessionStorage.setItem('tag', id);
    console.log(articleBack);
    if (values.length == 0) {
      $('#error-png').show();
    }
  });


}


function loadTodayArticles() {
  $('#error-png').hide();
  $('.today-body').removeClass('today-body-banners');
  $('.today-bar').attr('onclick', '');
  $('.article').html(articleBack.hide());
  $('#banners').html(bannerBack.hide());
  if (typeof(mySwiper) != 'undefined') {
    mySwiper.removeAllSlides();
  }
  $.get(typeUrl + healthCategoryId, function (data) {
    data = data.ret_values;
    //console.log(data);
    $('.swiper-wrapper').html('<div class="swiper-slide" style="width: 100px;"></div>');
    var types = '';
    $.each(data, function (n, value) {
      types += '<div class=\'swiper-slide\' ' +
      'onclick=\'loadArticles(' + value.id + ')\'>' + value.tag + '</div>';
    });
    $('.swiper-wrapper').append(types);
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
//      console.log(data.ret_values);
    var values = data.ret_values, $article = $('.article');
    articleList = values;
    var u = '';
    $.each(values, function (n, value) {
//        console.log(n);
      if (n === 0) {
        $('.banner-today-img').css('background', 'url("' + value.pictureUrl2 + '") 50% 50% / cover no-repeat');
        $('.banner-today-title').html(value.title);
        $('.banner-today-click').attr('id', 'click' + value.id);
        $('.banner-today-click').html('<img src="i/icon/read.png" alt="阅读"/>' + value.clickCount + '阅读');
        $('.banner-today').show();
        $('.banner-today-img').attr('onclick', 'getDetails(\'' + n + '\');');
        //console.log('getDetails('+healthCategoryId+'/'+value.id+');');
      }
      else {
        $article.find('.article-img').attr('src', value.pictureUrl);
        $article.find('li').css('display', 'block');
        $article.find('.article-title').html(value.title);
        $article.find('.article-description').html(value.desc);
        $article.find('li').attr('onclick', 'getDetails(\'' + n + '\')');
        $article.find('.article-clickCount').html('<img src="i/icon/read-grey.png" alt="阅读"/>' + value.clickCount + '阅读');
        $article.find('.article-clickCount').attr('id', 'click' + value.id);
        ;
        u = u + $article.html();
      }

    });
//      $('.article  li:first').siblings().remove();
    articleBack = $article.find('li:eq(0)').detach();
    $('#article').append(u).show();
    typeArticleUrl = commonUrl + 'discovery/app/recommand/tag/articles/';
    sessionStorage.removeItem('articleUrl');
  })
}


$.get(typeUrl + healthCategoryId, function (data) {
  data = data.ret_values;
  //console.log(data);
  var types = '';
  $.each(data, function (n, value) {
    types += '<div class=\'swiper-slide\' ' +
    'onclick=\'loadArticles(' + value.id + ')\'>' + value.tag + '</div>';
  });
  $('.swiper-wrapper').append(types);
  mySwiper = new Swiper('#header', {
    initialSlide: 0,
    freeMode: true,
    slidesPerView: 'auto'
  });

});

