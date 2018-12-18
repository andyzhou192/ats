package net.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import net.common.Consts;

/**
 * 
 * @author sifuma@163.com
 *
 */
public class StringUtil {

	/**
	 * ��ָ���ַ���ת��ΪJSON��
	 * @param jsonStr
	 * @return JSON����
	 */
	public static JSONObject convertToJson(String jsonStr){
		//contactList = "{\"result\":{\"contact_count\":\"1\",\"contact_list\":[{\"lastModifiedTime\":\"2014-06-30 13:43:40\",\"createTime\":\"2014-06-30 13:43:40\",\"status\":0,\"dataFromFlag\":\"1\",\"groupMap\":[],\"givenName\":\"\u738b\u4fca\u5b87\",\"contactUserId\":\"1031853202\",\"contactId\":\"6612128302\",\"lastContactTime\":null,\"userId\":1031853202,\"name\":\"\u738b\u4fca\u5b87\",\"syncMobileFlag\":\"1\",\"groups\":[],\"mobile\":[\"18701257471\"]}]},\"id\":\"1404194084187\",\"jsonrpc\":\"2.0\"}";
		JSONObject json = null;
		try {
			json = new JSONObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * �ж�ָ���ַ����Ƿ�Ϊ�գ�null���մ������Ȳ�����0 ��Ϊ�գ���Ϊ���򷵻�true�����򷵻�false
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return (null == str || str.length() < 1) ? true : false;
	}
	
	/**
	 * �ж�ָ���ַ����Ƿ�Ϊnull��Ϊnull�򷵻�true�����򷵻�false
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		return null == str ? true : false;
	}
	
	/**
	 * �ַ�����ָ���ķָ�����װ
	 * @param join  �ָ���
	 * @param strings ����װ�ַ�������
	 * @return
	 */
	public static String joinStr(String joinSymbol, String... strings) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.length; ++i) {
			if (i == strings.length - 1) {
				sb.append(strings[i]);
			} else {
				sb.append(strings[i]).append(joinSymbol);
			}
		}
		return sb.toString();
	}

	/**
	 * �ַ�����ָ���ķָ�����װ
	 * @param join �ָ���
	 * @param params ����װ�ַ���map
	 * @return
	 */
	public static String joinStr(String joinSymbol, Map<String, String> params) {
		return joinStr(joinSymbol, params, true);
	}

	/**
	 * �ַ�����ָ���ķָ�����װ
	 * @param join  �ָ���
	 * @param params ����װ�ַ���map
	 * @param urlEncode �Ƿ����utf-8����
	 * @return
	 */
	public static String joinStr(String join, Map<String, String> params,
			boolean urlEncode) {
		StringBuilder sb = new StringBuilder();

		for (Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (value == null) {
				continue;
			}

			if (sb.length() > 0) {
				sb.append(join);
			}

			try {
				if (urlEncode) {
					sb.append(String.format("%s=%s", key,
							URLEncoder.encode(value, Consts.UTF8)));
				} else {
					sb.append(String.format("%s=%s", key, value));
				}
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getCause());
			}
		}

		return sb.toString();
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
