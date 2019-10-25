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
<c:forEach items="${spittles}" var="spittle">
		<li id="spittle_<c:out value="spittle.id"/>">
			<div>
				<c:out value="${spittle.message}" />
			</div>
			<div>
				<div>
					<a href="<c:url value="/spittles/${spittle.id}" />">Link</a>
				</div>

	</c:forEach>
</body>
</html>