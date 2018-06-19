package xpdtr.acme.gui;

import java.util.function.Consumer;

public class Promise<T> {

	private Consumer<T> successHandler;
	private Consumer<Exception> failureHandler;
	private Thread thread;

	public void then(Consumer<T> successHandler, Consumer<Exception> failureHandler) {
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
		thread.start();
	}

	public void success(T response) {
		successHandler.accept(response);
	}

	public void failure(Exception e) {
		failureHandler.accept(e);
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

}
