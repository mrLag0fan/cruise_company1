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
		<link rel="stylesheet" href="css\login.css">
		<link rel="stylesheet" href="css\route.css">
		<title>Routes</title>
	</head>
	<body>
		<div id="opacity"></div> 
		<div id="menu_" style="width:15%;">
			<form>
	             <select id="language" name="language" onchange="submit()" style="height:3vh;margin-top: 0;">
	                 <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
	                 <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Ukrainian</option>
	             </select>
	         </form>
	         <a href="http://localhost:8080/EPAM_project/account.jsp"><label for="email"><fmt:message key="account" /></label></a>
        	 <a href="http://localhost:8080/EPAM_project/logout"><label for="email"><fmt:message key="logout" /></label></a>
		</div>
		<div id="routes">
			<h1> <label for="email"><fmt:message key="route.title" /></label> </h1>
			<form action="routeReg" method="post">
			<div id="select">
				<div id="start">
					<label for="from" id="l_from"><fmt:message key="route.lable.start" /></label>
					<select id="from" name="from">
						<c:forEach var="port" items="${ports}">
		  					<option value="${port.getId()}"><c:out value="${port.getName()}"/></option>
		  				</c:forEach>
					</select>
				</div>
				<span id="arow">⇒</span>
				<div id="end">
					<label for="to" id="l_to"><fmt:message key="route.lable.end" /></label><br>
					<select id="to" name="to">
						<c:forEach var="port" items="${ports}">
		  					<option value="${port.getId()}"><c:out value="${port.getName()}"/></option>
		  				</c:forEach>
					</select>
				</div>
			</div>
				<fmt:message key="route.register.button.submit" var="create"/>
				<input type="submit" value="${create}"><br>
			</form>
			<c:out value="${msg}"/>
			<div id="res">
				<c:forEach var="route" items="${routes}">
				<form action="routeDel" method="post">
	    			<div id="data">
		    			<c:forEach var="port" items="${ports}">
		    				<c:if test="${port.getId() == route.getFrom()}">
									<c:out value="${port.getName()}"/>
							</c:if>
		    			</c:forEach>
		    			<span id="arow">⇒</span> 
		    			<c:forEach var="port" items="${ports}">
		    				<c:if test="${port.getId() == route.getTo()}">
									<c:out value="${port.getName()}"/>
							</c:if>
		    			</c:forEach>
	    			</div>
	    			<fmt:message key="route.delete.button.submit" var="delete"/>
	    			<input type="hidden" name="id" value="${route.getId()}">
	    			<input type="submit" value="${delete}">
	    		</form>
			</c:forEach>
			</div>
			<h:pagination currentPage="${currentPage}" noOfPages="${noOfPages}" recordsPerPage="${recordsPerPage}"/>
		</div>
	</body>
</html>