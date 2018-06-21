package xpdtr.acme.gui.components;

import java.awt.Component;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import xpdtr.acme.gui.layout.LabelFieldButton;
import xpdtr.acme.gui.utils.U;

public class AccountCreationUI {

	public static Component renderCalling() {
		JLabel label = new JLabel("Calling account create");
		return label;
	}

	public static List<Component> renderSuccess(AcmeSession session) {

		List<Component> components = new ArrayList<>();

		JLabel nonceLabel = new JLabel("New nonce : " + session.getNonce());
		JLabel accountUrlLabel = new JLabel(session.getAccount().getUrl());

		components.add(nonceLabel);
		components.add(accountUrlLabel);

		return components;
	}

	public static Component renderInput(Consumer<String> onCreate, Runnable onCancel) {

		JLabel label = new JLabel("Provide account contact");

		JComboBox<String> contactInput = new JComboBox<>();
		contactInput.setEditable(true);
		List<String> knownContacts = getKnownContacts();
		addKnownContacts(knownContacts, contactInput);
		JButton createButton = new JButton("Create");
		JButton cancelButton = new JButton("Cancel");

		Runnable disable = () -> {
			contactInput.setEnabled(false);
			createButton.setEnabled(false);
			cancelButton.setEnabled(false);
		};

		U.clicked(createButton, (e) -> {
			disable.run();
			String selected = contactInput.getSelectedItem().toString();
			saveKnownContact(knownContacts, selected);
			onCreate.accept(selected);
		});

		U.clicked(cancelButton, (e) -> {
			disable.run();
			onCancel.run();
		});

		JPanel panel = new JPanel(new LabelFieldButton(5));
		panel.add(label);
		panel.add(contactInput);
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(createButton);
		buttons.add(cancelButton);
		panel.add(buttons);
		return panel;

	}

	private static boolean saveKnownContact(List<String> contacts, String selected) {
		for (String contact : contacts) {
			if (contact.equals(selected)) {
				return false;
			}
		}

		contacts.add(selected);
		Collections.sort(contacts);

		try (PrintWriter w = new PrintWriter(new FileWriter(getPath().toFile()))) {
			for (String contact : contacts) {
				w.println(contact);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static List<String> getKnownContacts() {

		List<String> knownContacts = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(getPath().toFile()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				knownContacts.add(line);
			}

		} catch (IOException e) {
			if (!(e instanceof FileNotFoundException)) {
				System.out.println(e.getClass().getName() + " : " + e.getMessage());
			}
		}

		Collections.sort(knownContacts);

		return knownContacts;

	}

	private static Path getPath() throws IOException {
		String userDirectory = System.getProperty("user.home");
		Path path = FileSystems.getDefault().getPath(userDirectory, ".acme-gui");
		File pathFile = path.toFile();
		if (pathFile.exists()) {
			if (!pathFile.isDirectory()) {
				throw new IOException(pathFile.getAbsolutePath() + " is not a directory");
			}
		} else {
			pathFile.mkdirs();
		}
		return path.resolve("knownContacts");
	}

	private static void addKnownContacts(List<String> knownContacts, JComboBox<String> contactInput) {
		for (String contact : knownContacts) {
			contactInput.addItem(contact);
		}
	}
}
