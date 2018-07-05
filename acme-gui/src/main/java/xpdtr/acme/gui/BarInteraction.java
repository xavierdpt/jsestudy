package xpdtr.acme.gui;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeResponse;
import example.company.asn.utils.CSRBuilder;
import example.company.tox.common.Common;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.interactions.Interacter;
import xpdtr.acme.gui.interactions.UIInteraction;

public class BarInteraction extends UIInteraction {

	private UILogger logger;
	private AcmeSession session;
	private Runnable after;

	public BarInteraction(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Runnable after) {
		super(interacter, container);
		this.logger = logger;
		this.session = session;
		this.after = after;

	}

	@Override
	protected void perform() {
		logger.beginGroup("Bar");
		logger.message("Doing something else");

		try {

			if (session.getKeyPairWithJWK() == null) {
				throw new Exception("Please load or create a key pair");
			}

			if (session.getAuthorization() == null) {
				throw new Exception("Please get an authorization");
			}

			byte[] csrBytes = createCsr(getKeyPair(session), session.getAuthorization().getIdentifier().getValue());

			logger.message("CSR bytes are as follow : ");
			logger.message(Common.bytesToString(csrBytes));

			AcmeResponse<Void> response = Acme2.finalize(session, csrBytes);

			if (response.isFailed()) {
				logger.message(response.getFailureDetails());
			} else {
				logger.message(response.getResponseText());
			}

		} catch (Exception ex) {
			logger.exception(ex);
		}

		after.run();
	}

	private KeyPair getKeyPair(AcmeSession session2) throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		return kpg.generateKeyPair();
	}

	private byte[] createCsr(KeyPair keyPair, String site)
			throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		CSRBuilder builder = new CSRBuilder();
		builder.setPublickKey(keyPair.getPublic());
		builder.setSubjectName("CN=" + site);
		builder.setSubjectKeyIdentifier(null);
		builder.setVersion(2);
		return builder.encode(keyPair.getPrivate());
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Runnable after) {
		new BarInteraction(interacter, container, logger, session, after).start();
	}

}
