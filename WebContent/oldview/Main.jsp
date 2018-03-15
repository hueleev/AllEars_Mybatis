<!DOCTYPE html>

<html>
<title>AllEars</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<body class="w3-theme-l5">


<%
 if(session.getAttribute("sessionid")==null)
 {
	 response.sendRedirect("Intro.jsp");
 }else
 {
%>

<!--  <script type="text/javascript">
 	function logoutPro(){
 		alert("로그아웃 되었습니다 -");
 		location.href="/Project_AllEars/user/logout.jsp";
 	}
 </script> -->
<!-- Navbar -->
<%@include file="/common/variable.jspf" %>

<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1000px; margin-top:80px">    
  <!-- The Grid -->
  <div class="w3-row">
    <!-- Left Column -->
    <div class="w3-col m3">
      <!-- Profile -->
      <div class="w3-card w3-round w3-white">
        <div class="w3-container">
         <h4 class="w3-center"><%=session.getAttribute("sessiondisplayname")%></h4>
         <p class="w3-center"><img src="/Project_AllEars/img/hyungwon.jpg" class="w3-circle" style="height:150px; width:150px;" alt="Avatar"></p>
         <hr>
	    <p class="w3-center">
             <button  onclick="url="";" type="button" class="w3-button w3-xlarge fa fa-facebook-official"></button>
             <button  onclick="url="";" type="button" class="w3-button w3-xlarge fa fa-instagram"></button>
             <button  onclick="url="";" type="button" class="w3-button w3-xlarge fa fa-soundcloud"></button>
         </p>
         <hr>
		 <p class="w3-center">
         	<i class="fa fa-user w3-large"></i>
         	<button onclick="document.getElementById('following').style.display='block'" class="w3-button">100</button>
				  <div id="following" class="w3-modal">
				    <div class="w3-modal-content w3-card-4 w3-animate-zoom" style="max-width:600px">
				      <div class="w3-container">
				        <span onclick="document.getElementById('following').style.display='none'" class="w3-button w3-display-topright">&times;</span>
				        <p>내가 팔로잉하는 사람들</p>
				      </div>
				    </div>
				  </div>
         </p>
         
         <p class="w3-center">
         	<i class="fa fa-users w3-large"></i>
         	<button onclick="document.getElementById('follower').style.display='block'" class="w3-button">100</button>
				  <div id="follower" class="w3-modal">
				    <div class="w3-modal-content w3-card-4 w3-animate-zoom" style="max-width:600px">
				      <div class="w3-container">
				        <span onclick="document.getElementById('follower').style.display='none'" class="w3-button w3-display-topright">&times;</span>
				        <p>날 팔로우 하는 사람</p>
				      </div>
				    </div>
				  </div>
         </p>
         
         <p><i class="fa fa-pencil fa-fw w3-margin-right w3-text-theme"></i>Listener</p>
         <p><i class="fa fa-home fa-fw w3-margin-right w3-text-theme"></i>Seoul, SouthKorea</p>
        </div>
      </div>
      <br>
      
      <!-- Accordion -->
      <div class="w3-card w3-round">
        <div class="w3-white">
          <button onclick="myFunction('Demo2')" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="	fa fa-commenting fa-fw w3-margin-right"></i> My GuestBook</button>
          <div id="Demo2" class="w3-hide w3-container">
            <p>Some other text..</p>
          </div>
          <button onclick="myFunction('Demo3')" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-music fa-fw w3-margin-right"></i> My Music</button>
          <div id="Demo3" class="w3-hide w3-container">
         <div class="w3-row-padding">
         <br>
           <div class="w3-half">
             <img src="/w3images/lights.jpg" style="width:100%" class="w3-margin-bottom">
           </div>
           <div class="w3-half">
             <img src="/w3images/nature.jpg" style="width:100%" class="w3-margin-bottom">
           </div>
           <div class="w3-half">
             <img src="/w3images/mountains.jpg" style="width:100%" class="w3-margin-bottom">
           </div>
           <div class="w3-half">
             <img src="/w3images/forest.jpg" style="width:100%" class="w3-margin-bottom">
           </div>
           <div class="w3-half">
             <img src="/w3images/nature.jpg" style="width:100%" class="w3-margin-bottom">
           </div>
           <div class="w3-half">
             <img src="/w3images/fjords.jpg" style="width:100%" class="w3-margin-bottom">
           </div>
         </div>
          </div>
        </div>      
      </div>
      <br>
      
      <!-- Interests --> 
      <div class="w3-card w3-round w3-white w3-hide-small">
        <div class="w3-container">
          <p>my style</p>
          <p>
            <span class="w3-tag w3-small w3-theme-d5">R&B</span>
            <span class="w3-tag w3-small w3-theme-d4">hiphop</span>
            <span class="w3-tag w3-small w3-theme-d3">Trackmaker</span>
            <span class="w3-tag w3-small w3-theme-d2">ambient</span>
            <span class="w3-tag w3-small w3-theme-d1">singer</span>
            <span class="w3-tag w3-small w3-theme">k-pop</span>
            <span class="w3-tag w3-small w3-theme-l1">idol</span>
          </p>
        </div>
      </div>
      <br>
      
      <!-- Alert Box -->
      <div class="w3-container w3-display-container w3-round w3-theme-l4 w3-border w3-theme-border w3-margin-bottom w3-hide-small">
        <span onclick="this.parentElement.style.display='none'" class="w3-button w3-theme-l3 w3-display-topright">
          <i class="fa fa-remove"></i>
        </span>
        <p><strong>Hey!</strong></p>
        <p>___님이 당신을 팔로우하였어요!</p>
      </div>
    
    <!-- End Left Column -->
    </div>
    
    <!-- Middle Column -->
    <div class="w3-col m7">
    
      <div class="w3-row-padding">
        <div class="w3-col m12">
          <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding w3-center">
            	  <br>
			  <button type="button" class="w3-button w3-large w3-theme"><i class="fa fa-music"></i></button>
			  <a class="w3-margin-left" style="font-size:13pt; color:grey;">당신의 음악을 들려주세요-!</a><br><br>
            </div>
          </div>
        </div>
      </div>
      
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <img src="/Project_AllEars/img/pptnz.png" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:80px">
        <span class="w3-right w3-opacity">1 min</span>
        <h3>Peppertones</h3><h4>Band</h4>
        <hr class="w3-clear">
        <p>2016 연말콘서트, 그리고 모닥불 앞 우리 같은 노래 '캠프파이어'</p>
          <div class="w3-row-padding" style="margin:0 -16px">
            <div class="w3-half">
              <img src="/Project_AllEars/img/pptnz-album.png" style="width:150px;" class="w3-margin-bottom">
            </div>
        </div>
        <button type="button" class="w3-button w3-theme-d1 w3-margin-bottom"><i class="fa fa-heartbeat"></i>  Like</button> 
        <button type="button" class="w3-button w3-theme-d2 w3-margin-bottom"><i class="fa fa-comment"></i>  Comment</button> 
      </div>
      
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <img src="/Project_AllEars/img/oohyo.png" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:80px">
        <span class="w3-right w3-opacity">16 min</span>
        <h3>Oohyo</h3><h4>Singersongwriter</h4>
        <hr class="w3-clear">
        <p>[꿀차]는 혼자인 듯하면서도 혼자가 아닌, 쓸쓸하지만 쓸쓸하지만은 않은 저의 일상을 돌아보며 만든 노래입니다.
        좋아하는 차의 향기를 맡을 때처럼 은근하게 따뜻하게 달콤하게 느껴지는 순간들을 노래에 담고 싶었습니다.
        이번 싱글은 일본의 영상 제작자 및 아티스트들과 함께 뮤비를 기획하고 촬영하는 새로운 도전을 할 수 있어서 저에게 더욱더 의미있고 즐거운 시간이었습니다.
        아직 많이 부족한데도 새 노래를 기다려주신 여러분, 그리고 기꺼이 이번 작업에 참여해주신 모든 분들께 감사드립니다.</p>
          <div class="w3-row-padding" style="margin:0 -16px">
            <div class="w3-half">
              <img src="/Project_AllEars/img/oohyo-album.png"  style="width:150px;"  class="w3-margin-bottom">
            </div>
        </div>        <button type="button" class="w3-button w3-theme-d1 w3-margin-bottom"><i class="fa fa-heartbeat"></i>  Like</button> 
        <button type="button" class="w3-button w3-theme-d2 w3-margin-bottom"><i class="fa fa-comment"></i>  Comment</button> 
      </div>  

      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <img src="/Project_AllEars/img/thexx.png" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:80px">
        <span class="w3-right w3-opacity">32 min</span>
        <h3>The XX</h3><h4>Band</h4>
        <hr class="w3-clear">
        <p>'The XX' [Lips (Edu Imbernon Remix)]</p>
          <div class="w3-row-padding" style="margin:0 -16px">
            <div class="w3-half">
              <img src="/Project_AllEars/img/thexx-album.png"  style="width:150px;"class="w3-margin-bottom">
            </div>
        </div>
        <button type="button" class="w3-button w3-theme-d1 w3-margin-bottom"><i class="fa fa-heartbeat"></i>  Like</button> 
        <button type="button" class="w3-button w3-theme-d2 w3-margin-bottom"><i class="fa fa-comment"></i>  Comment</button> 
      </div> 
      
    <!-- End Middle Column -->
    </div>
    
    <!-- Right Column -->
    <div class="w3-col m2">

	  <iframe src="/Project_AllEars/chart/chart.jsp" style="display:block; width:70vw; height:1000px; border:none; -webkit-transform: scale(0.80);
        -webkit-transform-origin: 0 0;"></iframe>
      <br>
      
      
      
    <!-- End Right Column -->
    </div>
    
  <!-- End Grid -->
  </div>
  
<!-- End Page Container -->
</div>
<br>

<!-- Footer -->

<%@include file="/common/footer.jspf" %>
 
<script>
// Accordion
function myFunction(id) {
    var x = document.getElementById(id);
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
        x.previousElementSibling.className += " w3-theme-d1";
    } else { 
        x.className = x.className.replace("w3-show", "");
        x.previousElementSibling.className = 
        x.previousElementSibling.className.replace(" w3-theme-d1", "");
    }
}

// Used to toggle the menu on smaller screens when clicking on the menu button
function openNav() {
    var x = document.getElementById("navDemo");
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
    } else { 
        x.className = x.className.replace(" w3-show", "");
    }
}
</script>

<%} %>

</body>
</html> 

