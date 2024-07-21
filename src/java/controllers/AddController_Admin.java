/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.AccountDAO;
import DAO.ContactDAO;
import DAO.ProductDAO;
import DAO.RequestDAO;
import DAO.RequestTypeDAO;
import DAO.ServiceDAO;
import DAO.StatusTypeDAO;
import DAO.TransactionDAO;
import DTO.Account;
import DTO.Contact;
import DTO.Product;
import DTO.ProductCategories;
import DTO.Request;
import DTO.RequestType;
import DTO.Role;
import DTO.Service;
import DTO.StatusType;
import DTO.Transaction;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mylibs.DBUtils;

/**
 *
 * @author ACER
 */
public class AddController_Admin extends HttpServlet {

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
            /*Update trong ADMIN*/
            String sec = (String) request.getParameter("sec");
            out.print("<h1>sec: " + sec + "</h1>");

            int result = 0;
            switch (sec)
            {
                case "1":
                    String name = (String) request.getParameter("name");
                    String thumbnail = (String) request.getParameter("thumbnail");
                    String description = (String) request.getParameter("description");
                    int price = Integer.parseInt(request.getParameter("price"));
                    int speed = Integer.parseInt(request.getParameter("speed"));
                    int cate_ID = Integer.parseInt(request.getParameter("cate_ID"));
                    ProductCategories prdC = new ProductDAO().getCateByID(cate_ID);
                    String status = (String) request.getParameter("status");
                    Product prd = new Product(0, name, thumbnail, description, price, speed, prdC, status);
                    result = new ProductDAO().addProduct(prd);
                    break;
                case "2":
                    String lastName = (String) request.getParameter("LastName");
                    String firstName = (String) request.getParameter("FirstName");
                    String phone = (String) request.getParameter("phone");
                    String gmail = (String) request.getParameter("gmail");
                    String password = (String) request.getParameter("password");
                    String status_acc = (String) request.getParameter("status");
                    String policyStatus = (String) request.getParameter("policyStatus");
                    String RoleID = (String) request.getParameter("RoleID");
                    Role role = new AccountDAO().getRoleByID(Integer.parseInt(RoleID));
                    String script = (String) request.getParameter("script");
                    String sex = (String) request.getParameter("sex");
                    Account acc = new Account(0, lastName, firstName, phone, gmail, password, sex, status_acc, policyStatus, role, script);
                    result = new AccountDAO().AddAccount(acc);
                    break;
                case "3":
                    //Tạo Transaction (stt false) => Tạo Contact (status false) => Tạo Request
                    int accID = Integer.parseInt(request.getParameter("AccountID"));
                    int managerID = Integer.parseInt(request.getParameter("ManagerID"));

                    out.print("<h1>accID: " + accID + "</h1>");
                    out.print("<h1>managerID: " + managerID + "</h1>");

                    Account acc_3 = new AccountDAO().getAccountByID(accID + "");
                    Account managerAcc_3 = new AccountDAO().getAccountByID(managerID + "");

                    //Product &ReqType & Des
                    int prdID_3 = Integer.parseInt(request.getParameter("PrdID"));
                    Product prd_3 = new ProductDAO().getProductByID(prdID_3 + "");
                    int reqTypeID = Integer.parseInt(request.getParameter("reqTypeID"));
                    RequestType rqType_3 = new RequestTypeDAO().getRequestTypeByID(reqTypeID);
                    String descriptionData = request.getParameter("Description");

                    //Contact==========
                    //=====Service
                    int serID = Integer.parseInt(request.getParameter("SerID"));
                    Service service_3 = new ServiceDAO().getServiceByID(serID);
                    //=====End Service

                    //=====Transaction
                    Date transDate_3 = new Date();
//                    out.print("<h3>newDate:     " + transDate_3 + "</h3>");
//                    out.print("<h3>GETTIME:    " + transDate_3.toString() + "</h3>");
                    double transMoney = prd_3.getPrice() + service_3.getServicePrice();
                    String transStatus_3 = "0"; //false
                    Product transProduct_3 = prd_3;
//                    Bug 
                    Transaction transaction_3 = new Transaction(0, transDate_3, 1 , transMoney, transStatus_3, transProduct_3);
                    int addTrans = new TransactionDAO().addNewTransaction(transaction_3);
//                    out.print("<h3>TransDate:     " + transaction_3.getDate() + "</h3>");
                    //=====End Transaction
                    //=====Status
                    String status_3 = "0"; //false
                    //=====End Status
                    Contact contact = new Contact(0, service_3, transaction_3, status_3);
                    int addContact = new ContactDAO().addContact(contact);

                    //End Contact========
                    int stt = 2; //// Da Xac Nhan,   1 neu la Client Tao
                    StatusType sttType = new StatusTypeDAO().getStatusTypeByID(stt);

                    Request request_3 = new Request(0, acc_3, managerAcc_3, contact, sttType, rqType_3, descriptionData);

                    //Adding...
                    if (addTrans >= 1 && addContact >= 1)
                    {
                        result = new RequestDAO().addRequest(request_3);
                    } else
                    {
                        out.print("Thêm Thất bại");
                    }

                    break;
            }

            if (result >= 1)
            {
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.GETPRODUCT_ADMIN).forward(request, response);
            } else
            {
                out.print("Some thing Wronggggggggggg");
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
