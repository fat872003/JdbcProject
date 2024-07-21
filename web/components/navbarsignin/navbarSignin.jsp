<%-- 
    Document   : navbar
    Created on : Feb 1, 2024, 4:34:43 AM
    Author     : ACER
--%>

<%@page import="DAO.ServiceDAO"%>
<%@page import="DTO.Account"%>

<%@page import="controllers.CONSTANTS"%>
<%@page import="DTO.Service"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">



    <head>
        <title>Title</title>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="/PrjProject/components/navbarsignin/stylenavbar.css"/>
    </head>

    <body class="max-w-[var(--maxWidth)] w-[95vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500">
        <!-- navbar  ------>

        <%

            Account acc = (Account) session.getAttribute("loginUser");


        %>
        <div class=" sticky top-0 z-40">
            <nav class="relative max-w-[var(--maxWidth)] h-[var(--minNavHeight)] px-4 py-4 flex justify-between items-center bg-white z-40"
                 id="navbar">
                <a class="text-3xl font-bold leading-none" href="mainController?action=gethomepagelogin" >
                    <img src="/PrjProject/img/navbar/logo.png" alt="logo" class="h-20 w-20">
                </a>
                <div class="lg:hidden">
                    <button class="navbar-burger flex items-center text-blue-600 p-3">
                        <svg class="block h-4 w-4 fill-current" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                        <title>Mobile menu</title>
                        <path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z"></path>
                        </svg>
                    </button>
                </div>
                <div class="flex flex-col">
                    <ul
                        class="hidden absolute top-1/2 left-1/2 transform -translate-y-1/2 -translate-x-1/2  lg:mx-auto lg:flex lg:items-center lg:w-auto lg:space-x-6">
                        <li>
                            <a class="text-sm activeNav" href="mainController?action=gethomepagelogin">Home</a>
                        </li>
                        <li class="text-gray-300">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" stroke="currentColor"
                                 class="w-4 h-4 current-fill" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M12 5v0m0 7v0m0 7v0m0-13a1 1 0 110-2 1 1 0 010 2zm0 7a1 1 0 110-2 1 1 0 010 2zm0 7a1 1 0 110-2 1 1 0 010 2z" />
                            </svg>
                        </li>
                        <li id="serviceNav">
                            <a class="text-sm non-actNav" href="#">Services</a>
                            <i class="fa fa-caret-down"></i>
                        </li>
                        <li class="text-gray-300">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" stroke="currentColor"
                                 class="w-4 h-4 current-fill" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M12 5v0m0 7v0m0 7v0m0-13a1 1 0 110-2 1 1 0 010 2zm0 7a1 1 0 110-2 1 1 0 010 2zm0 7a1 1 0 110-2 1 1 0 010 2z" />
                            </svg>
                        </li>
                        <li id="memNav">
                            <a class="text-sm non-actNav" href="#">Member</a>
                            <i class="fa fa-caret-down"></i>
                        </li>
                        <li class="text-gray-300">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" stroke="currentColor"
                                 class="w-4 h-4 current-fill" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M12 5v0m0 7v0m0 7v0m0-13a1 1 0 110-2 1 1 0 010 2zm0 7a1 1 0 110-2 1 1 0 010 2zm0 7a1 1 0 110-2 1 1 0 010 2z" />
                            </svg>
                        </li>
                        <li id="supportNav">
                            <a class="text-sm text-gray-400 hover:text-gray-500" href="#">Support</a>
                            <i class="fa fa-caret-down"></i>
                        </li>
                        <li class="text-gray-300">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" stroke="currentColor"
                                 class="w-4 h-4 current-fill" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M12 5v0m0 7v0m0 7v0m0-13a1 1 0 110-2 1 1 0 010 2zm0 7a1 1 0 110-2 1 1 0 010 2zm0 7a1 1 0 110-2 1 1 0 010 2z" />
                            </svg>
                        </li>
                        <li id="">
                            <a class="text-sm text-gray-400 hover:text-gray-500" href="#">Liên hệ</a>
                        </li>
                    </ul>

                </div>

                <img class="w-[45px] cursor-pointer" onclick="toggleMenu()" src="/PrjProject/img/navbarSignin/img_profile.png" alt="">


            </nav>
            <div class="navbar-menu relative  hidden">
                <div class="navbar-backdrop fixed inset-0 bg-gray-800 opacity-25"></div>
                <nav class="fixed top-0 left-0 bottom-0 flex flex-col w-5/6 max-w-sm py-6 px-6 bg-white overflow-y-auto">
                    <div class="flex items-center mb-8">
                        <a class="mr-auto text-3xl font-bold leading-none" href="mainController?action=getloginpage">
                            <svg class="h-12" alt="logo" viewBox="0 0 10240 10240">
                            <path xmlns="http://www.w3.org/2000/svg"
                                  d="M8284 9162 c-2 -207 -55 -427 -161 -667 -147 -333 -404 -644 -733 -886 -81 -59 -247 -169 -256 -169 -3 0 -18 -9 -34 -20 -26 -19 -344 -180 -354 -180 -3 0 -29 -11 -58 -24 -227 -101 -642 -225 -973 -290 -125 -25 -397 -70 -480 -80 -22 -3 -76 -9 -120 -15 -100 -13 -142 -17 -357 -36 -29 -2 -98 -7 -153 -10 -267 -15 -436 -28 -525 -40 -14 -2 -45 -7 -70 -10 -59 -8 -99 -14 -130 -20 -14 -3 -41 -7 -60 -11 -19 -3 -39 -7 -45 -8 -5 -2 -28 -6 -50 -10 -234 -45 -617 -165 -822 -257 -23 -10 -45 -19 -48 -19 -7 0 -284 -138 -340 -170 -631 -355 -1107 -842 -1402 -1432 -159 -320 -251 -633 -308 -1056 -26 -190 -27 -635 -1 -832 3 -19 7 -59 10 -89 4 -30 11 -84 17 -120 6 -36 12 -77 14 -91 7 -43 33 -174 39 -190 3 -8 7 -28 9 -45 6 -35 52 -221 72 -285 7 -25 23 -79 35 -120 29 -99 118 -283 189 -389 67 -103 203 -244 286 -298 75 -49 178 -103 196 -103 16 0 27 16 77 110 124 231 304 529 485 800 82 124 153 227 157 230 3 3 28 36 54 74 116 167 384 497 546 671 148 160 448 450 560 542 14 12 54 45 90 75 88 73 219 172 313 238 42 29 77 57 77 62 0 5 -13 34 -29 66 -69 137 -149 405 -181 602 -7 41 -14 82 -15 90 -1 8 -6 46 -10 83 -3 37 -8 77 -10 88 -2 11 -7 65 -11 122 -3 56 -8 104 -9 107 -2 3 0 12 5 19 6 10 10 8 15 -10 10 -34 167 -346 228 -454 118 -210 319 -515 340 -515 4 0 40 18 80 40 230 128 521 255 787 343 118 40 336 102 395 113 28 5 53 11 105 23 25 5 59 12 75 15 17 3 41 8 55 11 34 7 274 43 335 50 152 18 372 29 565 29 194 0 481 -11 489 -19 2 -3 -3 -6 -12 -6 -9 -1 -20 -2 -24 -3 -33 -8 -73 -16 -98 -21 -61 -10 -264 -56 -390 -90 -649 -170 -1243 -437 -1770 -794 -60 -41 -121 -82 -134 -93 l-24 -18 124 -59 c109 -52 282 -116 404 -149 92 -26 192 -51 220 -55 17 -3 64 -12 105 -21 71 -14 151 -28 230 -41 19 -3 46 -7 60 -10 14 -2 45 -7 70 -10 25 -4 56 -8 70 -10 14 -2 53 -7 88 -10 35 -4 71 -8 81 -10 10 -2 51 -6 92 -9 101 -9 141 -14 147 -21 3 -3 -15 -5 -39 -6 -24 0 -52 -2 -62 -4 -21 -4 -139 -12 -307 -22 -242 -14 -700 -7 -880 13 -41 4 -187 27 -250 39 -125 23 -274 68 -373 111 -43 19 -81 34 -86 34 -4 0 -16 -8 -27 -17 -10 -10 -37 -33 -59 -52 -166 -141 -422 -395 -592 -586 -228 -257 -536 -672 -688 -925 -21 -36 -43 -66 -47 -68 -4 -2 -8 -7 -8 -11 0 -5 -24 -48 -54 -97 -156 -261 -493 -915 -480 -935 2 -3 47 -21 101 -38 54 -18 107 -36 118 -41 58 -25 458 -138 640 -181 118 -27 126 -29 155 -35 14 -2 45 -9 70 -14 66 -15 137 -28 300 -55 37 -7 248 -33 305 -39 28 -3 84 -9 125 -13 163 -16 792 -8 913 12 12 2 58 9 102 15 248 35 423 76 665 157 58 19 134 46 170 60 86 33 344 156 348 166 2 4 8 7 13 7 14 0 205 116 303 184 180 126 287 216 466 396 282 281 511 593 775 1055 43 75 178 347 225 455 100 227 236 602 286 790 59 220 95 364 120 485 6 28 45 245 50 275 2 14 7 41 10 60 3 19 8 49 10 65 2 17 6 46 9 65 15 100 35 262 40 335 3 39 8 89 10 112 22 225 33 803 21 1043 -3 41 -7 129 -11 195 -3 66 -8 136 -10 155 -2 19 -6 76 -10 125 -3 50 -8 101 -10 115 -2 14 -6 57 -10 95 -7 72 -12 113 -20 175 -2 19 -7 55 -10 80 -6 46 -43 295 -51 340 -2 14 -9 54 -15 90 -5 36 -16 97 -24 135 -8 39 -17 84 -20 100 -12 68 -18 97 -50 248 -19 87 -47 204 -61 260 -14 56 -27 109 -29 117 -30 147 -232 810 -253 832 -4 4 -7 -23 -8 -60z">
                            </path>
                            </svg>
                        </a>
                        <button class="navbar-close">
                            <svg class="h-6 w-6 text-gray-400 cursor-pointer hover:text-gray-500"
                                 xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M6 18L18 6M6 6l12 12"></path>
                            </svg>
                        </button>
                    </div>
                    <div>
                        <ul>
                            <li class="mb-1">
                                <a class="block p-4 text-sm font-semibold text-gray-400 hover:bg-blue-50 hover:text-blue-600 rounded"
                                   href="mainController?action=getloginpage">Home</a>
                            </li>
                            <li class="mb-1">
                                <a class="block p-4 text-sm font-semibold text-gray-400 hover:bg-blue-50 hover:text-blue-600 rounded"
                                   href="#">Services</a>
                            </li>
                            <li class="mb-1">
                                <a class="block p-4 text-sm font-semibold text-gray-400 hover:bg-blue-50 hover:text-blue-600 rounded"
                                   href="#">Pricing</a>
                            </li>
                            <li class="mb-1">
                                <a class="block p-4 text-sm font-semibold text-gray-400 hover:bg-blue-50 hover:text-blue-600 rounded"
                                   href="#">Contact</a>
                            </li>
                            <li class="mb-1">
                                <a class="block p-4 text-sm font-semibold text-gray-400 hover:bg-blue-50 hover:text-blue-600 rounded"
                                   href="#">Liên hệ</a>
                            </li>
                        </ul>
                    </div>
                    <div class="mt-auto">
                        <div class="pt-6">
                            <button
                                class="block px-4 py-3 mb-3  text-xs text-center font-semibold leading-none bg-gray-50 hover:bg-gray-100 rounded-xl">Sign
                                in</button>
                            <button
                                class="block px-4 py-3 mb-2 leading-loose text-xs text-center text-white font-semibold bg-blue-600 hover:bg-blue-700 rounded-xl">Sign
                                Up</button>
                        </div>
                        <p class="my-4 text-xs text-center text-gray-400">
                            <span>Copyright © 2021</span>
                        </p>
                    </div>
                </nav>
            </div>
            <!-- <div class="invisibleHeader "></div> -->
            <div class="absolute w-screen flex justify-center">
                <div id="servicePop"
                     class="absolute top-0 w-[30vw] bg-white z-30 transition-all ease-in-out duration-200 translate-y-[-110%]">
                    <div class="w-full h-full p-3 grid grid-cols-3">
                        <div class="col-span-3 font-bold">Danh sách dịch vụ</div>
                        <%                            ServiceDAO ser = new ServiceDAO();
                            // in ra dich vu cho nabar moi lan qua trang nay

                            ArrayList<Service> serList = ser.getAllService();
                            if (serList != null && serList.size() > 0) {
                                for (Service item : serList) {
                        %>
                        <div class="col-span-1 ">
                            <form action="mainController">
                                <%
                                    if (item.getServicePrice() == 0) {
                                %>
                                <input type="hidden" name="action" value=<%=CONSTANTS.GETPRODUCTS%>>
                                <%
                                } else {
                                %>
                                <input type="hidden" name="action" value=<%=CONSTANTS.APPLICATION%>>
                                <input type="hidden" name="applicationID" value=<%=    item.getId()%>>
                                <%
                                    }
                                %>
                                <button class="" type="submit">
                                    <span class="capitalize"><%=         item.getServiceName()%></span>

                                    <!--<input type="hidden" name="prdSection"  value="<%%>"  />--> 

                                </button>
                            </form>

                        </div>
                        <%
                                }

                            }

                        %>
                    </div>
                </div>
                <div id="memPop"
                     class="absolute top-0 w-[30vw] bg-white z-30 transition-all ease-in-out duration-200 translate-y-[-110%]">
                    <div class="w-full h-full p-3 grid grid-cols-3">
                        <div class="col-span-1">
                            <div class="font-bold">Thanh toán</div>
                            <div class="">Xem giỏ hàng...</div>
                            <div class="">Xem giỏ khác...</div>
                        </div>
                        <div class="col-span-1">
                            <div class="font-bold">Giới thiệu</div>
                            <div class="">Giới thiệu bạn bè</div>
                            <div class="">Chính sách và hướng dẫn...</div>
                            <div class="">Lịch sử giới thiệu</div>
                        </div>
                        <div class="col-span-1">
                            <div class="font-bold">Hỗ trợ</div>
                            <div class="">Gửi yêu cầu</div>
                            <div class="">Liên hệ...</div>
                        </div>
                    </div>
                </div>
                <div id="supportPop"
                     class="absolute top-0 w-[30vw] bg-white z-30 transition-all ease-in-out duration-200 translate-y-[-110%]">
                    <div class="w-full h-full p-3 grid grid-cols-4 font-bold">
                        <form class="col-span-2" action="mainController" method="get">
                            <button   type="submit" name="action" value="getreq">
                                <input  type="hidden" name="searchIDtxt" value="" />
                                <input  type="hidden" name="searchStatustxt" value="" />
                                <input  type="hidden" name="currentPagetxt" value="1" />
                                <p>Xem đơn</p>
                            </button>
                        </form>
                        <form class="col-span-2" action="mainController" method="get">
                            <button   type="submit" name="action" value="getCreatereq">
                                
                                <p>Tạo đơn</p>
                            </button>
                        </form>
                    </div>
                </div>
                <div class="sub-menu-wrap " id="subMenu">
                    <div class="sub-menu" >
                        <div class="user-infor">
                            <img class="" src="/PrjProject/img/navbarSignin/img_profile.png" alt="">
                            <!-- cai ten o day -->
                            <h3 class="text-lg font-bold">
                                <%= acc.getLastName().trim() + " " + acc.getFirstName().trim()%>

                            </h3>
                        </div>
                        <hr>
                        <a class="sub-menu-link" href="mainController?action=getpro">
                            <img src="/PrjProject/img/navbarSignin/icon_proflie.png" alt="">
                            <p>Hồ sơ</p>
                        </a>

                        <%if (acc.getRole().getRoleID() == 1) {%>
                        <a class="sub-menu-link" href="">
                            <img src="/PrjProject/img/navbarSignin/shopping.png" alt="">
                            <p>Giỏ hàng</p>
                        </a>



                        <%}%>

                         
                        <form action="mainController" method="post">
                            <button class="sub-menu-link mb-[2px] " id="sub-menu-link-admin"  type="submit" name="action" value="getCreatereq">

                                <img src="/PrjProject/img/navbarSignin/request.png" alt="">
                                <p>Tạo yêu cầu</p>
                            </button>
                        </form>


                        <!-- yều càu là bên trong sẽ chia ra my request và history -->



                        <!-- xem thu thg technician co can hien dang ki ko -->


                        <form action="mainController" method="get">
                            <button class="sub-menu-link mb-[2px] " id="sub-menu-link-admin"  type="submit" name="action" value="getreq">
                                <input  type="hidden" name="searchIDtxt" value="" />
                                <input  type="hidden" name="searchStatustxt" value="" />
                                <input  type="hidden" name="currentPagetxt" value="1" />
                                <img src="/PrjProject/img/navbarSignin/register.png" alt="">
                                <p>lịch sử yêu cầu</p>
                            </button>
                        </form>

                        <form action="mainController" method="post">
                            <button class="sub-menu-link mb-[2px] " id="sub-menu-link-admin"  type="submit" name="action" value="getContract">
                                <input  type="hidden" name="searchIDtxt" value="" />
                                <input  type="hidden" name="searchStatustxt" value="" />
                                <!-- truyen du lieu trang dau tien --->
                                <input  type="hidden" name="currentPagetxt" value="1" />
                                <img src="/PrjProject/img/navbarSignin/contract.png" alt="">
                                <p>Hợp đồng</p>
                            </button>
                        </form>

                        <form class="sub-menu-link" action="mainController" method="get">
                            <button class="sub-menu-link mb-[2px] " id="sub-menu-link-admin"  type="submit" name="action" value="getTran">
                                <img src="/PrjProject/img/navbarSignin/payment.png" alt="">
                                <input  type="hidden" name="searchIDtxt" value="" />
                                <input  type="hidden" name="searchStatustxt" value="" />
                                <input  type="hidden" name="searchMonthtxt" value="all" />                             
                                <input  type="hidden" name="currentPagetxt" value="1" />
                                <p>Giao dịch</p>
                            </button>
                        </form>

                        <a class="sub-menu-link" href="mainController?action=getLogout">
                            <img src="/PrjProject/img/navbarSignin/logout.png" alt="">
                            <p>Đăng xuất</p>
                        </a>
                        <hr>
                        <% if (acc.getRole().getRoleID() != 1) {%>
                        <!-- admin only -->
                        <form action="mainController" method="get">
                            <button class="sub-menu-link mb-[2px] " id="sub-menu-link-admin"  type="submit" name="action" value="<%=     CONSTANTS.GETPRODUCT_ADMIN%>">
                                <img id="imgAdmin" class="w-[45px]" src="/PrjProject/img/navbarSignin/admin.png" alt="">
                                <p class="text-lg">Quản lí của Amin</p> 
                            </button>
                        </form>
                        <%}%>


                    </div>
                </div>
            </div>
        </div>



        <!-- Optional JavaScript -->
        <!--PUT IN index.jsp-->

    </body>

</html>