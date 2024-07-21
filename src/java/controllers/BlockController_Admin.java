/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.AccountDAO;
import DAO.ProductDAO;
import DTO.Product;
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
public class BlockController_Admin extends HttpServlet {

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

            out.println("<h1>Servlet BlockController_Admin at " + request.getContextPath() + "</h1>");
            String sec = (String) request.getParameter("sec");
            String id = (String) request.getParameter("itemID");
            int result = 0;
            switch (sec)
            {
                case "1":
                    result = new ProductDAO().blockProduct(id);
                    break;
                case "2":
                    String isPolicy = (String) request.getParameter("type");
//                    String isPolicy="asdad";
                    if (isPolicy != null)
                    {
                        
                        out.print("<h1>isPolicy ne: " + isPolicy + "</h1>");
                        out.print("<h1>ID ne: " + id + "</h1>");
                    } else
                    {
                        out.print("<h1>isPolicy ne: NOOOOOOOOO" + "</h1>");

                    }
                    if (isPolicy != null)
                    {
//                        result = new AccountDAO().blockAccount_Policy(id);
                        Connection cn = null;
                        try
                        {
                            cn = DBUtils.makeConnection();
                            if (cn != null)
                            {
                                String sql
                                        = "UPDATE [dbo].[Account] \n"
                                        + "SET [dbo].[Account].[PolicyStatus]= ~[dbo].[Account].[PolicyStatus]\n"
                                        + "WHERE [dbo].[Account].[AccountID]= ?";

                                PreparedStatement pst = cn.prepareStatement(sql);
                                pst.setString(1, id);

                                //Tra ve 0/1
                                result = pst.executeUpdate();
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        } finally
                        {
                            try
                            {
                                if (cn != null)
                                {
                                    cn.close();
                                }
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    } else
                    {
                        result = new AccountDAO().blockAccount(id);
                    }
                    break;
                case "3":
                    break;
                case "4":
                    break;
            }

            if (result >= 1)
            {
                out.print("YESSSSS");
                request.getRequestDispatcher("mainController?action=" + CONSTANTS.GETPRODUCT_ADMIN).forward(request, response);
            } else
            {
                out.print("Something Wrong");
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
