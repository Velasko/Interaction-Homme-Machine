package socket.multithread.pool;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client extends Thread{
	private Socket s;
	public int port = 8000;
	public int id = 0;
	
	public void run() {
		String host = "localhost";

		OutputStream os = null;
		InputStream is = null;
		try {
			s = new Socket(host, port);
			System.out.println("(client) connected");
			os = s.getOutputStream();
			is = s.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			
			//get file size
			int size = dis.readInt();
			
			//get file bytes
			byte[] b = new byte[size];
			int offset = 0;
			int nb = 0;
			while(offset < size) {
				nb = dis.read(b, offset, size-offset);
				offset += nb;
			}
			Files.write(Paths.get("/tmp/repartie/client/test" + id + ".pdf"), b);
			
			System.out.println("(client) got file " + size);
			
			byte[] msg = ("(client " + id + ") got " + size + " bytes").getBytes();
			
			os.write(msg);
			os.flush();
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
