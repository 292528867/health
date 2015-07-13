(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){

var article = localStorage.getItem('article');
var commonUrl = 'http://101.231.124.8:45677/xlab-healthcloud/';
var url = commonUrl + 'discovery/app/listInfo/';
var articleId = window.location.search.substring(1);
articleId = articleId.split('&')[0];

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
if (article != null&&JSON.parse(article).id==articleId) {
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

},{}]},{},[1]);
