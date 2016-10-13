package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Servlet implements Callable<Void> {
	
	private boolean isRunning = true;
	
	private final Socket connection;
	
	private final Server server;
	
	private Queue<String> messages = new ConcurrentLinkedQueue<>();
	
	private BufferedReader reader;
	
	private BufferedWriter writer;
	
	public Servlet(Socket connection, Server server) {
		this.connection = connection;
		this.server = server;
	}
	
	@Override
	public Void call() {
		start();
		try {
			while (this.isRunning == true) {
				running();
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			stop();
		}
		
		return null;
	}
	
	public void addToMessageQueue(String message) {
		this.messages.add(message);
	}

	private void start() {
		try {
			this.reader = new BufferedReader(
					new InputStreamReader(this.connection.getInputStream(), StandardCharsets.UTF_8));
			this.writer = new BufferedWriter(
					new OutputStreamWriter(this.connection.getOutputStream(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void running() throws IOException {
		if (this.messages.isEmpty() == false) {
			this.writer.write(this.messages.poll());
		}
		String message = this.reader.readLine();
		
		if (message != null) {
			server.sendToServer(message);
		}
	}

	private void stop() {
		this.isRunning = false;
		try {
			this.writer.close();
			this.reader.close();
			this.connection.close();
			this.server.closeServlet(this);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
