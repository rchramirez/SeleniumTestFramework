package utils;

import java.util.List;
import java.util.Random;

public class Helpers {

    public static String getRandomValueFromStringList(List<String> list) {
        Random random = new Random();
        int index = 0;
        if (list.size() > 0) {
            index = random.nextInt(list.size() - 1) + 1;
            return list.get(index);
        }
       return "";
    }

}
