package xpdtr.acme.gui.fiddling;

import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class TwoFullWidthTextAreas extends BasicFrameWithVerticalScroll {

	@Override
	 protected void addComponents(JPanel scrollView) {
		scrollView.add(new JTextField());
		scrollView.add(new JTextField());
	}

	@Override
	 protected LayoutManager getLayout(Container target) {
		return new FullWidthStacked();
	}
	

}

