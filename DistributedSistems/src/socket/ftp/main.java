package socket.ftp;

public class main {
	public static void main(String argv[]) {
		int port = 8002;
		
		
		System.out.println("Starting");
		Server s = new Server();
		s.port = port;
		s.setDaemon(true);
		s.start();
		System.out.println("server created");

		Client c = new Client();
		c.port = port;
		c.start();

		try {
			c.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("finished main");
	}

}
