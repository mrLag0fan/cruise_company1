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
		<div id="liners" style="width:60%">
			<h1><label for="email"><fmt:message key="user_orders.title" /></label></h1>
				<table>
					  <tr>
					  	<th><label for="email"><fmt:message key="user_orders.table.documents" /></label></th>
					    <th><label for="email"><fmt:message key="user_orders.table.price" /></label></th>
					    <th><label for="email"><fmt:message key="user_orders.table.status" /></label></th>
					  </tr>
					<c:forEach var="receipt" items="${receipts}">
						 <tr>
						 	<td><c:out value="${receipt.getDocuments()}"/></td>
						 	<td><c:out value="${receipt.getPrice()}"/></td>
						 	<c:forEach var="receipt_status" items="${receipt_statuses}">
									<c:if test="${receipt.getReceiptStatusId() == receipt_status.getId()}">
										<td><c:out value="${receipt_status.getName()}"/></td>
									</c:if>
							</c:forEach>
						 	<td>
						 		<form action="confirm" method="post">
						 			<input type="hidden" value="${receipt.getId()}" name="id">
						 			<fmt:message key="user_orders.button.confirm" var="confirm"/>
						 			<input type="submit" value="${confirm}" style="width:100%">
						 		</form>
						 	</td> 
						 </tr>
					</c:forEach>
				</table>
				<h:pagination currentPage="${currentPage}" noOfPages="${noOfPages}" recordsPerPage="${recordsPerPage}"/>
		</div>
	</body>
</html>