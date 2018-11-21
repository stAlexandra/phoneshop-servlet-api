<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<div>
    <jsp:include page="/WEB-INF/pages/header.jsp"/>
</div>
<main>
    <h2>Error: product not found!</h2>
</main>
<div>
    <jsp:include page="/WEB-INF/pages/footer.jsp"/>
</div>
</body>
</html>