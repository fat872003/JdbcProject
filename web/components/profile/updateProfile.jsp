<%-- 
    Document   : updateProfile
    Created on : Feb 24, 2024, 9:53:12 PM
    Author     : Lenovo
--%>

<%@page import="controllers.CONSTANTS"%>
<%@page import="DAO.AccountDAO"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Calendar"%>
<%@page import="DTO.Employee"%>
<%@page import="DTO.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập nhật nhật hồ sơ</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="/PrjProject/components/profile/style.css">
    </head>
    <body class="max-w-[var(--maxWidth)] w-[100vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500">
        <%
            AccountDAO d = new AccountDAO();
            Account acc = (Account) session.getAttribute("loginUser");
            Employee em = null;
            if (acc.getRole().getRoleID() != 1) {

                em = (Employee) session.getAttribute("emInfor");
            }

        %>

        <div class="title flex w-[full] bg-sky-700 text-white h-[110px] items-center text-[20px] shadow-sm pl-[103px]">
            HỒ SƠ CỦA TÔI
        </div>

        <form action="mainController" method="get"  class="content grid grid-cols-12 gap-5 p-[40px]  pb-[0px]">
            <div
                class="avatar_content   bg-gray-100 radius rounded-lg border-2 border-sky-700 col-span-12 xl:col-span-4 flex flex-col items-center  h-full">
                <div class="img_avatar mt-[64px]">
                    <img src="/PrjProject/img/profile/img_profile.png" alt="">
                </div>
                <div class="tilte_name">
                    <!-- name -->
                    <%= acc.getLastName().trim() + " " + acc.getFirstName().trim()%>
                </div>
                <div class="title_role  mb-0">
                    <span class="text-cyan-500">Role:</span> <%= acc.getRole().getRoleName()%>
                </div>
                <% if (acc.getRole().getRoleID() != 1) {%>

                <div class="title_role mb-[10px] lg:mb-0">
                    <span class="text-cyan-500">Major:</span> <%= em.getMajor().getMajorName()%> 
                </div>
                <%}%>

            </div>
            <!-- phan ben display ne  -->
            <div
                class="infor_content grid md:grid-cols-2 p-[16px] pb-[60px]  bg-sky-700 rounded-lg border-2 border-sky-700  col-span-12 xl:col-span-8 ">
                <div
                    class=" col-span-2 grid grid-cols-2  rounded-lg border-2 mt-[10px] mb-[20px] ml-[50px] mr-[50px]  xl:ml-[150px] xl:mr-[150px] bg-white">
                    <div class="h-[20px] col-span-2 flex justify-center  lg:gap-6 mt-[14px] mb-[14px] ml-[20px] mr-[20px] pl-[15px] pr-[15px]">
                        <img src="/PrjProject/img/profile/icon_proflie.png" alt="">
                        <p>Chỉnh sửa hồ sơ</p>
                    </div>


                </div>
                <!-- account detail -->

                <div class="col-span-2  grid grid-cols-2 bg-white  rounded-lg border-2 text-slate-500  ">
                    <div class=" hidden  col-span-1 lg:flex items-center pl-[24px]">
                        <p>Account</p>

                    </div>
                    <div
                        class=" flex flex-col gap-3 justify-center lg:block lg:justify-normal lg:gap-0 lg:ml-0 lg:mr-0 col-span-2 lg:col-span-1 email_infor p-[16px] ml-[25px] mr-[25px] md:ml-[150px] md:mr-[150px]   ">
                        <p class="lg:hidden text-lg">Account</p>
                        <p>Email</p>
                        <div class="bg-gray-100 flex  mr-[65px] items-center  border-2  border-gray-200">
                            <img class="h-[20px] pl-[4px]" src="/PrjProject/img/profile/email_icon.png" alt="">
                            <input type="gmail"  class="bg-gray-100 p-[8px] w-full outline-none " name="gmailtxt" value="<%= acc.getGmail()%>">
                        </div>



                    </div>
                    <div class="col-span-2 mt-[24px] mb-[24px]">
                        <hr>
                    </div>

                    <div class=" hidden col-span-1 lg:flex items-center pl-[24px]">
                        <p>Personal informations</p>
                    </div>

                    <div
                        class=" col-span-2 lg:col-span-1   email_infor ml-[25px] mr-[25px] md:ml-[150px] md:mr-[150px]   lg:ml-0 lg:mr-0  p-[16px] mb-[16px]">
                        <p class="lg:hidden text-lg mb-[10px]">Personal informations</p>
                        <p>Last name</p>
                        <div class="bg-gray-100 flex mr-[65px] items-center  border-2  border-gray-200">
                            <img class="h-[22px] pl-[4px]" src="/PrjProject/img/profile/personIcon.png" alt="">
                            <input type="text"  class="bg-gray-100 p-[8px] w-full outline-none " name="lastnametxt" value="<%= acc.getLastName().trim()%>">
                        </div>

                        <p>First name</p>
                        <div class="bg-gray-100 flex mr-[65px] items-center  border-2  border-gray-200">
                            <img class="h-[22px] pl-[4px]" src="/PrjProject/img/profile/personIcon.png" alt="">
                            <input type="text"  class="bg-gray-100 p-[8px] w-full outline-none " name="firstnametxt" value="<%= acc.getFirstName().trim()%>">
                        </div> 

                        <p class="mt-[20px] ">Phone number</p>
                        <div class="bg-gray-100 flex mr-[65px] items-center  border-2  border-gray-200">
                            <img class="h-[22px] pl-[4px]" src="/PrjProject/img/profile/phone.png" alt="">
                            <input type="text"  class="bg-gray-100 p-[8px] w-full outline-none " name="phonetxt" value="<%= acc.getPhone()%>" placeholder="vui lòng nhập từ 10 đến 11" >
                        </div>




                        <% if (acc.getRole().getRoleID() != 1) {%>
                        <p class="mt-[20px] "> Identify </p>
                        <div class="bg-gray-100 flex mr-[65px] items-center  border-2  border-gray-200">
                            <img class="h-[22px] pl-[4px]" src="/PrjProject/img/profile/identify.png" alt="">
                            <input type="text"  class="bg-gray-100 p-[8px] w-full outline-none " name="identifytxt" value="<%=em.getIdentify_ID()%>" placeholder="vui lòng nhập 12 kí tự" >
                        </div>
                        <%}%>


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

                        <%if (acc.getRole().getRoleID() != 1) {%>
                        <p>Day of birth</p>
                        <div class="bg-gray-100 flex mr-[65px] items-center  border-2  border-gray-200">
                            <img class="h-[22px] pl-[4px]" src="/PrjProject/img/profile/dayOfbirth.png" alt="">
                            <%
                                //lay name hien tai de tim dc nam phu hop di lam
                                Calendar calendar = Calendar.getInstance();
                                int year = calendar.get(Calendar.YEAR);
                                int verifyYear = year - 18;
                            %>


                            <input type="date"  class="bg-gray-100 p-[8px] w-full outline-none " name="birthtxt" max="<%= verifyYear%>-12-31" value="<%= d.convertDateToString(em.getDayOfBirth())%>" >
                        </div>
                        <%}%>

                        <% if (acc.getRole().getRoleID() == 2) {%>
                        <p>Working Day</p>
                        <div class="bg-gray-100 flex mr-[65px] items-center  border-2  border-gray-200">
                            <img class="h-[22px] pl-[4px]" src="/PrjProject/img/profile/dayOfbirth.png" alt="">
                            <%
                                LocalDate currentDate = LocalDate.now();
                                String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            %>


                            <input type="date"  class="bg-gray-100 p-[8px] w-full outline-none " name="workingtxt" max="<%= formattedDate%>" value="<%= d.convertDateToString(em.getWorkingDay())%>">
                        </div>
                        <%} else if (acc.getRole().getRoleID() == 3) {%>
                        <input type="hidden"  class="bg-gray-100 p-[8px] w-full outline-none " name="workingtxt" value="<%= d.convertDateToString(em.getWorkingDay())%>">
                        <%}%>

                        <p class="mt-[20px] ">Sex</p>
                        <div class="bg-gray-100 flex mr-[65px] items-center  border-2  border-gray-200">
                            <img class="h-[22px] pl-[4px]" src="/PrjProject/img/profile/sex.png" alt="">
                            <select class="bg-gray-100 p-[8px] w-full outline-none"  name="sextxt" >
                                <option  value="Male" >Male</option>                                
                                <option value="Female">Female</option>
                            </select>

                        </div>
                    </div>

                    <div class="col-span-2 mt-[24px] mb-[24px] ">
                        <hr>
                        <div class ="flex justify-center w-full">
                            <!-- msg o day ne !!!!!!!!!!!  -->
                            <%
                                String msg = (String) request.getAttribute("WARN");
                            %>
                            <p class="font-bold text-lg text-red-500" > <%= (msg != null) ? msg : ""%></p>
                        </div>




                    </div>

                    <div class="col-span-2  mb-[24px] mr-[32px] flex justify-end">

                        <button data-modal-target="#modal"
                                class="statusButton flex items-center bg-green-400 hover:bg-green-600 text-white font-bold py-2 px-4 rounded" type="button" onclick="openpopup()">
                            <div class="pl-[4px] pr-[4px]">
                                Lưu 
                            </div>

                        </button>


                    </div>

                </div>



            </div>

            <div class="popup" id="popup">
                <img class="" src="/PrjProject/img/profile/warning.png" alt="">
                <h2>Bạn có thực sự muốn lưu thông tin cập nhật không</h2>
                <div class="button-row flex justify-between ">
                    <button class="bg-red-600" onclick="closepopup()" name="action" value="viewuppro" >cancel</button>
                    <button class="bg-green-500" onclick="closepopup()" name="action" value="getuppro"  >ok</button>
                </div>
            </div> 

        </form>

        <div  class="fixed left-0 bottom-12 mb-[75px] cursor-pointer">
            <form action="mainController" method="get">
                <button name="action" value="<%= CONSTANTS.GETHOMEPAGELOGIN%>"> <img class="w-[70px] " src="/PrjProject/img/profile/home.png" alt="">
                </button>
            </form>
        </div>


        <div class="fixed left-0 bottom-11 cursor-pointer">
            <form action="mainController" method="get">
                <button name="action" value="<%= CONSTANTS.GETPROFILE %>"> <img class="w-[70px] cursor-pointer" src="/PrjProject/img/profile/back.png" alt=""></button>
                </button>
            </form>
        </div>
    </body>
    <script>
        let popup = document.getElementById("popup");

        function openpopup() {
            popup.classList.add("open-popup");

        }

        function removepopup() {
            popup.classList.remove("open-popup");

        }


    </script>
</html>
