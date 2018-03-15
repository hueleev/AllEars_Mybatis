<%@page import="Userlist.UserlistDataBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="Userlist.UserlistDBBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%request.setCharacterEncoding("UTF-8"); %>
<%
int pageSize = 5;
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
String pageNum = request.getParameter("pageNum");
if (pageNum==null || pageNum == "") {
	pageNum = "1";}
int currentPage = Integer.parseInt(pageNum);
int startRow = (currentPage -1) * pageSize +1;
int endRow = currentPage * pageSize;
int count = 0;
int number = 0;
List userList = null;
UserlistDBBean dbPro = UserlistDBBean.getInstance();
count = dbPro.getUserCount();
if (count >0){
	userList = dbPro.getUsers(startRow,endRow);}
	number = count - (currentPage - 1)*pageSize;
%>
<html>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-deep-purple.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<body class="w3-theme-l5">
<%@include file="/common/variable.jspf" %>



<div class="w3-container" style="width:800px; margin-left:30%; margin-top:15%;">	
  <h3 class="w3-center"><b>회원 목록</b></h3>	
  <table class="w3-table-all w3-hoverable">
    <thead>
      <tr class="w3-deep-purple">
        <th align="center" width="100">번호</th>
        <th align="center" width="100">아이디</th>
        <th align="center" width="150">이름</th>
        <th align="center" width="50">성별</th>
        <th align="center" width="200">포지션</th>
        <th align="center" width="200">가입일</th>
      </tr>
    </thead>
    <%
    	for(int i=0;i<userList.size();i++){
    		UserlistDataBean user = (UserlistDataBean) userList.get(i);%>
    		
    <tr>
      <td align="center" width="100"><%=number--%></td>
      <td width="100">
      <a href="/Project_AllEars/user/content.jsp?num=<%=user.getNum()%>&pageNum=<%=currentPage%>&number=<%=number+1%>">
      <%=user.getUserid()%></a></td>
      <td width="150"><%=user.getUsername()%></td>
      <td align="center" width="50"><%=user.getGender() %></td>
      <td width="200"><%=user.getPosition() %></td>
      <td width="200"><%=sdf.format(user.getReg_date()) %></td>
    </tr> <%} %>
  </table>
  <br>
	<div class="w3-center">
			<% int bottomLine = 3;
			if (count >0){int pageCount =count/pageSize
			+(count % pageSize == 0 ? 0 : 1);
			int startPage = 1 + (currentPage -1) / bottomLine * bottomLine;
			int endPage = startPage + bottomLine -1;
			if (endPage > pageCount) endPage = pageCount;
			if (startPage > bottomLine) { %>
			<a href = "list.jsp?pageNum=<%=startPage - bottomLine %>">[이전]</a>
			<% } %>
			<% for (int i = startPage; i<=endPage; i++){ %>
				<a href="list.jsp?pageNum=<%=i%>"> <%
				if (i!=currentPage) out.print("["+i+"]");
				else out.print("<font color='deep-purple'>[" + i +"]</font>");%></a>
			<% }
			if (endPage<pageCount) {
			%>
			<a href="list.jsp?pageNum=<%=startPage + bottomLine %>">[다음]</a>
			<% }		} %>
	</div>
</div>

</body>
</html>