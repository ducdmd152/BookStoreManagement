<%-- 
    Document   : register
    Created on : Oct 25, 2022, 7:33:17 AM
    Author     : MSI
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Register</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Create new account</h1>
        
        <!--<form action="DispatchController" method="POST">-->
        <form action="registerAction" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERRORS}" />
            
            Username*
            <input type="text" name="txtUsername"
                   value="${param.txtUsername}" />
            (6 - 20 chars)
            <br>
            <c:if test="${not empty errors.usernameLengthError}">
                <font color="red" style="font-style: italic">
                ${errors.usernameLengthError}
                </font>                
            </c:if>
            <c:if test="${not empty errors.usernameIsExisted}">
                <font color="red" style="font-style: italic">
                ${errors.usernameIsExisted}
                </font>                
            </c:if>
                
            <br>
            Password*
            <input type="password" name="txtPassword" value="" />
            (8 - 20 chars)
            <br>
            <c:if test="${not empty errors.passwordLengthError}">
                <font color="red" style="font-style: italic">
                ${errors.passwordLengthError}
                </font>                
            </c:if>
                
            <br>
            Confirm*
            <input type="password" name="txtConfirm" value="" />                  
            <br>
            <c:if test="${not empty errors.confirmNotMatched}">
                <font color="red" style="font-style: italic">
                ${errors.confirmNotMatched}
                </font>                
            </c:if>
            
            <br>
            Full name*
            <input type="text" name="txtFullname"
                   value="${param.txtFullname}" />
            (2-40 chars)
            <br>
            <c:if test="${not empty errors.fullNameLengthError}">
                <font color="red" style="font-style: italic">
                ${errors.fullNameLengthError}
                </font>                
            </c:if>
            
            <br>
            <input type="submit" value="Register" name="btAction"/>            
            <input type="reset" value="Reset" />
        </form>
                
        <!--You already have account? <a href="login.html">Login here</a>-->
        You already have account? <a href="loginPage">Login here</a>
        
        
    </body>
</html>