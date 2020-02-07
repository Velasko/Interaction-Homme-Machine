package socket.firststep;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	public int port = 8000;
	public void run() {		
		ServerSocket serverSoc = null;
		try {
			serverSoc = new ServerSocket(port);
			
			while(true) {
//				System.out.println("before accept");
				Socket s = serverSoc.accept();
//				System.out.println("after accept");
				OutputStream os = null;
				InputStream is = null;
				try {
					os = s.getOutputStream();
					is = s.getInputStream();
//					BufferedOutputStream bos = new BufferedOutputStream(os);
//					DataOutputStream dos = new DataOutputStream(bos);
					byte[] b = new byte[100];
					int nb = is.read(b);
					String name = new String(b);
					
					byte[] msg = ("Hello, " + new String(name)).getBytes();

					os.write(msg);
					os.flush();

				} finally {
					os.close();
					is.close();
					s.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				System.out.println("serverSoc close()");
				serverSoc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
