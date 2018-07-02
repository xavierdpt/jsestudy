package xpdtr.acme.gui.fiddling;

import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JTextField;

import xpdtr.acme.gui.components.BasicFrameWithVerticalScroll;

public class FullWidthTextAreaWithButtons extends BasicFrameWithVerticalScroll {

	protected void start() {
		container.add(new JTextField());
		container.add(new JButton("Hello"));
		container.add(new JButton("There"));
	}

	@Override
	protected LayoutManager getLayout(Container target) {
		return new FirstRemainingWidth();
	}
}
