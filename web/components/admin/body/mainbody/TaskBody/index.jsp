<%-- 
    Document   : index
    Created on : Feb 13, 2024, 3:04:59 PM
    Author     : ACER
--%>
<%@page import="DTO.Employee"%>
<%@page import="DAO.AccountDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="DAO.RequestDAO"%>
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
                        currentPage = (currentPage == null || currentPage.trim().equals("null")) ? "1" : currentPage;
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
                        <td class="px-6 py-4 flex flex-col items-center gap-2">
                            <form action="mainController">
                                <input type="hidden" name="action" value="<%= CONSTANTS.GETFORMINFOPRODUCT_ADMIN%>" />
                                <input type="hidden" name="sec" value="<%= request.getParameter("sec")%>" />
                                <input type="hidden" name="search" value="${search}" />
                                <input type="hidden" name="date" value="<%=          request.getParameter("date")%>" />
                                <input type="hidden" name="status" value="<%=          request.getParameter("status")%>" />
                                <input type="hidden" name="page" value="<%=         currentPage%>" />
                                <input type="hidden" name="reqID" value="<%=     item.getReqID()%>" />
                                <select class="flex justify-center rounded  py-1 " name="newManagerID">
                                    <%
                                        ArrayList<Account> technicianList = (ArrayList<Account>) session.getAttribute("technicianList");
                                        if (technicianList != null)
                                        {
                                            for (Account acc : technicianList)
                                            {
                                    %>
                                    <option value="<%= acc.getAccountID()%>"><%=  (acc.getFirstName() + " " + acc.getLastName())%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                                <button class="px-2 rounded bg-yellow-500 w-1/2">Giao việc</button>
                            </form>
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
        <!--<button class="px-4 py-2 bg-green-500 rounded text-white" id="toggleForm">Tạo Request</button>-->
        <!--===========-->

        <!--form update--> 
        <!--layer-->
        <%                Employee managerAcc = (Employee) request.getAttribute("formAccount");

        %>
        <div id="formUpdate" class="transition-all ease-in-out <%=  ((managerAcc == null) ? "hidden" : "")%>">
            <div id="formLayer" class="absolute top-0 left-0 w-screen h-screen bg-black bg-opacity-70 z-10"></div>
            <div class="bg-[#f6f6f6] absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 px-20 py-10 z-10">
                <!--quit button--> 
                <form action="mainController" class="cursor-pointer">
                    <div>
                        <input type="hidden" name="action" value="<%=          CONSTANTS.VIEWPRODUCT_ADMIN%>" />
                        <input type="hidden" name="sec" value="<%=          request.getParameter("sec")%>" />
                        <input type="hidden" name="search" value="${search}" />
                        <input type="hidden" name="date" value="<%=          request.getParameter("date")%>" />
                        <input type="hidden" name="status" value="<%=          request.getParameter("status")%>" />
                        <input type="hidden" name="page" value="<%=         ((request.getParameter("page") == null) ? "1" : request.getParameter("page"))%>" />
                    </div>
                    <button id="toggleForm" class="absolute top-3 right-3">
                        <svg width="20px" height="20px" viewBox="0 0 1024 1024" class="icon"  version="1.1" xmlns="http://www.w3.org/2000/svg"><path d="M512 457.6L905.6 64l54.336 54.336-393.6 393.6L960 905.6l-54.4 54.4L512 566.336 118.4 960 64 905.6 457.6 512 64 118.336 118.336 64l393.6 393.6z" fill="#000000" /></svg>
                    </button>
                </form>
                <div class="popup">
                    <%
                        if (managerAcc != null)
                        {
                            Account managerDetail = new AccountDAO().getAccountByID(managerAcc.getAccountID() + "");
                    %>

                    <div class="popup">

                        <div class="popup-content">
                            <div class="flex gap-4 mb-[10px]">
                                <img class="w-[250px]  border-2 border-gray-300 rounded-lg " src="https://scontent.xx.fbcdn.net/v/t1.15752-9/430145271_7319032638211554_4348224168719193655_n.jpg?stp=dst-jpg_s280x280&_nc_cat=106&ccb=1-7&_nc_sid=5f2048&_nc_ohc=WbAvM2DuhlAAX8eTUve&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdQjWEu5Al57zOmNBVCbcyNN9vxvEzeoXDUMLpBQP5MDag&oe=66162B94" alt="personIcon.png">
                                <div class="flex flex-col w-full text-start gap-3">
                                    <p class="font-bold text-2xl capitalize">Thông tin nhân viên</p>
                                    <p class="font-bold capitalize">CCCD: <span class="font-normal"><%= managerAcc.getIdentify_ID()%></span></p>
                                    <p class="font-bold capitalize">Tên nhân viên: <span class="font-normal"><%=  (managerDetail.getFirstName() + " " + managerDetail.getLastName())%></span></p>
                                    <p class="font-bold capitalize" > Ngày sinh: <span class="font-normal"><%=  new SimpleDateFormat("dd/MM/yyyy").format(managerAcc.getDayOfBirth())%></span></p>
                                    <p class="font-bold capitalize" > Chuyên môn: <span class="font-normal"><%=  managerAcc.getMajor().getMajorName()%></span> </p>
                                    <p class="font-bold capitalize" > Số điện thoại: <span class="font-normal"><%=  managerDetail.getPhone()%> </span></p>
                                    <p class="font-bold capitalize" > Email: <span class="font-normal"><%=  managerDetail.getGmail()%> </span></p>
                                    <p class="font-bold capitalize" > Giới tính: <span class="font-normal"><%=  managerDetail.getSex()%> </span></p>

                                </div>
                            </div>
                            <div class=" w-full h-auto flex flex-col gap-2  border-2  border-gray-300 rounded-lg p-[5px]">
                                <p class="text-center font-bold text-2xl ">Thông tin request</p>
                                <%
                                    Request requestForm = (Request) request.getAttribute("requestForm");
                                    if (requestForm != null)
                                    {
                                %>
                                <p class="font-bold capitalize">Tên request: <span class="font-normal"><%=  requestForm.getContact().getService().getServiceName()%></span></p>
                                <p class="font-bold capitalize">Sản phẩm: <span class="font-normal"><%=  requestForm.getContact().getTransaction().getProduct().getName()%></span></p>
                                <p class="font-bold capitalize">Giá: <span class="font-normal"><%=  requestForm.getContact().getService().getServicePrice() + requestForm.getContact().getTransaction().getMoney()%></span></p>
                                <p class="font-bold capitalize">Description: <span class="font-normal"><%=  requestForm.getDescription()%></span></p>
                                <p class="font-bold capitalize">Nguời gửi: <span class="font-normal"><%=  (requestForm.getAcc().getFirstName() + requestForm.getAcc().getLastName())%></span></p>
                                <p class="font-bold capitalize">Số điện thoại: <span class="font-normal"><%=  requestForm.getAcc().getPhone()%></span></p>
                                <p class="font-bold capitalize">Gmail: <span class="font-normal"><%=  requestForm.getAcc().getGmail()%></span></p>
                                    <%}%>
                            </div>
                        </div>
                        <div class=" flex justify-center items-center mt-5 gap-2">
                            <div class="font-bold ">Giao việc cho thằng ngu này? </div>
                            <!--xacnhan-->
                            <form action="mainController" class="cursor-pointer">
                                <div>
                                    <input type="hidden" name="action" value="<%=          CONSTANTS.UPDATEINFO_ADMIN%>" />

                                    <input type="hidden" name="sec" value="<%= request.getParameter("sec")%>"/>
                                    <input type="hidden" name="search" value="${search}" />
                                    <input type="hidden" name="date" value="<%=          request.getParameter("date")%>" />
                                    <input type="hidden" name="status" value="<%=          request.getParameter("status")%>" />
                                    <input type="hidden" name="page" value="<%=          request.getParameter("page")%>" />

                                    <input type="hidden" name="taskReq" value="<%=         requestForm.getReqID()%>" />
                                    <input type="hidden" name="employeeID" value="<%=       managerAcc.getAccountID()%>" />
                                </div>
                                <button class="capitalize px-4 py-2 bg-green-500 text-white rounded">Xác nhận</button>
                            </form>

                            <!--quit--> 
                            <form action="mainController" class="cursor-pointer">
                                <div>
                                    <input type="hidden" name="action" value="<%=          CONSTANTS.VIEWPRODUCT_ADMIN%>" />
                                    <input type="hidden" name="sec" value="<%=          request.getParameter("sec")%>" />
                                    <input type="hidden" name="search" value="${search}" />
                                    <input type="hidden" name="date" value="<%=          request.getParameter("date")%>" />
                                    <input type="hidden" name="status" value="<%=          request.getParameter("status")%>" />
                                    <input type="hidden" name="page" value="<%=         ((request.getParameter("page") == null) ? "1" : request.getParameter("page"))%>" />
                                </div>
                                <button class="capitalize px-4 py-2 bg-red-500 text-white rounded">Hủy</button>
                            </form>
                        </div>

                    </div>

                    <%
                        }
                    %>
                </div>
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
