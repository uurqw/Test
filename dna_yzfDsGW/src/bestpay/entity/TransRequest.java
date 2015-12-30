package bestpay.entity;

public class TransRequest {
	private String reqSeq;           //请求流水号
	private String custCode;         //发起客户号
	private String platCode;         //平台号
	private String requestTime;      //请求时间
	private String reqIp;            //请求方IP地址
	private String trsSummary;       //交易摘要
	private String trsMemo;          //交易备注
	private String projectCode;      //项目号
	private String extUserId;        //外部用户标识
	private String externalId;       //外部订单号
	private String currencyCode;     //币种编码
	private String transactionAmount;//交易金额
	private String accountCode;      //转出方账户编码
	private String accountName;      //转出方账户名
	private String payeeBankAccount; //转入银行账户信息
	
	
	public String getReqSeq() {
		return reqSeq;
	}
	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getReqIp() {
		return reqIp;
	}
	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}
	public String getTrsSummary() {
		return trsSummary;
	}
	public void setTrsSummary(String trsSummary) {
		this.trsSummary = trsSummary;
	}
	public String getTrsMemo() {
		return trsMemo;
	}
	public void setTrsMemo(String trsMemo) {
		this.trsMemo = trsMemo;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getExtUserId() {
		return extUserId;
	}
	public void setExtUserId(String extUserId) {
		this.extUserId = extUserId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPayeeBankAccount() {
		return payeeBankAccount;
	}
	public void setPayeeBankAccount(String payeeBankAccount) {
		this.payeeBankAccount = payeeBankAccount;
	}
	
	
}
