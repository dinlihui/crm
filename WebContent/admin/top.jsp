<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=path %>/css/base.css" rel="stylesheet" type="text/css">
    <script language='javascript'>
		var preFrameW = '206,*';
		var FrameHide = 0;
		var curStyle = 1;
		var totalItem = 9;
		function ChangeMenu(way){
			var addwidth = 10;
			var fcol = top.document.all.btFrame.cols;
			if(way==1) addwidth = 10;
			else if(way==-1) addwidth = -10;
			else if(way==0){
				if(FrameHide == 0){
					preFrameW = top.document.all.btFrame.cols;
					top.document.all.btFrame.cols = '0,*';
					FrameHide = 1;
					return;
				}else{
					top.document.all.btFrame.cols = preFrameW;
					FrameHide = 0;
					return;
				}
			}
			fcols = fcol.split(',');
			fcols[0] = parseInt(fcols[0]) + addwidth;
			top.document.all.btFrame.cols = fcols[0]+',*';
		}
		
		function logout()
		{
		   if(confirm("确定要退出本系统吗??"))
		   {
			   window.parent.location="<%=path %>/login.jsp";
		   }
		}
    </script>
    <div id="time"> </div>

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
  </head>
  
  <body bgColor='#84c1ff' style="margin: 0;padding: 0">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" background="<%=path %>/images/abg.jpg">
	  <tr>
	    <td width='50%' height="60" style="font-size:26px; font-weight: bold;">&nbsp;&nbsp;客户管理系统</td>
	    <td width='50%' align="right">
	    	<table width="550" border="0" cellspacing="0" cellpadding="0">
		      <tr>
			      <td align="right" height="26" style="padding-right:10px;line-height:26px;font-size:17px">
			        	<font style="font-size:16px; font-weight: bold;">
			        	    <c:if test="${sessionScope.userType==0}">
			        	         您好：管理员_${sessionScope.admin.userName }&nbsp;&nbsp;&nbsp;&nbsp;
			        	    </c:if>
			        	    <c:if test="${sessionScope.userType==1}">
			        	         您好：${sessionScope.yuangong.name }&nbsp;&nbsp;&nbsp;&nbsp;
			        	    </c:if>
			        	</font>
			        	<!--  [<a href="" target="_blank">网站主页</a>]-->
			        	
			        	[<a href="#" onclick="logout()">注销退出</a>]
						[<a href="javascript:ChangeMenu(0);">显示主框架</a>]
			      </td>
	          </tr>
	        </table>
	    </td>
	  </tr>
	</table>
  </body>
</html>
