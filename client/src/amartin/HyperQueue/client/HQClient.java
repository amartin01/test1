package amartin.HyperQueue.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HQClient {

	private String session = "";
	
	public static void main(String[] args) {
		HQClient app = new HQClient();
		switch (args.length) {
		case 1:
				app.showlist(args[0]);
				break;
		case 2:
				app.messageLoop(args[0], args[1]);
				break;
		case 0:
		default:
				app.usage();
		}
	}
	
	private void usage() {
		System.out.println("Usage: HQClient [broker url [topic name]]");
		System.out.println("");
		System.out.println("broker url: full adress of broker server ex: http://localhost:8080/broker/");
		System.out.println("topic name: name of topic to fetch messages from");
		System.out.println("");
		System.out.println("with no parameter: show this usage help");
		System.out.println("");
		System.out.println("with only the broker url: shows a list of topic available");
		System.out.println("");
	}
	
	private void showlist(String url) {
		URLConnection conn = null;
		try {
			conn = new URL(url + "/topics").openConnection();
		} catch (IOException e) {
			System.out.println("Can't connect to: " + url);
			return;
		}
		
		conn.setRequestProperty("SESSION", "aaaaaaaaaaaaa");
		try {
			InputStream response = conn.getInputStream();
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            System.out.println(line);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void messageLoop(String urlAddress, String topic) {
		try {
			URL url = new URL(urlAddress + "/messages/" + topic);
			while (true) {
				String message = getMessage(url);
				System.out.println(message);
				System.console().readLine();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getMessage(URL url) {
		StringBuilder buffer = new StringBuilder();
		URLConnection conn = null;
 
		try {
			conn = url.openConnection();
			conn.setRequestProperty("SESSION", session);
			InputStream response;
			
			response = conn.getInputStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(response));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            buffer.append(line);
	        }
	        
	        session = conn.getHeaderField("SESSION");
	        response.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}

}
