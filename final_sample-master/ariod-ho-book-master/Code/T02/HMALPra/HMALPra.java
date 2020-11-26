import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class HMALPra {
	public static void main(String[] args) {

        ArrayList aryList = new ArrayList();

        int iddn, nadn, phdn;
        String[] IDD = { "D043", "G001", "I041"};
        String[] NAD = { "Kan", "Haoye", "Cheng"};
        String[] PHD = { "9487", "3838", "8787"};
        
        iddn = IDD.length;
        nadn = NAD.length;
        phdn = PHD.length;

        if (iddn == nadn && iddn == phdn && nadn == phdn){
            for(int i = 0; i < iddn; i++){

                HashMap hmap = new HashMap();
                hmap.put("ID", IDD[i]);
                hmap.put("Name", NAD[i]);
                hmap.put("Phone", PHD[i]);
                aryList.add(hmap);
            }
            for (int j = 0; j < iddn; j++) {
                System.out.println(aryList.get(j));
            }
        } else {
            System.out.println("OwO //");
        }
	}
}