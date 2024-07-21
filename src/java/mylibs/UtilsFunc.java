/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylibs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class UtilsFunc {

    //Int: mang, so luong item moi trang // out: [ [ ], [ ]  ]
    public ArrayList<ArrayList> pagination(ArrayList arr, int itemPerPage) {
        ArrayList<ArrayList> result = new ArrayList<>();

        //flag
        int track = 0;
        ArrayList index = new ArrayList();

        for (int i = 0; i < arr.size(); i++)
        {
            index.add(arr.get(i));
            track++;

            if (track == itemPerPage)
            {
                result.add(index);
                index = new ArrayList();
                track=0;
            }
        }

        result.add(index);

        return result;
    }
    
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
    
    public java.util.Date convertStringToBirthDate(String dateString) throws ParseException {
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
}
