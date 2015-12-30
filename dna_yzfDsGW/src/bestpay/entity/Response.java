package bestpay.entity;

import java.io.Serializable;

/**
 * 翼支付前置调用返回的加签对象
 */
@SuppressWarnings("serial")
public class Response implements Serializable {
    private String sign;//签名信息
    private String data;   //返回信息

    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
}