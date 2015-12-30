package bestpay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import org.apache.log4j.Logger;

import util.CommonUtil;

import bestpay.entity.BestpayConstants;
import bestpay.entity.QueryRequest;
import bestpay.entity.TransRequest;
import bestpay.entity.TransRequestDetail;
import bestpay.trans.Trans11;
import bestpay.trans.TransQuery;
import bestpay.util.ObjectJsonUtil;
import bestpay.util.RsaUtil;

import dnaCGW.common.*;
import dnaCGW.encrypt.AES;
import dnaCGW.entity.CGWMessageBean;
import dnaCGW.mq.MqService;
import dnaCGW.mq.Semaphore;

public class Handler extends Thread {
	private static Logger logger = Logger.getLogger(Handler.class.getName());
	private MqService ms = null;
	private String msgId;
	private String req = "";
	private BestpayDsServer server = null;
	private static Semaphore semaphore = new Semaphore(1000);
	
	public Handler(String[] req , BestpayDsServer s , MqService ms){
		semaphore.acquire();
		this.msgId = req[0];
		this.server = s;
		this.ms = ms;
		try{
			this.req = new String(Formatter.base64Decode(req[1]),"UTF-8");
		}catch(UnsupportedEncodingException e){
			ToolKit.writeLog(this.getClass().getName(), "异常：", e);
		}catch(IOException e){
			ToolKit.writeLog(this.getClass().getName(), "异常: ", e);
		}
	}
	
	public void run(){
		CGWMessageBean reqMsgBean = (CGWMessageBean) Reflecter.xmlToBeanByFields(CGWMessageBean.class, req);
		CGWMessageBean resMsgBean = (CGWMessageBean) reqMsgBean.clone();
		ToolKit.writeLog(this.getClass().getName(), "upiDS获取请求消息：msgid: " + this.msgId + ": " + Strings.toXmlString(reqMsgBean));
		String sign = null;
		String data = null;
		
		//公共需求
		if(!reqMsgBean.VerifyMac()){
			resMsgBean.setResponseCode("00A0");
			resMsgBean.setRemark(reqMsgBean.getChannelGWID()+"网关检验请求报文失败！");
			response(resMsgBean,"");
			return;
		}
		if(Strings.isNullOrEmpty(reqMsgBean.getCentralGWTranSeqNo())){      //验证集中网管流水号
			resMsgBean.setResponseCode("0030");
			resMsgBean.setRemark("报文格式出错,集中网关交易流水CentralGWTransSeqNo无值");
			response(resMsgBean,"");
			return;
		}
		if(Strings.isNullOrEmpty(reqMsgBean.getCUPMerID())){                //验证商户号
			resMsgBean.setResponseCode("0030");
			resMsgBean.setRemark("报文格式出错,银联商户号CUPMerID不能为空");
			response(resMsgBean, "");
			return;
		}
		
		//交易查询
		if(reqMsgBean.getTranCode().equals("TransQuery")){
			if(Strings.isNullOrEmpty(reqMsgBean.getOriCentralGWTranSeqNo())){//验证原始交易流水
				resMsgBean.setResponseCode("0030");
				resMsgBean.setRemark("报文格式出错,原始交易流水不能为空");
				response(resMsgBean, "");
				return;
			}
			QueryRequest queryRequest = new QueryRequest();
			queryRequest.setReqSeq(reqMsgBean.getCentralGWTranSeqNo());
			queryRequest.setCustCode(ConfigUtil.getValue("MERID"));
			/* 商户在翼支付的平台号 */
			queryRequest.setPlatCode("");
			queryRequest.setReqIp(ConfigUtil.getValue("ip_address"));
			queryRequest.setExtOrderSeq(reqMsgBean.getOriCentralGWTranSeqNo());
			queryRequest.setOriginalSeq(reqMsgBean.getOriCentralGWTranSeqNo());
			queryRequest.setTrsSeq(reqMsgBean.getOriCentralGWTranSeqNo());
			try {
				data = ObjectJsonUtil.objToJson(queryRequest);
				sign = RsaUtil.sign(data);
			} catch (IOException e) {
				sign = "加签失败";
	            ToolKit.writeLog(this.getClass().getName(), sign, e);
			} catch (GeneralSecurityException e) {
				ToolKit.writeLog(this.getClass().getName(), "异常", e);
			}
			if(!Strings.isNullOrEmpty(data)&&!Strings.isNullOrEmpty(sign)){
				try{
					new TransQuery().trans(queryRequest.getPlatCode(), BestpayConstants.BESTPAY_CERT, sign, data, resMsgBean);
				}catch(Exception e){
					ToolKit.writeLog(this.getClass().getName(), "异常", e);
				}
			}
			response(resMsgBean, "");
			return;
		}
		
		//实时代付
		if(reqMsgBean.getTranCode().equals("SPay4OtherRTime")){
			if(Strings.isNullOrEmpty(reqMsgBean.getAppPlatformTranTime())){//验证时间
				resMsgBean.setResponseCode("0030");
				resMsgBean.setRemark("报文格式出错,交易时间不能为空");
				response(resMsgBean, "");
				return;
			}
			if(Strings.isNullOrEmpty(reqMsgBean.getBankName())){//验证银行名称
				resMsgBean.setResponseCode("0030");
				resMsgBean.setRemark("报文格式出错,银行名称不能为空");
				response(resMsgBean, "");
				return;
			}
			if(Strings.isNullOrEmpty(reqMsgBean.getAccType())){//验证账户类型
				resMsgBean.setResponseCode("0030");
				resMsgBean.setRemark("报文格式出错,账户类型不能为空");
				response(resMsgBean, "");
				return;
			}
			if(Strings.isNullOrEmpty(reqMsgBean.getAccNo())){     //验证银行账号
				resMsgBean.setResponseCode("0030");
				resMsgBean.setRemark("报文格式出错,accno不能为空");
				response(resMsgBean, "");
				return;
			}
			if(Strings.isNullOrEmpty(reqMsgBean.getAccName())){
				resMsgBean.setResponseCode("0030");
				resMsgBean.setRemark("报文格式出错,accName不能为空");//验证开户名
				response(resMsgBean, "");
				return;
			}
			if(Strings.isNullOrEmpty(reqMsgBean.getIDNo())){//验证证件号码
				resMsgBean.setResponseCode("0030");
				resMsgBean.setRemark("报文格式出错,证件号码不能为空");
				response(resMsgBean, "");
				return;
			}
			if(Strings.isNullOrEmpty(reqMsgBean.getIDType())){//验证证件类型
				resMsgBean.setResponseCode("0030");
				resMsgBean.setRemark("报文格式出错,证件类型不能为空");
				response(resMsgBean, "");
				return;
			}
			if(Strings.isNullOrEmpty(reqMsgBean.getAccOpenCity())){//验证开户省市
				resMsgBean.setResponseCode("0030");
				resMsgBean.setRemark("报文格式出错,开户省市不能为空");
				response(resMsgBean, "");
				return;
			}
			if(Strings.isNullOrEmpty(reqMsgBean.getTranAmount())){//验证交易金额
				resMsgBean.setResponseCode("0030");
				resMsgBean.setRemark("报文格式出错,交易金额不能为空");
				response(resMsgBean, "");
				return;
			}
			TransRequest transReq = new TransRequest();
			TransRequestDetail transReqDetail = new TransRequestDetail();
			transReqDetail.setBankCode(CommonUtil.getBankCode(reqMsgBean.getBankName()));
			transReqDetail.setCardType(CommonUtil.getCardType(reqMsgBean.getAccType()));
			transReqDetail.setAccountCode(AES.decrypt(reqMsgBean.getAccNo()));
			transReqDetail.setBankCardName(reqMsgBean.getAccName());
			transReqDetail.setCertNo(reqMsgBean.getIDNo());
			transReqDetail.setCertType(CommonUtil.transCertType(reqMsgBean.getIDType()));
			transReqDetail.setOpenBankAddress(Strings.isNullOrEmpty(reqMsgBean.getAccOpenCity())? "" : reqMsgBean.getAccOpenCity());
			transReqDetail.setMobile(Strings.isNullOrEmpty(reqMsgBean.getMobileNo())? "" : AES.decrypt(reqMsgBean.getMobileNo()));
			/* 银行区域码(M) */
			transReqDetail.setAreaCode("");
			transReqDetail.setPerEntFlag(reqMsgBean.getAccProp() == "1"? "0" : "1");
			try{
				data = ObjectJsonUtil.objToJson(transReqDetail);
			}catch(Exception e ){
				ToolKit.writeLog(this.getClass().getName(), "异常", e);
			}
			
			transReq.setReqSeq(reqMsgBean.getCentralGWTranSeqNo());
			/* 商户在翼支付的商户号 */
			transReq.setCustCode("");
			/* 商户在翼支付的平台号 */
			transReq.setPlatCode("");
			transReq.setRequestTime(reqMsgBean.getAppPlatformTranTime());
			transReq.setReqIp(ConfigUtil.getValue("ip_address"));
			transReq.setTrsSummary("实时代付");
			transReq.setTrsMemo(Strings.isNullOrEmpty(reqMsgBean.getTranDescription())? "" : reqMsgBean.getTranDescription());
			transReq.setProjectCode("");
			transReq.setCurrencyCode(CommonUtil.getCurrencyCode(reqMsgBean.getCurrency()));
			transReq.setTransactionAmount(reqMsgBean.getTranAmount());
			/* 易联账户编码 */
			transReq.setAccountCode("");
			/* 易联账户名 */
			transReq.setAccountName("");
			transReq.setPayeeBankAccount(data);
			try{
				data = ObjectJsonUtil.objToJson(transReq);
				sign = RsaUtil.sign(data);
			} catch (GeneralSecurityException e) {
	            sign = "加签失败";
	            ToolKit.writeLog(this.getClass().getName(), sign, e);
			} catch (Exception e){
				ToolKit.writeLog(this.getClass().getName(), "异常", e);
			}
			if(!Strings.isNullOrEmpty(data)&&!Strings.isNullOrEmpty(sign)){
				try{
					new Trans11().trans(transReq.getPlatCode(), BestpayConstants.BESTPAY_CERT, sign, data, resMsgBean);
				} catch (IOException e) {
					ToolKit.writeLog(this.getClass().getName(), "异常", e);
				}
			}
			response(resMsgBean, "");
			return;
		}else{
			resMsgBean.setResponseCode("0030");
			resMsgBean.setRemark("报文格式出错:"+reqMsgBean.getTranCode()+" 有错误!"+reqMsgBean.getChannelGWID()+"网关只支持实时代付");
			response(resMsgBean, "");
			return;
		}
	}
	
	public void response(CGWMessageBean resMsgBean, String sendResult){
		String xmlMessage = "";
		resMsgBean.setRetrievalRefNo(resMsgBean.getCentralGWTranSeqNo());
		resMsgBean.setSettlementDate(Formatter.MMdd(new Date()));
		try {
			 xmlMessage=Reflecter.beanToXmlByFields(resMsgBean);	
			 resMsgBean=(CGWMessageBean)Reflecter.xmlToBeanByFields(CGWMessageBean.class, xmlMessage);
		} catch (Exception e) {
			 ToolKit.writeLog(this.getClass().getName(), "异常", e);
		}
		
		resMsgBean.setSign(resMsgBean.getMacValue());
		xmlMessage = Reflecter.beanToXmlByFields(resMsgBean);
		ToolKit.writeLog(this.getClass().getName(), this.ms.getProperties().get("hostname") + resMsgBean.getChannelGWID() + "网关返回resMsgBeanXML:" + Strings.toXmlString(resMsgBean));
		String res = null;
		try {
			res = Formatter.base64Encode(xmlMessage.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			ToolKit.writeLog(this.getClass().getName(), "异常：", e);
		} catch (IOException e) {
			ToolKit.writeLog(this.getClass().getName(), "异常：", e);
		}
		if (res != null) {
			ms.put(msgId, res);
			logger.info(msgId + " :msg 放回响应队列成功；" + ms.getProperties().get("hostname"));
		}
		if (sendResult.equals("fail")) {
			this.server.addSendFailNum();
			int maxSendFailNum = Integer.parseInt(ConfigUtil.getValue("MAXSendFailNum"));
			//long suspendServerTime = Long.parseLong(ConfigUtil.getValue("suspendServerTime"));
			logger.info("发送到银联失败最大次数:" + maxSendFailNum);
			logger.info("当前失败次数：" + this.server.getSendFailNum());
			if (this.server.getSendFailNum() >= maxSendFailNum * 5) {
				logger.info("收不到确定返回连续次数超过" + maxSendFailNum * 5 + "次:" + ",网关将休眠：30分钟");
				this.server.setSuspendTime(1000 * 60 * 30);
			} else if (this.server.getSendFailNum() >= maxSendFailNum * 4) {
				logger.info("收不到确定返回连续次数超过" + maxSendFailNum * 4 + "次:" + ",网关将休眠：10分钟");
				this.server.setSuspendTime(1000 * 60 * 10);
			} else if (this.server.getSendFailNum() >= maxSendFailNum * 3) {
				logger.info("收不到确定返回连续次数超过" + maxSendFailNum * 3 + "次:" + ",网关将休眠：5分钟");
				this.server.setSuspendTime(1000 * 60 * 5);
			} else if (this.server.getSendFailNum() >= maxSendFailNum * 2) {
				logger.info("收不到确定返回连续次数超过" + maxSendFailNum * 2 + "次:" + ",网关将休眠：1分钟");
				this.server.setSuspendTime(1000 * 60);
			} else if (this.server.getSendFailNum() >= maxSendFailNum) {
				logger.info("收不到确定返回连续次数超过" + maxSendFailNum + "次:" + ",网关将休眠：5秒");
				this.server.setSuspendTime(1000 * 5);
			}
		} else if (sendResult.equals("success")) {
			this.server.minusSendFailNum();
			this.server.setSuspendTime(0);
		}
		semaphore.release();
	}
}
