/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTO.Request;
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
public class testController extends HttpServlet {

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
            String sec = request.getParameter("sec");
            String sttType = request.getParameter("sttType");
            String reqID = request.getParameter("reqID");
            out.print("<h3>StatusType: " + sttType + "</h3>");
            out.print("<h3>reqID: " + reqID + "</h3>");
            out.print("<h3> TEST" + "</h3>");
            out.print("<h3> sec: " + sec + "</h3>");

            ArrayList<Request> list = (ArrayList<Request>) request.getSession().getAttribute("list");
            if (list != null)
            {
                out.print("Ngon: ");
                for (Request request1 : list)
                {
                    out.print(request1.getReqID()+", ");
                }
            } else
            {
                out.print("CC");
            }

            out.print("<form action='mainController'>");
            out.print("<input type='hidden'  name='action' value='getProductAdmin' >");
            out.print("<input type='hidden'  name='sec' value=' " + sec + ">");
            out.print("<div><button type='submit' class='bg-yellow-500 px-4 py-2 rounded'>Letsgooooo</button></div>");
            out.print("</form>");

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
