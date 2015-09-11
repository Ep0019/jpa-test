<%@page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  <%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div class="easyui-accordion" fit="true">   
      
    <div title="Title2" data-options="iconCls:'icon-save',selected:true" style="overflow:auto;padding:5px;">   
     	  <div class="nav-item">
     	  	<a href="javascript:void(0);" onclick="addTab('easyui','http://jeasyui.com/')">添加标签页</a>
     	  </div>
     	  <div class="nav-item">
     	  	<a href="javascript:void(0);" onclick="addTab('datagrid','${ctx}/main/datagrid')">添加标签页</a>
     	  </div>
     	  <div class="nav-item">
     	  	<a href="javascript:void(0);" onclick="addTab('easyui','http://jeasyui.com/')">添加标签页</a>
     	  </div>
     	  <div class="nav-item">
     	  	<a href="javascript:void(0);" onclick="addTab('easyui','http://jeasyui.com/')">添加标签页</a>
     	  </div>   
    </div>   
    <div title="Title3" data-options="iconCls:'icon-save'" style="overflow:auto;padding:5px;">   
        	测试    
    </div> 
    
    <div title="树形节点" data-options="iconCls:'icon-save'" style="overflow:auto;padding:5px;">   
       
       		<ul class="easyui-tree">
       			<li state="closed">
       				<span>树形节点1</span>
       				<ul>
       					<li><span>sub-folder1</span></li>
       					<li><span>sub-folder2</span></li>
       				</ul>
       			</li>
       			<li state="closed">
       				<span>树形节点2</span>
       				<ul>
       					<li><span>folder2</span></li>
       				</ul>
       			</li>
       		</ul>
    </div>   
    
</div>  
 