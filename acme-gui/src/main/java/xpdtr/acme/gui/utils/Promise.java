package xpdtr.acme.gui.utils;

import java.util.function.Consumer;

public class Promise<T> {

	private Consumer<T> successHandler;
	private Consumer<Exception> failureHandler;
	private Thread thread;

	public Promise() {
	}

	public Promise(Thread thread) {
		this.thread = thread;
	}

	public Promise(Runnable runnable) {
		this.thread = new Thread(runnable);
	}

	public Promise(Consumer<Promise<T>> consumer) {
		this.thread = new Thread(() -> {
			consumer.accept(this);
		});
	}

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

	public Promise<T> setThread(Thread thread) {
		this.thread = thread;
		return this;
	}

}
