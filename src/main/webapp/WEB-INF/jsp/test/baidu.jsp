<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta name="keywords" content="百度地图,百度地图API，百度地图自定义工具，百度地图所见即所得工具" />
<meta name="description" content="百度地图API自定义地图，帮助用户在可视化操作下生成百度地图" />
<title>百度地图API自定义地图</title>
<!--引用百度地图API-->
<style type="text/css">
    html,body{margin:0;padding:0;}
    .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
    .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?ak=ZmDuOFoBhKljoNYxgMLMdBZF&v=2.0"></script>
<body>
  <!--百度地图容器-->
  <div style="width:697px;height:550px;border:#ccc solid 1px;" id="dituContent"></div>
</body>
<script type="text/javascript">
    //创建和初始化地图函数：
    function initMap(){
        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
        
    }
    var opts = {
    		width : 250, // 信息窗口宽度
    		height: 100, // 信息窗口高度
    		title : "Hello" // 信息窗口标题
    		}
    //创建地图函数：
    function createMap(){
        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var point = new BMap.Point(118.08,24.48);//定义一个中心点坐标
        map.centerAndZoom(point,13);//设定地图的中心点和坐标并将地图显示在地图容器中
        
        
        var opts = {
        		  position : point   // 指定文本标注所在的地理位置
        		   //设置文本偏移量
        		}
        		var label = new BMap.Label("欢迎使用百度地图，这是一个简单的文本标注哦~", opts);  // 创建文本标注对象
        			label.setStyle({
        				 color : "red",
        				 fontSize : "12px",
        				 height : "20px",
        				 lineHeight : "20px",
        				 fontFamily:"微软雅黑"
        			 });
        		map.addOverlay(label);
        
        var circle = new BMap.Circle(point,500,{strokeColor:"blue",fillColor:"green",strokeWeight:2, strokeOpacity:0.5}); //创建圆
        map.addOverlay(circle);
        //思明区
        var point1 = new BMap.Point(118.088838,24.452137);
        var marker1 = new BMap.Marker(point1); // 创建标注（小红点）
        map.addOverlay(marker1);
        //湖里区
        var point2 = new BMap.Point(118.15236,24.517466);
        var marker2 = new BMap.Marker(point2); // 创建标注（小红点）
        map.addOverlay(marker2);
        marker2.enableDragging();
        marker2.addEventListener("dragend", function(e){
        alert("当前位置： " + e.point.lng + ", " + e.point.lat);
        })
        
        //海沧区
        var point3 = new BMap.Point(118.040499,24.489787);
        var marker3 = new BMap.Marker(point3); // 创建标注（小红点）
        map.addOverlay(marker3);
        
        marker3.addEventListener("mouseover", function(){
        	 var infoWindow = new BMap.InfoWindow("World", opts); // 创建信息窗口对象
     		map.openInfoWindow(infoWindow, point3); // 打开信息窗口
       	});
        
        
        var customLayer=new BMap.CustomLayer(117765);
        map.addTileLayer(customLayer);
        customLayer.addEventListener('onhotspotclick',callback);
        
        window.map = map;//将map变量存储在全局
    }
    function callback(e){
    	alert('ok');
    	var customPoi = e.customPoi;
    	alert(customPoi);
    }
    //地图事件设置函数：
    function setMapEvent(){
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }
    
    //地图控件添加函数：
    function addMapControl(){
        //向地图中添加缩放控件
	var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
	map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
	map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
	map.addControl(ctrl_sca);
	
	// 创建控件实例
	var myZoomCtrl = new ZoomControl();
	// 添加到地图当中
	map.addControl(myZoomCtrl);
    }
 	// 定义一个控件类，即 function
    function ZoomControl(){
	    // 设置默认停靠位置和偏移量
	    this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	    this.defaultOffset = new BMap.Size(10, 10);
    }
    // 通过 JavaScript 的 prototype 属性继承于 BMap.Control
    ZoomControl.prototype = new BMap.Control();
    
	 // 自定义控件必须实现 initialize 方法，并且将控件的 DOM 元素返回
	 // 在本方法中创建个 div 元素作为控件的容器，并将其添加到地图容器中
	 ZoomControl.prototype.initialize = function(map){
		 // 创建一个 DOM 元素
		 var div = document.createElement("div");
		 // 添加文字说明
		 div.appendChild(document.createTextNode("放大2级"));
		 // 设置样式
		 div.style.cursor = "pointer";
		 div.style.border = "1px solid gray";
		 div.style.backgroundColor = "white";
		 // 绑定事件，点击一次放大两级
		 div.onclick = function(e){
		 map.zoomTo(map.getZoom() + 2);
		 }
		 // 添加 DOM 元素到地图中
		 map.getContainer().appendChild(div);
		 // 将 DOM 元素返回
		 return div;
	 }
	 
	 
	 
	 
	 initMap();//创建和初始化地图
    
</script>
</html>