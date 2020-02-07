package socket.multithread.simple;

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
				ServerThread t = new ServerThread(serverSoc.accept());
				t.setDaemon(true);
				t.start();
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
