package xpdtr.acme.gui.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

	public static void addM(Container container, List<Component> components) {
		for (Component component : components) {
			container.add(component);
		}
	}

	public static void addM(Container container, Component component) {
		container.add(component);
	}

	public static void clicked(JButton button, Consumer<ActionEvent> consumer) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				consumer.accept(event);
			}
		});
	}

	public static Runnable disabler(Component... components) {
		return () -> {
			for (Component component : components) {
				component.setEnabled(false);
			}
		};
	}
}
