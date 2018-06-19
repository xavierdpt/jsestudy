package xpdtr.acme.gui.components;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;

public class NonceUI {

	public static Component renderGetting() {
		JLabel label = new JLabel("Getting new nonce... ");
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		return label;
	}

	public static JLabel renderSuccess(String nonce) {
		JLabel label = new JLabel(nonce);
		return label;

	}

	public static JLabel renderFailure(Exception exception) {
		JLabel label = new JLabel(exception.getClass().getName() + " : " + exception.getMessage());
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		return label;

	}

}
