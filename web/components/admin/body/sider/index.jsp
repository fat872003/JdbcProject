<%@page import="DTO.Account"%>
<%@page import="controllers.CONSTANTS"%>
<%-- 
    Document   : SIDER/index
    Created on : Feb 7, 2024, 11:29:22 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SIDER COMP</title>
    </head>
    <body>
        <%

            String param = (String) request.getParameter("sec");
            if (param == null)
            {
                param = "1";
            }
        %>
        <form id="sider" class="fixed w-[200px] text-white h-[calc(100vh-56px)] top-[56px] left-0 bg-[#323232]" action="mainController">
            <input type="hidden" name="action" value=<%=         CONSTANTS.GETPRODUCT_ADMIN%> /> 
            <!--<input id="siderInput" type="hidden" name="sec" value="" />-->
            <!--Quan Ly--> 
            <div>
                <div class="text-2xl border-b-2 mr-2">Quản Lý</div>
                <div class="flex flex-col">
                    <div>Page: <%=param%></div>

                    <%--<jsp:useBean id="loginUser" class="DTO.Account" scope="session" />--%> 
                    <jstl:set  var="loginUser" value="${sessionScope.loginUser}"/>
                    <div><jstl:out value="${loginUser.lastName} ${loginUser.firstName}" /></div>
                    <div><jstl:out value="${loginUser.role.roleName}" /></div>

                    <div class="px-5 py-2  bg-transparent  hover:bg-gray-700 cursor-pointer <%=           (param.equals("1")) ? "!bg-gray-800 !cursor-default" : ""%> "><button name="sec" class="text-xl capitalize" value="1" data-sider >Sản phẩm</button></div>
                    <div class="px-5 py-2  bg-transparent  hover:bg-gray-700 cursor-pointer <%=           (param.equals("2")) ? "!bg-gray-800 !cursor-default" : ""%> "><button name="sec" class="text-xl capitalize" value="2" data-sider >Người dùng</button></div>
                </div>

                <!--Giao viec--> 
                <div class="mt-5">
                    <div class="text-2xl border-b-2 mr-2">Task Manager</div>
                    <div class="flex flex-col">
                        <div class="px-5 py-2  bg-transparent  hover:bg-gray-700 cursor-pointer <%=           (param.equals("3")) ? "!bg-gray-800 !cursor-default" : ""%> "><button name="sec" class="text-xl capitalize" value="3" data-sider >Xem yêu cầu</button></div>
                        <div class="px-5 py-2  bg-transparent  hover:bg-gray-700 cursor-pointer <%=           (param.equals("4")) ? "!bg-gray-800 !cursor-default" : ""%> "><button name="sec" class="text-xl capitalize" value="4" data-sider >Giao Task</button></div>
                    </div>
                </div>

                <!----CongTY:-->
                <div class="mt-5">
                    <div class="text-2xl border-b-2 mr-2 capitalize">Company Management</div>
                    <div class="flex flex-col">
                        <div class="px-5 py-2  bg-transparent  hover:bg-gray-700 cursor-pointer <%=           (param.equals("5")) ? "!bg-gray-800 !cursor-default" : ""%> "><button name="sec" class="text-xl capitalize" value="5" data-sider >nhân viên</button></div>
                    </div>
                </div>

        </form>


    </body>
</html>
