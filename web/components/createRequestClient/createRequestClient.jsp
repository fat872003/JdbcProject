<%-- 
    Document   : createRequestClient
    Created on : Mar 19, 2024, 10:42:00 PM
    Author     : Lenovo
--%>

<%@page import="controllers.CONSTANTS"%>
<%@page import="DTO.RequestType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class=" max-w-[var(--maxWidth)] w-[100vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500 ">
        <%
            ArrayList<RequestType> requestTypeList = (ArrayList<RequestType>) request.getAttribute("requestTypeList");
        %>

        <div class=" m-[10px] bg-sky-700 p-[5px] text-white">

            <p class="font-semibold text-2xl">REQUEST INFORMATION</p>


        </div>

        <form  action="mainController" method="get"
               class=" content grid grid-cols-12 gap-2 m-[10px]  p-[10px]  pb-[15px] border-2 rounded-lg     border-gray-400">
            <input type="hidden"  name="action" value="getCreatereq" />
            <div class="col-span-6 mb-[20px] text-3xl font-bold flex justify-center">
                Thông tin yêu cầu
            </div>
            <div class="col-span-6  mb-[20px] text-3xl font-bold flex justify-center">
                Mô tả yêu cầu
            </div>
            <div class="col-span-12 mb-[10px]">
                <hr>
            </div>

            <div class="col-span-6 pr-[50px]">

                 <div class="flex m">
                    <label class="w-[150px] pt-[5px] pl-[10px] " for="">Loại sản phẩm</label>
                    <select class="bg-gray-100 p-[8px] w-full hover:selection:backdrop:bg-none  outline-none cursor-pointer"
                            name="proTypetxt">
                        <option value="1"selected>4G</option>
                        <option value="2">F-lightning</option>
                        <option value="3">3G</option>
                    </select>


                </div>
                
                <div class="flex m">
                    <label class="w-[150px] pt-[5px] pl-[10px] " for="">Loại dịch vụ</label>
                    <select class="bg-gray-100 p-[8px] w-full hover:selection:backdrop:bg-none  outline-none cursor-pointer"
                            name="serTypetxt">
                        <option value="" selected></option>
                        <option value="1">bảo Trì mạng</option>
                        <option value="2">Sửa máy tính</option>
                        <option value="4">Tư vấn gói mạng</option>
                    </select>


                </div>
                
                
                <div class="flex mt-[20px]">
                    <label class="w-[150px] pt-[5px] pl-[10px] " for="">Loại yêu cầu</label>
                    <select class="bg-gray-100 p-[8px] w-full hover:selection:backdrop:bg-none  outline-none cursor-pointer"
                            name="reqTypetxt">
                        <option value="1" selected>Yêu cầu bảo hành</option>
                        <option value="2" selected>Yêu cầu hỗ trợ kỹ thuật</option>
                    </select>

                </div>



            </div>
            <div class="col-span-6 w-full h-auto bg-gray-100 border-2 border-gray-400 rounded-md">
                <textarea type="text" name="destxt" placeholder="nhập mô tả về yêu cầu tối đa 100 kí tự"
                          class="outline-none w-full h-full bg-gray-100" maxlength="100"></textarea>
            </div>
            <%String error = (String) request.getAttribute("error");%>
            <div class="col-span-12 flex justify-center text-red-500 text-lg">
                <p><%= (error != null) ? error : ""%></p>
            </div>
            <input  type="hidden" name="searchIDtxt" value="" />
            <input  type="hidden" name="searchStatustxt" value="" />
            <input  type="hidden" name="currentPagetxt" value="1" />
            <div class="col-span-12 flex justify-end"> <button class="bg-green-400 cursor-pointer hover:bg-green-500 hover:text-white hover:font-bold p-[5px] rounded-md pl-[10px] pr-[10px]"33>submit</button></div>
        </form>
        <div  class="fixed left-0 bottom-12 mb-[75px] cursor-pointer">
            <form action="mainController" method="get">
                <button name="action" value="<%= CONSTANTS.GETHOMEPAGELOGIN%>"> <img class="w-[70px] " src="/PrjProject/img/profile/home.png" alt="">
                </button>
            </form>
        </div>

    </body>
</html>
