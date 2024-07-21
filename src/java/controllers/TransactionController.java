/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.ServiceDAO;
import DAO.TransactionDAO;
import DTO.Account;
import DTO.Request;
import DTO.Service;
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
public class TransactionController extends HttpServlet {

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
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("loginUser");
            ServiceDAO ser = new ServiceDAO();
            TransactionDAO rq = new TransactionDAO();
            // in ra dich vu cho nabar moi lan qua trang nay
            ArrayList<Service> serList = ser.getAllService();
            request.setAttribute("serviceList", serList);
            int currentPage = Integer.parseInt(request.getParameter("currentPagetxt"));
            // lay sreach IDcontact
            String searchID = request.getParameter("searchIDtxt");
            // search status contact
            String searchStatus = request.getParameter("searchStatustxt");
            int total = 0; // tim total de chia trang
            String searchMonth = request.getParameter("searchMonthtxt");
            if (searchMonth.equals("all")) {
                request.setAttribute("month", "all");
            } else {
                request.setAttribute("month", "inMonth");
            }
            
            //check thu search co chua chu cai ko
            if (rq.checkNumberInString(searchID) != 0) {
                currentPage = 1;
                // tra ve list
                ArrayList<Request> list = (acc.getRole().getRoleID() != 1) ? rq.getAllAdmin(acc, currentPage) : rq.getAllClient(acc, currentPage);
                total = (acc.getRole().getRoleID() != 1) ? rq.getTotalAllTranAdmin(acc.getAccountID()) : rq.getTotalAllTranClient(acc.getAccountID());
                request.setAttribute("error", "Tìm kiếm không được chứa kí tự");
                request.setAttribute("total", total);
//                sua day ne currentPage thanh currentPagetxt
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("list", list);
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
            }

            if (acc != null) {
                if (acc.getRole().getRoleID() == 1) {
                    if (searchID.equals("") && searchStatus.equals("") & searchMonth.equals("all")) {
                        ArrayList<Request> list = rq.getAllClient(acc, currentPage);
                        total = rq.getTotalAllTranClient(acc.getAccountID());
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    } else if (!searchID.equals("") && searchStatus.equals("") & searchMonth.equals("all")) {
                        ArrayList<Request> list = rq.getAllTranClientByTranID(acc, currentPage, searchID);
                        total = rq.getTotalTranCLientByTranID(acc.getAccountID(), searchID);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    } else if (searchID.equals("") && !searchStatus.equals("") & searchMonth.equals("all")) {
                        ArrayList<Request> list = rq.getAllTranClientByTranStatus(acc, currentPage, searchStatus);
                        total = rq.getTotalAllTranClientByStatusTran(acc.getAccountID(), searchStatus);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    } else if (searchID.equals("") && searchStatus.equals("") && searchMonth.equals("month")) {
                        ArrayList<Request> list = rq.getAllTranClientByInMonth(acc, currentPage);
                        total = rq.getTotalAllTranClientByInMonth(acc.getAccountID());
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    } else if (!searchID.equals("") && !searchStatus.equals("") && searchMonth.equals("all")) {
                        ArrayList<Request> list = rq.getAllTranClientByTranIDANDTranStatus(acc, currentPage, searchStatus, searchID);
                        total = rq.getTotalAllTranClientByTranIDAndStatusTran(acc.getAccountID(), searchID, searchStatus);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    } else if (!searchID.equals("") && searchStatus.equals("") && searchMonth.equals("month")) {
                        ArrayList<Request> list = rq.getAllTranClientByTranIDANDInMonth(acc, currentPage, searchID);
                        total = rq.getTotalAllTranClientByTranIDAndInMonth(total, searchID);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    } else if (searchID.equals("") && !searchStatus.equals("") && searchMonth.equals("month")) {
                        ArrayList<Request> list = rq.getAllTranClientByTranStatusANDInMonth(acc, currentPage, searchStatus);
                        total = rq.getTotalAllTranClientByStatusTranAndInMonth(acc.getAccountID(), searchStatus);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    } else if (!searchID.equals("") && !searchStatus.equals("") && searchMonth.equals("month")) {
                        ArrayList<Request> list = rq.getAllTranClientByTranIDAndTranStatusANDInMonth(acc, currentPage, searchID, searchStatus);
                        total = rq.getTotalAllTranClientByStatusIDAndStatusTranAndInMonth(acc.getAccountID(), searchID, searchStatus);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    }
                } else {
                        //admin
                    if (searchID.equals("") && searchStatus.equals("") & searchMonth.equals("all")) {
                        ArrayList<Request> list = rq.getAllAdmin(acc, currentPage);
                        total = rq.getTotalAllTranAdmin(acc.getAccountID());
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);                        
                    } else if (!searchID.equals("") && searchStatus.equals("") & searchMonth.equals("all")) {
                        ArrayList<Request> list = rq.getAllTranAdminByTranID(acc, currentPage, searchID);
                        total = rq.getTotalTranAdminByTranID(acc.getAccountID(), searchID);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    }  else if (searchID.equals("") && !searchStatus.equals("") & searchMonth.equals("all")) {
                        ArrayList<Request> list = rq.getAllTranAdminByTranStatus(acc, currentPage, searchStatus);
                        total = rq.getTotalAllTranAdminByStatusTran(acc.getAccountID(), searchStatus);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    }   else if (searchID.equals("") && searchStatus.equals("") && searchMonth.equals("month")) {
                        ArrayList<Request> list = rq.getAllTranAdmintByInMonth(acc, currentPage);
                        total = rq.getTotalAllTranAdmintByInMonth(acc.getAccountID());
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    }   else if (!searchID.equals("") && !searchStatus.equals("") && searchMonth.equals("all")) {
                        ArrayList<Request> list = rq.getAllTranAdminByTranIDANDTranStatus(acc, currentPage, searchStatus, searchID);
                        total = rq.getTotalAllTranAdminByTranIDAndStatusTran(acc.getAccountID(), searchID, searchStatus);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    }   else if (!searchID.equals("") && searchStatus.equals("") && searchMonth.equals("month")) {
                        ArrayList<Request> list = rq.getAllTranAdminByTranIDANDInMonth(acc, currentPage, searchID);
                        total = rq.getTotalAllTranAdminByTranIDAndInMonth(acc.getAccountID(), searchID);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    }  else if (searchID.equals("") && !searchStatus.equals("") && searchMonth.equals("month")) {
                        ArrayList<Request> list = rq.getAllTranAdminByTranStatusANDInMonth(acc, currentPage, searchStatus);
                        total = rq.getTotalAllTranAdminByStatusTranAndInMonth(acc.getAccountID(), searchStatus);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    }    else if (!searchID.equals("") && !searchStatus.equals("") && searchMonth.equals("month")) {
                        ArrayList<Request> list = rq.getAllTranAdminByTranIDAndTranStatusANDInMonth(acc, currentPage, searchID, searchStatus);
                        total = rq.getTotalAllTranAdminByStatusIDAndStatusTranAndInMonth(acc.getAccountID(), searchID, searchStatus);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.setAttribute("total", total);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWTRAN).forward(request, response);
                    }

                }

            } else {
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.GETHOMEPAGELOGIN).forward(request, response);
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
