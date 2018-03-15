<%@page import="Userlist.UserlistDBBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
 String userid = request.getParameter("userid");
 UserlistDBBean dao = UserlistDBBean.getInstance();
 boolean result = dao.confirmId(userid);
 String none=null;
 if (result){ %>
 	<center>
 	<br/><br/>
 	<h4>이미 사용중인 ID입니다.</h4>
 	</center>
<%} else {%>
	<center>
	<br/><br/>
	<h4>입력하신 <%=userid %>는 사용할 수 있는 ID입니다.</h4>
	</center>
	<%} %>
</body>
</html>