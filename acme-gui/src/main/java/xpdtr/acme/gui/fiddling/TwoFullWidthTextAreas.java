package xpdtr.acme.gui.fiddling;

import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.JTextField;

import xpdtr.acme.gui.components.BasicFrameWithVerticalScroll;

public class TwoFullWidthTextAreas extends BasicFrameWithVerticalScroll {

	protected void start() {
		container.add(new JTextField());
		container.add(new JTextField());
	}

	@Override
	protected LayoutManager getLayout(Container target) {
		return new FullWidthStacked();
	}

}
