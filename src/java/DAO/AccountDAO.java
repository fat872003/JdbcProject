/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Account;
import DTO.Employee;
import DTO.Major;
import DTO.Role;
import DTO.Service;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import mylibs.DBUtils;

/**
 *
 * @author ACER
 */
public class AccountDAO {

    //FAT===========================
    public java.util.Date convertStringToDate(String dateString) throws ParseException {
        java.util.Date date;
        // validation  
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            date = sdf.parse(dateString);
            return date;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String convertDateToString(java.util.Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

    // dang nhap account
    public Account getClientAccount(String phone, String gmail, String pass) {
        Account rs = null;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String s = "SELECT [AccountID], [LastName], [FirstName], [Phone], [Gmail],[Password],[sex] ,[status], [PolicyStatus], Account.[RoleID],Role.RoleName ,[Script] FROM\n"
                        + "[dbo].[Account] JOIN [dbo].[Role] ON [dbo].[Account].[RoleID] = [dbo].[Role].RoleID\n"
                        + "where (Account.Phone = ? and  Account.Password like  ? COLLATE Latin1_General_CS_AS  and Account.RoleID =1) or (Account.Gmail like ? and  Account.Password = ? COLLATE Latin1_General_CS_AS and Account.RoleID != 1) ";
                PreparedStatement pst = cn.prepareStatement(s);
                pst.setString(1, phone);
                pst.setString(2, pass);
                pst.setString(3, gmail);
                pst.setString(4, pass);
                ResultSet table = pst.executeQuery();

                if (table != null && table.next())
                {
                    int s_accid = table.getInt("AccountID");
                    String s_lastName = table.getString("LastName");
                    String s_firstName = table.getString("FirstName");
                    String s_phone = table.getString("Phone");
                    String s_gmail = table.getString("Gmail");
                    String s_password = table.getString("Password");
                    String s_sex = table.getString("sex");
                    String s_status = (table.getBoolean("status")) ? "1" : "0";
                    String s_policystatus = (table.getBoolean("status")) ? "1" : "0";
                    Role role = new Role(table.getInt("RoleID"), table.getString("RoleName"));
                    String s_script = table.getString("Script");
                    rs = new Account(s_accid, s_lastName, s_firstName, s_phone, s_gmail, s_password, s_sex, s_status, s_policystatus, role, s_script);
                };
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

    public Employee getEmployeInfor(int accountID) {
        Connection cn = null;
        Employee em = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT [AccountID],[DayOfBirth],[Identify_ID],[Working_Day],[Salary],Employee.MajorID, Major.MajorName  FROM Employee join Major\n"
                        + "on Employee.MajorID = Major.MajorID where AccountID = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    int s_accountID = table.getInt("AccountID");
                    java.util.Date s_birthdate = convertStringToDate(table.getString("DayOfBirth"));
                    java.util.Date s_workdate = convertStringToDate(table.getString("Working_day"));
                    String s_identifyID = table.getString("Identify_ID");
                    Double s_salary = table.getDouble("Salary");
                    Major s_major = new Major(table.getInt("MajorID"), table.getString("MajorName"));
                    em = new Employee(accountID, s_birthdate, s_identifyID, s_workdate, s_salary, s_major);
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

        return em;

    }

    // kiem tra thu mail co ton tai ko neu co !=0 neu ko thi =0
    public int checkMail(String gmail) {
        Connection cn = null;
        int count = 0;

        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(*) as [count] from [dbo].[Account] where [Gmail] =?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, gmail);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    count = table.getInt("count");
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

        return count;
    }

    public int checkPhone(String phone) {
        Connection cn = null;
        int count = 0;

        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(*)as [count] from [dbo].[Account] where [Phone] = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, phone);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    count = table.getInt("count");
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

        return count;
    }
    // kiem tra thu phone co ton tai ko neu co !=0 neu ko thi =0
    public int checkIdentifyID(String identify) {
        Connection cn = null;
        int count = 0;

        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(*)as [count] from [dbo].Employee where [Identify_ID] = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, identify);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    count = table.getInt("count");
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

        return count;
    }
  
    public int registerAccount(String lastName, String firstName, String phone, String gmail, String pass) {
        Connection cn = null;
        int result = 0;
        String sex = "--";
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = " INSERT INTO Account\n"
                        + "VALUES \n"
                        + "(?, ?, ?, ?, ?, ?, 1, 1, 1, NULL) ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, lastName);
                pst.setString(2, firstName);
                pst.setString(3, phone);
                pst.setString(4, gmail);
                pst.setString(5, pass);
                pst.setString(6, sex);
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

    public int createClientInfor(int accountID) {
        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Client\n"
                        + "VALUES (?, null);";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
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

    public int updateAccountInfor(int accountID, String first, String last, String phone, String gmail, String sex) {
        Connection cn = null;
        int result = 0;

        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String s = "Update Account set FirstName = ? , LastName= ? , Phone = ?, Gmail =?, sex=?  where AccountID=?";
                PreparedStatement pst = cn.prepareStatement(s);
                pst.setString(1, first);
                pst.setString(2, last);
                pst.setString(3, phone);
                pst.setString(4, gmail);
                pst.setString(5, sex);
                pst.setInt(6, accountID);
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

    public int updateEmployeeInfor(int accountID, String dayOfBirth, String workingDay, String identify) {
        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update Employee set DayOfBirth=?,Identify_ID = ?,Working_Day=? where AccountID=?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, dayOfBirth);
                pst.setString(2, identify);
                pst.setString(3, workingDay);
                pst.setInt(4, accountID);
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

    public static void main(String[] args) {

    }

    public Account SearchAccountByID(int idParam) {
        Account list = new Account();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT [AccountID], [LastName], [FirstName], [Phone], [Gmail],[Password],[sex] ,[status], [PolicyStatus], Account.[RoleID],Role.RoleName ,[Script] FROM\n"
                        + "[dbo].[Account] JOIN [dbo].[Role] ON [dbo].[Account].[RoleID] = [dbo].[Role].RoleID\n"
                        + "WHERE [dbo].[Account].[AccountID] = ? ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, idParam);
                ResultSet table = st.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int s_accid = table.getInt("AccountID");
                        String s_lastName = table.getString("LastName");
                        String s_firstName = table.getString("FirstName");
                        String s_phone = table.getString("Phone");
                        String s_gmail = table.getString("Gmail");
                        String s_password = table.getString("Password");
                        String s_sex = table.getString("sex");
                        String s_status = (table.getBoolean("status")) ? "1" : "0";
                        String s_policystatus = (table.getBoolean("status")) ? "1" : "0";
                        Role role = new Role(table.getInt("RoleID"), table.getString("RoleName"));
                        String s_script = table.getString("Script");
                        list = new Account(s_accid, s_lastName, s_firstName, s_phone, s_gmail, s_password, s_sex, s_status, s_policystatus, role, s_script);

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

    //END FAT==============================
    //KHANH=========================
    //dtb query function
    public Role getRoleByID(int id) {
        Role rs = null;
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT [RoleID], [RoleName]  FROM [dbo].[Role]\n"
                        + "WHERE [RoleID] = ?  ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int roleid = table.getInt("RoleID");
                        String roleName = table.getString("RoleName");
                        rs = new Role(roleid, roleName);
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

    //getaccount:
    public ArrayList<Account> getAllAccount() {
        ArrayList<Account> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT [AccountID], [LastName], [FirstName], [Phone], [Gmail],[Password],[sex] ,[status], [PolicyStatus], Account.[RoleID],Role.RoleName ,[Script] FROM\n"
                        + "[dbo].[Account] JOIN [dbo].[Role] ON [dbo].[Account].[RoleID] = [dbo].[Role].RoleID";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null) {
                    while (table.next()) {
                        int s_accid = table.getInt("AccountID");
                        String s_lastName = table.getString("LastName");
                        String s_firstName = table.getString("FirstName");
                        String s_phone = table.getString("Phone");
                        String s_gmail = table.getString("Gmail");
                        String s_password = table.getString("Password");
                        String s_sex = table.getString("sex");
                        String s_status = (table.getBoolean("status")) ? "1" : "0";
                        String s_policystatus = (table.getBoolean("PolicyStatus")) ? "1" : "0";
                        Role role = new Role(table.getInt("RoleID"), table.getString("RoleName"));
                        String s_script = table.getString("Script");
                        list.add(new Account(s_accid, s_lastName, s_firstName, s_phone, s_gmail, s_password, s_sex, s_status, s_policystatus, role, s_script));
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

    public ArrayList<Account> getAccountByPhone(String phoneParam) {
        ArrayList<Account> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT [AccountID], [LastName], [FirstName], [Phone], [Gmail],[Password],[sex] ,[status], [PolicyStatus], Account.[RoleID],Role.RoleName ,[Script] FROM\n"
                        + "[dbo].[Account] JOIN [dbo].[Role] ON [dbo].[Account].[RoleID] = [dbo].[Role].RoleID\n"
                        + "WHERE [dbo].[Account].[Phone] like ?  ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, "%" + phoneParam + "%");
                ResultSet table = st.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int s_accid = table.getInt("AccountID");
                        String s_lastName = table.getString("LastName");
                        String s_firstName = table.getString("FirstName");
                        String s_phone = table.getString("Phone");
                        String s_gmail = table.getString("Gmail");
                        String s_password = table.getString("Password");
                        String s_sex = table.getString("sex");
                        String s_status = (table.getBoolean("status")) ? "1" : "0";
                        String s_policystatus = (table.getBoolean("PolicyStatus")) ? "1" : "0";
                        Role role = new Role(table.getInt("RoleID"), table.getString("RoleName"));
                        String s_script = table.getString("Script");
                        list.add(new Account(s_accid, s_lastName, s_firstName, s_phone, s_gmail, s_password, s_sex, s_status, s_policystatus, role, s_script));
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

    public Account getAccountByID(String idParam) {
        Account list = new Account();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT [AccountID], [LastName], [FirstName], [Phone], [Gmail],[Password],[sex] ,[status], [PolicyStatus], Account.[RoleID],Role.RoleName ,[Script] FROM\n"
                        + "[dbo].[Account] JOIN [dbo].[Role] ON [dbo].[Account].[RoleID] = [dbo].[Role].RoleID\n"
                        + "WHERE [dbo].[Account].[AccountID] = ? ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, idParam);
                ResultSet table = st.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int s_accid = table.getInt("AccountID");
                        String s_lastName = table.getString("LastName");
                        String s_firstName = table.getString("FirstName");
                        String s_phone = table.getString("Phone");
                        String s_gmail = table.getString("Gmail");
                        String s_password = table.getString("Password");
                        String s_sex = table.getString("sex");
                        String s_status = (table.getBoolean("status")) ? "1" : "0";
                        String s_policystatus = (table.getBoolean("PolicyStatus")) ? "1" : "0";
                        Role role = new Role(table.getInt("RoleID"), table.getString("RoleName"));
                        String s_script = table.getString("Script");
                        list = new Account(s_accid, s_lastName, s_firstName, s_phone, s_gmail, s_password, s_sex, s_status, s_policystatus, role, s_script);

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

    public ArrayList<Role> getAllAccountRole() {
        ArrayList<Role> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT [RoleID],[RoleName] FROM [dbo].[Role]";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null) {
                    while (table.next()) {
                        int id = table.getInt("RoleID");
                        String roleName = table.getString("RoleName");
                        list.add(new Role(id, roleName));
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

    public ArrayList<Account> getAllTechnician() {
        ArrayList<Account> list = new ArrayList<>();
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [AccountID], [LastName], [FirstName], [Phone], [Gmail],[Password],[sex] ,[status], [PolicyStatus],[RoleID] ,[Script] FROM Account\n"
                        + "WHERE RoleID = 3";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null)
                {
                    while (table.next())
                    {
                        int s_accid = table.getInt("AccountID");
                        String s_lastName = table.getString("LastName");
                        String s_firstName = table.getString("FirstName");
                        String s_phone = table.getString("Phone");
                        String s_gmail = table.getString("Gmail");
                        String s_password = table.getString("Password");
                        String s_sex = table.getString("sex");
                        String s_status = (table.getBoolean("status")) ? "1" : "0";
                        String s_policystatus = (table.getBoolean("policystatus")) ? "1" : "0";
                        int s_roleID = table.getInt("RoleID");
                        Role role = getRoleByID(s_roleID);
                        String s_script = table.getString("Script");
                        list.add(new Account(s_accid, s_lastName, s_firstName, s_phone, s_gmail, s_password, s_sex, s_status, s_policystatus, role, s_script));
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

        return list;
    }

    //POST UPDATE
    public int updateAccountInfo(Account acc) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql
                        = "UPDATE [dbo].[Account]\n"
                        + "SET \n"
                        + "[LastName]=?,\n"
                        + "[FirstName]=?,\n"
                        + "[Phone]=?,\n"
                        + "[Gmail]=?,\n"
                        + "[Password]=?,\n"
                        + "[status]=?,\n"
                        + "[PolicyStatus]=?,\n"
                        + "[RoleID]=(SELECT [dbo].[Role].[RoleID] FROM [dbo].[Role]\n"
                        + "                Where [dbo].[Role].RoleName =?  ),\n"
                        + "[Script]=?\n"
                        + "WHERE [AccountID] = ?";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, acc.getLastName());
                pst.setString(2, acc.getFirstName());
                pst.setString(3, acc.getPhone());
                pst.setString(4, acc.getGmail());
                pst.setString(5, acc.getPassword());
                pst.setString(6, acc.getStatus());
                pst.setString(7, acc.getPolicyStatus());
                pst.setString(8, acc.getRole().getRoleName());
                pst.setString(9, acc.getScript());
                pst.setInt(10, acc.getAccountID());

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

    public int AddAccount(Account acc) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql
                        = "INSERT INTO [dbo].[Account] ([LastName],[FirstName],[Phone],[Gmail],[Password],[sex], [status],[PolicyStatus],[RoleID],[Script])\n"
                        + "VALUES ("
                        + "?" //1
                        + ",?" //2
                        + ",?"//3
                        + ",?"//4
                        + ",?"//5
                        + ",?"//sex
                        + ",?"//6
                        + ",?"//7
                        + ",(SELECT [dbo].[Role].[RoleID] FROM [dbo].[Role]\n"
                        + "Where [dbo].[Role].RoleName = ? )" //8
                        + ",?)"; //9

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, acc.getLastName());
                pst.setString(2, acc.getFirstName());
                pst.setString(3, acc.getPhone());
                pst.setString(4, acc.getGmail());
                pst.setString(5, acc.getPassword());
                pst.setString(6, acc.getSex());
                pst.setString(7, acc.getStatus());
                pst.setString(8, acc.getPolicyStatus());
                pst.setString(9, acc.getRole().getRoleName());
                pst.setString(10, acc.getScript());

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

    public int blockAccount(String id) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql
                        = "UPDATE [dbo].[Account]\n"
                        + "SET [dbo].[Account].[status]= ~[dbo].[Account].[status]\n"
                        + "WHERE [dbo].[Account].[AccountID]= ?";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, id);

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

    public int blockAccount_Policy(String id) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql
                        = "UPDATE [dbo].[Account]\n"
                        + "SET [dbo].[Account].[PolicyStatus]= ~[dbo].[Account].[PolicyStatus]\n"
                        + "WHERE [dbo].[Account].[AccountID]= ?";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, id);

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

}
