package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApplication implements Application, Server {

	private final ExecutorService service = Executors.newFixedThreadPool(200);

	private SocketChannel server;

	private boolean isRunning = true;

	public final static ServerApplication newInstance() {
		return new ServerApplication();
	}

	private ServerApplication() {

	}

	@Override
	public void start() {
		try {
			this.server = SocketChannel.open();
			this.server.bind(new InetSocketAddress(6789));
			this.server.configureBlocking(false);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void running() {
		while (isRunning) {
			Socket connection = this.server.socket();
			if (connection != null) {
				Servlet servlet = new Servlet(connection, this);
				Future<Void> future = this.service.submit(servlet);
				
			}
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isStart() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStop() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendToServer(String message) {
		
		
	}

	@Override
	public void closeServlet(Servlet servlet) {
		// TODO Auto-generated method stub
		
	}
}
