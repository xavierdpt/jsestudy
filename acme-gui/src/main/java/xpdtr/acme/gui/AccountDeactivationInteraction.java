package xpdtr.acme.gui;

import java.util.function.Consumer;

import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.AcmeResponse;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.interactions.Interacter;
import xpdtr.acme.gui.interactions.UIInteraction;

public class AccountDeactivationInteraction extends UIInteraction {

	private UILogger logger;
	private AcmeSession session;
	private Consumer<Boolean> consumer;

	public AccountDeactivationInteraction(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<Boolean> consumer) {
		super(interacter, container);
		this.logger = logger;
		this.session = session;
		this.consumer = consumer;

	}

	@Override
	protected void perform() {
		logger.beginGroup("Account deactivation");
		logger.message("Deactivating account...");
		AccountDeactivationRequest.send(session).then(this::success, this::failure);
	}

	public void success(AcmeResponse<Boolean> response) {
		interacter.perform(() -> {
			if (!response.isFailed()) {
				logger.message("Success");
			} else {
				logger.message(response.getFailureDetails());
			}
			logger.endGroup();
			consumer.accept(true);
		});
	}

	public void failure(Exception exception) {
		interacter.perform(() -> {
			logger.exception(exception);
			logger.endGroup();
			consumer.accept(false);
		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<Boolean> consumer) {
		new AccountDeactivationInteraction(interacter, container, logger, session, consumer).start();

	}

}
