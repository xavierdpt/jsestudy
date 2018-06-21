package xpdtr.acme.gui.interactions;

import java.awt.Container;

public abstract class UIInteraction {

	protected Container container;
	private Runnable validate;
	private Runnable finished;

	public UIInteraction(Container container, Runnable validate, Runnable finished) {
		this.container = container;
		this.validate = validate;
		this.finished = finished;
	}

	public abstract void start();

	protected final void validate() {
		validate.run();
	}

	protected final void finished() {
		finished.run();
	}

}
