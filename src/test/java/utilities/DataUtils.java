package utilities;

import java.util.Random;

public class DataUtils {

    /**
     * This method will generate random emails.
     * Ex.
     *     .getRandomEmail(); -> return "abc4362537@gmail.com"
     *
     */
    public static String getRandomEmail(){
        //abc4362537@gmail.com
        String email = "abc";
        Random random = new Random();
        int number = random.nextInt(1000);
        return email+number+"@gmail.com"; // "abc4362537@gmail.com"


    }
}
