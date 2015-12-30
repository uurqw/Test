package bestpay.entity;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class QueryResponse {
	private String totalCount;  //总条数
	private String totalPage;   //总页数
	private List details;       //列表详细
	
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
}
