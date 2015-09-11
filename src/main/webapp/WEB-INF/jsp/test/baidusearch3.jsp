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
	<title>百度地图找房</title>
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
var level=0;
var searchContent="";
var isSearch=false;
var search=0;
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
           {"title":"特房五缘湾尊府","avgprice":3.2,"address":"福建省厦门市湖里区金山路","location":"118.170984|24.524857","pid":3,"id":1,"level":2},
           {"title":"国贸天琴湾","avgprice":3.2,"address":"福建省厦门市湖里区五缘湾道","location":"118.176445|24.525087","pid":3,"id":2,"level":2},
           {"title":"紫金家园","avgprice":3.2,"address":"福建省厦门市湖里区金山路","location":"118.171092,24.526862","pid":3,"id":3,"level":2}
];
//拖拽数据level=1
var datat1=[
     {"title":"百乐园","avgprice":3.2,"address":"福建省厦门市湖里区华昌路10号","location":"118.104222|24.511476","pid":2,"id":1,"level":1},
     {"title":"东渡","avgprice":3.2,"address":"福建省厦门市湖里区东渡路144号","location":"118.089849|24.49885","pid":2,"id":2,"level":1},
     {"title":"中山路","avgprice":3.2,"address":"福建省厦门市思明区中山路173号","location":"118.087118|24.460438","pid":2,"id":3,"level":1}
];
//拖拽数据level=2
var datat2=[
            {"title":"裕发广场","avgprice":3.2,"address":"福建省厦门市思明区火车站莲坂商圈厦禾路1019号","location":"118.125476|24.480624","pid":2,"id":1,"level":2},
            {"title":"昌源商业中心","avgprice":3.2,"address":"福建省厦门市思明区厦禾路999-7号","location":"118.125624|24.479695","pid":2,"id":2,"level":2},
            {"title":"京华大厦","avgprice":3.2,"address":"福建省厦门市思明区厦禾路1130号-之5","location":"118.127515|24.479638","pid":2,"id":3,"level":2}
];
//--------------------------------------以上为测试数据-----------------------------------------------------------------------------------------------------------------

var map = new BMap.Map("l-map");        
map.centerAndZoom(new BMap.Point(118.08,24.48),11);
//设置地图最小级别（这里只定位厦门这块区域）
map.setMinZoom(11);
map.enableScrollWheelZoom();
map.addEventListener("click", getjw);
map.addEventListener("zoomend", zomm);
map.addEventListener("dragend", dragend);
//地图级别缩放监听
function zomm(){
	var zoom=map.getZoom();
	myZoom=zoom;
	if(myZoom > 11 && myZoom <= 14){
		level=1;
	}else if(myZoom > 15){
		level=2;
	}else{
		level=0;
	}
	
	//-------获取东北和西南的经纬度信息------开始
	//搜索条件
	//minX0<=x<=maxX1 and minY0<=y<=maxY1
	var bounds=map.getBounds();
	var sw=bounds.getSouthWest();
	var ne=bounds.getNorthEast();
	var minX0=sw.lat;
	var maxX1=ne.lat;
	var minY0=sw.lng;
	var maxY1=ne.lng;
	//-------获取东北和西南的经纬度信息------结束
	<%--isSearch 为true时，表示搜索入口--%>
	if(isSearch){
		if(search == 0){
			//这里整合搜索（调整最佳视野）
			search++;
			return;
		}else{
			//根据content和经纬度搜索数据
			map.clearOverlays();
			alert("我要加载搜索的数据了");
			/* initData(map,dataq,level); */
		}
	}else{
		map.clearOverlays();
		/*  a. 具体业务只需要这样处理     initData(map,dataq,level); */
		/*  b. 一下是静态数据，为了显示效果只能这样处理了 */
		if(zoom <=11){
			//这里加载一级地区数据dataq需要自己替换level 0
			initData(map,dataq,level);
		}else if(zoom> 11 && zoom <= 14){
			//这里加载二级地区数据datax需要自己替换level 1
			initData(map,datax,level);
		}else if(zoom > 15){
			//这里加载三级地区数据dataq需要自己替换level 2
			initData(map,dataxq,level);
		} 
		/*  真是环境中，用a代码替换b段代码即可，避免冗余的代码判断 */
	}
	//加载数据列表
	alert("最后加载列表数据");
}

function getjw(e){
	
}
//拖拽功能实现
function dragend(){
	var bounds=map.getBounds();
	var sw=bounds.getSouthWest();
	var ne=bounds.getNorthEast();
	var minX0=sw.lat;
	var maxX1=ne.lat;
	var minY0=sw.lng;
	var maxY1=ne.lng;
	//这里如果是先搜索后在拖动的话（查询数据的时候需要加content和经纬度去查询）
	if(myZoom > 11 && myZoom <= 14){
		//搜索二级地区数据 level=1
		map.clearOverlays();
		initData(map,datat1,level);
	}else if(myZoom > 15){
		//搜索三级地区数据 level=2
		map.clearOverlays();
		initData(map,datat2,level);
	}
	//最后一步再加载右边列表数据
	
}
//复杂自定义覆盖物start-----------------------自定义样式在此处自定义，修改div样式----------------------------------
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
  span.appendChild(document.createTextNode("&nbsp;&nbsp;&nbsp;"));      
  span.appendChild(document.createTextNode(this._text));   
  span.appendChild(document.createTextNode("<br>&nbsp;&nbsp;"));   
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
  var level=this._level;
  //处理点击事件（出发点击事件只需要负责地图的缩放功能，设置中心点即可，zomm方法会自动负责数据加载）
  div.onclick = function(){
	  if(map.getZoom() != 19){
		  map.clearOverlays();
		  if(level == 1){
		  	  map.centerAndZoom(myPoint,map.getZoom()+5);
		  }else{
			  map.centerAndZoom(myPoint,map.getZoom()+3);
		  }
	  }else{
		  alert('列表数据');
		  //此处加载列表数据 level=2
	  }
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
function ComplexCustomOverlay(point,text,mouseoverText,level){
  this._point = point;
  this._text = text;
  this._overText = mouseoverText;
  this._level = level;
}
//复杂的自定义覆盖物end 0表示一级地区不需要过滤
map.setViewport(initData(map,dataq,0));
//初始化数据六个大区
function initData(map,data,funlevel){
	var initPoint=[];
	for(var i=0;i<data.length;i++){
		var point = new BMap.Point(data[i].location.split("|")[0],data[i].location.split("|")[1]);
		var myCompOverlay = new ComplexCustomOverlay(point,data[i].title,data[i].title+" "+data[i].avgprice,level);
		var result = BMapLib.GeoUtils.isPointInRect(point, map.getBounds());
		if(result && funlevel == data[i].level){
			initPoint.push(point);
			map.addOverlay(myCompOverlay);
		}else{
			map.removeOverlay(myCompOverlay);
		}
	}
	return initPoint;
}
<%--搜索开始--%>
function searchApi(content,page){
	isSearch=true;
	search=0;
	var keyword=document.getElementById("searchContent").value;
	<%--此处替换自己的搜索接口  开始--%>
	<%--搜索结果根据level来降序排序，第一条数据放在视野中心位置--%>
	<%--第一条数据的level为0，级别设置为11,level为1设置为14，level为3设置为19--%>
	var contents=[
	            {"title":"百乐园","avgprice":3.2,"address":"福建省厦门市湖里区华昌路10号","location":"118.104222|24.511476","pid":2,"id":1,"level":1},
	            {"title":"思明区","avgprice":3.2,"address":"福建省厦门市思明区","location":"118.08919|24.451606","pid":0,"id":1,"level":0},
	            {"title":"京华大厦","avgprice":3.2,"address":"福建省厦门市思明区厦禾路1130号-之5","location":"118.127515|24.479638","pid":2,"id":3,"level":2}
	];
    //渲染地图
    var points = [];
    map.clearOverlays();
    var centerlevel=11;
    level=contents[1].level;
    if(level == 1){
    	centerlevel=14;
    }
    if(level == 2){
    	centerlevel=19;
    }
    //调整到最佳视野11,14,19
    map.centerAndZoom(new BMap.Point(contents[0].location.split("|")[0],contents[0].location.split("|")[1]),centerlevel);
    initData(map,contents,contents[1].level);
}
</script>
</html>