/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Account;
import DTO.Contact;
import DTO.Product;
import DTO.ProductCategories;
import DTO.Request;
import DTO.RequestType;
import DTO.Service;
import DTO.StatusType;
import DTO.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import mylibs.DBUtils;

/**
 *
 * @author ACER
 */
public class ContactDAO {

    public Contact getContactByID(int id) {
        Contact rs = null;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [ContactID], [SerID] , [TranID],[status]  FROM [dbo].[Contact]\n"
                        + "WHERE [dbo].[Contact].[ContactID] = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null)
                {
                    while (table.next())
                    {
                        int contactID = table.getInt("ContactID");
                        //get SerObj
                        int serID = table.getInt("SerID");
                        Service ser = new ServiceDAO().getServiceByID(serID);
                        //end SerObj
                        int tranID = table.getInt("TranID");
                        Transaction trans = new TransactionDAO().getTransByID(tranID);
                        String status = (table.getBoolean("status")) ? "1" : "0";
                        rs = new Contact(contactID, ser, trans, status);
                    }
                }

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
        return rs;
    }

    //POST UPDATE=======================================================================:
    public int addContact(Contact contact) {
        int result = 0;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {

                String sql
                        = "INSERT INTO [dbo].[Contact]([SerID],[TranID],[status])\n"
                        + "VALUES (?,(SELECT MAX(TranID) FROM [dbo].[Transaction_infor]),?)";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, contact.getService().getId());
                pst.setString(2, contact.getStatus());

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

        return result;
    }
    
    //============================================================= view Contract
           public int getTotalAllClientContract(int accountID) {
        Connection cn = null;
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT count(*)as[count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where Request.[AccountID]=?  and Contact.status='true'";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);

                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    total = table.getInt("count");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return total;
    }

    public ArrayList<Request> getAllContractClient(Account acc, int index) {
        ArrayList<Request> list = new ArrayList<>();
        AccountDAO d = new AccountDAO();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT Request.[ReqID],Request.[AccountID],Request.[ManagerAccountID],\n"
                        + "\n"
                        + "Request.[Description] as [requestDes],\n"
                        + "\n"
                        + "RequestType.reqTypeID ,RequestType.ReqTypeName,\n"
                        + "\n"
                        + "StatusType.StatusID as [statusTypeID],StatusType.StatusName,\n"
                        + "\n"
                        + "Contact.ContactID,Contact.status as [contactStatus],\n"
                        + "\n"
                        + "Service.SerID,Service.SerName,Service.Status as [ServiceStatus],Service.price as[servicePrice],\n"
                        + "\n"
                        + "Transaction_infor.TranID,Transaction_infor.Date as [tranDate], Transaction_infor.quantity as[tranQuantity],\n"
                        + "\n"
                        + "Transaction_infor.money as[tranMoney],Transaction_infor.Status as[tranStatus],\n"
                        + "\n"
                        + "Product.prd_ID,Product.name as[productName],Product.thumnail as[productThumnail],Product.description as[productDes],\n"
                        + "\n"
                        + "Product.price as[productPrice],Product.speed as[productSpeed], Product.status as[productStatus] ,\n"
                        + "\n"
                        + "Category.cate_ID , Category.Name as[cateName] ,Category.icon as[cateIcon],Category.status as [cateStatus]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID "
                        + "where Request.[AccountID]= ?  and Contact.status='true'\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, (index - 1) * 5);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");

                        ProductCategories cate = new ProductCategories(table.getInt("cate_ID"), table.getString("cateName"), table.getString("cateIcon"), table.getString("cateStatus"));

                        Product product = new Product(table.getInt("prd_ID"), table.getString("productName"), table.getString("productThumnail"), table.getString("productDes"), table.getInt("productPrice"), table.getInt("productSpeed"), cate, table.getString("productStatus"));

                        java.util.Date tranDate = d.convertStringToDate(table.getString("tranDate"));
                        Transaction tran = new Transaction(table.getInt("TranID"), tranDate, table.getInt("tranQuantity"), table.getDouble("tranMoney"), table.getString("tranStatus"), product);

                        Service service = new Service(table.getInt("SerID"), table.getString("SerName"), table.getString("ServiceStatus"), table.getInt("servicePrice"));

                        Account Acc = d.SearchAccountByID(table.getInt("AccountID"));  //FK
                        Account adminAcc = d.SearchAccountByID(table.getInt("ManagerAccountID")); //FK           
                        Contact Contact = new Contact(table.getInt("ContactID"), service, tran, table.getString("contactStatus")); //FK
                        StatusType statusType = new StatusType(table.getInt("statusTypeID"), table.getString("StatusName"));
                        RequestType requestType = new RequestType(table.getInt("reqTypeID"), table.getString("ReqTypeName")); //FK
                        String ReDescription = table.getString("requestDes");
                        list.add(new Request(ReqID, Acc, adminAcc, Contact, statusType, requestType, ReDescription));
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;

    }

    public int getTotalContractCLientByContactID(int accID, String proID) {
        // vi dung like kiem tra nen proID tu int ve String
        String proIDString = "%" + proID + "%";
        Connection cn = null;
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT count(*) as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where Request.[AccountID]= ?  and Contact.status='true'\n"
                        + " AND Contact.ContactID LIKE ? ;";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accID);
                pst.setString(2, proIDString);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    total = table.getInt("count");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return total;

    }

    public int checkNumberInString(String input) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            // Kiểm tra xem ký tự có phải là một số không
            if (!Character.isDigit(input.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Request> getAllContractClientByContactID(Account acc, int index, String proID) {
        // vi dung like kiem tra nen proID tu int ve String
        String proIDString = "%" + proID + "%";
        ArrayList<Request> list = new ArrayList<>();
        AccountDAO d = new AccountDAO();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT Request.[ReqID],Request.[AccountID],Request.[ManagerAccountID],\n"
                        + "\n"
                        + "Request.[Description] as [requestDes],\n"
                        + "\n"
                        + "RequestType.reqTypeID ,RequestType.ReqTypeName,\n"
                        + "\n"
                        + "StatusType.StatusID as [statusTypeID],StatusType.StatusName,\n"
                        + "\n"
                        + "Contact.ContactID,Contact.status as [contactStatus],\n"
                        + "\n"
                        + "Service.SerID,Service.SerName,Service.Status as [ServiceStatus],Service.price as[servicePrice],\n"
                        + "\n"
                        + "Transaction_infor.TranID,Transaction_infor.Date as [tranDate], Transaction_infor.quantity as[tranQuantity],\n"
                        + "\n"
                        + "Transaction_infor.money as[tranMoney],Transaction_infor.Status as[tranStatus],\n"
                        + "\n"
                        + "Product.prd_ID,Product.name as[productName],Product.thumnail as[productThumnail],Product.description as[productDes],\n"
                        + "\n"
                        + "Product.price as[productPrice],Product.speed as[productSpeed], Product.status as[productStatus] ,\n"
                        + "\n"
                        + "Category.cate_ID , Category.Name as[cateName] ,Category.icon as[cateIcon],Category.status as [cateStatus]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where Request.[AccountID]= ?  and Contact.status='true'\n"
                        + " AND Contact.ContactID LIKE ? \n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setString(2, proIDString);
                pst.setInt(3, (index - 1) * 5);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");

                        ProductCategories cate = new ProductCategories(table.getInt("cate_ID"), table.getString("cateName"), table.getString("cateIcon"), table.getString("cateStatus"));

                        Product product = new Product(table.getInt("prd_ID"), table.getString("productName"), table.getString("productThumnail"), table.getString("productDes"), table.getInt("productPrice"), table.getInt("productSpeed"), cate, table.getString("productStatus"));

                        java.util.Date tranDate = d.convertStringToDate(table.getString("tranDate"));
                        Transaction tran = new Transaction(table.getInt("TranID"), tranDate, table.getInt("tranQuantity"), table.getDouble("tranMoney"), table.getString("tranStatus"), product);

                        Service service = new Service(table.getInt("SerID"), table.getString("SerName"), table.getString("ServiceStatus"), table.getInt("servicePrice"));

                        Account Acc = d.SearchAccountByID(table.getInt("AccountID"));  //FK
                        Account adminAcc = d.SearchAccountByID(table.getInt("ManagerAccountID")); //FK           
                        Contact Contact = new Contact(table.getInt("ContactID"), service, tran, table.getString("contactStatus")); //FK
                        StatusType statusType = new StatusType(table.getInt("statusTypeID"), table.getString("StatusName"));
                        RequestType requestType = new RequestType(table.getInt("reqTypeID"), table.getString("ReqTypeName")); //FK
                        String ReDescription = table.getString("requestDes");
                        list.add(new Request(ReqID, Acc, adminAcc, Contact, statusType, requestType, ReDescription));
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;

    }

    public int getTotalAllAdminContract(int accountID) {
        Connection cn = null;
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT \n"
                        + " count(*)as[count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? );";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    total = table.getInt("count");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return total;
    }

    public ArrayList<Request> getAllAdminContract(Account acc, int index) {
        ArrayList<Request> list = new ArrayList<>();
        AccountDAO d = new AccountDAO();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT Request.[ReqID],Request.[AccountID],Request.[ManagerAccountID],\n"
                        + "\n"
                        + "Request.[Description] as [requestDes],\n"
                        + "\n"
                        + "RequestType.reqTypeID ,RequestType.ReqTypeName,\n"
                        + "\n"
                        + "StatusType.StatusID as [statusTypeID],StatusType.StatusName,\n"
                        + "\n"
                        + "Contact.ContactID,Contact.status as [contactStatus],\n"
                        + "\n"
                        + "Service.SerID,Service.SerName,Service.Status as [ServiceStatus],Service.price as[servicePrice],\n"
                        + "\n"
                        + "Transaction_infor.TranID,Transaction_infor.Date as [tranDate], Transaction_infor.quantity as[tranQuantity],\n"
                        + "\n"
                        + "Transaction_infor.money as[tranMoney],Transaction_infor.Status as[tranStatus],\n"
                        + "\n"
                        + "Product.prd_ID,Product.name as[productName],Product.thumnail as[productThumnail],Product.description as[productDes],\n"
                        + "\n"
                        + "Product.price as[productPrice],Product.speed as[productSpeed], Product.status as[productStatus] ,\n"
                        + "\n"
                        + "Category.cate_ID , Category.Name as[cateName] ,Category.icon as[cateIcon],Category.status as [cateStatus]\n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setInt(3, (index - 1) * 5);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");

                        ProductCategories cate = new ProductCategories(table.getInt("cate_ID"), table.getString("cateName"), table.getString("cateIcon"), table.getString("cateStatus"));

                        Product product = new Product(table.getInt("prd_ID"), table.getString("productName"), table.getString("productThumnail"), table.getString("productDes"), table.getInt("productPrice"), table.getInt("productSpeed"), cate, table.getString("productStatus"));

                        java.util.Date tranDate = d.convertStringToDate(table.getString("tranDate"));
                        Transaction tran = new Transaction(table.getInt("TranID"), tranDate, table.getInt("tranQuantity"), table.getDouble("tranMoney"), table.getString("tranStatus"), product);

                        Service service = new Service(table.getInt("SerID"), table.getString("SerName"), table.getString("ServiceStatus"), table.getInt("servicePrice"));

                        Account Acc = d.SearchAccountByID(table.getInt("AccountID"));  //FK
                        Account adminAcc = d.SearchAccountByID(table.getInt("ManagerAccountID")); //FK           
                        Contact Contact = new Contact(table.getInt("ContactID"), service, tran, table.getString("contactStatus")); //FK
                        StatusType statusType = new StatusType(table.getInt("statusTypeID"), table.getString("StatusName"));
                        RequestType requestType = new RequestType(table.getInt("reqTypeID"), table.getString("ReqTypeName")); //FK
                        String ReDescription = table.getString("requestDes");
                        list.add(new Request(ReqID, Acc, adminAcc, Contact, statusType, requestType, ReDescription));
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;

    }

    public int getTotalAllAdminContractByContactID(int accountID, int proID) {
        Connection cn = null;
        String stringProID = "%" + proID + "%";
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT count(*)as [count]\n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "AND Contact.ContactID LIKE ? ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, stringProID);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    total = table.getInt("count");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return total;
    }

    public ArrayList<Request> getAllAdminContractByContactID(Account acc, int index, String proID) {
        ArrayList<Request> list = new ArrayList<>();
        String stringProID = "%" + proID + "%";
        AccountDAO d = new AccountDAO();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT Request.[ReqID],Request.[AccountID],Request.[ManagerAccountID],\n"
                        + "\n"
                        + "Request.[Description] as [requestDes],\n"
                        + "\n"
                        + "RequestType.reqTypeID ,RequestType.ReqTypeName,\n"
                        + "\n"
                        + "StatusType.StatusID as [statusTypeID],StatusType.StatusName,\n"
                        + "\n"
                        + "Contact.ContactID,Contact.status as [contactStatus],\n"
                        + "\n"
                        + "Service.SerID,Service.SerName,Service.Status as [ServiceStatus],Service.price as[servicePrice],\n"
                        + "\n"
                        + "Transaction_infor.TranID,Transaction_infor.Date as [tranDate], Transaction_infor.quantity as[tranQuantity],\n"
                        + "\n"
                        + "Transaction_infor.money as[tranMoney],Transaction_infor.Status as[tranStatus],\n"
                        + "\n"
                        + "Product.prd_ID,Product.name as[productName],Product.thumnail as[productThumnail],Product.description as[productDes],\n"
                        + "\n"
                        + "Product.price as[productPrice],Product.speed as[productSpeed], Product.status as[productStatus] ,\n"
                        + "\n"
                        + "Category.cate_ID , Category.Name as[cateName] ,Category.icon as[cateIcon],Category.status as [cateStatus]\n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "AND Contact.ContactID LIKE ?\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, stringProID);
                pst.setInt(4, (index - 1) * 5);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");

                        ProductCategories cate = new ProductCategories(table.getInt("cate_ID"), table.getString("cateName"), table.getString("cateIcon"), table.getString("cateStatus"));

                        Product product = new Product(table.getInt("prd_ID"), table.getString("productName"), table.getString("productThumnail"), table.getString("productDes"), table.getInt("productPrice"), table.getInt("productSpeed"), cate, table.getString("productStatus"));

                        java.util.Date tranDate = d.convertStringToDate(table.getString("tranDate"));
                        Transaction tran = new Transaction(table.getInt("TranID"), tranDate, table.getInt("tranQuantity"), table.getDouble("tranMoney"), table.getString("tranStatus"), product);

                        Service service = new Service(table.getInt("SerID"), table.getString("SerName"), table.getString("ServiceStatus"), table.getInt("servicePrice"));

                        Account Acc = d.SearchAccountByID(table.getInt("AccountID"));  //FK
                        Account adminAcc = d.SearchAccountByID(table.getInt("ManagerAccountID")); //FK           
                        Contact Contact = new Contact(table.getInt("ContactID"), service, tran, table.getString("contactStatus")); //FK
                        StatusType statusType = new StatusType(table.getInt("statusTypeID"), table.getString("StatusName"));
                        RequestType requestType = new RequestType(table.getInt("reqTypeID"), table.getString("ReqTypeName")); //FK
                        String ReDescription = table.getString("requestDes");
                        list.add(new Request(ReqID, Acc, adminAcc, Contact, statusType, requestType, ReDescription));
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;

    }

    public int getTotalAllAdminContractByContractStatus(int accountID, int status) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT count(*)as [count]\n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "AND Contact.status =?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setInt(3, status);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    total = table.getInt("count");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return total;
    }

    public ArrayList<Request> getAllAdminContractByContractStatus(Account acc, int index, int status) {
        ArrayList<Request> list = new ArrayList<>();

        AccountDAO d = new AccountDAO();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT Request.[ReqID],Request.[AccountID],Request.[ManagerAccountID],\n"
                        + "\n"
                        + "Request.[Description] as [requestDes],\n"
                        + "\n"
                        + "RequestType.reqTypeID ,RequestType.ReqTypeName,\n"
                        + "\n"
                        + "StatusType.StatusID as [statusTypeID],StatusType.StatusName,\n"
                        + "\n"
                        + "Contact.ContactID,Contact.status as [contactStatus],\n"
                        + "\n"
                        + "Service.SerID,Service.SerName,Service.Status as [ServiceStatus],Service.price as[servicePrice],\n"
                        + "\n"
                        + "Transaction_infor.TranID,Transaction_infor.Date as [tranDate], Transaction_infor.quantity as[tranQuantity],\n"
                        + "\n"
                        + "Transaction_infor.money as[tranMoney],Transaction_infor.Status as[tranStatus],\n"
                        + "\n"
                        + "Product.prd_ID,Product.name as[productName],Product.thumnail as[productThumnail],Product.description as[productDes],\n"
                        + "\n"
                        + "Product.price as[productPrice],Product.speed as[productSpeed], Product.status as[productStatus] ,\n"
                        + "\n"
                        + "Category.cate_ID , Category.Name as[cateName] ,Category.icon as[cateIcon],Category.status as [cateStatus]\n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "AND Contact.status =?\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setInt(3, status);
                pst.setInt(4, (index - 1) * 5);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");

                        ProductCategories cate = new ProductCategories(table.getInt("cate_ID"), table.getString("cateName"), table.getString("cateIcon"), table.getString("cateStatus"));

                        Product product = new Product(table.getInt("prd_ID"), table.getString("productName"), table.getString("productThumnail"), table.getString("productDes"), table.getInt("productPrice"), table.getInt("productSpeed"), cate, table.getString("productStatus"));

                        java.util.Date tranDate = d.convertStringToDate(table.getString("tranDate"));
                        Transaction tran = new Transaction(table.getInt("TranID"), tranDate, table.getInt("tranQuantity"), table.getDouble("tranMoney"), table.getString("tranStatus"), product);

                        Service service = new Service(table.getInt("SerID"), table.getString("SerName"), table.getString("ServiceStatus"), table.getInt("servicePrice"));

                        Account Acc = d.SearchAccountByID(table.getInt("AccountID"));  //FK
                        Account adminAcc = d.SearchAccountByID(table.getInt("ManagerAccountID")); //FK           
                        Contact Contact = new Contact(table.getInt("ContactID"), service, tran, table.getString("contactStatus")); //FK
                        StatusType statusType = new StatusType(table.getInt("statusTypeID"), table.getString("StatusName"));
                        RequestType requestType = new RequestType(table.getInt("reqTypeID"), table.getString("ReqTypeName")); //FK
                        String ReDescription = table.getString("requestDes");
                        list.add(new Request(ReqID, Acc, adminAcc, Contact, statusType, requestType, ReDescription));
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;

    }

    public int getTotalAllAdminContractByContractStatusAndContactID(int accountID, int status, String proID) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT count(*)as [count]\n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "AND Contact.status =? and Contact.ContactID LIKE ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setInt(3, status);
                pst.setString(4, "%" + proID + "%");
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    total = table.getInt("count");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return total;
    }

    public ArrayList<Request> getAllAdminContractByContractStatusAndContactID(Account acc, int index, int status, String proID) {
        ArrayList<Request> list = new ArrayList<>();

        AccountDAO d = new AccountDAO();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELEcT Request.[ReqID],Request.[AccountID],Request.[ManagerAccountID],\n"
                        + "\n"
                        + "Request.[Description] as [requestDes],\n"
                        + "\n"
                        + "RequestType.reqTypeID ,RequestType.ReqTypeName,\n"
                        + "\n"
                        + "StatusType.StatusID as [statusTypeID],StatusType.StatusName,\n"
                        + "\n"
                        + "Contact.ContactID,Contact.status as [contactStatus],\n"
                        + "\n"
                        + "Service.SerID,Service.SerName,Service.Status as [ServiceStatus],Service.price as[servicePrice],\n"
                        + "\n"
                        + "Transaction_infor.TranID,Transaction_infor.Date as [tranDate], Transaction_infor.quantity as[tranQuantity],\n"
                        + "\n"
                        + "Transaction_infor.money as[tranMoney],Transaction_infor.Status as[tranStatus],\n"
                        + "\n"
                        + "Product.prd_ID,Product.name as[productName],Product.thumnail as[productThumnail],Product.description as[productDes],\n"
                        + "\n"
                        + "Product.price as[productPrice],Product.speed as[productSpeed], Product.status as[productStatus] ,\n"
                        + "\n"
                        + "Category.cate_ID , Category.Name as[cateName] ,Category.icon as[cateIcon],Category.status as [cateStatus]\n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "AND Contact.status =? and Contact.ContactID LIKE ?\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setInt(3, status);
                pst.setString(4, "%" + proID + "%");
                pst.setInt(5, (index - 1) * 5);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");

                        ProductCategories cate = new ProductCategories(table.getInt("cate_ID"), table.getString("cateName"), table.getString("cateIcon"), table.getString("cateStatus"));

                        Product product = new Product(table.getInt("prd_ID"), table.getString("productName"), table.getString("productThumnail"), table.getString("productDes"), table.getInt("productPrice"), table.getInt("productSpeed"), cate, table.getString("productStatus"));

                        java.util.Date tranDate = d.convertStringToDate(table.getString("tranDate"));
                        Transaction tran = new Transaction(table.getInt("TranID"), tranDate, table.getInt("tranQuantity"), table.getDouble("tranMoney"), table.getString("tranStatus"), product);

                        Service service = new Service(table.getInt("SerID"), table.getString("SerName"), table.getString("ServiceStatus"), table.getInt("servicePrice"));

                        Account Acc = d.SearchAccountByID(table.getInt("AccountID"));  //FK
                        Account adminAcc = d.SearchAccountByID(table.getInt("ManagerAccountID")); //FK           
                        Contact Contact = new Contact(table.getInt("ContactID"), service, tran, table.getString("contactStatus")); //FK
                        StatusType statusType = new StatusType(table.getInt("statusTypeID"), table.getString("StatusName"));
                        RequestType requestType = new RequestType(table.getInt("reqTypeID"), table.getString("ReqTypeName")); //FK
                        String ReDescription = table.getString("requestDes");
                        list.add(new Request(ReqID, Acc, adminAcc, Contact, statusType, requestType, ReDescription));
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;

    }

}
