<%-- 
    Document   : products
    Created on : Feb 3, 2024, 11:40:34 AM
    Author     : ACER
--%>

<%@page import="controllers.CONSTANTS"%>
<%@page import="DTO.ProductCategories"%>
<%@page import="DAO.ProductDAO"%>
<%@page import="DTO.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
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
        <link rel="stylesheet" href="utils.css"/>
    </head>
    <body>
        <jsp:include page="/components/navbar/navbar.jsp" />


        <div class="max-w-[var(--maxWidth)] w-[95vw] m-auto overflow-x-hidden transition-all ease-in-out duration-500">

            <div class="pageTitle">Sản phẩm</div>

            <!-- Section -->
            <div class="flex justify-center gap-10 bg-gray-100 py-8">

                <%
                    ArrayList<ProductCategories> cateList = new ProductDAO().getAllCategories();
                    if (cateList != null)
                    {
                        for (ProductCategories item : cateList)
                        {
                %>
                <form action="mainController">
                    <input type="hidden" name="action" value=<%=            CONSTANTS.GETPRODUCTS%>>
                    <input type="hidden" name="cateID" value=<%=item.getCate_ID()%>> <!--  Lấy ID bỏ vào param  -->
                    <button type="submit" class="flex items-center flex-col cursor-pointer"> 
                        <div class="bg-gray-300 rounded-[50%] p-2"> 
                            <img class="w-[50px] text-white" src= <%=        item.getIcon()%>  alt=<%=        item.getName()%>  />
                        </div> 
                        <div class="font-bold text-xl <%=(request.getParameter("cateID").equals(item.getCate_ID())) ? "text-blue-500" : ""%>"> <%=        item.getName()%>   </div> 
                    </button>
                </form>
                <%
                        }
                    }
                %>


            </div>

            <!--ITEM FRAME--> 
            <div class="grid grid-cols-6 p-2 gap-10">

                <%
                    ArrayList<Product> list = (ArrayList<Product>) request.getAttribute("productList");
                    if (list != null)
                    {
                        for (Product item : list)
                        {
                            //Status hd moi render
                            if (item.getStatus().equals("1"))
                            {

                %>
                <div style="box-shadow: rgba(50, 50, 93, 0.25) 0px 6px 12px -2px, rgba(0, 0, 0, 0.3) 0px 3px 7px -3px;"
                     class="rounded-2xl text-sm xl:col-span-2 md:col-span-3 col-span-6 py-4 px-8 text-center flex flex-col justify-center items-center gap-3">
                    <div class="text-green-500 text-2xl font-bold italic"> <%=       item.getName()%> </div>
                    <img src=<%=     item.getThumbnail()%> class="w-1/2" alt=<%=       item.getName()%> />


                    <!-- speed -->
                    <span class="px-4 py-2 rounded-xl bg-blue-500 "> <%=       item.getSpeed()%> Mbps </span>

                    <!-- Speed commerce -->
                    <div class="">
                        <p class="">Tốc độ Download <%=       item.getSpeed()%>  Mbps</p>
                        <p class="">Tốc độ Upload <%=       item.getSpeed()%>  Mbps</p>
                    </div>

                    <!-- linebreak -->
                    <div class="bg-black h-[1px] rounded-2xl my-2 w-[70%]"></div>

                    <!-- description -->
                    <div class=""><%=       item.getDescription()%> </div>

                    <!-- linebreak -->
                    <div class="bg-black h-[1px] rounded-2xl my-2 w-[70%]"></div>

                    <!-- commer -->
                    <div class="">
                        <p class="">Giảm ngay 90.000đ (áp dụng đến 31/12)</p>
                        <p class="">Tặng thêm đến 2 tháng (áp dụng theo khu vực)</p>
                        <p class="">Ưu tiên lắp đặt trong 12h - 36h</p>
                    </div>

                    <button class="text-white px-4 py-2 bg-green-500 rounded-lg">Đăng ký ngay</button>

                    <!-- Policy -->
                    <div class="text-xs text-gray-300 italic">
                        Mức giá trên đã bao gồm VAT. Giá này sẽ thay đổi theo khu vực, theo từng thời điểm, chưa bao gồm
                        tiền thuê thiết bị đầu cuối, phí thu tiền dịch vụ tại nhà và các dịch vụ gia tăng đi kèm khác
                    </div>
                </div>
                <%
                            }

                        }

                    }

                %>
            </div>
            <!--END ITEM FRAME--> 
        </div>

        <script src="./productJS.js"></script>
        <script type="text/javascript" src="Javascript/Navbar/index.js"></script> <!-- For Navbar -->
    </body>
</html>
