package example.company.jse.fiddle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;

public class DirectoriesReaction implements ActionListener {

	private JButton directoriesButton;
	private JPanel contentPanel;
	private JFrame f;
	private ObjectMapper om;
	private Map<String, Object> map;
	private JComboBox<String> serversList;

	public DirectoriesReaction(JButton directoriesButton, JPanel contentPanel, JFrame f, ObjectMapper om,
			Map<String, Object> map, JComboBox<String> serversList) {
		this.directoriesButton = directoriesButton;
		this.contentPanel = contentPanel;
		this.f = f;
		this.om = om;
		this.map = map;
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
			directories = Acme2.directory((String) serversList.getSelectedItem(),om);
			directoriesTA.setText(om.writeValueAsString(directories));
		} catch (Exception e) {
			directoriesTA.setText(e.getClass().getName() + " : " + e.getMessage());
		}

		contentPanel.add(directoriesSP);

		contentPanel.add(new JLabel("Now, the first thing we need to do is get a new nonce"));

		JButton getNonceButton = new JButton("Get a new nonce");
		NonceButtonListenerFactory nonceButtonListenerFactory = new NonceButtonListenerFactory(contentPanel,
				directories, f);
		getNonceButton.addActionListener(nonceButtonListenerFactory.getNonceListener());
		contentPanel.add(getNonceButton);

		f.validate();

	}

}
