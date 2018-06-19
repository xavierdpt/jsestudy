package xpdtr.acme.gui;

import java.awt.Component;

import javax.swing.JLabel;

public class ExceptionGui {

	public static Component render(Exception ex) {
		JLabel label = new JLabel(ex.getClass().getName() + " : " + ex.getMessage());
		return label;
	}

}
