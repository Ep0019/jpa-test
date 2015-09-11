<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>easyui</title>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/default/easyui.css">   
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/icon.css"> 
<script type="text/javascript" src="${ctx}/javascript/jquery-1.7.2.min.js"></script>  
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>  
</head>
<script type="text/javascript">
$(function(){
	$('#mm').menu('show', {    
		  left: 200,    
		  top: 100    
		}); 
})
</script>
<body>



<div id="mm" class="easyui-menu" style="width:120px;">   
    <div>New</div>   
    <div>   
        <span>Open</span>   
        <div style="width:150px;">   
            <div><b>Word</b></div>   
            <div>Excel</div>   
            <div>PowerPoint</div>   
        </div>   
    </div>   
    <div data-options="iconCls:'icon-save'">Save</div>   
    <div class="menu-sep"></div>   
    <div>Exit</div>   
</div> 
</body>
</html>