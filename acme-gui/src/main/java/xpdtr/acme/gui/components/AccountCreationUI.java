package xpdtr.acme.gui.components;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import example.company.acme.v2.account.AcmeAccount;
import xpdtr.acme.gui.layout.LabelFieldButton;

public class AccountCreationUI {

	public static Component renderCalling() {
		JLabel label = new JLabel("Calling account create");
		return label;
	}

	public static List<Component> renderSuccess(AcmeAccount account) {

		List<Component> components = new ArrayList<>();

		JLabel nonceLabel = new JLabel("New nonce : " + account.getNonce());
		JLabel accountUrlLabel = new JLabel(account.getUrl());

		components.add(nonceLabel);
		components.add(accountUrlLabel);

		return components;
	}

	public static Component renderInput(Consumer<String> onCreate, Runnable onCancel) {

		JLabel label = new JLabel("Provide account contact");
		JTextField contactTextField = new JTextField(150);
		JButton create = new JButton("Create");
		JButton cancel = new JButton("Cancel");

		Runnable disable = () -> {
			contactTextField.setEnabled(false);
			create.setEnabled(false);
			cancel.setEnabled(false);
		};

		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				disable.run();
				onCreate.accept(contactTextField.getText());
			}
		});

		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				disable.run();
				onCancel.run();
			}
		});

		JPanel panel = new JPanel(new LabelFieldButton(5));
		panel.add(label);
		panel.add(contactTextField);
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(create);
		buttons.add(cancel);
		panel.add(buttons);
		return panel;

	}

}
