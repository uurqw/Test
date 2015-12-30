/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnaCGW.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class MathUtil {
	private static DecimalFormat doubleFormatter = new DecimalFormat("##0.00");

    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }

    public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
        if (d1 == null) {
            d1 = BigDecimal.valueOf(0);
        }
        if (d2 == null) {
            d2 = BigDecimal.valueOf(0);
        }

        return d1.add(d2);
    }

    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();
    }

    public static BigDecimal sub(BigDecimal d1, BigDecimal d2) {
        if (d1 == null) {
            d1 = BigDecimal.valueOf(0);
        }

        if (d2 == null) {
            d2 = BigDecimal.valueOf(0);
        }

        return d1.subtract(d2);
    }

    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();
    }

    public static BigDecimal mul(BigDecimal d1, BigDecimal d2) {
        if (d1 == null) {
            d1 = BigDecimal.valueOf(0);
        }

        if (d2 == null) {
            d2 = BigDecimal.valueOf(0);
        }

        return d1.multiply(d2);
    }

    public static double div(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal div(BigDecimal d1, BigDecimal d2) {
        if (d1 == null) {
            d1 = BigDecimal.valueOf(0);
        }

        if (d2 == null) {
            d2 = BigDecimal.valueOf(0);
        }

        return d1.divide(d2, 2, BigDecimal.ROUND_HALF_UP);
    }

    //2012.9.4 xmq
    public static BigDecimal div(BigDecimal d1, BigDecimal d2,int scale) {
        if (d1 == null) {
            d1 = BigDecimal.valueOf(0);
        }

        if (d2 == null) {
            d2 = BigDecimal.valueOf(0);
        }

        return d1.divide(d2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static double round(double d1, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal one = new BigDecimal("1");
        return b1.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal round(BigDecimal d1, int scale) {
        BigDecimal one = new BigDecimal("1");
        return d1.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * ���㽻��Ӷ�� 0.0,  0.0,0.005|, 5, 180.0, 0.0
     * @param contract
     * @param orderAmt
     * @return
     * @throws NumberFormatException
     */
    public static double getCommisionFee(Double leastCommision, String commisionRate, String commisionType, Double orderAmt, Double adjustAmt) throws NumberFormatException {
        double commisionFee = 0.0d, backCommisionFee = 0.0d;
        if (commisionType.equals("CommisionType.2105") && !Strings.isNullOrEmpty(commisionRate)) { //���ʷֶ��շ�
            String[] liFee = commisionRate.trim().split("\\|");
            for (int i = 0; i < liFee.length; i++) {
                if (Strings.isNullOrEmpty(liFee[i])) {
                    continue;
                }

                String[] _liFee = liFee[i].split("\\,");
                String amount = _liFee[0].trim();
                String ti = _liFee[1].trim();
                String type = "1"; //1:�������, 2:�̶�Ӷ��
                if (_liFee.length > 2) {
                    type = _liFee[2].trim();
                }
                if (orderAmt > Double.parseDouble(amount)) {
                    if (type.equals("1")) {
                        commisionFee = round(mul(orderAmt, Double.parseDouble(ti)), 2);
                    } else {
                        commisionFee = Double.parseDouble(ti);
                    }

                    if (adjustAmt != null) {
                        if (type.equals("1")) {
                            backCommisionFee = round(mul(adjustAmt, Double.parseDouble(ti)), 2);
                        } else {
                            backCommisionFee = Double.parseDouble(ti);
                        }
                    }
                } else {
                    break;
                }
            }
            if (commisionFee < leastCommision) {
                if (adjustAmt != null && !orderAmt.toString().equals(adjustAmt.toString())) {
                    return 0.0f;
                } else {
                    return round(leastCommision, 2);
                }
            } else if (adjustAmt != null) {
                return backCommisionFee;
            } else {
                return commisionFee;
            }
        } else { //�̶�����
            if (adjustAmt != null && orderAmt.toString().equals(adjustAmt.toString())) {
                return round(leastCommision, 2);
            } else {
                return round(leastCommision, 2);
            }
        }
    }
    
    /**
     * ���㽻��Ӷ��
     * @param leastCommision
     * @param commisionRate
     * @param commisionType
     * @param orderAmt
     * @param adjustAmt
     * @param digit
     * @return
     * @throws NumberFormatException
     */
    public static double getCommisionFee(Double leastCommision, String commisionRate, String commisionType, Double orderAmt, Double adjustAmt, int digit) throws NumberFormatException {
        double commisionFee = 0.0d, backCommisionFee = 0.0d;
        if (commisionType.equals("CommisionType.2105") && !Strings.isNullOrEmpty(commisionRate)) { //���ʷֶ��շ�
            String[] liFee = commisionRate.trim().split("\\|");
            for (int i = 0; i < liFee.length; i++) {
                if (Strings.isNullOrEmpty(liFee[i])) {
                    continue;
                }

                String[] _liFee = liFee[i].split("\\,");
                String amount = _liFee[0].trim();
                String ti = _liFee[1].trim();
                String type = "1"; //1:�������, 2:�̶�Ӷ��
                if (_liFee.length > 2) {
                    type = _liFee[2].trim();
                }
                if (orderAmt > Double.parseDouble(amount)) {
                    if (type.equals("1")) {
                        commisionFee = round(mul(orderAmt, Double.parseDouble(ti)), digit);
                    } else {
                        commisionFee = Double.parseDouble(ti);
                    }

                    if (adjustAmt != null) {
                        if (type.equals("1")) {
                            backCommisionFee = round(mul(adjustAmt, Double.parseDouble(ti)), digit);
                        } else {
                            backCommisionFee = Double.parseDouble(ti);
                        }
                    }
                } else {
                    break;
                }
            }
            if (commisionFee < leastCommision) {
                if (adjustAmt != null && !orderAmt.toString().equals(adjustAmt.toString())) {
                    return 0.0f;
                } else {
                    return round(leastCommision, digit);
                }
            } else if (adjustAmt != null) {
                return backCommisionFee;
            } else {
                return commisionFee;
            }
        } else { //�̶�����
            if (adjustAmt != null && orderAmt.toString().equals(adjustAmt.toString())) {
                return round(leastCommision, digit);
            } else {
                return round(leastCommision, digit);
            }
        }
    }

    /**
     * ���㽻�׵绰��
     * @param contract
     * @param orderAmt
     * @return
     * @throws NumberFormatException
     */
    public static double getTelExpense(Double amt, Double adjustAmt, String telExpenseRate) throws NumberFormatException {
        //�绰��=���׽��*�绰���ʣ���
        double tellExpense = 0.0d;
        if ((adjustAmt == null || amt.toString().equals(adjustAmt.toString())) && !Strings.isNullOrEmpty(telExpenseRate)) {
            String[] liFee = telExpenseRate.trim().split("\\|");
            for (int i = 0; i < liFee.length; i++) {
                String[] _liFee = liFee[i].split("\\,");
                String amount = _liFee[0].trim();
                String ti = _liFee[1].trim();
                if (amt >= Double.parseDouble(amount)) {
                    tellExpense = round(Double.parseDouble(ti), 2);  //(double) (Math.round((float) (Double.parseDouble(ti) * 100))) / 100;
                } else {
                    break;
                }
            }
        }
        return tellExpense;
    }
    
    /**
     * ���㽻�׵绰��
     * @return
     * @throws NumberFormatException
     */
    public static double getTelExpense(Double amt, Double adjustAmt, String telExpenseRate, int digit) throws NumberFormatException {
        //�绰��=���׽��*�绰���ʣ���
        double tellExpense = 0.0d;
        if ((adjustAmt == null || amt.toString().equals(adjustAmt.toString())) && !Strings.isNullOrEmpty(telExpenseRate)) {
            String[] liFee = telExpenseRate.trim().split("\\|");
            for (int i = 0; i < liFee.length; i++) {
                String[] _liFee = liFee[i].split("\\,");
                String amount = _liFee[0].trim();
                String ti = _liFee[1].trim();
                if (amt >= Double.parseDouble(amount)) {
                    tellExpense = round(Double.parseDouble(ti), digit);
                } else {
                    break;
                }
            }
        }
        return tellExpense;
    }
    
    
	//yangbo@2013-09-16 ƥ�����ֺ͸�����
	public static boolean findNuminRegex(String s) {
		if (s == null || s.equals("")) {
			return false;
		}
		Pattern p = Pattern
				.compile("^(([0-9]*\\.[0-9]{1,2})|([0-9]*))$");
		Matcher m = p.matcher(s);
		return m.matches();
	}
    
    public static BigDecimal toDecimal(String amt) {
        if (Strings.isNullOrEmpty(amt)) {
            amt = "0";
        }
        return BigDecimal.valueOf(Double.valueOf(amt));
    }

    public static BigDecimal toDecimal(float amt) {
        return BigDecimal.valueOf(Double.valueOf(String.valueOf(amt)));
    }

    public static BigDecimal toDecimal(double amt) {
        return BigDecimal.valueOf(amt);
    }
    
    //2012.05.31 caihongzhi 
    public static double roundup(double d1, int scale){
    	BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal one = new BigDecimal("1");
        return b1.divide(one, scale, BigDecimal.ROUND_UP).doubleValue();
    }
    
    //2012.05.25 xmq ���ת����ҹ�ʽ����������
    public static double to_cny(double amt,double rate) {
        return round(mul(amt, rate), 2);
    }
    /**
     * ��ʽ��DoubleֵΪ��λС��
     * @param amount
     * @return
     */
    public static String format(double amount){
    	return doubleFormatter.format(amount);
    }
    
    /**
     * ��ʽ��DoubleֵΪ��λС��
     * @param amount
     * @return
     */
    public static String formateDouble(double amount){
    	return doubleFormatter.format(amount);
    }

	private static DecimalFormat doubleFormatter4 = new DecimalFormat("##0.0000");
    public static String formateDouble4(double amount){
    	return doubleFormatter4.format(amount);
    }
    
    public static void main(String[] args) {
    	System.out.println(MathUtil.round(MathUtil.mul(new BigDecimal(0.8188), new BigDecimal(100)),2));
    	System.out.println(div(new BigDecimal(81.88), new BigDecimal(100),4));
    }
}
