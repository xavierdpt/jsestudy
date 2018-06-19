package xpdtr.acme.gui.components;

import java.awt.Component;

import javax.swing.JLabel;

public class ExceptionUI {

	public static Component render(Exception ex) {
		JLabel label = new JLabel(ex.getClass().getName() + " : " + ex.getMessage());
		return label;
	}

}
