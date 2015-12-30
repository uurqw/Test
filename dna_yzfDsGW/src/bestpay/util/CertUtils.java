package bestpay.util;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertUtils {
    public static boolean compare(String thumbprint, String base64Cert)
            throws GeneralSecurityException {
        X509Certificate cert = base64StrToCert(base64Cert);
        return thumbprint.equals(getThumbprint(cert));
    }

    public static String getThumbprint(X509Certificate cert)
            throws GeneralSecurityException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        byte[] ab = md.digest(cert.getEncoded());
        return Base64Utils.encode(ab);
    }

    public static X509Certificate base64StrToCert(String base64Cert)
            throws GeneralSecurityException {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream streamCert = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(base64Cert));

            X509Certificate cert = (X509Certificate) factory.generateCertificate(streamCert);
            if (cert == null) {
                throw new GeneralSecurityException("将cer从base64转换为对象失败");
            }
            return cert;
        } catch (IOException ex) {
            throw new GeneralSecurityException("将cer从base64转换为对象失败", ex);
        }
    }

    public static String certToBase64Str(X509Certificate cert)
            throws CertificateEncodingException {
        return Base64Utils.encode(cert.getEncoded());
    }

    public static X509Certificate getCertFromKeyStore(String keyStorePath, String keyStorePwd, String alias)
            throws IOException, GeneralSecurityException {
        KeyStore ks = getKeyStore(keyStorePath, keyStorePwd);
        return (X509Certificate) ks.getCertificate(alias);
    }

    public static PrivateKey getPrivateKey(String keyStorePath, String keyStorePwd, String alias) throws GeneralSecurityException, IOException {
        KeyStore ks = getKeyStore(keyStorePath, keyStorePwd);
        PrivateKey pk = (PrivateKey) ks.getKey(alias, keyStorePwd.toCharArray());
        return pk;
    }

    public static String getCertStringFromCer(String cerFilePath) throws GeneralSecurityException {
        X509Certificate cert = getCertFromCer(cerFilePath);
        String base64Str = null;
        base64Str = certToBase64Str(cert);
        return base64Str;
    }

    public static X509Certificate getCertFromCer(String cerFilePath) throws GeneralSecurityException {
        CertificateFactory f = CertificateFactory.getInstance("X.509");
        File file = getCertFile(cerFilePath);
        InputStream fs = null;
        try {
            fs = new FileInputStream(file);
            X509Certificate certificate = (X509Certificate) f.generateCertificate(fs);

            return certificate;
        } catch (IOException ex) {
            throw new GeneralSecurityException("获取证书异常");
        } finally {
            try {
                if (fs != null)
                    fs.close();
            } catch (Exception ex) {
            }
        }
    }

    private static File getCertFile(String path) throws GeneralSecurityException {
        File file = new File(CertUtils.class.getClassLoader().getResource(path).getFile());
        if (!file.exists()) {
            throw new GeneralSecurityException("证书文件不存在：" + path);
        }
        return file;
    }

    private static KeyStore getKeyStore(String keyStorePath, String keyStorePwd) throws GeneralSecurityException {
        File file = getCertFile(keyStorePath);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream fs = null;
        try {
            fs = new FileInputStream(file);
            ks.load(fs, keyStorePwd.toCharArray());
        } catch (IOException ex) {
            throw new GeneralSecurityException("获取证书异常");
        } finally {
            try {
                if (fs != null)
                    fs.close();
            } catch (Exception ex) {
            }
        }
        return ks;
    }
}