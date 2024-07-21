/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Product;
import DTO.ProductCategories;
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
public class ProductDAO {

    //Dtb function
    public ProductCategories getCateByID(int id) {
        ProductCategories prdC = null;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [cate_ID],[Name],[icon],[status] FROM [dbo].[Category]\n"
                        + "WHERE [cate_ID] = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null)
                {
                    while (table.next())
                    {
                        int cateid = table.getInt("cate_ID");
                        String cateName = table.getString("Name");
                        String cateIcon = table.getString("icon");
                        String status = (table.getBoolean("status")) ? "1" : "0";
                        prdC = new ProductCategories(cateid, cateName, cateIcon, status);
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
        return prdC;
    }

    //======
    //Lấy toàn bộ category bên product
    public ArrayList<ProductCategories> getAllCategories() {
        ArrayList<ProductCategories> list = new ArrayList<>();

        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [cate_ID],[Name],[icon],[status] FROM [dbo].[Category]";

                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);

                if (table != null)
                {
                    while (table.next())
                    {
                        int cate_ID = table.getInt("cate_ID");
                        String name = table.getString("Name");
                        String icon = table.getString("Icon");
                        String status = (table.getBoolean("status")) ? "1" : "0";
                        list.add(new ProductCategories(cate_ID, name, icon, status));
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

    //GET CATE NAME FOR RENDER PRODUCT ( PRODUCT CHI CO CATEID)
    public String getCatenameByID(int id) {
        String name = null;

        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [Name] FROM [dbo].[Category]\n"
                        + "WHERE [cate_ID] = ?";

                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null)
                {
                    table.next();
                    name = table.getString("Name");

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

        return name;
    }

//    RENDER PRODUCT BY CATE 
    public ArrayList<Product> getAllProductByCateID(String cate_ID) {
        ArrayList<Product> list = new ArrayList<>();

        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [prd_ID],[name],[thumnail], [description],[price],[speed],[cate_ID], [status] from [dbo].[Product]\n"
                        + "WHERE [cate_ID] like ?";

                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, cate_ID);
                ResultSet table = st.executeQuery();

                if (table != null)
                {
                    while (table.next())
                    {
                        int id = table.getInt("prd_ID");
                        String name = table.getString("name");
                        String thumbnail = table.getString("thumnail");
                        String description = table.getString("description");
                        int price = table.getInt("price");
                        int speed = table.getInt("speed");
                        //get Cate
                        int cateid = table.getInt("cate_ID");
                        ProductCategories prdC = getCateByID(cateid);
                        //End GetCate
                        String status = table.getBoolean("status") ? "1" : "0";
                        Product prd = new Product(id, name, thumbnail, description, price, speed, prdC, status);
                        list.add(prd);
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

    //for RENDER
    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> list = new ArrayList<>();

        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [prd_ID],[name],[thumnail],[description],[price],[speed],[cate_ID],[status] FROM [dbo].[Product]";

                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);

                if (table != null)
                {
                    while (table.next())
                    {
                        int prd_ID = table.getInt("prd_ID");
                        String name = table.getString("name");
                        String thumbnail = table.getString("thumnail");
                        String description = table.getString("description");
                        int price = table.getInt("price");
                        int speed = table.getInt("speed");
                        //get Cate
                        int cateid = table.getInt("cate_ID");
                        ProductCategories prdC = getCateByID(cateid);
                        //End GetCate
                        String status = table.getBoolean("status") ? "1" : "0";
                        list.add(new Product(prd_ID, name, thumbnail, description, price, speed, prdC, status));
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

    //for SEARCH
    public ArrayList<Product> getProductByName(String nameParam) {
        ArrayList<Product> list = new ArrayList<>();

        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [prd_ID],[name],[thumnail], [description],[price],[speed],[cate_ID], [status] from [dbo].[Product]\n"
                        + "WHERE [name] like ?";

                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, "%" + nameParam + "%");
                ResultSet table = st.executeQuery();

                if (table != null)
                {
                    while (table.next())
                    {
                        int id = table.getInt("prd_ID");
                        String name = table.getString("name");
                        String thumbnail = table.getString("thumnail");
                        String description = table.getString("description");
                        int price = table.getInt("price");
                        int speed = table.getInt("speed");
                        //get Cate
                        int cateid = table.getInt("cate_ID");
                        ProductCategories prdC = getCateByID(cateid);
                        //End GetCate
                        String status = table.getBoolean("status") ? "1" : "0";
                        Product prd = new Product(id, name, thumbnail, description, price, speed, prdC, status);
                        list.add(prd);
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

    // tra ve 1 Obj
    public Product getProductByID(String idParam) {
        Product list = new Product();

        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {
                String sql = "SELECT [prd_ID],[name],[thumnail], [description],[price],[speed],[cate_ID], [status] from [dbo].[Product]\n"
                        + "WHERE [prd_ID] like ?";

                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, idParam);
                ResultSet table = st.executeQuery();

                if (table != null)
                {
                    while (table.next())
                    {
                        int id = table.getInt("prd_ID");
                        String name = table.getString("name");
                        String thumbnail = table.getString("thumnail");
                        String description = table.getString("description");
                        int price = table.getInt("price");
                        int speed = table.getInt("speed");
                        //get Cate
                        int cateid = table.getInt("cate_ID");
                        ProductCategories prdC = getCateByID(cateid);
                        //End GetCate
                        String status = table.getBoolean("status") ? "1" : "0";
                        list = new Product(id, name, thumbnail, description, price, speed, prdC, status);
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

    //POST UPDATE:
    public int updateProductInfo(Product prd) {
        int result = 0;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {

                String sql
                        = "UPDATE [dbo].[Product]\n"
                        + "SET [name] = ?,\n"
                        + "[thumnail]= ?,\n"
                        + "[description] = ?,\n"
                        + "[price]=?,\n"
                        + "[speed]=?,\n"
                        + "[cate_ID]=?\n"
                        + "WHERE [prd_ID] = ?";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, prd.getName());
                pst.setString(2, prd.getThumbnail());
                pst.setString(3, prd.getDescription());
                pst.setInt(4, prd.getPrice());
                pst.setInt(5, prd.getSpeed());
                pst.setInt(6, prd.getCategory().getCate_ID());
                pst.setInt(7, prd.getPrd_ID());

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

    public int addProduct(Product prd) {
        int result = 0;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {

                String sql
                        = "INSERT INTO Product ([name],[thumnail],[description],[price],[speed],[cate_ID],[status])\n"
                        + "VALUES (?,?,?,?,?,?,?)";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, prd.getName());
                pst.setString(2, prd.getThumbnail());
                pst.setString(3, prd.getDescription());
                pst.setInt(4, prd.getPrice());
                pst.setInt(5, prd.getSpeed());
                pst.setInt(6, prd.getCategory().getCate_ID());
                pst.setString(7, prd.getStatus());

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

    public int blockProduct(String id) {
        int result = 0;
        Connection cn = null;
        try
        {
            cn = DBUtils.makeConnection();
            if (cn != null)
            {

                String sql
                        = "UPDATE [dbo].[Product]\n"
                        + "SET [dbo].[Product].[status]= ~[dbo].[Product].[status]\n"
                        + "WHERE [dbo].[Product].[prd_ID]= ?";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, id);

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
}
