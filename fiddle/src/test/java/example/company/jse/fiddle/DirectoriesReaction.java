package example.company.jse.fiddle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.fasterxml.jackson.databind.ObjectMapper;

import xdptdr.acme.v2.Acme2;
import xdptdr.acme.v2.AcmeDirectoryInfos2;
import xdptdr.acme.v2.AcmeSession;

public class DirectoriesReaction implements ActionListener {

	private JButton directoriesButton;
	private JPanel contentPanel;
	private JFrame f;
	private ObjectMapper om;
	private JComboBox<String> serversList;

	public DirectoriesReaction(JButton directoriesButton, JPanel contentPanel, JFrame f, ObjectMapper om,
			JComboBox<String> serversList) {
		this.directoriesButton = directoriesButton;
		this.contentPanel = contentPanel;
		this.f = f;
		this.om = om;
		this.serversList = serversList;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		directoriesButton.setEnabled(false);

		RSyntaxTextArea directoriesTA = new RSyntaxTextArea(20, 60);
		directoriesTA.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
		directoriesTA.setCodeFoldingEnabled(true);
		directoriesTA.setEditable(false);
		RTextScrollPane directoriesSP = new RTextScrollPane(directoriesTA);

		AcmeDirectoryInfos2 directories = null;
		try {
			AcmeSession session = new AcmeSession();
			directories = Acme2.directory((String) serversList.getSelectedItem(), om, session).getContent();
			directoriesTA.setText(om.writeValueAsString(directories));
		} catch (Exception e) {
			directoriesTA.setText(e.getClass().getName() + " : " + e.getMessage());
		}

		contentPanel.add(directoriesSP);

		contentPanel.add(new JLabel("Now, the first thing we need to do is get a new nonce"));

		JButton getNonceButton = new JButton("Get a new nonce");
		NonceButtonListenerFactory nonceButtonListenerFactory = new NonceButtonListenerFactory(contentPanel, f);
		getNonceButton.addActionListener(nonceButtonListenerFactory.getNonceListener());
		contentPanel.add(getNonceButton);

		f.validate();

	}

}
