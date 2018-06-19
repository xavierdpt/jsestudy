package xpdtr.acme.gui.components;

import java.awt.Component;

import javax.swing.JLabel;

public class MessageUI {

	public static Component render(String message) {
		return new JLabel(message);
	}

}
