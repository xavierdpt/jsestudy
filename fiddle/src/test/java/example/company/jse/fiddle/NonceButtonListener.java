package example.company.jse.fiddle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeResponse;

public class NonceButtonListener implements ActionListener {

	private JPanel contentPanel;
	private JFrame f;

	public NonceButtonListener(JPanel contentPanel, JFrame f) {
		this.contentPanel = contentPanel;
		this.f = f;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			AcmeSession session = new AcmeSession();
			AcmeResponse<String> nonce2 = Acme2.nonce(session);
			String nonce = nonce2.getContent();
			contentPanel.add(new JLabel("Nonce is " + nonce));
		} catch (Exception e) {
			contentPanel.add(new JLabel(e.getClass().getName() + " : " + e.getMessage()));
		}
		f.validate();
	}

}
