package example.company.jse.fiddle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServersReaction implements ActionListener {

	private JComboBox<String> serversList;
	private JButton serversButtonSet;
	private JPanel topPanel;
	private JFrame f;
	private DirectoriesReactionFactory directoriesReactionFactory;

	public ServersReaction(JComboBox<String> serversList, JButton serversButtonSet, JPanel topPanel, JFrame f) {
		this.serversList = serversList;
		this.serversButtonSet = serversButtonSet;
		this.topPanel = topPanel;
		this.f = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		serversList.setEnabled(false);
		serversButtonSet.setEnabled(false);
		topPanel.add(new JLabel("Good. Now we can learn about that servers capabilities"));
		JButton directoriesButton = new JButton("Learn");

		DirectoriesReaction directoriesAction = directoriesReactionFactory.getDirectoriesReaction(directoriesButton);
		directoriesButton.addActionListener(directoriesAction);
		topPanel.add(directoriesButton);
		f.validate();

	}

	public void setDirectoriesReactionFactory(DirectoriesReactionFactory directoriesReactionFactory) {
		this.directoriesReactionFactory = directoriesReactionFactory;
	}

}
