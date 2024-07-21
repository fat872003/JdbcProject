    <%@page import="controllers.CONSTANTS"%>
<%@page import="DTO.Account"%>
<%-- 
    Document   : index
    Created on : Feb 1, 2024, 4:36:17 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

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
        <link href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.2.1/flowbite.min.css" rel="stylesheet" />

        <!-- css  -->
        <link rel="stylesheet" href="utils.css"/>
        <link rel="stylesheet" href="/PrjProject/components/navbarsignin/stylenavbar.css"/>
    </head>
    <body>
     
 <jsp:include page="/components/navbarsignin/navbarSignin.jsp" /> 
    <!--navbar khi login dc--> 
        <div  class="max-w-[var(--maxWidth)] w-[95vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500">
           
            <jsp:include page="/components/carousel/carousel.jsp" />
            <jsp:include page="/components/navigate/navigate.jsp" />
            <jsp:include page="/components/news/news.jsp" />
            <jsp:include page="/components/countUp/countUp.jsp" />
            <%--<jsp:include page="/components/sponsor/sponsor.jsp" />--%>
            <jsp:include page="/components/footer//footer.jsp" />




        </div> 


        <!--JQuery/CDN--> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.7.2/jquery.min.js"
                integrity="sha512-poSrvjfoBHxVw5Q2awEsya5daC0p00C8SKN74aVJrs7XLeZAi+3+13ahRhHm8zdAFbI2+/SUIrKYLvGBJf9H3A=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!--   Waypoint-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/4.0.1/jquery.waypoints.js"
                integrity="sha512-ZKNVEa7gi0Dz4Rq9jXcySgcPiK+5f01CqW+ZoKLLKr9VMXuCsw3RjWiv8ZpIOa0hxO79np7Ec8DDWALM0bDOaQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!--    flowbite-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.2.1/flowbite.min.js"></script>

        <!--LIBRARY-->         
        <!--    Countup (use for number countup)-->
        <script src="lib/countUp/jquery.countup.js"></script>
        <!--    custom countup number -->
        <script>
            $('.counter').countUp({
                'time': 3000,
                'delay': 10
            })
        </script>

        <!--    slick  -->
        <script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
        <script type="text/javascript" src="lib/slick/slick.min.js"></script>
        <script type="text/javascript">
            $('.responsive').slick({
                infinite: false,
                autoplay: true,
                speed: 300,
                slidesToShow: 5,
                slidesToScroll: 3,
                responsive: [
                    {
                        breakpoint: 1024,
                        settings: {
                            slidesToShow: 4,
                            slidesToScroll: 2,
                            infinite: true,
                            dots: true
                        }
                    },
                ]
            });
        </script>


        <!--Component's JS--> 
        <script type="text/javascript" src="/PrjProject/Javascript/Navbar/index.js"></script> <!-- For Navbar -->
        <script type="text/javascript" src="/PrjProject/Javascript/NavbarSignin/index.js"></script>
        <script type="text/javascript"  src="/PrjProject/Javascript/News/index.js"></script> <!--For NEWS -->
        <script type="text/javascript"  src="/PrjProject/Javascript/Footer/index.js"></script>


    </body>
</html>
<%--<%@include file="news.jsp" %>--%>
