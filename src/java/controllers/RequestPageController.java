/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.ContactDAO;
import DAO.RequestDAO;
import DAO.ServiceDAO;
import DAO.StatusTypeDAO;
import DTO.Account;
import DTO.Request;
import DTO.Service;
import DTO.StatusType;
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
public class RequestPageController extends HttpServlet {

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
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("loginUser");
            // gui ve kieu du lieu request
            ArrayList<StatusType> listStatusRequest = new StatusTypeDAO().getAllStatusType();
            request.setAttribute("listStatusRequest", listStatusRequest);
            RequestDAO rq = new RequestDAO();
            int currentPage = Integer.parseInt(request.getParameter("currentPagetxt"));
            // lay sreach IDcontact
            String searchID = request.getParameter("searchIDtxt");
            // search status contact
            String searchStatus = request.getParameter("searchStatustxt");
            // cac bien trng pop up khi update
            String statusPopUPtxt = request.getParameter("statusPopUPtxt");
            String updateReIDtxt = request.getParameter("updateReIDtxt");
            int total = 0;
            ArrayList<Request> list = new ArrayList<>();
            
            if (!searchID.equals("") &&rq.checkNumberInString(searchID) != 0) {
    
                currentPage = 1;
                // tra ve list
                total = rq.viewTotalAllUser(acc.getAccountID());
                list = rq.viewAllUser(acc, currentPage);
                request.setAttribute("error", "Tìm kiếm không được chứa kí tự");
                request.setAttribute("currentPagetxt", currentPage);
                request.setAttribute("total", total);
                request.setAttribute("list", list);
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWREQUESTNAVBAR).forward(request, response);
            }

            if (acc != null) {
                if (searchID.equals("") && searchStatus.equals("")) {
                    total = rq.viewTotalAllUser(acc.getAccountID());
                    list = rq.viewAllUser(acc, currentPage);
                }
                if(!searchID.equals("")&&searchStatus.equals("")){
                    total = rq.viewTotalAllUserByID(acc.getAccountID(), searchID);
                    list=rq.viewAllUserByID(acc, currentPage, searchID);
                }
                if(searchID.equals("")&&!searchStatus.equals("")){
                    total = rq.viewTotalAllUserByStatus(acc.getAccountID(),searchStatus);
                    list=rq.viewAllUserByStatus(acc, currentPage, searchStatus);
                }
                
                if(!searchID.equals("")&&!searchStatus.equals("")) {
                    total=rq.viewTotalAllUserByIDAndStatus(acc.getAccountID(), searchID, searchStatus);
                    list=rq.viewAllUserByIDAndStatus(acc, currentPage, searchID, searchStatus);
                }
                if(statusPopUPtxt !=null&&statusPopUPtxt !=null){
                    int result = rq.updateStatusInViewRequestPage(updateReIDtxt, statusPopUPtxt);
                    if(result==0&&statusPopUPtxt !=null){
                         request.setAttribute("error", "Cập nhật trạng thái lỗi");
                    }
                    total = rq.viewTotalAllUser(acc.getAccountID());
                    list = rq.viewAllUser(acc, currentPage);
                }
                request.setAttribute("currentPagetxt", currentPage);
                request.setAttribute("total", total);
                request.setAttribute("list", list);
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWREQUESTNAVBAR).forward(request, response);

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
