package example.company.jse.fiddle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import example.company.acme.v2.AcmeDirectoryInfos2;

public class NonceButtonListenerFactory {

	private JPanel contentPanel;
	private AcmeDirectoryInfos2 infos;
	private JFrame f;

	public NonceButtonListenerFactory(JPanel contentPanel,AcmeDirectoryInfos2 infos, JFrame f) {
		this.contentPanel = contentPanel;
		this.infos = infos;
		this.f = f;
	}

	public NonceButtonListener getNonceListener() {
		return new NonceButtonListener(contentPanel, infos,f);
	}

}
