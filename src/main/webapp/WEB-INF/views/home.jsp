<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Recent spittles</title>
</head>
<body>

				<div>
					<a href="<c:url value="/spittles/${spittle.id}" />">Link</a>
				</div>

				<div>
					<a href="<c:url value="/spittles/register" />">Register</a>
				</div>

</body>
</html>