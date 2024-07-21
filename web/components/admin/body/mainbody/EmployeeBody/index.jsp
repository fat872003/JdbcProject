<%-- 
    Document   : index.jsp
    Created on : Mar 12, 2024, 12:50:01 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Employee</h1>


        <div class="relative overflow-x-auto shadow-md sm:rounded-lg my-5">
            <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th scope="col" class="px-6 py-3 text-center">
                            AccountID
                        </th>
                        <th scope="col" class="px-6 py-3">
                            LastName
                        </th>
                        <th scope="col" class="px-6 py-3">
                            FirstName
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Phone
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Gmail
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Password
                        </th>
                        <th scope="col" class="px-6 py-3 text-center">
                            Policy status
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Script
                        </th>
                        <th scope="col" class="px-6 py-3 text-center">
                            Block/Unblock
                        </th>
                        <th scope="col" class="px-6 py-3 text-center">
                            <span class="">Other</span>
                        </th>

                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

        <!--Add btn--> 
        <button class="px-4 py-2 bg-green-500 rounded text-white" id="toggleForm">Thêm Sản Phẩm</button>
        <!--===========-->

    </body>
</html>
