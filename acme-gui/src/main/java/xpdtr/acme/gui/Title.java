package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Title {

	public static Component create() {
		JLabel label = new JLabel("xdptdr's Acme GUI", SwingConstants.CENTER);
		U.setFont(label, Font.BOLD, 1.3);
		U.setMargins(label, 0, 20);

		JPanel panel = new JPanel(new FullWidthLayout());
		panel.add(label);
		return panel;
	}

}
