package dnaCGW.common;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class Reflecter {
	private static Logger logger = Logger.getLogger(Reflecter.class.getName());

	private static boolean isBaseType(Type gt) {
		return gt.equals(int.class) || gt.equals(long.class)
				|| gt.equals(double.class) || gt.equals(boolean.class)
				|| gt.equals(byte.class) || gt.equals(short.class)
				|| gt.equals(float.class) || gt.equals(char.class)
				|| gt.equals(java.lang.Integer.class)
				|| gt.equals(java.lang.Long.class)
				|| gt.equals(java.lang.Double.class)
				|| gt.equals(java.lang.Boolean.class)
				|| gt.equals(java.lang.Byte.class)
				|| gt.equals(java.lang.Short.class)
				|| gt.equals(java.lang.Float.class)
				|| gt.equals(java.lang.Character.class)
				|| gt.equals(java.lang.String.class)
				|| gt.equals(java.math.BigDecimal.class)
				|| gt.equals(java.math.BigInteger.class);
	}

	private static boolean isDate(Type gt) {
		return gt.equals(java.util.Date.class);
	}

	private static boolean isList(Type gt) {
		return gt.toString().startsWith("java.util.ArrayList")
				|| gt.toString().startsWith("java.util.List")
				|| gt.toString().startsWith("java.util.Vector")
				|| gt.toString().startsWith("java.util.LinkedList");
	}

	private static boolean isIgnoreOrIsMap(Type gt, Class[] ignores) {
		if (ignores != null) {
			for (Class<?> ignore : ignores) {
				if (gt.equals(ignore)) {
					return true;
				}
			}
		}
		return gt.toString().startsWith("java.util.HashMap")
				|| gt.toString().startsWith("java.util.Map");
	}

	// ֧��int|long|boolean|byte|short|float|char|
	// Integer|Long|Boolean|Byte|Short|Float|String|Date|BigDecimal|BigInteger|List|
	public static String beanToXmlByFields(Object o) {
		return beanToXmlByFields(o, null);
	}

	public static String beanToXmlByFields(Object o, Class[] ignores) {
		if (o == null) {
			return "";
		}
		if (isIgnoreOrIsMap(o.getClass(), ignores)) {
			return "";
		}
		StringBuffer buf = new StringBuffer();
		String rn = o.getClass().getName();
		try {
			Class<? extends Object> c = o.getClass();
			Field[] fs = c.getDeclaredFields();
			for (Field f : fs) {
				try {
					boolean af = f.isAccessible();
					f.setAccessible(true);
					String fn = f.getName();
					Object son = f.get(o);
					String value = "";
					if (son != null) {
						Type gt = f.getGenericType();
						if (!isIgnoreOrIsMap(gt, ignores)) {
							if (isBaseType(gt)) {
								value = son.toString();
							} else if (isDate(gt)) {
								value = Formatter
										.yyyy_MM_dd_HH_mm_ss((Date) son);
							} else if (isList(gt)) {
								for (Object son_o : (List) son) {
									System.out.println(son_o);
									value += beanToXmlByFields(son_o, ignores);
								}
							} else {
								value = beanToXmlByFields(son, ignores);
							}
						}
					}

					if (!Strings.isNullOrEmpty(value)) {
						buf.append("<" + fn + ">" + value + "</" + fn + ">");
					}
					f.setAccessible(af);
				} catch (Exception e) {
					// e.printStackTrace();
					ToolKit.writeLog(Reflecter.class.getName(),
							"bean2XmlByFields.for", e);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			ToolKit.writeLog(Reflecter.class.getName(), "bean2XmlByFields", e);
		}
		StringBuffer ret = new StringBuffer();
		if (!Strings.isNullOrEmpty(buf.toString())) {
			ret.append("<" + rn + ">" + buf.toString() + "</" + rn + ">");
		}
		return ret.toString();
	}

	public static void main(String[] args) throws Exception {

	}

	// ֧��int|long|boolean|byte|short|float|char|
	// Integer|Long|Boolean|Byte|Short|Float|String|Date|BigDecimal|BigInteger|List|
	public static Object xmlToBeanByFields(Class cls, String xml) {
		return xmlToBeanByFields(cls, xml, null);
	}

	public static Object xmlToBeanByFields(Class cls, String xml, Class[] ignores) {
		if (StringUtil.isNullOrEmpty(xml)) {
			return null;
		}
		try {
			StringReader read = new StringReader(xml);
			InputSource source = new InputSource(read);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(source);
			Element root = doc.getRootElement();
			return xmlToBeanByFields(cls, root, ignores);
		} catch (Exception e) {
			logger.info("xml2BeanByFields:" + StringUtil.toString(e));
		}
		return null;
	}

	private static Object xmlToBeanByFields(Class<?> cls, Element root, Class[] ignores) throws Exception {
		// logger.info("root.getName() newInstance:"+root.getName());
		Object o = null;
		if (cls != null) {
			o = Class.forName(cls.getName()).newInstance();
		} else
			o = Class.forName(root.getName()).newInstance();// ---------//��Ҫ���޲���Ĺ��캯��
		List cs = root.getChildren();
		for (Object c_t : cs) {
			Element c = (Element) c_t;
			try {
				String fn = c.getName();
				String fv = c.getValue();
				Field f = o.getClass().getDeclaredField(fn);
				if (f != null && !StringUtil.isNullOrEmpty(fv)
						&& !f.toGenericString().contains(" final ")) {
					boolean af = f.isAccessible();
					f.setAccessible(true);
					Type gt = f.getGenericType();
					if (!isIgnoreOrIsMap(gt, ignores)) {
						if (gt.equals(int.class) || gt.equals(java.lang.Integer.class)) {
							f.set(o, Integer.parseInt(fv));
						} else if (gt.equals(long.class) || gt.equals(java.lang.Long.class)) {
							f.set(o, Long.parseLong(fv));
						} else if (gt.equals(double.class) || gt.equals(java.lang.Double.class)) {
							f.set(o, Double.parseDouble(fv));
						} else if (gt.equals(boolean.class) || gt.equals(java.lang.Boolean.class)) {
							f.set(o, Boolean.parseBoolean(fv));
						} else if (gt.equals(byte.class) || gt.equals(java.lang.Byte.class)) {
							f.set(o, Byte.parseByte(fv));
						} else if (gt.equals(short.class) || gt.equals(java.lang.Short.class)) {
							f.set(o, Short.parseShort(fv));
						} else if (gt.equals(float.class) || gt.equals(java.lang.Float.class)) {
							f.set(o, Float.parseFloat(fv));
						} else if (gt.equals(char.class) || gt.equals(java.lang.Character.class)) {
							f.set(o, fv.toCharArray()[0]);
						} else if (gt.equals(java.lang.String.class)) {
							f.set(o, fv.toString());
						} else if (gt.equals(java.math.BigDecimal.class)) {
							f.set(o, new BigDecimal(fv.toString()));
						} else if (gt.equals(java.math.BigInteger.class)) {
							f.set(o, new BigInteger(fv.toString()));
						} else if (isDate(gt)) {
							f.set(o, DateParser.yyyy_MM_dd_HH_mm_ss(fv.toString()));
						} else if (isList(gt)) {
							List<Object> l = null;
							if (gt.toString().startsWith("java.util.ArrayList") || gt.toString().startsWith("java.util.List")) {
								l = new ArrayList<Object>();
							} else if (gt.toString().startsWith("java.util.LinkedList")) {
								l = new LinkedList<Object>();
							} else if (gt.toString().startsWith("java.util.Vector")) {
								l = new Vector<Object>();
							}
							if (l != null) {
								for (Object c_l : (List) c.getChildren()) {
									Object tmp = xmlToBeanByFields(null,(Element) c_l, ignores);
									l.add(tmp);
								}
								f.set(o, l);
							}
						} else {
							Object tmp = xmlToBeanByFields(null, (Element) c.getChildren().get(0), ignores);
							f.set(o, tmp);
						}
					}
					f.setAccessible(af);
				}
			} catch (Exception e) {
				logger.info("bean2XmlByFields.for:" + StringUtil.toString(e));
			}
		}
		return o;
	}
}