<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<html>
  <head>
    <title>Product List</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="<c:url value="/styles/main.css"/>">
  </head>
  <body class="product-list">
    <div>
        <jsp:include page="header.jsp"/>
    </div>
    <main>
      <p>
        Welcome to Expert-Soft training!
      </p>
      <form>
        <input name = "query" value="${param.query}"/>
        <button>Search</button>
      </form>
      <table>
        <thead>
          <tr>
            <td>Image</td>
            <td>Description
                <a href="
                    <c:url value="/products">
                        <c:param name="query" value="${param.query}"/>
                        <c:param name="sort" value="description"/>
                        <c:param name="order" value="desc"/>
                    </c:url>">
                    <img src="<c:url value="/images/down.png"/>"/>
                </a>
                <a href="
                    <c:url value="/products">
                        <c:param name="query" value="${param.query}"/>
                        <c:param name="sort" value="description"/>
                        <c:param name="order" value="asc"/>
                    </c:url>">
                    <img src="<c:url value="/images/up.png"/>"/>
                </a>
            </td>
            <td class="price">Price
                <a href="
                    <c:url value="/products">
                        <c:param name="query" value="${param.query}"/>
                        <c:param name="sort" value="price"/>
                        <c:param name="order" value="desc"/>
                    </c:url>">
                    <img src="<c:url value="/images/down.png"/>"/>
                </a>
                <a href="
                    <c:url value="/products">
                        <c:param name="query" value="${param.query}"/>
                        <c:param name="sort" value="price"/>
                        <c:param name="order" value="asc"/>
                    </c:url>">
                    <img src="<c:url value="/images/up.png"/>"/>
                </a>
            </td>
          </tr>
        </thead>
        <c:forEach var="product" items="${products}">
          <tr>
            <td>
              <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price">
              <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
          </tr>
        </c:forEach>
      </table>
    </main>
    <div>
        <jsp:include page="footer.jsp"/>
    </div>
  </body>
</html>