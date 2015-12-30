package util;

import java.util.HashMap;

import dnaCGW.common.Strings;

public class CommonUtil {
	private static HashMap<String, String> respCode = new HashMap<String, String>();
	private static HashMap<String, String> bankNo = new HashMap<String, String>();
	private static HashMap<String, String> cardType = new HashMap<String, String>();
	private static HashMap<String, String> certType = new HashMap<String, String>();
	private static HashMap<String, String> currencyCode = new HashMap<String, String>();
	static{
		/*
		 * "code":"010007","msg":"请求报文验签失败",验签失败
		 * "code":"010008","msg":"请求客户校验失败",客户名错
		 * 
		 */
		
		/*返回码*/
		respCode.put("0011000", "0006");respCode.put("011007", "0009");
		respCode.put("010001", "");respCode.put("010002", "U030");
		respCode.put("010003", "");respCode.put("010004", "");
		respCode.put("010005", "0094");respCode.put("010007", "U082");
		respCode.put("010008", "");respCode.put("010010", "");
		respCode.put("010011", "");respCode.put("010012", "");respCode.put("010013", "");
		respCode.put("010014", "");respCode.put("010015", "");respCode.put("010016", "");
		respCode.put("010017", "");respCode.put("010018", "");respCode.put("010019", "");
		respCode.put("010020", "");respCode.put("010021", "");respCode.put("010022", "");
		respCode.put("010023", "");respCode.put("010024", "");respCode.put("019999", "");
		/*bankNo*/
		bankNo.put("中国人民银行", "860000");bankNo.put("邮政储蓄", "866000");bankNo.put("中国银行", "866100");
		bankNo.put("工商银行", "866200");bankNo.put("农业银行", "866300");bankNo.put("建设银行", "866500");
		bankNo.put("广发银行", "866800");bankNo.put("交通银行", "866400");bankNo.put("民生银行", "866600");
		bankNo.put("招商银行", "866900");bankNo.put("广州银行", "867000");bankNo.put("浦发银行", "867100");
		bankNo.put("光大银行", "867200");bankNo.put("中信银行", "867400");bankNo.put("兴业银行", "867600");
		bankNo.put("广州农商银行", "866700");bankNo.put("北京银行", "865900");bankNo.put("//东莞农信", "867319");
		bankNo.put("华夏银行", "865800");bankNo.put("深发展", "865600");bankNo.put("平安银行", "865700");
		bankNo.put("//东莞农商", "867800");bankNo.put("东莞银行", "867900");bankNo.put("广东省农信社", "867300");
		/*账户类型*/
		cardType.put("0", "4");cardType.put("2", "1");
		cardType.put("3", "2");cardType.put("5", "1");cardType.put("6", "2");
		/*证件类型*/
		certType.put("01", "00");certType.put("02", "01");
		certType.put("03", "02");certType.put("04", "99");
		certType.put("05", "99");certType.put("06", "06");certType.put("99", "99");
		/*币种类型*/
		currencyCode.put("156", "RMB");currencyCode.put("840", "USD");currencyCode.put("344", "HKD");
	}
	
	public static String transRespCode(String IDType){
		if(!Strings.isNullOrEmpty(IDType)){
			if(!Strings.isNullOrEmpty(respCode.get(IDType))){
				return respCode.get(IDType);
			}
		}
		return "T000";
	}
	
	public static String transCertType(String IDType){
		if(!Strings.isNullOrEmpty(IDType)){
			return certType.get(IDType);
		}
		return "99";
	}
	
	public static String getCardType(String IDType){
		return cardType.get(IDType);
	}
	
	public static String getBankCode(String bankName){
		return bankNo.get(bankName);
	}
	
	public static String getCurrencyCode(String IDType){
		return currencyCode.get(IDType);
	}
}
