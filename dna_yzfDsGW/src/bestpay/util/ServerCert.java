package bestpay.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class ServerCert {
    private String keyStore;
    private String keyStorePwd;
    private String certAlias;
    private String publicKey;
    private static PrivateKey serverPrivateKey;
    private static X509Certificate cert;

    public String getKyStore() {
        return this.keyStore;
    }

    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    public String getKeyStorePwd() {
        return this.keyStorePwd;
    }

    public void setKeyStorePwd(String keyStorePwd) {
        this.keyStorePwd = keyStorePwd;
    }

    public String getCertAlias() {
        return this.certAlias;
    }

    public void setCertAlias(String certAlias) {
        this.certAlias = certAlias;
    }

    public X509Certificate getServerCert()
            throws GeneralSecurityException {
        try {
            if (cert == null) {
                checkConfig();
                cert = CertUtils.getCertFromKeyStore(this.keyStore, this.keyStorePwd, this.certAlias);
            }
        } catch (IOException ex) {
            throw new GeneralSecurityException("获取证书异常", ex);
        }

        return cert;
    }

    public PrivateKey getServerPrivateKey()
            throws GeneralSecurityException {
        try {
            if (serverPrivateKey == null) {
                checkConfig();
                serverPrivateKey = CertUtils.getPrivateKey(this.keyStore, this.keyStorePwd, this.certAlias);
            }
        } catch (IOException ex) {
            throw new GeneralSecurityException("获取私钥异常", ex);
        }

        return serverPrivateKey;
    }

    private void checkConfig() throws GeneralSecurityException {
        if (this.keyStore.length() <= 0) {
            throw new GeneralSecurityException("配置文件中keystore未配置");
        }
        if (this.certAlias.length() <= 0) {
            throw new GeneralSecurityException("配置文件中alias未配置");
        }
        if (this.keyStorePwd.length() <= 0)
            throw new GeneralSecurityException("配置文件中password未配置");
    }

    public String getPublicKey() {
        if (this.publicKey == null) {
            try {
                this.publicKey = CertUtils.certToBase64Str(getServerCert());
            } catch (GeneralSecurityException ex) {
                throw new RuntimeException("获取证书异常", ex);
            }
        }
        return this.publicKey;
    }
}