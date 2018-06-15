package xpdtr.acme.gui;

import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FullWidthTextAreaWithButtons extends BasicFrameWithVerticalScroll {

	@Override
	protected void addComponents(JPanel scrollView) {
		scrollView.add(new JTextField());
		scrollView.add(new JButton("Hello"));
		scrollView.add(new JButton("There"));
	}

	@Override
	protected LayoutManager getLayout() {
		return new FirstRemainingWidth();
	}
}
