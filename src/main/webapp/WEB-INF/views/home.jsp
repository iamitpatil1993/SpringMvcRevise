<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<html>
<body>
<h1>Welcome to Spittr</h1>
<h1>Welcome message : <c:out value="${message}"></c:out></h1>
    <a href =  "<c:url value = "/spittles" />" >Spittles</a> <br/>
    <a href =  "<c:url value = "/spittles/register" />" >Register</a>
</body>
</html>
