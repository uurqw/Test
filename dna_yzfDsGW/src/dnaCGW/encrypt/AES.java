package dnaCGW.encrypt;

import javax.crypto.*;
import javax.crypto.spec.*;
import dnaCGW.common.*;

public class AES {

    private static byte[] AES_KEY;
    private static Object lock = new Object();

    private static String getAESKeyFromOriKey(String oriKey){
    	int len=oriKey.length();
    	if(len!=32){
    		ToolKit.writeLog("AES", "oriKey Length is not 32");
    	}
    	byte[] MK=Util.str2Bcd("891987418A1E15ABC4DA98745BCE4D1E");
    	StringBuffer buf=new StringBuffer(32);
    	int i;
    	for(i=31;i>15;i--){
        	buf.append(oriKey.charAt(i));
        }
        for(i=0;i<16;i++){
        	buf.append(oriKey.charAt(i));
        }
    	byte[] BoriKey=Util.str2Bcd(buf.toString());
    	byte[] AESKeyBtye=new byte[16];
    	for(i=0;i<16;i++)
    	{
    		AESKeyBtye[i]=(byte)(BoriKey[i]^MK[i]);
    	}
    	String AESKeyString=Util.bcdBytes2Str(AESKeyBtye);
    	return AESKeyString;
    }
    
    private static void initAES_KEY() {

        byte[] local = null;
        synchronized (lock) {

            String[] src = new String[3];
            
         /*   src[0] = DictionUtil.getValue("KEY.DB_KEY_1");
            src[1] = DictionUtil.getValue("KEY.DB_KEY_2");
            src[2] = DictionUtil.getValue("KEY.DB_KEY_3");*/
            String oriKey=ToolKit.getPropertyFromFile("AESOriKey");
            String AESKey=getAESKeyFromOriKey(oriKey);
            ToolKit.writeLog(LogLevel.DEBUG, "AES", "AESKey:",AESKey);
            local = Util.str2Bcd(AESKey);
           /* if (src.length > 1) {

                for (int i = 1; i < src.length; i++) {

                    byte[] tmp = Util.str2Bcd(src[i]);

                    for (int j = 0; j < local.length; j++) {
                        local[j] = (byte) (local[j] ^ tmp[j]);
                    }
                }
            }*/
            
        }

        if (local != null) {
            AES_KEY = local;
        }

    }

    public static String encryptTmp(String str) throws Exception {

        if (AES_KEY == null || AES_KEY.length != 16) {
            initAES_KEY();
        }

        return Util.bcdBytes2Str(encrypt(str.getBytes(), AES_KEY));
    }

    //����
    public static String encrypt(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return "";
        }
        try {
            if (AES_KEY == null || AES_KEY.length != 16) {
                initAES_KEY();
            }

            return Util.bcdBytes2Str(encrypt(str.getBytes("UTF-8"), AES_KEY));
        } catch (Exception e) {
            ToolKit.writeLog(ToolKit.class.getName(), "", e);
        }

        return str;
    }

    //����
    public static String decrypt(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return "";
        }

        try {
            if (AES_KEY == null || AES_KEY.length != 16) {
                initAES_KEY();
            }

            return new String(decrypt(Util.str2Bcd(str), AES_KEY),"UTF-8");
        } catch (Exception e) {
            ToolKit.writeLog(ToolKit.class.getName(), "", e.getMessage());
        }

        return str;
    }

    private static byte[] encrypt(byte[] data, byte[] keyByte) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");
        SecretKey key = new SecretKeySpec(keyByte, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);

    }

    private static byte[] decrypt(byte[] data, byte[] keyByte) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");
        SecretKey key = new SecretKeySpec(keyByte, "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {

//        String enc = AES.encrypt("i want to XXX you������һ��");
//        System.out.println(enc);
//        System.out.println(AES.decrypt(enc));   1234567890123456789012345678901234567890
    	String str="���ܲ��ԣ�";
    	String enc=AES.encrypt(str);
    	System.out.println("���ĳ���"+str.length());
    	System.out.println("���ĳ���"+enc.length());
        System.out.println(enc);        
        System.out.println(AES.decrypt(enc));
        
       // System.out.println(ToolKit.decryptTmp("F710 7794 9FB5 8A04E 92C9 26C5 E0EE D49C 6BD9BFCF1E3A422"));

    }
}
