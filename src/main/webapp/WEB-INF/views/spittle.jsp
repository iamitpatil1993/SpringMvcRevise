<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spittle</title>
</head>
<body>
	<div>
				<c:out value="${spittle.id}" />
			</div>
			<div>
				<c:out value="${spittle.message}" />
			</div>



</body>
</html>