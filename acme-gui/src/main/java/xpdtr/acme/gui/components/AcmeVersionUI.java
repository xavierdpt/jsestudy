package xpdtr.acme.gui.components;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import example.company.acme.AcmeSession;
import xpdtr.acme.gui.interactions.UIInteraction;
import xpdtr.acme.gui.layout.LabelFieldButton;
import xpdtr.acme.gui.utils.U;

public class AcmeVersionUI extends UIInteraction {

	private AcmeSession session;

	public AcmeVersionUI(Container container, AcmeSession session, Runnable validate, Runnable finished) {
		super(container, validate, finished);
		this.session = session;
	}

	@Override
	public void start() {
		JTextField question = SelectableLabelUI.render("Which version of ACME do you want to use ?");
		U.setFont(question, Font.BOLD);

		JComboBox<String> list = new JComboBox<String>(new String[] { "Version 1", "Version 2" });
		list.setSelectedItem("Version 2");

		JButton button = new JButton("OK");
		U.clicked(button, (e) -> {
			list.setEnabled(false);
			button.setEnabled(false);
			session.setVersion((String) list.getSelectedItem());
			finished();
			validate();
		});

		JPanel panel = new JPanel();
		panel.setLayout(new LabelFieldButton(5));
		panel.add(question);
		panel.add(list);
		panel.add(button);

		U.addM(container, panel);

	}

}
