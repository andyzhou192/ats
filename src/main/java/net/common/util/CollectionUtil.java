package net.common.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionUtil {

	/**
	 * ��listת��������
	 * @param list
	 * @return
	 */
	public static String[] listToStrArray(List<?> list){
		if(null == list || list.size()<1){
			return new String[0];
		}
		String[] array = new String[list.size()];
		for(int i = 0; i < list.size(); i++){
			array[i] = list.get(i).toString();
		}
		return array;
	}
	
	/**
	 * ��ȡmap������key
	 * @param map
	 * @return
	 */
	public static String[] getMapKeys(Map<String, ?> map){
		if(null == map){
			return null;
		}
		String[] array = new String[map.size()];
		Iterator<String> it = map.keySet().iterator();
		int index = 0;
		while (it.hasNext()){
			array[index] = it.next();
			index++;
		}
		return array;
	}
}
