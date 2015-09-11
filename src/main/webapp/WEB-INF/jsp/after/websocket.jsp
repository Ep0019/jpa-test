<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="${ctx}/javascript/swfobject.js"></script>
<script type="text/javascript" src="${ctx}/javascript/jquery.js"></script>  
<script type="text/javascript" src="${ctx}/javascript/web_socket.js"></script>
<script type="text/javascript" src="${ctx}/javascript/jquery.WebSocket.js"></script>
<title>无标题文档</title>
</head>
  <script type="text/javascript">
  var socket=null;
  window.WEB_SOCKET_SWF_LOCATION="${ctx}/javascript/WebSocketMain.swf";
	 /*  socket = new WebSocket("ws://localhost:8080/websocket"); */
	  
	  /* socket = new SockJS("websocket",null,8080); */
	     var url = "websocket";  
		 socket = new $.websocket({  
	        protocol : url,  
	        domain : window.location.hostname,  
	        port : "8080",  
	        onOpen:function(data){    
	        	var ta = document.getElementById('responseText');
				ta.value = "打开WebSoket 服务正常，浏览器支持WebSoket!"+"\r\n";   
	        },    
	        onError:function(data){  
	             alert("error:"+ data)  
	        },    
	        onMessage:function(data){    
	        	var ta = document.getElementById('responseText');
				ta.value += event.data+"\r\n";  
	        },  
	        onClose:function(data){  
	        	var ta = document.getElementById('responseText');
				ta.value = "";
				ta.value = "WebSocket 关闭"+"\r\n"; 
				socket = null;  
	        }  
   		 });  
 
  function send(message){
	if(!socket){return;}
	socket.send(message);
  }
  </script>
  <body>
    <form onSubmit="return false;">
    	<input type = "text" name="message" value="Netty The Sinper"/>
    	<br/><br/>
    	<input type="button" value="发送 WebSocket 请求消息" onClick="send(this.form.message.value)"/>
    	<hr color="blue"/>
    	<h3>服务端返回的应答消息</h3>
    	<textarea id="responseText" style="width: 1024px;height: 300px;"></textarea>
    </form>
  </body>
</html>