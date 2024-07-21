<%-- 
    Document   : viewRequestPageNavbar
    Created on : Mar 19, 2024, 12:57:59 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Yêu cầu của tôi</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
              integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <!-- googlefont  -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Rubik&display=swap" rel="stylesheet">

        <!-- Tailwind CSS -->
        <script src="https://cdn.tailwindcss.com"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.2.1/flowbite.min.css" rel="stylesheet" />

        <!-- css  -->
        <link rel="stylesheet" href="utils.css"/>
        <link rel="stylesheet" href="/PrjProject/components/navbarsignin/stylenavbar.css"/>
    </head>
    <body>

        <jsp:include page="/components/navbarsignin/navbarSignin.jsp" /> 
        <jsp:include page="/components/requestPage/requestPage.jsp"/>
        <!--navbar khi login dc--> 
        <div  class="max-w-[var(--maxWidth)] w-[95vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500">



            <%--<jsp:include page="/components/sponsor/sponsor.jsp" />--%>
            <jsp:include page="/components/footer//footer.jsp" />


        </div> 

        <!--Component's JS--> 
        <script type="text/javascript" src="/PrjProject/Javascript/Navbar/index.js"></script> <!-- For Navbar -->
        <script type="text/javascript" src="/PrjProject/Javascript/NavbarSignin/index.js"></script> <!--For NEWS -->
        <script type="text/javascript"  src="/PrjProject/Javascript/Footer/index.js"></script>


    </body>
</html>
