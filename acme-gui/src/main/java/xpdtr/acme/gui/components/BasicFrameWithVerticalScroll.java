package xpdtr.acme.gui.components;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JComponent;
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

	public BasicFrameWithVerticalScroll() {

	}

	public void init() {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("xdptdr's Acme Maven Plugin Gui");

		JPanel sessionScrollView = new JPanel();
		sessionScrollView.setLayout(getLayout(sessionScrollView));

		JPanel stateScrollView = new JPanel();
		stateScrollView.setLayout(getLayout(stateScrollView));

		addComponents(sessionScrollView, stateScrollView);

		sessionScrollPane = new JScrollPane(sessionScrollView);
		sessionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		stateScrollPane = new JScrollPane(stateScrollView);
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
		JScrollBar vsb = sessionScrollPane.getVerticalScrollBar();
		if (vsb != null) {
			vsb.setValue(vsb.getMaximum());
		}
	}

	abstract protected void addComponents(JComponent scrollView, JComponent container2);

	abstract protected LayoutManager getLayout(Container target);

}
