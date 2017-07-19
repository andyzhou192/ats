package net.common.util;

/**
 * 
 * @author zhouyelin@chinamobile.com
 *
 */
public class ThreadUtil {

	public static void sleepInSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
