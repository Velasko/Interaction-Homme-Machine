package socket.firststep;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
	private Socket s;
	public int port = 8000;
	
	public void run() {
		String host = "localhost";

		OutputStream os = null;
		InputStream is = null;
		try {
			s = new Socket(host, port);
			System.out.println("(client) connected");
			os = s.getOutputStream();
			is = s.getInputStream();
			
			byte[] msg = ("myClient").getBytes();
			
			os.write(msg);
			os.flush();
			
			System.out.println("(client) msg sent");

			byte[] b = new byte[100];
			int nb = is.read(b);
			String reply = new String(b);
			System.out.println("(client) Host says \"" + reply + "\"");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
