package server;

import java.awt.Frame;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerFrame extends Frame implements Application {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9101429101650600902L;
	private final ServerApplication server; 
	private final JTextField userText;
	private final JTextArea chatWindow;
	
	public ServerFrame() {
		this(ServerApplication.newInstance());
	}
	
	public ServerFrame(ServerApplication server) {
		this.server = server;
		this.userText = new JTextField();
		this.userText.setEditable(true);
		this.userText.addActionListener((e)-> {
			this.server.sendToServer(e.getActionCommand());
			this.userText.setText("");
		});
		this.chatWindow = new JTextArea();
		setSize(150, 300);
	}

	@Override
	public void start() {
		this.server.start();	
	}

	@Override
	public void running() {
		this.server.running();	
	}

	@Override
	public void stop() {
		this.server.stop();
	}

	@Override
	public boolean isStart() {
		return this.server.isStart();
	}

	@Override
	public boolean isRunning() {
		return this.server.isRunning();
	}

	@Override
	public boolean isStop() {
		return this.server.isStop();
	}	
}
