<%-- 
    Document   : search
    Created on : Oct 4, 2022, 8:14:12 AM
    Author     : MSI
--%>

<%-- <%@page import="ducdmd.registration.RegistrationDTO"%>
<%@page import="java.util.List"%> --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
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

        <h1>Search Page</h1>

        <!--<form action="DispatchController" method="POST">-->
        <form action="searchLastnameAction" method="POST">
            Search Value
            <input type="text" name="txtSearchValue"
                   value="${param.txtSearchValue}" />

            <input type="submit" value="Search" name="btAction" />
        </form><br>

        <c:set var="searchValue" value="${param.txtSearchValue}"/>

        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>                            
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                        <!--<form action="DispatchController" method="POST">-->                        
                        <form action="updateAccountAction" method="POST">                        
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername" value="${dto.username}" />
                                </td>
                                <td>
                                    <!--<input type="text" name="txtPassword" value="${dto.password}" />--> 
                                    <input type="text" name="txtPassword" value="" /> 
                                </td>
                                <td>
                                    ${dto.fullName}
                                </td>
                                <td>                                    
                                    <input type="checkbox" name="isAdmin" value="ON"
                                           ${dto.role ? "checked" : ""}
                                           ${dto.username==sessionScope.USER.username || dto.role==true? "onclick='return false;'" : ""}
                                           />
                                </td>
                                <td>
                                    <c:if test="${dto.username!=sessionScope.USER.username && dto.role!=true}">                                        
                                        <%--<c:url var="urlRewriting" value="DispatchController">--%>
                                        <c:url var="urlRewriting" value="deleteAccounAction">
                                            <%--<c:param name="btAction" value="delete"/>--%>
                                            <c:param name="pk" value="${dto.username}"/>
                                            <c:param name="lastSearchValue" value="${searchValue}"/>
                                        </c:url>                                    
                                        <a href="${urlRewriting}">Delete</a>
                                    </c:if>
                                </td>

                                <td>
                                    <input type="hidden" name="lastSearchValue"
                                           value="${searchValue}" />
                                    <input type="submit" value="Update" name="btAction" />
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>

        </c:if>

        <c:if test="${empty result}">
            <h2>
                No record is matched!!!
            </h2>
        </c:if>
    </c:if>
    <%-- <font color="green">
        <%
            String username = null;
            Cookie[] cookies = request.getCookies();
            if(cookies != null ) {
                Cookie lastCookie = cookies[cookies.length-1];
//                    username = lastCookie.getName();
                username = (String) request.getAttribute("username");
                %>
                Welcome, <%= username %>
        <%                    
            }
        %>
        
    </font>
    <h1>Search Page</h1>
    
    <%
        String searchValue = request.getParameter("txtSearchValue");
    %>
    
    <form action="DispatchController" method="POST">
        Search Value
        <input type="text" name="txtSearchValue"
               value="<%= request.getParameter("txtSearchValue")!=null ? request.getParameter("txtSearchValue") : "" %>" />
        
        <input type="submit" value="Search" name="btAction" />
    </form>
    
    <br>
    
    <%
        
        if(searchValue != null) {
            List<RegistrationDTO> result =
                    (List<RegistrationDTO>) request.getAttribute("SEARCH_RESULT");
                    /// ep kieu tuong minh
                    
            if(result!=null) {
                %>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                            
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int count = 0;
                            for(RegistrationDTO dto : result) {
                                String urlRewriting = "DispatchController"
                                        + "?btAction=delete"
                                        + "&pk=" + dto.getUsername()
                                        + "&lastSearchValue=" + searchValue;
                                %>
                    <form action="DispatchController" method="POST">    
                                <tr>
                                    <td>
                                        <%= ++count %>
                                    .</td>
                                    <td>
                                        <%= dto.getUsername() %>
                                        <input type="hidden" name="txtUsername" value="<%=dto.getUsername()%>" />
                                    </td>
                                    <td>
                                        <input type="text" name="txtPassword" value="<%= dto.getPassword() %>" />
                                    </td>
                                    <td>
                                        <%= dto.getFullName() %>
                                    </td>
                                    <td>
                                        <input type="checkbox" name="isAdmin" value="ON" 
                                               <%
                                                   if(dto.isRole()) {
                                                       %>
                                                       checked="checked"
                                               <%
                                                   } /// emnd admin is true
                                               %>
                                               />
                                    </td>
                                    
                                    <td>
                                        <a href="<%= urlRewriting %>"
                                                <%
                                                   if(dto.getUsername().equals(username)) {
                                                       %>
                                                       disabled
                                                       style="color: gray; cursor: default;"
                                               <%
                                                   } // can not delete your seft
                                               %>
                                           >Delete</a>
                                    </td>
                                    
                                    <td>
                                        <input type="hidden" name="lastSearchValue"
                                               value="<%= searchValue %>" />
                                        <input type="submit" value="Update" name="btAction" />
                                    </td>
                                </tr>
                    </form>            
                        <%
                            } // end traverse result DTO list
                        %> 
                    </tbody>
                </table>

        <%
                }
                else { // no record
                    %>
                    <h2>
                        No record is matched!!!
                    </h2>
        <%
                }
                        
            } /// end search Value is valid and had already heen sent
        %> --%>
</body>
</html>
