package socket.ftp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
