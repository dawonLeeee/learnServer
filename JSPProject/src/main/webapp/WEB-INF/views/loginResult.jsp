<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�α��� ���</title>

<style>
	h1 {
		background-color: yellow;
		color: red;
	}
</style>
</head>
<body>

	<!-- jsp���� �ڹ��ڵ��� ���� ����ϴ� ��� -->
	<!-- '='��ȣ�� ������ ��� -->
	<%=request.getParameter("inputId")%>
	<br>
	<%=request.getParameter("inputPw")%>



	<!-- �ڹ��ڵ� �ۼ� ����(���x) -->
	<%
	// �ڹ� �ּ� ���� == �ڹ��ڵ� �ۼ� ����
	//request.getAttribute("key") -> value���� ����
	String result = (String) request.getAttribute("key");
	//->�ٿ�ĳ���� �ʿ�
	%>

	<h3 style='color: green'><%=result%></h3>
	<button type='button' onclick='history.back()'>���ư���</button>


</body>
</html>