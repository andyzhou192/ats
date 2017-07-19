package net.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecretUtil {
	
	private static String[] letter = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
			"w", "x", "y", "z" };
			
	public static String TelEncrypt(String tel) {
		int telLength = tel.length();
		if(telLength >= 11) {
			String telHeader = tel.substring(0, telLength - 11);
			String telEncrypt = tel.substring(telLength - 11, telLength);
			if(isNumeric(telEncrypt)) {
				int head = Integer.parseInt(String.valueOf(telEncrypt.charAt(10)));
				String num = String.valueOf(telEncrypt.charAt(head)) + String.valueOf(telEncrypt.charAt(10));
				int res = Integer.parseInt(num) % 17;
				String[] des = new String[10];
				System.arraycopy(letter, res, des, 0, des.length);
				StringBuffer buffer = new StringBuffer(telEncrypt);
				for(int i = 0; i < telEncrypt.length(); i++) {
					if(head != i && 10 != i) {
						buffer.replace(i, i + 1, des[Integer.parseInt(String.valueOf(telEncrypt.charAt(i)))]);
					}
					else {
						buffer.replace(i, i + 1, letter[Integer.parseInt(String.valueOf(telEncrypt.charAt(i)))]);
					}
				}
				telEncrypt = buffer.toString();
			}
			
			tel = telHeader + telEncrypt;
		}
		return tel;
	}
	
	public static String TelDecrypt(String tel) {
		int telLength = tel.length();
		if(telLength >= 11) {
			String telHeader = tel.substring(0, telLength - 11);
			String telDecrypt = tel.substring(telLength - 11, telLength);
			
			if(isLetter(telDecrypt)) {
				List<String> lista = new ArrayList<String>();
				for(int i = 0; i < letter.length; i++) {
					lista.add(letter[i]);
				}
				int head = lista.indexOf(String.valueOf(telDecrypt.charAt(10)));
				int res = Integer.parseInt(String.valueOf(lista.indexOf(String.valueOf(telDecrypt.charAt(head))))
						+ String.valueOf(lista.indexOf(String.valueOf(telDecrypt.charAt(10))))) % 17;
				String[] des = new String[10];
				System.arraycopy(letter, res, des, 0, des.length);
				List<String> listDes = new ArrayList<String>();
				for(int i = 0; i < des.length; i++) {
					listDes.add(des[i]);
				}
				StringBuffer buffer = new StringBuffer(telDecrypt);
				for(int i = 0; i < telDecrypt.length(); i++) {
					if(head != i && 10 != i) {
						buffer.replace(i, i + 1, String.valueOf(listDes.indexOf(String.valueOf(telDecrypt.charAt(i)))));
					}
					else {
						buffer.replace(i, i + 1, String.valueOf(lista.indexOf(String.valueOf(telDecrypt.charAt(i)))));
					}
					
				}
				telDecrypt = buffer.toString();
				
				if(isNumeric(telDecrypt)) {
					tel = telHeader + telDecrypt;
				}
			}
		}
		return tel;
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public static boolean isLetter(String str) {
		Pattern pattern = Pattern.compile("[a-z]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public static String sendIdMix(String sendId) {
		if(null != sendId && !sendId.trim().equals("")) {
			sendId = sendId.trim();
			int length = sendId.length();
			if(length >= 11) {
				sendId = SecretUtil.TelDecrypt(sendId);
				int start = length - 8;
				String startStr = sendId.substring(0, start);
				String endStr = sendId.substring(start + 5);
				sendId = startStr + "*****" + endStr;
			}
		}
		return sendId;
	}
}
