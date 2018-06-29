package xpdtr.acme.gui.interactions;

import java.util.function.Consumer;

import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import xpdtr.acme.gui.async.NonceRequest;
import xpdtr.acme.gui.components.UILogger;

public class NonceInteraction extends UIInteraction {

	private AcmeSession session;
	private UILogger logger;
	private Consumer<String> consumer;

	public NonceInteraction(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<String> consumer) {
		super(interacter, container);
		this.logger = logger;
		this.session = session;
		this.consumer = consumer;
	}

	public void perform() {
		logger.beginGroup("New nonce");
		logger.message("Getting new nonce... ");
		NonceRequest.send(session.getInfos()).then(this::nonceSuccess, this::nonceFailure);
	}

	private void nonceSuccess(String nonce) {
		interacter.perform(() -> {
			logger.message(nonce);
			logger.endGroup();
			consumer.accept(nonce);
		});
	}

	private void nonceFailure(Exception exception) {
		interacter.perform(() -> {
			logger.exception(exception);
			logger.endGroup();
			consumer.accept(null);
		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<String> consumer) {
		new NonceInteraction(interacter, container, logger, session, consumer).start();
	}

}
