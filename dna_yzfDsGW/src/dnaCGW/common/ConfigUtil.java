package dnaCGW.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ConfigUtil {

    private static Logger logger = Logger.getLogger(ConfigUtil.class.getName());
    
    private static Map<String,String> configs = new HashMap<String,String>();
    private static Map<String,String> files = new HashMap<String,String>();

    public static String getValue(String key) {
    	
    //	String value = getValueFromFile(key);
    	
    	//if(ToolKit.isNullOrEmpty(value))
    	//	value = getValueFromProperties("systemsetting",key);
    	
    	return getValueFromProperties("systemsetting",key);
    }
    
    public static String getValue(String file, String key) {
    	
    	/*String value = getValueFromFile(file,key);
    	
    	if(ToolKit.isNullOrEmpty(value))
    		value = getValueFromProperties(file,key);*/
    	
    	return getValueFromProperties(file,key);
    }
    
    private static String getValueFromProperties(String filename, String key) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle(filename);
            return ToolKit.trim(rb.getString(key));
        } catch (Exception e) {
        	logger.error("no properties key:"+key);
            return "";
        }
    }
    
    private static void set(String file) {

        try {
    		BufferedReader br = new BufferedReader(new FileReader(new File("./"+file+".ini")));
    		// ResourceBundle rb = ResourceBundle.getBundle("systemsetting");
    		String tmp = "";
    		while((tmp = br.readLine()) != null) {
    			if(!tmp.startsWith("#") && !ToolKit.isNullOrEmpty(tmp)) {
        			try {
        				String[] cf = tmp.split("=");
        				if(cf.length == 2)
        					configs.put(cf[0].trim(), cf[1].trim());
        			} catch(Exception e) {
        	        	logger.error(ToolKit.toString(e));
        			}
    			}
    		}
    		files.put(file, file);
        } catch (Exception e) {
        	logger.error(ToolKit.toString(e));
        }
    }
    
    private static String getValueFromFile(String key) {
    	
    	if(configs == null || files.get("config") == null)
    		set("config");
    	return configs.get(key);
    }
    
    private static String getValueFromFile(String file,String key) {
    	
    	if(configs == null || files.get(file) == null)
    		set(file);
    	return configs.get(key);
    }
}
