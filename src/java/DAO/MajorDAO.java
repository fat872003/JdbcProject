/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Major;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import mylibs.DBUtils;
import mylibs.UtilsFunc;

/**
 *
 * @author ACER
 */
public class MajorDAO {

    public Major getMajorByID(int id) {
        Major rs = null;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [MajorID],[MajorName] FROM [dbo].[Major]\n"
                        + "WHERE [MajorID]= ? ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null)
                {
                    while (table.next())
                    {
                        int MajorID = table.getInt("MajorID");
                        String MajorName = table.getString("MajorName");

                        rs = new Major(MajorID, MajorName);
                        
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
