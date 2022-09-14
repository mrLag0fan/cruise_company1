<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="WEB-INF/port_status.tld" prefix="m" %>  
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="cruise_company.bandle.text" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css\port.css">
		<title>Ports</title>
	</head>
	<body>
		<div id="opacity"></div>
		<div id="menu_">
			<form action="portReg" method="get">
	             <select id="language" name="language" onchange="submit()">
	            	 <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Ukrainian</option>
	                 <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
	             </select>
	             <input type="hidden" name="language" value="${language}">
	         </form>
	         <a href="http://localhost:8080/EPAM_project/account.jsp"><label for="email"><fmt:message key="account" /></label></a>
        	 <a href="http://localhost:8080/EPAM_project/logout"><label for="email"><fmt:message key="logout" /></label></a>
		</div>
		<div id="ports">
			<h1> <label for="email"><fmt:message key="port.title" /></label> </h1>
			<form action="portReg" method="post">
				<fmt:message key="port.hint" var="placeholder"/>
				<input type="text" name="name" placeholder="${placeholder}" required><br>
				<c:forEach var="port_status" items="${port_statuses}">
					<input type="radio" id="${port_status.getName()}" name="status" value="${port_status.getId()}" required>
	    			<label for="${port_status.getName()}">${port_status.getName()}</label><br>
				</c:forEach>
				<fmt:message key="port.register.button.submit" var="port"/>
				<input type="submit" value="${port}"><br>
			</form>
			<hr>
			<c:forEach var="port" items="${ports}">
	    		<form action="portDel" method="post"> 
	    			<div id="port">
	    				<label for="email"><fmt:message key="port.lable.name" />:</label> ${port.getName()}<br>
	    				<label for="email"><fmt:message key="port.lable.status" />:</label>
	    				<m:port_name portId="${port.getId()}"/>
	    			</div>
	    			<input type="hidden" name="id" value="${port.getId()}">
	    			<fmt:message key="port.delete.button.submit" var="delete"/>
	    			<input type="submit" value="${delete}">
	    		</form>
	    		<form action="portUpdate" method="post">
   					<input type="hidden" name="id" value="${port.getId()}">
   					<select name="status" id="select">
   						<c:forEach var="port_status" items="${port_statuses}">
							<option value="${port_status.getId()}"><c:out value="${port_status.getName()}"/></option>
						</c:forEach>
   					</select>
   					<fmt:message key="port.update.button.submit" var="update"/>
   					<input type="submit" value="${update}">
   				</form>
			</c:forEach>
			<h:pagination currentPage="${currentPage}" noOfPages="${noOfPages}" recordsPerPage="${recordsPerPage}"/>
		</div>
	</body>
</html>