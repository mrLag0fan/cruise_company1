<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="cruise_company.bandle.text" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Balance</title>
		<link rel="stylesheet" href="css\login.css">
	</head>
	<body>
		<div id="opacity"></div>
		<div id="menu_" style="width:30vh;">
			<form>
	             <select id="language" name="language" onchange="submit()">
	                 <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
	                 <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Ukrainian</option>
	             </select>
	         </form>
	         <a href="http://localhost:8080/EPAM_project/account.jsp"><label for="email"><fmt:message key="account" /></label></a>
        	 <a href="http://localhost:8080/EPAM_project/logout"><label for="email"><fmt:message key="logout" /></label></a>
		</div>
		<div id="login" style="height:20%;">
		<h3><label for="password"><fmt:message key="payment.title" />:</label></h3>
			<form action="balance" method="post">
				<input type="number" step=0.01 name="payment">
				<fmt:message key="payment.button.submit" var="payment"/>
				<input type="submit" value="${payment}">
			</form>
		</div>
	</body>
</html>