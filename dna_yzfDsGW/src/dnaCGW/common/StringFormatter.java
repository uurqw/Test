package dnaCGW.common;

import java.text.DecimalFormat;

public class StringFormatter {

    public static String format(String str,int beginSize,int endSize,String leftFill,String rightFill) throws Exception{

        return format(str,beginSize,endSize,leftFill,rightFill,false);
    }
    public static String format(String str,int beginSize,int endSize,String leftFill,String rightFill,boolean cutLeft) throws Exception{
    	
        while(beginSize >0) {
            str = leftFill + str;
            beginSize--;
        }
        if(str.getBytes("gbk").length > endSize) {
        	
        	byte[] temp = str.getBytes();
        	byte[] newbyte = new byte[endSize];
        	if(cutLeft) {
            	for(int i = newbyte.length-1,j = temp.length-1;i >= 0;i--,j--) {
            		newbyte[i] = temp[j];
            	}
        	} else {
            	for(int i = 0;i < newbyte.length;i++) {
            		newbyte[i] = temp[i];
            	}
        	}
        	str = new String(newbyte);
        }

        while(str.getBytes("gbk").length < endSize) {
            str += rightFill;
        }
        return str;
    }

    public static String format(String str,int endSize) {
    	try {
        	return format(str,0,endSize," "," ");
    	} catch(Exception e) {
    		return "��ʽ���ַ�����쳣:" + e;
    	}
    }
    
    public static String getStackTrace(Throwable e) {
        StringBuffer stack  = new StringBuffer();
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

    public static String padLeft(String input, char c, int length) {
        String output = input;
        while (output.length() < length) {
            output = c + output;
        }
        return output;
    }

    public static String padRight(String input, char c, int length) {
        String output = input;
        while (output.length() < length) {
            output = output + c;
        }
        return output;
    }

    public static String padRight(String input, int length) {
        return padRight(input, ' ', length);
    }

    public static String padLeft(String input, int length) {
        return padLeft(input, ' ', length);
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

    public static String formatNumber(double in, int sf) {

        DecimalFormat formater = new DecimalFormat();
        String pattern = "#########";

        if (sf > 0) {
            pattern = pattern + ".#";
        }

        for (int i = 1; i < sf; i++) {
            pattern = pattern + "#";
        }

        formater.applyPattern(pattern);

        String tmp = formater.format(in);

        if (sf > 0) {
            if (tmp.indexOf(".") == -1) {
                tmp = tmp + ".";
            }

            int zeros = (sf + 1) - (tmp.length() - tmp.indexOf("."));

            for (int i = 0; i < zeros; i++) {
                tmp += "0";
            }
        }

        return tmp;
    }
    
    private static boolean checkByte(char a) {

    	if(a >= 'A' && a <= 'Z') return true;
    	if(a >= '0' && a <= '9') return true;
    	if(a == ' ' || a == ',' || a == '.') return true;
    	
    	return false;
    }
    /**
     * ����ѡ���MAC������Ӧ��һ�����ַ��?��ȥһЩ������Ϣ�������MAC�����������?�����£�
     * (1) �������֮�����һ���ո� 
     * (2) ���е�Сд��ĸת���ɴ�д��ĸ��
     * (3) ������ĸ(A-Z)������(0-9)���ո񣬶���(��)�͵��(.)������ַ�ɾȥ��
     * (4) ɾȥ������Ĵ�ͷ�ո�ͽ�β�ո�
     * (5) ����һ��������ո���һ���ո����
     * @param temp
     * @return
     * @throws Exception
     */
    public static String getMacBlock(String temp) throws Exception{
    	
    	temp = temp.toUpperCase();
    	
    	StringBuffer get = new StringBuffer();
    	for(int i = 0;i < temp.length();i++) {
    		
    		if(checkByte(temp.charAt(i))) get.append(temp.charAt(i));
    	}
    	temp = get.toString();
    	
    	while(temp.startsWith(" ")) temp = temp.substring(1);
    	temp = temp.trim();
    	
    	int i = -1;
    	while((i = temp.indexOf("  ")) >= 0) {
    		temp = temp.substring(0,i) + temp.substring(i+1);
    	}
    	
    	return temp;
    }
    
    /**
     * ����:100.01�����:һ��Ԫ��һ��
     */
    public static String toCNAmount(String s) 
    { 
        if (s == null || s.equals("")) 
            return ""; 
        if (s.startsWith("��") || s.startsWith("$")) 
            s = s.substring(1); 

        String s1 = "" + s; 
        String s2 = "Y"; 
        StringBuffer stringbuffer = new StringBuffer(); 
        int i = 0; 
        boolean flag = true; 
        i = 0; 
        do 
        { 
            if (i >= s1.length()) 
                break; 
            if ((s1.charAt(i) < '0' || s1.charAt(i) > '9') && s1.charAt(i) != '.' && s1.charAt(i) != '-') 
            { 
                flag = false; 
                break; 
            } 
            if (!s1.substring(i, i + 1).equals("0") && !s1.substring(i, i + 1).equals("-") && !s1.substring(i, i + 1).equals(".") && !s1.substring(i, i + 1).equals(" ")) 
                break; 
            i++; 
        } while (true); 
        if (!flag) 
            return s; 
        if (i == s1.length()) 
            if (s2.equalsIgnoreCase("N")) 
                return ""; 
            else 
                return "��Ԫ"; 
        i = s1.indexOf("-"); 
        if (i > -1) 
        { 
            stringbuffer.append("��"); 
            s1 = s1.substring(i + 1); 
        } 
        String s3 = s1; 
        String s4 = "00"; 
        i = s1.indexOf("."); 
        if (i > -1) 
        { 
            s3 = s1.substring(0, i); 
            s4 = s1.substring(i + 1); 
            if (s4.length() == 0) 
                s4 = "00"; 
            if (s4.length() == 1) 
                s4 = s4 + "0"; 
        } 
        boolean flag1 = false; 
        if (s3.startsWith("0") && s3.endsWith("0")) 
        { 
            flag1 = true; 
        } else 
        { 
            String s6 = "N"; 
            for (int j = 0; j < s3.length(); j++) 
            { 
                String s7 = s3.substring(j, j + 1); 
                int k = s3.length() - j - 1; 
                if (s7.equals("0") && k != 0 && k != 4 && k != 8) 
                { 
                    s6 = "Y"; 
                    continue; 
                } 
                if (s7.equals("0")) 
                { 
                    s6 = "N"; 
                    String p1=stringbuffer.substring(stringbuffer.length()-1,stringbuffer.length()); 
                    String p2=addUnit(k); 
                    if(!(p1.equals("��") && p2.equals("��")) ){ 
                      stringbuffer.append(addUnit(k)); 
                    } 
                    continue; 
                } 
                if (s6.equals("Y")) 
                { 
                    stringbuffer.append(dToU("0")); 
                    s6 = "N"; 
                } 
                stringbuffer.append(dToU(s7)); 
                stringbuffer.append(addUnit(k)); 
            } 

        } 
        long l = Long.parseLong(s4); 
        for (int i1 = s4.length() - 2; i1 > 0; i1--) 
        { 
            long l1 = l % 10L; 
            l /= 10L; 
            if (l1 >= 5L) 
                l++; 
        } 

        if (l == 0L) 
        { 
            stringbuffer.append(""); 
        } else 
        { 
            String s5 = String.valueOf(l); 
            if (s5.length() == 1) 
                s5 = "0" + s5; 
            if (s5.startsWith("0") && !s5.endsWith("0")) 
            { 
                if (!flag1) 
                    stringbuffer.append("��"); 
            } else 
            { 
                stringbuffer.append(dToU(s5.substring(0, 1))); 
                stringbuffer.append("��"); 
            } 
            String s8 = s5.substring(1, 2); 
            if (!s8.equals("0")) 
            { 
                stringbuffer.append(dToU(s5.substring(1, 2))); 
                stringbuffer.append("��"); 
            } else 
            { 
                stringbuffer.append(""); 
            } 
        } 
        return stringbuffer.toString(); 
    } 

    private static String dToU(String s) 
    { 
        String s1 = "��һ�����������߰˾�"; 
        int i = Integer.parseInt(s); 
        return s1.substring(i, i + 1); 
    } 

    private static String addUnit(int i) 
    { 
        String s = "Ԫʮ��ǧ��ʮ��ǧ��ʮ��ǧ��"; 
        return s.substring(i, i + 1); 
    } 

    public static String formatAmt(String amt) {
    	
    	try {
    		
    		if(amt.indexOf(".") > 0) {
    			String temp2 = amt.substring(amt.indexOf(".")+1);
    			if(temp2.length() > 2) {
    				throw new RuntimeException("���С���λ���ܴ���2");
    			} else if(temp2.length() == 1) {
    				amt += "0";
    			}
    		} else amt += "00";
        	amt = amt.replace(".","");
        	amt = format(amt,12-amt.length(),12,"0","0");
    	} catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    	return amt;
    }
}
