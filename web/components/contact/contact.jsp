<%-- 
    Document   : contact
    Created on : Mar 11, 2024, 8:48:52 PM
    Author     : Lenovo
--%>

<%@page import="DAO.ContactDAO"%>
<%@page import="DTO.Request"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DTO.Account"%>
<%@page import="DAO.RequestDAO"%>
<%@page import="DAO.AccountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

            /* Chưa xác nhận */
            .option-false {
                background-color: #EF4444;
                /* Màu đỏ */
                color: white;
                /* Màu chữ trắng */
            }

            /* Xác nhận */
            .option-true {
                background-color: #6ade38;
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

    <!--
    dat ten: searchIDtxt: la thanh sreach ID
    searchStatustxt: la sreachstatus
    
    --->
    <body class=" max-w-[var(--maxWidth)] w-[100vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500 ">
        <% AccountDAO d = new AccountDAO();
            ContactDAO rq = new ContactDAO();
            Account acc = (Account) session.getAttribute("loginUser");
            String msgIDSearch = request.getParameter("searchIDtxt");
            String msgStatusSearch = request.getParameter("searchStatustxt");
            ArrayList<Request> list = (ArrayList<Request>) request.getAttribute("list");
            int currentP = (int) request.getAttribute("currentPage");
            int total = 0;
            if (acc.getRole().getRoleID() == 1) {
                total = (msgIDSearch != null) ? rq.getTotalContractCLientByContactID(acc.getAccountID(), msgIDSearch) : rq.getTotalAllClientContract(acc.getAccountID());
            } else {
                if (msgIDSearch == "" && msgStatusSearch == "") {
                    total = rq.getTotalAllAdminContract(acc.getAccountID());
                } else if (msgIDSearch != "" && rq.checkNumberInString(msgIDSearch) == 0 && msgStatusSearch == "") {
                    total = rq.getTotalAllAdminContractByContactID(acc.getAccountID(), Integer.parseInt(msgIDSearch));
                } else if (msgIDSearch == "" && msgStatusSearch != "") {
                    total = rq.getTotalAllAdminContractByContractStatus(acc.getAccountID(), Integer.parseInt(msgStatusSearch));
                } else if (msgIDSearch != "" && rq.checkNumberInString(msgIDSearch) == 0 && msgStatusSearch != "") {
                    total = rq.getTotalAllAdminContractByContractStatusAndContactID(acc.getAccountID(), Integer.parseInt(msgStatusSearch), msgIDSearch);
                }
            }

            int endP = total / 5;
            if (total % 5 != 0 || endP == 0) {
                endP++;
            }
        %>

        <div class=" m-[10px] bg-sky-700 p-[5px] text-white">

            <p class="font-semibold text-2xl">CONTRACT INFORMATION</p>


        </div>

        <div class=" content  m-[10px]  p-[10px]  pb-[15px] border-2 rounded-lg     border-gray-400">

            <form class="grid grid-cols-12" action="mainController" method="get" >
                <input type="hidden" name="action" value ="getContract">
                <input type="hidden"class="" name ="currentPagetxt" value="1"> 
                <div class="col-span-9 grid grid-cols-10 ">
                    <div class=" col-span-10 flex justify-start font-bold pl-[10px]">
                        <p>Tìm kiếm ID hợp đồng</p>
                    </div>
                    <div class="col-span-3 flex border-2 border-gray-400 rounded-md " >
                        <!-- neu nhu ma o tran hien tai ma nhan search thi gia tri cua current page giu nguyen con neu curr >page thi sset ve curr 1 --> 

                        <button class=" cursor-pointer bg-gray-100  rounded-l-md   border-2 border-r-gray-400 "><img
                                class="w-[29px] h-[22px]" src="/PrjProject/img/contact/sreach.png" alt=""></button>
                        <input class=" pr-[10px] pl-[10px] text-base w-full outline-none  bg-slate-200 " type="search"
                               name="searchIDtxt" placeholder="Nhập id hợp đồng " value="<%= (msgIDSearch != null) ? msgIDSearch : ""%>">
                    </div>
                    <div class="col-span-7">

                    </div>
                </div>
                <div class=" col-span-3   w-full ">
                    <%if (acc.getRole().getRoleID() != 1) {%>
                    <div class="flex justify-center font-bold">
                        <p>Tìm kiếm trạng thái hợp đồng</p>
                    </div>
                    <div     class="flex justify-end w-full border-2 border-gray-400 rounded-md ">


                        <button class=" cursor-pointer bg-gray-100  rounded-l-md   border-2 border-r-gray-400 ">

                            <img
                                class="w-[29px] h-[22px]" src="/PrjProject/img/contact/sreach.png" alt=""></button>
                        <select class="bg-gray-100 p-[8px] w-full hover:selection:backdrop:bg-none  outline-none"
                                name="searchStatustxt">
                            <%if (msgStatusSearch.equals("")) {%>
                            <option value="" selected>tất cả</option>
                            <option value="0">Chưa xác nhận</option>
                            <option value="1">xác nhận</option>
                            <%} else if (msgStatusSearch.equals("0")) {%>
                            <option value="" >tất cả</option>
                            <option value="0" selected>Chưa xác nhận</option>
                            <option value="1">xác nhận</option>
                            <%} else {%>
                            <option value="" >tất cả</option>
                            <option value="0" >Chưa xác nhận</option>
                            <option value="1" selected>xác nhận</option>
                            <%}%>

                        </select>
                    </div>
                    <%}%>
                </div>
            </form>
            <div class="m-[10px] ">
                <hr>
            </div>

            <table class="w-full border-2  rounded-l-md    border-gray-400 ">

                <tr class="  grid grid-cols-12  text-white bg-slate-500        ">
                    <th class="col-span-2 flex justify-center border-2     border-gray-400">ID hợp đồng</th>
                    <th class="col-span-3 flex justify-center border-2   border-gray-400">Tên hợp đồng </th>
                    <th class="col-span-4 flex justify-center border-2     border-gray-400">Ngày giao dịch</th>
                    <th class="col-span-2 flex justify-center border-2     border-gray-400">Trạng thái</th>
                    <th class="col-span-1 flex justify-center border-2    border-gray-400">Chi tiết</th>
                </tr>


                <%if (list.size() == 0) {%>

                <tr class="  grid grid-cols-12 border-1 rounded-md   ">
                    <td class="col-span-12 flex justify-center border-2  text-2xl       border-gray-400">  < Danh sách Rỗng > </td>

                </tr>



                <%} else {%>
                <%for (Request o : list) {
                %>
                <tr class="  grid grid-cols-12 border-1 rounded-md   ">
                    <td class="col-span-2 flex justify-center border-2        border-gray-400"><%= o.getContact().getContactID()%></td>
                    <td class="col-span-3 flex justify-center border-2   border-gray-400"><%= o.getContact().getService().getServiceName() + " " + o.getContact().getTransaction().getProduct().getName()%></td>
                    <td class="col-span-4 flex justify-center border-2     border-gray-400"><%= new AccountDAO().convertDateToString(o.getContact().getTransaction().getDate())%></td>

                    <%if (Integer.parseInt(o.getContact().getStatus()) == 1) {%>
                    <td class="col-span-2 flex justify-center border-2  option-true  font-bold   border-gray-400">
                        Xác nhận
                    </td>
                    <%} else {%>
                    <td class="col-span-2 flex justify-center border-2  option-false  font-bold   border-gray-400">
                        Chưa xác nhận
                    </td>
                    <%}%>
                    <td id=""
                        class="butonMore col-span-1 flex justify-center border-2 text-green-400 hover:text-green-500 cursor-pointer      border-gray-400"
                        data-popup-id="popup<%= o.getReqID()%>">
                        xem thêm</td>
                </tr>




                <div class="popup" id="popup<%= o.getReqID()%>">
                    <div class="popup-content relative border-[17px] rounded-md border-white  bg-sky-400">
                        <img class="closePopup absolute pt-[2px] pr-[2px] top-[-18px] right-[-18px]  w-[20px] h-[20px] cursor-pointer "
                             src="/PrjProject/img/contact/closeButton.png" alt="">
                        <div class="flex gap-2  mb-[10px] border-2  bg-white border-gray-300 rounded-lg ">
                            <img class="w-[250px] h-auto   " src="<%= o.getContact().getTransaction().getProduct().getThumbnail()%>" alt="">
                            <div class="flex flex-col w-auto border-l-2 border-l-gray-300 text-start gap-0">
                                <p class="font-bold flex justify-center text-xl  bg-gray-400">Thông tin giao dịch</p>
                                <hr class="border-2 border-gray-400">
                                <div class="flex flex-col gap-1">
                                    <%if (acc.getRole().getRoleID() == 1) {%>
                                    <p><span class="font-bold pl-[5px] ">Người Giao dịch:</span>
                                        <%= o.getAcc().getLastName() + " " + o.getAcc().getFirstName()%>
                                    </p>
                                    <%}%>
                                    <p><span class="font-bold pl-[5px]">Dịch vụ:</span><%= o.getContact().getService().getServiceName()%>

                                    </p>
                                    <p><span class="font-bold pl-[5px] ">Tiền dịch vụ:</span>
                                        <%= o.getContact().getService().getServicePrice()%>
                                    </p>
                                    <p> <span class="font-bold pl-[5px] ">Tên sản phẩm:</span><%= o.getContact().getTransaction().getProduct().getName()%>

                                    </p>
                                    <p> <span class="font-bold pl-[5px] ">Giá tiền sản phẩm:</span><%= o.getContact().getTransaction().getProduct().getPrice()%>
                                    </p>
                                    <p> <span class="font-bold pl-[5px] ">Ngày giao dịch:</span>
                                        <%= new AccountDAO().convertDateToString(o.getContact().getTransaction().getDate())%>
                                    </p>
                                    <p> <span class="font-bold pl-[5px] ">Trạng thái yêu cầu:</span>
                                        <%= o.getStatusType().getStatusName()%>
                                    </p>
                                    <p> <span class="font-bold pl-[5px] ">Số lượng:</span><%= o.getContact().getTransaction().getQuantity()%>
                                    </p>
                                    <p> <span class="font-bold pl-[5px] ">Tổng tiền:</span></span><%= o.getContact().getTransaction().getMoney()%>
                                    </p>
                                    <hr class="mt-[5px]">
                                </div>
                            </div>
                        </div>
                        <div class="  w-auto h-auto grid grid-cols-12  gap-0  border-2  border-gray-300 rounded-lg bg-white ">
                            <div class="col-span-12 bg-gray-400">
                                <p class="text-center font-bold text-2xl  mb-[5px]  ">Thông tin thêm</p>
                                <hr>

                            </div>
                            <div class=" flex flex-col col-span-6 border-r-2 border-r-gray-300 p-[5px] ">

                                <p> <span class="font-bold">Tên sản phẩm:</span><%= o.getContact().getTransaction().getProduct().getName()%></p>

                                <p> <span class="font-bold">Loại Sản phẩm:</span> <%= o.getContact().getTransaction().getProduct().getCategory().getName()%></p>
                                <p> <span class="font-bold">Mô tả:</span> <%= o.getContact().getTransaction().getProduct().getDescription()%> </p>
                                <p> <span class="font-bold">tốc độ:</span> <%= o.getContact().getTransaction().getProduct().getSpeed()%></p>
                                <p><span class="font-bold">ID hợp đồng:</span> <%= o.getContact().getContactID()%></p>
                            </div>
                            <div class="col-span-6 flex flex-col gap-2 p-[5px]">
                                <!-- nho sua them display cho thg bendamin -->


                                <p> <span class="font-bold">Tên người Giao dịch:</span>   <%= o.getAcc().getLastName() + " " + o.getAcc().getFirstName()%>
                                </p>

                                <p> <span class="font-bold">Gmail:</span> <%= o.getAcc().getGmail()%> 
                                </p>
                                <p> <span class="font-bold">Số điện thoại:</span> <%= o.getAcc().getPhone()%>
                                </p>
                                <p> <span class="font-bold">Vai trò:</span> <%= o.getAcc().getRole().getRoleName()%>
                                </p>

                            </div>

                        </div>
                    </div>
                </div>
                <%}%>
                <%}%>
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
                        <input type="hidden" name="action" value ="getContract">
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
                        <input type="hidden" name="action" value ="getContract">
                        <input  type="hidden" name="searchIDtxt" value="<%= (msgIDSearch != null) ? msgIDSearch : ""%>"> 
                        <input type="hidden"class="" name ="searchStatustxt" value="<%= (msgStatusSearch != null) ? msgStatusSearch : ""%>"> 

                        <input type="submit"class="cursor-pointer" name ="currentPagetxt" value="<%=i%>">                                      
                    </form>
                </li>

                <%} else {%>
                <li class="pagination hover:bg-cyan-400 hover:text-white">
                    <!-- ti sua lai post -->
                    <form action="mainController" class="cursor-pointer pagination-item_link " method="get">
                        <input type="hidden" name="action" value ="getContract">

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
                        <input type="hidden" name="action" value ="getContract">
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
            <div  class="fixed left-0 bottom-12 mb-[75px] cursor-pointer">
                <form action="mainController" method="get">
                    <button name="action" value="<%= CONSTANTS.GETHOMEPAGELOGIN%>"> <img class="w-[70px] " src="/PrjProject/img/profile/home.png" alt="">
                    </button>
                </form>
            </div>
        </div>



    </body>
    <script>
        const buttons = document.querySelectorAll(".butonMore");

        buttons.forEach(function (button) {
            button.addEventListener("click", function () {
                const popupId = button.getAttribute("data-popup-id");
                const popup = document.getElementById(popupId);
                if (popup) {
                    popup.style.display = "flex";
                }
            });
        });

        document.querySelectorAll(".closePopup").forEach(function (closeButton) {
            closeButton.addEventListener("click", function () {
                const popup = closeButton.closest('.popup');
                if (popup) {
                    popup.style.display = "none";
                }
            });
        });

    </script>

</html>
