package xpdtr.acme.gui.utils;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class U {

	private U() {
	}

	public static void setFont(Component component, int style) {
		component.setFont(component.getFont().deriveFont(style));
	}
	
	public static void setFont(Component component, int style, double multiplier) {
		Font font = component.getFont();
		font = font.deriveFont(style);
		font = font.deriveFont((float) (font.getSize2D() * multiplier));
		component.setFont(font);
	}

	public static void setMargins(JComponent component, int h, int v) {
		component.setBorder(BorderFactory.createEmptyBorder(v, h, v, h));
	}

}
