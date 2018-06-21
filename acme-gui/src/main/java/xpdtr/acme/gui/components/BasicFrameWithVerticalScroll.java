package xpdtr.acme.gui.components;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public abstract class BasicFrameWithVerticalScroll {

	private JFrame frame;

	private Dimension screenSize;

	private JScrollPane scrollPane;

	protected Container contentPane;

	public BasicFrameWithVerticalScroll() {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("xdptdr's Acme Maven Plugin Gui");

		JPanel scrollView = new JPanel();
		scrollView.setLayout(getLayout(scrollView));

		addComponents(scrollView);

		scrollPane = new JScrollPane(scrollView);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(scrollPane, BorderLayout.CENTER);
		Dimension size = frame.getSize();
		size.setSize(screenSize.getWidth() * .9, screenSize.getHeight() * 0.9);
		frame.setSize(size);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

	public final void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	protected final void validate() {
		frame.validate();
		JScrollBar vsb = scrollPane.getVerticalScrollBar();
		if (vsb != null) {
			vsb.setValue(vsb.getMaximum());
		}
	}

	abstract protected void addComponents(JPanel scrollView);

	abstract protected LayoutManager getLayout(Container target);

}
