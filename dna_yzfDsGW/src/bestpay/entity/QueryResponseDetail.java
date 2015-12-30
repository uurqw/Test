package bestpay.entity;

public class QueryResponseDetail {
	private String trsSeq;      //交易流水号
	private String platCode;    //平台号
	private String extOrderSeq; //外部系统订单号
	private String reqSeq;      //请求流水号
	private String custCode;    //发起客户号
	private String custAreaCode;//客户区域编码
	private String extUserId;   //外部用户标识
	private String trsType;     //交易类型
	private String trsDesc;     //交易类型描述
	private String trsCode;     //交易编码
	private String reqTime;     //请求时间
	private String tradeTime;   //开始时间
	private String updateTime;  //更新时间
	private String endTime;     //结束时间
	private String settleStat;  //结算状态
	private String settleTime;  //结算时间
	private String relevanceSeq;//结算时间
	private String originalSeq; //原订单号
	private String channelCode; //渠道号
	private String appName;     //应用系统名
	private String payType;     //支付方式
	private String trsMemo;     //交易备注
	private String trsSummary;  //交易摘要
	private String stat;        //状态
	private String originAmount;//原始金额
	private String actualAmount;//实际交易金额
	private String fee;         //服务费用
	private String currencyCode;//币种
	private String bizCode;     //业务类型编码
	private String bizDesc;     //业务类型描述
	private String trsRespCode; //交易响应码
	private String trsRespMes;  //交易响应码描述
	
	
	public String getTrsSeq() {
		return trsSeq;
	}
	public void setTrsSeq(String trsSeq) {
		this.trsSeq = trsSeq;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getExtOrderSeq() {
		return extOrderSeq;
	}
	public void setExtOrderSeq(String extOrderSeq) {
		this.extOrderSeq = extOrderSeq;
	}
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
	public String getCustAreaCode() {
		return custAreaCode;
	}
	public void setCustAreaCode(String custAreaCode) {
		this.custAreaCode = custAreaCode;
	}
	public String getExtUserId() {
		return extUserId;
	}
	public void setExtUserId(String extUserId) {
		this.extUserId = extUserId;
	}
	public String getTrsType() {
		return trsType;
	}
	public void setTrsType(String trsType) {
		this.trsType = trsType;
	}
	public String getTrsDesc() {
		return trsDesc;
	}
	public void setTrsDesc(String trsDesc) {
		this.trsDesc = trsDesc;
	}
	public String getTrsCode() {
		return trsCode;
	}
	public void setTrsCode(String trsCode) {
		this.trsCode = trsCode;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSettleStat() {
		return settleStat;
	}
	public void setSettleStat(String settleStat) {
		this.settleStat = settleStat;
	}
	public String getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(String settleTime) {
		this.settleTime = settleTime;
	}
	public String getRelevanceSeq() {
		return relevanceSeq;
	}
	public void setRelevanceSeq(String relevanceSeq) {
		this.relevanceSeq = relevanceSeq;
	}
	public String getOriginalSeq() {
		return originalSeq;
	}
	public void setOriginalSeq(String originalSeq) {
		this.originalSeq = originalSeq;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTrsMemo() {
		return trsMemo;
	}
	public void setTrsMemo(String trsMemo) {
		this.trsMemo = trsMemo;
	}
	public String getTrsSummary() {
		return trsSummary;
	}
	public void setTrsSummary(String trsSummary) {
		this.trsSummary = trsSummary;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getOriginAmount() {
		return originAmount;
	}
	public void setOriginAmount(String originAmount) {
		this.originAmount = originAmount;
	}
	public String getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getBizCode() {
		return bizCode;
	}
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	public String getBizDesc() {
		return bizDesc;
	}
	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}
	public String getTrsRespCode() {
		return trsRespCode;
	}
	public void setTrsRespCode(String trsRespCode) {
		this.trsRespCode = trsRespCode;
	}
	public String getTrsRespMes() {
		return trsRespMes;
	}
	public void setTrsRespMes(String trsRespMes) {
		this.trsRespMes = trsRespMes;
	}
	
}
