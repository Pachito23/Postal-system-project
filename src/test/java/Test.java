import com.sun.jdi.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Random rand = new Random(); //instance of random class
        int upperbound = 168;
        int lowerbound=5;
        //generate random values from 5-24
        long int_random = 0;
        for (int i=1;i<=10;i++) {
            int_random = rand.nextInt(lowerbound, upperbound);
            System.out.println(int_random);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        now=now.plusHours(int_random);
        System.out.println(dtf.format(now));
    }
}
