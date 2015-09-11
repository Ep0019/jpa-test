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
function bank(content){
 var local = new BMap.LocalSearch(map, {   
	renderOptions:{map: map}   
 });   
  local.searchInBounds(content, map.getBounds()); 
}
// 百度地图API功能
var map = new BMap.Map("l-map");        
map.centerAndZoom(new BMap.Point(118.08,24.48), 13);
customLayer=new BMap.CustomLayer({  
    geotableId: 117701,   
    q:'', //检索关键字  
    tags:'', //空格分隔的多字符串  
    filter:'' //过滤条件,参考http://developer.baidu.com/map/lbs-geosearch.htm#.search.nearby  
});
map.addTileLayer(customLayer);
customLayer.addEventListener('onhotspotclick',callback);

var point22 = new BMap.Point(118.148436,24.481619);
var marker22 = new BMap.Marker(point22);
map.addOverlay(marker22);
/* var tr = $("<tr><td width='75%'><a href='" + item.roomurl + "' target='_blank' onclick='Util.addLogCount()'>" + item.title + "<a/><br/>" + item.address + "</td><td width='25%'>" + item.dayprice + "<br/><span style='color:red;'>元/晚</span></td></tr>").click(showInfo);
$('#mapList').append(tr);; */
marker22.addEventListener('click',dfdf);
function dfdf(){
	alert(1111111);
}
//麻点图
var options = {
	renderOptions: {
		map: map,
		selectFirstResult: true,
		autoViewport: true,
	},
	pageCapacity:5,
	onSearchComplete: function(results) {
		alert(results.getNumPois());
		alert('Search Completed');
		//可添加自定义回调函数
	}
};
function callback(e){
//获取poi数据
var customPoi = e.customPoi;
alert("点击了麻点，可获取poi数据");
}
<%--搜索--%>
function search(){
	var content=document.getElementById("searchContent").value;
	var localSearch = new BMap.LocalSearch(map, options);
	localSearch.searchNearby(content, new BMap.Point(118.08,24.48), 500000, {
				customData: {
					geotableId: 117701,
					tags:"",
					filter:""/*http://developer.baidu.com/map/index.php?title=lbscloud/api/geosearch*/
				}
	});
}
/* var customLayer;
function searchM(){
		map.removeTileLayer(customLayer);
	var content=document.getElementById("searchContent").value;
	customLayer=new BMap.CustomLayer({  
	    geotableId: 117701,   
	    q: content, //检索关键字  
	    tags: '', //空格分隔的多字符串  
	    filter: '' //过滤条件,参考http://developer.baidu.com/map/lbs-geosearch.htm#.search.nearby  
	});
	map.addTileLayer(customLayer);
customLayer.addEventListener('onhotspotclick',callback); 
} */


function searchApi(keyword,page){
	
	
	 /* map.clearOverlays();
     map.removeTileLayer(customLayer);
	// 创建地址解析器实例     
	var myGeo = new BMap.Geocoder();      
	// 将地址解析结果显示在地图上，并调整地图视野    
	myGeo.getPoint("乐旺家", function(point){      
          if (point) {
        	  alert(1);
              map.centerAndZoom(point, 16);      
              map.addOverlay(new BMap.Marker(point));      
          }      
      }, "厦门市"); */
	
	var keyword=document.getElementById("searchContent").value;
	var url = "http://api.map.baidu.com/geosearch/v2/local?callback=?";
    $.getJSON(url, {
        'q': keyword, //检索关键字
         'page_index' : page,   //页码
        /* 'filter'     : filter.join('|'),  */ //过滤条件
        'region'     : '厦门市',//限定范围
        'scope'      : '2',  //显示详细信息
        'geotable_id': 117701,
        'ak'         : 'I6aZeIGITxzBn5VXbgM41ORG'  //用户ak
    },function(e) {
        alert(e.contents);
        //渲染地图
        var points = [];
        map.clearOverlays();
        map.removeTileLayer(customLayer);
        $.each(e.contents, function(i, item){
            var point = new BMap.Point(item.location[0], item.location[1]),
                marker = new BMap.Marker(point);
            points.push(point);
            /* var tr = $("<tr><td width='75%'><a href='" + item.roomurl + "' target='_blank' onclick='Util.addLogCount()'>" + item.title + "<a/><br/>" + item.address + "</td><td width='25%'>" + item.dayprice + "<br/><span style='color:red;'>元/晚</span></td></tr>").click(showInfo);
            $('#mapList').append(tr);; */
            marker.addEventListener('click', showInfo);
            function showInfo() {
                var content = /* "<img src='" + item.mainimage + "' style='width:111px;height:83px;float:left;margin-right:5px;'/>" + */
                              "<p>名称：" + item.title + "</p>" +
                              "<p>地址：" + item.address + "</p>";
                            /*   "<p>价格：" + item.dayprice + "</p>"; */
                //创建检索信息窗口对象
                var searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
                    title  : item.title,       //标题
                    panel  : "panel",         //检索结果面板
                    enableAutoPan : true,     //自动平移
                    searchTypes   :[
                        BMAPLIB_TAB_SEARCH,   //周边检索
                        BMAPLIB_TAB_TO_HERE,  //到这里去
                        BMAPLIB_TAB_FROM_HERE //从这里出发
                    ]
                });
                searchInfoWindow.open(marker);
            };
            map.addOverlay(marker);
        });
       //调整到最佳视野
        map.setViewport(points);
        /**
         * 分页
         */
        /* var pagecount = Math.ceil(e.total / 10);
        alert(pagecount);
        if (pagecount > 76) {
            pagecount = 76; //最大页数76页
        }
        function PageClick (pageclickednumber) {
            pageclickednumber = parseInt(pageclickednumber);
            $("#pager").pager({ pagenumber: pageclickednumber, pagecount: pagecount, showcount: 3, buttonClickCallback: PageClick });
            searchApi(keyword, pageclickednumber -1);
        }
        $("#mapPager").pager({ pagenumber: page, pagecount: pagecount, showcount:3, buttonClickCallback: PageClick }); */
    });
}
</script>