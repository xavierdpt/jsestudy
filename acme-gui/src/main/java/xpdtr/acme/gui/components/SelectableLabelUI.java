package xpdtr.acme.gui.components;

import javax.swing.JTextField;

public class SelectableLabelUI {

	public static JTextField render(String message) {
		JTextField component = new JTextField(message);
		component.setEditable(false);
		component.setBackground(null);
		component.setBorder(null);
		return component;
	}

}
