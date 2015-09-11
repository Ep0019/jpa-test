<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<title>社交地图</title>
<script type="text/javascript" src="http://api.map.baidu.com/api?ak=ZmDuOFoBhKljoNYxgMLMdBZF&v=2.0"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>
</head>
<body>
<div style="width:520px;height:340px;border:1px solid gray" id="container"></div>
<div style="width:200px;height:340px;border:1px solid gray" id="info"></div>
<p id="info"></p>
<input type="button" value="搜索" onclick="search()"/>
</body>
</html>
<script type="text/javascript">
function search(){
	var p2 = new BMap.Point(116.361581,39.961129);
	var icon2 = new BMap.Icon("${ctx}/upload/img2.jpg", new BMap.Size(50,50));
	var m2 = new BMap.Marker(p2,{icon:icon2});
	map.addOverlay(m2);
}
//创建地图
var map = new BMap.Map("container");
map.centerAndZoom(new BMap.Point(118.08,24.48), 13);

//添加一个矩形覆盖物
var polyline = new BMap.Polyline([
  new BMap.Point(116.279655,40.020499),
  new BMap.Point(116.260683,39.833259),
  new BMap.Point(116.532043,39.830599),
  new BMap.Point(116.526869,40.021383),
  new BMap.Point(116.279655,40.020499)
], {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
map.addOverlay(polyline);

//显示可视区域内的标注
function addMymarkers(){
	/* alert("地图放大级别:"+map.getZoom()); */
    for(i=0;i<markers.length;i++){
        var result = BMapLib.GeoUtils.isPointInRect(markers[i].point, map.getBounds());
        if(result == true) map.addOverlay(markers[i]);
        else map.removeOverlay(markers[i]);
    }
}

//对地图级别变化、移动结束和图块加载完毕后，进行添加marker的操作
map.addEventListener("tilesloaded", addMymarkers);
map.addEventListener("zoomend", addMymarkers);
map.addEventListener("moveend", addMymarkers);

//创建30个点
var p1 = new BMap.Point(118.08,24.48);
var p2 = new BMap.Point(116.361581,39.961129);
var p3 = new BMap.Point(116.437901,39.960133);
var p4 = new BMap.Point(116.459748,39.919528);
var p5 = new BMap.Point(116.424247,39.939557);
var p6 = new BMap.Point(116.485188,39.974511);
var p7 = new BMap.Point(116.485188,39.974511);
var p8 = new BMap.Point(116.494243,39.930484);
var p9 = new BMap.Point(116.45328,39.884103);
var p0 = new BMap.Point(116.456011,39.844671);

var p11 = new BMap.Point(116.387165,39.850654);
var p12 = new BMap.Point(116.461185,39.8975);
var p13 = new BMap.Point(116.380122,39.87458);
var p14 = new BMap.Point(116.354395,39.899825);
var p15 = new BMap.Point(116.394495,39.887093);
var p16 = new BMap.Point(116.30524,39.902482);
var p17 = new BMap.Point(116.287992,39.937676);
var p18 = new BMap.Point(116.277931,39.911116);
var p19 = new BMap.Point(116.340166,39.929267);
var p10 = new BMap.Point(116.290004,39.965885);

var p21 = new BMap.Point(116.377535,39.966548);
var p22 = new BMap.Point(116.423672,39.95239);
var p23 = new BMap.Point(116.423672,39.95239);
var p24 = new BMap.Point(116.300353,40.003146);
var p25 = new BMap.Point(116.294172,39.982251);
var p26 = new BMap.Point(116.313432,39.977497);
var p27 = new BMap.Point(116.390183,39.983357);
var p28 = new BMap.Point(116.390183,39.983357);
var p29 = new BMap.Point(116.484469,39.943872);
var p20 = new BMap.Point(116.509191,39.932586);

//创建30张图标
var icon1 = new BMap.Icon("${ctx}/upload/img1.jpg", new BMap.Size(50,50));
var icon2 = new BMap.Icon("${ctx}/upload/img2.jpg", new BMap.Size(50,50));
var icon3 = new BMap.Icon("${ctx}/upload/img3.jpg", new BMap.Size(50,50));
var icon4 = new BMap.Icon("${ctx}/upload/img4.jpg", new BMap.Size(50,50));
var icon5 = new BMap.Icon("${ctx}/upload/img5.jpg", new BMap.Size(50,50));
var icon6 = new BMap.Icon("${ctx}/upload/img6.jpg", new BMap.Size(50,50));
var icon7 = new BMap.Icon("${ctx}/upload/img7.jpg", new BMap.Size(50,50));
var icon8 = new BMap.Icon("${ctx}/upload/img2.jpg", new BMap.Size(50,50));
var icon9 = new BMap.Icon("${ctx}/upload/img1.jpg", new BMap.Size(50,50));
var icon0 = new BMap.Icon("${ctx}/upload/img0.jpg", new BMap.Size(50,50));

var icon11 = new BMap.Icon("${ctx}/upload/img/1.jpg", new BMap.Size(50,50));
var icon12 = new BMap.Icon("${ctx}/upload/img/2.jpg", new BMap.Size(50,50));
var icon13 = new BMap.Icon("${ctx}/upload/img/3.jpg", new BMap.Size(50,50));
var icon14 = new BMap.Icon("${ctx}/upload/img/4.jpg", new BMap.Size(50,50));
var icon15 = new BMap.Icon("${ctx}/upload/img/5.jpg", new BMap.Size(50,50));
var icon16 = new BMap.Icon("${ctx}/upload/img/6.jpg", new BMap.Size(50,50));
var icon17 = new BMap.Icon("${ctx}/upload/img/7.jpg", new BMap.Size(50,50));
var icon18 = new BMap.Icon("${ctx}/upload/img/1.jpg", new BMap.Size(50,50));
var icon19 = new BMap.Icon("${ctx}/upload/img/1.jpg", new BMap.Size(50,50));
var icon10 = new BMap.Icon("${ctx}/upload/img/0.jpg", new BMap.Size(50,50));

var icon21 = new BMap.Icon("${ctx}/upload/img/1.jpg", new BMap.Size(50,50));
var icon22 = new BMap.Icon("${ctx}/upload/img/2.jpg", new BMap.Size(50,50));
var icon23 = new BMap.Icon("${ctx}/upload/img/2.jpg", new BMap.Size(50,50));
var icon24 = new BMap.Icon("${ctx}/upload/img/4.jpg", new BMap.Size(50,50));
var icon25 = new BMap.Icon("${ctx}/upload/img/5.jpg", new BMap.Size(50,50));
var icon26 = new BMap.Icon("${ctx}/upload/img/6.jpg", new BMap.Size(50,50));
var icon27 = new BMap.Icon("${ctx}/upload/img/2.jpg", new BMap.Size(50,50));
var icon28 = new BMap.Icon("${ctx}/upload/img/2.jpg", new BMap.Size(50,50));
var icon29 = new BMap.Icon("${ctx}/upload/img/2.jpg", new BMap.Size(50,50));
var icon20 = new BMap.Icon("${ctx}/upload/img/2.jpg", new BMap.Size(50,50));

//创建30个marker，但不添加到地图上
var m1 = new BMap.Marker(p1,{icon:icon1}); 
var m2 = new BMap.Marker(p2,{icon:icon2}); 
var m3 = new BMap.Marker(p3,{icon:icon3}); 
var m4 = new BMap.Marker(p4,{icon:icon4}); 
var m5 = new BMap.Marker(p5,{icon:icon5}); 
var m6 = new BMap.Marker(p6,{icon:icon6}); 
var m7 = new BMap.Marker(p7,{icon:icon7}); 
var m8 = new BMap.Marker(p8,{icon:icon8}); 
var m9 = new BMap.Marker(p9,{icon:icon9}); 
var m0 = new BMap.Marker(p0,{icon:icon0}); 

var m11 = new BMap.Marker(p11,{icon:icon11}); 
var m12 = new BMap.Marker(p12,{icon:icon12}); 
var m13 = new BMap.Marker(p13,{icon:icon13}); 
var m14 = new BMap.Marker(p14,{icon:icon14}); 
var m15 = new BMap.Marker(p15,{icon:icon15}); 
var m16 = new BMap.Marker(p16,{icon:icon16}); 
var m17 = new BMap.Marker(p17,{icon:icon17}); 
var m18 = new BMap.Marker(p18,{icon:icon18}); 
var m19 = new BMap.Marker(p19,{icon:icon19}); 
var m10 = new BMap.Marker(p10,{icon:icon10}); 

var m21 = new BMap.Marker(p21,{icon:icon21}); 
var m22 = new BMap.Marker(p22,{icon:icon22}); 
var m23 = new BMap.Marker(p23,{icon:icon23}); 
var m24 = new BMap.Marker(p24,{icon:icon24}); 
var m25 = new BMap.Marker(p25,{icon:icon25}); 
var m26 = new BMap.Marker(p26,{icon:icon26}); 
var m27 = new BMap.Marker(p27,{icon:icon27}); 
var m28 = new BMap.Marker(p28,{icon:icon28}); 
var m29 = new BMap.Marker(p29,{icon:icon29}); 
var m20 = new BMap.Marker(p20,{icon:icon20}); 



//建立一个marker的数组
var markers = [m1,m2,m3,m4,m5,m6,m7,m8,m9,m0,m11,m12,m13,m14,m15,m16,m17,m18,m19,m10,m21,m22,m23,m24,m25,m26,m27,m28,m29,m20];
</script>