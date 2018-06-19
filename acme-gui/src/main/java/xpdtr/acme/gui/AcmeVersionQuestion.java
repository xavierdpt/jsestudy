package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AcmeVersionQuestion {

	public static Component create(Consumer<String> consumer) {

		JLabel label = new JLabel("Which version of ACME do you want to use ?");
		U.setFont(label, Font.BOLD);

		JComboBox<String> list = new JComboBox<String>(new String[] { "Version 1", "Version 2" });
		list.setSelectedItem("Version 2");

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
