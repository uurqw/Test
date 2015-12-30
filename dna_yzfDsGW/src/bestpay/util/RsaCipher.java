package bestpay.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class RsaCipher {
    public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    public static byte[] sign(byte[] data, PrivateKey privateK)
            throws GeneralSecurityException {
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateK);
        signature.update(data);

        return signature.sign();
    }

    public static boolean verify(byte[] data, String sBase64Cert, byte[] sign)
            throws GeneralSecurityException {
        X509Certificate cerObj = CertUtils.base64StrToCert(sBase64Cert);

        PublicKey publicKey = cerObj.getPublicKey();
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }

    public static boolean verify(String data, String sBase64Cert, String sign) throws GeneralSecurityException {
        try {
            byte[] bytes = data.getBytes("UTF-8");
            return verify(bytes, sBase64Cert, Base64Utils.decode(sign));
        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException("获取utf-8编码异常", e);
        }
    }

    public static byte[] enDecryptByRsa(byte[] data, Key key, int mode)
            throws GeneralSecurityException {
        Provider provider = new BouncyCastleProvider();
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            Cipher cp = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            cp.init(mode, key);
            int blockSize = cp.getBlockSize();
            int blocksNum = (int) Math.ceil(data.length / blockSize);
            int calcSize = blockSize;
            byte[] buffer = null;
            for (int i = 0; i < blocksNum; i++) {
                if (i == blocksNum - 1) {
                    calcSize = data.length - i * blockSize;
                }
                buffer = cp.doFinal(data, i * blockSize, calcSize);
                try {
                    outputStream.write(buffer);
                } catch (IOException e) {
                    throw new GeneralSecurityException("RSA加/解密时出现异常", e);
                }
            }
            return outputStream.toByteArray();
        } finally {
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
        }
    }
}