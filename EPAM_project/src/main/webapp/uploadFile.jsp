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
		<link rel="stylesheet" href="css\uploadFile.css">
		<title>File upload</title>
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
		<div id="files">
			<form action="uploadDocuments" method="post" enctype="multipart/form-data">
				<label for="email"><fmt:message key="upload_file.title" /></label> <input type="file" name="multiPartServlet"  required><br>
				<div id="submit">
					<fmt:message key="upload_file.button" var="upload"/>
					<input type="submit" value="${upload}">
				</div>
			</form>
		</div>
	</body>
</html>