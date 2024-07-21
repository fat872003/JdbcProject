<%-- 
    Document   : profilePage
    Created on : Feb 25, 2024, 2:02:35 PM
    Author     : Lenovo
--%>

<%@page import="controllers.CONSTANTS"%>
<%@page import="DTO.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hồ sơ người dùng</title>

        <!-- FontAwesome  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
              integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <!-- googlefont  -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Rubik&display=swap" rel="stylesheet">

        <!-- Tailwind CSS -->
        <script src="https://cdn.tailwindcss.com"></script>


        <!-- css  -->

    </head>
    <body>
        <%
            Account acc = (Account) session.getAttribute("loginUser");
            if (acc.getRole().getRoleID() == 1) {
        %>


        <jsp:include page="/components/navbarsignin/navbarSignin.jsp" /> 
        <jsp:include page="/components/profile/profile.jsp" /> 

        <%} else {%>
        <jsp:include page="/components/profile/profileAdmin.jsp" /> 
        <%}%>
        <div  class="max-w-[var(--maxWidth)] w-[95vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500 ">
            
            <jsp:include page="/components/footer/footer.jsp" />
        </div> 


        <script type="text/javascript" src="/PrjProject/Javascript/Navbar/index.js"></script> <!-- For Navbar -->
        <script type="text/javascript" src="/PrjProject/Javascript/NavbarSignin/index.js"></script>
        <script type="text/javascript"  src="/PrjProject/Javascript/Footer/index.js"></script>
    </body>
</html>
