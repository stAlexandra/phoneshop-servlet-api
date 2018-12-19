<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 19.12.2018
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Quick order" pageClass="product-details">
    <form method="post" action="<c:url value="/quickOrder"/>">
        <table>
            <thead>
            <tr>
                <td>Code:</td>
                <td class="number">Quantity</td>
            </tr>
            </thead>

            <c:forEach var="i" begin="0" end="9" varStatus="status">
                <tr>
                    <td>
                        <input name="code" placeholder="Product code"/>
                        <input type="hidden" name="rawIndex" value="${status.index}"/>
                    </td>
                    <td>
                        <input name="quantity"
                               value="${not empty quantityErrors[status.index] ? paramValues["quantity"][i] : 1}"
                               class="number"
                        />
                        <c:if test="${not empty quantityErrors[status.index]}">
                            <p class="error">${quantityErrors[status.index]}</p>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br><br>
        <button>Add to cart</button>
        <c:if test="${not empty param.message}">
            <p class="success">${param.message}</p>
        </c:if>
    </form>
</tags:master>