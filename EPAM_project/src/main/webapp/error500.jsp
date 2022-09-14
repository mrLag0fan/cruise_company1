<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" href="css\login.css">
	</head>
	<body>
		<div id="opacity"></div>
		<div id="error">
			<span style="color:red;">ERROR 500</span><c:out value="${msg}"/>
		</div>
	</body>
</html>