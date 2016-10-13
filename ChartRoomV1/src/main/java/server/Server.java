package server;

public interface Server {
	void sendToServer(String message);
	void closeServlet(Servlet servlet);
}
