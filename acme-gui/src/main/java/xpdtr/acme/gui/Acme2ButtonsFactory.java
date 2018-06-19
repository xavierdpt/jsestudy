package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Acme2ButtonsFactory {

	private boolean nonceEnabled;
	private boolean accountEnabled;
	private boolean orderEnabled;
	private boolean changeKeyEnabled;
	private boolean revokeCertEnabled;

	private Runnable nonceClicked;
	private Runnable accountClicked;
	private Runnable orderClicked;
	private Runnable changeKeyClicked;
	private Runnable revokeCertClicked;

	public void setNonceEnabled(boolean nonceEnabled) {
		this.nonceEnabled = nonceEnabled;
	}

	public void setAccountEnabled(boolean accountEnabled) {
		this.accountEnabled = accountEnabled;
	}

	public void setOrderEnabled(boolean orderEnabled) {
		this.orderEnabled = orderEnabled;
	}

	public void setChangeKeyEnabled(boolean changeKeyEnabled) {
		this.changeKeyEnabled = changeKeyEnabled;
	}

	public void setRevokeCertEnabled(boolean revokeCertEnabled) {
		this.revokeCertEnabled = revokeCertEnabled;
	}

	public void setNonceClicked(Runnable nonceClicked) {
		this.nonceClicked = nonceClicked;
	}

	public void setAccountClicked(Runnable accountClicked) {
		this.accountClicked = accountClicked;
	}

	public void setOrderClicked(Runnable orderClicked) {
		this.orderClicked = orderClicked;
	}

	public void setChangeKeyClicked(Runnable changeKeyClicked) {
		this.changeKeyClicked = changeKeyClicked;
	}

	public void setRevokeCertClicked(Runnable revokeCertClicked) {
		this.revokeCertClicked = revokeCertClicked;
	}

	public Component create() {

		JButton nonce = new JButton("New Nonce");
		nonce.setEnabled(nonceEnabled);
		clicked(nonce, nonceClicked);

		JButton account = new JButton("New Account");
		account.setEnabled(accountEnabled);
		clicked(account, accountClicked);

		JButton order = new JButton("New Order");
		order.setEnabled(orderEnabled);
		clicked(order, orderClicked);

		JButton changeKey = new JButton("Change Key");
		changeKey.setEnabled(changeKeyEnabled);
		clicked(changeKey, changeKeyClicked);

		JButton revokeCert = new JButton("Revoke Cert");
		revokeCert.setEnabled(revokeCertEnabled);
		clicked(revokeCert, revokeCertClicked);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttons.add(nonce);
		buttons.add(account);
		buttons.add(order);
		buttons.add(changeKey);
		buttons.add(revokeCert);

		return buttons;
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
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (consumer != null) {
					consumer.run();
				}
				disableAll(e.getSource());
			}

		});
	}

}
