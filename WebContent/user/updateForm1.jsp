<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="Userlist.UserlistDataBean"%>
<%@page import="Userlist.UserlistDBBean"%>
<!DOCTYPE html>
<html>
<title>AllEars</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-metro.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<body class="w3-theme-l5">

<%@include file="/common/variable.jspf" %>


<form method="post" action="updatePro1">
  
<div class="w3-container" style="width:750px; margin-left:30%; margin-top:15%; text-align: center;">	
  <h3 class="w3-center"><b>정보 수정</b></h3>
  <br>
  <table class="w3-table">
    <thead>
      <tr class="w3-light-grey">
        <th style="text-align:center">${sessionid}님 !</th>
      </tr>
    </thead>

  </table>
	
    <input class="w3-input w3-margin-top w3-margin-bottom w3-center" type="password" placeholder="정보수정을 원하시면, 비밀번호를 입력해주세요." name="passwd" required>
	<br>
	<button type="submit" class="w3-btn w3-round w3-metro-yellow w3-hover-white"><b>정보 수정</b></button>
   	&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="main" class="w3-btn w3-round w3-red w3-hover-white"><b>취소</b></a>
 </div>
</form>




</body>
</html>