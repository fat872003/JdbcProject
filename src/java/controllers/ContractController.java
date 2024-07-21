/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.ContactDAO;
import DAO.RequestDAO;
import DAO.ServiceDAO;
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
public class ContractController extends HttpServlet {

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
            ContactDAO rq = new ContactDAO ();
            ServiceDAO ser = new ServiceDAO();
            // in ra dich vu cho nabar moi lan qua trang nay
            ArrayList<Service> serList = ser.getAllService();
            request.setAttribute("serviceList", serList);
            int currentPage = Integer.parseInt(request.getParameter("currentPagetxt"));
            // lay sreach IDcontact
            String searchID = request.getParameter("searchIDtxt");
            // search status contact
            String searchStatus = request.getParameter("searchStatustxt");

            //check thu search co chua chu cai ko
            if (rq.checkNumberInString(searchID) != 0) {
                currentPage = 1;
                // tra ve list
                ArrayList<Request> list = (acc.getRole().getRoleID() != 1) ? rq.getAllAdminContract(acc, currentPage) : rq.getAllContractClient(acc, currentPage);
                request.setAttribute("error", "Tìm kiếm không được chứa kí tự");
                
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("list", list);
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWCONTRACT).forward(request, response);
            }

            if (acc != null) {

                if (acc.getRole().getRoleID() == 1) {
                    if (searchID.equals("")) {
                        ArrayList<Request> list = rq.getAllContractClient(acc, currentPage);
                        // tra ve list
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWCONTRACT).forward(request, response);

                    } else {
                        ArrayList<Request> list = rq.getAllContractClientByContactID(acc, currentPage, searchID);
                        // gui 1 request thong bao dang tim kiem
                        request.setAttribute("searchIDtxt", searchID);
                        request.setAttribute("list", list);
                        request.setAttribute("currentPage", currentPage);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWCONTRACT).forward(request, response);
                    }
                } else {
                    if (searchID.equals("") && searchStatus.equals("")) {
                        ArrayList<Request> list = rq.getAllAdminContract(acc, currentPage);
                        // tra ve list
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWCONTRACT).forward(request, response);
                    } else if (!searchID.equals("")&& searchStatus.equals("")) {
                        ArrayList<Request> list = rq.getAllAdminContractByContactID(acc, currentPage, searchID);
                        // gui 1 request thong bao dang tim kiem
                        request.setAttribute("searchIDtxt", searchID);
                        // gui 1 thng bao status
                       
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWCONTRACT).forward(request, response);
                    } else if (!searchStatus.equals("")&&searchID.equals("")) {
                        ArrayList<Request> list = rq.getAllAdminContractByContractStatus(acc, currentPage, Integer.parseInt(searchStatus));
                       
                        request.setAttribute("searchStatustxt", searchStatus);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                     request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWCONTRACT).forward(request, response);
                    }else if(!searchID.equals("")&&!searchStatus.equals("")){
                      ArrayList<Request> list = rq.getAllAdminContractByContractStatusAndContactID(acc, currentPage,Integer.parseInt(searchStatus), searchID);
                       request.setAttribute("searchIDtxt", searchID);
                       request.setAttribute("searchStatustxt", searchStatus);
                        request.setAttribute("currentPage", currentPage);
                        request.setAttribute("list", list);
                       request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWCONTRACT).forward(request, response);
                    }
                }

            } else {
                //tra ve thong bao loi
        
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
