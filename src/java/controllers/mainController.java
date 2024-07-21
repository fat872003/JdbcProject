/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTO.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ACER
 */
public class mainController extends HttpServlet {

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
            String url = "";

            String action = request.getParameter("action");

            if (action == null) {
                action = CONSTANTS.GETHOME;
            }

            switch (action) {
                case CONSTANTS.VIEWHOME:
                    url = "page/index.jsp";
                    break;
                case CONSTANTS.GETHOME:
                    url = "homeController";
                    break;
                case CONSTANTS.GETPRODUCTS:
                    url = "productController";
                    break;
                case CONSTANTS.VIEWPRODUCTS:
                    url = "page/productPage/products.jsp";
                    break;

                case CONSTANTS.GETLOGINPAGE:
                    url = "loginController";
                    break;
                // sua lai
                case CONSTANTS.VIEWLOGINPAGE:
                    url = "page/loginPage/login.jsp";
                    break;

                case CONSTANTS.GETSIGNUP:
                    url = "registerController";
                    break;
                case CONSTANTS.GETSIGNIN:
                    url = "loginController";
                    break;

                case CONSTANTS.GETHOMEPAGELOGIN:
                    url = "homePageLoginController";
                    break;

                case CONSTANTS.VIEWHOMEPAGELOGIN:
                    url = "page/mainPageSighIn/mainPageSighIn.jsp";
                    break;

                case CONSTANTS.GETPROFILE:
                    url = "profileController";
                    break;
                case CONSTANTS.VIEWPROFILE:
                    url = "page/profilePage/profilePage.jsp";
                    break;

                case CONSTANTS.GETUPDATEPRO:
                    url = "updateProfileController";
                    break;
                case CONSTANTS.VIEWUPDATEPRO:
                    url = "page/profilePage/updateProfilePage.jsp";
                    break;

                case CONSTANTS.GETMYDEVICE:
                    url = "MyDeviceController";
                    break;

                case CONSTANTS.GETCONTRACT:
                    url = "ContractController";
                    break;
                case CONSTANTS.VIEWCONTRACT:
                    url = "page/contactPage/contactPage.jsp";
                    break;

                case CONSTANTS.GETTRAN:
                    url = "TransactionController";
                    break;
                case CONSTANTS.VIEWTRAN:
                    url = "page/transactionPage/TransactionPage.jsp";
                    break;
                case CONSTANTS.GETREQUESTNAVBAR:
                    url = "RequestPageController";
                    break;
                case CONSTANTS.VIEWREQUESTNAVBAR:
                    url="page/viewRequestPageNavbar/viewRequestPageNavbar.jsp";
                    break;
                case CONSTANTS.GETCREATEREQUESTNAVBAR:
                    url = "CreateRequestClient";
                    break;
                case CONSTANTS.VIEWCREATEREQUESTNAVBAR:
                    url="page/createRequestClientPage/createRequestClientPage.jsp";
                    break;
                
                    
                
//                    CALL GET => VIEW 
//                    CALL FORM => GET => View 
                case CONSTANTS.GETPRODUCT_ADMIN:
                    url = "AdminController_Admin";
                    break;
                case CONSTANTS.VIEWPRODUCT_ADMIN:
                    url = "page/adminPage/admin.jsp";
                    break;
                //Lấy thông tin  ra formInput
                case CONSTANTS.GETFORMINFOPRODUCT_ADMIN:
                    url = "ProductsFormController_Admin";
                    break;
                case CONSTANTS.UPDATEINFO_ADMIN:
                    url = "UpdateController_Admin";
                    break;
                case CONSTANTS.ADDINFO_ADMIN:
                    url = "AddController_Admin";
                    break;
                case CONSTANTS.BLOCK_ADMIN:
                    url = "BlockController_Admin";
                    break;
                case CONSTANTS.LOGOUT:
                    url = "logoutController";
                    break;
                default:
                    url = "testController";
                    break;
            }

//            url="productController";
//            out.print("action: "+ action);
            request.getRequestDispatcher(url).forward(request, response);

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
