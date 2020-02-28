<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!--bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<!--font-awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
h1 { 
	text-align : center;
	margin-top : 20px;
}
#name {
	color : blue;
}
.container {
	margin-top : 50px;
	margin-bottom : 50px; 
}
#title {
	font-weight : 200px;
} 
</style>


</head>
<body>
<div id="BMain"></div>
<br/><br/>

<div class="container">
  <c:forEach items="${ApplyDetailList}" var="dto">
	<h3>입점문의 정보<b id="aStatus">(${dto.aStatus})</b></h3>
	<table class="table table-bordered">
		<thead>
		
				<tr>
					<th id="title">가게이름</th><td>${dto.asName}</td>
				</tr>
				
				<tr>
					<th id="title">사업자번호</th><td>${dto.abNo}</td>
				</tr>
				
				<tr>
					<th id="title">가게주소</th><td>${dto.asAd}</td>
				</tr>
				
				<tr>
					<th id="title">가게 전화번호</th><td>${dto.asPhone}</td>
				</tr>  
				
				<tr>
					<th id="title">카테고리</th><td>${dto.asCategory}</td>
				</tr>  
				
				<tr>
					<!-- 날짜형식으로 인해서 fmt 사용 -->
					<th id="title">등록일</th><td><fmt:formatDate value="${dto.asDate}" pattern="yyyy년 MM월 dd일 "/></td>
				</tr>

		</thead>
	</table>

		<a id="Mod" class="btn btn-danger text-white" href="ApplyContent?asNo=${dto.asNo}">수정하기</a>&nbsp;&nbsp;&nbsp;
		<a id="del" class="btn btn-danger text-white" href="ApplyDelete?asNo=${dto.asNo}&user_id=${dto.user_id}">삭제하기</a>
  </c:forEach>
</div>


<!--jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<!--popper -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<!--javascript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
//맨위에 헤더를 가져옴
$("#BMain").load("BossHome");

$(document).ready(function() {
	
	var aStatus = $("#aStatus").text();
	if(aStatus == "(처리완료)") {
		$("#Mod").hide();
		$("#del").hide();
	}
	else {
		$("#Mod").show();
		$("#del").show();
	}

});
</script>
</body>
</html>