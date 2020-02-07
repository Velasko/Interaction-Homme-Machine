package socket.multithread.simple;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.ByteBuffer;

public class ServerThread extends Thread{
	private Socket s;

	public ServerThread(Socket s) {
		this.s = s;
	}
	
	public void run() {
		OutputStream os = null;
		InputStream is = null;
		try {
			os = s.getOutputStream();
			is = s.getInputStream();

			byte[] b = Files.readAllBytes(Paths.get("/tmp/repartie/server/test.pdf"));
			
			DataOutputStream socketWriter = new DataOutputStream(os);
			socketWriter.writeInt(b.length);
			socketWriter.write(b, 0, b.length);
			socketWriter.flush();
			
			System.out.println("(server) msg sent");

			byte[] b2 = new byte[100];
			int nb = is.read(b2);
			String reply = new String(b2);
			System.out.println("(server) client says \"" + reply + "\"");

		}catch( Exception e) {
			e.printStackTrace();	
		} finally {
			try {
				os.close();
				is.close();
				s.close();
			}catch(Exception e) {
				e.printStackTrace();	
			}
		}
	}

}
