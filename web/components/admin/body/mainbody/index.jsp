<%-- 
    Document   : index.jsp
    Created on : Feb 12, 2024, 3:54:15 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="main" class="w-[calc(100vw-200px)] ml-[200px]">
            <%
                String param = (String) request.getParameter("sec");
                if (param == null)
                {
                    param = "1";
                }
                switch (param)
                {
                    case "1":
            %> <jsp:include page="/components/admin/body/mainbody/ProductBody/index.jsp" /><%
                    break;
                case "2":
            %> <jsp:include page="/components/admin/body/mainbody/AccountBody/index.jsp" /><%
                    break;
                case "3":
            %> <jsp:include page="/components/admin/body/mainbody/RequestBody/index.jsp" /><%
                    break;
                case "4":
            %> <jsp:include page="/components/admin/body/mainbody/TaskBody/index.jsp" /><%
                    break;
                case "5":
            %> <jsp:include page="/components/admin/body/mainbody/EmployeeBody/index.jsp" /><%
                        break;
                }


            %>

            <!--Pagination--> 
            <jsp:include page="/components/admin/body/pagination/index.jsp" />


        </div>
    </body>
</html>
