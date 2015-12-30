package bestpay.trans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;

import org.apache.log4j.Logger;

import util.CommonUtil;

import bestpay.entity.BestpayConstants;
import bestpay.entity.Request;
import bestpay.entity.Response;
import bestpay.entity.ResponseDetail;
import bestpay.util.ObjectJsonUtil;
import bestpay.util.RsaCipher;

import dnaCGW.common.ToolKit;
import dnaCGW.entity.CGWMessageBean;

//交易查询
public class TransQuery {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	/*通信方法*/
	public void trans(String platformCode, String cert, String sign, String  data, CGWMessageBean resMsgBean) throws IOException{
		logger.info("平台号: " + platformCode + ",公钥信息: " + cert + ",签名信息: " + sign + ",原始明文串: " + data);
		Request transReq = new Request();
		transReq.setPlatformCode(platformCode);
		transReq.setCert(cert);
		transReq.setSign(sign);
		transReq.setData(data);
		String transMes = ObjectJsonUtil.objToJson(transReq);
		URLConnection conn = null;
		try{
			URL url = new URL(BestpayConstants.BESTPAY_URL + "/service/transIntegratedQuery");
			conn = url.openConnection();
			HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
			httpUrlConnection.setRequestMethod("POST");
			byte[] dataByte = transMes.getBytes("utf-8");
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setInstanceFollowRedirects(false);
			httpUrlConnection.addRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpUrlConnection.addRequestProperty("Content-Length", String.valueOf(dataByte.length));
            OutputStream out = httpUrlConnection.getOutputStream();
            out.write(dataByte);
            out.flush();
            out.close();
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
            BufferedReader in = new BufferedReader(inputStreamReader);
            
            /*解读返回数据*/
            String line = null;
            StringBuilder stringBuilder = new StringBuilder(255);
            while((line = in.readLine())!=null){
            	stringBuilder.append(line);
            	stringBuilder.append("\n");
            }
            String responseMessage = stringBuilder.toString();
            ToolKit.writeLog("Trans11.trans", "解密返回的数据====>"+responseMessage);
            Response transResponse = ObjectJsonUtil.jsonToObj(responseMessage, Response.class);
            String plainText = ObjectJsonUtil.objToJson(transResponse);
            try{
            	boolean check = RsaCipher.verify(plainText, BestpayConstants.BESTPAY_CERT, transResponse.getSign());
            	if(!check){
            		responseMessage = "报文被篡改";
            		resMsgBean.setResponseCode("00A0");
            		resMsgBean.setRemark(responseMessage);
            		ToolKit.writeLog("trans", responseMessage);
            		return;
            	}
            }catch(GeneralSecurityException ge){
            	responseMessage = "验签失败";
            	resMsgBean.setResponseCode("00A0");
        		resMsgBean.setRemark(responseMessage);
            	ToolKit.writeLog(Trans11.class.getName(), responseMessage, ge);
            }
            ToolKit.writeLog("trans", "验签成功");
            ResponseDetail transResponseDetail = ObjectJsonUtil.jsonToObj(plainText, ResponseDetail.class);
            if("000000".equals(transResponseDetail.getCode())){
            	resMsgBean.setResponseCode("0000");
            	resMsgBean.setRemark(transResponseDetail.getMsg());
            	return;
            }else{
            	resMsgBean.setResponseCode(CommonUtil.transRespCode(transResponseDetail.getCode()));
            	resMsgBean.setRemark(transResponseDetail.getMsg());
            	return;
            }
		}catch(Exception e){
			ToolKit.writeLog(Trans11.class.getName(), "异常", e);
		}finally{
			if((conn!=null)&&conn instanceof HttpURLConnection){
				((HttpURLConnection) conn).disconnect();
			}
		}
	}
}
