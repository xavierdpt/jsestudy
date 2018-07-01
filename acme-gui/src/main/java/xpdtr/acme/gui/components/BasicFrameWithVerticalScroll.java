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
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

public abstract class BasicFrameWithVerticalScroll {

	private JFrame frame;

	private Dimension screenSize;

	private JScrollPane sessionScrollPane;
	protected JScrollPane stateScrollPane;

	protected Container contentPane;

	protected JPanel sessionContainer;
	protected JPanel stateContainer;

	public BasicFrameWithVerticalScroll() {

	}

	public void init() {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("xdptdr's Acme Maven Plugin Gui");

		sessionContainer = new JPanel();
		sessionContainer.setLayout(getLayout(sessionContainer));

		stateContainer = new JPanel();
		stateContainer.setLayout(getLayout(stateContainer));

		sessionScrollPane = new JScrollPane(sessionContainer);
		sessionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		stateScrollPane = new JScrollPane(stateContainer);
		stateScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Session", null, sessionScrollPane, "Session");
		tabbedPane.addTab("State", null, stateScrollPane, "State");

		contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(tabbedPane, BorderLayout.CENTER);
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
	}

	protected void autoscroll() {
		JScrollBar vsb = sessionScrollPane.getVerticalScrollBar();
		if (vsb != null) {
			vsb.setValue(vsb.getMaximum());
		}
	}

	abstract protected LayoutManager getLayout(Container target);

	protected final JFrame getFrame() {
		return frame;
	}
}
