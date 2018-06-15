package xpdtr.acme.gui;

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
	 protected LayoutManager getLayout() {
		return new FullWidthStacked();
	}
	

}

