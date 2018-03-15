<%@page import="Userlist.UserlistDataBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Userlist.UserlistDBBean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body >
<%
 String userid=request.getParameter("userid");
 String passwd=request.getParameter("passwd");
 UserlistDBBean dao = UserlistDBBean.getInstance();
 int pwcheck = dao.login(userid, passwd);
 
%>
<% if(pwcheck == -1) { %>
	<script>
		alert("가입되지 않은 아이디입니다 -");
		history.back();
	</script>
<%
	}
	else if(pwcheck == 0)
	{
%>

	<script>
	alert("비밀번호가 틀립니다 -");
	history.back();
	</script>
<%
	}
	else
	{
		UserlistDataBean user = dao.getUser2(userid,passwd);
		String displayname=user.getDisplayname();
		session.setAttribute("sessionid",userid);
		session.setAttribute("sessionpasswd",passwd);
		session.setAttribute("sessiondisplayname",displayname);

		response.sendRedirect("/Project_AllEars/view/Main.jsp");
		
	}
%>


</body>
</html>