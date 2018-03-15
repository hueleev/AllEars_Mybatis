<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="Userlist.UserlistDBBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<% request.setCharacterEncoding("UTF-8"); %>


	
<jsp:useBean id="user" class="Userlist.UserlistDataBean"/>
	<jsp:setProperty name="user" property="*"/>




<% System.out.println(user); %>
<% UserlistDBBean dbPro = UserlistDBBean.getInstance(); 

String pageNum=request.getParameter("pageNum");
if(pageNum == null || pageNum == ""){
	pageNum ="1";}

dbPro.insertUser(user);
%>


<% response.sendRedirect("/Project_AllEars/view/Intro.jsp");%>



</body>


</html>