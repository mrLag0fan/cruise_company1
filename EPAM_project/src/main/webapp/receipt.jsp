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
		<title>Receipt</title>
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
			<form action="receiptReg" method="post">
				<h1><label for="email"><fmt:message key="order.title" /></label></h1>
				<table>
					  <tr>
					  	<th><label for="email"><fmt:message key="order.liner.table.choose" /></label></th>
					    <th><label for="email"><fmt:message key="order.liner.table.passenger_capacity" /></label></th>
					    <th><label for="email"><fmt:message key="order.liner.table.visited_ports" /></label></th>
					    <th><label for="email"><fmt:message key="order.liner.table.start_date" /></label></th>
					    <th><label for="email"><fmt:message key="order.liner.table.end_date" /></label></th>
					    <th><label for="email"><fmt:message key="order.liner.table.duration" /></label></th>
					    <th><label for="email"><fmt:message key="order.liner.table.price" /></label></th>
					    <th><label for="email"><fmt:message key="order.liner.table.start" /></label></th>
					    <th><label for="email"><fmt:message key="order.liner.table.end" /></label></th>
					    <th><label for="email"><fmt:message key="order.lable.route" /></label></th>
					  </tr>
					<c:forEach var="liner" items="${liners}">
						 <tr>
						 	<td><input type="radio" value="${liner.getId()}" name="liner_id" required></td>
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
						  		<a href="http://localhost:8080/EPAM_project/linerRoute?id=${liner.getId()}">Route</a>
						  	</td>
						  </tr>
					</c:forEach>
				</table>
				<div id="submit">
					<fmt:message key="order.register.button" var="register"/>
					<input type="submit" value="${register}">
				</div>
				<ul class="pagination">
			            <c:if test="${lcurrentPage != 1}">
			                <li class="page-item">
			                	<form action="receiptReg" method="get">
			                		<input type="hidden" name="lcurrentPage" value="${lcurrentPage-1}">
									<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
									<input type="hidden" name="rcurrentPage" value="${rcurrentPage}">
									<input type="hidden" name="rrecordsPerPage" value="${rrecordsPerPage}">
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
		                        		<form action="receiptReg" method="get">
					                		<input type="hidden" name="lcurrentPage" value="${i}">
											<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
											<input type="hidden" name="rcurrentPage" value="${rcurrentPage}">
											<input type="hidden" name="rrecordsPerPage" value="${rrecordsPerPage}">
											<input type="submit" value="${i}"  style="width:100%;">
		                				</form>
			                    </c:otherwise>
			                </c:choose>
			            </c:forEach>
			
			            <c:if test="${lcurrentPage lt lnoOfPages}">
			                <li class="page-item">
				                <form action="receiptReg" method="get">
			                		<input type="hidden" name="lcurrentPage" value="${lcurrentPage+1}">
									<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
									<input type="hidden" name="rcurrentPage" value="${rcurrentPage}">
									<input type="hidden" name="rrecordsPerPage" value="${rrecordsPerPage}">
									<input type="submit" value=">"  style="width:100%;">
			                	</form>
			                </li>
			            </c:if>
        			</ul>
				<div id="submit"><c:out value="${msg}"/></div><br>
			</form>
				<h1><label for="email"><fmt:message key="order.title2" /></label></h1>
				<table>
					  <tr>
					  	<th><label for="email"><fmt:message key="order.table.price" /></label></th>
					    <th><label for="email"><fmt:message key="order.table.order_status" /></label></th>
					    <th><label for="email"><fmt:message key="order.table.liner" /></label></th>
					  </tr>
					<c:forEach var="receipt" items="${receipts}">
						<tr>
						 	<td><c:out value="${receipt.getPrice()}"/></td>
							<c:forEach var="receipt_status" items="${receipt_statuses}">
								<c:if test="${receipt_status.getId() == receipt.getReceiptStatusId()}">
									<td><c:out value="${receipt_status.getName()}"/></td>
								</c:if>
		    					<c:if test="${receipt_status.getId() == receipt.getReceiptStatusId()-6}">
									<td><c:out value="${receipt_status.getName()}"/></td>
								</c:if>
							</c:forEach>
							<td>
								TO DO LINER
							</td>
							<c:if test="${receipt.getReceiptStatusId() == 9}">
								<td>
									<form action="withdraw " method="post">
										<input type="hidden" value="-${receipt.getPrice()}" name="payment">
										<input type="hidden" value="${receipt.getId()}" name="id">
										<fmt:message key="order.table.button.pay" var="pay"/>
										<input type="submit" value="${pay}" style="width:100%;height:100%">
									</form>
								</td>
							</c:if>
							<td>
								<form action="cancel " method="post">
									<input type="hidden" value="${receipt.getId()}" name="id">
									<fmt:message key="order.table.button.cancel" var="cancel"/>
									<input type="submit" value="${cancel}" style="width:100%;height:100%">
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
				<ul class="pagination">
	            <c:if test="${rcurrentPage != 1}">
	                <li class="page-item">
	                	<form action="receiptReg" method="get">
	                		<input type="hidden" name="rcurrentPage" value="${rcurrentPage-1}">
							<input type="hidden" name="rrecordsPerPage" value="${rrecordsPerPage}">
							<input type="hidden" name="lcurrentPage" value="${lcurrentPage}">
							<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
							<input type="submit" value="<"  style="width:100%;">
	                	</form>
	                </li>
	            </c:if>
	
	            <c:forEach begin="1" end="${rnoOfPages}" var="i">
	                <c:choose>
	                    <c:when test="${rcurrentPage eq i}">
	                        <li class="page-item active">
	                        	<c:out value="${i}"/>
	                        </li>
	                    </c:when>
	                    <c:otherwise>
	                        <li class="page-item">
                        		<form action="receiptReg" method="get">
			                		<input type="hidden" name="rcurrentPage" value="${i}">
									<input type="hidden" name="rrecordsPerPage" value="${rrecordsPerPage}">
									<input type="hidden" name="lcurrentPage" value="${lcurrentPage}">
									<input type="hidden" name="lrecordsPerPage" value="${lrecordsPerPage}">
									<input type="submit" value="${i}"  style="width:100%;">
                				</form>
	                    </c:otherwise>
	                </c:choose>
	            </c:forEach>
	
	            <c:if test="${rcurrentPage lt rnoOfPages}">
	                <li class="page-item">
		                <form action="receiptReg" method="get">
	                		<input type="hidden" name="rcurrentPage" value="${rcurrentPage+1}">
							<input type="hidden" name="rrecordsPerPage" value="${rrecordsPerPage}">
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