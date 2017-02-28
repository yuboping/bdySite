package com.xirui.util.common;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * 
	 * <p>Description:将list map数据转换为文件存储数据</p>
	 * @author yuboping
	 * @date 2016年3月16日上午11:48:02
	 * @param params 需要转换的list map
	 * @param sign 分隔符,比如|
	 * @return 以|分割的字符串的值
	 */
	public static String mapToStr(List<Map<?, ?>> params,String sign) {
		StringBuffer result = new StringBuffer();
 
		if (null != params && params.size() > 0) {
			for (Map<?,?> map : params) {
				result.append(mapToStr(map,sign));
			}
		}

		return result.toString();
	}
	
	/**
	 * 
	 * <p>Description:将 map数据转换为文件存储数据</p>
	 * @author yuboping
	 * @date 2016年4月8日下午3:44:08
	 * @param params
	 * @param sign
	 * @return
	 */
	public static String mapToStr(Map<?, ?> params,String sign) {
		StringBuffer result = new StringBuffer();
		int sum = 1;
		for (Map.Entry<?, ?> entry : params.entrySet()) {
			if (!"".equals(entry.getValue()) && null != entry.getValue()) {
				if (sum == params.size()) {
					result.append(entry.getValue());
				} else {
					result.append(entry.getValue()).append(sign);
				} 
			} else {
				if (sum == params.size()) {
					result.append("");
				} else {
					result.append("").append(sign);
				} 
			}
			sum ++;
		}
		return result.toString();
	}
	
	/**
	 * 
	 * <p>Description:list bean 数据转换为文件存储数据</p>
	 * @author yuboping
	 * @date 2016年3月16日下午2:16:27
	 * @param params 需要转换的list bean
	 * @param sign 分隔符,比如|
	 * @return 以|分割的字符串的值
	 * @throws Exception
	 */
	public static String beanToStr(List<?> params,String sign) throws Exception {
		StringBuffer result = new StringBuffer();
		
		if (null != params && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				result.append(beanToStr(params.get(i),sign));
			}
		} 

		return result.toString();
	}
	
	/**
	 * 
	 * <p>Description:bean 数据转换为文件存储数据</p>
	 * @author yuboping
	 * @param <T>
	 * @date 2016年3月16日下午2:16:27
	 * @param params 需要转换的list bean
	 * @param sign 分隔符,比如|
	 * @return 以|分割的字符串的值
	 * @throws Exception
	 */
	public static <T> String beanToStr(T t,String sign) throws Exception {
		StringBuffer result = new StringBuffer();
		
		// java 反射获取bean属性
		Field[] fields = t.getClass().getDeclaredFields();
		// 属性被声明为private的,需要将setAccessible设置为true,默认的值为false
		Field.setAccessible(fields,  true);
		for (int j = 0; j < fields.length; j++) {
			if (null != fields[j].get(t)) {
				if (j == fields.length-1) {
					result.append(fields[j].get(t));
				} else {
					result.append(fields[j].get(t)).append(sign);
				}
			} else {
				if (j == fields.length-1) {
					result.append("");
				} else {
					result.append("").append(sign);
				}
			}
		}
			
		return result.toString();
	}
	
	/**
	 * 
	 * <p>Description:文件名称</p>
	 * @author yuboping
	 * @date 2016年3月16日上午10:36:42
	 * @param fileflowno 文件流水号
	 * @param type 文件类型
	 * @param suffix 文件后缀
	 * @return
	 */
	public static String bulidFileName(String fileflowno,String type,String suffix) {
		StringBuffer result = new StringBuffer();
		result.append(fileflowno).append("_");
		result.append(DateUtil.parseStr(new Date(),DateUtil.C_TIMES_PATTON_DEFAULT)).append("_");
		result.append(type).append(suffix);
		return result.toString();
	}
		
	/**
	 * 
	 * <p>Description:将分割的字符串转换为list map</p>
	 * @author yuboping
	 * @date 2016年3月21日下午5:36:36
	 * @param title map key值,自定义
	 * @param content map value值,分割获取
	 * @return
	 */
	public static List<Map<String,Object>> strToMap(String[] title,String content) {
		List<Map<String,Object>> resultMap = new ArrayList<Map<String,Object>>();
		if (null != title && title.length > 0) {
			if (content.contains("|")) {
				String[] cont = content.split("\\|");
				if (title.length == cont.length) {
					for (int i = 0;i<title.length;i++) {
						Map<String,Object> map = new HashMap<String,Object>();
						map.put(title[i], cont[i]);
						resultMap.add(map);
					}
				}
			}
		}
		return resultMap;
	}
	
	
	/**
	 * 该方法只返回一条数据
	 * <p>Description:将分割的字符串分割成list bean</p>
	 * @author yuboping
	 * @date 2016年3月22日上午10:19:10
	 * @param t pojo 实体类
	 * @param content 分割的字符串
	 * @return List bean 字符串的值要对应bean属性的顺序
	 * @throws Exception
	 */
	public static <T> List<T> strTobean(T t,String content) throws Exception {
		List<T> resultList = new ArrayList<T>();
		if (content.contains("|")) {
			String[] cont = content.split("\\|");
			for (int i = 0;i<cont.length;i++) {
				Field[] fields = t.getClass().getDeclaredFields();
				Field.setAccessible(fields, true);
				fields[i].set(t, cont[i]);
			} 
			resultList.add(t);
		}
		return resultList;
	}
	
	/**
	 * 
	 * <p>Description:修改分割字符串中的值</p>
	 * @author yuboping
	 * @date 2016年4月22日下午5:53:11
	 * @param spl 需要修改的分割字符串
	 * @param sum 需要修改的位置
	 * @param value 修改位置对应的值
	 * @return
	 */
	public static String updToString(String spl,int sum,String value) {
		String[] ct = spl.split("\\|");
		StringBuffer sb = new StringBuffer();
		for (int i = 0;i<ct.length;i++ ) {
			if (i == sum) {
				ct[i] = value;
				sb.append(ct[i]).append("|");
			} else {
				sb.append(ct[i]).append("|");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * <p>Description:获取2个数的百分比</p>
	 * @author yuboping
	 * @date 2016年7月14日 下午6:22:36
	 * @param num
	 * @param sum
	 * @return
	 */
	public static String getRatiso(int num,int sum) {
		NumberFormat numberFormat = NumberFormat.getInstance();  
	    // 设置精确到小数点后2位  
        numberFormat.setMaximumFractionDigits(2);  
        String result = numberFormat.format((float) num / (float) sum * 100);
        return result + "%";
	}
	
	/**
	 * 
	 * <p>Description:生成短信内容</p>
	 * @author yuboping
	 * @date 2016年8月22日 下午2:36:56
	 * @return
	 */
	public static String getContent(String code) {
		return "保钱袋的验证码为："+code+"。请于15分钟内正确输入";
	}
	
	/**
	 * 
	 * <p>Description:去除表情包数据</p>
	 * @author yuboping
	 * @date 2016年9月6日 下午3:16:04
	 * @param source
	 * @return
	 */
	public static String creatBrow(String source) {
		if (source != null && source.length() > 0) {
			return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
		} else {
			return source;
		}
	}
	
	/**
	 * 
	 * <p>Description:获取时间戳</p>
	 * @author yuboping
	 * @date 2016年9月18日 下午4:33:02
	 * @return
	 */
	public static String getTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	
	/**
	 * 
	 * <p>Description:生成随机字符串</p>
	 * @author yuboping
	 * @date 2016年9月18日 下午5:11:32
	 * @param length 字符串的长度
	 * @return
	 */
	public static String getNonceStr(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFHIJKLMNOPSUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * <p>Description:UUID创建随机字符串</p>
	 * @author yuboping
	 * @date 2016年9月18日 下午5:33:49
	 * @return
	 */
	public static String getUUIDStr() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	
	public static void main(String[] args) {
		String cd = "o8_Qqs-e8_VdV0DTsfmKkDVXXwQg";
		System.out.println(cd.hashCode());
	}
	
}
