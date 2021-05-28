<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>欢迎使用客户管理系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <style>
	body
	{
	  scrollbar-base-color:#C0D586;
	  scrollbar-arrow-color:#FFFFFF;
	  scrollbar-shadow-color:DEEFC6;
	}
	</style>
  </head>
  <script>
window.onload=function(){

//定时器每秒调用一次fnDate()
setInterval(function(){
fnDate();
},1000);
}

//js 获取当前时间
function fnDate(){
var oDiv=document.getElementById("time");
var date=new Date();
var year=date.getFullYear();//当前年份
var month=date.getMonth();//当前月份
var data=date.getDate();//天
var hours=date.getHours();//小时
var minute=date.getMinutes();//分
var second=date.getSeconds();//秒
var time=year+"-"+fnW((month+1))+"-"+fnW(data)+" "+fnW(hours)+":"+fnW(minute)+":"+fnW(second);
oDiv.innerHTML=time;
}
//补位 当某个字段不是两位数时补0
function fnW(str){
var num;
str>=10?num=str:num="0"+str;
return num;
} 
</script>
  <frameset rows="60,*" cols="*" frameborder="no" border="0" framespacing="0">
	  <frame src="<%=path %>/admin/top.jsp" name="topFrame" scrolling="no">
	  <frameset cols="180,*" name="btFrame" frameborder="NO" border="0" framespacing="0">
	    <frame src="<%=path %>/admin/menu.jsp" noresize name="menu" scrolling="yes">
	    <frame src="<%=path %>/admin/index/sysPro.jsp" noresize name="main" scrolling="yes">
	  </frameset>
  </frameset>
  <noframes>
	  <body>您的浏览器不支持框架！</body>
  </noframes>
</html>
