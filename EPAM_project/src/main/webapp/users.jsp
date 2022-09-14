<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="cruise_company.bandle.text" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css\personal.css">
		<title>Users</title>
	</head>
	<body>
		<div id="opacity"></div>
		<div id="menu_">
			<form style="width:20%;">
	             <select id="language" name="language" onchange="submit()" style="height: 3vh;margin-top:0;width:120%;">
	                 <option value="en" ${language == 'en' ? 'selected' : ''}>Eng</option>
	                 <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Ukr</option>
	             </select>
	         </form>
	         <a href="http://localhost:8080/EPAM_project/account.jsp"><label for="email"><fmt:message key="account" /></label></a>
        	 <a href="http://localhost:8080/EPAM_project/logout"><label for="email"><fmt:message key="logout" /></label></a>
		</div>
		<div id="liners" style="width:30%">
			<h1><label for="email"><fmt:message key="user.title" /></label></h1>
				<table>
					<tr>
						<th><label for="email"><fmt:message key="user.table.email" /></label></th>
					    <th><label for="email"><fmt:message key="user.table.balance" /></label></th>
					    <th><label for="email"><fmt:message key="user.table.button.orders" /></label></th>
					</tr>
					<c:forEach var="temp" items="${users}">
						<tr>
							<td><c:out value="${temp.getEmail()}"/></td>
							<td><c:out value="${temp.getBalance()}"/></td>
					 		<td>
					 			<form action="receipts" method="get">
					 				<fmt:message key="user.table.button.orders" var="orders"/>
					 				<input type="hidden" value="${temp.getId()}" name="id">
					 				<input type="submit" value="${orders}" style="width:100%">
					 			</form>
					 		</td> 
					 		<td>
					 			<form action="promote" method="post">
					 				<fmt:message key="user.table.button.promote" var="promote"/>
					 				<input type="hidden" value="${temp.getId()}" name="id">
					 				<input type="submit" value="${promote}" style="width:100%">
					 			</form>
					 		</td>
						 </tr>
					</c:forEach>
				</table>
				<h:pagination currentPage="${currentPage}" noOfPages="${noOfPages}" recordsPerPage="${recordsPerPage}"/>
		</div>
	</body>
</html>