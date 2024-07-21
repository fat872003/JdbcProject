/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Contact;
import DTO.RequestType;
import DTO.Service;
import DTO.Transaction;
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
public class RequestTypeDAO {

    //dtb function
    public RequestType getRequestTypeByID(int id) {
        RequestType rs = null;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [reqTypeID],[ReqTypeName]  FROM [dbo].[RequestType] \n"
                        + "WHERE [reqTypeID] = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null)
                {
                    while (table.next())
                    {
                        int reqTypeID = table.getInt("reqTypeID");
                        String rqTypename = table.getString("ReqTypeName");
                        rs = new RequestType(reqTypeID, rqTypename);
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

    //end dtb===============
    public ArrayList<RequestType> getAllRequestType() {
        ArrayList<RequestType> rs = new ArrayList<RequestType>();
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [reqTypeID],[ReqTypeName]  FROM [dbo].[RequestType]";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null)
                {
                    while (table.next())
                    {
                        int reqTypeID = table.getInt("reqTypeID");
                        String rqTypename = table.getString("ReqTypeName");
                        rs.add( new RequestType(reqTypeID, rqTypename) );
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
