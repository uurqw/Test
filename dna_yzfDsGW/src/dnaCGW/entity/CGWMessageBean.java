package dnaCGW.entity;

import java.io.UnsupportedEncodingException;

import dnaCGW.common.ToolKit;
import dnaCGW.encrypt.AES;
import dnaCGW.encrypt.MD5;
/** 
 * @author liufeng E-mail:liu.feng@payeco.com 
 * @version ����ʱ�䣺2014-10-11 ����05:29:18 
 * ��˵��   ������ؽӿڱ���bean
 */

public class CGWMessageBean implements Cloneable {
	private String AppPlatformID="";//Ӧ��ƽ̨��
	private String Version="";//�ӿڰ汾��
	private String TranCode="";//���״���
	private String AppPlatformTranTime="";//Ӧ��ƽ̨����ʱ��
	private String AppPlatformSeqNo="";//Ӧ��ƽ̨��ˮ��
	private String ChannelGWID="";//ͨ����ر��
	private String BankName="";//�������
	private String TerminalID="";//�ն˺�
	private String CUPMerID="";//�����̻���
	private String PayEcoMerID="";//�����̻���
	private String PayEcoMerName="";//�����̻����
	private String OrderNo="";//Ӧ��ƽ̨������
	private String RiskLevel="";//���յȼ�
	private String Industry="";//��ҵ
	private String CentralGWTranSeqNo="";//������ؽ�����ˮ��
	private String OriCentralGWTranSeqNo="";//ԭʼ������ؽ�����ˮ��
	private String AccType="";//�˺�����
	private String AccProp="";//�˺����
	private String AccNo="";//�˺�
	private String AccOpenCity="";//����ʡ��
	private String Currency="";//����
	private String PayMode="";//֧��ģʽ
	private String Password="";//����
	private String TranAmount="";//���׽��
	private String Cvn="";//��ȫ��
	private String Expiry="";//��Ч��
	private String IDType="";//֤������
	private String IDNo="";//֤������
	private String AccName="";//������
	private String MobileNo="";//�ֻ��
	private String TranDescription="";//��������
	private String OriTranAmount="";//ԭʼ���׽��
	private String SettlementDate="";//��������
	private String OriSettlementDate="";//ԭʼ��������
	private String OriAppPlatformSeqNo="";//ԭʼӦ��ƽ̨��ˮ��
	private String OriAppPlatformTranTime="";//ԭʼӦ��ƽ̨����ʱ��
	private String ResponseCode="";//������Ӧ��
	private String RetrievalRefNo="";//�����ο���
	private String OriRetrievalRefNo="";//ԭ�����ο���
	private String TimeOutInterval="";//��ʱʱ��
	private String IDCheckRes="";//֤��������֤���
	private String AccNameCheckRes="";//��������֤���
	private String MobileNoCheckRes="";//�ֻ����֤���
	private String PWDCheckRes="";//������֤���
	private String CVNExpCheckRes="";//CVN����Ч����֤���
	private String Remark="";//��ע
	private String oriTranCode="";//ԭ���״���
	/**
	 * ���󷽱�����
	 */
	private String reqReserved=""; //���󷽱�����
	/**
	 * ������
	 */
	private String reserved="";//������
	private String Sign="";//����ǩ��ֵ
	
	public Object clone(){ 
			Object o = null; 
			try{ 
			   o = super.clone(); 
			}catch(CloneNotSupportedException e){ 
			    ToolKit.writeLog("CGWMessageBean","clone exception", e);
		     } 
			return o; 
     }
	/**
	 * 
	 * @return У��mac�ɹ�������true�� ���򷵻�false
	 */
	public boolean VerifyMac(){
		if(Sign.equals(getMacValue()) && !Sign.equals("")){
			return true;
		}else {
			return false;
		}
	}
	/**
	 * ���ر��ĵ�У��ֵ
	 * @return
	 */
	public String getMacValue(){
		String res="";		
		if(AppPlatformID!=null){
			res+=AppPlatformID;
		}
		if(Version!=null){
			res+=Version;
		}
		if(TranCode!=null){
			res+=TranCode;
		}
		if(AppPlatformTranTime!=null){
			res+=AppPlatformTranTime;
		}
		if(AppPlatformSeqNo!=null){
			res+=AppPlatformSeqNo;
		}
		if(ChannelGWID!=null){
			res+=ChannelGWID;
		}
		if(BankName!=null){
			res+=BankName;
		}
		if(TerminalID!=null){
			res+=TerminalID;
		}
		if(CUPMerID!=null){
			res+=CUPMerID;
		}
		if(PayEcoMerID!=null){
			res+=PayEcoMerID;
		}
		if(PayEcoMerName!=null){
			res+=PayEcoMerName;
		}
		if(OrderNo!=null){
			res+=OrderNo;
		}
		if(RiskLevel!=null){
			res+=RiskLevel;
		}
		if(Industry!=null){
			res+=Industry;
		}
		if(CentralGWTranSeqNo!=null){
			res+=CentralGWTranSeqNo;
		}
		if(OriCentralGWTranSeqNo!=null){
			res+=OriCentralGWTranSeqNo;
		}
		if(AccType!=null){
			res+=AccType;
		}
		if(AccProp!=null){
			res+=AccProp;
		}
		if(AccNo!=null){
			res+=AccNo;
		}
		if(AccOpenCity!=null){
			res+=AccOpenCity;
		}
		if(Currency!=null){
			res+=Currency;
		}
		if(PayMode!=null){
			res+=PayMode;
		}
		if(Password!=null){
			res+=Password;
		}
		if(TranAmount!=null){
			res+=TranAmount;
		}
		if(Cvn!=null){
			res+=Cvn;
		}
		if(Expiry!=null){
			res+=Expiry;
		}
		if(IDType!=null){
			res+=IDType;
		}
		if(IDNo!=null){
			res+=IDNo;
		}
		if(AccName!=null){
			res+=AccName;
		}
		if(MobileNo!=null){
			res+=MobileNo;
		}
		if(TranDescription!=null){
			res+=TranDescription;
		}
		if(OriTranAmount!=null){
			res+=OriTranAmount;
		}
		if(SettlementDate!=null){res+=SettlementDate;
		
		}
		if(OriSettlementDate!=null){
			res+=OriSettlementDate;
		}
		if(OriAppPlatformSeqNo!=null){
			res+=OriAppPlatformSeqNo;
		}
		if(OriAppPlatformTranTime!=null){
			res+=OriAppPlatformTranTime;
		}
		if(ResponseCode!=null){
			res+=ResponseCode;
		}
		if(RetrievalRefNo!=null){
			res+=RetrievalRefNo;
		}
		if(OriRetrievalRefNo!=null){
			res+=OriRetrievalRefNo;
		}
		if(TimeOutInterval!=null){
			res+=TimeOutInterval;
		}
		if(IDCheckRes!=null){
			res+=IDCheckRes;
		}
		if(AccNameCheckRes!=null){
			res+=AccNameCheckRes;
		}
		if(MobileNoCheckRes!=null){
			res+=MobileNoCheckRes;
		}
		if(PWDCheckRes!=null){
			res+=PWDCheckRes;
		}
		if(CVNExpCheckRes!=null){
			res+=CVNExpCheckRes;
		}
		if(Remark!=null){
			res+=Remark;
		}
		if(oriTranCode!=null){
			res+=oriTranCode;
		}
		if(reqReserved!=null){
			res+=reqReserved;
		}
		if(reserved!=null){
			res+=reserved;
		}
		
	   // ToolKit.writeLog(this.getClass().getName(), "����MAC������ַ�",res);
	    MD5 m = new MD5(); 
	    String digest=null;
	    try {
			 digest= m.getMD5ofByte(res.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {			
			ToolKit.writeLog("CGWMessageBean", "getMacValue", e);
		}
	    if (digest!=null) {
	    	return AES.encrypt(digest);
		}else{
			return "";
		} 	    
	}
	
	
	
	
	public String getOriTranCode() {
		return oriTranCode;
	}
	public void setOriTranCode(String oriTranCode) {
		this.oriTranCode = oriTranCode;
	}
	public String getReqReserved() {
		return reqReserved;
	}
	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public String getPayEcoMerName() {
		return PayEcoMerName;
	}
	public void setPayEcoMerName(String payEcoMerName) {
		PayEcoMerName = payEcoMerName;
	}
	public String getAppPlatformID() {
		return AppPlatformID;
	}
	public void setAppPlatformID(String appPlatformID) {
		AppPlatformID = appPlatformID;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getTranCode() {
		return TranCode;
	}
	public void setTranCode(String tranCode) {
		TranCode = tranCode;
	}
	public String getAppPlatformTranTime() {
		return AppPlatformTranTime;
	}
	public void setAppPlatformTranTime(String appPlatformTranTime) {
		AppPlatformTranTime = appPlatformTranTime;
	}
	public String getAppPlatformSeqNo() {
		return AppPlatformSeqNo;
	}
	public void setAppPlatformSeqNo(String appPlatformSeqNo) {
		AppPlatformSeqNo = appPlatformSeqNo;
	}
	public String getChannelGWID() {
		return ChannelGWID;
	}
	public void setChannelGWID(String channelGWID) {
		ChannelGWID = channelGWID;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	public String getTerminalID() {
		return TerminalID;
	}
	public void setTerminalID(String terminalID) {
		TerminalID = terminalID;
	}
	public String getCUPMerID() {
		return CUPMerID;
	}
	public void setCUPMerID(String cUPMerID) {
		CUPMerID = cUPMerID;
	}
	public String getPayEcoMerID() {
		return PayEcoMerID;
	}
	public void setPayEcoMerID(String payEcoMerID) {
		PayEcoMerID = payEcoMerID;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public String getRiskLevel() {
		return RiskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		RiskLevel = riskLevel;
	}
	public String getIndustry() {
		return Industry;
	}
	public void setIndustry(String industry) {
		Industry = industry;
	}	
	public String getCentralGWTranSeqNo() {
		return CentralGWTranSeqNo;
	}
	public void setCentralGWTranSeqNo(String centralGWTranSeqNo) {
		CentralGWTranSeqNo = centralGWTranSeqNo;
	}
	public String getOriCentralGWTranSeqNo() {
		return OriCentralGWTranSeqNo;
	}
	public void setOriCentralGWTranSeqNo(String oriCentralGWTranSeqNo) {
		OriCentralGWTranSeqNo = oriCentralGWTranSeqNo;
	}
	public String getAccType() {
		return AccType;
	}
	public void setAccType(String accType) {
		AccType = accType;
	}
	public String getAccProp() {
		return AccProp;
	}
	public void setAccProp(String accProp) {
		AccProp = accProp;
	}
	public String getAccNo() {
		return AccNo;
	}
	public void setAccNo(String accNo) {
		AccNo = accNo;
	}
	public String getAccOpenCity() {
		return AccOpenCity;
	}
	public void setAccOpenCity(String accOpenCity) {
		AccOpenCity = accOpenCity;
	}
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
	public String getPayMode() {
		return PayMode;
	}
	public void setPayMode(String payMode) {
		PayMode = payMode;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getTranAmount() {
		return TranAmount;
	}
	public void setTranAmount(String tranAmount) {
		TranAmount = tranAmount;
	}
	public String getCvn() {
		return Cvn;
	}
	public void setCvn(String cvn) {
		Cvn = cvn;
	}
	public String getExpiry() {
		return Expiry;
	}
	public void setExpiry(String expiry) {
		Expiry = expiry;
	}
	public String getIDType() {
		return IDType;
	}
	public void setIDType(String iDType) {
		IDType = iDType;
	}
	
	public String getIDNo() {
		return IDNo;
	}
	public void setIDNo(String iDNo) {
		IDNo = iDNo;
	}
	public String getAccName() {
		return AccName;
	}
	public void setAccName(String accName) {
		AccName = accName;
	}
	public String getMobileNo() {
		return MobileNo;
	}
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	public String getTranDescription() {
		return TranDescription;
	}
	public void setTranDescription(String tranDescription) {
		TranDescription = tranDescription;
	}
	public String getOriTranAmount() {
		return OriTranAmount;
	}
	public void setOriTranAmount(String oriTranAmount) {
		OriTranAmount = oriTranAmount;
	}
	public String getSettlementDate() {
		return SettlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		SettlementDate = settlementDate;
	}
	public String getOriSettlementDate() {
		return OriSettlementDate;
	}
	public void setOriSettlementDate(String oriSettlementDate) {
		OriSettlementDate = oriSettlementDate;
	}
	public String getOriAppPlatformSeqNo() {
		return OriAppPlatformSeqNo;
	}
	public void setOriAppPlatformSeqNo(String oriAppPlatformSeqNo) {
		OriAppPlatformSeqNo = oriAppPlatformSeqNo;
	}
	public String getOriAppPlatformTranTime() {
		return OriAppPlatformTranTime;
	}
	public void setOriAppPlatformTranTime(String oriAppPlatformTranTime) {
		OriAppPlatformTranTime = oriAppPlatformTranTime;
	}
	public String getResponseCode() {
		return ResponseCode;
	}
	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}
	
	public String getRetrievalRefNo() {
		return RetrievalRefNo;
	}
	public void setRetrievalRefNo(String retrievalRefNo) {
		RetrievalRefNo = retrievalRefNo;
	}
	public String getOriRetrievalRefNo() {
		return OriRetrievalRefNo;
	}
	public void setOriRetrievalRefNo(String oriRetrievalRefNo) {
		OriRetrievalRefNo = oriRetrievalRefNo;
	}
	public String getTimeOutInterval() {
		return TimeOutInterval;
	}
	public void setTimeOutInterval(String timeOutInterval) {
		TimeOutInterval = timeOutInterval;
	}
	public String getIDCheckRes() {
		return IDCheckRes;
	}
	public void setIDCheckRes(String iDCheckRes) {
		IDCheckRes = iDCheckRes;
	}
	public String getAccNameCheckRes() {
		return AccNameCheckRes;
	}
	public void setAccNameCheckRes(String accNameCheckRes) {
		AccNameCheckRes = accNameCheckRes;
	}
	public String getMobileNoCheckRes() {
		return MobileNoCheckRes;
	}
	public void setMobileNoCheckRes(String mobileNoCheckRes) {
		MobileNoCheckRes = mobileNoCheckRes;
	}
	public String getPWDCheckRes() {
		return PWDCheckRes;
	}
	public void setPWDCheckRes(String pWDCheckRes) {
		PWDCheckRes = pWDCheckRes;
	}
	public String getCVNExpCheckRes() {
		return CVNExpCheckRes;
	}
	public void setCVNExpCheckRes(String cVNExpCheckRes) {
		CVNExpCheckRes = cVNExpCheckRes;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getSign() {
		return Sign;
	}
	public void setSign(String sign) {
		Sign = sign;
	}

}
