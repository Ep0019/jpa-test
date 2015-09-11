<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%; margin:0;font-family:"微软雅黑";}
		#l-map{height:500px;width:1000px;}
		#r-result{width:100%; font-size: 14px; line-height: 20px;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=I6aZeIGITxzBn5VXbgM41ORG"></script>
	<script type="text/javascript" src="${ctx}/javascript/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/javascript/jquery.pager.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
	<title>本地搜索的数据接口</title>
<body>
	<div id="l-map"></div>
	<input type="button" value="银行" onclick="bank('银行')"/>
	<input type="button" value="公交" onclick="bank('公交')"/>
	<input type="button" value="教育" onclick="bank('教育')"/>
	<input type="button" value="生活" onclick="bank('生活')"/>
	<br>
	搜索：<input type="text" id="searchContent" value=""/>
	<input type="button" value="搜索" onclick="searchApi()"/>
	 <div id="pager" class="pagination text-center"></div>
	 <div id="mapPager" class="pagination text-center"></div>
	
</body>
</html>
<script type="text/javascript">
var dataq=[
           {"title":"思明区","avgprice":3.2,"address":"福建省厦门市思明区","location":"118.08919|24.451606"},
           {"title":"湖里区","avgprice":3.2,"address":"福建省厦门市湖里区","location":"118.152718|24.516982"},
           {"title":"集美区","avgprice":3.2,"address":"福建省厦门市集美区","location":"118.103419|24.581536"},
           {"title":"海沧区","avgprice":3.2,"address":"福建省厦门市海沧区","location":"118.040322|24.490283"},
           {"title":"同安区","avgprice":3.2,"address":"福建省厦门市同安区","location":"118.158755|24.728135"},
           {"title":"翔安区","avgprice":3.2,"address":"福建省厦门市翔安区","location":"118.254191|24.623064"}
];
var datax=[
           {"title":"思明区","avgprice":3.2,"address":"福建省厦门市思明区","location":"118.08919|24.451606"},
           {"title":"湖里区","avgprice":3.2,"address":"福建省厦门市湖里区","location":"118.152718|24.516982"},
           {"title":"集美区","avgprice":3.2,"address":"福建省厦门市集美区","location":"118.103419|24.581536"},
           {"title":"海沧区","avgprice":3.2,"address":"福建省厦门市海沧区","location":"118.040322|24.490283"},
           {"title":"同安区","avgprice":3.2,"address":"福建省厦门市同安区","location":"118.158755|24.728135"},
           {"title":"翔安区","avgprice":3.2,"address":"福建省厦门市翔安区","location":"118.254191|24.623064"}
];
var map = new BMap.Map("l-map");        
map.centerAndZoom(new BMap.Point(118.08,24.48), 13);
map.enableScrollWheelZoom();
map.addEventListener("click", getjw);
map.addEventListener("zoomend", zomm);
function getjw(e){
	
}
function zomm(){
	
}
<%--复杂自定义覆盖物start--%>
ComplexCustomOverlay.prototype = new BMap.Overlay();
ComplexCustomOverlay.prototype.initialize = function(map){
  this._map = map;
  var div = this._div = document.createElement("div");
  div.style.position = "absolute";
  div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
  div.style.backgroundColor = "#EE5D5B";
  div.style.border = "1px solid #BC3B3A";
  div.style.color = "white";
  div.style.height = "18px";
  div.style.padding = "2px";
  div.style.lineHeight = "18px";
  div.style.whiteSpace = "nowrap";
  div.style.MozUserSelect = "none";
  div.style.fontSize = "12px"
  var span = this._span = document.createElement("span");
  div.appendChild(span);
  span.appendChild(document.createTextNode(this._text));      
  var that = this;

  var arrow = this._arrow = document.createElement("div");
  arrow.style.background = "url(http://map.baidu.com/fwmap/upload/r/map/fwmap/static/house/images/label.png) no-repeat";
  arrow.style.position = "absolute";
  arrow.style.width = "11px";
  arrow.style.height = "10px";
  arrow.style.top = "22px";
  arrow.style.left = "10px";
  arrow.style.overflow = "hidden";
  div.appendChild(arrow);
 
  div.onmouseover = function(){
    this.style.backgroundColor = "#6BADCA";
    this.style.borderColor = "#0000ff";
    this.getElementsByTagName("span")[0].innerHTML = that._overText;
    arrow.style.backgroundPosition = "0px -20px";
  }

  div.onmouseout = function(){
    this.style.backgroundColor = "#EE5D5B";
    this.style.borderColor = "#BC3B3A";
    this.getElementsByTagName("span")[0].innerHTML = that._text;
    arrow.style.backgroundPosition = "0px 0px";
  }
  var myPoint=this._point;
  //一级地区的点击事件（让地图放大三个级别）
  div.onclick = function(){
	  map.centerAndZoom(myPoint,map.getZoom()+3);
	  map.clearOverlays();
  }
  map.getPanes().labelPane.appendChild(div);
  return div;
}
ComplexCustomOverlay.prototype.draw = function(){
  var map = this._map;
  var pixel = map.pointToOverlayPixel(this._point);
  this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
  this._div.style.top  = pixel.y - 30 + "px";
}
function ComplexCustomOverlay(point, text, mouseoverText,map){
  this._point = point;
  this._text = text;
  this._overText = mouseoverText;
}
<%--复杂的自定义覆盖物end--%>
initDataq(map);
<%--初始化数据六个大区--%>
function initDataq(map){
	var initPoint=[];
	for(var i=0;i<dataq.length;i++){
		var point = new BMap.Point(dataq[i].location.split("|")[0],dataq[i].location.split("|")[1]);
		var marker = new BMap.Marker(point);
		initPoint.push(point);
		var myCompOverlay = new ComplexCustomOverlay(point, dataq[i].title,dataq[i].title+" "+dataq[i].avgprice);
	    map.addOverlay(myCompOverlay);
	}
	map.setViewport(initPoint);
}
</script>