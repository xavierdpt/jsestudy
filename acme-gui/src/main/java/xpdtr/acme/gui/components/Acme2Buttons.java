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
	private boolean authorizationDetailsEnabled;
	private boolean challengeEnabled;

	private Runnable nonceClicked;
	private Runnable createAccountClicked;
	private Runnable orderClicked;
	private Runnable changeKeyClicked;
	private Runnable revokeCertClicked;
	private Runnable accountDetailsClicked;
	private Runnable authorizationDetailsClicked;
	private Runnable challengeClicked;

	private JButton nonce;
	private JButton account;
	private JButton order;
	private JButton changeKey;
	private JButton revokeCert;
	private JButton accountDetails;
	private JButton authorizationDetails;
	private JButton challenge;

	private boolean created;

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

	public void setAuthorizationDetailsEnabled(boolean authorizationDetailsEnabled) {
		this.authorizationDetailsEnabled = authorizationDetailsEnabled;
	}

	public void setChallengeEnabled(boolean challengeEnabled) {
		this.challengeEnabled = challengeEnabled;
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

	public void setAuthorizationDetailsClicked(Runnable authorizationDetailsClicked) {
		this.authorizationDetailsClicked = authorizationDetailsClicked;
	}

	public void setChallengeClicked(Runnable challengeClicked) {
		this.challengeClicked = challengeClicked;
	}

	public Component render() {

		JPanel buttons = null;
		if (!created) {
			nonce = new JButton("New Nonce");
			account = new JButton("New Account");
			order = new JButton("New Order");
			changeKey = new JButton("Change Key");
			revokeCert = new JButton("Revoke Cert");
			accountDetails = new JButton("Account Details");
			authorizationDetails = new JButton("Authorization details");
			challenge = new JButton("Challenge");

			buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
			buttons.add(nonce);
			buttons.add(account);
			buttons.add(order);
			buttons.add(changeKey);
			buttons.add(revokeCert);
			buttons.add(accountDetails);
			buttons.add(authorizationDetails);
			buttons.add(challenge);
			created = true;
		}

		nonce.setEnabled(nonceEnabled);
		clicked(nonce, nonceClicked);

		account.setEnabled(createAccountEnabled);
		clicked(account, createAccountClicked);

		order.setEnabled(orderEnabled);
		clicked(order, orderClicked);

		changeKey.setEnabled(changeKeyEnabled);
		clicked(changeKey, changeKeyClicked);

		revokeCert.setEnabled(revokeCertEnabled);
		clicked(revokeCert, revokeCertClicked);

		accountDetails.setEnabled(accountDetailsEnabled);
		clicked(accountDetails, accountDetailsClicked);

		authorizationDetails.setEnabled(authorizationDetailsEnabled);
		clicked(authorizationDetails, authorizationDetailsClicked);

		challenge.setEnabled(challengeEnabled);
		clicked(challenge, challengeClicked);

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

		for (ActionListener listener : button.getActionListeners()) {
			button.removeActionListener(listener);
		}

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
