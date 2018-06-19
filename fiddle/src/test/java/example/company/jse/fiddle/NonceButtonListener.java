package example.company.jse.fiddle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;

public class NonceButtonListener implements ActionListener {

	private JPanel contentPanel;
	private AcmeDirectoryInfos2 infos;
	private JFrame f;

	public NonceButtonListener(JPanel contentPanel, AcmeDirectoryInfos2 infos, JFrame f) {
		this.contentPanel = contentPanel;
		this.infos = infos;
		this.f = f;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			String nonce = Acme2.nonce(infos);
			contentPanel.add(new JLabel("Nonce is " + nonce));
		} catch (Exception e) {
			contentPanel.add(new JLabel(e.getClass().getName() + " : " + e.getMessage()));
		}
		f.validate();
	}

}
