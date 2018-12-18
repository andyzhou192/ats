package example.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import net.ats.api.protocol.httpclient.HttpClientImpl;

public class HttpClientTest {
	public static void main(String[] args) throws FileNotFoundException {
		try {
			String uri = "http://10.10.10.10/ems/new/getEnterprise";
			String headers = "Connection: keep-alive\r\nContent-Type: application/x-www-form-urlencoded\r\nContent-Length: 52\r\nHost: 10.10.10.10\r\nUser-Agent: Apache-HttpClient/4.5.2 (Java/1.8.0_111)";
			String params = "loginName=12345678&loginPassword=123456789&type=1";
			HttpClientImpl client = new HttpClientImpl(uri, headers, params);
			HttpResponse rsp = client.doPost();
			System.out.println(rsp.getStatusLine().getStatusCode() + ":" + rsp.getStatusLine().getReasonPhrase());
			System.out.println(EntityUtils.toString(rsp.getEntity()));
			System.out.println(client.getCookieStore().getCookies());
			
			String url = "http://10.10.10.10/ems/new/loginPson";
			String header = "Content-Type:application/json;charset=UTF-8";
			String param = "enterpriseCode=1234";
			HttpClientImpl client2 = new HttpClientImpl(url, header, param, client.getCookieStore());
			HttpResponse rsp2 = client2.doGet();
			System.out.println(rsp2.getStatusLine().getStatusCode() + ":" + rsp2.getStatusLine().getReasonPhrase());
			System.out.println(EntityUtils.toString(rsp2.getEntity()));
			System.out.println(client2.getCookieStore().getCookies());
			
			client.close();
			client2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
