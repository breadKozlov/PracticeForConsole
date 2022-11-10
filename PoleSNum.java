package patnashki;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PoleSNum {

    public static int[][] pole = new int[4][4];

    public static void main(String[] args) {

        for (int i = 0; i < pole.length; i++){

            for(int j = 0; j < pole[i].length; j++) {

            boolean sovp = false;
            int num = 0;

            while (!sovp) {
                num = new Random().nextInt(1,17);
                sovp = new PoleSNum().proverkaNaSovp(num);
            }

            pole[i][j] = num;
            System.out.print(pole[i][j] + " ");
        }
            System.out.println();
        }

    }


    public boolean proverkaNaSovp(int ob) {

        boolean a = false;
        int o = 0;

        for(int[] init: pole) {
            for (int in : init) {
            if(in == ob) {
                o++;
            }
        }
        }
        if (o == 0) {
            a = true;
        }
        return a;
        }
    }
