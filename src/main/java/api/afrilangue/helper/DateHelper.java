package api.afrilangue.helper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateHelper {
    static Date convertStrToDate(String strDate){
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
        } catch (ParseException e) {
        }
        return null;
    }

    static String convertDateToStr(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    static boolean reachedEarlyEndDate(Date date) {
        return new Date(System.currentTimeMillis()).compareTo(date) >= 0;
    }


}
