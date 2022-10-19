<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 페이지</title>

    <link rel="stylesheet" href="/resources/css/login-style.css">
    <script src="https://kit.fontawesome.com/f7459b8054.js" crossorigin="anonymous"></script>

</head>
<body>
    <main>
        <section class="logo-area">
            <a href="/">
                <img src="/resources/images/logo.jpg"/>
            </a>
        </section>
        <form action="/member/login" method="post">
            <section class="input-box">
            <%-- 쿠키에 저장된 아이디가 있으면 추가>> value=${cookie.saveId.value} --%>
                <input type="text" name="inputEmail" placeholder="Email" required value=${cookie.saveId.value}> <br>
            </section>
            <section class="input-box">
                <input type="password" name="inputPw" placeholder="password" required> <br>
            </section>

            <button class="login-btn">Login</button>

            <%-- 쿠키에 saveId가 있는 경우 변수 생성 --%>
            <c:if test="${!empty cookie.saveId.value}">
                <c:set var="temp" value="checked"/>
            </c:if>

            <div class="saveId-area">
                <%-- saveId가 존재한다면 checked속성 적용 >> ${temp} --%>
                <input type="checkbox" name="saveId" id="saveId" ${temp}>
                <label for="saveId">
                    <i class="fas fa-check"></i> 아이디 저장
                </label>
            </div>

            <p class="text-area">
                <a href="/member/signUp">회원가입</a>
                |
                <a href="#">ID/PW 찾기</a>
            </p>
        </form>
    </main>


      <c:if test="${!empty sessionScope.message}">
  	<script>
  		alert("${sessionScope.message}");
  	</script>
  	
  	<%-- message 1회 출력 후 session scope에서 삭제 --%>
  	<c:remove var="message" scope="session"></c:remove>
  </c:if>
  
</body>
</html>