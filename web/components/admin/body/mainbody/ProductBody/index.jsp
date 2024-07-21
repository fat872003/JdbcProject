<%-- 
    Document   : index.jsp
    Created on : Feb 13, 2024, 3:03:46 PM
    Author     : ACER
--%>

<%@page import="mylibs.UtilsFunc"%>
<%@page import="controllers.CONSTANTS"%>
<%@page import="DTO.ProductCategories"%>
<%@page import="DAO.ProductDAO"%>
<%@page import="DTO.Product"%>
<%@page import="DTO.Account"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body class="">

        <!--Dropdown--> 
        <div class="flex gap-2" >
            <!--            <div class="">Filter theo danh mục: </div>
                        <select class="rounded capitalize" id="cars">
                        </select>-->
        </div>
        <!--endDropdown-->

        <div class="flex justify-between px-2">
            <!--Search--> 
            <div class="my-2 flex">
                <form action="mainController" class="flex items-center gap-2">
                    <input type="hidden" name="action" value=<%=    CONSTANTS.GETPRODUCT_ADMIN%> />
                    <!--<input type="hidden" name="sec" value=<%= request.getParameter("sec")%>  />-->
                    <div class="mr-2">Tìm kiếm: </div>
                    <input class="border-2" name="search" value="<%=(request.getParameter("search") != null) ? request.getParameter("search") : ""%>" placeholder="Enter product name..." />
                    <button type="submit" class="px-4 py-2  rounded bg-yellow-600">Search</button>
                </form
            </div>

        </div>

    </div>




    <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
        <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                    <th scope="col" class="px-6 py-3 text-center">
                        productID
                    </th>
                    <th scope="col" class="px-6 py-3">
                        ProductName
                    </th>
                    <th scope="col" class="px-6 py-3">
                        Thumbnail
                    </th>
                    <th scope="col" class="px-6 py-3">
                        Description
                    </th>
                    <th scope="col" class="px-6 py-3">
                        Price
                    </th>
                    <th scope="col" class="px-6 py-3">
                        Speed
                    </th>
                    <th scope="col" class="px-6 py-3 text-center">
                        cateName
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

                    ArrayList<Product> list = (ArrayList<Product>)session.getAttribute("list");
                    if (list != null)
                    {
                        String currentPage = (String) request.getParameter("page");
                        if (currentPage == null)
                        {
                            currentPage = "1";
                        }

                        ArrayList<ArrayList> pagingList = (new UtilsFunc().pagination(list, CONSTANTS.MAXPAGE_ADMIN));

                        ArrayList<Product> currList = pagingList.get(Integer.parseInt(currentPage) - 1);
                        for (Product item : currList)
                        {
                %>
                <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white text-center">
                        <%=          item.getPrd_ID()%>
                    </th>
                    <td class="px-6 py-4">
                        <%=          item.getName()%>
                    </td>
                    <td class="px-6 py-4">
                        <%=          item.getThumbnail()%>
                    </td>
                    <td class="px-6 py-4">
                        <%=          item.getDescription()%>
                    </td>
                    <td class="px-6 py-4">
                        <%=          item.getPrice()%>
                    </td>
                    <td class="px-6 py-4">
                        <%=          item.getSpeed()%>
                    </td>
                    <td class="px-6 py-4 text-center capitalize">
                        <%=          new ProductDAO().getCatenameByID(item.getCategory().getCate_ID())%>
                    </td>
                    <td class="px-6 py-4 flex justify-center items-center">
                        <form action="mainController" />
                        <input type="hidden" name="action" value="<%=  CONSTANTS.BLOCK_ADMIN%>" />
                        <input type="hidden" name="sec" value="<%=  request.getParameter("sec")%>" />
                        <input type="hidden" name="itemID" value="<%=   item.getPrd_ID()%>" />
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
                            <input type="hidden" name="itemID" value= <%=       item.getPrd_ID()%> />
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
    <button class="px-4 py-2 bg-green-500 rounded text-white" id="toggleForm">Thêm Sản Phẩm</button>
    <!--===========-->

    <!--form update--> 
    <!--layer-->
    <%        Product prd = (Product) request.getAttribute("formList");
    %>
    <div id="formUpdate" class="transition-all ease-in-out <%= (prd != null) ? "" : "hidden"%>">
        <div id="formLayer" class="absolute top-0 left-0 w-screen h-screen bg-black bg-opacity-70"></div>
        <div class="bg-[#f6f6f6] absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 px-20 py-10 z-10">
            <!--quit button--> 
            <form action="mainController" class="z-100 cursor-pointer">
                <div>
                    <input type="hidden" name="action" value="<%=          CONSTANTS.GETPRODUCT_ADMIN%>" />
                    <input type="hidden" name="sec" value="<%=          request.getParameter("sec")%>" />

                </div>
                <button id="toggleForm" class="absolute top-3 right-3">
                    <svg width="20px" height="20px" viewBox="0 0 1024 1024" class="icon"  version="1.1" xmlns="http://www.w3.org/2000/svg"><path d="M512 457.6L905.6 64l54.336 54.336-393.6 393.6L960 905.6l-54.4 54.4L512 566.336 118.4 960 64 905.6 457.6 512 64 118.336 118.336 64l393.6 393.6z" fill="#000000" /></svg>
                </button>
            </form>
            <form action="mainController" class="max-w-md mx-auto" method="post">
                <%
                    if (prd != null)
                    {
                %>
                <input type="hidden" name="action" value="<%=     CONSTANTS.UPDATEINFO_ADMIN%>" />
                <%
                } else
                {
                %>
                <input type="hidden" name="action" value="<%=     CONSTANTS.ADDINFO_ADMIN%>" />
                <%
                    }
                %>

                <input type="hidden" name="sec" value="<%=      request.getParameter("sec")%>" />
                <!--form--> 
                <%if (prd != null)
                    {
                %>
                <input type="hidden" name="prd_ID" value="<%=prd.getPrd_ID()%>" />

                <%
                    }%>
                <!--Khi khởi tạo, mặc định là true-->
                <input type="hidden" name="status" value="<%=        (prd != null) ? prd.getStatus() : "1"%>" />

                <div class="relative z-0 w-full mb-5 group">
                    <input value="<%=      (prd != null) ? prd.getName() : ""%>" type="text" name="name"  class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                    <label for="floating_email" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Product Name</label>
                </div>
                <div class="relative z-0 w-full mb-5 group">
                    <input value="<%=      (prd != null) ? prd.getThumbnail() : ""%>" type="text" name="thumbnail" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                    <label for="floating_password" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Thumbnail</label>
                </div>
                <div class="relative z-0 w-full mb-5 group">
                    <input value="<%=      (prd != null) ? prd.getDescription() : ""%>" type="text" name="description" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                    <label for="floating_password" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Description</label>
                </div>
                <!--1/2--> 
                <div class="grid md:grid-cols-2 md:gap-6">
                    <div class="relative z-0 w-full mb-5 group">
                        <input value="<%=      (prd != null) ? prd.getPrice() : ""%>" type="number" name="price" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                        <label for="floating_first_name" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Price</label>
                    </div>
                    <div class="relative z-0 w-full mb-5 group">
                        <input value="<%=      (prd != null) ? prd.getSpeed() : ""%>" type="number" name="speed" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                        <label for="floating_last_name" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Speed</label>
                    </div>
                </div>

                <!--=============--> 
                <select name="cate_ID" class="capitalize block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer">
                    <%
                        ArrayList<ProductCategories> cateList = new ProductDAO().getAllCategories();
                        if (cateList != null)
                        {
                            for (ProductCategories item : cateList)
                            {
                                if (prd != null)
                                {
                                    if (item.getCate_ID() == prd.getCategory().getCate_ID())
                                    {
                    %>
                    <option value=<%=      item.getCate_ID()%> selected>
                        <%=             item.getName()%> *
                    </option>
                    <%
                    } else
                    {
                    %>
                    <option value=<%=      item.getCate_ID()%> >
                        <%=             item.getName()%> 
                    </option>
                    <%
                        }
                    } else
                    {
                    %>
                    <option value=<%=      item.getCate_ID()%> >
                        <%=             item.getName()%> 
                    </option>
                    <%
                                }

                            }
                        }
                    %>
                </select>

                <div class="flex justify-between">
                    <div></div>
                    <div class="flex gap-2">
                        <%
                            if (prd != null)
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
