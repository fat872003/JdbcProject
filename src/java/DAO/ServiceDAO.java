/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import mylibs.DBUtils;

/**
 *
 * @author ACER
 */
public class ServiceDAO {

    //dtb function
    public Service getServiceByID(int id) {
        Service rs = null;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "select [SerID], [SerName], [Status] ,[Price] from [dbo].[Service]\n"
                        + "WHERE [SerID] = ?  ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null)
                {
                    while (table.next())
                    {
                        int serID = table.getInt("SerID");
                        String name = table.getString("SerName");
                        String status = (table.getBoolean("Status")) ? "1" : "0";
                        int price = table.getInt("Price");
                        rs = new Service(id, name, status, price);
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

    //==============
    public ArrayList<Service> getAllService() {
        ArrayList<Service> rs = new ArrayList<Service>();
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "select [SerID], [SerName], [Status] ,[Price] from [dbo].[Service]";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null)
                {
                    while (table.next())
                    {
                        int id = table.getInt("SerID");
                        String name = table.getString("SerName");
                        String status = (table.getBoolean("Status")) ? "1" : "0";
                        int price = table.getInt("Price");
                        Service ser = new Service(id, name, status, price);
                        rs.add(ser);
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
}
