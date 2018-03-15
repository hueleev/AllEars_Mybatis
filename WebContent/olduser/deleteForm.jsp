<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="Userlist.UserlistDataBean"%>
<%@page import="Userlist.UserlistDBBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	int num=Integer.parseInt(request.getParameter("num"));
	String pageNum = request.getParameter("pageNum");
	
	UserlistDBBean dbPro = UserlistDBBean.getInstance();
 	UserlistDataBean user = dbPro.getUser(num,"content"); 
	
%>
<html>

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-deep-purple.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<body class="w3-theme-l5">

	<%@include file="/common/variable.jspf" %>
<form method="post" action="/Project_AllEars/user/deletePro.jsp">
<div class="w3-container" style="width:750px; margin-left:30%; margin-top:15%; text-align: center;">	
  <h3 class="w3-center"><b>회원 목록</b></h3>
  <br>
  <table class="w3-table-all w3-hoverable">
    <thead>
      <tr class="w3-deep-purple">
        <th style="text-align:center"><%=user.getUserid()%>님 !</th>
      </tr>
    </thead>
    
    

  </table>
	
    <input class="w3-input w3-margin-top w3-margin-bottom w3-center" type="password" placeholder="탈퇴를 원하시면, 비밀번호를 입력해주세요." name="passwd" required>
    <input type="hidden" name="num" value="<%=num %>">
    

	<br>
	
	<button type="submit" class="w3-button w3-red"><b>탈퇴</b></button>
   	&nbsp;&nbsp;&nbsp;&nbsp;
 <%if (session.getAttribute("sessionid").equals("admin")) {%>
    <a href="/Project_AllEars/user/list.jsp?pageNum=<%=pageNum %>" class="w3-button w3-yellow"><b>회원 목록</b></a>
 <%}else{ %>
 	<a href="/Project_AllEars/user/updateForm.jsp" class="w3-button w3-yellow"><b>취소</b></a>
 <%} %>
 </div>
</form>


</body>
</html>