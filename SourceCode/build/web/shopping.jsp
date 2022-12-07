<%-- 
    Document   : shopping
    Created on : Oct 16, 2022, 8:16:05 PM
    Author     : MSI
--%>

<%--<%@page import="java.util.List"%>
<%@page import="ducdmd.product.ProductDTO"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Page</title>
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

        <h1 style="color: orange">Shopping Page</h1>  


        <%--<c:url var="urlViewYourCart" value="DispatchController">--%>
            <%--<c:param name="btAction" value="View Your Cart"/>--%>
        <%--</c:url>--%> 
        <!--<a href="${urlViewYourCart}">View your cart</a>-->
        <a href="viewCartAction">View your cart</a>

        <c:set var="dtos" value="${requestScope.PRODUCT_LIST}"/>

        <c:if test="${not empty dtos}">
            <table border="1">
                <caption align="center">Product List</caption>
                <thead>
                    <tr>
                        <th>No.</th>
                        <!--<th>SKU</th>-->
                        <th style="color : orange">Name</th>
                        <th>Price</th>
                        <th>Description</th>
                        <th>Quantity</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${dtos}" varStatus="counter">
                        <tr>
                            <td>
                                ${counter.count}
                            </td>
                            <%--<td>
                                ${dto.sku}
                            </td>--%>
                            <td style="font-weight: bold">
                                ${dto.name}                        
                            </td>
                            <td>
                                ${dto.price}$
                            </td>
                            <td>
                                ${dto.description}
                            </td>
                            <td align='center'>
                                ${dto.quantity}
                            </td>

                            <td align='center'>
                                <c:if test="${dto.quantity>0}">
                                    <%--<c:url var="urlRewriting" value="DispatchController">--%>
                                    <c:url var="urlRewriting" value="addToCartAction">
                                        <%--<c:param name="btAction" value="Add Book to Your Cart"/>--%>
                                        <c:param name="cboBook" value="${dto.sku}"/>
                                    </c:url> 
                                    <a href="${urlRewriting}">Add to cart</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty dtos}">
            <h3>
                Products are updating.
                <br>
                You can shopping soon.
                <br>
                Thank you.
            </h3>
        </c:if>        

        <%--    <h1 style="color: orange">Shopping</h1>
            
            <%
                List<ProductDTO> dtos = (List<ProductDTO>)request.getAttribute("PRODUCT_LIST");
                
                if(dtos != null) {
                    %>
                    <table border="1">
                        <caption align="center">Product List</caption>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int count=0;
                                for(ProductDTO dto : dtos) {
                                    String urlAction = "DispatchController"
                                            + "?btAction=Add+Book+to+Your+Cart"
                                            + "&cboBook=" + dto.getSku();
                                    %>
                                    <tr>                                    
                                        <td><%= ++count%></td>
                                        <td><%= dto.getName() %></td>
                                        <td><%= dto.getPrice() %></td>
                                        <td>
                                            <a href="<%= urlAction %>">Add to cart</a>
                                        </td>
                                    </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
            <%
                }
                else {
                    %>
                    <h3>
                        Products are updating.
                        <br>
                        You can shopping soon.
                        <br>
                        Thank you.
                    </h3>
            <%
                }
            %>
            
            <%
                String urlViewYourCart = "DispatchController"
                        + "?btAction=View Your Cart";
            %>
            <a href="<%= urlViewYourCart %>">View your cart</a> --%>
    </body>
</html>
