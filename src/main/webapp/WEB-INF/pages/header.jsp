<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <a href="<c:url value="/products"/>">
        <img src="<c:url value="/images/logo.svg"/>">
        PhoneShop
    </a>
    <c:if test="${param.showMiniCart == true}">
        <jsp:include page="minicart.jsp"/>
    </c:if>
</header>

