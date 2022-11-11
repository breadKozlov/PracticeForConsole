import java.util.ArrayList;
import java.util.Scanner;

public class Checker {

    public static void main(String[] args) {

        run();
    }

    public static void run() {

        int[] arrayNumOfTickets = {2135,3899457,12366312,12365,654123,6323,11,9874,36985214,32145023};
        for (int i: arrayNumOfTickets) {
            System.out.println(checkTickets(i));
        }
    }

    public static boolean checkTickets(int numOfTicket) {

        ArrayList<Integer> arrayList = new ArrayList<>();
        int sum1 = 0;
        int sum2 = 0;
        int num = numOfTicket;

        while (num > 9) {

            int rem = num % 10;
            arrayList.add(rem);
            num = (num - num % 10)/10;
        }
        arrayList.add(num);

        if(arrayList.size() % 2 == 0) {
            for (int i = 0; i < (arrayList.size())/2; i++) {
                sum1 += arrayList.get(i);
            }
            for (int i = (arrayList.size())/2; i < arrayList.size(); i++) {
                sum2 += arrayList.get(i);
            }
        } else {
            System.out.print("Your ticket number is odd! - ");
        }
        return sum1 == sum2 && sum1 != 0;
    }
}
