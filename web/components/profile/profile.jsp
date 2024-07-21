<%-- 
    Document   : profileNew
    Created on : Mar 5, 2024, 1:35:47 AM
    Author     : Lenovo
--%>

<%@page import="DAO.AccountDAO"%>
<%@page import="controllers.CONSTANTS"%>
<%@page import="DTO.Request"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DAO.RequestDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="DTO.Employee"%>
<%@page import="DTO.Employee"%>
<%@page import="DTO.Employee"%>
<%@page import="DTO.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdn.tailwindcss.com"></script>

        <style>
            #block {
                width: auto;
                height: auto;
                display: flex;
            }

            .diva {
                visibility: hidden;
                width: 0%;
                height: auto;
            }

            .divb {
                visibility: hidden;
                width: 0%;
                height: auto;
            }

            .open {
                visibility: visible;
                width: 100%;
            }

            .status {
                border-radius: 5px;
                border: 3px solid white;
                background-color: white;
                outline: none;
                /* chuyen dong cham */
                transition-property: all;
                transition-duration: 0.5s;
                transition-timing-function: ease;
            }

            /* pagination */

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

            .popup-content {
                height: 400px;
                width: 520px;
                background-color: white;
                padding: 10px;
                border-radius: 5px;
                position: relative;
            }

        </style>

    </head>
    <body class="max-w-[var(--maxWidth)] w-[100vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500">


        <%
            Account acc = (Account) session.getAttribute("loginUser");


        %>

        <div class="title flex w-[full] bg-sky-700 text-white h-[110px] items-center text-[20px] shadow-sm pl-[103px]">
            HỒ SƠ CỦA TÔI
        </div>

        <div class="content grid grid-cols-12 gap-5 p-[40px] ">
            <div
                class="avatar_content   bg-gray-100 radius rounded-lg border-2 border-sky-700 col-span-12 xl:col-span-4 flex flex-col items-center  h-full">
                <div class="img_avatar mt-[64px]">
                    <img src="/PrjProject/img/profile/img_profile.png" alt="">
                </div>
                <div class="tilte_name mb-0">
                    <span class="text-cyan-500">Role:</span> <%= acc.getRole().getRoleName()%> 
                </div>

            </div>
            <!-- phan ben display ne  -->
            <div
                class="infor_content grid md:grid-cols-2 p-[16px] pb-[60px]  bg-sky-700 rounded-lg border-2 border-sky-700  col-span-12 xl:col-span-8 ">
                <div
                    class=" col-span-2 grid grid-cols-2  rounded-md border-1  mt-[10px] p-[4px] mb-[20px] ml-[50px] mr-[50px]  lg:ml-[150px] lg:mr-[150px] bg-slate-200">
                    <% if (acc.getRole().getRoleID() == 1) { %>
                    <button id="btn_diva"
                            class="h-full col-span-1 flex justify-center rounded-md border-1  gap-2  pt-[14px] pb-[14px] pl-[20px] pr-[20px]">
                        <img class="w-[20px]" src="/PrjProject/img/profile/icon_proflie.png" alt="">
                        <p>Hồ sơ của tôi</p>
                    </button>


                    <button id="btn_divb"
                            class="h-full col-span-1 flex justify-center   gap-2 pt-[14px] pb-[14px] pl-[20px] pr-[20px]">
                        <img class="w-[27px]" src="/PrjProject/img/profile/device.png" alt="">
                        <p>thiết bị của tôi</p>

                    </button>
                    <%}%>



                    <%if (acc.getRole().getRoleID() != 1) { %>
                    <button id="btn_diva"
                            class="h-full col-span-2 flex justify-center rounded-md border-1  gap-2  pt-[14px] pb-[14px] pl-[20px] pr-[20px]">
                        <img class="w-[20px]" src="/PrjProject/img/profile/icon_proflie.png" alt="">
                        <p>Hồ sơ của tôi</p>
                    </button>

                    <%}%>
                </div>
                <!-- account detail -->

                <div id="block" class="col-span-2 w-full h-auto  grid grid-cols-2 bg-gray-100  rounded-lg border-2 text-slate-500">
                    <div id="diva"
                         class="diva col-span-2   grid grid-cols-2 bg-gray-100  rounded-lg border-2 text-slate-500  ">
                        <div class=" hidden  col-span-1 lg:flex items-center pl-[24px]">
                            <p>Tài Khoản</p>

                        </div>
                        <div
                            class=" flex flex-col gap-3 justify-center lg:block lg:justify-normal lg:gap-0 lg:ml-0 lg:mr-0 col-span-2 lg:col-span-1 email_infor p-[16px] ml-[25px] mr-[25px] md:ml-[150px] md:mr-[150px]   ">
                            <p class="lg:hidden text-lg">Account</p>
                            <p>Email</p>
                            <div class="bg-gray-100 flex  mr-[65px] items-center  border-2  border-gray-200">
                                <img class="h-[20px] pl-[4px]" src="/PrjProject/img/profile/email_icon.png" alt="">
                                <p class="p-[8px] "><%= acc.getGmail()%> </p>
                            </div>

                        </div>
                        <div class="col-span-2 mt-[24px] mb-[24px]">
                            <hr>
                        </div>

                        <div class=" hidden col-span-1 lg:flex items-center pl-[24px]">
                            <p>Thông tin cá nhân</p>
                        </div>

                        <div
                            class=" col-span-2 lg:col-span-1   email_infor ml-[25px] mr-[25px] md:ml-[150px] md:mr-[150px]   lg:ml-0 lg:mr-0  p-[16px] mb-[16px]">
                            <p class="lg:hidden text-lg mb-[10px]">Personal informations</p>
                            <p>Full name</p>
                            <div class="bg-gray-100 flex mr-[65px] items-center  border-2  border-gray-200">
                                <img class="h-[22px] pl-[4px]" src="/PrjProject/img/profile/personIcon.png" alt="">
                                <p class="p-[8px] "><%=  acc.getLastName().trim() + " " + acc.getFirstName().trim()%></p>
                            </div>

                            <p class="mt-[20px] ">Phone number</p>
                            <div class="bg-gray-100 flex mr-[65px] items-center  border-2  border-gray-200">
                                <img class="h-[22px] pl-[4px]" src="/PrjProject/img/profile/phone.png" alt="">
                                <p class="p-[8px] "><%= acc.getPhone()%></p>
                            </div>

                        </div>



                        <div class="col-span-2 mt-[24px] mb-[24px]">
                            <hr>
                        </div>

                        <div class="hidden col-span-1 lg:flex items-center pl-[24px]">
                            <p>Other informations</p>
                        </div>

                        <div
                            class="col-span-2 lg:col-span-1   email_infor ml-[25px] mr-[25px] md:ml-[150px] md:mr-[150px]   lg:ml-0 lg:mr-0  p-[16px] mb-[16px]">
                            <p class="lg:hidden text-lg mb-[10px]">Other informations</p>




                            <p class="mt-[20px] ">Sex</p>
                            <div class="bg-gray-100 flex mr-[65px] items-center  border-2  border-gray-200">
                                <img class="h-[22px] pl-[4px]" src="/PrjProject/img/profile/sex.png" alt="">
                                <p class="p-[8px] "><%= acc.getSex()%></p>
                            </div> 


                        </div>

                        <div class="col-span-2 mt-[24px] mb-[0px]">
                            <hr>
                            <%
                                String msg = (String) request.getAttribute("NOTIFYPRO");
                            %>
                            <p class="font-bold text-lg text-green-500 flex justify-center" > <%= (msg != null) ? msg : ""%></p>
                        </div>

                        <div class="col-span-2 mt-[10px] mb-[24px] mr-[32px] flex justify-end">

                            <form action="mainController" method="get">
                                <input type="hidden" name="action" value="viewuppro" >
                                <button 
                                    class="statusButton flex items-center  bg-green-400 hover:bg-green-600 text-white font-bold py-2 px-4 rounded">
                                    <img class="h-[16px] m-[8px] " src="/PrjProject/img/profile/reset.png" alt="">
                                    cập nhật
                                </button>
                            </form>

                        </div>

                    </div>
                    <% if (acc.getRole().getRoleID() == 1) {
                            RequestDAO rq = new RequestDAO();
                            // lai du lieu danh sach thiet bi mua max vbao nhieu de chia trang
                            // o day msgsreach that ra ID product ma ng dung sai sreach 
                            String msgSreach = (String) request.getAttribute("searchStatus");
                            ;
                            int total = (msgSreach == null) ? (rq.getTotalRequest(acc.getAccountID())) : (rq.getTotalDeviceBySearching(acc, (int) request.getAttribute("proID")));

                            int endP = total / 2;
                            if (total % 2 != 0 || endP == 0) {
                                endP++;
                            }

                            int indexList = (int) request.getAttribute("indexList");

                            ArrayList<Request> myDevice = (ArrayList<Request>) request.getAttribute("listDevice");
                            // check thu phan tu co ton tai trong list ko neu nhu list = 0 thi in ra list rong

                    %>
                    <div id="divb"
                         class="divb col-span-2  grid grid-cols-2 h-full bg-white  rounded-lg border-2 text-slate-500   p-[16px] pb-0  ">
                        <form action="mainController" method="post"  class="w-full  col-span-2 border-2 gap-3 mb-[10px]   border-gray-300 rounded-md flex">
                            <img class=" h-[18px]" src="/PrjProject/img/profile/sreach.png" alt="">
                            <input type="hidden" name="action" value ="getdevi">
                            <input type="hidden"class="" name ="index" value="1">    
                            <input class="w-full outline-none " placeholder="Tìm kiếm ID sản phẩm" type="search" name="searchtxt" value="<%= (msgSreach == null) ? "" : msgSreach%>"  > 
                            <button class="border-2 rounded-md bg-slate-300 text-white pl-[5px] pr-[5px] font-bold border-gray-400 cursor-pointer hover:text-gray-600  "> find </button>
                        </form>


                        <table class="w-full h-auto mb-[10px] col-span-2 border-2   ">
                            <!-- div tung product ne -->

                            <tr class="grid grid-cols-12 ">
                                <th class="col-span-2 flex justify-center border-2 rounded-l-sm     border-gray-400">id</th>
                                <th class="col-span-3 flex justify-center border-2   border-gray-400">Sản phẩm</th>
                                <th class="col-span-4 flex justify-center border-2     border-gray-400">ngày mua</th>
                                <th class="col-span-2 flex justify-center border-2     border-gray-400">trạng thái</th>
                                <th class="col-span-1 flex justify-center border-2     border-gray-400">chi tiết</th>
                            </tr>


                            <%
                                if (total > 0) {
                                    for (Request list : myDevice) {
                            %>
                            <tr class="grid grid-cols-12 ">
                                <td class="col-span-2 flex justify-center border-2     border-gray-400"><%= list.getContact().getTransaction().getProduct().getPrd_ID()%></td>
                                <td class="col-span-3 flex justify-center border-2     border-gray-400"><%= list.getContact().getTransaction().getProduct().getName()%></td>


                                <!-- fomat lai-->

                                <td class="col-span-4 flex justify-center border-2     border-gray-400"><%= rq.formatDate(list.getContact().getTransaction().getDate())%></td>
                                <td class="col-span-2 flex justify-center border-2     border-gray-400"><%= list.getStatusType().getStatusName()%></td>
                                <td
                                    class=" butonMore col-span-1 flex justify-center  cursor-pointer text-xs text-green-400 hover:text-green-600 border-2    border-gray-400"
                                    data-popup-id="popup<%=list.getReqID()%>"
                                    >
                                    xem thêm </td>
                            </tr>

                            <div class="popup"  id="popup<%=list.getReqID()%>">

                                <div class="popup-content">
                                    <img class= "closePopup absolute pt-[2px] pr-[2px] top-[0px] right-[0px] w-[20px] h-[20px] cursor-pointer " src="/PrjProject/img/profile/closeButton.png" alt="">
                                    <div class="flex gap-4 mb-[10px]">
                                        <img class="w-[250px] border-2 border-gray-300 rounded-lg " src="<%= list.getContact().getTransaction().getProduct().getThumbnail()%>" alt="">
                                        <div class="flex flex-col w-full text-start gap-3">
                                            <p class="font-bold text-2xl " >Thông tin sản phẩm</p>
                                            <p> <span class="font-bold">Tên sản phẩm:</span> <%=list.getContact().getTransaction().getProduct().getName()%></p>
                                            <p>  <span class="font-bold">Giá tiền:</span> <%= list.getContact().getTransaction().getMoney()%></p>
                                            <p>  <span class="font-bold">Ngày giao dịch:</span> <%= new AccountDAO().convertDateToString(list.getContact().getTransaction().getDate())%> </p>
                                            <p> <span class="font-bold">Số lượng:</span> <%= list.getContact().getTransaction().getQuantity()%>  </p>
                                        </div>
                                    </div>
                                    <div class=" w-full h-auto flex flex-col gap-2  border-2  border-gray-300 rounded-lg p-[5px]">
                                        <p class="text-center">thong tin chi tiet</p>
                                        <p><span class="font-bold">Người nhận:</span> <%= list.getAcc().getLastName() + " " + list.getAcc().getFirstName()%> </p>
                                        <p><span class="font-bold">Dịch vụ:</span> <%= list.getContact().getService().getServiceName() + " " + list.getContact().getTransaction().getProduct().getName()%></p>
                                        <p><span class="font-bold">ID giao dịch:</span> <%= list.getContact().getTransaction().getTranID()%></p>
                                        <p><span class="font-bold">ID hợp đồng:</span>  <%= list.getContact().getContactID()%> </p>
                                    </div>
                                </div>

                            </div>

                            <% }
                            } else {%>
                            <tr class="grid grid-cols-12 ">
                                <th class="col-span-12 flex justify-center border-2 rounded-l-sm  font-semibold text-2xl     border-gray-400"> < Danh sách rỗng ></th>

                            </tr>
                            <%}%>
                        </table>

                        <ul class="pagination mb-[10px] gap-3 text-gray-500 ">
                            <% if ((indexList - 1) != 0) {
                            %>
                            <li class="pagination hover:bg-cyan-400 hover:text-white">
                                <form   class="pagination-item_link cursor-pointer" action="mainController" method="post">
                                    <!-- ti sua lai post -->
                                    <input type="hidden" name="action" value ="getdevi">
                                    <input  type="hidden" name="searchtxt" value="<%= (msgSreach == null) ? "" : msgSreach%>"> 
                                    <input type="hidden"class="" name ="index" value="<%= (indexList - 1)%>">    
                                    <button type="submit" class="cursor-pointer hover:bg-cyan-400 hover:text-white "><</button>
                                </form>
                            </li>
                            <%
                                }
                            %>


                            <%for (int i = (((endP - indexList) == 0) ? (indexList - 1) : indexList); i <= Math.min(indexList + 1, endP); i++) {
                            %>    
                            <% if (i == indexList && i > 0) {%>
                            <li class="pagination-item-active hover:bg-cyan-400 hover:text-white">
                                <form   class="pagination-item_link cursor-pointer" action="mainController" method="post">
                                    <!-- ti sua lai post -->
                                    <input type="hidden" name="action" value ="getdevi">
                                    <input  type="hidden" name="searchtxt" value="<%= (msgSreach == null) ? "" : msgSreach%>"> 
                                    <input type="submit"class="cursor-pointer" name ="index" value="<%= i%>">                                      
                                </form>
                            </li>

                            <%} else if (i > 0) {%>
                            <li class="pagination hover:bg-cyan-400 hover:text-white">

                                <!-- ti sua lai post -->
                                <form action="mainController" class="cursor-pointer pagination-item_link " method="post">
                                    <input type="hidden" name="action" value ="getdevi">
                                    <input  type="hidden" name="searchtxt" value="<%= (msgSreach == null) ? "" : msgSreach%>"> 
                                    <input type="submit" class="cursor-pointer" name ="index" value="<%= i%>">
                                </form>

                            </li>

                            <%}
                            %>
                            <% }
                            %>

                            <% if (((indexList + 1) <= endP) || (indexList) < endP) {%>
                            <li class="pagination hover:bg-cyan-400 hover:text-white">
                                <form   class="pagination-item_link cursor-pointer" action="mainController" method="post">
                                    <!-- ti sua lai post -->
                                    <input type="hidden" name="action" value ="getdevi">
                                    <input  type="hidden" name="searchtxt" value="<%= (msgSreach == null) ? "" : msgSreach%>"> 
                                    <input type="hidden"class="" name ="index" value="<%= (indexList + 1)%>">    
                                    <button type="submit" class="cursor-pointer hover:bg-cyan-400 hover:text-white "> > </button>
                                </form>
                            </li>
                            <%}%>


                        </ul>

                    </div>

                </div>
                <%}%>
            </div>
        </div>


        <div  class="fixed left-0 bottom-12 mb-[75px] cursor-pointer">
            <form action="mainController" method="get">
                <button name="action" value="<%= CONSTANTS.GETHOMEPAGELOGIN%>"> <img class="w-[70px] " src="/PrjProject/img/profile/home.png" alt="">
                </button>
            </form>
        </div>

    </body>
    <script>
        var btn_diva = document.getElementById("btn_diva");
        var btn_divb = document.getElementById("btn_divb");
        var diva = document.getElementById("diva");
        var divb = document.getElementById("divb");

        <% String showDivb = (String) request.getAttribute("myDevice");
            if (showDivb == null) {
        %>
        divb.style.display = 'none';
        diva.style.display = 'grid';
        diva.classList.add("open");
        btn_diva.classList.add("status");
        btn_divb.classList.remove("status");
        divb.classList.remove("open");
        <%} else if (showDivb != null) {%>
        btn_divb.classList.add("status");
        btn_diva.classList.remove("status");
        divb.style.display = 'block';
        diva.classList.remove("open");
        divb.classList.add("open");
        diva.style.display = 'none';
        <%}%>




        btn_diva.addEventListener('click', () => {
            btn_diva.classList.add("status");
            btn_divb.classList.remove("status");
            diva.style.display = 'grid';
            diva.classList.add("open");
            divb.classList.remove("open");
            divb.style.display = 'none';
        });
        btn_divb.addEventListener('click', () => {

            btn_divb.classList.add("status");
            btn_diva.classList.remove("status");
            divb.style.display = 'block';
            diva.classList.remove("open");
            divb.classList.add("open");
            diva.style.display = 'none';
        });

//        mo them chi tiet ve san pham


        const buttons = document.querySelectorAll(".butonMore");
        buttons.forEach(function (button) {
            button.addEventListener("click", function () {
                const popupId = button.getAttribute("data-popup-id");
                const popup = document.getElementById(popupId); // Sử dụng ID để chọn popup cụ thể
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
