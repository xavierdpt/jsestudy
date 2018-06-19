package xpdtr.acme.gui.components;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import example.company.acme.v1.AcmeUrls;
import xpdtr.acme.gui.layout.LabelFieldButton;

public class AcmeUrlUI {

	public static Component create(String version, Consumer<String> consumer) {
		switch (version) {
		case "Version 1":
			return create1();
		case "Version 2":
		default:
			return create2(consumer);
		}
	}

	private static Component create1() {
		JLabel label = new JLabel("Sorry, support for Acme v1 is not available");
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		return label;
	}

	private static Component create2(Consumer<String> consumer) {

		JLabel label = new JLabel("Which URL do you want to use ?");
		label.setFont(label.getFont().deriveFont(Font.BOLD));

		JComboBox<String> list = new JComboBox<String>(
				new String[] { AcmeUrls.LETS_ENCRYPT_V2, AcmeUrls.LETS_ENCRYPT_V2_STAGING });
		list.setSelectedItem(AcmeUrls.LETS_ENCRYPT_V2_STAGING);

		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				list.setEnabled(false);
				button.setEnabled(false);

				consumer.accept((String) list.getSelectedItem());
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new LabelFieldButton(5));
		panel.add(label);
		panel.add(list);
		panel.add(button);

		return panel;

	}
}
