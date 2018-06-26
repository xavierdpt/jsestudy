package xpdtr.acme.gui.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Acme2Buttons {

	public enum Action {

		NONCE("New Nonce"),

		CREATE_ACCOUNT("New Account"),

		ORDER("New Order"),

		CHANGE_KEY("Change Key"),

		REVOKE_CERT("Revoke Cert"),

		ACCOUNT_DETAILS("Account Details"),

		AUTHORIZATION_DETAILS("Authorization details"),

		CHALLENGE("Challenge"),

		CREATE_KEY_PAIR("Create new key pair"),

		SAVE_KEY_PAIR("Save key pair"),

		LOAD_KEY_PAIR("Load key pair");

		private String label;

		private Action(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

	};

	private boolean created;

	Map<Action, Boolean> enabled = new HashMap<>();
	Map<Action, Runnable> clicked = new HashMap<>();
	Map<Action, JButton> buttons = new HashMap<>();

	public void setEnabled(Action action, boolean value) {
		enabled.put(action, value);
	}

	public void setClicked(Action action, Runnable handler) {
		clicked.put(action, handler);
	}

	public Component render() {

		JPanel panel = null;
		if (!created) {

			panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			for (Action action : Action.values()) {
				JButton button = new JButton(action.getLabel());
				buttons.put(action, button);
				panel.add(button);
			}
			created = true;
		}

		for (Action action : Action.values()) {
			if (buttons.containsKey(action)) {
				boolean e = Boolean.TRUE.equals(enabled.get(action)) && clicked.containsKey(action)
						&& clicked.get(action) != null;
				buttons.get(action).setEnabled(e);
				if (clicked.containsKey(action)) {
					clicked(this.buttons.get(action), clicked.get(action));
				}
			}
		}

		return panel;
	}

	private void disableAll(Object object) {
		Container container = ((Component) object).getParent();
		int n = container.getComponentCount();
		for (int i = 0; i < n; ++i) {
			Component component = container.getComponent(i);
			if (component instanceof JButton) {
				JButton button = (JButton) component;
				button.setEnabled(false);
			}
		}
	}

	private void clicked(JButton button, Runnable consumer) {

		for (ActionListener listener : button.getActionListeners()) {
			button.removeActionListener(listener);
		}

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAll(e.getSource());
				if (consumer != null) {
					consumer.run();
				}
			}

		});
	}

}
