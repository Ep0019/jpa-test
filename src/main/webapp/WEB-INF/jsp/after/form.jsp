<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/javascript/jquery-1.7.2.min.js"></script> 
<script type="text/javascript" src="${ctx}/javascript/jquery.form.js"></script> 
<title>表单重复提交</title>
<script type="text/javascript">
function submitForm(){
	 $('#role-form').ajaxSubmit({
			dataType:'json',
			success:function(data){
				if(data.code == 4){
					isCommitted=true;
				}
			}
		});
}
var isCommitted = false;//表单是否已经提交标识，默认为false
function dosubmit(){
    if(isCommitted==false){
        isCommitted = true;//提交表单后，将表单是否已经提交标识设置为true
        return true;//返回true让表单正常提交
    }else{
        return false;//返回false那么表单将不提交
    }
}

</script>
<body>   
    <form id="role-form" action="${ctx }/role/repeat-form"  method="post">
    <input type="text" name="token" value="${token}">
    	角色名称<input type="text" name="name" value=""/>
    	描述<input type="text" name="descript">
    	<input type="button" onclick="submitForm()" value="提交"/>
    </form>
</body>
</html>