<%-- 
    Document   : footer
    Created on : Feb 6, 2024, 6:02:04 PM
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
        <div class=" grid grid-cols-2 md:hidden">
    <div class=" col-span-2 grid grid-cols-2 bg-gray-100/50 pt-[10px] pb-[15px]  md:hidden">

      <div id="hidden_top_1" class=" col-span-2 pt-[15px] pb-[15px]">
        <div class=" flex justify-between ">
          <p class=" text-[17px] font-bold">Về F-LIGHTNING </p>
          <button
            class="accordion-button h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50 text-[20px] font-bold  pr-[10px]">
            >
          </button>
        </div>

        <div class="col-span-2 accordion-content hidden">
          <ul class="cursor-pointer hover:cursor-pointer">
            <li class="mb-[4px] ">
              <p class="hover:underline">Giới thiệu chung</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Liên kết - Thành viên</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Khách hàng - Đối tác</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Quan hệ cổ đông</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Tập đoàn F-LIGHTNING</p>
            </li>
          </ul>
        </div>
      </div>


      <div id="hidden_top_2" class=" col-span-2 pt-[15px] pb-[15px]">
        <div class=" flex justify-between ">
          <p class="  text-[17px] font-bold">Khách hàng F-LIGHTNING</p>
          <button
            class="accordion-button h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50 text-[20px] font-bold pr-[10px]">
            >
          </button>
        </div>

        <div class="col-span-2 accordion-content hidden">
          <ul class="cursor-pointer hover:cursor-pointer">
            <li class="mb-[4px] ">
              <p class="hover:underline">Hướng dẫn sử dụng dịch vụ</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Giới thiệu bạn bè</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Thanh toán Online</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Góp ý khách hàng</p>
            </li>
          </ul>
        </div>
      </div>

      <div id="hidden_top_3" class=" col-span-2 pt-[15px] pb-[15px]">
        <div class=" flex justify-between ">
          <p class="  text-[17px] font-bold">Chưa là khách hàng của F-LIGHTNING</p>
          <button
            class="accordion-button h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50 text-[20px] font-bold  pr-[10px]">
            >
          </button>
        </div>

        <div class="col-span-2 accordion-content hidden">
          <ul class="cursor-pointer hover:cursor-pointer">
            <li class="mb-[4px] ">
              <p class="hover:underline">Đăng ký Online</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Sản phẩm dịch vụ</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Khuyến mại</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Tìm điểm giao dịch</p>
            </li>

            <li class="mb-[4px]">
              <p class="hover:underline">Tin tức</p>
            </li>

          </ul>
        </div>
      </div>




    </div>
  </div>




  <div
    class="footer grid grid-cols-12  md:mt-[0px] bg-gray-100/50  md:pl-[65px] md:pr-[73px] mt-[30px] md:pt-[25px] pl-[15px] pr-[15px] pb-[30px] ">
    <div class="md:col-span-3 flex">
      <img class="h-[50px] md:-[60px] md:block hidden" src="/PrjProject/img/footer/services.png" alt="">

    </div>
    <!-- cách giữa image và footer content ở cái pb này -->
    <div class="  hidden col-span-9 gap-[30px] md:flex justify-end pb-[45px]  ">
      <!-- cai image dau tien link voi web contact -->
      <img class="h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50" src="/PrjProject/img/footer/10.png"
        alt="">
      <img class="h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50" src="/PrjProject/img/footer/7.png"
        alt="">
      <img class="h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50" src="/PrjProject/img/footer/8.png"
        alt="">
      <img class="h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50" src="/PrjProject/img/footer/9.png"
        alt="">

    </div>
    <div class=" col-span-12 mb-[27px]">
      <hr>
    </div>
    <div class=" sm:col-span-12 md:col-span-4 text-[15px]">
      <p>Giấy chứng nhận ĐKKD số 0101778163 do Sở Kế hoạch Đầu tư Thành phố Hà Nội cấp ngày 28/07/2005</p>
      <p>Giấp phép cung cấp dịch vụ Viễn thông số 147/GP-CVT ngày 02/05/2013</p>
      <p>Công ty Cổ phần Viễn thông FPT
        Địa chỉ liên lạc: Tầng 9, Block A, tòa nhà FPT Cầu Giấy, số 10 Phạm Văn Bạch, quận Cầu Giấy, TP. Hà Nội
      </p>
      <p>
        Thư điện tử: <span class="cursor-pointer hover:cursor-pointer ">hotrokhachhang@F-LIGHTNING.com</span>
      </p>
      <p>
        Số điện thoại liên hệ: 024 7300 2222
      </p>
      <p>
        Tên người đại diện: Ông Hoàng Việt A
      </p>
      <img class="w-[130px] mt-[17px]" src="/PrjProject/img/footer/20150827110756-dathongbao.png" alt="">
    </div>

    <div class="hidden sm:col-span-12 md:col-span-8 md:grid grid-cols-12">

      <div class=" col-span-3 pl-[32px] pr-[10px]">
        <p class="text-lg text-orange-600 pb-[10px]">Về F-LIGHTNING</p>
        <ul class="cursor-pointer hover:cursor-pointer">
          <li>
            <p class="hover:underline">Giới thiệu chung</p>
          </li>

          <li>
            <p class="hover:underline">Liên kết - Thành viên</p>
          </li>

          <li>
            <p class="hover:underline">Khách hàng - Đối tác</p>
          </li>

          <li>
            <p class="hover:underline">Quan hệ cổ đông</p>
          </li>

          <li>
            <p class="hover:underline">Tập đoàn F-LIGHTNING</p>
          </li>
        </ul>
      </div>

      <div class=" col-span-4">
        <p class="text-lg text-orange-600 pb-[10px]">Khách hàng F-LIGHTNING</p>
        <ul class="cursor-pointer hover:cursor-pointer">
          <li>
            <p class="hover:underline">Hướng dẫn sử dụng dịch vụ</p>
          </li>

          <li>
            <p class="hover:underline">Giới thiệu bạn bè</p>
          </li>

          <li>
            <p class="hover:underline">Thanh toán Online</p>
          </li>

          <li>
            <p class="hover:underline">Góp ý khách hàng</p>
          </li>
        </ul>
      </div>

      <div class=" col-span-4">
        <p class="text-lg font-normal text-orange-600 pb-[10px]">Chưa là khách hàng của F-LIGHTNING</p>
        <ul class="cursor-pointer hover:cursor-pointer">
          <li>
            <p class="hover:underline">Đăng ký Online</p>
          </li>

          <li>
            <p class="hover:underline">Sản phẩm dịch vụ</p>
          </li>

          <li>
            <p class="hover:underline">Khuyến mại</p>
          </li>

          <li>
            <p class="hover:underline">Tìm điểm giao dịch</p>
          </li>

          <li>
            <p class="hover:underline">Tin tức</p>
          </li>
        </ul>
      </div>

    </div>
  </div>

  <div class=" gap-[40px] pt-[15px] md:hidden flex justify-center">
    <img class="h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50" src="/PrjProject/img/footer/10.png" alt="">
    <img class="h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50" src="/PrjProject/img/footer/7.png" alt="">
    <img class="h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50" src="/PrjProject/img/footer/8.png" alt="">
    <img class="h-[23px] cursor-pointer hover:cursor-pointer hover:opacity-50" src="/PrjProject/img/footer/9.png" alt="">


  </div>
    </body>
</html>
