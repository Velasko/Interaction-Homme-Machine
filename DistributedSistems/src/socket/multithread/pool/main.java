package socket.multithread.pool;

public class main {
	public static void main(String argv[]) {
		int port = 8002;
		int clients = 5;
				
		System.out.println("Starting");
		Server s = new Server(2, 3);
		s.port = port;
		s.setDaemon(true);
		s.start();
		System.out.println("server created");

		Client[] cvec = new Client[clients];
		for(int i = 0; i < clients; i++) {
			Client c = new Client();
			c.port = port;
			c.id = i;
			cvec[i] = c;
			c.start();
		}
			
		try {
			for(int i = 0; i < clients; i++)
				cvec[i].join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("finished main");
	}

}
