/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author ACER
 */
public interface CONSTANTS {

    public final int MAXPAGE_ADMIN = 5;

    //Get => Qua controller lay database
    //view => Qua JSP
    // ?action=...
    public final String GETHOME = "gethome"; //back to  homeController: (getdata before go home)
    public final String VIEWHOME = "home"; //back to home: index.jsp

    public final String GETLOGINPAGE = "getloginpage"; //back to  loginController: )
    public final String VIEWLOGINPAGE = "loginpage"; //back to loginpage

    public final String GETPRODUCTS = "getProducts"; // get products xong mới render qua products
    public final String VIEWPRODUCTS = "products"; // qua trang products

    public final String GETSIGNUP = "getsignup";// go to registerContrller to check account exit    
    public final String GETSIGNIN = "getsignin"; //back to signinController (xu ly data khi mà login dữ liệu vào)

    public final String GETHOMEPAGELOGIN = "gethomepagelogin"; // Qua homePageLogin.java kiem tra xong gui qua viewhomepagelogin
    public final String VIEWHOMEPAGELOGIN = "homepagelogin"; // vào trang chủ sau khi đăng nhập thành công

    public final String GETPROFILE = "getpro"; // qua profileController kiem tra qua viewprofile
    public final String VIEWPROFILE = "viewpro"; // qua trang profilePage

    public final String GETUPDATEPRO = "getuppro";
    public final String VIEWUPDATEPRO = "viewuppro";

    public final String GETMYDEVICE = "getdevi"; // show ra phần my device bên profile bằng cách qua my device controller
    public final String GETCONTRACT = "getContract";
    public final String VIEWCONTRACT = "viewContract";

    public final String GETTRAN = "getTran";
    public final String VIEWTRAN = "viewTran";

    public final String GETREQUESTNAVBAR = "getreq";
    public final String VIEWREQUESTNAVBAR = "viewreq";

    public final String GETCREATEREQUESTNAVBAR = "getCreatereq";
    public final String VIEWCREATEREQUESTNAVBAR = "viewCreatereq";

    public final String APPLICATION = "application"; // qua trang viết đơn

    //admin
    public final String GETPRODUCT_ADMIN = "getProductAdmin"; // Lấy thông tin admin
    public final String VIEWPRODUCT_ADMIN = "productAdmin"; // qua trang Admin

    public final String GETFORMINFOPRODUCT_ADMIN = "formHandle"; // qua trang Admin
    public final String UPDATEINFO_ADMIN = "updateAdmin"; // Qua update Servlet
    public final String ADDINFO_ADMIN = "addAdmin"; // qua Add Servlet
    public final String BLOCK_ADMIN = "blockAdmin"; // qua Add Servlet
    public final String LOGOUT = "getLogout";
}
