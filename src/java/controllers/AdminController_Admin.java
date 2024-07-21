package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DAO.AccountDAO;
import DAO.ProductDAO;
import DAO.RequestDAO;
import DAO.StatusTypeDAO;
import DTO.Account;
import controllers.CONSTANTS;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 *
 * @author ACER
 */
public class AdminController_Admin extends HttpServlet {

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
        try (PrintWriter out = response.getWriter())
        {
            ArrayList list = new ArrayList();
            HttpSession session = request.getSession();

            String sec = request.getParameter("sec");
            String search = request.getParameter("search");

            if (sec == null)
            {
                sec = "1";
            }

            switch (sec)
            {
                case "1":
                    if (search != null)
                    {
                        list = new ProductDAO().getProductByName(search);
                    } else
                    {
                        list = new ProductDAO().getAllProduct();
                    }
                    break;
                case "2":
                    if (search != null)
                    {
                        list = new AccountDAO().getAccountByPhone(search);
                    } else
                    {
                        list = new AccountDAO().getAllAccount();
                    }
                    break;
                case "3":

                    session.setAttribute("statusList", new StatusTypeDAO().getAllStatusType());
                    //========================================
                    String date = request.getParameter("date");
                    String status = request.getParameter("status");
                    date = (date == null || date.trim().equals("null")) ? date = "1" : date;
                    status = (status == null || status.trim().equals("null")) ? "" : status;
                    search = (search == null || search.trim().equals("null")) ? "" : search;

                    list = new RequestDAO().getSortRequest(date, search, status);
                    request.setAttribute("date", date);
                    request.setAttribute("status", status);
                    //========================================
                    break;

                case "4":
                    Account acc = (Account) session.getAttribute("loginUser");
                    if (acc != null)
                    {
                        String date4 = request.getParameter("date");
                        String status4 = request.getParameter("status");
                        date4 = (date4 == null || date4.trim().equals("null")) ? date = "1" : date4;
                        status4 = (status4 == null || status4.trim().equals("null")) ? "" : status4;
                        search = (search == null || search.trim().equals("null")) ? "" : search;
                        list = new RequestDAO().getSortRequestByManagerID(date4, search, status4, acc.getAccountID());
                    }
                    ArrayList<Account> technicianList = new AccountDAO().getAllTechnician();
                    session.setAttribute("technicianList", technicianList);
                    break;
                case "5":
                    out.print("Case 5: ");
                    break;
            }

            request.setAttribute("sec", sec);
            session.setAttribute("list", list);
//            request.setAttribute("list", list);

            //            Về view nè 
            request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWPRODUCT_ADMIN).forward(request, response);
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
