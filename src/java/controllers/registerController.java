/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */
public class registerController extends HttpServlet {

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

            String lastName = URLDecoder.decode(request.getParameter("txtlastname"), "UTF-8");

            String firstName = URLDecoder.decode(request.getParameter("txtfirstname"), "UTF-8");
            String phone = request.getParameter("txtphone");
            String gmail = request.getParameter("txtgmail");
            String pass = request.getParameter("txtpass");
            String rePass = request.getParameter("txtpassagain");
            AccountDAO acc = new AccountDAO();
            if (rePass.equals(pass)) {
                if (acc.checkMail(gmail) != 0) {
                    //chek gmail
                    response.sendRedirect("mainController?action=loginpage&sec=2&renotify=2");
                } else if (acc.checkPhone(phone) != 0) {
                    //check phone
                    response.sendRedirect("mainController?action=loginpage&sec=2&renotify=3");
                } else {
                    if (acc.registerAccount(lastName, firstName, phone, gmail, pass) != 0) {
                        request.setAttribute("ERROR", "Đăng kí thành công");
                        request.getRequestDispatcher("mainController?action=loginpage&renotify=0&sec=1").forward(request, response);
                    } else {
                        response.sendRedirect("mainController?action=loginpage&sec=2&renotify=4");
                    }
                }
                // goi hma dang ki dong thoi neu !=0 thi dang ki thanh cong

            } else {
                //check thu pass co khop k
                response.sendRedirect("mainController?action=loginpage&sec=2&renotify=1");
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
