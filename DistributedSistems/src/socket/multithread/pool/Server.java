package socket.multithread.pool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	public int port = 8000;
	public SocketBuffer buffer;
	
	public Server(int pool, int bfsize) {
		ServerThread[] threadpool = new ServerThread[pool];
		buffer = new SocketBuffer(bfsize);
		for(int i = 0; i < pool; i++) {
			threadpool[i] = new ServerThread(buffer);
			threadpool[i].setDaemon(true);
			threadpool[i].start();
		}
	}
	
	public void run() {
		ServerSocket serverSoc = null;
		try {
			serverSoc = new ServerSocket(port);
			Socket s = null;
			while(true) {
				s = serverSoc.accept();
				buffer.put(s);
			}
		} catch (Exception e) {
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
