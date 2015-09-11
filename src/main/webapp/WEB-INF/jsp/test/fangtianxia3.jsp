<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%; margin:0;font-family:"微软雅黑";}
		#l-map{height:500px;width:1000px;}
		#r-result{width:100%; font-size: 14px; line-height: 20px;}
	</style>
	<script type="text/javascript" src="${ctx}/javascript/jquery-1.8.0.min.js"></script>
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
	<input type="button" value="搜索" onclick="clickSearch()"/>
	
</body>
</html>
<script type="text/javascript">
<%--搜索条件--%>
var search_options={
		pid:0,
		level:1,
		isSearch:false,
		searchCount:0,
		keywork:"",//小区名称
		minlat:"",
		maxlat:"",
		minlng:"",
		maxlng:"",
		page:1
};
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
//拖动数据获取
var datatt=[
           {"title":"塘边","avgprice":3.2,"address":"福建省厦门市湖里区兴隆路453","location":"118.125116|24.514106","pid":2,"id":3,"level":1},
           {"title":"江头","avgprice":3.2,"address":"福建省厦门市湖里区台湾街161号-之9","location":"118.138483|24.501744","pid":2,"id":4,"level":1},
           {"title":"莲坂","avgprice":3.2,"address":"福建省厦门市思明区莲岳路1号","location":"118.127649|24.487933","pid":2,"id":5,"level":1},
           {"title":"黄厝","avgprice":3.2,"address":"福建省厦门市思明区黄厝路","location":"118.166313|24.45307","pid":2,"id":6,"level":1}
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
<%--地图级别缩放监听   数据统一放在这个方法里面处理--%>
function zomm(){
	var zoom=map.getZoom();
	zoomUpdateOptions();
	if(search_options.isSearch){
		if(search_options.searchCount == 0){
			updateJson("searchCount",1);
			//return;
		}else{
			//根据content和经纬度搜索数据
			var contents=[
	            {"title":"百乐园","avgprice":3.2,"address":"福建省厦门市湖里区华昌路10号","location":"118.104222|24.511476","pid":2,"id":1,"level":1},
	            {"title":"东渡","avgprice":3.2,"address":"福建省厦门市湖里区东渡路144号","location":"118.089849|24.49885","pid":2,"id":2,"level":1},
	            {"title":"中山路","avgprice":3.2,"address":"福建省厦门市思明区中山路173号","location":"118.087118|24.460438","pid":2,"id":3,"level":1}
			];
			map.clearOverlays();
			addMark(map,contents);
		}
	}else{
		if(zoom <= 11){
			map.clearOverlays();
			initData(map,dataq);
			//渲染列表数据-----例-------
		}else{
			//datax为某个区内的数据或者是所有可视二级数据和分页的房屋
			//（pid=0加载可视区域所有数据(混合经纬度搜索)
			// pid!=0加载pid为pid的数据）
			//if(!search_options.isSearch){
			//	map.clearOverlays();
			//	addMark(map,datax);
			//}else{
				//已search_options和经纬度为搜索条件搜索
				//var contents=[
		         //   {"title":"百乐园","avgprice":3.2,"address":"福建省厦门市湖里区华昌路10号","location":"118.104222|24.511476","pid":2,"id":1,"level":1},
		          //  {"title":"东渡","avgprice":3.2,"address":"福建省厦门市湖里区东渡路144号","location":"118.089849|24.49885","pid":2,"id":2,"level":1},
		          //  {"title":"中山路","avgprice":3.2,"address":"福建省厦门市思明区中山路173号","location":"118.087118|24.460438","pid":2,"id":3,"level":1}
				//];
				map.clearOverlays();
				addMark(map,datax);
			//}
			//渲染列表数据-----例-------
		}
	}
}
<%--地图级别缩放监听   数据统一放在这个方法里面处理--%>
function getjw(e){
	
}
<%--拖拽功能实现   start--%>
function dragend(){
	zoomUpdateOptions();
	//这里如果是先搜索后再拖动的话search_options（查询数据的时候需要加content和经纬度去查询）
	if(map.getZoom() <= 11){
		map.clearOverlays();
		initData(map,dataq);
	}else{
		map.clearOverlays();
		addMark(map,datatt);
	}
	//最后一步再加载右边列表数据
} 
<%--拖拽功能实现   end--%>
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
  var dataId=this._dataId;
  var dataPid=this._dataPid;
  var mylevel=this._level;
  //处理点击事件
  div.onclick = function(){
	  pid=dataId;
	  updateJson("pid",dataId);
	  map.centerAndZoom(myPoint,14);
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
<%--自定义覆盖物构造方法--%>
function ComplexCustomOverlay(point,dataId,dataPid,level,text,mouseoverText){
  this._dataId=dataId;
  this._dataPid=dataPid;
  this._level=level;
  this._point = point;
  this._text = text;
  this._overText = mouseoverText;
}
//复杂的自定义覆盖物end
<%--初始化大块区域数据，同时设置最佳视野，dataq需要自己获取替换（为大块区域的数据），格式如上--%>
map.setViewport(initData(map,dataq));
/*初始化大块区域数据
 * map:地图示例
 * dataq:json数组
 */
function initData(map,data){
	var initPoint=[];
	for(var i=0;i<data.length;i++){
		var point = new BMap.Point(data[i].location.split("|")[0],data[i].location.split("|")[1]);
		var myCompOverlay = new ComplexCustomOverlay(point,data[i].id,data[i].pid,data[i].level,data[i].title,data[i].title+" "+data[i].avgprice);
		var result = BMapLib.GeoUtils.isPointInRect(point, map.getBounds());
		if(result){
			initPoint.push(point);
			map.addOverlay(myCompOverlay);
		}else{
			map.removeOverlay(myCompOverlay);
		}
	}
	return initPoint;
}
<%--添加普通覆盖物--%>
function addMark(map,data){
		var points=[];
		$.each(data, function(i, item){
	    	var point=new BMap.Point(item.location.split("|")[0],item.location.split("|")[1]);
	    	var icondefault = new BMap.Icon("${ctx}/upload/icon3.png", new BMap.Size('50','50'));
	    	var iconover = new BMap.Icon("${ctx}/upload/icon2.png", new BMap.Size('50','50'));
	    	var mark = new BMap.Marker(point,{icon:icondefault});
	        //var result = BMapLib.GeoUtils.isPointInRect(point, map.getBounds());
	        //if(result){
	        	function showInfo() {
	        		<%--窗口样式--%>
	        	    var content = "<p>名称：" + item.title + "</p>" +
	        	                  "<p>地址：" + item.address + "</p>";
	        	    <%--窗口样式--%>
	        	    //创建检索信息窗口对象
	        	    var searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
	        	        title  : item.title,       //标题
	        	        panel  : "panel",         //检索结果面板
	        	        enableAutoPan : false,     //自动平移
	        	        searchTypes   :[
	        	            BMAPLIB_TAB_SEARCH,   //周边检索
	        	            BMAPLIB_TAB_TO_HERE,  //到这里去
	        	            BMAPLIB_TAB_FROM_HERE //从这里出发
	        	        ]
	        	    });
	        	    mark.setIcon(iconover);
	        	    searchInfoWindow.open(mark);
	        	    function closeInfo(){
	        	    	mark.setIcon(icondefault);
	        	    	searchInfoWindow.close();
	        	    }
	        	    mark.addEventListener('mouseout', closeInfo);
	        	};
	        	function getData(){
	        		<%--这里刷新列表数据--%>
	        		alert("开始加载pid为："+item.id+"的数据了！");
	        	}
	        	<%--添加监听开始--%>
	        	mark.addEventListener('mouseover', showInfo);
	        	mark.addEventListener('click', getData);
	        	<%--添加监听结束--%>
	        	points.push(point);
	        	map.addOverlay(mark);
	        //}else{
	        //	map.removeOverlay(mark);
	        //}
		});
	return points;    
}
<%--添加普通覆盖物结束--%>
function clickSearch(){
	//小区名称
	 var keyword=document.getElementById("searchContent").value;
	//更新搜索条件
	updateJson("keyword",keyword);
	updateJson("isSearch",true);
	var data=searchApi();
	map.clearOverlays();
	map.setViewport(addMark(map,data)); 
}


<%--搜索开始--%>
function searchApi(){
	<%--此处替换自己的搜索接口 搜索小区--%>
	<%--搜索条件为：search_options  搜索结果：  小区+房屋的json格式--%>
	var contents=[
	            {"title":"百乐园","avgprice":3.2,"address":"福建省厦门市湖里区华昌路10号","location":"118.104222|24.511476","pid":2,"id":1,"level":1},
	            {"title":"东渡","avgprice":3.2,"address":"福建省厦门市湖里区东渡路144号","location":"118.089849|24.49885","pid":2,"id":2,"level":1},
	            {"title":"中山路","avgprice":3.2,"address":"福建省厦门市思明区中山路173号","location":"118.087118|24.460438","pid":2,"id":3,"level":1}
	];
	return contents;
    //调整到最佳视野11,14,19
    //map.centerAndZoom(new BMap.Point(contents[0].location.split("|")[0],contents[0].location.split("|")[1]),centerlevel);
    /* 数据统一在zomm事件中处理 */
    //initData(map,contents,contents[1].level);
}
/* 每次地图级别发生变化时，首先修改可视区域 */
function zoomUpdateOptions(){
	var bounds=map.getBounds();
	var sw=bounds.getSouthWest();
	var ne=bounds.getNorthEast();
	var minX0=sw.lat;
	var maxX1=ne.lat;
	var minY0=sw.lng;
	var maxY1=ne.lng;
	updateJson("minlat",sw.lat);
	updateJson("maxlat",ne.lat);
	updateJson("minlng",sw.lng);
	updateJson("maxlng",ne.lng);
}
/* 更新search_options数据*/
function updateJson(prop, val) { 
    if(typeof val === "undefined") { 
        delete search_options[prop]; 
    }else{ 
    	search_options[prop] = val; 
    } 
}
/* 重置search_options数据*/
function resetJson() { 
	search_options={
			pid:0,
			level:1,
			keyword:"",
			isSearch:false,
			searchCount:0,
			minlat:"",
			maxlat:"",
			minlng:"",
			maxlng:"",
			page:1
	};
	return search_options;
}
</script>
</html>