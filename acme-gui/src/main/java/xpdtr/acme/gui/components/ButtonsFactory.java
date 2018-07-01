package xpdtr.acme.gui.components;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import xpdtr.acme.gui.Buttons;

public class ButtonsFactory {

	Map<AcmeGuiActions, Runnable> clicked = new HashMap<>();
	Map<AcmeGuiActions, JButton> buttons = new HashMap<>();

	public void setClicked(AcmeGuiActions action, Runnable handler) {
		clicked.put(action, handler);
	}

	private void disableAll() {
		buttons.forEach((a, b) -> {
			b.setEnabled(false);
		});
	}

	private void clicked(JButton button, Runnable consumer) {
		if (consumer != null) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					disableAll();
					consumer.run();
				}
			});
		}
	}

	public Buttons render(Container contentPane) {

		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		JPanel panel = new JPanel(layout);

		for (AcmeGuiActions action : AcmeGuiActions.values()) {
			JButton button = new JButton(action.getLabel());
			clicked(button, clicked.get(action));
			buttons.put(action, button);
			panel.add(button);
		}
		
		panel.setMaximumSize(new Dimension(contentPane.getWidth(), Integer.MAX_VALUE));

		contentPane.add(panel, BorderLayout.SOUTH);

		return new Buttons(buttons);
	}

}
