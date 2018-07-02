package xpdtr.acme.gui.interactions;

import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.AcmeResponse;
import xpdtr.acme.gui.async.NonceRequest;
import xpdtr.acme.gui.components.UILogger;

public class NonceInteraction extends UIInteraction {

	private AcmeSession session;
	private UILogger logger;
	private Runnable consumer;

	public NonceInteraction(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Runnable consumer) {
		super(interacter, container);
		this.logger = logger;
		this.session = session;
		this.consumer = consumer;
	}

	public void perform() {
		logger.beginGroup("New nonce");
		logger.message("Getting new nonce... ");
		NonceRequest.send(session.getInfos(), session).then(this::nonceSuccess);
	}

	private void nonceSuccess(AcmeResponse<String> nonce) {
		interacter.perform(() -> {
			if (nonce.isFailed()) {
				logger.message(nonce.getFailureDetails());
				logger.endGroup();
				consumer.run();
			} else {
				logger.message(nonce.getContent());
				logger.endGroup();
				consumer.run();
			}
		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Runnable consumer) {
		new NonceInteraction(interacter, container, logger, session, consumer).start();
	}

}
