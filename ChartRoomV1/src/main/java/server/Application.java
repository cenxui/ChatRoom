package server;

interface Application {

	void start();

	void running();

	void stop();

	boolean isStart();

	boolean isRunning();

	boolean isStop();
}
