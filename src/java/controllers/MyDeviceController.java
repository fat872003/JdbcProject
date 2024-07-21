/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.RequestDAO;
import DTO.Account;
import DTO.Request;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
public class MyDeviceController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            // thong bao co phai ng dung dang nhan vao nut sreach ko
            String msgSearch = (String) request.getParameter("searchtxt");
            RequestDAO rq = new RequestDAO();
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("loginUser");
            //lay gai tri index moi
            int indexListNew = Integer.parseInt(request.getParameter("index"));

            // gui request show form myDevice
            request.setAttribute("myDevice", "show");

            if (!msgSearch.equalsIgnoreCase("")) {

//                vi luc nay chua ton tai 1 gia tri index default la page 1 khi ma goi ra tran sreach nay
             
                // tao request yeu cau set msg gui di o day la proID qua tung phan tu i chay mang in ra

                request.setAttribute("searchStatus", msgSearch);
                // chuyen ve tu chuoi cua masg qua int de tim Id product vi ban dau no chuoi ma phai truonwg hop ng dung ko go gi thi gia tri default no la "" nen ko the chuyen ve int dc luon
                request.setAttribute("proID", Integer.parseInt(msgSearch));
                int proID = Integer.parseInt(msgSearch);
                ArrayList<Request> list = rq.getMyDeviceInforBySearching(acc, 1, proID);

                request.setAttribute("indexList", indexListNew);
                request.setAttribute("listDevice", list);
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWPROFILE).forward(request, response);

            }  else {
              
                // lay gia tri index ng dung chon
                ArrayList<Request> list = rq.getMyDeviceInfor(acc, indexListNew);
                request.setAttribute("indexList", indexListNew);
                request.setAttribute("listDevice", list);
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWPROFILE).forward(request, response);
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
