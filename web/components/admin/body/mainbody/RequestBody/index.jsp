<%-- 
    Document   : index
    Created on : Feb 13, 2024, 3:04:59 PM
    Author     : ACER
--%>
<%@page import="DTO.StatusType"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<%@page import="DAO.ServiceDAO"%>
<%@page import="DTO.Service"%>
<%@page import="DTO.RequestType"%>
<%@page import="DAO.RequestTypeDAO"%>
<%@page import="DTO.Request"%>
<%@page import="DAO.ProductDAO"%>
<%@page import="mylibs.UtilsFunc"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DTO.Product"%>
<%@page import="DTO.Account"%>
<%@page import="controllers.CONSTANTS"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!--//           Lấy từ session--> 
        <jstl:set var="accSes" value="${sessionScope.loginUser}" />
        <jstl:set var="search" value="${param.search}" />
        <form action="mainController" class="flex items-center gap-10 my-5">
            <input type="hidden" name="action" value="<%=CONSTANTS.GETPRODUCT_ADMIN%>" />
            <input type="hidden" name="sec" value="<%=         request.getParameter("sec")%>" />
            <!--Theo sdt--> 
            <div class="flex gap-2">
                <div class="mr-2 flex justify-center items-center">Tìm kiếm: </div>
                <input class="border-2" name="search" value="${search}" placeholder="Enter product name..." />
                <button type="submit" class="px-4 py-2  rounded bg-yellow-600">Search</button>
            </div>

            <!--Theo danh mục--> 
            <%
                String date = (String) request.getAttribute("date");
                String datePar = (String) request.getParameter("date");
                datePar = (datePar == null) ? "1" : datePar;
                date = (date == null) ? datePar : date;


            %>
            <div class="flex gap-2" >
                <select name="date" class="capitalize rounded">
                    <option value="1" >Mặc định</option>
                    <%                        if (date != null)
                        {
                    %>
                    <option value="2"  <%=     (date.equals("2")) ? "selected" : ""%>>Theo ngày gần nhất</option>
                    <%
                        }
                    %>
                </select>
            </div>

            <!--Theo trạng thái:--> 
            <%
                //Neu status attribute = null => lay status param
                String status = (String) request.getAttribute("status");
                String statusPar = (String) request.getParameter("status");
                statusPar = (statusPar == null) ? "" : statusPar;
                status = (status == null) ? statusPar : status;
            %>
            <div class="flex gap-2" >
                <div class="">Theo trạng thái: </div>
                <select name="status" class="capitalize rounded">
                    <option value="" >Mặc định</option>
                    <%
                        ArrayList<StatusType> sttList = (ArrayList<StatusType>) session.getAttribute("statusList");
                        if (sttList != null)
                        {
                            for (StatusType stt : sttList)
                            {
                    %>
                    <option value="<%=    stt.getStatusID()%>"  <%=  ((status + "").equals(stt.getStatusID() + "")) ? "selected" : ""%> ><%=    stt.getStatusName()%></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

        </form>
        <!--TABLE--> 
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
            <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th scope="col" class="px-6 py-3 text-center">
                            Request ID
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Request Name
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Register Date
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Contact Phone
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Contact Gmail
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Admin Name
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Admin Role
                        </th>
                        <th scope="col" class="px-6 py-3 text-center">
                            Status
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Request Type 
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Sản Phẩm
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Price
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Description
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Request> listReq = (ArrayList<Request>) session.getAttribute("list");
                        String currentPage = (String) request.getParameter("page");
                        currentPage = (currentPage == null || currentPage.trim().equals("null") )? "1": currentPage;
                        if (listReq != null)
                        {

                            ArrayList<ArrayList> pagingList = (new UtilsFunc().pagination(listReq, CONSTANTS.MAXPAGE_ADMIN));
//                            out.print("<div class='font-bold text-2xl'>currPage: "+ currentPage +"</div>");
                            ArrayList<Request> currList = pagingList.get(Integer.parseInt(currentPage) - 1);
//                            for (Request item : currList)
                            for (Request item : currList)
                            {
                    %>


                    <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                        <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white text-center">
                            <%=             item.getReqID()%>
                        </th>
                        <td class="px-6 py-4 capitalize">
                            <%=          item.getContact().getService().getServiceName()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          (item.getContact().getTransaction().getDate() == null) ? "NULL" : item.getContact().getTransaction().getDate().toLocaleString()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getAcc().getPhone()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getAcc().getGmail()%>
                        </td>

                        <td class="px-6 py-4">
                            <%=          (item.getAdminAcc() != null) ? (item.getAdminAcc().getFirstName() + " " + item.getAdminAcc().getLastName()) : item.getAdminAcc()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=         (item.getAdminAcc().getRole() != null) ? item.getAdminAcc().getRole().getRoleName() : item.getAdminAcc().getRole()%>
                        </td>

                        <td class="px-6 py-4 text-center ">
                            <!-- 148 --> 
                            <details class="dropdown w-[150px]">
                                <summary class="m-1 btn rounded  py-1 text-[20px] text-white
                                         <%
                                             if (item.getStatusType().getStatusID() == 1)
                                             {
                                                 out.print("bg-gray-500");
                                             } else if (item.getStatusType().getStatusID() == 3)
                                             {
                                                 out.print("bg-cyan-500");
                                             } else if (item.getStatusType().getStatusID() == 4)
                                             {
                                                 out.print("bg-green-500");
                                             } else if (item.getStatusType().getStatusID() == 5)
                                             {
                                                 out.print("bg-red-500");
                                             } else
                                             {
                                                 out.print("bg-yellow-500");
                                             }
                                         %>
                                         "><%=      item.getStatusType().getStatusName()%></summary>
                                <ul class="menu dropdown-content z-[1] rounded  absolute w-[150px]  bg-gray-100">
                                    <form action="mainController">
                                        <input type="hidden" name="sec" value="<%= request.getParameter("sec")%>"/>
                                        <input type="hidden" name="action" value="<%= CONSTANTS.UPDATEINFO_ADMIN%>"/>
                                        <input type="hidden" name="reqID" value="<%=   item.getReqID()%>"/>
                                        <input type="hidden" name="search" value="${search}" />
                                        <input type="hidden" name="date" value="<%=          request.getParameter("date")%>" />
                                        <input type="hidden" name="status" value="<%=          request.getParameter("status")%>" />
                                        <input type="hidden" name="page" value="<%=          request.getParameter("page")%>" />

                                        <!--input để xem nếu chưa đc gắn managerID thì sẽ tự gắn--> 
                                        <input type="hidden" name="isAttach" value="<%=    (item.getAdminAcc() != null)%>"/>
                                        <input type="hidden" name="managerID" value="${accSes.accountID}"/>

                                        <%
                                            ArrayList<StatusType> sttType = (ArrayList<StatusType>) session.getAttribute("statusList");
                                            if (sttType != null)
                                            {

                                                for (StatusType stt : sttType)
                                                {
                                        %>
                                        <li class="">
                                            <button class="py-2 h-full w-full <%=(stt.getStatusID() == item.getStatusType().getStatusID()) ? "bg-gray-200" : "bg-gray-100"%>   
                                                    <%
                                                        if (item.getStatusType().getStatusID() >= 3 && stt.getStatusID() <= 2)
                                                        {
                                                            out.print("opacity-30 ");
                                                        } else
                                                        {
                                                            out.print("hover:bg-gray-200 cursor-pointer");
                                                        }
                                                    %>
                                                    " 
                                                    type="submit" name="sttType" value="<%=stt.getStatusID()%>" 
                                                    <%
                                                        //Neu id dang >3 thi kh the ve chua xu ly
                                                        if (item.getStatusType().getStatusID() >= 3 && stt.getStatusID() <= 2)
                                                        {
                                                            out.print("disabled");
                                                        }
                                                    %>
                                                    >
                                                <%=      stt.getStatusName()%>
                                            </button>
                                        </li>
                                        <%
                                                }
                                            }%>
                                    </form>
                                </ul>
                            </details>

                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getRequestType().getRqTyName()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getContact().getTransaction().getProduct().getName()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getContact().getService().getServicePrice() + item.getContact().getTransaction().getMoney()%>
                        </td>
                        <td class="px-6 py-4">
                            <%=          item.getDescription()%>
                        </td>
                    </tr>
                    <%                            }
                        }
                    %>
                </tbody>
            </table>
        </div>
        <!--=============================-->

        <!--Add btn--> 
        <button class="px-4 py-2 bg-green-500 rounded text-white" id="toggleForm">Tạo Request</button>
        <!--===========-->

        <!--form update--> 
        <!--layer-->

        <div id="formUpdate" class="transition-all ease-in-out hidden">
            <div id="formLayer" class="absolute top-0 left-0 w-screen h-screen bg-black bg-opacity-70"></div>
            <div class="bg-[#f6f6f6] absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 px-20 py-10 z-10">
                <!--quit button--> 
                <form action="mainController" class="cursor-pointer">
                    <div>
                        <input type="hidden" name="action" value="<%=          CONSTANTS.GETPRODUCT_ADMIN%>" />
                        <input type="hidden" name="sec" value="<%=          request.getParameter("sec")%>" />

                    </div>
                    <button id="toggleForm" class="absolute top-3 right-3">
                        <svg width="20px" height="20px" viewBox="0 0 1024 1024" class="icon"  version="1.1" xmlns="http://www.w3.org/2000/svg"><path d="M512 457.6L905.6 64l54.336 54.336-393.6 393.6L960 905.6l-54.4 54.4L512 566.336 118.4 960 64 905.6 457.6 512 64 118.336 118.336 64l393.6 393.6z" fill="#000000" /></svg>
                    </button>
                </form>
                <form action="mainController" class="max-w-md mx-auto" method="post">
                    <input type="hidden" name="action" value="<%=     CONSTANTS.ADDINFO_ADMIN%>" />
                    <input type="hidden" name="sec" value="<%=      request.getParameter("sec")%>" />


                    <input type="hidden" name="AccountID" value="${accSes.accountID}" />   <!-- ra accID   --> 
                    <input type="hidden" name="ManagerID" value="${accSes.accountID}" />  <!-- Client tạo sẽ ra null, nhưng trường hợp này do thg Admin tạo => accSes)  --> 
                    <!-- end session  -->

                    <!--Khi khởi tạo, mặc định là true-->
                    <input type="hidden" name="status" value="" />

                    <!--Service Type--> 
                    <div class="relative z-0 w-full mb-5 group">
                        <select name="SerID" class="capitalize block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer">
                            <%
                                ArrayList<Service> serList = new ServiceDAO().getAllService();
                                if (serList != null)
                                {
                                    for (Service service : serList)
                                    {
                                        if (service.getStatus().matches("1"))
                                        {
                            %>
                            <option value="<%=  service.getId()%>" > <%=   service.getServiceName()%></option>
                            <%
                                        }
                                    }

                                }
                            %>
                        </select>
                        <label class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Service Name</label>
                    </div>
                    <!--End Service Type-->  

                    <!--get Product--> 

                    <div class="relative z-0 w-full mb-5 group">
                        <select name="PrdID" class="capitalize block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer">
                            <option value="0" > Khác </option>
                            <%
                                ArrayList<Product> productList = new ProductDAO().getAllProduct();
                                if (productList != null)
                                {
                                    for (Product prd : productList)
                                        if (prd.getStatus().matches("1"))
                                        {
                            %>
                            <option value="<%=     prd.getPrd_ID()%>" > <%=   prd.getName()%></option>
                            <%
                                        }
                                    {

                                    }

                                }
                            %>
                        </select>
                        <label class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Product Name</label>
                    </div>
                    <!--End Get Product--> 

                    <!--RequestType--> 
                    <div class="relative z-0 w-full mb-5 group">
                        <select name="reqTypeID" class="capitalize block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer">
                            <%
                                ArrayList<RequestType> reqTypeList = new RequestTypeDAO().getAllRequestType();
                                if (reqTypeList != null)
                                {
                                    for (RequestType reqType : reqTypeList)
                                    //Đáng lý phải checkstatus trước render
                                    {
                            %>
                            <option value="<%=  reqType.getRqTyID()%>" > <%=   reqType.getRqTyName()%></option>
                            <%
                                    }

                                }
                            %>
                        </select>
                        <label class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Loại đơn</label>
                    </div>
                    <!--End Request Type--> 

                    <div class="relative z-0 w-full mb-5 group">
                        <textarea value="" type="text" name="Description" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required ></textarea>
                        <label class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Mô tả</label>
                    </div>

                    <!--=============--> 


                    <div class="flex justify-between">
                        <div></div>
                        <div class="flex gap-2">
                            <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 rounded  px-5 py-2 text-center">Submit</button>
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
