package xpdtr.acme.gui.components;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
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

import xpdtr.acme.gui.utils.U;

public class AccountCreationUI {

	private JComboBox<String> contactInput;
	private JButton createButton;
	private JButton cancelButton;
	private Consumer<String> onCreate;
	private Runnable onCancel;
	private List<String> knownContacts;

	public AccountCreationUI(Consumer<String> onCreate, Runnable onCancel) {
		this.onCreate = onCreate;
		this.onCancel = onCancel;
	}

	public void renderInput(Container container) {
		addFields(container);
		addButtons(container);
	}

	private void addFields(Container container) {
		JPanel fieldsPanel = new JPanel(new LabelsAndFields(5,15));
		fieldsPanel.add(new JLabel("Contact"));
		addContactInput(fieldsPanel);
		container.add(fieldsPanel);
	}

	private void addContactInput(JPanel fieldsPanel) {
		contactInput = new JComboBox<>();
		contactInput.setEditable(true);
		knownContacts = getKnownContacts();
		addKnownContacts(knownContacts, contactInput);
		fieldsPanel.add(contactInput);

	}

	private void addButtons(Container container) {
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEADING));

		createButton = new JButton("Create");
		U.clicked(createButton, this::create);

		cancelButton = new JButton("Cancel");
		U.clicked(cancelButton, this::cancel);

		buttons.add(createButton);
		buttons.add(cancelButton);
		container.add(buttons);
	}

	private void disable() {
		contactInput.setEnabled(false);
		createButton.setEnabled(false);
		cancelButton.setEnabled(false);
	};

	private void create(ActionEvent e) {
		disable();
		String selected = contactInput.getSelectedItem().toString();
		saveKnownContact(knownContacts, selected);
		onCreate.accept(selected);
	}

	private void cancel(ActionEvent e) {
		disable();
		onCancel.run();
	}

	private boolean saveKnownContact(List<String> contacts, String selected) {
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

	private List<String> getKnownContacts() {

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

	private Path getPath() throws IOException {
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

	private void addKnownContacts(List<String> knownContacts, JComboBox<String> contactInput) {
		for (String contact : knownContacts) {
			contactInput.addItem(contact);
		}
	}
}
