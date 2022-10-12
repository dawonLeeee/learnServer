<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>로그인 결과</title>

<style>
	h1 {
		background-color: yellow;
		color: red;
	}
</style>
</head>
<body>

	<!-- jsp에서 자바코드의 값을 출력하는 방법 -->
	<!-- '='기호가 있으면 출력 -->
	<%=request.getParameter("inputId")%>
	<br>
	<%=request.getParameter("inputPw")%>



	<!-- 자바코드 작성 영역(출력x) -->
	<%
	// 자바 주석 가능 == 자바코드 작성 영역
	//request.getAttribute("key") -> value값이 나옴
	String result = (String) request.getAttribute("key");
	//->다운캐스팅 필요
	%>

	<h3 style='color: green'><%=result%></h3>
	<button type='button' onclick='history.back()'>돌아가기</button>


</body>
</html>