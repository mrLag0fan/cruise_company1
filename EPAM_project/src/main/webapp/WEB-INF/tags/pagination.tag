<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="currentPage" required="true" rtexprvalue="true" %>
<%@ attribute name="noOfPages" required="true" rtexprvalue="true" %>
<%@ attribute name="recordsPerPage" required="true" rtexprvalue="true" %>
<ul class="pagination">
	<c:if test="${currentPage != 1}">
		<li class="page-item">
			<form action="portReg" method="get">
				<input type="hidden" name="currentPage" value="${currentPage-1}">
				<input type="hidden" name="recordsPerPage" value="${recordsPerPage}">
				<input type="submit" value="<">
			</form>
		</li>
	</c:if>

	<c:forEach begin="1" end="${noOfPages}" var="i">
		<c:choose>
			<c:when test="${currentPage eq i}">
				<li class="page-item active">
					<c:out value="${i}"/>
			    </li>
			</c:when>
			<c:otherwise>
		   		<li class="page-item">
			   		<form action="portReg" method="get">
						<input type="hidden" name="currentPage" value="${i}">
						<input type="hidden" name="recordsPerPage" value="${recordsPerPage}">
						<input type="submit" value="${i}"  style="width:100%;">
					</form>
				</li>
	        </c:otherwise>
	    </c:choose>
	</c:forEach>

	<c:if test="${currentPage lt noOfPages}">
		<li class="page-item">
		 	<form action="portReg" method="get">
				<input type="hidden" name="currentPage" value="${currentPage+1}">
				<input type="hidden" name="recordsPerPage" value="${recordsPerPage}">
				<input type="submit" value=">"  style="width:100%;">
        	</form>
        </li>
    </c:if>
</ul>