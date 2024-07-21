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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import mylibs.DBUtils;

/**
 *
 * @author ACER
 */
public class TransactionDAO {

    //Add Utils
    public java.util.Date convertStringToDate(String dateString) throws ParseException {
        java.util.Date date;
        // validation  
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            date = sdf.parse(dateString);
            return date;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    //====

    public Transaction getTransByID(int id) {
        Transaction trans = null;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [TranID] ,[Date], [money],[Status], [prd_ID] FROM [dbo].[Transaction_infor]"
                        + "WHERE [TranID] = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null)
                {
                    while (table.next())
                    {
                        int tranID = table.getInt("TranID");
                        java.util.Date date = convertStringToDate(table.getString("Date"));
                        double money = table.getDouble("money");
                        String status = (table.getBoolean("Status")) ? "1" : "0";
                        //getPrdObj
                        int prdID = table.getInt("prd_ID");
                        Product prd = new ProductDAO().getProductByID(prdID + "");
                        //end===========================
                        
                        //bug
                        trans = new Transaction(tranID, date, 1 , money, status, prd);

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
        return trans;
    }

    //POST UPDATE=======================================================================:
    public int addNewTransaction(Transaction trans) {
        Connection cn = null;
        int result = 0;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "INSERT INTO [dbo].[Transaction_infor] ([Date],[money],[Status],[prd_ID])\n"
                        + "VALUES (?, ?, ?, ?) ";
                PreparedStatement pst = cn.prepareStatement(sql);
                if (trans.getDate() != null)
                {
                    pst.setTimestamp(1, new java.sql.Timestamp(trans.getDate().getTime()));
                } else
                {
                    pst.setDate(1, null);
                }
                pst.setDouble(2, trans.getMoney());
                pst.setString(3, trans.getStatus());

                if (trans.getProduct().getPrd_ID() == 0)
                {
                    pst.setString(4, null);
                } else
                {
                    pst.setInt(4, trans.getProduct().getPrd_ID());
                }

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

      //================================================= funtion veiw TRansaction
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

    public int getTotalAllTranClient(int accountID) {
        Connection cn = null;
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "  SELEcT count(*)as[count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )  and Contact.status='true' ";
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

    public ArrayList<Request> getAllClient(Account acc, int index) {
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
                        + "join Category on Product.cate_ID = Category.cate_ID  where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )  and Contact.status='true'  \n"
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

    public int getTotalTranCLientByTranID(int accID, String proID) {
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
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )   and Contact.status='true'\n"
                        + " and Transaction_infor.TranID like  ? ;";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accID);
                pst.setInt(2, accID);
                pst.setString(3, proIDString);
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

    public ArrayList<Request> getAllTranClientByTranID(Account acc, int index, String proID) {
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
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )   and Contact.status='true'\n"
                        + " and Transaction_infor.TranID like ?\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, proIDString);
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

    public int getTotalAllTranClientByStatusTran(int accountID, String status) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "and Contact.status ='true'\n"
                        + "AND Transaction_infor.Status like ? ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, status);
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

    public ArrayList<Request> getAllTranClientByTranStatus(Account acc, int index, String status) {
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
                        + "and Contact.status ='true'\n"
                        + "AND Transaction_infor.Status like  ? \n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, status);
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

    public int getTotalAllTranClientByInMonth(int accountID) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "and Contact.status ='true' and MONTH(Transaction_infor.Date) = MONTH(GETDATE()) ";
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

    public ArrayList<Request> getAllTranClientByInMonth(Account acc, int index) {
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
                        + "and Contact.status ='true'\n"
                        + "AND  MONTH(Transaction_infor.Date) = MONTH(GETDATE())\n"
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

    public int getTotalAllTranClientByTranIDAndStatusTran(int accountID, String proID, String status) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "and Contact.status ='true'\n"
                        + "AND Transaction_infor.Status like ? and Transaction_infor.TranID like ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, status);
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

    public ArrayList<Request> getAllTranClientByTranIDANDTranStatus(Account acc, int index, String status, String proID) {
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
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]= ? or Request.[ManagerAccountID]= ? )\n"
                        + "and Contact.status ='true'\n"
                        + "AND Transaction_infor.Status like ?  and Transaction_infor.TranID like ?\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, status);
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

    public int getTotalAllTranClientByTranIDAndInMonth(int accountID, String proID) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "and Contact.status ='true'\n"
                        + " and Transaction_infor.TranID like ? and MONTH(Transaction_infor.Date) = MONTH(GETDATE())";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, "%" + proID + "%");

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

    public ArrayList<Request> getAllTranClientByTranIDANDInMonth(Account acc, int index, String proID) {
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
                        + "and Contact.status ='true'  and Transaction_infor.TranID like  ? \n"
                        + "AND  MONTH(Transaction_infor.Date) = MONTH(GETDATE())\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, "%" + proID + "%");
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

    public int getTotalAllTranClientByStatusTranAndInMonth(int accountID, String status) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "and Contact.status ='true'\n"
                        + "AND Transaction_infor.Status like ? and MONTH(Transaction_infor.Date) = MONTH(GETDATE()) ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, status);
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

    public ArrayList<Request> getAllTranClientByTranStatusANDInMonth(Account acc, int index, String status) {
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
                        + "and Contact.status ='true'\n"
                        + "AND Transaction_infor.Status like ? and MONTH(Transaction_infor.Date) = MONTH(GETDATE())\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, status);
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

    public int getTotalAllTranClientByStatusIDAndStatusTranAndInMonth(int accountID, String proID, String status) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "and Contact.status ='true' and Transaction_infor.TranID like ? \n"
                        + "AND Transaction_infor.Status like ?  and MONTH(Transaction_infor.Date) = MONTH(GETDATE())";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, "%" + proID + "%");
                pst.setString(4, status);
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

    public ArrayList<Request> getAllTranClientByTranIDAndTranStatusANDInMonth(Account acc, int index, String proID, String status) {
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
                        + "and Contact.status ='true'\n"
                        + "and Transaction_infor.TranID like ? \n"
                        + "AND Transaction_infor.Status like ? and MONTH(Transaction_infor.Date) = MONTH(GETDATE())\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, "%" + proID + "%");
                pst.setString(4, status);
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

    //============================= admin view
    public int getTotalAllTranAdmin(int accountID) {
        Connection cn = null;
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "  SELEcT count(*)as[count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )   ";
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

    public ArrayList<Request> getAllAdmin(Account acc, int index) {
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
                        + "join Category on Product.cate_ID = Category.cate_ID  where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )   \n"
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

    public int getTotalTranAdminByTranID(int accID, String proID) {
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
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? ) \n"
                        + " and Transaction_infor.TranID like  ? ;";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accID);
                pst.setInt(2, accID);
                pst.setString(3, proIDString);
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

    public ArrayList<Request> getAllTranAdminByTranID(Account acc, int index, String proID) {
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
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=?)  \n"
                        + " and Transaction_infor.TranID like ?\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, proIDString);
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

    public int getTotalAllTranAdminByStatusTran(int accountID, String status) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "AND Transaction_infor.Status like ? ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, status);
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

    public ArrayList<Request> getAllTranAdminByTranStatus(Account acc, int index, String status) {
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
                        + "AND Transaction_infor.Status like  ? \n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, status);
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

    public int getTotalAllTranAdmintByInMonth(int accountID) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + " and MONTH(Transaction_infor.Date) = MONTH(GETDATE()) ";
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

    public ArrayList<Request> getAllTranAdmintByInMonth(Account acc, int index) {
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
                        + "AND  MONTH(Transaction_infor.Date) = MONTH(GETDATE())\n"
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

    public int getTotalAllTranAdminByTranIDAndStatusTran(int accountID, String proID, String status) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "\n"
                        + "AND Transaction_infor.Status like ? and Transaction_infor.TranID like ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, status);
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

    public ArrayList<Request> getAllTranAdminByTranIDANDTranStatus(Account acc, int index, String status, String proID) {
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
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]= ? or Request.[ManagerAccountID]= ? )\n"
                        + "AND Transaction_infor.Status like ?  and Transaction_infor.TranID like ?\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, status);
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

        public int getTotalAllTranAdminByTranIDAndInMonth(int accountID, String proID) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + " and Transaction_infor.TranID like ? and MONTH(Transaction_infor.Date) = MONTH(GETDATE())";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, "%" + proID + "%");

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

    public ArrayList<Request> getAllTranAdminByTranIDANDInMonth(Account acc, int index, String proID) {
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
                        + " and Transaction_infor.TranID like  ? \n"
                        + "AND  MONTH(Transaction_infor.Date) = MONTH(GETDATE())\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, "%" + proID + "%");
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
    
     public int getTotalAllTranAdminByStatusTranAndInMonth(int accountID, String status) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "AND Transaction_infor.Status like ? and MONTH(Transaction_infor.Date) = MONTH(GETDATE()) ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, status);
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

    public ArrayList<Request> getAllTranAdminByTranStatusANDInMonth(Account acc, int index, String status) {
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
                        + "AND Transaction_infor.Status like ? and MONTH(Transaction_infor.Date) = MONTH(GETDATE())\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, status);
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
    
        public int getTotalAllTranAdminByStatusIDAndStatusTranAndInMonth(int accountID, String proID, String status) {
        Connection cn = null;

        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT\n"
                        + " count(*)as [count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
                        + "and Transaction_infor.TranID like ? \n"
                        + "AND Transaction_infor.Status like ?  and MONTH(Transaction_infor.Date) = MONTH(GETDATE())";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, "%" + proID + "%");
                pst.setString(4, status);
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

    public ArrayList<Request> getAllTranAdminByTranIDAndTranStatusANDInMonth(Account acc, int index, String proID, String status) {
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
                        + "and Transaction_infor.TranID like ? \n"
                        + "AND Transaction_infor.Status like ? and MONTH(Transaction_infor.Date) = MONTH(GETDATE())\n"
                        + "ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, "%" + proID + "%");
                pst.setString(4, status);
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
