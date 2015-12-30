package dnaCGW.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DNAPAY Ltd,
 * Date: 2005-5-25
 * Time: 15:54:10
 *
 * @author XieminQuan
 */
public class DateFormatter {

	/**
	 * caihongzhi@2012-03-05 V2.21.36 R257 ���operationlog, transactionlog, ivrlog����db�Ĳ�ѯ
	 * ��ȡ��������
	 * @param now
	 * @param formater
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(Date now, String formater) throws ParseException{
    	return DateFormatter.strToDate(formater, DateFormatter.dateToStr(formater, now));
	}
	
	/**
	 * caihongzhi@2012-03-05 V2.21.36 R257 ���operationlog, transactionlog, ivrlog����db�Ĳ�ѯ
	 * ��ȡ��������
	 * @param now
	 * @param formater
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(Object now, String formater) throws Exception{
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = df.parse(now.toString());
	     return getDate(date, formater);
	}
	
    public static String MMddHHmmss(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("MMddHHmmss").format(time);
    }

    public static String HH(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("HH").format(time);
    }

    public static String HHmmss(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("HHmmss").format(time);
    }

    public static String yyMM(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("yyMM").format(time);
    }

    public static String MMdd(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("MMdd").format(time);
    }

    public static String yyyy_MM_dd(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    public static String yyMMdd(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("yyMMdd").format(time);
    }

    public static String yy(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("yy").format(time);
    }

    public static String yyyy_MM_dd_HH_mm_ss(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    public static String yyyyMMdd(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("yyyyMMdd").format(time);
    }

    public static String yyyyMMdd(Date time, String MMdd) {
        if (time == null || MMdd == null) {
            return "";
        }

        int MM = Integer.parseInt(MMdd.substring(0, 2));
        int dd = Integer.parseInt(MMdd.substring(2));
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        while (cal.get(Calendar.DAY_OF_MONTH) != dd || cal.get(Calendar.MONTH) + 1 != MM) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }

    public static String yyyyMMdd(String MMdd) {
        if (MMdd == null) {
            return "";
        }

        int MM = Integer.parseInt(MMdd.substring(0, 2));
        int dd = Integer.parseInt(MMdd.substring(2));
        Calendar cal = Calendar.getInstance();
        while (cal.get(Calendar.DAY_OF_MONTH) != dd || cal.get(Calendar.MONTH) + 1 != MM) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }

    public static String china_yyyy_MM_dd_HH_mm_ss(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��").format(time);
    }

    public static String china_yyyy_MM_dd(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy��MM��dd��").format(time);
    }

    public static String HH_mm_ss_SSS(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("HH:mm:ss.SSS").format(time);
    }

    public static String HH_mm_ss(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("HH:mm:ss").format(time);
    }

    public static String yyyyMMddHHmmss(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("yyyyMMddHHmmss").format(time);
    }
    
    public static String yyyyMM(Date time) {
        if (time == null) {
            return "";
        }
        return new SimpleDateFormat("yyyyMM").format(time);
    }

    public static boolean isLastWeekDay(Calendar cal) {
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFirstWeekDay(Calendar cal) {
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLastMonthDay(Calendar cal) {
        Calendar calTmp = Calendar.getInstance();
        calTmp.setTime(cal.getTime());
        calTmp.add(Calendar.DAY_OF_MONTH, 1);
        if (calTmp.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFirstMonthDay(Calendar cal) {
        if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static Calendar getLastWeekDay(Calendar cal) {
        Calendar calTmp = Calendar.getInstance();
        calTmp.setTime(cal.getTime());
        while (!isLastWeekDay(calTmp)) {
            calTmp.add(Calendar.DAY_OF_WEEK, 1);
        }
        return calTmp;
    }

    public static Calendar getFirstWeekDay(Calendar cal) {
        Calendar calTmp = Calendar.getInstance();
        calTmp.setTime(cal.getTime());
        while (!isFirstWeekDay(calTmp)) {
            calTmp.add(Calendar.DAY_OF_WEEK, -1);
        }
        return calTmp;
    }

    public static Calendar getLastMonthDay(Calendar cal) {
        Calendar calTmp = Calendar.getInstance();
        calTmp.setTime(cal.getTime());
        while (!isLastMonthDay(calTmp)) {
            calTmp.add(Calendar.DAY_OF_MONTH, 1);
        }
        return calTmp;
    }

    public static Calendar getFirstMonthDay(Calendar cal) {
        Calendar calTmp = Calendar.getInstance();
        calTmp.setTime(cal.getTime());
        while (!isFirstMonthDay(calTmp)) {
            calTmp.add(Calendar.DAY_OF_MONTH, -1);
        }
        return calTmp;
    }
    //ʱ��ת�ַ�
    public static String dateToStr(String pattern, Date time){
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	return sdf.format(time);
    }
   //�ַ�תʱ��
    public static Date strToDate(String pattern, String time) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	return sdf.parse(time);
    }
    //����ʱ���������
    public static long betweenDays(String start, String end, String pattern) throws ParseException{
    	long day = 0;
    	if(Strings.isNullOrEmpty(start) || Strings.isNullOrEmpty(end)){
    		day = -1;
    	} else {
    		try{
	    		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		    	day = (sdf.parse(end).getTime()-sdf.parse(start).getTime())/(1000*60*60*24);
    		} catch(ParseException e){
    			day = -1;
    		}
    	}
    	return day;
    }
    /**
     * lyq@2013-09-18 ���ʱ�������MMDD������������
     * @param time
     * @param MMDD
     * @return
     */
    public static String getSettlementDate(Date time, String MMDD){
    	String settleDate = "";
		Calendar orderTime = Calendar.getInstance();
		orderTime.setTime(time);
		Calendar nextdate = Calendar.getInstance();
		nextdate.setTime(time);
		nextdate.add(Calendar.DAY_OF_MONTH, 1);
		if(!Strings.isNullOrEmpty(MMDD)){
			if((nextdate.get(Calendar.YEAR) > orderTime.get(Calendar.YEAR))
					&& (!DateFormatter.MMdd(orderTime.getTime()).equals(MMDD))){
				settleDate = nextdate.get(Calendar.YEAR) + "" + MMDD;
			} else {
				settleDate = orderTime.get(Calendar.YEAR) + "" + MMDD;
			}
		} else {
			if(orderTime.get(Calendar.HOUR_OF_DAY) == 23 
					&& (orderTime.get(Calendar.MINUTE) > 0
							|| orderTime.get(Calendar.SECOND) > 0
							|| orderTime.get(Calendar.MILLISECOND) > 0)){
				orderTime.add(Calendar.DAY_OF_MONTH, 1);
			}
			settleDate = DateFormatter.yyyyMMdd(orderTime.getTime());
		}
		return settleDate;
    }
}
