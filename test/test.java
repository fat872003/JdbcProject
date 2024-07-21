
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ACER
 */
public class test {

    public static ArrayList<ArrayList> pagination(ArrayList arr, int itemPerPage) {
        ArrayList<ArrayList> result = new ArrayList<>();

        //flag
        int track = 0;
        ArrayList index = new ArrayList();

        for (int i = 0; i < arr.size(); i++)
        {
            index.add(arr.get(i));
            track++;

            
            if(track == itemPerPage){
                result.add(index);
                index = new ArrayList();
                track = 0;
            }
        }

        result.add(index);

        return result;
    }

    public static void main(String[] args) {
    
        System.out.println("DATE: "+  new Date() );
        int a=2;
        int b=3;

        
    }

}
