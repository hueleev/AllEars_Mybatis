<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<html>
<title>W3.CSS</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-metro.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">



<body class="w3-theme-l5">

<div class="w3-container w3-center" style="margin-top:5%; margin-bottom:10%;">

   <i class="fa fa-stop-circle-o" style="font-size:70px;">&nbsp;</i>
   <i class="fa fa-play-circle-o" style="font-size:70px;">&nbsp;</i>
    <i class="fa fa-pause-circle-o" style="font-size:70px;">&nbsp;</i>
    <br><br>
	<form method="post" action="songUpdate">
	  <table align="center" cellspacing="10" class="w3-card w3-round w3-white w3-padding-large" style="width:50%%;">
  
	<input type="hidden" name="sboardid" value="${song.sboardid}">
	<input type="hidden" name="snum" value="${song.snum}">
	
    <tr>
      <td><b>타이틀</b>&nbsp;&nbsp;<a style="color:red">*</a></td>
      <td><input class="w3-input w3-animate-input" name="stitle" style="width:50%" type="text" value="${song.stitle}" required></td>
    </tr>
    
    <tr>
      <td><b>장르</b>&nbsp;&nbsp;<a style="color:red">*</a></td>
      <td><input class="w3-input w3-animate-input" name="genre" style="width:50%" type="text" value="${song.genre}" required></td>
    </tr>
    
    
      	<c:if test="${song.cfilesize!=0}">
	      <tr>
	      <td><b>앨범 커버</b></td>
			  
			   <td colspan="4" align="center">
			   <br>
			   <img src="/AllEars/songSave/${song.cfilename}" class="w3-rounded" style="height:300px; width:300px;" alt="Avatar"></td>
		</tr>
		</c:if>

    <tr>
      <td><b>음악/영상 :)</b>&nbsp;&nbsp;<a style="color:red">*</a></td>
      <td>
      				<c:if test="${song.type eq 'mp3' || song.type eq 'wav'}">
						<audio controls="controls">
						  <source src="/AllEars/songSave/${song.sfilename}" type="audio/mpeg">
						  <source src="/AllEars/songSave/${song.sfilename}" type="audio/wav">
						</audio>
					</c:if>	
					
					<c:if test="${song.type eq 'mp4'}">
						<video width="320" height="240" controls>
						  <source src="/AllEars/songSave/${song.sfilename}" type="video/mp4">
						  <source src="/AllEars/songSave/${song.sfilename}" type="video/ogg">
						</video>
					</c:if>
				
      
      </td>
    </tr>

    <tr>
      <td><b>소개</b></td>
      <td><textarea class="w3-input w3-border w3-margin-bottom" name="sbio" rows="4" cols="25">${song.sbio}</textarea></td>
    </tr>
    
    <tr>
       <td></td>
       <td><button class="w3-btn w3-round w3-metro-yellow w3-hover-white w3-right" type="submit">수정</button><td>
    <tr>
    

  </table>
</form>
</div>

</body>
</html> 