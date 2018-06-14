package example.company.jse.fiddle;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DirectoriesReactionFactory {

	private JPanel contentPanel;
	private JFrame f;
	private ObjectMapper om;
	private Map<String, Object> map;
	private JComboBox<String> serversList;

	public DirectoriesReactionFactory(JPanel contentPanel, JFrame f, ObjectMapper om, Map<String, Object> map,
			JComboBox<String> serversList) {
		this.contentPanel = contentPanel;
		this.f = f;
		this.om = om;
		this.map = map;
		this.serversList = serversList;
	}

	DirectoriesReaction getDirectoriesReaction(JButton directoriesButton) {
		return new DirectoriesReaction(directoriesButton, contentPanel, f, om, map, serversList);
	}

}
