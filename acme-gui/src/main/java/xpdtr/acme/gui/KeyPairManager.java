package xpdtr.acme.gui;

import java.awt.Container;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.AcmeSession;
import example.company.acme.jw.JWK;
import example.company.acme.jw.KeyPairWithJWK;
import xpdtr.acme.gui.components.ExceptionUI;
import xpdtr.acme.gui.components.MessageUI;
import xpdtr.acme.gui.utils.U;

public class KeyPairManager {

	private AcmeSession session;
	private Container sessionContainer;
	private JFrame frame;
	private Runnable after;

	public KeyPairManager(AcmeSession session, Container sessionContainer, JFrame frame, Runnable after) {
		this.session = session;
		this.sessionContainer = sessionContainer;
		this.frame = frame;
		this.after = after;
	}

	public void saveKeyPair() {

		JFileChooser jfc = new JFileChooser();
		int r = jfc.showOpenDialog(frame);
		U.addM(sessionContainer, MessageUI.render("Returned value : " + r));
		if (r == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			U.addM(sessionContainer, MessageUI.render(file.getAbsolutePath()));
			try {
				FileWriter fw = new FileWriter(file);
				JWK jwk = session.getKeyPairWithJWK().getFullJWK();
				session.getOm().writeValue(fw, jwk);
				fw.close();
				U.addM(sessionContainer, MessageUI.render("Key pair saved"));
			} catch (IOException e) {
				U.addM(sessionContainer, ExceptionUI.render(e));
			}
		} else {
			U.addM(sessionContainer, MessageUI.render("Cancelled"));
		}
		after.run();

	}

	public void loadKeyPair() {
		JFileChooser jfc = new JFileChooser();
		int r = jfc.showOpenDialog(frame);
		U.addM(sessionContainer, MessageUI.render("Returned value : " + r));
		if (r == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			U.addM(sessionContainer, MessageUI.render(file.getAbsolutePath()));
			if (!file.exists() || !file.isFile() || !file.canRead()) {
				U.addM(sessionContainer, MessageUI.render("File is not a readable existing file"));
			} else {
				try {
					ObjectMapper om = session.getOm();
					JWK jwk = om.readValue(new FileReader(file), JWK.class);
					session.setKeyPairWithJWK(KeyPairWithJWK.fromJWK(jwk));
					U.addM(sessionContainer, MessageUI.render("Key loaded"));
				} catch (Exception e) {
					U.addM(sessionContainer, ExceptionUI.render(e));
				}
			}
		} else {
			U.addM(sessionContainer, MessageUI.render("Cancelled"));
		}
		after.run();
	}

}
