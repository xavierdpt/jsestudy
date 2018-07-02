package example.company.jse.fiddle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DirectoriesReactionFactory {

	private JPanel contentPanel;
	private JFrame f;
	private ObjectMapper om;
	private JComboBox<String> serversList;

	public DirectoriesReactionFactory(JPanel contentPanel, JFrame f, ObjectMapper om, JComboBox<String> serversList) {
		this.contentPanel = contentPanel;
		this.f = f;
		this.om = om;
		this.serversList = serversList;
	}

	DirectoriesReaction getDirectoriesReaction(JButton directoriesButton) {
		return new DirectoriesReaction(directoriesButton, contentPanel, f, om, serversList);
	}

}
