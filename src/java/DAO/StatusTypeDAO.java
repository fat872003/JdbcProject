/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.StatusType;
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
public class StatusTypeDAO {

    public StatusType getStatusTypeByID(int id) {
        StatusType rs = null;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [StatusID], [StatusName] FROM [dbo].[StatusType]\n"
                        + "WHERE [StatusID] = ? ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null)
                {
                    while (table.next())
                    {
                        int serID = table.getInt("StatusID");
                        String name = table.getString("StatusName");
                        rs = new StatusType(serID, name);
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
    
    public ArrayList getAllStatusType(){
          ArrayList<StatusType> rs = new ArrayList();
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [StatusID],[StatusName] FROM [dbo].[StatusType]";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null)
                {
                    while (table.next())
                    {
                        int statusID = table.getInt("StatusID");
                        String name = table.getString("StatusName");
                        rs.add(new StatusType(statusID, name));
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
