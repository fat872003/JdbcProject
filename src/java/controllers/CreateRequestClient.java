/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.RequestDAO;
import DAO.RequestTypeDAO;
import DTO.Account;
import DTO.RequestType;
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
public class CreateRequestClient extends HttpServlet {

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
            RequestDAO rq = new RequestDAO();
            RequestTypeDAO rtd = new RequestTypeDAO();
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("loginUser");
            ArrayList<RequestType> requestTypeList = rtd.getAllRequestType();
            request.setAttribute("requestTypeList", requestTypeList);
            String serTypetxt = request.getParameter("serTypetxt");
            String reqTypetxt = request.getParameter("reqTypetxt");
            String destxt = request.getParameter("destxt");
            String proTypetxt = request.getParameter("proTypetxt");
            if (acc != null) {
                if (serTypetxt == null && reqTypetxt == null) {
                    request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWCREATEREQUESTNAVBAR).forward(request, response);
                }

                if (serTypetxt.equals("")) {
                    request.setAttribute("error", "Vui lòng không để trống loại dịch vụ");
                    request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWCREATEREQUESTNAVBAR).forward(request, response);

                } else {
                    if (rq.CreateNewTranClientInReq(proTypetxt)!= 0 && rq.CreateNewContactClientInReq(serTypetxt) != 0 && rq.CreateNewReqClientInReq(acc.getAccountID(),reqTypetxt, destxt) != 0) {
                        request.getRequestDispatcher("mainController?action=getreq").forward(request, response);
                    } else {
                        request.setAttribute("error", "Lỗi gửi");
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWCREATEREQUESTNAVBAR).forward(request, response);
                    }

                }

            } else {
                request.setAttribute("MUSTLOGIN", "Vui lòng đăng nhập!");
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.GETLOGINPAGE).forward(request, response);
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
