<%@page import="Userlist.UserlistDBBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%-- <% request.setCharacterEncoding("UTF-8"); %>
<%
 String pageNum = request.getParameter("pageNum");
 if(pageNum == null || pageNum == "") {
	 pageNum = "1";
 }
%>

<%
int num = Integer.parseInt(request.getParameter("num"));
String passwd = request.getParameter("passwd");
UserlistDBBean dbPro = UserlistDBBean.getInstance();


int check = dbPro.deleteUser(num,passwd);

if (check ==1) {
%>

	<script language="JavaScript">
	 alert("회원 탈퇴가 완료 되었습니다.")
	</script>
	
<meta http-equiv="Refresh"
	content="0;url=/Project_AllEars/view/Intro.jsp">
	


	
<%} else if (passwd.equals(session.getAttribute("sessionpasswd"))) { 
	if (session.getAttribute("sessionid").equals("admin")){
		dbPro.deleteUser2(num,"Admin");
	}
	%>
	
	<script language="JavaScript">
	 alert("회원 탈퇴가 완료 되었습니다.")
	</script>
	
	<meta http-equiv="Refresh"
	content="0;url=list.jsp?pageNum=<%=pageNum%>">
	
<%} else { %>
	<script language="JavaScript">
	alert("비밀번호가 맞지 않습니다.");
	history.go(-1);
</script>
<% } %> --%>
</body>
</html>