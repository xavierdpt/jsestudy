package xpdtr.acme.gui;

import java.util.function.Consumer;

import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.AcmeResponse;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.interactions.Interacter;
import xpdtr.acme.gui.interactions.UIInteraction;

public class AccountDetailsInteraction extends UIInteraction {

	private UILogger logger;
	private AcmeSession session;
	private Consumer<AcmeResponse<String>> consumer;

	public AccountDetailsInteraction(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<AcmeResponse<String>> consumer) {
		super(interacter, container);
		this.logger = logger;
		this.session = session;
		this.consumer = consumer;
	}

	@Override
	protected void perform() {
		logger.beginGroup("Account details");
		logger.message("Getting account details...");
		AccountDetailsRequest.send(session).then(this::success, this::failure);
	}

	private void success(AcmeResponse<String> response) {
		interacter.perform(() -> {
			logger.message(response.getContent(),true);
			logger.endGroup();
			consumer.accept(response);
		});
	}

	private void failure(Exception exception) {
		interacter.perform(() -> {
			logger.exception(exception);
			logger.endGroup();
			consumer.accept(null);
		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<AcmeResponse<String>> consumer) {
		new AccountDetailsInteraction(interacter, container, logger, session, consumer).start();

	}

}
