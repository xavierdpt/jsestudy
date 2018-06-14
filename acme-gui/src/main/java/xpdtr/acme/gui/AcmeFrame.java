package xpdtr.acme.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class AcmeFrame {

	private JFrame frame;

	private JPanel mainBoxPanel;

	private List<JPanel> things = new ArrayList<>();

	private JScrollPane scrollPane;

	private Dimension screenSize;

	public AcmeFrame() {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("xdptdr's Acme Maven Plugin Gui");

		Container contentPane = frame.getContentPane();
		contentPane.add(createAddButtons(), BorderLayout.PAGE_START);

		contentPane.add(createQuitButton(), BorderLayout.PAGE_END);
		contentPane.add(createMainScrollPane(), BorderLayout.CENTER);

		Dimension size = frame.getSize();
		size.setSize(screenSize.getWidth() * .9, screenSize.getHeight() * 0.9);
		frame.setSize(size);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

	private JPanel createAddButtons() {
		JPanel contentPane = new JPanel(new GridLayout(1, 2));
		contentPane.add(createAddButton1());
		contentPane.add(createAddButton2());
		return contentPane;
	}

	private JButton createAddButton1() {
		JButton button = new JButton("Add 1");
		addActionListener(button, (e) -> {
			addSomething1();
		});
		return button;
	}

	private JButton createAddButton2() {
		JButton button = new JButton("Add 2");
		addActionListener(button, (e) -> {
			addSomething2();
		});
		return button;
	}

	private JScrollPane createMainScrollPane() {
		mainBoxPanel = createMainPanel();
		scrollPane = new JScrollPane(mainBoxPanel);
		return scrollPane;
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainBoxPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(mainBoxPanel, BoxLayout.Y_AXIS);
		mainBoxPanel.setLayout(boxLayout);
		mainPanel.add(mainBoxPanel, BorderLayout.CENTER);
		return mainPanel;
	}

	private JButton createQuitButton() {
		JButton quitButton = new JButton("Quit");
		addActionListener(quitButton, (e) -> {
			quit();
		});
		;
		return quitButton;
	}

	private void addSomething1() {
		mainBoxPanel.add(createSomething(), mainBoxPanel.getComponentCount() - 1);
		mainBoxPanel.add(Box.createVerticalStrut(5), mainBoxPanel.getComponentCount() - 1);
		frame.revalidate();
	}

	private void addSomething2() {
		things.clear();
		mainBoxPanel.removeAll();
		mainBoxPanel.revalidate();
		mainBoxPanel.repaint();
		frame.revalidate();
	}

	private JPanel createSomething() {

		JLabel label = new JLabel("Label " + things.size());
		label.setFont(frame.getFont().deriveFont(Font.BOLD));

		JPanel textFieldPanel = new JPanel(new FlowLayout());

		JTextField textField = new JTextField();
		textFieldPanel.add(textField);

		JButton button = new JButton("Y");

		JPanel line1Panel = new JPanel();
		line1Panel.setLayout(new BoxLayout(line1Panel, BoxLayout.LINE_AXIS));
		line1Panel.add(label);
		line1Panel.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(Integer.MAX_VALUE, 0)));

		JPanel line2Panel = new JPanel();
		line2Panel.setLayout(new BoxLayout(line2Panel, BoxLayout.LINE_AXIS));
		line2Panel.add(textFieldPanel);
		line2Panel.add(Box.createHorizontalStrut(50));
		line2Panel.add(button);
		line2Panel.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(Integer.MAX_VALUE, 0)));

		JPanel bothPanels = new JPanel();
		bothPanels.setLayout(new BoxLayout(bothPanels, BoxLayout.PAGE_AXIS));
		bothPanels.add(line1Panel);
		bothPanels.add(line2Panel);

		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.LINE_AXIS));
		outerPanel.add(Box.createHorizontalStrut(10));
		outerPanel.add(bothPanels);
		outerPanel.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(Integer.MAX_VALUE, 0)));
		things.add(outerPanel);

		addActionListener(button, (e) -> {
			removeThing(outerPanel);
		});
		return outerPanel;
	}

	protected void removeThing(JPanel panel) {
		for (int i = 0; i < things.size(); ++i) {
			if (things.get(i) == panel) {
				things.set(i, null);
				panel.getParent().remove(panel);
			}
		}

		frame.revalidate();
	}

	private void quit() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	private void addActionListener(AbstractButton button, Consumer<ActionEvent> r) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				r.accept(e);
			}
		});
	}

}
