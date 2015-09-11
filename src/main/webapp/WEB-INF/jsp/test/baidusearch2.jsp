<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%; margin:0;font-family:"微软雅黑";}
		#l-map{height:500px;width:1000px;}
		#r-result{width:100%; font-size: 14px; line-height: 20px;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=I6aZeIGITxzBn5VXbgM41ORG"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
	<title>本地搜索的数据接口</title>
	</head>
<body>
	<div id="l-map"></div>
	<input type="button" value="银行" onclick="bank('银行')"/>
	<input type="button" value="公交" onclick="bank('公交')"/>
	<input type="button" value="教育" onclick="bank('教育')"/>
	<input type="button" value="生活" onclick="bank('生活')"/>
	<br>
	搜索：<input type="text" id="searchContent" value=""/>
	<input type="button" value="搜索" onclick="searchApi()"/>
	
</body>
</html>
<script type="text/javascript">
var pid=0;
var myZoom=11;
//一级地区的数据
var dataq=[
           {"title":"思明区","avgprice":3.2,"address":"福建省厦门市思明区","location":"118.08919|24.451606","pid":0,"id":1,"level":0},
           {"title":"湖里区","avgprice":3.2,"address":"福建省厦门市湖里区","location":"118.152718|24.516982","pid":0,"id":2,"level":0},
           {"title":"集美区","avgprice":3.2,"address":"福建省厦门市集美区","location":"118.103419|24.581536","pid":0,"id":3,"level":0},
           {"title":"海沧区","avgprice":3.2,"address":"福建省厦门市海沧区","location":"118.040322|24.490283","pid":0,"id":4,"level":0},
           {"title":"同安区","avgprice":3.2,"address":"福建省厦门市同安区","location":"118.158755|24.728135","pid":0,"id":5,"level":0},
           {"title":"翔安区","avgprice":3.2,"address":"福建省厦门市翔安区","location":"118.254191|24.623064","pid":0,"id":6,"level":0}
];
//一级地区下的二级地区的数据
var datax=[
           {"title":"石鼓山立交桥","avgprice":3.2,"address":"福建省厦门市湖里区石鼓山立交桥","location":"118.11969|24.527717","pid":2,"id":1,"level":1},
           {"title":"五缘湾道","avgprice":3.2,"address":"福建省厦门市湖里区五缘湾道","location":"118.174739|24.524561","pid":2,"id":2,"level":1},
           {"title":"塘边","avgprice":3.2,"address":"福建省厦门市湖里区兴隆路453","location":"118.125116|24.514106","pid":2,"id":3,"level":1},
           {"title":"江头","avgprice":3.2,"address":"福建省厦门市湖里区台湾街161号-之9","location":"118.138483|24.501744","pid":2,"id":4,"level":1},
           {"title":"莲坂","avgprice":3.2,"address":"福建省厦门市思明区莲岳路1号","location":"118.127649|24.487933","pid":2,"id":5,"level":1},
           {"title":"黄厝","avgprice":3.2,"address":"福建省厦门市思明区黄厝路","location":"118.166313|24.45307","pid":2,"id":6,"level":1}
];
//三级地区数据
var dataxq=[
           {"title":"特房五缘湾尊府","avgprice":3.2,"address":"福建省厦门市湖里区金山路","location":"118.170984|24.524857","pid":2,"id":1,"level":2},
           {"title":"国贸天琴湾","avgprice":3.2,"address":"福建省厦门市湖里区五缘湾道","location":"118.176445|24.525087","pid":2,"id":2,"level":2},
           {"title":"紫金家园","avgprice":3.2,"address":"福建省厦门市湖里区金山路","location":"118.171092,24.526862","pid":2,"id":3,"level":2}
];


var map = new BMap.Map("l-map");        
map.centerAndZoom(new BMap.Point(118.08,24.48),11);
//设置地图最小级别（这里只定位厦门这块区域）
map.setMinZoom(11);
map.enableScrollWheelZoom();
map.addEventListener("click", getjw);
//map.addEventListener("zoomstart", zoomstart);
map.addEventListener("zoomend", zomm);
map.addEventListener("dragend", dragend);
function zomm(){
	var zoom=map.getZoom();
	myZoom=zoom;
	if(zoom <=11){
		//初始化一级地区
		map.clearOverlays();
		initData(map,dataq,0);
		map.centerAndZoom(new BMap.Point(118.08,24.48),11);
		//map.setZoom(11);
	}else if(zoom> 11 && zoom <= 14){
		//二级地区数据
		map.clearOverlays();
		initData(map,datax,1);
		//map.panTo(new BMap.Point(118.152718,24.516982))
		// map.centerAndZoom(new BMap.Point(118.152718,24.516982));
		//map.setZoom(11);
	}else if(zoom > 15){
		//if(zoom != 19){
		//	map.clearOverlays();
		//}
		//三级地区数据
		map.clearOverlays();
		initData(map,dataxq,2);
		//pid为0直
		 /* for(i=0;i<dataxq.length;i++){
			    var point = new BMap.Point(dataxq[i].location.split("|")[0],dataxq[i].location.split("|")[1]);
		        var result = BMapLib.GeoUtils.isPointInRect(point, map.getBounds());
		        if(result == true){
		        	var m = new BMap.Marker(point); 
		        	map.addOverlay(m);
		        }
	    } */
	}
	alert("最后加载列表数据");
}

function getjw(e){
	
}
//function zomm(){
//	alert(map.getZoom());
	//if(map.getZoom() <=11){
		//初始化一级地区
	//	map.clearOverlays();
	//	initData(map,dataq,0);
	//	map.setZoom(11);
	
	//}
//}
//拖拽功能实现
function dragend(){
	
	var bounds=map.getBounds();
	var sw=bounds.getSouthWest();
	var ne=bounds.getNorthEast();
	var minX0=sw.lat;
	var maxX1=ne.lat;
	var minY0=sw.lng;
	var maxY1=ne.lng;
	if(myZoom> 11 && myZoom <= 14){
		//map.clearOverlays();
		//获取level=1的数据
	}else{
		//map.clearOverlays();
		//获取level=2的数据
	}
	//pid将在这里会被使用（鼠标停止拖动时，加载可视区域的标注）
	//alert(pid);
	//alert('拖动');
	//map.clearOverlays();
	//initData(map,datax,1);
}
//复杂自定义覆盖物start
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
  var title =this._text;
  var level=this._level;
  var address=this._address;
  //一级地区的点击事件,加载该区域下范围的信息（让地图放大三个级别）
  div.onclick = function(){
	  //放到最小级别的时候处理
	  //if(map.getZoom() != 19){
		  if(map.getZoom() != 19){
			  map.clearOverlays();
			  if(level == 1){
			  	  map.centerAndZoom(myPoint,map.getZoom()+5);
			  }else{
				  map.centerAndZoom(myPoint,map.getZoom()+3);
			  }
		  }else{
			  alert('列表数据');
		  }
		  
	  //}else{
		  
		  //加载数据列表
		//  alert("加载我的数据列表");
	 // }
	  
	  //map.setZoom(map.getZoom()+3);
	  //map.setZoom(map.getZoom()+3);
	  //重新初始化一级地区下的数据(获取该地区下的数据，然后渲染)
	  //if(level == 2){
		  //alert(11);
		  //alert("hello");
		  //这里可以弹出详细的信息窗口
		//  showInfo(myPoint,title,address,map);
	  //}else{
		//  map.centerAndZoom(myPoint,map.getZoom()+3);
	  	//initData(map,datax,level);
	  //}
	  //map.setZoom(map.getZoom()+3);
	  //实际应用中可以给pid赋值（记录当前区域的上一级信息（鼠标拖动处理拖动加载事件））;
  }
  map.getPanes().labelPane.appendChild(div);
  return div;
}
function test(point){
	alert(point.lat);
}
ComplexCustomOverlay.prototype.draw = function(){
  var map = this._map;
  var pixel = map.pointToOverlayPixel(this._point);
  this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
  this._div.style.top  = pixel.y - 30 + "px";
}
function ComplexCustomOverlay(point,text,mouseoverText,level,address){
  this._point = point;
  this._text = text;
  this._overText = mouseoverText;
  this._level = level;
  this._address=address;
}
//复杂的自定义覆盖物end 0表示一级地区不需要过滤
map.setViewport(initData(map,dataq,0));
//初始化数据六个大区
function initData(map,data,level){
	var initPoint=[];
	for(var i=0;i<data.length;i++){
		var point = new BMap.Point(data[i].location.split("|")[0],data[i].location.split("|")[1]);
		var result = BMapLib.GeoUtils.isPointInRect(point, map.getBounds());
		if(result){
			//var marker = new BMap.Marker(point);
			initPoint.push(point);
			var myCompOverlay = new ComplexCustomOverlay(point, data[i].title, data[i].title+" "+data[i].avgprice,data[i].level,data[i].address);
			map.addOverlay(myCompOverlay);
		}
	}
	return initPoint;
}
//弹出窗口
function showInfo(point,title,address,map) {
                var content = /* "<img src='" + item.mainimage + "' style='width:111px;height:83px;float:left;margin-right:5px;'/>" + */
                              "<p>名称：" + title + "</p>" +
                              "<p>地址：" + address + "</p>";
                            /*   "<p>价格：" + item.dayprice + "</p>"; */
                //创建检索信息窗口对象
                var searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
                    title  : title,       //标题
                    panel  : "panel",         //检索结果面板
                    enableAutoPan : true,     //自动平移
                    searchTypes   :[
                        BMAPLIB_TAB_SEARCH,   //周边检索
                        BMAPLIB_TAB_TO_HERE,  //到这里去
                        BMAPLIB_TAB_FROM_HERE //从这里出发
                    ]
                });
                searchInfoWindow.open(point);
};
</script>
</html>