package net.common.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	
	/**
	 * ��ȡJSON�ַ�����ָ����JSON����
	 * ע�⣺������ֻ����Ը��ӽṹ��JSON
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static JSONObject getJSONObject(String jsonStr, String...keys){
		return (JSONObject) getFiled(jsonStr, 2, keys);
	}
	
	/**
	 * ��ȡJSON�ַ�����ָ����JSON��������
	 * ע�⣺������ֻ����Ը��ӽṹ��JSON
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static JSONArray getJSONArray(String jsonStr, String...keys){
		return (JSONArray) JsonUtil.getFiled(jsonStr, 3, keys);
	}

	/**
	 * ��ȡJSON�ַ�����ָ�����ֶ�ֵ
	 * ע�⣺������ֻ����Լ򵥵���ṹ��JSON,��keysΪ��ʱ���������ַ���ת���ĵ���JSON����
	 * @param jsonStr: json�ַ���������ΪJSON��ʽ
	 * @param flag: ���ؽ�����͡�
	 * 			0-�������JSON�ַ���ת����JSON����
	 * 			1-����JSON�ַ�����ָ�����ֶ�ֵ��
	 * 			2-����JSON�ַ�����ָ����JSON����
	 * 			3-����JSON�ַ�����ָ����JSON�������顿
	 * @param keys
	 * @return
	 */
	public static Object getFiled(String jsonStr, int flag, String...keys){
		JSONObject obj = StringUtil.convertToJson(jsonStr);
		int count = keys.length;
		try {
			switch (flag){
			case 0:
				if(count == 0){
					return obj;
				}else{
					LogUtil.err(JsonUtil.class, "����������󣬵�flag=0ʱ��keysӦ��Ϊ�գ����������ò���.");
				}
				break;
				
			case 1:
				if(count == 1){
					return obj.get(keys[0]);
				}else if(count>1){
					for(int i=0; i<count-1; i++){
						obj = obj.getJSONObject(keys[i]);
					}
					return obj.get(keys[count-1]);
				}else{
					LogUtil.err(JsonUtil.class, "����������󣬵�flag=1ʱ��keysӦ�ò�Ϊ�գ�������һ��ֵ.");
				}
				break;
				
			case 2:
				if(count == 1){
					return obj.getJSONObject(keys[0]);
				}else if(count>1){
					for(int i=0; i<count; i++){
						obj = obj.getJSONObject(keys[i]);
					}
					return obj;
				}else{
					LogUtil.err(JsonUtil.class, "����������󣬵�flag=2ʱ��keysӦ�ò�Ϊ�գ�������һ��ֵ.");
				}
				break;
				
			case 3:
				if(count == 1){
					return obj.getJSONArray(keys[0]);
				}else if(count>1){
					for(int i=0; i<count-1; i++){
						obj = obj.getJSONObject(keys[i]);
					}
					return obj.getJSONArray(keys[count-1]);
				}else{
					LogUtil.err(JsonUtil.class, "����������󣬵�flag=2ʱ��keysӦ�ò�Ϊ�գ�������һ��ֵ.");
				}
				break;
				
			default:
				LogUtil.err(JsonUtil.class, "flag�����������.");
			}
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}
