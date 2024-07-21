/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.AccountDAO;
import DTO.Account;
import DTO.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
public class updateProfileController extends HttpServlet {

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

//            check pass voi i ID co dai hon 12 ko
            String gmail = request.getParameter("gmailtxt").trim();
            String lastName = request.getParameter("lastnametxt");
            String firstName = request.getParameter("firstnametxt");
            String phone = request.getParameter("phonetxt").trim();
            String sex = request.getParameter("sextxt");
            int phoneLenght = phone.length();
            AccountDAO d = new AccountDAO();

            int oldIntPhone = Integer.parseInt(acc.getPhone());
            int newIntPhone = Integer.parseInt(phone);

            //check thu gmail co khac gmail ban dau ko sau check thu no co ton tai trong db ko
            if (!gmail.equals(acc.getGmail()) && d.checkMail(gmail) != 0) {
                request.setAttribute("WARN", "Gmail đã tồn tại vui lòng nhập lại");
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWUPDATEPRO).forward(request, response);
            }

            if (oldIntPhone != newIntPhone) {

                if (d.checkPhone(phone) != 0) {
                    request.setAttribute("WARN", "Số điện thoại đã tồn tại vui lòng nhập lại");
                    request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWUPDATEPRO).forward(request, response);
                }
                if (phone.length() != 9) {
                    request.setAttribute("WARN", "Số điện thoại phải có 9 kí tự ");
                    request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWUPDATEPRO).forward(request, response);
                }

            }

            if (d.updateAccountInfor(acc.getAccountID(), firstName, lastName, phone, gmail, sex) != 0) {
                Account accUpdate = d.getClientAccount(acc.getPhone(), acc.getGmail(), acc.getPassword());
                if (acc.getRole().getRoleID() == 1) {
                    session.setAttribute("loginUser", accUpdate);
                    request.setAttribute("NOTIFYPRO", "Cập nhật thành công");
                    // vi no phai qua tao 1 cai list default cho sreach
                    request.getRequestDispatcher("mainController?action=" + CONSTANTS.GETPROFILE).forward(request, response);
                } else {

                    //--------------------------------------------------------------------------------------------------- 
                    // lay infor cua empl dua vao accID
                    Employee em = d.getEmployeInfor(acc.getAccountID());

                    String dayOfBirth = request.getParameter("birthtxt");
                    String workingDay = request.getParameter("workingtxt");
                    String identyfiID = request.getParameter("identifytxt").trim();

                    if (d.updateEmployeeInfor(acc.getAccountID(), dayOfBirth, workingDay, identyfiID) != 0) {
                        request.setAttribute("NOTIFYPRO", "Cập nhật thành công");
                        session.setAttribute("loginUser", accUpdate);
                        Employee emUpdate = d.getEmployeInfor(accUpdate.getAccountID());
                        session.setAttribute("emInfor", emUpdate);
                        request.setAttribute("NOTIFYPRO", "Cập nhật thành công");
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.GETPROFILE).forward(request, response);
                    } else {
                        request.setAttribute("WARN", "Cập nhật hồ sơ nhân viên thất bại");
                        request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWUPDATEPRO).forward(request, response);
                    }
                }
            } else {
                request.setAttribute("WARN", "Cập nhật thất bại");
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.VIEWUPDATEPRO).forward(request, response);

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
