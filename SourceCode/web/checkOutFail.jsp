<%-- 
    Document   : checkOutFail
    Created on : Oct 26, 2022, 2:32:25 PM
    Author     : MSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Fail</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.USER}">
            <font color="green">
            Welcome, ${ sessionScope.USER.fullName }
            </font>
            <br>
            <br>
        </c:if>        
        <header style="background: lightgray">
            <c:forEach var="featureNav" items="${sessionScope.FEATURE_NAVS}">
                <a style="padding: 4px 16px" href="${featureNav.value}">${featureNav.key}</a>
            </c:forEach>
        </header>
            
        <h1>Checkout Fail</h1>
        
        <h3>There were some problems while trying to checkout.</h3>
        
        <c:set var="errors" value="${requestScope.CHECKOUT_ERROR}"/>
        <font color="darkyellow">
        ${errors.notEnoughProductForOrder}
        </font>

        <h3>Please view your cart & try to check out again.</h3>
        
        <a href="viewCartAction">Click to back to your cart</a>
        <br>   
    </body>
</html>
