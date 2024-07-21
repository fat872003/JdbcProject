/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.AccountDAO;
import DAO.ProductDAO;
import DAO.RequestDAO;
import DTO.Account;
import DTO.Product;
import DTO.ProductCategories;
import DTO.Role;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mylibs.DBUtils;

/**
 *
 * @author ACER
 */
public class UpdateController_Admin extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            String sec = (String) request.getParameter("sec");
            int result = 0;
            switch (sec)
            {
                case "1":
                    int prd_ID = Integer.parseInt(request.getParameter("prd_ID"));
                    String name = (String) request.getParameter("name");
                    String thumbnail = (String) request.getParameter("thumbnail");
                    String description = (String) request.getParameter("description");
                    int price = Integer.parseInt(request.getParameter("price"));
                    int speed = Integer.parseInt(request.getParameter("speed"));
                    int cate_ID = Integer.parseInt(request.getParameter("cate_ID"));
                    ProductCategories prdC = new ProductDAO().getCateByID(cate_ID);
                    String status = (String) request.getParameter("status");

                    result = new ProductDAO().updateProductInfo(new Product(prd_ID, name, thumbnail, description, price, speed, prdC, status));
                    break;
                case "2":
                    int acc_ID = Integer.parseInt(request.getParameter("AccountID"));
                    String FirstName = (String) request.getParameter("FirstName");
                    String LastName = (String) request.getParameter("LastName");
                    out.print(LastName);
                    String phone = (String) request.getParameter("phone");
                    String gmail = (String) request.getParameter("gmail");
                    String password = (String) request.getParameter("password");
                    String status_Acc = (String) request.getParameter("status");
                    String policyStatus = (String) request.getParameter("policyStatus");
                    //convert thanh RoleName
                    String RoleID = (String) request.getParameter("RoleID");
//                    String RoleName = new AccountDAO().getRoleByID(RoleID);
                    Role role = new AccountDAO().getRoleByID(Integer.parseInt(RoleID));
//                    //==============
                    String script = (String) request.getParameter("script");

                    //BUG
                    String sex = "Male";
                    result = new AccountDAO().updateAccountInfo(new Account(acc_ID, LastName, FirstName, phone, gmail, password, sex, status_Acc, policyStatus, role, script));
                    //=================
//                    DEBUG 
//                    int acc_ID = 1;
//                    String FirstName = "An";
//                    String LastName = "Nguyễn Hoàng";
//                    String phone = "093213214";
//                    String gmail = "ChubeBao@gmail.com";
//                    String password = "Saotaokobiet12345";
//                    String status_Acc = "1";
//                    String policyStatus = "1";
//                    String RoleName = "Client";
//                    String script = "1";
//
//                    Account acc = new Account(acc_ID, LastName, FirstName, phone, gmail, password, status_Acc, policyStatus, RoleName, script);

                    //END=========================
                    break;
                case "3":
                    String sttType = request.getParameter("sttType");
                    String reqID = request.getParameter("reqID");
                    String managerID = request.getParameter("managerID");
                    boolean isAttach = Boolean.getBoolean(request.getParameter("isAttach"));
                    result = new RequestDAO().updateRequestStatus(sttType, reqID);
                    if(sttType.equals("1")){
                        new RequestDAO().detachManagerID(reqID);
                    }else if(sttType.equals("1")==false && isAttach==false) {
                        new RequestDAO().attachManagerID( managerID , reqID);
                    }
                    
                    out.print("Hello Update 3");
                    out.print("---------------");
                    out.print(request.getParameter("search"));
                    break;
                case "4":
          
                    String employeeID = request.getParameter("employeeID");
                    String taskReq = request.getParameter("taskReq");
  
                    result= new RequestDAO().attachManagerID(employeeID, taskReq);
                    
                    
                    break;
            }

            if (result >= 1)
            {
//                out.print("Update Thanh cong");
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.GETPRODUCT_ADMIN).forward(request, response);

            } else
            {
                out.print("Something Wrong.");
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
