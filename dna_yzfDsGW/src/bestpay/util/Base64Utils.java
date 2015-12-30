package bestpay.util;

import org.bouncycastle.util.encoders.Base64;

public final class Base64Utils
{
    public static byte[] decode(String base64)
    {
    	if("".equals(base64)){
    		return null;
    	}
        return Base64.decode(base64.getBytes());
    }

    public static String encode(byte[] bytes)
    {
    	if("".equals(bytes)){
    		return null;
    	}
        return new String(Base64.encode(bytes));
    }
}