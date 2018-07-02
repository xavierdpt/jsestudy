package xpdtr.acme.gui.utils;

import java.util.function.Consumer;

public class Promise<T> {

	private Consumer<T> handler;
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

	public void then(Consumer<T> handler) {
		this.handler = handler;
		thread.start();
	}

	public void done(T response) {
		handler.accept(response);
	}

	public Promise<T> setThread(Thread thread) {
		this.thread = thread;
		return this;
	}

}
