package xpdtr.acme.gui.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Acme2Buttons {

	private boolean nonceEnabled;
	private boolean createAccountEnabled;
	private boolean orderEnabled;
	private boolean changeKeyEnabled;
	private boolean revokeCertEnabled;
	private boolean accountDetailsEnabled;

	private Runnable nonceClicked;
	private Runnable createAccountClicked;
	private Runnable orderClicked;
	private Runnable changeKeyClicked;
	private Runnable revokeCertClicked;
	private Runnable accountDetailsClicked;

	public void setNonceEnabled(boolean enabled) {
		this.nonceEnabled = enabled;
	}

	public void setCreateAccountEnabled(boolean enabled) {
		this.createAccountEnabled = enabled;
	}

	public void setOrderEnabled(boolean enabled) {
		this.orderEnabled = enabled;
	}

	public void setChangeKeyEnabled(boolean enabled) {
		this.changeKeyEnabled = enabled;
	}

	public void setRevokeCertEnabled(boolean enabled) {
		this.revokeCertEnabled = enabled;
	}

	public void setAccountDetailsEnabled(boolean enabled) {
		this.accountDetailsEnabled = enabled;
	}

	public void setNonceClicked(Runnable handler) {
		this.nonceClicked = handler;
	}

	public void setCreateAccountClicked(Runnable handler) {
		this.createAccountClicked = handler;
	}

	public void setOrderClicked(Runnable handler) {
		this.orderClicked = handler;
	}

	public void setChangeKeyClicked(Runnable handler) {
		this.changeKeyClicked = handler;
	}

	public void setRevokeCertClicked(Runnable handler) {
		this.revokeCertClicked = handler;
	}

	public void setAccountDetailsClicked(Runnable handler) {
		this.accountDetailsClicked = handler;
	}

	public Component create() {

		JButton nonce = new JButton("New Nonce");
		nonce.setEnabled(nonceEnabled);
		clicked(nonce, nonceClicked);

		JButton account = new JButton("New Account");
		account.setEnabled(createAccountEnabled);
		clicked(account, createAccountClicked);

		JButton order = new JButton("New Order");
		order.setEnabled(orderEnabled);
		clicked(order, orderClicked);

		JButton changeKey = new JButton("Change Key");
		changeKey.setEnabled(changeKeyEnabled);
		clicked(changeKey, changeKeyClicked);

		JButton revokeCert = new JButton("Revoke Cert");
		revokeCert.setEnabled(revokeCertEnabled);
		clicked(revokeCert, revokeCertClicked);

		JButton accountDetails = new JButton("Account Details");
		accountDetails.setEnabled(accountDetailsEnabled);
		clicked(accountDetails, accountDetailsClicked);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttons.add(nonce);
		buttons.add(account);
		buttons.add(order);
		buttons.add(changeKey);
		buttons.add(revokeCert);
		buttons.add(accountDetails);

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