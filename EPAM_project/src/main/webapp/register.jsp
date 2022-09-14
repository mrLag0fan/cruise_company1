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
		<link rel="stylesheet" href="css\login.css">
		<title>Register</title>
		<style>
			#register {
    			position: fixed; /* or absolute */
   				top: 50%;
    			left: 50%;
    			width:380px;
    			height:360px;
   				transform: translate(-50%, -50%);
   				padding:20px;
   				opacity:0.8;
   				background-color:white;
   				border-radius:10px;
			}
			body{
				font-family: sans-serif;
			}
		</style>
	</head>
	<body>
		<div id="opacity"></div>
		<div id="menu_" style="width:15%;">
			<form>
	             <select id="language" name="language" onchange="submit()">
	                 <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
	                 <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Ukrainian</option>
	             </select>
	             <a href="http://localhost:8080/EPAM_project/account.jsp"><label for="email"><fmt:message key="account" /></label></a>
	        	 <a href="http://localhost:8080/EPAM_project/logout"><label for="email"><fmt:message key="logout" /></label></a>
	         </form>
         </div>
		<div id="register">
			<form action="register" method="post">
				<label for="email"><fmt:message key="register.lable.email" /></label> <br><input type="email" name="email" required  x-moz-errormessage="Please specify a valid email address."><br>
				<label for="email"><fmt:message key="register.lable.password" /></label> <br><input type="password" name="password" maxlength="16"  required><br>
				<span style="font-size:10px;">
					<label for="email"><fmt:message key="register.lable.password.required.digit" /></label><br>
					<label for="email"><fmt:message key="register.lable.password.required.lowercase" /></label><br>
					<label for="email"><fmt:message key="register.lable.password.required.uppercase" /></label><br>
					<label for="email"><fmt:message key="register.lable.password.required.special" /></label><br>
					<label for="email"><fmt:message key="register.lable.password.required.min_max" /></label><br>
				</span>
				<label for="email"><fmt:message key="register.lable.password.repeat" /></label> <br><input type="password" name="repPassword" maxlength="16" required><br><br>
				<fmt:message key="register.button.submit" var="register"/>
				<input type="submit" value="${register}"><br>
			</form>
			<div id="msg"><c:out value="${msg}"/></div>
		</div>
	</body>
</html>