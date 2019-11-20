<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!-- Spring form-binding tag library -->
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ page session="false"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register spittle</title>

</head>
<body>
	<h1>Register</h1>
	<sf:form modelAttribute="spittle" method="POST"
		enctype="multipart/form-data">
		<sf:label path="firstName">First Name : </sf:label>
		<sf:input path="firstName" />
		<br />
		<sf:label path="lastName">Last Name : </sf:label>
		<sf:input path="lastName" />
		<br />
		<sf:label path="username">Username : </sf:label>
		<sf:input path="username" />
		<br />
		<sf:label path="password">Password: </sf:label>
		<sf:password path="password" />
		<br />

		Select profile picture : <sf:input type="file" name="profilePicture"
			path="profilePicture" />
		<input type="submit" value="Register" />
	</sf:form>
</body>
</html>