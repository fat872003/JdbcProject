<%-- 
    Document   : index
    Created on : Feb 19, 2024, 4:26:40 PM
    Author     : ACER
--%>

<%@page import="DTO.Request"%>
<%@page import="controllers.CONSTANTS"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            ArrayList list = (ArrayList) session.getAttribute("list");
            int maxPage = 0;
            
            String search = (request.getParameter("search") == null)? "": request.getParameter("search");
            
            if (list != null)
            {
                maxPage = (int) Math.ceil((list.size() * 1.0) / CONSTANTS.MAXPAGE_ADMIN);
            }
            String currPage = (String) request.getParameter("page");
            if (currPage == null || (Integer.parseInt(currPage) <= 0))
            {
                currPage = "1";
            } else if (Integer.parseInt(currPage) > maxPage)
            {
                currPage = maxPage + "";
            }

            //Pagination

        %>
        
        <div class="flex justify-center">
            <nav>
                <ul class="list-style-none flex">
                    <li class= "<%=          (Integer.parseInt(currPage) == 1) ? "hidden" : ""%>">
                        <form action="mainController">
                            <input type="hidden" name="action" value="<%=       CONSTANTS.VIEWPRODUCT_ADMIN%>" />
                            <input type="hidden" name="sec" value="<%=          request.getParameter("sec")%>" />
                            <input type="hidden" name="search" value="<%=     search     %>" />
                            <input type="hidden" name="date" value="<%=          request.getParameter("date")%>" />
                            <input type="hidden" name="status" value="<%=          request.getParameter("status")%>" />
                            <button name="page" value="<%=   (Integer.parseInt(currPage) > 1) ? (Integer.parseInt(currPage) - 1) : (Integer.parseInt(currPage))%>"
                                    class="relative block rounded bg-transparent px-3 py-1.5 text-sm text-neutral-500 transition-all duration-300 dark:text-neutral-400">Previous</button>
                        </form>
                    </li>
                    <!--3 button--> 
                    <li>
                        <form action="mainController">
                            <input type="hidden" name="action" value="<%=        CONSTANTS.VIEWPRODUCT_ADMIN%>" />
                            <input type="hidden" name="sec" value="<%=          request.getParameter("sec")%>" />
                            <input type="hidden" name="search" value="<%=          search  %>" />
                            <input type="hidden" name="date" value="<%=          request.getParameter("date")%>" />
                            <input type="hidden" name="status" value="<%=          request.getParameter("status")%>" />
                            <button
                                class="relative block rounded bg-transparent px-3 py-1.5 text-sm text-neutral-600 transition-all duration-300 hover:bg-neutral-100  dark:text-white dark:hover:bg-neutral-700 dark:hover:text-white"
                                name="page"
                                name="page" value="<%=        (Integer.parseInt(currPage) - 1)%>"
                                ><%=          (Integer.parseInt(currPage) > 1) ? (Integer.parseInt(currPage) - 1) : ""%>
                            </button>
                        </form>
                    </li>
                    <li aria-current="page">
                        <form action="mainController">
                            <input type="hidden" name="action" value="<%=        CONSTANTS.VIEWPRODUCT_ADMIN%>" />
                            <input type="hidden" name="sec" value="<%=          request.getParameter("sec")%>" />
                            <input type="hidden" name="search" value="<%=      search   %>" />
                            <input type="hidden" name="date" value="<%=          request.getParameter("date")%>" />
                            <input type="hidden" name="status" value="<%=          request.getParameter("status")%>" />
                            <button
                                class="relative block rounded bg-blue-100 px-3 py-1.5 text-sm font-medium text-primary-700 transition-all duration-300"
                                name="page" value="<%=        (Integer.parseInt(currPage))%>"
                                ><%=          currPage%>
                                <span
                                    class="absolute -m-px h-px w-px overflow-hidden whitespace-nowrap border-0 p-0 [clip:rect(0,0,0,0)]"
                                    >(current)</span
                                >
                            </button>
                        </form>
                    </li>
                    <li>
                        <form action="mainController">
                            <input type="hidden" name="action" value="<%=        CONSTANTS.VIEWPRODUCT_ADMIN%>" />
                            <input type="hidden" name="sec" value="<%=          request.getParameter("sec")%>" />
                            <input type="hidden" name="search" value="<%=     search    %>" />
                            <input type="hidden" name="date" value="<%=          request.getParameter("date")%>" />
                            <input type="hidden" name="status" value="<%=          request.getParameter("status")%>" />
                            <button
                                class="relative block rounded bg-transparent px-3 py-1.5 text-sm text-neutral-600 transition-all duration-300 hover:bg-neutral-100 dark:text-white dark:hover:bg-neutral-700 dark:hover:text-white"
                                name="page" value="<%=        (Integer.parseInt(currPage) + 1)%>"
                                ><%=          (Integer.parseInt(currPage) < maxPage) ? (Integer.parseInt(currPage) + 1) : ""%></button
                            >
                        </form>
                    </li>
                    <!--End 3 Button--> 
                    <li class= "<%=          (Integer.parseInt(currPage) == maxPage) ? "hidden" : ""%>"   >
                        <form action="mainController">
                            <!--    CONSTANTS.VIEWPRODUCT_ADMIN -->    
                            <input type="hidden" name="action" value="<%= CONSTANTS.VIEWPRODUCT_ADMIN%>" />
                            <input type="hidden" name="sec" value="<%=          request.getParameter("sec")%>" />
                            <input type="hidden" name="search" value="<%=        search   %>" />
                            <input type="hidden" name="date" value="<%=          request.getParameter("date")%>" />
                            <input type="hidden" name="status" value="<%=          request.getParameter("status")%>" />

                            <button name="page" value="<%=   (Integer.parseInt(currPage) < maxPage) ? (Integer.parseInt(currPage) + 1) : (Integer.parseInt(currPage))%>" 
                                    class="relative block rounded bg-transparent px-3 py-1.5 text-sm text-neutral-500 transition-all duration-300 dark:text-neutral-400">Next</button>
                        </form>

                    </li>
                </ul>
            </nav>
        </div>

    </body>
</html>
