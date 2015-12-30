package bestpay.entity;

public class TransRequestDetail {
	private String bankCode;        //银行编码
	private String cardType;        //银行卡卡类型
	private String accountCode;     //银行卡号
	private String bankCardName;    //银行卡户名
	private String certNo;          //证件号码
	private String certType;        //证件类型
	private String openBankAddress; //开户支行地址
	private String mobile;          //联系方式:移动号码
	private String areaCode;        //银行区域码
	private String perEntFlag;      //对公对私标识
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getOpenBankAddress() {
		return openBankAddress;
	}
	public void setOpenBankAddress(String openBankAddress) {
		this.openBankAddress = openBankAddress;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getPerEntFlag() {
		return perEntFlag;
	}
	public void setPerEntFlag(String perEntFlag) {
		this.perEntFlag = perEntFlag;
	}
}
