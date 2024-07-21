<%-- 
    Document   : index
    Created on : Feb 13, 2024, 3:04:24 PM
    Author     : ACER
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<%@page import="mylibs.UtilsFunc"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="mylibs.DBUtils"%>
<%@page import="java.sql.Connection"%>
<%@page import="DAO.AccountDAO"%>
<%@page import="DTO.Role"%>
<%@page import="DTO.Product"%>
<%@page import="controllers.CONSTANTS"%>
<%@page import="DAO.ProductDAO"%>
<%@page import="DTO.ProductCategories"%>
<%@page import="DTO.Account"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <jstl:set var='search' value="${param.search}" />
        <jstl:set var='sec' value="${param.sec}" />
        <jstl:set var='page' value="${param.page}" />

        <!--Dropdown--> 
        <div class="flex gap-2" >
            <div class="">Danh mục đang hiển thị: </div>
            <select class="rounded" id="cars">
                <option value="allProduct">Toàn bộ sản phẩm</option>
                <option value="cate">Danh mục sản phẩm</option>
                <option value="service">Dịch vụ</option>
            </select>
        </div>
        <!--endDropdown-->


        <!--Search--> 
        <div class="my-5">
            <form action="mainController" class="flex items-center gap-2">
                <input type="hidden" name="action" value=<%=    CONSTANTS.GETPRODUCT_ADMIN%> />
                <input type="hidden" name="sec" value=<%= request.getParameter("sec")%>  />
                <div class="mr-2">Tìm kiếm: </div>
                <input class="border-2" name="search" value="<%=(request.getParameter("search") != null) ? request.getParameter("search") : ""%>" placeholder="Enter phone numbers..." />
                <button type="submit" class="px-4 py-2  rounded bg-yellow-600">Search</button>
            </form
        </div>

        <div class="relative overflow-x-auto shadow-md sm:rounded-lg my-5">
            <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th scope="col" class="px-6 py-3 text-center">
                            AccountID
                        </th>
                        <th scope="col" class="px-6 py-3">
                            LastName
                        </th>
                        <th scope="col" class="px-6 py-3">
                            FirstName
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Phone
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Gmail
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Password
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Gender
                        </th>
                        <th scope="col" class="px-6 py-3 text-center">
                            Policy status
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Script
                        </th>
                        <th scope="col" class="px-6 py-3 text-center">
                            Block/Unblock
                        </th>
                        <th scope="col" class="px-6 py-3 text-center">
                            <span class="">Other</span>
                        </th>

                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Account> list = (ArrayList<Account>) session.getAttribute("list");
                        if (list != null)
                        {
                            String currentPage = (String) request.getParameter("page");
                            if (currentPage == null)
                            {
                                currentPage = "1";
                            }
                            ArrayList<ArrayList> pagingList = (new UtilsFunc().pagination(list, CONSTANTS.MAXPAGE_ADMIN));
                            ArrayList<Account> currList = pagingList.get(Integer.parseInt(currentPage) - 1);
                            for (Account item : currList)
                            {


                    %>
                    <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                        <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white text-center">
                            <%=          item.getAccountID()%>
                        </th>
                        <td class="px-6 py-4">
                            <%=          item.getLastName()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getFirstName()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getPhone()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getGmail()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getPassword()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getSex()%>
                        </td>
                        <td class="px-6 py-4 flex justify-center items-center">
                            <form action="mainController" />
                            <input type="hidden" name="action" value="<%=  CONSTANTS.BLOCK_ADMIN%>" />
                            <input type="hidden" name="sec" value="<%=  request.getParameter("sec")%>" />
                            <input type="hidden" name="type" value="changePolicy" />
                            <input type="hidden" name="itemID" value="<%=   item.getAccountID()%>" />
                            <%
                                if (item.getPolicyStatus().equals("1"))
                                {
                            %>
                            <button class="bg-yellow-700 rounded py-1 px-2 text-white ">Legal <%= item.getPolicyStatus()%></button>
                            <%
                            } else
                            {
                            %>
                            <button class="bg-red-500 rounded py-1 px-2 text-white">Illegal <%= item.getPolicyStatus()%></button>
                            <%
                                }

                            %>                           
                            </form>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getScript()%>
                        </td>
                        <td class="px-6 py-4 flex justify-center items-center">
                            <form action="mainController" />
                            <input type="hidden" name="action" value="<%=  CONSTANTS.BLOCK_ADMIN%>" />
                            <input type="hidden" name="sec" value="<%=  request.getParameter("sec")%>" />
                            <input type="hidden" name="itemID" value="<%=   item.getAccountID()%>" />
                            <%
                                if (item.getStatus().equals("1"))
                                {
                            %>
                            <button class="bg-green-500 rounded py-1 px-2 text-white ">Active</button>
                            <%
                            } else
                            {
                            %>
                            <button class="bg-red-500 rounded py-1 px-2 text-white">Inactive</button>
                            <%
                                }

                            %>
                            </form>
                        </td>
                        <td class="px-6 py-4 text-center cursor-pointer">
                            <form action="mainController">
                                <input type="hidden" name="action" value=<%=    CONSTANTS.GETFORMINFOPRODUCT_ADMIN%> />
                                <input type="hidden" name="sec" value=<%= request.getParameter("sec")%>  />
                                <input type="hidden" name="itemID" value= <%=       item.getAccountID()%> />
                                <button class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit</button>
                            </form>
                        </td>
                    </tr>
                    <%                            }
                        }
                    %>
                </tbody>
            </table>
        </div>

        <!--Add btn--> 
        <button class="px-4 py-2 bg-green-500 rounded text-white capitalize" id="toggleForm">Thêm Người Dùng</button>
        <!--===========-->

        <!--form update--> 
        <!--layer-->
        <%        Account prd = (Account) request.getAttribute("formList");
        %>
        <div id="formUpdate" class="transition-all ease-in-out <%= (prd != null) ? "" : "hidden"%>">
            <div id="formLayer" class="absolute top-0 left-0 w-screen h-screen bg-black bg-opacity-70"></div>
            <div class="bg-[#f6f6f6] absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 px-20 py-10 z-10">
                <!--quit button--> 
                <form action="mainController" class="z-100 cursor-pointer">
                    <div>
                        <input type="hidden" name="action" value="<%=          CONSTANTS.GETPRODUCT_ADMIN%>" />
                        <input type="hidden" name="sec" value="<%=          request.getAttribute("sec")%>" />

                    </div>
                    <button id="toggleForm" class="absolute top-3 right-3">
                        <svg width="20px" height="20px" viewBox="0 0 1024 1024" class="icon"  version="1.1" xmlns="http://www.w3.org/2000/svg"><path d="M512 457.6L905.6 64l54.336 54.336-393.6 393.6L960 905.6l-54.4 54.4L512 566.336 118.4 960 64 905.6 457.6 512 64 118.336 118.336 64l393.6 393.6z" fill="#000000" /></svg>
                    </button>
                </form>
                <form action="mainController" class="max-w-md mx-auto" method="post" accept-charset="UTF-8">
                    <%
                        if (prd != null)
                        {
                    %>
                    <input type="hidden" name="action" value="<%=     CONSTANTS.UPDATEINFO_ADMIN%>" />
                    <input type="hidden" name="AccountID" value="<%=      prd.getAccountID()%>" />

                    <%
                    } else
                    {
                    %>
                    <input type="hidden" name="action" value="<%=     CONSTANTS.ADDINFO_ADMIN%>" />
                    <%
                        }
                    %>

                    <input type="hidden" name="sec" value="<%=      request.getAttribute("sec")%>" />
                    <!--form--> 
                    <!--1/2--> 
                    <div class="grid md:grid-cols-2 md:gap-6">
                        <div class="relative z-0 w-full mb-5 group">
                            <input value="<%=      (prd != null) ? prd.getFirstName() : ""%>" type="text" name="FirstName" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                            <label for="floating_first_name" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">First name</label>
                        </div>
                        <div class="relative z-0 w-full mb-5 group">
                            <input value="<%=      (prd != null) ? prd.getLastName() : ""%>" type="text" name="LastName" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                            <label for="floating_last_name" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Last name</label>
                        </div>
                    </div>
                    <div class="grid md:grid-cols-2 md:gap-6">
                        <div class="relative z-0 w-full mb-5 group">
                            <input value="<%=      (prd != null) ? prd.getPhone() : ""%>" type="text" name="phone" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                            <label for="floating_password" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Phone</label>
                        </div>
                        <div class="relative z-0 w-full mb-5 group">
                            <label for="floating_gender" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Gender</label>
                            <select name="sex" class="capitalize block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer">
                                <option class="capitalize" value="Male"  <%= (prd != null) ? (prd.getSex().equals("Male") ? "selected" : "") : ""%>  >male</option>
                                <option class="capitalize" value="Female"  <%= (prd != null) ? (prd.getSex().equals("Female") ? "selected" : "") : ""%> >female</option>
                            </select>
                        </div>
                    </div>

                    <div class="relative z-0 w-full mb-5 group">
                        <input value="<%=      (prd != null) ? prd.getGmail() : ""%>" type="email" name="gmail" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                        <label for="floating_password" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Gmail</label>
                    </div>
                    <div class="relative z-0 w-full mb-5 group">
                        <input value="<%=      (prd != null) ? prd.getPassword() : ""%>" type="password" name="password" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                        <label for="floating_first_name" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Password</label>
                    </div>
                    <!--Khi khởi tạo, mặc định là true-->
                    <input type="hidden" name="status" value="<%=        (prd != null) ? prd.getStatus() : "1"%>" />
                    <!--Khi khởi tạo, mặc định là true-->
                    <input type="hidden" name="policyStatus" value="<%=        (prd != null) ? prd.getPolicyStatus() : "1"%>" />
                    <div class="relative z-0 w-full mb-5 group">
                        <input value="<%=      (prd != null) ? prd.getScript() : ""%>" type="text" name="script" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                        <label for="floating_first_name" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Policy Script</label>
                    </div>
                    <!--=============--> 
                    <select name="RoleID" class="capitalize block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer">
                        <%
                            ArrayList<Role> roleList = new AccountDAO().getAllAccountRole();

                            for (Role item : roleList)
                            {
                                if (prd != null)
                                {
                                    if (prd.getRole().getRoleName().equals(item.getRoleName()))
                                    {
                        %>
                        <option value=<%=         item.getRoleID()%> selected  ><%=              item.getRoleName()%>*</option>
                        <%
                            }
                        %>
                        <%
                        } else if (item.getRoleName().matches("Client"))
                        {
                        %>
                        <option value=<%=     item.getRoleID()%>  ><%=              item.getRoleName()%></option>
                        <%
                                }
                            }

                        %>
                    </select>

                    <div class="flex justify-between">
                        <div></div>
                        <div class="flex gap-2">
                            <%                                if (prd != null)
                                {

                            %>
                            <button type="submit" class="text-white bg-cyan-500 hover:bg-cyan-600 rounded  px-5 py-2 text-center">Update</button>
                            <%                            } else
                            {
                            %>
                            <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 rounded  px-5 py-2 text-center">Submit</button>
                            <%}%>
                        </div>
                    </div>
                </form>
            </div>
        </div>





        <script>
            //    on&off formUpdate:
            const toggleList = document.querySelectorAll("#toggleForm");
            toggleList.forEach(btn => {
                btn.addEventListener("click", () => {
                    document.getElementById("formUpdate").classList.toggle("hidden");
                });
            });

        </script>
    </body>
</html>
