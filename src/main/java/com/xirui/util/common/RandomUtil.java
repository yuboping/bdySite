/**
 * 
 */
package com.xirui.util.common;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Title:RandomUtil
 * </p>
 * <p>
 * Description:随机数工具类
 * </p>
 * <p>
 * Company:yuboping
 * </p>
 * 
 * @author yuboping
 * @date 2016年8月22日下午2:00:13
 */
public class RandomUtil {

	// 数字字母随机
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	// 大小写字母随机
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	// 数字随机
	public static final String NUMBERCHAR = "0123456789";

	/**
	 * 
	 * <p>
	 * Description:包含大小写字母,数字
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年8月22日 下午2:03:36
	 * @param length
	 * @return
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:生成纯数字
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年8月22日 下午2:23:37
	 * @param length
	 * @return
	 */
	public static String generateNum(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年8月22日 下午2:05:19
	 * @param length
	 * @return
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年8月22日 下午2:10:13
	 * @param length
	 * @return
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 
	 * <p>
	 * Description:返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年8月22日 下午2:10:27
	 * @param length
	 * @return
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 
	 * <p>
	 * Description:生成一个定长的纯0字符串
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年8月22日 下午2:12:48
	 * @param length
	 * @return
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:根据数字生成一个定长的字符串，长度不够前面补0
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年8月22日 下午2:16:34
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符长度
	 * @return
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:每次生成的len位数都不相同
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年8月22日 下午2:19:46
	 * @param param
	 * @param len
	 * @return
	 */
	public static int getNotSimple(int[] param, int len) {
		Random rand = new Random();
		for (int i = param.length; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = param[index];
			param[index] = param[i - 1];
			param[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < len; i++) {
			result = result * 10 + param[i];
		}
		return result;
	}

}
