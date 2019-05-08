package com.haier.datamart.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.datamart.entity.EnteringTableSettingDetail;

/**
 * 
 * @author zuoqb 根据统一接口请求数据
 */
public class InterfaceDataUtils {
	/**
	 * 
	 * @time 2018年10月10日 下午4:17:52
	 * @author zuoqb
	 * @todo TODO
	 * @param @param settingDetail
	 * @param @param param
	 * @param @return
	 * @return_type List<String>
	 */
	public static List<String> getInterfaceData(
			EnteringTableSettingDetail settingDetail, String param) {
		List<String> result = new ArrayList<String>();
		if (settingDetail == null
				|| StringUtils.isBlank(settingDetail.getDataType())
				|| StringUtils.isBlank(settingDetail.getDataSpace())
				|| StringUtils.isBlank(settingDetail.getDataKey())) {
			return result;
		}
		// http://10.138.42.215:19820/common/inter?dataType=aa_bb_cc_111&fresh=1&params=time::201806
		String url = ExcelConnection.interface_url + "?dataType="
				+ settingDetail.getDataType() + "&fresh=1";
		if (StringUtils.isNotBlank(param)) {
			try {
				url += "&params="
						+ new String(param.getBytes("utf-8"), "utf-8");
			} catch (Exception e) {
			}
		}
		System.out.println(url);
		// 发送请求
		String data = HttpClientUtil.sendGetRequest(url, "UTF-8");
		System.out.println(data);
		JSONObject object = JSONObject.parseObject(data);
		Object arrStr = object.get(settingDetail.getDataSpace());
		System.out.println(arrStr);
		if (arrStr != null && StringUtils.isNotBlank(arrStr.toString())) {
			JSONArray array = JSONArray.parseArray(arrStr.toString());
			for (int i = 0; i < array.size(); i++) {
				JSONObject cu = JSONObject.parseObject(array.get(i).toString());
				Object value = cu.get(settingDetail.getDataKey());
				System.out.println(value);
				if (value != null) {
					result.add(value.toString());
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * @time 2018年10月10日 下午4:24:41
	 * @author zuoqb
	 * @todo 获取外部接口最大行数
	 * @param @param interfaceData
	 * @param @return
	 * @return_type int
	 */
	public static int getMaxRow(List<List<String>> interfaceData) {
		int max = 0;
		for (List<String> list : interfaceData) {
			if (max < list.size()) {
				max = list.size();
			}
		}
		return max;

	}

	/**
	 * 
	 * @time 2018年10月10日 下午5:45:49
	 * @author zuoqb
	 * @todo 转二进制
	 * @param @return
	 * @return_type String
	 */
	public static String toBinary(String str) {
		/*
		 * char[] strChar = str.toCharArray(); String result = ""; String data
		 * =""; for (int i = 0; i < strChar.length; i++) { result +=
		 * Integer.toBinaryString(strChar[i]) + " "; } try {
		 * System.out.println(result); data = new String(result.getBytes(),
		 * "utf-8"); } catch (Exception e) { e.printStackTrace(); }
		 * System.out.println(data); return data;
		 */
		byte[] b = null;
		try {
			b = str.getBytes("gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < b.length; i++) {
			System.out.print(Integer.toBinaryString(b[i] & 0xff));
		}
		String fString = new String(b);
		System.out.print("\n" + fString);
		return fString;
	}

	public static void main(String[] args) {
		EnteringTableSettingDetail d = new EnteringTableSettingDetail();
		d.setDataType("aa_bb_cc_111");
		d.setDataSpace("abc");
		d.setDataKey("cname");
		// getInterfaceData(d, "time::201806");
		binaryToString(toBinary("我爱你"));//谪僨鹖
	}

	/**
	 * 
	 * @time 2018年10月10日 下午5:46:17
	 * @author zuoqb
	 * @todo 二进制转字符串
	 * @param @param source
	 * @return_type void
	 */
	protected static void binaryToString(String source) {
		// source="101111001110100101001101001110100100000001110111111111000011001001011101010010110011000100101100101101011111010011010011110111111111100001100101100100101001110110110101111100111101010101101100100000100110011000101111101111101010010100011011110111111000000000010";
		// 定义正则表达式 // 匹配所有由1或0组成的8位字符
		Pattern p = Pattern.compile("[10]{8}");
		// 定义匹配器，与源字符串关连上
		Matcher m = p.matcher(source);
		// 安放匹配结果
		List<Byte> list = new ArrayList<Byte>();
		while (m.find()) {
			list.add((byte) Integer.parseInt(m.group(), 2));
		}
		// 准备将list转为byte数组 // 由于String构造器参数类型的限制
		byte[] b = new byte[list.size()]; // 开始转换
		for (int j = 0; j < b.length; j++) { //
			b[j] = list.remove(0); // 方法一 去掉泛型后这里会报错，因为取出的是Object类型
			b[j] = (Byte) list.remove(0); // 方法二 把Object强制转成Byte就可以了 }
			/*
			 * List.remove(int index)是将指定位置的元素删除, 然后右边所有剩下的数据向左移一位，填补第一个数据的空缺。
			 * remove(0)中0表示第一个元素，不停的调用remove(0)导致所有元素被删光，
			 * 剩下一个空集合。除了删除指定元素外，同时也具有返回值，就是被删掉的元素。
			 */// 将数组转换为String输出 // 故意不指定字符集(GBK)，让编绎器按系统默认打印
			System.out.println(new String(b));
		}
	}
}
