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
		<link rel="stylesheet" href="css\personal.css">
		<title>Personal</title>
	</head>
	<body>
		<div id="opacity"></div>
		<div id="menu_">
			<form style="width:20%;">
	             <select id="language" name="language" onchange="submit()" style="height: 3vh;margin-top:0;width:120%;">
	                 <option value="en" ${language == 'en' ? 'selected' : ''}>Eng</option>
	                 <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Ukr</option>
	             </select>
	             <input type="hidden" name="language" value="${language}">
	         </form>
	         <a href="http://localhost:8080/EPAM_project/account.jsp"><label for="email"><fmt:message key="account" /></label></a>
        	 <a href="http://localhost:8080/EPAM_project/logout"><label for="email"><fmt:message key="logout" /></label></a>
		</div>
		<div id="liners">
			<h1> <label for="email"><fmt:message key="personal.title" /></label> </h1>
			<form action="personalReg" method="post">
				<div id="row1">
					<div class="input">
						<label for="name"><fmt:message key="personal.lable.name" /></label>
						<input name="name" id="name" required>
					</div>
					<div class="input">
						<label for="surname"><fmt:message key="personal.lable.surname" /></label>
						<input name="surname" id="surname" required>
					</div>
					<div class="input">
						<label for="experience"><fmt:message key="personal.lable.experience" /></label>
						<input type="number" name="experience" id="experience" required>
					</div>
				</div>
				
				<div id="row2">
					<div class="input">
						<label for=phone><fmt:message key="personal.lable.phone" /></label>
						<input id="phone" name="phone">
					</div>
					
					<div class="input">
						<label for="personal_role_id"><fmt:message key="personal.lable.personal_role" /></label>
						<select id="personal_role_id" name="personal_role_id" required>
							<c:forEach var="personal_role" items="${personal_roles}">
			  					<option value="${personal_role.getId()}"><c:out value="${personal_role.getName()}"/></option>
			  				</c:forEach>
						</select>
					</div>
				</div>
				<table>
					<tr>
						<th><label for="personal_role_id"><fmt:message key="personal.liner.table.choose" /></label></th>
					    <th><label for="personal_role_id"><fmt:message key="personal.liner.table.passenger_capacity" /></label></th>
					    <th><label for="personal_role_id"><fmt:message key="personal.liner.table.start_date" /></label></th>
					    <th><label for="personal_role_id"><fmt:message key="personal.liner.table.end_date" /></label></th>
					    <th><label for="personal_role_id"><fmt:message key="personal.liner.table.duration" /></label></th>
					    <th><label for="personal_role_id"><fmt:message key="personal.liner.table.start" /></label></th>
					    <th><label for="personal_role_id"><fmt:message key="personal.liner.table.end" /></label></th>
				  	</tr>
					<c:forEach var="liner" items="${liners}">
					 <tr>
					 	<td><input type="radio" value="${liner.getId()}" name="liner_id" required></td>
					    <td><c:out value="${liner.getPassengerCapacity()}"/></td>
					    <td><c:out value="${liner.getDateStart()}"/></td>
					    <td><c:out value="${liner.getDateEnd()}"/></td>
					    <td><c:out value="${liner.getDurationInDays()}"/></td>
					    <c:forEach var="port" items="${ports}">
							<c:if test="${liner.getStart() == port.getId()}">
									<td><c:out value="${port.getName()}"/></td>
							</c:if>
						</c:forEach>
					    <c:forEach var="port" items="${ports}">
							<c:if test="${liner.getEnd() == port.getId()}">
									<td><c:out value="${port.getName()}"/></td>
							</c:if>
						</c:forEach>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${liners.size() > 0}">
					<div id="submit">
				  		<fmt:message key="personal.register.button" var="register"/>
						<input type="submit" value="${register}">
					</div>
				</c:if>
			</form>
			<c:if test="${liners.size() <= 0}">
					<form action="linerReg" method="get">
						<div id="submit">
					  		<fmt:message key="personal.register.liner.button" var="register"/>
							<input type="submit" value="${register}">
						</div>
					</form>
			</c:if>
			<ul class="pagination">
	            <c:if test="${lcurrentPage != 1}">
	                <li class="page-item">
	                	<form action="personalReg" method="get">
	                		<input type="hidden" name="lcurrentPage" value="${lcurrentPage-1}">
							<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
							<input type="hidden" name="pcurrentPage" value="${pcurrentPage}">
							<input type="hidden" name="precordsPerPage" value="${precordsPerPage}">
							<input type="submit" value="<"  style="width:100%;">
	                	</form>
	                </li>
	            </c:if>
	
	            <c:forEach begin="1" end="${lnoOfPages}" var="i">
	                <c:choose>
	                    <c:when test="${lcurrentPage eq i}">
	                        <li class="page-item active">
	                        	<c:out value="${i}"/>
	                        </li>
	                    </c:when>
	                    <c:otherwise>
	                        <li class="page-item">
                        		<form action="personalReg" method="get">
			                		<input type="hidden" name="lcurrentPage" value="${i}">
									<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
									<input type="hidden" name="pcurrentPage" value="${pcurrentPage}">
									<input type="hidden" name="precordsPerPage" value="${precordsPerPage}">
									<input type="submit" value="${i}"  style="width:100%;">
                				</form>
	                    </c:otherwise>
	                </c:choose>
	            </c:forEach>
	
	            <c:if test="${lcurrentPage lt lnoOfPages}">
	                <li class="page-item">
		                <form action="personalReg" method="get">
	                		<input type="hidden" name="lcurrentPage" value="${lcurrentPage+1}">
							<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
							<input type="hidden" name="pcurrentPage" value="${pcurrentPage}">
							<input type="hidden" name="precordsPerPage" value="${precordsPerPage}">
							<input type="submit" value=">"  style="width:100%;">
	                	</form>
	                </li>
	            </c:if>
        	</ul>
			<table>
				<tr>
					<th><label for="personal_role_id"><fmt:message key="personal.table.name" /></label></th>
					<th><label for="personal_role_id"><fmt:message key="personal.table.surname" /></label></th>
					<th><label for="personal_role_id"><fmt:message key="personal.table.experience" /></label></th>
					<th><label for="personal_role_id"><fmt:message key="personal.table.phone" /></label></th>
					<th><label for="personal_role_id"><fmt:message key="personal.table.personal_role" /></label></th>
				</tr>
					<c:forEach var="personal" items="${personal}">
						<tr>
				    		<td><c:out value="${personal.getName()}"/></td>
							<td><c:out value="${personal.getSurname()}"/></td>
							<td><c:out value="${personal.getExperience()}"/></td>
							<td><c:out value="${personal.getPhone()}"/></td>
							<c:forEach var="personal_role" items="${personal_roles}">
								<c:if test="${personal.getPersonalRoleId() == personal_role.getId()}">
									<td><c:out value="${personal_role.getName()}"/></td>
								</c:if>
		    					<c:if test="${personal.getPersonalRoleId()+2 == personal_role.getId()}">
									<td><c:out value="${personal_role.getName()}"/></td>
								</c:if>
							</c:forEach>
							<td>
								<form action="personalDel" method="post">
									<fmt:message key="personal.table.delete" var="delete"/>
									<input type="hidden" name="id" value="${personal.getId()}">
									<input type="submit" value="${delete}"  style="width:100%; height:2vh;">
								</form>
							</td>
						</tr>
			    	</c:forEach>
	    	</table>
	    	<ul class="pagination">
	            <c:if test="${pcurrentPage != 1}">
	                <li class="page-item">
	                	<form action="personalReg" method="get">
	                		<input type="hidden" name="pcurrentPage" value="${pcurrentPage-1}">
							<input type="hidden" name="precordsPerPage" value="${precordsPerPage}">
							<input type="hidden" name="lcurrentPage" value="${lcurrentPage}">
							<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
							<input type="submit" value="<"  style="width:100%;">
	                	</form>
	                </li>
	            </c:if>
	
	            <c:forEach begin="1" end="${pnoOfPages}" var="i">
	                <c:choose>
	                    <c:when test="${pcurrentPage eq i}">
	                        <li class="page-item active">
	                        	<c:out value="${i}"/>
	                        </li>
	                    </c:when>
	                    <c:otherwise>
	                        <li class="page-item">
                        		<form action="personalReg" method="get">
			                		<input type="hidden" name="pcurrentPage" value="${i}">
									<input type="hidden" name="precordsPerPage" value="${precordsPerPage}">
									<input type="hidden" name="lcurrentPage" value="${lcurrentPage}">
									<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
									<input type="submit" value="${i}"  style="width:100%;">
                				</form>
	                    </c:otherwise>
	                </c:choose>
	            </c:forEach>
	
	            <c:if test="${pcurrentPage lt pnoOfPages}">
	                <li class="page-item">
		                <form action="personalReg" method="get">
	                		<input type="hidden" name="pcurrentPage" value="${pcurrentPage+1}">
							<input type="hidden" name="precordsPerPage" value="${precordsPerPage}">
							<input type="hidden" name="lcurrentPage" value="${lcurrentPage}">
							<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
							<input type="submit" value=">"  style="width:100%;">
	                	</form>
	                </li>
	            </c:if>
        	</ul>
    	</div>
	</body>	
</html>