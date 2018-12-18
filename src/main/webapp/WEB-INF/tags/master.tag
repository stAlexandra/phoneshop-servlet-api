<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="pageTitle" type="java.lang.String" required="true" %>
<%@attribute name="pageClass" type="java.lang.String" required="false" %>
<%@attribute name="showMiniCart" type="java.lang.Boolean" required = "false" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="<c:url value="/styles/main.css"/>">
</head>
<body class="${pageClass}">
<div>
    <jsp:include page="/WEB-INF/pages/header.jsp">
        <jsp:param name="showMiniCart" value="${showMiniCart}"/>
    </jsp:include>
</div>
<main>
    <jsp:doBody/>
</main>
<div>
    <jsp:include page="/WEB-INF/pages/footer.jsp"/>
</div>
</body>
</html>