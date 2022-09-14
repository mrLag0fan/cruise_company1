<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ERROR 401</title>
		<link rel="stylesheet" href="css\login.css">
	</head>
	<body>
		<div id="opacity"></div>
		<div id="error">
			<span style="color:red;">ERROR 403</span><c:out value="${msg}"/>
		</div>
	</body>
</html>