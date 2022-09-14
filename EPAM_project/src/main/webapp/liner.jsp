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
		<link rel="stylesheet" href="css\liner.css">
		<title>Liner</title>
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
		<div id="liners">
			<h1> <label for="password"><fmt:message key="liner.title" /></label></h1>
			<form action="linerReg" method="post">
				<div id="row1">
					<div class="input">
						<label for="passenger_capacity"><fmt:message key="liner.lablel.passenger_capacity" /></label><br>
						<input type="number" id="passenger_capacity" name="passenger_capacity" min="1" required>
					</div>
					
					<div class="input">
						<label for="date_start"><fmt:message key="liner.lable.start_date" /></label><br>
						<input type="date" id="date_start" name="date_start" value="2022-02-15" min="2022-02-15" required>
					</div>
					
					
					<div class="input">
						<label for="date_end"><fmt:message key="liner.lable.end_date" /></label><br>
						<input type="date" id="date_end" name="date_end" value="2022-02-16" min="2022-02-16" required>
					</div>
					
					
					<div class="input">
						<label for="price"><fmt:message key="liner.lable.price" /></label><br>
						<input type="number" id="price" name="price" min="1" step="0.01" required>
					</div>
				</div>
				
				<div id="row2">
					<div class="input">
						<label for="start"><fmt:message key="liner.lable.start" /></label>
						<select id="start" name="start" required>
							<c:forEach var="port" items="${ports}">
			  					<option value="${port.getId()}"><c:out value="${port.getName()}"/></option>
			  				</c:forEach>
						</select>
					</div>
					
					
					<div class="input">
						<label for="end"><fmt:message key="liner.lable.end" /></label>
						<select id="end" name="end" required>
							<c:forEach var="port" items="${ports}">
			  					<option value="${port.getId()}"><c:out value="${port.getName()}"/></option>
			  				</c:forEach>
						</select>
					</div>
				
				
					<div class="input">
						<label for="route"><fmt:message key="liner.lable.route" /></label><br>
						<select id="reoute" name="routes" multiple required>
							<c:forEach var="route" items="${routes}">
								<option value="${route.getId()}">
									<c:forEach var="port" items="${ports}">
										<c:if test="${route.getFrom() == port.getId()}">
												<c:out value="${port.getName()}"/>
										</c:if>
									</c:forEach>
									⇒
									<c:forEach var="port" items="${ports}">
										<c:if test="${route.getTo() == port.getId()}">
												<c:out value="${port.getName()}"/>
										</c:if>
									</c:forEach>
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div id="submit">
					<fmt:message key="liner.button.create" var="create"/>
					<input type="submit" value="${create}">
				</div>
			</form>
			<hr>
			<div>
				<form  id="filter" action="linerReg" method="get">
					<input type="date" name="f_date_start" required>
					<input type="date" name="f_date_end" required>
					<fmt:message key="liner.button.filter" var="filter"/>
					<input type="submit" value="${filter}">
				</form>
			</div>
			<table>
				  <tr>
				    <th><label for="route"><fmt:message key="liner.table.passenger_capacity" /></label></th>
				    <th><label for="route"><fmt:message key="liner.table.visited_ports" /></label></th>
				    <th><label for="route"><fmt:message key="liner.table.start_date" /></label></th>
				    <th><label for="route"><fmt:message key="liner.table.end_date" /></label></th>
				    <th><label for="route"><fmt:message key="liner.table.duration" /></label></th>
				    <th><label for="route"><fmt:message key="liner.table.price" /></label></th>
				    <th><label for="route"><fmt:message key="liner.table.start" /></label></th>
				    <th><label for="route"><fmt:message key="liner.table.end" /></label></th>
				    <th><label for="route"><fmt:message key="liner.table.route" /></label></th>
				    <th><label for="route"><fmt:message key="liner.table.delete" /></label></th>
				  </tr>
				<c:forEach var="liner" items="${liners}">
					 <tr>
					    <td><c:out value="${liner.getPassengerCapacity()}"/></td>
					    <td><c:out value="${liner.getVisitedPorts()}"/></td>
					    <td><c:out value="${liner.getDateStart()}"/></td>
					    <td><c:out value="${liner.getDateEnd()}"/></td>
					    <td><c:out value="${liner.getDurationInDays()}"/></td>
					    <td><c:out value="${liner.getPrice()}"/></td>
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
						<td>
					  		<form action="linerRoute" method="get">
					  			<input type="hidden" name="id" value="${liner.getId()}">
					  			<fmt:message key="liner.table.button.route" var="route"/>
					  			<input type="submit" value="${route}" style="width:100%;height:100%">
					  		</form>
					  	</td>
					  	<td>
					  		<form action="linerDel" method="post">
					  			<input type="hidden" name="id" value="${liner.getId()}">
					  			<fmt:message key="liner.table.button.delete" var="delete"/>
					  			<input type="submit" value="${delete}" style="width:100%;height:100%">
					  		</form>
					  	</td>
					  </tr>
				</c:forEach>
			</table><br>
			<h:pagination currentPage="${currentPage}" noOfPages="${noOfPages}" recordsPerPage="${recordsPerPage}"/>
		</div>
	</body>
</html>