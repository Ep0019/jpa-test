<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 引入easyui  -->
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/default/easyui.css">   
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/icon.css"> 
<script type="text/javascript" src="${ctx}/javascript/jquery-1.7.2.min.js"></script>  
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<title>后台</title>
</head>
  <style>
        .nav-item
        {
            text-align: center;
        }
        .nav-item a
        {
            color: #000;
            text-decoration: none;
        }
        /*菜单js打开tab出现的滚动条隐藏*/
        .panel-body-noborder
        {
            overflow: hidden;
        }
    </style>

<script type="text/javascript">
/* 添加标签页 */
function addTab(title, url){
	if ($('#my_tab').tabs('exists', title)){
		$('#my_tab').tabs('select', title);
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		$('#my_tab').tabs('add',{
			title:title,
			content:content,
			closable:true
		});
	}
}
</script>
<body class="easyui-layout">   
    <div data-options="region:'north',split:true,collapsible:false" style="height:100px;"></div>   
    
    <div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>   
   
    <div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true" style="width:100px;"></div>   
   
    <div data-options="region:'west',title:'导航菜单',split:true,href:'${ctx }/main/left'" style="width:150px;">
    
    </div>   
    
    <div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
    
    <%--tab start --%>
      <div id="my_tab"  class="easyui-tabs" fit="true">
			<div title="主页">
				<div id="p1" class="easyui-panel" title="通知"     
        			style="width:500px;height:150px;padding:10px;background:#fafafa;"   
       			 	data-options="iconCls:'icon-save',closable:true,    
                collapsible:true,minimizable:true,maximizable:true">   
		    	<p>panel content.</p>   
		    	<p>panel content.</p>   
				</div>
				<div id="p2" class="easyui-panel" title="通知"     
        			style="width:500px;height:150px;padding:10px;background:#fafafa;"   
       			 	data-options="iconCls:'icon-save',closable:true,    
                collapsible:true,minimizable:true,maximizable:true">   
		    	<p>panel content.</p>   
		    	<p>panel content.</p>   
				</div>  
				<div id="p3" class="easyui-panel" title="通知"     
        			style="width:500px;height:150px;padding:10px;background:#fafafa;"   
       			 	data-options="iconCls:'icon-save',closable:true,    
                collapsible:true,minimizable:true,maximizable:true">   
		    	<p>panel content.</p>   
		    	<p>panel content.</p>   
				</div>  
			</div>
	  </div>
    <%--tab end --%>
    </div>   
</body>
</html>