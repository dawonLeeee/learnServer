<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%-- 
    prefix : 접두사
   uri(Uniform Resource Identifier) : 자원을 구분하는 식별자 (주소 형태)
                   
 --%>
 
 <!DOCTYPE html>
 <html lang="ko">
 <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>selectAllStudent</title>
    

 </head>
 <body>
 	<table border="1">
 		<tr>
 			<th>학번</th>
 			<th>이름</th>
 			<th>학과</th>
 			<th>주소</th>
 		</tr>
 		
 		<c:forEach var="student" items="${stdList}">
 			<tr>
 				<td>${student.getStudentNo()}</td>
 				<td>${student.getStudentName()}</td>
 				<td>${student.getDepartmentName()}</td>
 				<td>${student.getStudentAddress()}</td>
 			</tr>
 		</c:forEach>
 	
 	</table>
 


</body>
</html>