package xpdtr.acme.gui.components;

import java.awt.Container;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import xpdtr.acme.gui.utils.U;

public class UILogger {

	private Container container;
	private Container destination;

	public UILogger(Container container) {
		this.container = container;
		this.destination = container;
	}

	public void beginGroup(String title) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		TitledBorder bf = BorderFactory.createTitledBorder(title);
		bf.setTitleFont(bf.getTitleFont().deriveFont(Font.BOLD));
		panel.setBorder(bf);
		destination = panel;
	}

	public void important(String message) {
		JTextField label = MessageUI.render(message);
		U.setFont(label, Font.BOLD);
		destination.add(label);
	}

	public void message(String message) {
		destination.add(MessageUI.render(message));
	}
	
	public void endGroup() {
		container.add(destination);
		destination=container;
	}

}
