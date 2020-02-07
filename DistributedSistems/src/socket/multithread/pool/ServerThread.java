package socket.multithread.pool;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServerThread extends Thread{
	private SocketBuffer b;

	public ServerThread(SocketBuffer b) {
		this.b = b;
	}

	public void run() {
		while(true) {
			OutputStream os = null;
			InputStream is = null;
			Socket s = null;
			try {
				s = b.get();
				os = s.getOutputStream();
				is = s.getInputStream();

				byte[] b = Files.readAllBytes(Paths.get("/tmp/repartie/server/test.pdf"));

				DataOutputStream socketWriter = new DataOutputStream(os);
				socketWriter.writeInt(b.length);
				socketWriter.write(b, 0, b.length);
				socketWriter.flush();

				System.out.println("(server) file sent");

				byte[] b2 = new byte[100];
				is.read(b2);
				String reply = new String(b2);
				System.out.println("(server) recieved: " + reply);

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

}
