package xpdtr.acme.gui.interactions;

import javax.swing.JFrame;

public class Interacter {

	private JFrame frame;

	public Interacter(JFrame frame) {
		this.frame = frame;
	}

	public void perform(Runnable runnable) {
		runnable.run();
		frame.validate();
	}

}
