<%-- 
    Document   : updateProfilePage
    Created on : Feb 26, 2024, 2:27:27 PM
    Author     : Lenovo
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
    </head>
    <body>
             <jsp:include page="/components/profile/updateProfile.jsp" />
        <div  class="max-w-[var(--maxWidth)] w-[95vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500 ">
            <jsp:include page="/components/footer/footer.jsp" />
        </div> 
            <script type="text/javascript"  src="/PrjProject/Javascript/Footer/index.js"></script>
    </body>
</html>
