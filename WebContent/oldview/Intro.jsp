<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<title>AllEars</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
html,body {
      background-image: url("/AllEars/img/main.png");
      background-size: cover;
      background-attachment: fixed;}
</style>
<body class="w3-theme-l5 w3-animate-opacity">

<!-- Navbar -->

<div class="w3-bar w3-black">
  <a href="" class="w3-bar-item w3-button w3-large w3-hover-cyan"><i class="fa fa-play w3-margin-right"></i>All Ears</a>
<!--   <a href="#" class="w3-bar-item w3-button w3-large w3-hover-white"><i class="fa fa-envelope"></i></a> -->
  <input type="text" class="w3-bar-item w3-large w3-input" placeholder="Search..">
  <a href="#" class="w3-bar-item w3-button w3-large w3-hover-cyan"><i class="fa fa-search"></i></a>
  <a onclick="document.getElementById('id01').style.display='block'" class="w3-bar-item w3-button w3-large w3-right w3-hover-cyan" title="Login">
  <i class="fa fa-user-circle w3-margin-right" alt="Login"></i>Login</a>
</div>

<!-- body -->


<div class="w3-center w3-animate-right" style="margin-top:10%;">

  <p><i class="fa fa-spinner w3-spin" style="font-size:64px; color:white;"></i></p>  
  <a class="w3-jumbo" style="color:white;">ALL EARS</a>
  <br>
  <a style="color:white;">당신의 음악을 들려주세요 -<br>All Ears가 당신의 음악을 기다려요 !</a>
  <br><br>
  <!-- 로그인  -->
   <button onclick="document.getElementById('id01').style.display='block'" class="w3-button w3-cyan w3-large w3-center w3-border w3-border-white" style="width:25%;"><b style="color:white;">Login</b></button>

  <div id="id01" class="w3-modal ">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom " style="max-width:600px">
  
      <div class="w3-center"><br>
        <span onclick="document.getElementById('id01').style.display='none'" class="w3-button w3-xlarge w3-transparent w3-display-topright" title="Close Modal">×</span>
        
      </div>

      <form class="w3-container" method="post" action="<%=request.getContextPath() %>/user/LoginPro.jsp">
        <div class="w3-section">
          <label><b>아이디</b></label>
          <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="아이디를 입력해주세요" name="userid" required>
          <label><b>비밀번호</b></label>
          <input class="w3-input w3-border" type="password" placeholder="비밀번호를 입력해주세요" name="passwd" required>
          <button class="w3-button w3-block w3-cyan w3-section w3-padding" type="submit"><b style="color:white;">Login</b></button>
        </div>
      </form>
	
      <div class="w3-container w3-border-top w3-padding-16 w3-light-grey">
        <button onclick="document.getElementById('id01').style.display='none'" type="button" class="w3-button w3-red">Cancel</button>
        <span class="w3-right w3-padding w3-hide-small">Forgot <a href="#">password?</a></span>
        <span class="w3-right w3-padding w3-hide-small">Forgot <a href="#">id?</a></span>
      </div>

    </div>
  </div>
  
  <br>
  <br>
  
  <!-- 회원가입 폼 -->
	 <a href="JoinForm"><button class="w3-button w3-white w3-large w3-center w3-border w3-border-white w3-hover-cyan" style="width:25%;"><b>Join</b></button></a>


  
</div>

</body>
</html>