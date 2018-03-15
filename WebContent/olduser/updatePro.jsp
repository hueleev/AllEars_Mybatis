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
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="user" class="Userlist.UserlistDataBean">
<jsp:setProperty name="user" property="*"/>
</jsp:useBean>
<%System.out.println(user); %>
<%UserlistDBBean dbPro = UserlistDBBean.getInstance();
int chk=dbPro.updateUser(user); %>

<% if (chk==1) {
	String passwd=request.getParameter("passwd");
	session.setAttribute("sessionpasswd",passwd);%>

	
	<script type="text/javascript">
 		alert("수정 되었습니다 -");
 	</script>
 	<meta http-equiv="Refresh"
	content="0;url=/Project_AllEars/view/Main.jsp">

<% }%>

</body>
</html>