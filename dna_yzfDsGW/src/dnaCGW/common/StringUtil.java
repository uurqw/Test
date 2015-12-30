package dnaCGW.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author xmq
 */
public class StringUtil {

    public boolean isInteger(String str) {
        int begin = 0;
        if (str == null || str.trim().equals("")) {
            return false;
        }
        str = str.trim();
        if (str.startsWith("+") || str.startsWith("-")) {
            if (str.length() == 1) {
                // "+" "-"
                return false;
            }
            begin = 1;
        }
        for (int i = begin; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isCreditCard(String cardNo) {
    	
    	if(!new StringUtil().isInteger(cardNo))
    		return false;
    	
    	int len = cardNo.length();
    	int weight = 1;
    	int total = 0;
    	
    	for(int i = 0;i < len;i++) {
    		
    		if(i == 0) {
    	    	if(len%2 == 0)
    	    		weight = 2;
    		} else {
    			if(i%2 == 0)
    				weight = 2;
    			else
    				weight = 1;
    		}
    		
    		int tmp = cardNo.charAt(i) - '0';
    		tmp = tmp * weight;
    		if(tmp > 9)
    			tmp = tmp - 9;
    		
    		total += tmp;

    	}
    	
    	if(total%10 == 0) return true;
    	
    	return false;
    }

    public static boolean isNullOrEmpty(Object str) {
        return str == null || str.toString().equals("");
    }

    public static String toXml(Object o) {
        if (o == null) {
            return "<OBJECT></OBJECT>";
        }
        try { 
            return toXml(o, "\n");        
        } catch(Exception e) {
            return "<" + e.getClass().getName()+">" + e.getMessage() +  "</" + e.getClass().getName()+">";
        }
    }

    public static String toXml(Object o, String append) {

		StringBuffer buf = new StringBuffer();
		Class<?> cl = o.getClass();
		String rootName = cl.getSimpleName();
		buf.append("<" + rootName + ">");
		Field[] fields = cl.getDeclaredFields();
		Method[] methods = cl.getDeclaredMethods();
		for(Field fd: fields){
			try {
				String fieldName = fd.getName();
				String fieldGetName = parGetName(fd.getName());
				if (!checkGetMet(methods, fieldGetName)) {
					continue;
				}
				Method fieldGetMet = cl.getMethod(fieldGetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(o, new Object[] {});
				if(null != fieldVal) {
					if(fieldVal instanceof java.util.Collection) {
						buf.append(toMultXml((ArrayList<?>)fieldVal));
					} else {
						buf.append("<"+fieldName+">" + fieldVal + "</"+fieldName+">");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		buf.append("</" + rootName + ">");
		return buf.toString();
    }
	public static String toOneXml(Object obj){
		StringBuffer buf = new StringBuffer();
		Class<?> cl = obj.getClass();
		String rootName = cl.getSimpleName();
		buf.append("<" + rootName + ">");
		Field[] fields = cl.getDeclaredFields();
		Method[] methods = cl.getDeclaredMethods();
		for(Field fd: fields) {
			try {
				String fieldName = fd.getName();
				String fieldGetName = parGetName(fd.getName());
				if (!checkGetMet(methods, fieldGetName)) {
					continue;
				}
				Method fieldGetMet = cl.getMethod(fieldGetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(obj, new Object[] {});
				fieldVal = fieldVal==null?"":fieldVal;
				buf.append("<"+fieldName+">" + fieldVal + "</"+fieldName+">");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		buf.append("</" + rootName + ">");
		return buf.toString();
	}
	public static String toMultXml(ArrayList<?> list){
		StringBuffer buf = new StringBuffer();
		buf.append("<TRANS_DETAILS>");
		for(Object obj: list){
			buf.append(toOneXml(obj));
		}
		buf.append("</TRANS_DETAILS>");
		return buf.toString();
	}
	private static String parGetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
    private static boolean checkGetMet(Method[] methods, String fieldGetMet) {
		for (Method met : methods) {
			if (fieldGetMet.equals(met.getName())) {
				return true;
			}
		}
		return false;
	}
    
    public static String toString(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString().trim();
    }

    public static String padLeft(String input, char c, int length) {
        String output = input;
        if(output.length() > length)
        	output = output.substring(output.length()-length);
        while (output.length() < length) {
            output = c + output;
        }
        return output;
    }

    public static String padRight(String input, char c, int length) {
        String output = input;
        if(output.length() > length)
        	output = output.substring(output.length()-length);
        while (output.length() < length) {
            output = output + c;
        }
        return output;
    }

    public static String toString(Throwable e) {
        StringBuffer stack = new StringBuffer();
        stack.append(e);
        stack.append("\r\n");

        Throwable rootCause = e.getCause();

        while (rootCause != null) {
            stack.append("Root Cause:\r\n");
            stack.append(rootCause);
            stack.append("\r\n");
            stack.append(rootCause.getMessage());
            stack.append("\r\n");
            stack.append("StackTrace:\r\n");
            stack.append(rootCause);
            stack.append("\r\n");
            rootCause = rootCause.getCause();
        }


        for (int i = 0; i < e.getStackTrace().length; i++) {
            stack.append(e.getStackTrace()[i].toString());
            stack.append("\r\n");
        }
        return stack.toString();
    }

    public static String bytePadLeft(String input, char c, int length) {
        String output = input;
        while (output.getBytes().length < length) {
            output = c + output;
        }
        return output;
    }

    public static String bytePadRight(String input, char c, int length) {
        String output = input;
        while (output.getBytes().length < length) {
            output = output + c;
        }
        return output;
    }
    
    public static String trim(Object obj) {
    	if(StringUtil.isNullOrEmpty(obj))
    		return "";
    	else return obj.toString().trim();
    }
    
    public static String getMatching(String target, String pattern) {
		StringBuffer result = new StringBuffer();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(target);
		if (m.find()) {
			result.append(m.group());
		}
		return result.toString();
	}
    
    public static String random(int len) {
        String str = "";
        java.util.Random rander = new java.util.Random(System.currentTimeMillis());
        for (int i = 0; i < len; i++) {
            str += HEXCHAR[rander.nextInt(16)];
        }
        return str.toUpperCase();
    }
    private static char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	
    public static void main(String[] args) {/*
    	System.out.println(formatAmt(12.3,16));
    	System.out.println(formatAmt(12,16));
    	System.out.println(formatAmt(12.34,16));
    	System.out.println(formatAmt(0,16));
    	System.out.println("".getBytes().length);
    	System.out.println("1".getBytes().length);
    	System.out.println("23".getBytes().length);
    	System.out.println("��".getBytes().length);
    	System.out.println(Strings.formatAmt(12.23));*/
    	//System.out.println(Strings.isCreditCard("5309900599078506"));
    	
    	String a = "ksdjfoshgafsdfiu";
    	System.out.println(StringUtil.padLeft(a,' ',30));
    	
    }
	
	public static String getSafe(String str) {
		
		if(StringUtil.isNullOrEmpty(str))
			return "";
		else
			return "***";
	}

    //Ԫ���1.00->100
    public static String formatAmt(String amt) {
        //amt*100����ɾ�������:10.20*100=1019.999...���ִ˺���������
        String temp = String.valueOf(amt);
        if (temp.indexOf(".") > 0) {
            String temp2 = temp.substring(temp.indexOf(".") + 1);
            if (temp2.length() > 2) {
                temp = temp.substring(0, (temp.length() - temp2.length() + 2));
            } else if (temp2.length() == 1) {
                temp += "0";
            }
        } else {
            temp += "00";
        }
        String amount = temp;
        amount = amount.replace(".", "");
        //2011.12.29 xmq 0.01Ԫ -> 1��
        while (amount.startsWith("0")) {
            amount = amount.substring(1);
        }
        return amount;
    }
}
