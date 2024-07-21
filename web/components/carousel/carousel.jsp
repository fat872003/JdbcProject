<%-- 
    Document   : carousel
    Created on : Feb 1, 2024, 3:58:55 AM
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

    <!-- css  -->
    <link rel="../../utils.css">
</head>

<body>
    <div id="body" class="relative">
        <!-- Carousel  -->
        <div id="default-carousel" class="relative w-full h-full" data-carousel="slide">
            <!-- Carousel wrapper -->
            <div class="relative overflow-hidden min-h-[550px]">
                <!-- Item 1 -->
                <div class="hidden duration-700 ease-in-out" data-carousel-item>
                    <img src="/PrjProject/img/bg1.jpg"
                        class="absolute object-cover h-full  w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2"
                        alt="..." />
                </div>
                <!-- Item 2 -->
                <div class="hidden duration-700 ease-in-out" data-carousel-item>
                    <img src="/PrjProject/img/bg2.jpg"
                        class="absolute object-cover h-full w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2"
                        alt="..." />
                </div>
                <!-- Item 3 -->
                <div class="hidden duration-700 ease-in-out" data-carousel-item>
                    <img src="/PrjProject/img/bg3.jpg"
                        class="absolute object-cover h-full w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2"
                        alt="..." />
                </div>

                <!-- Slider indicators -->
                <div class="absolute z-[40] flex -translate-x-1/2 bottom-5 left-1/2 space-x-3 rtl:space-x-reverse">
                    <button type="button" class="w-3 h-3 rounded-full" aria-current="true" aria-label="Slide 1"
                        data-carousel-slide-to="0"></button>
                    <button type="button" class="w-3 h-3 rounded-full" aria-current="false" aria-label="Slide 2"
                        data-carousel-slide-to="1"></button>
                    <button type="button" class="w-3 h-3 rounded-full" aria-current="false" aria-label="Slide 3"
                        data-carousel-slide-to="2"></button>
                </div>
                <!-- Slider controls -->
                <button type="button"
                    class="absolute top-0 start-0 z-[40] flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none"
                    data-carousel-prev>
                    <span
                        class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
                        <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M5 1 1 5l4 4" />
                        </svg>
                        <span class="sr-only">Previous</span>
                    </span>
                </button>
                <button type="button"
                    class="absolute top-0 end-0 z-[40] flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none"
                    data-carousel-next>
                    <span
                        class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
                        <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="m1 9 4-4-4-4" />
                        </svg>
                        <span class="sr-only">Next</span>
                    </span>
                </button>
            </div>
        </div>
    </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <!--PUT IN index.jsp--> 
</body>

</html>
