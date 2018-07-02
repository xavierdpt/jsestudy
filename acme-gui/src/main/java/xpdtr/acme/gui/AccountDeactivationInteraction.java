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
		AccountDeactivationRequest.send(session).then(this::success);
	}

	public void success(AcmeResponse<Boolean> response) {
		interacter.perform(() -> {
			if (response.isFailed()) {
				logger.message(response.getFailureDetails());
				logger.endGroup();
				consumer.accept(false);
			} else {
				logger.message("Success");
				logger.endGroup();
				consumer.accept(response.getContent());
			}
		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<Boolean> consumer) {
		new AccountDeactivationInteraction(interacter, container, logger, session, consumer).start();

	}

}
