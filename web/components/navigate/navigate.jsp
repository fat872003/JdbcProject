<%-- 
    Document   : navigate
    Created on : Feb 6, 2024, 5:19:42 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body>
        <div class="  ml-[31px] mr-[31px] mt-[15px]  grid lg:grid-cols-3 gap-3 ">
        <div
          class="  px-[35px] cursor-pointer hover:cursor-pointer  flex col-span-1 items-center  h-full bg-gray-100/50 border-2 rounded-md border-solid border-white transition duration-300      hover:border-zinc-700/15   hover:text-green-400   ">
    
          <img src="/PrjProject/img/navigate/register.png" class="h-[67px] " alt="">
          <div class=" pl-[25px] text-3xl flex items-center font-semibold  h-[154px] ">
            Đăng ký Online
          </div>
    
        </div>
    
        <div
          class="   px-[35px]  cursor-pointer hover:cursor-pointer flex col-span-1 font-bold  h-full  bg-gray-100/50 border-2 rounded-md border-solid border-white transition duration-300      hover:border-zinc-700/15 hover:text-cyan-700  ">
          <div class="flex items-center">
            <img src="/PrjProject/img/navigate/new.png" class="h-[67px] " alt="">
          </div>
          <div class="  pl-[25px] text-3xl flex items-center font-semibold   h-[154px]">
            Tin tức
          </div>
        </div>
    
        <div
          class="   px-[35px] cursor-pointer hover:cursor-pointer  flex col-span-1 font-bold   h-full  bg-gray-100/50 border-2 rounded-md border-solid border-white  transition duration-300    hover:border-zinc-700/15 hover:text-cyan-400 ">
          <div class="flex items-center">
            <img src="/PrjProject/img/navigate/support.png" class="h-[67px]" alt="">
          </div>
          <div class=" pl-[25px] text-3xl flex items-center font-semibold h-[154px] ">
            Hỗ trợ
          </div>
        </div>
      </div>
    </body>
</html>
