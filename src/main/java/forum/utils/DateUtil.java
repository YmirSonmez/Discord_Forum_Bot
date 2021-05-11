package forum.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class DateUtil {
    public static final SimpleDateFormat SDF= new SimpleDateFormat("d MMMM kk:mm",new Locale("tr"));
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd MMMM yyyy",new Locale("tr"));



    public static Calendar getDate(String stringDate){
        String[] split = stringDate.split("");
        Calendar date = Calendar.getInstance();
        for(int i=0;i+1<=(split.length-1);i+=2){
            int add= Integer.parseInt(split[i+1]);
            switch (split[i]){
                case "g":
                    date.add(Calendar.DAY_OF_YEAR,add);
                    break;
                case "s":
                    date.add(Calendar.HOUR,add);
                    break;
                case "d":
                    date.add(Calendar.MINUTE,add);
                    break;
            }
        }
        return date;
    }


}
