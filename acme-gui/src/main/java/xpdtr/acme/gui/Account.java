package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeAccount;
import example.company.acme.v2.AcmeDirectoryInfos2;

public class Account {

	public static Component renderCalling() {
		JLabel label = new JLabel("Calling account create");
		return label;
	}

	public static Promise<AcmeAccount> get(AcmeDirectoryInfos2 infos, String nonce, ObjectMapper om, String contact) {

		Promise<AcmeAccount> promise = new Promise<>();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					promise.success(Acme2.newAccount(infos, nonce, om, contact));
				} catch (Exception exception) {
					promise.failure(exception);
				}
			}
		});
		promise.setThread(thread);

		return promise;
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
