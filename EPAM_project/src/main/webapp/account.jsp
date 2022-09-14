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
		<title>Account</title>
		<link rel="stylesheet" href="css\login.css">
		<link rel="stylesheet" href="css\account.css">
		<style type="text/css">
		</style>
	</head>
	<body>
		
		<div id="opacity"></div>
		<div id="menu">
			<form>
	             <select id="language" name="language" onchange="submit()">
	                 <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
	                 <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Ukrainian</option>
	             </select>
        	 </form>
        	 <a href="http://localhost:8080/EPAM_project/account.jsp"><label for="email"><fmt:message key="account" /></label></a>
        	 <a href="http://localhost:8080/EPAM_project/logout"><label for="email"><fmt:message key="logout" /></label></a>
		</div>
		<div id="account">
			<img alt="avatar" src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/OOjs_UI_icon_userAvatar.svg/1200px-OOjs_UI_icon_userAvatar.svg.png" width="50px" height="50px" align="center"><br>
			<label for="email"><fmt:message key="account.email" /></label><c:out value="${user.getEmail()}"/>
			<div>
				<label for="password"><fmt:message key="account.balance" />:</label><c:out value="${user.getBalance()}"/>$
				<form action="balance" method="get">
					<fmt:message key="account.payment.button.submit" var="payment"/>
		    		<input type="submit" value="${payment}"></input>
				</form>
			</div>
			<div id="buttons">
				<c:if test="${user.getUserRoleId() == 1}">
					<form action="portReg" method="get">
						<fmt:message key="account.port.button.submit" var="port"/>
	    				<input type="submit" value="${port}"></input>
					</form>
					<form action="routeReg" method="get">
					
						<fmt:message key="account.route.button.submit" var="route"/>
	    				<input type="submit" value="${route}" />
					</form>
					<form action="linerReg" method="get">
						<fmt:message key="account.liner.button.submit" var="liner"/>
						<input type="hidden" name="currentPage" value="1">
						<input type="hidden" name="recordsPerPage" value="10">
	    				<input type="submit" value="${liner}" />
					</form>
					<form action="personalReg" method="get">
						<fmt:message key="account.personal.button.submit" var="personal"/>
	    				<input type="submit" value="${personal}" />
					</form>
				</c:if>
			</div>
			<div id="buttons">
				<form action="receiptReg" method="get">
					<fmt:message key="account.order.button.submit" var="order"/>
		    		<input type="submit" value="${order}" />
				</form>
				<c:if test="${user.getUserRoleId() == 1}">
					<form action="users" method="get">
						<fmt:message key="account.users.button.submit" var="users"/>
			    		<input type="submit" value="${users}" />
					</form>
				</c:if>
			</div>
		</div>
	</body>	
</html>