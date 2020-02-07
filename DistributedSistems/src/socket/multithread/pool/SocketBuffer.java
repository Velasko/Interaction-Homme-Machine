package socket.multithread.pool;

import java.net.Socket;
import java.util.concurrent.Semaphore;

public class SocketBuffer {

	Semaphore sput, sget;
	int getP; //indice du compteur get
	int putP; //indice du compteur put "(put >= get)"
	int totalMsg;
	int bufsz; //size of the buffer
	Socket[] tableau;
	
	public SocketBuffer(int bufsz) {
		sput = new Semaphore(bufsz);
		sget = new Semaphore(bufsz);
		getP = 0;
		putP = 0;
		tableau = new Socket[bufsz];
		this.bufsz = bufsz;
		
		for(int i=0; i < bufsz; i++) {
			try {
				sget.acquire();
			} catch (InterruptedException e) {}
		}
	}

	public void put(Socket s) throws InterruptedException {
		sput.acquire();
		synchronized(this){
			tableau[putP++] = s;
			putP %= bufsz;
			totalMsg++;
		}
		sget.release();
	}

	public Socket get() throws InterruptedException {
		Socket msg;
		sget.acquire();
		synchronized(this){
			msg = tableau[getP++];
			getP %= bufsz;
		}
		sput.release();
		return msg;
	}

	synchronized public int nmsg() {
		return putP - getP;
	}

	public int totmsg() {
		return totalMsg;
	}

}
