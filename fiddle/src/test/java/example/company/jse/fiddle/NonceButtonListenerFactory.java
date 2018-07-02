package example.company.jse.fiddle;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class NonceButtonListenerFactory {

	private JPanel contentPanel;
	private JFrame f;

	public NonceButtonListenerFactory(JPanel contentPanel, JFrame f) {
		this.contentPanel = contentPanel;
		this.f = f;
	}

	public NonceButtonListener getNonceListener() {
		return new NonceButtonListener(contentPanel, f);
	}

}
