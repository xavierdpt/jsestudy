package xpdtr.acme.gui.fiddling;

import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JTextField;

import xpdtr.acme.gui.components.BasicFrameWithVerticalScroll;

public class TwoFullWidthTextAreas extends BasicFrameWithVerticalScroll {

	@Override
	protected void addComponents(JComponent scrollView, JComponent container2) {
		scrollView.add(new JTextField());
		scrollView.add(new JTextField());
	}

	@Override
	protected LayoutManager getLayout(Container target) {
		return new FullWidthStacked();
	}

}
