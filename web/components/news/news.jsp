<%-- 
    Document   : news
    Created on : Feb 1, 2024, 5:10:07 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <title>Title</title>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- FontAwesome  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
              integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <!-- googlefont  -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Rubik&display=swap" rel="stylesheet">

        <!-- Tailwind CSS -->
        <script src="https://cdn.tailwindcss.com"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.2.1/flowbite.min.css" rel="stylesheet" />

        <!-- my css  -->
        <link rel="stylesheet" href="components/news/index.css">
        <link rel="stylesheet" href="utils.css">

    </head>

    <body >

        <!-- Tin tuc &KMai -->
        <div class="my-[10vh]">
            <div class="flex justify-center text-3xl">Tin tức-Khuyến mãi</div>
            <div class="flex justify-center gap-5 my-10">
                <button id="btnTinTuc"
                        class="w-32 px-5 py-3 rounded-3xl text-black bg-gray-100 transition-all ease-in-out newsActive">Tin
                    tức</button>
                <button id="btnKhuyenMai"
                        class="w-32 px-5 py-3 rounded-3xl text-black bg-gray-100 transition-all ease-in-out">Khuyến mãi</button>
            </div>

            <div class="relative">
                <!-- Tin tuc  -->
                <div id="tinTuc"
                     class="transition-all ease-in-out duration-300 w-[100%] m-auto grid grid-cols-6 gap-5 relative top-0">
                    <div class="col-span-6 md:!col-span-3 relative">
                        <div class="newsLayer">
                            <div class="">Cuộc đời sang trang của những em nhỏ Trường Hy Vọng</div>
                        </div>
                        <div class="w-full h-full">
                            <img class="w-full h-full object-cover" src="img/news/item1.jpg" alt="item1">
                        </div>
                    </div>
                    <div class="col-span-6 sm:!col-span-3 md:!col-span-3 relative">
                        <div class="newsLayer w-1/2 sm:!w-full">
                            <div class="hidden sm:!block">Hi FPT cập nhật phiên bản 7.5 với nhiều tính năng mới mẻ</div>
                        </div>
                        <div class="w-full h-full flex gap-2 sm:block">
                            <img class="w-1/2 sm:!w-full h-full object-cover" src="img/news/item2.jpg" alt="item2">
                            <div class="block sm:!hidden font-bold text-xl">Hi FPT cập nhật phiên bản 7.5 với nhiều tính năng mới mẻ
                            </div>
                        </div>
                    </div>

                    <div class="col-span-6 sm:!col-span-3 md:!col-span-2 relative">
                        <div class="newsLayer w-1/2 sm:!w-full">
                            <div class="hidden sm:!block">Jang Nara bùng nổ khi bị bạn thân và chồng phản bội trong Một
                                Chương Hạnh Phúc</div>
                        </div>
                        <div class="w-full h-full flex gap-2 sm:!block">
                            <img class="w-1/2 sm:!w-full h-full object-cover" src="img/news/item3.png" alt="item3">
                            <div class="block sm:!hidden font-bold text-xl">Jang Nara bùng nổ khi bị bạn thân và chồng phản bội trong Một
                                Chương Hạnh Phúc</div>
                        </div>
                    </div>

                    <div class="col-span-6 sm:!col-span-3 md:!col-span-2 relative">
                        <div class="newsLayer w-1/2 sm:!w-full">
                            <div class="hidden sm:block">FPT Camera nhận giải 'Cloud camera ứng dụng AI tốt nhất</div>
                        </div>
                        <div class="w-full h-full flex gap-2 sm:!block">
                            <img class="w-1/2 sm:!w-full h-full object-cover" src="img/news/item4.jpg" alt="item4">
                            <div class="block sm:!hidden font-bold text-xl">FPT Camera nhận giải 'Cloud camera ứng dụng AI tốt nhất</div>
                        </div>
                    </div>


                    <div class="col-span-6 sm:!col-span-3 md:!col-span-2 relative">
                        <div class="newsLayer w-1/2 sm:!w-full">
                            <div class="hidden sm:!block">FPT hái “quả ngọt”nhờ chuyển đổi số</div>
                        </div>
                        <div class="w-full h-full flex gap-2 sm:!block">
                            <img class="w-1/2 sm:!w-full h-full object-cover" src="img/news/item5.jpeg" alt="item5">
                            <div class="block sm:!hidden font-bold text-xl">FPT hái “quả ngọt”nhờ chuyển đổi số</div>
                        </div>
                    </div>


                </div>

                <!-- Khuyen Mai  -->
                <div id="khuyenMai"
                     class="opacity-0 transition-all ease-in-out duration-300 w-[100%] m-auto grid grid-cols-6 gap-5 absolute top-0">
                    <div class="col-span-6 md:!col-span-3 relative">
                        <div class="newsLayer">
                            <div class="">Vui đón năm mới - Rủng rỉnh quà tới tại Chương trình Khách hàng thân thiết FPT Telecom</div>
                        </div>
                        <div class="w-full h-full">
                            <img class="w-full h-full object-cover" src="img/news/km1.jpg" alt="km1">
                        </div>
                    </div>
                    <div class="col-span-6 sm:!col-span-3 md:!col-span-3 relative">
                        <div class="newsLayer w-1/2 sm:!w-full">
                            <div class="hidden sm:!block">Lần Đầu Tiên Trái Thanh Long Có Mặt Tại Sale FPT</div>
                        </div>
                        <div class="w-full h-full flex gap-2 sm:!block">
                            <img class="w-1/2 sm:!w-full h-full object-cover" src="img/news/km2.jpg" alt="km2">
                            <div class="block sm:!hidden font-bold text-xl">Lần Đầu Tiên Trái Thanh Long Có Mặt Tại Sale FPT
                            </div>
                        </div>
                    </div>

                    <div class="col-span-6 sm:!col-span-3 md:!col-span-2 relative">
                        <div class="newsLayer w-1/2 sm:!w-full">
                            <div class="hidden sm:!block">Đại Tiệc Săn Sale - Ưu Đãi Tháng 11 Đỉnh Cao - Duy Nhất Tại shop.fpt.vn
                            </div>
                        </div>
                        <div class="w-full h-full flex gap-2 sm:!block">
                            <img class="w-1/2 sm:!w-full h-full object-cover" src="img/news/km3.jpg" alt="km3">
                            <div class="block sm:!hidden font-bold text-xl">Đại Tiệc Săn Sale - Ưu Đãi Tháng 11 Đỉnh Cao - Duy Nhất Tại
                                shop.fpt.vn</div>
                        </div>
                    </div>

                    <div class="col-span-6 sm:!col-span-3 md:!col-span-2 relative">
                        <div class="newsLayer w-1/2 sm:!w-full">
                            <div class="hidden sm:block">Ngàn lẻ một cách đổi quà tại chương trình Khách hàng thân thiết FPT Telecom
                            </div>
                        </div>
                        <div class="w-full h-full flex gap-2 sm:!block">
                            <img class="w-1/2 sm:!w-full h-full object-cover" src="img/news/km4.jpg" alt="km4">
                            <div class="block sm:!hidden font-bold text-xl">Ngàn lẻ một cách đổi quà tại chương trình Khách hàng thân
                                thiết FPT Telecom</div>
                        </div>
                    </div>


                    <div class="col-span-6 sm:!col-span-3 md:!col-span-2 relative">
                        <div class="newsLayer w-1/2 sm:!w-full">
                            <div class="hidden sm:!block">Ẵm trọn quà tại chương trình Khách hàng thân thiết Tháng 10</div>
                        </div>
                        <div class="w-full h-full flex gap-2 sm:!block">
                            <img class="w-1/2 sm:!w-full h-full object-cover" src="img/news/km5.jpg" alt="km5">
                            <div class="block sm:!hidden font-bold text-xl">Ẵm trọn quà tại chương trình Khách hàng thân thiết Tháng 10
                            </div>
                        </div>
                    </div>


                </div>
            </div>
        </div>


        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <!--PUT IN index.js--> 
    </body>

</html>
