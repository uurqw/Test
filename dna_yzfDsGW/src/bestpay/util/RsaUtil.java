package bestpay.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class RsaUtil {
    public static ServerCert serverCert = new ServerCert();
    public static PrivateKey key;
    public static String cert;

    /**
     * 获取私钥
     * @return
     * @throws GeneralSecurityException
     */
    public static PrivateKey getPrivateKey()
            throws GeneralSecurityException {
        if (key == null) {
            key = serverCert.getServerPrivateKey();
        }
        return key;
    }

    /**
     * 获取公钥
     * @return
     * @throws GeneralSecurityException
     */
    public static String getPublicKey()
            throws GeneralSecurityException {
        if ((cert == null) || ("".equals(cert))) {
            X509Certificate x509Certificate = null;
            x509Certificate = serverCert.getServerCert();
            cert = CertUtils.certToBase64Str(x509Certificate);
        }
        return cert;
    }

    /**
     * 加签
     * @param plainText
     * @return
     * @throws UnsupportedEncodingException
     * @throws GeneralSecurityException
     */
    public static String sign(String plainText) throws UnsupportedEncodingException, GeneralSecurityException {
        byte[] bytes = RsaCipher.sign(plainText.getBytes("UTF-8"), getPrivateKey());
        return Base64Utils.encode(bytes);
    }

    /**
     * 初始化证书信息
     */
    static {
        serverCert.setCertAlias("server");
        serverCert.setKeyStorePwd("123456");
        serverCert.setKeyStore("cert/server.jks");
    }
}