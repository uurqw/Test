/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnaCGW.common;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * @author Administrator
 */
public class Strings {
	private static Logger logger = Logger.getLogger(Strings.class);
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
		if (!new Strings().isInteger(cardNo)) {
			return false;
		}
		int len = cardNo.length();
		int weight = 1;
		int total = 0;
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				if (len % 2 == 0) {
					weight = 2;
				}
			} else {
				if (i % 2 == 0) {
					weight = 2;
				} else {
					weight = 1;
				}
			}
			int tmp = cardNo.charAt(i) - '0';
			tmp = tmp * weight;
			if (tmp > 9) {
				tmp = tmp - 9;
			}
			total += tmp;
		}
		if (total % 10 == 0) {
			return true;
		}
		return false;
	}
	/**
	 * �����ַ��λ, �ַ��Ȳ���,�򷵻��ַ�ȫ��.
	 * 
	 * @param str
	 * @param num
	 * @return
	 */
	public static String getLast(String str, int num) {
		if (str == null || str.length() <= num) {
			return str;
		} else {
			return str.substring(str.length() - num);
		}
	}

	public static String formatAccountNo(String pan) {
		if (Strings.isNullOrEmpty(pan)) {
			return "";
		}
		try {
			String accType = pan.substring(0, 2);
			String value = pan.substring(2);
			String[] acc = pan.substring(2).split("\\|");
			String result = accType;
			if (accType.equals("14") || accType.equals("21")) {
				if (!Strings.isNullOrEmpty(acc[0]) && acc[0].length() >= 7) {
					result = result + acc[0].substring(0, 7) + "****";
				} else {
					result = result + value;
				}
				if (acc.length > 1 && !Strings.isNullOrEmpty(acc[1]) && acc[1].length() >= 4) {
					result = result + "|" + acc[1].substring(0, 4) + "*******" + acc[1].substring(acc[1].length() - 4);
				} else {
					result = result + value;
				}
			} else if (accType.equals("01")) {
				if (!Strings.isNullOrEmpty(acc[0]) && acc[0].length() >= 4) {
					result = result + acc[0].substring(0, 4) + "****" + acc[0].substring(acc[0].length() - 4);
				} else {
					result = result + value;
				}
			} else if (accType.equals("04")) {
				if (!Strings.isNullOrEmpty(acc[0]) && acc[0].length() >= 7) {
					result = result + acc[0].substring(0, 7) + "*******";
				} else {
					result = result + value;
				}
			} else if (accType.equals("05")) {
				if (!Strings.isNullOrEmpty(acc[0]) && acc[0].length() >= 4) {
					result = result + acc[0].substring(0, 4) + "****" + acc[0].substring(acc[0].length() - 4);
				} else {
					result = result + value;
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return pan;
		}
	}

	public static String formatPassword(String pan, int showStartLen, int showEndLen) {
		if (pan == null) {
			return "";
		}
		String temp = "";
		for (int i = 0; i < pan.length(); i++) {
			if (i < showStartLen || i >= pan.length() - showEndLen) {
				temp = temp + pan.charAt(i);
			} else {
				temp = temp + "*";
			}
		}
		return temp;
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNullOrEmpty(Object str) {
		return str == null || str.toString().equals("");
	}

	public static String toXmlString(Object o, String append) {
		StringBuilder sb = new StringBuilder(append);
		sb.append("<" + o.getClass().getName() + ">" + append);
		Object[] agrs = new Object[] {};
		for (Method m : o.getClass().getDeclaredMethods()) {
			if (!m.getReturnType().getName().startsWith("dna") && (!m.getReturnType().getName().startsWith("java.util") 
					|| m.getReturnType().getName().startsWith("java.util.Date"))&& m.getDeclaringClass().getName().equals(o.getClass().getName())
					&& m.getName().startsWith("get")) {
				Object obj = null;
				try {
					obj = m.invoke(o, agrs);
				} catch (IllegalArgumentException e1) {
					logger.info(e1.getMessage());
				} catch (IllegalAccessException e1) {
					logger.info(e1.getMessage());
				} catch (InvocationTargetException e1) {
					logger.info(e1.getMessage());
				}
				if (Strings.isNullOrEmpty(obj)) {
					continue;
				}
				sb.append("  <" + m.getName().substring(3) + ">");
				try {
					// 2011.12.20 xmq ���ο���
					String tmp = m.getName().substring(3);
					if ("account".equalsIgnoreCase(tmp)
							|| "accountno".equalsIgnoreCase(tmp)
							|| "accountaes".equalsIgnoreCase(tmp)
							|| "accountnoaes".equalsIgnoreCase(tmp)) {
						// Object obj = m.invoke(o, agrs);
						if (!Strings.isNullOrEmpty(obj)) {
							String s_obj = Strings.formatAccount(obj.toString());
							sb.append(s_obj);
						} else {
							sb.append("");
						}
					} else {
						// Object obj = m.invoke(o, agrs);
						sb.append(obj == null ? "" : obj);
					}
				} catch (Exception e) {
					sb.append(toXmlString(e, ""));
				}
				sb.append("</" + m.getName().substring(3) + ">" + append);
			}
		}
		sb.append("</" + o.getClass().getName() + ">" + append);
		return sb.toString();
	}

	public static String getStackTrace(Throwable e) {
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

	/*
	 * public static String toString(Date o) { if (o == null) { return ""; }
	 * return DateFormatter.yyyy_MM_dd_HH_mm_ss(o); }
	 */
	public static String toHalfIp(String ip) {
		String[] ips = ip.split("\\.");
		if (ips.length > 3) {
			return ips[0] + "_*_*_" + ips[3];
		} else {
			return ip.replace(".", "_");
		}
	}

	public static String toString(Object o) {
		if (o == null) {
			return "";
		}
		return o.toString().trim();
	}

	public static String toXmlString(Object o) {
		if (o == null) {
			return "<object></object>";
		}
		try {
			return toXmlString(o, "\n");
		} catch (Exception e) {
			return "<" + e.getClass().getName() + ">" + e.getMessage() + "</" + e.getClass().getName() + ">";
		}
	}

	public static String format(String str) {
		if (str == null) {
			return "";
		} else {
			return str.trim();
		}
	}

	public static String format(String str, int beginSize, int endSize,String leftFill, String rightFill, boolean cutLeft) throws Exception {
		while (beginSize > 0) {
			str = leftFill + str;
			beginSize--;
		}
		if (str.getBytes("gbk").length > endSize) {
			byte[] temp = str.getBytes();
			byte[] newbyte = new byte[endSize];
			if (cutLeft) {
				for (int i = newbyte.length - 1, j = temp.length - 1; i >= 0; i--, j--) {
					newbyte[i] = temp[j];
				}
			} else {
				for (int i = 0; i < newbyte.length; i++) {
					newbyte[i] = temp[i];
				}
			}
			str = new String(newbyte);
		}
		while (str.getBytes("gbk").length < endSize) {
			str += rightFill;
		}
		return str;
	}

	public static String padLeft(String input, char c, int length) {
		String output = input;
		while (output.length() < length) {
			output = c + output;
		}
		return output;
	}

	public static String toString(Throwable e) {
		if (e == null) {
			return "";
		}
		String exStr = e.getMessage();
		for (StackTraceElement ste : e.getStackTrace()) {
			exStr = exStr + "\n" + ste.toString();
		}
		return exStr;

	}

	public static String formatAmtToStr12(String amt) {
		try {
			if (amt.indexOf(".") > 0) {
				String temp2 = amt.substring(amt.indexOf(".") + 1);
				if (temp2.length() > 2) {
					throw new RuntimeException("���С���λ���ܴ���2");
				} else if (temp2.length() == 1) {
					amt += "0";
				}
			} else {
				amt += "00";
			}
			amt = amt.replace(".", "");
			amt = Strings.format(amt, 12 - amt.length(), 12, "0", "0", false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return amt;
	}

	public static String padRight(String input, char c, int length) {
		String output = input;
		while (output.length() < length) {
			output = output + c;
		}
		return output;
	}

	/**
	 * �Ҳ��ո�
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String padRight(String input, int length) {
		return padRight(input, ' ', length);
	}

	/**
	 * ��0
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String padLeft(String input, int length) {
		return padLeft(input, '0', length);
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

	public static String bytePadRight(String input, String charset, char c, int length) {
		String output = input;
		try {
			while (output.getBytes(charset).length < length) {
				output = output + c;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

	public static String trim(String str) {
		if (Strings.isNullOrEmpty(str)) {
			return "";
		} else {
			return str.trim();
		}
	}

	public static String trim(Object obj) {
		if (Strings.isNullOrEmpty(obj)) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}

	public static String trimNull(Object o) {
		if (Strings.isNullOrEmpty(o)) {
			return "";
		} else {
			return o.toString();
		}
	}

	// Ԫ���1.00->100
	public static String formatAmt(double amt, int size) {
		String tmp = formatAmt(amt);
		return Strings.bytePadLeft(tmp, '0', size);
	}

	// Ԫ���1.00->100
	public static String formatAmt(double amt) {
		// 2011.09.19 xmq edit amt*100����ɾ�������:10.20*100=1019.999...���ִ˺���������
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
		// 2011.12.29 xmq 0.01Ԫ -> 1��
		while (amount.startsWith("0")) {
			amount = amount.substring(1);
		}
		return amount;
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

	// 2011.12.16 xmq ���ο���
	public static String formatAccount(String str) {
		try {
			if (Strings.isNullOrEmpty(str)) {
				return "";
			}
			if (str.length() < 8) {
				return "####";
			}
			return str.substring(0, 4) + "########"
					+ str.substring(str.length() - 4);
		} catch (Exception e) {
			return "####";
		}
	}

	// 2011.12.16 xmq ���ο���
	public static String formatStr(String str, String flag) {
		try {
			int i = str.indexOf(flag);
			if (i >= 0) {
				str = str.substring(0, i + flag.length() + 5) + "######"
						+ str.substring(i + flag.length() + 11);
			}
			// 2011.12.16 xmq ����cvn����Ч�ڡ�����
			i = str.indexOf("cvn");
			if (i >= 0) {
				str = str.substring(0, i + 4) + "###" + str.substring(i + 7);
			}
			i = str.indexOf("expire");
			if (i >= 0) {
				str = str.substring(0, i + 7) + "####" + str.substring(i + 11);
			}
			i = str.indexOf("password");
			if (i >= 0) {
				str = str.substring(0, i + 9) + "######"
						+ str.substring(i + 15);
			}
		} catch (Exception e) {
			return str;
		}
		return str;
	}

	public static String random(int len) {
		String str = "";
		java.util.Random rander = new java.util.Random(
				System.currentTimeMillis());
		for (int i = 0; i < len; i++) {
			str += HEXCHAR[rander.nextInt(16)];
		}
		return str;
	}

	private static char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}
