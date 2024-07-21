<%-- 
    Document   : requestPage
    Created on : Mar 19, 2024, 11:06:03 AM
    Author     : Lenovo
--%>

<%@page import="controllers.CONSTANTS"%>
<%@page import="DAO.AccountDAO"%>
<%@page import="DTO.Request"%>
<%@page import="DTO.Account"%>
<%@page import="DTO.StatusType"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <style>
            .popup {
                background-color: rgb(0, 0, 0, 0.7);
                width: 100%;
                height: 100%;
                position: fixed;
                top: 0;
                left: 0;
                bottom: 0;
                display: none;
                justify-content: center;
                align-items: center;
            }

            .popup-active {
                display: flex;

            }

            /* Chưa xác nhận */
            .option-false {
                background-color: #EF4444;
                /* Màu đỏ */
                color: white;
                /* Màu chữ trắng */
            }

            /* Xác nhận */
            .option-true {
                background-color: #6EE7B7;
                /* Màu xanh lá cây */
                color: black;
                /* Màu chữ đen */
            }

            .popup-content {
                height: auto;
                width: auto;
                background-color: white;
                padding: 10px;
                border-radius: 5px;
                position: relative;
            }


            .pagination {
                display: flex;
                justify-content: center;
                align-items: center;
            }



            .pagination-item-active .pagination-item_link {
                color: white;
                background-color: rgb(3, 105, 161);

            }

            .pagination-item_link {
                display: block;
                text-align: center;
                text-decoration: none;
                font-size: 1rem;

                min-width: 20px;
                height: 27px;
                border-radius: 2px;
            }
        </style>
    </head>
    <body class=" max-w-[var(--maxWidth)] w-[100vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500 ">
        <%
            ArrayList<StatusType> listStatusRequest = (ArrayList<StatusType>) request.getAttribute("listStatusRequest");
            Account acc = (Account) session.getAttribute("loginUser");
            // lay sreach IDcontact
            String msgIDSearch = request.getParameter("searchIDtxt");
            String msgStatusSearch = request.getParameter("searchStatustxt");
            int total = (int) request.getAttribute("total");
            ArrayList<Request> list = (ArrayList<Request>) request.getAttribute("list");
            int currentP = (int) request.getAttribute("currentPagetxt");
            int endP = total / 5;
            if (total % 5 != 0 || endP == 0) {
                endP++;
            }
        %>

        <div class=" m-[10px] bg-sky-700 p-[5px] text-white">

            <p class="font-semibold text-2xl">REQUEST INFORMATION</p>


        </div>

        <div class=" content  m-[10px]  p-[10px]  pb-[15px] border-2 rounded-lg     border-gray-400">
            <form action="mainController" method="get" class="grid grid-cols-12">
                <input type="hidden" name="action" value ="getreq">
                <input type="hidden"class="" name ="currentPagetxt" value="1"> 
                <div class="col-span-9 grid grid-cols-10 ">
                    <div class=" col-span-10 flex justify-start font-bold pl-[10px]">
                        <p>Tìm kiếm thông tin yêu cầu</p>
                    </div>
                    <div class=" col-span-10 lg:col-span-4 flex border-2 w-auto border-gray-400 rounded-md mr-[20px] ">
                        <button class=" cursor-pointer bg-gray-100  rounded-l-md   border-2 border-r-gray-400 "><img
                                class="w-[29px] h-[22px]" src="/PrjProject/img/contact/sreach.png" alt=""></button>
                        <input class=" pr-[10px] pl-[10px] text-base w-full outline-none  bg-slate-200 " type="search"
                               name="searchIDtxt" placeholder="Nhập id yêu cầu" value="<%= (msgIDSearch != null) ? msgIDSearch : ""%>">
                    </div>
                    <div class="hidden  lg:col-span-6">

                    </div>
                </div>
                <div class=" col-span-3   w-full ">
                    <div class="flex justify-center font-bold">
                        <p>Tìm kiếm trạng thái yêu cầu</p>
                    </div>
                    <div class="flex justify-end w-full border-2 border-gray-400 rounded-md ">
                        <button class=" cursor-pointer bg-gray-100  rounded-l-md   border-2 border-r-gray-400 "><img
                                class="w-[29px] h-[22px]" src="/PrjProject/img/contact/sreach.png" alt=""></button>
                        <select class="bg-gray-100 p-[8px] w-full hover:selection:backdrop:bg-none  outline-none"
                                name="searchStatustxt">
                            <%if (msgStatusSearch.equals("")) {%>
                            <option value=""  selected >tất cả</option>
                            <%} else if (!msgStatusSearch.equals("")) {%>
                            <option value="" >tất cả</option>
                            <%}%>
                            <% if (listStatusRequest != null) {

                                    for (StatusType o : listStatusRequest) {

                                        if (msgStatusSearch.equals(o.getStatusID() + "") && !msgStatusSearch.equals("")) {
                            %>
                            <option value="<%= o.getStatusID()%>" selected ><%= o.getStatusName()%></option>
                            <%} else {%>
                            <option value="<%= o.getStatusID()%>" ><%= o.getStatusName()%></option>
                            <%}%>

                            <%}
                                }%>

                        </select>
                    </div>
                </div>

            </form>
            <div class="m-[10px] ">
                <hr><hr>
            </div>
            <table class="w-full border-2  rounded-l-md    border-gray-400 ">

                <tr class="  grid grid-cols-12  text-white bg-slate-500        ">
                    <th class="col-span-2 flex justify-center border-2     border-gray-400">ID yêu cầu </th>
                    <th class="col-span-3 flex justify-center border-2   border-gray-400">Tên giao dịch  </th>
                    <th class="col-span-4 flex justify-center border-2     border-gray-400">Ngày giao dịch </th>
                    <th class="col-span-2 flex justify-center border-2     border-gray-400">Trạng thái</th>
                    <th class="col-span-1 flex justify-center border-2    border-gray-400">Chi tiết</th>
                </tr>
                <!---->
                <% if (list != null) {
                        for (Request o : list) {
                %>
                <tr class="  grid grid-cols-12 border-1 rounded-md   ">
                    <td class="col-span-2 flex justify-center border-2        border-gray-400"><%= o.getReqID()%></td>
                    <td class="col-span-3 flex justify-center border-2   border-gray-400"><%= o.getContact().getService().getServiceName()%></td>
                    <td class="col-span-4 flex justify-center border-2     border-gray-400"><%= new AccountDAO().convertDateToString(o.getContact().getTransaction().getDate())%></td>
                    <%if (o.getStatusType().getStatusID() == 5) {%>
                    <td class="col-span-2 flex justify-center border-2 option-false font-bold   border-gray-400">
                        <%= o.getStatusType().getStatusName()%>
                    </td>
                    <%} else if (o.getStatusType().getStatusID() == 1) {%>
                    <td class="col-span-2 flex justify-center border-2 bg-yellow-500 text-white   font-bold   border-gray-400">
                        <%= o.getStatusType().getStatusName()%>
                    </td>
                    <%} else {%>
                    <td class="col-span-2 flex justify-center border-2 option-true       font-bold   border-gray-400">
                        <%= o.getStatusType().getStatusName()%>
                    </td>
                    <%}%>
                    <td id=""
                        class="butonMore col-span-1 flex justify-center border-2 text-green-400 hover:text-green-500 cursor-pointer hover:font-semibold      border-gray-400"
                        data-popup-id="popup<%= o.getReqID()%>">
                        xem thêm</td>
                </tr>




                <div class="popup" id="popup<%= o.getReqID()%>">
                    <div class="popup-content w-auto relative border-[17px] rounded-md border-white  bg-sky-400">

                        <form  action="mainController" method="get"class="w-auto h-auto">
                            <img class="closePopup absolute pt-[2px] pr-[2px] top-[-18px] right-[-18px]  w-[20px] h-[20px] cursor-pointer "
                                 src="/PrjProject/img/contact/closeButton.png" alt="">

                            <div

                                class="  w-auto h-auto grid grid-cols-12  gap-0  border-2  border-gray-300 rounded-lg bg-white ">
                                <div class="col-span-12 bg-gray-400">
                                    <p class="text-center font-bold text-2xl  mb-[5px]  ">Thông tin thêm</p>
                                    <hr>

                                </div>
                                <div class=" flex flex-col col-span-6 border-r-2 border-r-gray-300 p-[10px] gap-5 ">

                                    <p> <span class="font-bold">ID yêu cầu :</span> <%= o.getReqID()%> </p>

                                    <% if (o.getStatusType().getStatusID() != 1) {%>
                                    <p> <span class="font-bold"> ID Hợp đồng :</span><%= o.getContact().getContactID()%></p>
                                    <%}%>
                                    <input type="hidden" name="action" value ="getreq">
                                    <input type="hidden"class="" name ="currentPagetxt" value="1">
                                    <p> <span class="font-bold">Mô tả yêu cầu:</span> <%= o.getDescription()%> </p>
                                    <input  type="hidden" name="searchIDtxt" value="<%= (msgIDSearch != null) ? msgIDSearch : ""%>"> 
                                    <input type="hidden"class="" name ="searchStatustxt" value="<%= (msgStatusSearch != null) ? msgStatusSearch : ""%>"> 
                                    <input type="hidden"class="" name ="currentPagetxt" value="1"> 
                                    <input type="hidden" name="updateReIDtxt" value="<%= o.getReqID()%>" >
                                    <%if (acc.getRole().getRoleID() == 1 && o.getStatusType().getStatusID() == 1) {%>
                                    <p>
                                        <span class="font-bold">Trạng thái yêu cầu:</span>                                           
                                        <select
                                            class="bg-gray-100 p-[8px] w-full hover:selection:backdrop:bg-none  outline-none"
                                            name="statusPopUPtxt">

                                            <option value="1">Chưa xử lý</option>
                                            <option value="5">hủy</option>
                                        </select>
                                    </p>
                                    <%} else {%>
                                    <p> <span class="font-bold"> Trạng thái yêu cầu:</span> <%= o.getStatusType().getStatusName()%></p>
                                    <%}%>
                                    <p> <span class="font-bold"> Dịch vụ:</span> <%= o.getContact().getService().getServiceName()%></p>
                                    <% if (o.getStatusType().getStatusID() != 1) {%>
                                    <p><span class="font-bold">ID giao dịch:</span> <%= o.getContact().getTransaction().getTranID()%></p>
                                    <%}%>

                                </div>
                                <div class="col-span-6 flex flex-col gap-6 p-[10px]">
                                    <p> <span class="font-bold">Tên người giao dịch:</span> <%= o.getAcc().getFirstName() + " " + o.getAcc().getLastName()%>
                                    </p>
                                    <p> <span class="font-bold">Gmail:</span> <%= o.getAcc().getGmail()%>
                                    </p>
                                    <p> <span class="font-bold">Số điện thoại:</span> <%= o.getAcc().getPhone()%>
                                    </p>
                                    <p> <span class="font-bold">Vai trò:</span> <%= o.getAcc().getRole().getRoleName()%>
                                    </p>

                                </div>
                                <div class="col-span-12">
                                    <hr>
                                </div>
                                <%if (acc.getRole().getRoleID() == 1 && o.getStatusType().getStatusID() == 1) {%>
                                <div class="col-span-12 flex justify-end pr-[10px] mt-[10px] mb-[5px]">

                                    <button
                                        class="bg-green-400 hover:bg-green-600 hover:text-white hover:font-bold pl-[5px] pr-[5px] pt-[2px] pb-[2px] rounded-md"
                                        onclick="return window.confirm('Khi cập nhật về trạng thái hủy thì không thể cập nhật lại được nữa. Bạn có chắc chắn với thay đổi ?')"> submit </button>
                                </div>
                                <%}%>
                            </div>
                        </form>

                    </div>

                </div>
                <!---->
                <%}
                    }%>

            </table>
            <% String msg = (String) request.getAttribute("error");
            %>
            <p class="text-lg font-bold flex justify-center text-red-500">
                <%= (msg != null) ? msg : ""%>
            </p>
            <ul class="pagination mt-[10px] gap-2 text-gray-500 ">

                <% if ((currentP - 1) != 0) {
                %>
                <li class="pagination hover:bg-cyan-400 hover:text-white">
                    <form   class="pagination-item_link cursor-pointer" action="mainController" method="get">
                        <!-- ti sua lai post -->
                        <input type="hidden" name="action" value ="getreq">
                        <input  type="hidden" name="searchIDtxt" value="<%= (msgIDSearch != null) ? msgIDSearch : ""%>"> 
                        <input type="hidden"class="" name ="searchStatustxt" value="<%= (msgStatusSearch != null) ? msgStatusSearch : ""%>"> 

                        <input type="hidden"class="" name ="currentPagetxt" value="<%= (currentP - 1)%>">    
                        <button type="submit" class="cursor-pointer hover:bg-cyan-400 hover:text-white "><</button>
                    </form>
                </li>
                <%
                } else {
                %>
                <li class="pagination text-gray-200">
                    <form   class="pagination-item_link cursor-pointer">
                        <!-- ti sua lai post -->

                        <p class="cursor-pointer"><</p>
                    </form>
                </li>
                <%}%>
                <!-- curent-1>0 vi khi no ra 1 thi no in ra ) luon -->
                <%for (int i = ((((endP - currentP) == 0) && (currentP - 1) > 0) ? (currentP - 1) : currentP); i <= Math.min(currentP + 1, endP); i++) {
                %>    
                <% if (i == currentP) {%>
                <li class="pagination-item-active hover:bg-cyan-400 hover:text-white">
                    <form  class="pagination-item_link cursor-pointer" action="mainController" method="get">
                        <!-- ti sua lai post --> 
                        <input type="hidden" name="action" value ="getreq">
                        <input  type="hidden" name="searchIDtxt" value="<%= (msgIDSearch != null) ? msgIDSearch : ""%>"> 
                        <input type="hidden"class="" name ="searchStatustxt" value="<%= (msgStatusSearch != null) ? msgStatusSearch : ""%>"> 

                        <input type="submit"class="cursor-pointer" name ="currentPagetxt" value="<%=i%>">                                      
                    </form>
                </li>

                <%} else {%>
                <li class="pagination hover:bg-cyan-400 hover:text-white">
                    <!-- ti sua lai post -->
                    <form action="mainController" class="cursor-pointer pagination-item_link " method="get">
                        <input type="hidden" name="action" value ="getreq">
                        <input  type="hidden" name="searchIDtxt" value="<%= (msgIDSearch != null) ? msgIDSearch : ""%>"> 
                        <input type="hidden"class="" name ="searchStatustxt" value="<%= (msgStatusSearch != null) ? msgStatusSearch : ""%>"> 

                        <input type="submit" class="cursor-pointer" name ="currentPagetxt" value="<%= i%>">
                    </form>

                </li>

                <%}
                %>
                <% }
                %>

                <% if (((currentP + 1) <= endP) || (currentP) < endP) {%>
                <li class="pagination hover:bg-cyan-400 hover:text-white">
                    <form   class="pagination-item_link cursor-pointer" action="mainController" method="get">
                        <!-- ti sua lai post -->
                        <input type="hidden" name="action" value ="getreq">
                        <input  type="hidden" name="searchIDtxt" value="<%= (msgIDSearch != null) ? msgIDSearch : ""%>"> 
                        <input type="hidden"class="" name ="searchStatustxt" value="<%= (msgStatusSearch != null) ? msgStatusSearch : ""%>"> 

                        <input type="hidden"class="" name ="currentPagetxt" value="<%= (currentP + 1)%>">    
                        <button type="submit" class="cursor-pointer hover:bg-cyan-400 hover:text-white "> > </button>
                    </form>
                </li>
                <%} else {%>

                <li class="pagination text-gray-200">
                    <form   class="pagination-item_link cursor-pointer" action="mainController" method="post">
                        <!-- ti sua lai post -->

                        <p class="cursor-pointer">></p>
                    </form>
                </li>
                <%}%>

            </ul>
        </div>
        <div  class="fixed left-0 bottom-12 mb-[75px] cursor-pointer">
            <form action="mainController" method="get">
                <button name="action" value="<%= CONSTANTS.GETHOMEPAGELOGIN%>"> <img class="w-[70px] " src="/PrjProject/img/profile/home.png" alt="">
                </button>
            </form>
        </div>


        <script>
            const buttons = document.querySelectorAll(".butonMore");

            buttons.forEach(function (button) {
                button.addEventListener("click", function () {
                    const popupId = button.getAttribute("data-popup-id");
                    const popup = document.getElementById(popupId);
                    if (popup) {
                        popup.classList.add("popup-active");
                    }
                });
            });

            document.querySelectorAll(".closePopup").forEach(function (closeButton) {
                closeButton.addEventListener("click", function () {
                    const popup = closeButton.closest('.popup');
                    if (popup) {
                        popup.classList.remove("popup-active");
                    }
                });
            });

        </script>

    </body>
</html>
