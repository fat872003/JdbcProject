/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Employee;
import DTO.Major;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import mylibs.DBUtils;
import mylibs.UtilsFunc;

/**
 *
 * @author ACER
 */
public class EmployeeDAO {

    public Employee getEmployeeByID(int id) {
        Employee rs = null;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [AccountID],[DayOfBirth],[Identify_ID],[Working_Day],[Salary],[MajorID] FROM [dbo].[Employee]\n"
                        + "WHERE [AccountID]=? ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null)
                {
                    while (table.next())
                    {
                        int AccountID = table.getInt("AccountID");
                        Date dayOfBirth = new UtilsFunc().convertStringToBirthDate(table.getString("DayOfBirth"));
                        String identify_ID = table.getString("Identify_ID");
                        Date workingDay = new UtilsFunc().convertStringToBirthDate(table.getString("Working_Day"));
                        Double salary = table.getDouble("Salary");

                        int majorID = table.getInt("MajorID");
                        Major major = new MajorDAO().getMajorByID(majorID);

                        rs = new Employee(AccountID, dayOfBirth, identify_ID, workingDay, salary, major);
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

    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> list = new ArrayList<>();
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [AccountID],[DayOfBirth],[Identify_ID],[Working_Day],[Salary],[MajorID] FROM [dbo].[Employee]";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null)
                {
                    while (table.next())
                    {
                        int AccountID = table.getInt("AccountID");
                        Date dayOfBirth = new UtilsFunc().convertStringToBirthDate(table.getString("DayOfBirth"));
                        String identify_ID = table.getString("Identify_ID");
                        Date workingDay = new UtilsFunc().convertStringToBirthDate(table.getString("Working_Day"));
                        Double salary = table.getDouble("Salary");

                        int majorID = table.getInt("MajorID");
                        Major major = new MajorDAO().getMajorByID(majorID);

                        list.add(new Employee(AccountID, dayOfBirth, identify_ID, workingDay, salary, major));
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
}
