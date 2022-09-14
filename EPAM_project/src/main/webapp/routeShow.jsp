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
		<link rel="stylesheet" href="css\port.css">
		<title>Route</title>
		<style>
			#routes{
				position: fixed; /* or absolute */
			    top: 50%;
			    left: 50%;
				transform: translate(-50%, -50%);
			   	padding:20px;
			   	opacity:0.8;
			   	background-color:white;
				width:90%;
				height:20%;
				display:flex;
				justify-content: space-around;
				align-items:center;
				border-radius:10px;
			}
			.route{
				display:flex;
				color:#49977F;
				font-size: 25px;
			}
			#arrow{
				font-size: 25px;
			}
		</style>
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
		<div id="routes">
			<ul class="pagination">
	            <c:if test="${currentPage > 1 }">
	                <li class="page-item">
	                	<form action="linerRoute" method="get">
	                		<input type="hidden" name="id" value="${id}">
	                		<input type="hidden" name="currentPage" value="${currentPage-1}">
							<input type="hidden" name="recordsPerPage" value="${recordsPerPage}">
							<input type="submit" value="<"  style="width:100%;">
	                	</form>
	                </li>
	            </c:if>
	    	</ul>
			<c:forEach var="route" items="${routes}">
				<div class="route">
					<c:forEach var="port" items="${ports}">
	    				<c:if test="${port.getId() == route.getFrom()}">
								<c:out value="${port.getName()}"/>
						</c:if>
	    			</c:forEach>
				</div>
				<div id="arrow">⇒</div> 
			</c:forEach>
			<div class="route">
				<c:forEach var="port" items="${ports}">
    				<c:if test="${port.getId() == end}">
							<c:out value="${port.getName()}"/>
					</c:if>
    			</c:forEach>
			</div>
			<ul class="pagination">
	            <c:if test="${currentPage lt noOfPages}">
	                <li class="page-item">
		                <form action="linerRoute" method="get">
		                	<input type="hidden" name="id" value="${id}">
	                		<input type="hidden" name="currentPage" value="${currentPage+1}">
							<input type="hidden" name="recordsPerPage" value="${recordsPerPage}">
							<input type="submit" value=">"  style="width:100%;">
	                	</form>
	                </li>
	            </c:if>
        	</ul>
		</div>
	</body>
</html>