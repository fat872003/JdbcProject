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
import DTO.Role;
import DTO.Service;
import DTO.StatusType;
import DTO.Transaction;
import controllers.CONSTANTS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import mylibs.DBUtils;

/**
 *
 * @author ACER
 */
public class RequestDAO {

    public String formatDate(Date date) {

        SimpleDateFormat outputFormat = new SimpleDateFormat("HH-mm-dd-MM-yyyy");
        String dateString = outputFormat.format(date);
        return dateString;

    }

    public int getTotalRequest(int AccountID) {
        Connection cn = null;
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT count(*)as[Count]\n"
                        + "FROM Request \n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID\n"
                        + "where Request.AccountID = ? \n"
                        + "and Request.reqTypeID=3\n"
                        + "and Contact.status='true'\n"
                        + "and Service.SerID=3 \n"
                        + "and Product.status='true'\n"
                        + "and Category.status='true'";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, AccountID);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    total = table.getInt("Count");
                }
            }
        } catch (Exception e) {
        }

        return total;

    }

    public ArrayList<Request> getMyDeviceInfor(Account acc, int index) {
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
                        + "join Category on Product.cate_ID = Category.cate_ID\n"
                        + "where Request.AccountID = ? \n"
                        + "and Request.reqTypeID=3\n"
                        + "and Contact.status='true'\n"
                        + "and Service.SerID=3 \n"
                        + "and Product.status='true'\n"
                        + "and Category.status='true' ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 2 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, (index - 1) * 2);
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

    public int getTotalDeviceBySearching(Account acc, int proID) {
        Connection cn = null;
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT count(*)as[Count]\n"
                        + "FROM Request \n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "join Product on Transaction_infor.prd_ID = Product.prd_ID\n"
                        + "join Category on Product.cate_ID = Category.cate_ID\n"
                        + "where Request.AccountID = ? \n"
                        + "and Request.reqTypeID=3\n"
                        + "and Contact.status='true'\n"
                        + "and Service.SerID=3 \n"
                        + "and Product.status='true'\n"
                        + "and Category.status='true' and Product.prd_ID = ? ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, proID);

                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    total = table.getInt("Count");
                }
            }
        } catch (Exception e) {
        }

        return total;
    }

    public ArrayList<Request> getMyDeviceInforBySearching(Account acc, int index, int proID) {
        ArrayList<Request> list = new ArrayList<>();
        AccountDAO d = new AccountDAO();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT Request.[ReqID],Request.[AccountID],Request.[ManagerAccountID],\n"
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
                        + "join Category on Product.cate_ID = Category.cate_ID\n"
                        + "where Request.AccountID = ? \n"
                        + "and Request.reqTypeID=3\n"
                        + "and Contact.status='true'\n"
                        + "and Service.SerID=3 \n"
                        + "and Product.status='true'\n"
                        + "and Category.status='true'  and Product.prd_ID = ?  ORDER BY [ReqID] \n"
                        + "OFFSET ? ROWS FETCH NEXT 2 ROWS ONLY; ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, proID);
                pst.setInt(3, index);
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

//test case
    //Get Req:
    public Request getRequestByID(int id) {
        Request rs = null;
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT [ReqID], [AccountID],[ManagerAccountID] ,[ContactID],[StatusID], [reqTypeID], [Description] FROM [dbo].[Request]"
                        + "WHERE [ReqID] = ? ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");
                        //getAccObj
                        int accID = table.getInt("AccountID");
                        Account account = new AccountDAO().getAccountByID(accID + "");
                        //end accobj

                        //getAdminAccObj
                        int adminAccID = table.getInt("ManagerAccountID");
                        Account adminAcc = new AccountDAO().getAccountByID(adminAccID + "");
                        //end accobj

                        //getContactObj
                        int contactID = table.getInt("ContactID");
                        Contact contact = new ContactDAO().getContactByID(contactID);
                        //end contactobj

                        int statusID = table.getInt("StatusID");
                        StatusType statusType = new StatusTypeDAO().getStatusTypeByID(statusID);

                        //getReqObj
                        int reqTypeID = table.getInt("reqTypeID");
                        RequestType rqt = new RequestTypeDAO().getRequestTypeByID(reqTypeID);
                        //=========

                        String Description = table.getString("Description");

                        rs = new Request(ReqID, account, adminAcc, contact, statusType, rqt, Description);

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
        return rs;
    }

    public ArrayList<Request> getSortRequest(String dateSort, String phoneSearch, String status) {
        ArrayList<Request> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "DECLARE @SortOrder varchar = ?  \n"
                        + "SELECT [ReqID], R.[AccountID],[ManagerAccountID] , R.[ContactID] ,R.[StatusID], [reqTypeID], [Description] FROM [dbo].[Request] as R\n"
                        + "JOIN [dbo].[Account] as A ON R.[AccountID] = A.[AccountID]\n"
                        + "JOIN [dbo].[StatusType] as ST ON R.[StatusID] = ST.[StatusID]\n"
                        + "JOIN [dbo].[Contact] as C ON R.[ContactID] = C.[ContactID]\n"
                        + "JOIN [dbo].[Transaction_infor] as T ON C.[TranID] =  T.[TranID]\n"
                        + "WHERE A.Phone like ?  \n"
                        + "AND ST.[StatusID] like ?    \n"
                        + "ORDER BY\n"
                        + "    CASE WHEN @SortOrder = '1' THEN T.Date END ASC,  \n"
                        + "    CASE WHEN @SortOrder = '2' THEN T.Date END DESC ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, dateSort);
                st.setString(2, "%" + phoneSearch + "%");
                st.setString(3, "%" + status + "%");
                ResultSet table = st.executeQuery();

                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");
                        //getAccObj
                        int accID = table.getInt("AccountID");
                        Account account = new AccountDAO().getAccountByID(accID + "");
                        //end accobj

                        //getAdminAccObj
                        int adminAccID = table.getInt("ManagerAccountID");
                        Account adminAcc = new AccountDAO().getAccountByID(adminAccID + "");
                        //end accobj

                        //getContactObj
                        int contactID = table.getInt("ContactID");
                        Contact contact = new ContactDAO().getContactByID(contactID);
                        //end contactobj

                        int statusID = table.getInt("StatusID");
                        StatusType statusType = new StatusTypeDAO().getStatusTypeByID(statusID);

                        //getReqObj
                        int reqTypeID = table.getInt("reqTypeID");
                        RequestType rqt = new RequestTypeDAO().getRequestTypeByID(reqTypeID);
                        //=========

                        String Description = table.getString("Description");

                        list.add(new Request(ReqID, account, adminAcc, contact, statusType, rqt, Description));

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
//POST UPDATE=======================================================================:

    public int addRequest(Request request) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {

                String sql
                        = "INSERT INTO [dbo].[Request]([AccountID],[ManagerAccountID],[ContactID],[StatusID],[reqTypeID],[Description])\n"
                        + "VALUES (?,?,(SELECT Max([ContactID]) FROM [dbo].[Contact]),?,?,?)";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, request.getAcc().getAccountID());
                pst.setInt(2, request.getAdminAcc().getAccountID());
                pst.setInt(3, request.getStatusType().getStatusID());
                pst.setInt(4, request.getRequestType().getRqTyID());
                pst.setString(5, request.getDescription());

                //Tra ve 0/1
                result = pst.executeUpdate();

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

        return result;
    }

    public int updateRequestStatus(String statusID, String reqID) {
        Connection cn = null;
        int result = 0;

        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String s = "UPDATE [dbo].[Request]\n"
                        + "SET [StatusID] =? \n"
                        + "WHERE [ReqID]=? ";
                PreparedStatement pst = cn.prepareStatement(s);
                pst.setString(1, statusID);
                pst.setString(2, reqID);

                result = pst.executeUpdate();
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
        return result;
    }

    public int detachManagerID(String reqID) {
        Connection cn = null;
        int result = 0;

        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String s = "UPDATE [dbo].[Request]\n"
                        + "SET [dbo].[Request].[ManagerAccountID] = null\n"
                        + "WHERE [ReqID]=?";
                PreparedStatement pst = cn.prepareStatement(s);
                pst.setString(1, reqID);

                result = pst.executeUpdate();
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
        return result;
    }

    public int attachManagerID(String accID, String reqID) {
        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String s = "UPDATE [dbo].[Request]\n"
                        + "SET [dbo].[Request].[ManagerAccountID] = ? \n"
                        + "WHERE [ReqID]=?";
                PreparedStatement pst = cn.prepareStatement(s);
                pst.setString(1, accID);
                pst.setString(2, reqID);

                result = pst.executeUpdate();
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
        return result;
    }

    public ArrayList<Request> getSortRequestByManagerID(String dateSort, String phoneSearch, String status, int managerID) {
        ArrayList<Request> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "DECLARE @SortOrder varchar = ?\n"
                        + "SELECT [ReqID], R.[AccountID],[ManagerAccountID] , R.[ContactID] ,R.[StatusID], [reqTypeID], [Description] FROM [dbo].[Request] as R\n"
                        + "JOIN [dbo].[Account] as A ON R.[AccountID] = A.[AccountID]\n"
                        + "JOIN [dbo].[StatusType] as ST ON R.[StatusID] = ST.[StatusID]\n"
                        + "JOIN [dbo].[Contact] as C ON R.[ContactID] = C.[ContactID]\n"
                        + "JOIN [dbo].[Transaction_infor] as T ON C.[TranID] =  T.[TranID]\n"
                        + "WHERE A.Phone like ?\n"
                        + "AND ST.[StatusID] like ?  \n"
                        + "AND [ManagerAccountID] like ?\n"
                        + "ORDER BY\n"
                        + "CASE WHEN @SortOrder = '1' THEN T.Date END ASC, \n"
                        + "CASE WHEN @SortOrder = '2' THEN T.Date END DESC ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, dateSort);
                st.setString(2, "%" + phoneSearch + "%");
                st.setString(3, "%" + status + "%");
                st.setInt(4, managerID);
                ResultSet table = st.executeQuery();

                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");
                        //getAccObj
                        int accID = table.getInt("AccountID");
                        Account account = new AccountDAO().getAccountByID(accID + "");
                        //end accobj

                        //getAdminAccObj
                        int adminAccID = table.getInt("ManagerAccountID");
                        Account adminAcc = new AccountDAO().getAccountByID(adminAccID + "");
                        //end accobj

                        //getContactObj
                        int contactID = table.getInt("ContactID");
                        Contact contact = new ContactDAO().getContactByID(contactID);
                        //end contactobj

                        int statusID = table.getInt("StatusID");
                        StatusType statusType = new StatusTypeDAO().getStatusTypeByID(statusID);

                        //getReqObj
                        int reqTypeID = table.getInt("reqTypeID");
                        RequestType rqt = new RequestTypeDAO().getRequestTypeByID(reqTypeID);
                        //=========

                        String Description = table.getString("Description");

                        list.add(new Request(ReqID, account, adminAcc, contact, statusType, rqt, Description));

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

    //============================================ view request
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

    public int viewTotalAllUser(int accountID) {
        Connection cn = null;
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT count(*)as[count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "where (Request.[AccountID]=? or Request.[ManagerAccountID]=? ) ";
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

    public ArrayList<Request> viewAllUser(Account acc, int index) {
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
                        + "Transaction_infor.money as[tranMoney],Transaction_infor.Status as[tranStatus], Transaction_infor.prd_ID as[TranProID] \n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )\n"
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

                        java.util.Date tranDate = d.convertStringToDate(table.getString("tranDate"));
                        Transaction tran = new Transaction(table.getInt("TranID"), tranDate, table.getInt("tranQuantity"), table.getDouble("tranMoney"), table.getString("tranStatus"), table.getString("TranProID"));

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

    public int viewTotalAllUserByID(int accountID, String reqID) {
        Connection cn = null;
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT count(*)as[count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "where (Request.[AccountID]=? or Request.[ManagerAccountID]=? ) and Request.ReqID like ? ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, "%" + reqID + "%");
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

    public ArrayList<Request> viewAllUserByID(Account acc, int index, String reqID) {
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
                        + "Transaction_infor.money as[tranMoney],Transaction_infor.Status as[tranStatus], Transaction_infor.prd_ID as[TranProID]\n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "where (Request.[AccountID]=? or Request.[ManagerAccountID]=? ) and Request.ReqID like ?\n"
                        + "ORDER BY [ReqID]  \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, "%" + reqID + "%");
                pst.setInt(4, (index - 1) * 5);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");

                        java.util.Date tranDate = d.convertStringToDate(table.getString("tranDate"));
                        Transaction tran = new Transaction(table.getInt("TranID"), tranDate, table.getInt("tranQuantity"), table.getDouble("tranMoney"), table.getString("tranStatus"), table.getString("TranProID"));

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

    public int viewTotalAllUserByStatus(int accountID, String status) {
        Connection cn = null;
        int total = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " SELEcT count(*)as[count]\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "where (Request.[AccountID]=? or Request.[ManagerAccountID]=? ) and  Request.StatusID = ? ";
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

    public ArrayList<Request> viewAllUserByStatus(Account acc, int index, String status) {
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
                        + "Transaction_infor.money as[tranMoney],Transaction_infor.Status as[tranStatus], Transaction_infor.prd_ID as[TranProID]\n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "where (Request.[AccountID]=? or Request.[ManagerAccountID]=? ) and  Request.StatusID = ? \n"
                        + "ORDER BY [ReqID]  \n"
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

                        java.util.Date tranDate = d.convertStringToDate(table.getString("tranDate"));
                        Transaction tran = new Transaction(table.getInt("TranID"), tranDate, table.getInt("tranQuantity"), table.getDouble("tranMoney"), table.getString("tranStatus"), table.getString("TranProID"));

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

    public int viewTotalAllUserByIDAndStatus(int accountID, String reqID, String status) {
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
                        + "where (Request.[AccountID]=? or Request.[ManagerAccountID]=? )and Request.ReqID like ? and  Request.StatusID like ? ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                pst.setInt(2, accountID);
                pst.setString(3, "%" + reqID + "%");
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

    public ArrayList<Request> viewAllUserByIDAndStatus(Account acc, int index, String reqID, String status) {
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
                        + "Transaction_infor.money as[tranMoney],Transaction_infor.Status as[tranStatus], Transaction_infor.prd_ID as[TranProID]\n"
                        + "\n"
                        + "FROM Request \n"
                        + "join StatusType on Request.StatusID = StatusType.StatusID\n"
                        + "join RequestType on Request.reqTypeID=RequestType.reqTypeID\n"
                        + "join Contact on Request.ContactID = Contact.ContactID \n"
                        + "join Service on Contact.SerID = Service.SerID\n"
                        + "join Transaction_infor on Contact.TranID = Transaction_infor.TranID \n"
                        + "where (Request.[AccountID]=? or Request.[ManagerAccountID]=? ) and Request.ReqID like ? and  Request.StatusID like ? \n"
                        + "ORDER BY [ReqID]  \n"
                        + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY;";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, acc.getAccountID());
                pst.setInt(2, acc.getAccountID());
                pst.setString(3, "%" + reqID + "%");
                pst.setString(4, status);
                pst.setInt(5, (index - 1) * 5);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int ReqID = table.getInt("ReqID");

                        java.util.Date tranDate = d.convertStringToDate(table.getString("tranDate"));
                        Transaction tran = new Transaction(table.getInt("TranID"), tranDate, table.getInt("tranQuantity"), table.getDouble("tranMoney"), table.getString("tranStatus"), table.getString("TranProID"));

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

    public int updateStatusInViewRequestPage(String accid, String status) {

        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update Request set StatusID = ? where ReqID = ?;\n"
                        + "\n"
                        + "UPDATE Contact\n"
                        + "SET status = 0 FROM Contact INNER JOIN Request ON Contact.ContactID = Request.ContactID WHERE Request.ReqID = ?;\n"
                        + "\n"
                        + "\n"
                        + "UPDATE Transaction_infor SET status = 0 FROM Transaction_infor INNER JOIN Contact ON Contact.TranID = Transaction_infor.TranID\n"
                        + "WHERE Contact.ContactID IN (	\n"
                        + "  SELECT Request.ContactID\n"
                        + "  FROM Request\n"
                        + "  WHERE Request.ReqID = ?\n"
                        + ");";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(status.trim()));
                pst.setInt(2, Integer.parseInt(accid.trim()));
                pst.setInt(3, Integer.parseInt(accid.trim()));
                pst.setInt(4, Integer.parseInt(accid.trim()));
                result = pst.executeUpdate();
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
                // Xử lý ngoại lệ hoặc ném ngoại lệ để được xử lý ở nơi gọi phương thức
            }
        }
        return result;

    }

    public int CreateNewTranClientInReq(String proID) {

        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Transaction_infor (Date, quantity, money, Status, prd_ID) "
                        + "VALUES (GETDATE(), 0, 0, 0, null);";
                     PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(proID));
                result = pst.executeUpdate();// Update result variable

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
                // Xử lý ngoại lệ hoặc ném ngoại lệ để được xử lý ở nơi gọi phương thức
            }
        }
        return result;

    }

    public int CreateNewContactClientInReq(String serID) {

        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Contact (SerID, TranID, status)\n"
                        + "VALUES (?,(SELECT TOP 1 Transaction_infor.TranID FROM Transaction_infor ORDER BY Transaction_infor.TranID DESC\n"
                        + ") , 1); ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(serID));
                result = pst.executeUpdate();  // Update result variable

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
                // Xử lý ngoại lệ hoặc ném ngoại lệ để được xử lý ở nơi gọi phương thức
            }
        }
        return result;

    }

    public int CreateNewReqClientInReq(int accID, String reqID, String des) {

        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Request (AccountID, ManagerAccountID, ContactID, StatusID, reqTypeID, Description)\n"
                        + "VALUES (?, null, (SELECT TOP 1 Contact.ContactID FROM Contact ORDER BY Contact.ContactID DESC), 1 ,?, ?);";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accID);
                pst.setInt(2, Integer.parseInt(reqID.trim()));
                pst.setString(3, des);
                result = pst.executeUpdate();  // Update result variable

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
                // Xử lý ngoại lệ hoặc ném ngoại lệ để được xử lý ở nơi gọi phương thức
            }
        }
        return result;

    }
    public static void main(String[] args) {
        RequestDAO rq = new  RequestDAO();
        int check= rq.CreateNewTranClientInReq(1+"");
        System.out.println(""+check);
    }
}
