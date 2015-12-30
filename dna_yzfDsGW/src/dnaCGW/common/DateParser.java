package dnaCGW.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author: XieminQuan
 * @time  : 2008-1-3 ����02:56:50
 *
 * DNAPAY
 */
public class DateParser {

	//private static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	//private static SimpleDateFormat yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//private static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	//private static SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	//private static SimpleDateFormat hh_mm_ss = new SimpleDateFormat("hh:mm:ss");

    public static Date yyyyMMdd(String date) throws ParseException {
        if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        return yyyyMMdd.parse(date);
    }

    public static Date yyyy_MM_dd_HH_mm_ss(String date) throws ParseException {
         if (Strings.isNullOrEmpty(date)) {
            return null;
        }
         SimpleDateFormat yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return yyyy_MM_dd_HH_mm_ss.parse(date);
    }

    public static Date yyyyMMddHHmmss(String date) throws ParseException {
         if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        return yyyyMMddHHmmss.parse(date);
    }

    public static Date MMddHHmmss(String date) throws ParseException {
         if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        return yyyyMMddHHmmss(Calendar.getInstance().get(Calendar.YEAR) + date);
    }

    public static Date yyyy_MM_dd(String date) throws ParseException {
         if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
        return yyyy_MM_dd.parse(date);
    }

    public static Date hh_mm_ss(String date) throws ParseException {
         if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        SimpleDateFormat hh_mm_ss = new SimpleDateFormat("hh:mm:ss");
        return hh_mm_ss.parse(date);
    }
    
    public static Date HH_mm_ss(String date) throws ParseException {
        if (Strings.isNullOrEmpty(date)) {
           return null;
       }
       SimpleDateFormat HH_mm_ss = new SimpleDateFormat("HH:mm:ss");
       return HH_mm_ss.parse(date);
   }
    
    public static Date EEEMMM(String date) throws ParseException {
        if (Strings.isNullOrEmpty(date)) {
           return null;
       }
        SimpleDateFormat eeemmm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
       return eeemmm.parse(date);
   }
    //lyq@2013-01-17
    public static Date MMdd(Date transTime, String MMdd) throws ParseException{
    	Date date = null;
    	String settleDate = "";
    	Calendar orderTime = Calendar.getInstance();
		orderTime.setTime(transTime);
		Calendar nextdate = Calendar.getInstance();
		nextdate.setTime(transTime);
		nextdate.add(Calendar.DAY_OF_MONTH, 1);
		if((nextdate.get(Calendar.YEAR) > orderTime.get(Calendar.YEAR))
				&& (!DateFormatter.MMdd(orderTime.getTime()).equals(MMdd))){
			settleDate = nextdate.get(Calendar.YEAR) + "" + MMdd;
		} else {
			settleDate = orderTime.get(Calendar.YEAR) + "" + MMdd;
		}
		date = yyyyMMdd(settleDate);
		return date;
    }
}
