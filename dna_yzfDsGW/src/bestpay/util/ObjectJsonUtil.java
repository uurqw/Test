package bestpay.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Object、JSon格式转换工具类
 */
public class ObjectJsonUtil
{
    public static String objToJson(Object obj) throws IOException {
        if (obj == null){
        	return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = new StringWriter();
        mapper.writeValue(strWriter, obj);
        return strWriter.toString();
    }

    public static <T> T jsonToObj(String dataJsonStr, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(dataJsonStr, clazz);
    }
}