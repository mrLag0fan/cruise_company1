<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="cruise_company.bandle.text" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css\login.css">
		<title>Login</title>
	</head>
	<body>
		<div id="opacity"></div>
		<div id="menu_">
			<form>
	             <select id="language" name="language" onchange="submit()">
	                 <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
	                 <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Ukrainian</option>
	             </select>
	         </form>
		</div>
			<div id="login">
				<h2><label for="email"><fmt:message key="login" /></label></h2>
				<form action="login" method="post">
					<input type="email" name="email" required><br>
					<input type="password" name="password" required><br>
					<fmt:message key="login.button.submit" var="login"/>
					<input type="submit" value="${login}"> <br>
				</form><br>
				<form action="register" method="get">
					<fmt:message key="login.register.button.submit" var="register"/>
    				<input type="submit" value="${register }" />
				</form>
				<div id="msg"><c:out value="${msg}"/></div>
			</div>
	</body>
</html>