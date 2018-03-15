<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%
 int num=0;
 if(request.getParameter("num")!=null){
	 num=Integer.parseInt(request.getParameter("num"));
	 
	 
 };
 
%>
<style type="text/css">
#checkMsg{
  font-size: 12px;
}
#checkPwd{
  color : red;
  font-size: 12px;
}
</style>
<script type="text/javascript" src="httpRequest.js"></script>
<script type="text/javascript">

	function checkValue()
	{
		
	
		if(document.JoinForm.passwd.value!=document.JoinForm.passwdcheck.value){
			alert("비밀번호를 동일하게 입력하세요."); 
		return false;
		}
		
		if(document.JoinForm.idDuplication.value!= "idCheck"){
			alert("아이디 중복체크를 해주세요.");
			return false;
		}
		
		alert("반가워요! 로그인해주세요 - "); 
	}

 	function inputIdChk() {
 		document.JoinForm.idDuplication.value="idUncheck";
 	}
 	
 	function confirmId() {
		
		if (document.JoinForm.userid.value=="") {
			alter("ID를 입력하세요.");
			return;
		}
		url = "/Project_AllEars/user/confirmId.jsp?userid="+document.JoinForm.userid.value;
		open(url,"confirm","toobar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=300,height=200");
		
	}
	 
</script>

 <button onclick="document.getElementById('join').style.display='block'" class="w3-button w3-black w3-large w3-center w3-border w3-border-white w3-hover-cyan" style="width:25%;"><b>Join</b></button>
  <div id="join" class="w3-modal">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom" style="max-width:600px">
  
      <div class="w3-center"><br>
        <span onclick="document.getElementById('join').style.display='none'" class="w3-button w3-xlarge w3-transparent w3-display-topright" title="Close Modal">×</span>
        
      </div>

      <form class="w3-container" name="JoinForm" method="post" action="/Project_AllEars/user/JoinPro.jsp" onsubmit="return checkValue()">
        <input type="hidden" name="num" value="<%=num %>">
        <div class="w3-section" >
          <label><b>아이디</b></label><br>
          <input class="w3-input w3-half w3-center w3-margin-bottom w3-animate-input" type="text" placeholder="아이디를 입력해주세요." style="width:70%" name="userid" onkeydown="inputIdChk()" required>
          <input type = "button" value="중복확인" name="confirm_id" onClick="confirmId(this.form)">
          <input type="hidden" name="idDuplication" value="idUncheck">
	<!-- 	  <div id="checkMsg">아이디를 입력하세요.</div> -->
          <label><b>비밀번호</b></label>
          <input class="w3-input w3-margin-bottom" type="password" placeholder="비밀번호를 입력해주세요." name="passwd" required>
          <label><b>비밀번호 확인</b></label>
          <input class="w3-input w3-margin-bottom" type="password" placeholder="비밀번호를 다시 한번 입력해주세요." name="passwdcheck" required>
          <label><b>이름</b></label>
          <input class="w3-input w3-margin-bottom" type="text" placeholder="이름을 입력해주세요." name="username" required>
          <label><b>닉네임</b></label>
          <input class="w3-input w3-margin-bottom" type="text" placeholder="닉네임을 입력해주세요." name="displayname" required>
          <label><b>포지션</b></label>
          <input class="w3-input w3-margin-bottom" type="text" placeholder="Songwriter? Listener? Entertainment? 어느 쪽이신가요 ?" name="position" required>
          <label><b>성별</b></label><br>
          <input type="radio" name="gender" value="남"> 남
       	  <input class="w3-margin-bottom" type="radio" name="gender" value="여"> 여 <br>
          <label><b>연락처</b></label>
          <input class="w3-input w3-margin-bottom" type="text" placeholder="연락처를 입력해주세요." name="hp">
          <label><b>주소</b></label>
          <input class="w3-input w3-margin-bottom" type="text" placeholder="주소를 입력해주세요." name="Address">
          <label><b>이메일</b></label>
          <input class="w3-input w3-margin-bottom" type="email" placeholder="이메일을 입력해주세요." name="email">
          <label><b>소개</b></label><br>
          <textarea class="w3-input w3-border w3-margin-bottom" name="bio" rows="5" cols="20"></textarea>
          <button class="w3-button w3-block w3-cyan w3-section w3-padding" type="submit"><b style="color:white;">Join</b></button>
        </div>
      </form>
      </div>

    </div>