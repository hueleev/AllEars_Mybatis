<%@page import="Userlist.UserlistDataBean"%>
<%@page import="Userlist.UserlistDBBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%request.setCharacterEncoding("UTF-8"); %>

<%
 String userid = (String)session.getAttribute("sessionid");
 String passwd = (String)session.getAttribute("sessionpasswd");
 String none ="";

 try{
	 UserlistDBBean dbPro = UserlistDBBean.getInstance();
	 UserlistDataBean user = dbPro.getUser2(userid,passwd);

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-deep-purple.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<body class="w3-theme-l5">

	<%@include file="/common/variable.jspf" %>
<script>
	function checkValue()
	{
		
	
		if(document.updateForm.passwd.value!=document.updateForm.passwdcheck.value){
			alert("비밀번호를 동일하게 입력하세요."); 
		return false;
		}
		
	}
</script>
<form method="post" name="updateForm" action="<%=request.getContextPath() %>/user/updatePro.jsp" onsubmit="return checkValue()">
<div class="w3-container" style="width:750px; margin-left:30%; margin-top:10%; margin-bottom:30%; text-align: center;">	
 <input type="hidden" name="num" value="<%=user.getNum()%>">
  <table class="w3-table-all">
    <thead>
      <tr class="w3-deep-purple">
        <th colspan="4" style="text-align:center"><%=user.getUserid() %> 님의 정보</th>
      </tr>
    </thead>
    
    <tr>
    	<td width="200"><b>아이디</b></td>
    	<td width="550" colspan="3"><%=user.getUserid() %></td>
    </tr>
 
    <tr>
 		<td width="200"><b>이름</b></td>
 		<td width="200"><%=user.getUsername() %></td>
 		<td width="100"><b>성별</b></td>
 		<td width="250"><%=user.getGender() %></td> 
    </tr>
    
    <tr>
    		<td width="200"><b>비밀번호</b></td>
    		<td width="175"><input type="password" name="passwd" value="<%=user.getPasswd() %>"></td>
    		<td width="200"><b>비밀번호 확인</b></td>
    		<td width="175"><input type="password" name="passwdcheck" value="<%=user.getPasswd() %>"></td>
    </tr>
    
    <tr>
    		<td width="200"><b>닉네임</b></td>
    		<td width="175"><input type="text" name="displayname" value="<%=user.getDisplayname() %>"></td>
    		<td width="200"><b>포지션</b></td>
    		<td width="175"><input type="text" name="position" value="<%=user.getPosition()%>"></td>
    </tr>
    
   
    <tr>
    	<td width="200"><b>연락처</b></td>
    	<td width="550" colspan="3"><input type="text" name="hp" value="<%if (user.getHp()==null) {%><%=none %><%}else{%><%=user.getHp()%><%}%>"></td>
    </tr>
    
    <tr>
    	<td width="200"><b>주소</b></td>
    	<td width="550" colspan="3"><input type="text" name="address" value="<%if (user.getAddress()==null) {%><%=none %><%}else{%><%=user.getAddress()%><%}%>"></td>
    </tr>
    
    <tr>
    	<td width="200"><b>이메일</b></td>
    	<td width="550" colspan="3"><input type="text" name="email" value="<%if (user.getEmail()==null) {%><%=none %><%}else{%><%=user.getEmail()%><%}%>"></td>
    </tr>
    
    <tr>
    	<td width="200"><b>소개</b></td>
    	<td width="550"  colspan="3"><textarea name="bio" rows="13" cols="40"><%if (user.getBio()==null) {%><%=none %><%}else{%><%=user.getBio()%><%}%></textarea></td>
    </tr>
  
  
  </table>
 <br>
	<a onclick="document.location.href='deleteForm.jsp?userid=<%=user.getUserid()%>&num=<%=user.getNum()%>'" class="w3-button w3-red"><b>탈퇴</b></a>
 	<button class="w3-button w3-yellow" type="submit"><b>수정</b></button>
 	<a href="/Project_AllEars/view/Main.jsp" class="w3-button w3-yellow"><b>취소</b></a>
 	
 	</div>
 </form>
 

<%}catch(Exception e){} %>
</body>
</html>