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

<script type="text/javascript">

	
</script>
        <table class="easyui-datagrid" fit="true"
            data-options="url:'http:www.baidu.com',fitColumns:true,singleSelect:true">
        <thead>
            <tr>
               <th data-options="field:'code'">Code</th>
               <th data-options="field:'code'">Code</th>
               <th data-options="field:'code'">Code</th>
               <th data-options="field:'code'">Code</th>
               <th data-options="field:'code'">Code</th>
               <th data-options="field:'name'">Name</th>
               <th data-options="field:'price'">Price</th>
            </tr>
        </thead>
    </table>
</html>