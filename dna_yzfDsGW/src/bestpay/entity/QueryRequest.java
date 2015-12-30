package bestpay.entity;

public class QueryRequest {
	private String reqSeq;     //请求流水号
	private String custCode;   //发起客户号
	private String platCode;   //平台号
	private String reqIp;      //请求方IP地址
	private String extOrderSeq;//外部订单号
	private String originalSeq;//原请求流水号
	private String trsSeq;     //原交易流水号
	private String currentPage;//当前页
	private String pageSize;   //每页显示的条数
	
	
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
	public String getReqIp() {
		return reqIp;
	}
	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}
	public String getExtOrderSeq() {
		return extOrderSeq;
	}
	public void setExtOrderSeq(String extOrderSeq) {
		this.extOrderSeq = extOrderSeq;
	}
	public String getOriginalSeq() {
		return originalSeq;
	}
	public void setOriginalSeq(String originalSeq) {
		this.originalSeq = originalSeq;
	}
	public String getTrsSeq() {
		return trsSeq;
	}
	public void setTrsSeq(String trsSeq) {
		this.trsSeq = trsSeq;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
}
