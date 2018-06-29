package xpdtr.acme.gui.interactions;

import java.awt.Container;
import java.util.function.Consumer;

import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.AcmeResponse;
import example.company.acme.v2.account.AcmeAccount;
import xpdtr.acme.gui.async.AccountCreationRequest;
import xpdtr.acme.gui.components.AccountCreationUI;
import xpdtr.acme.gui.components.UILogger;

public class NewAccountInteraction extends UIInteraction {

	private AcmeSession session;
	private UILogger logger;
	private Consumer<AcmeResponse<AcmeAccount>> consumer;
	private Container destination;

	public NewAccountInteraction(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<AcmeResponse<AcmeAccount>> consumer) {
		super(interacter, container);
		this.logger = logger;
		this.session = session;
		this.consumer = consumer;
	}

	public void perform() {
		logger.beginGroup("New Account");
		destination = logger.getDestination();
		new AccountCreationUI(this::proceed, this::cancel).renderInput(destination);
	}

	private void proceed(String contact) {
		interacter.perform(() -> {
			logger.message("Calling account create");
			AccountCreationRequest.send(session, contact).then(this::success, this::failure);
		});
	}

	private void cancel() {
		interacter.perform(() -> {
			logger.message("Account creation cancelled");
			logger.endGroup();
			consumer.accept(null);
		});
	}

	private void success(AcmeResponse<AcmeAccount> accountWithNonce) {
		interacter.perform(() -> {
			logger.message("New nonce : " + accountWithNonce.getNonce());
			logger.message(accountWithNonce.getContent().getUrl());
			logger.endGroup();
			consumer.accept(accountWithNonce);
		});
	}

	private void failure(Exception ex) {
		interacter.perform(() -> {
			logger.exception(ex);
			consumer.accept(null);
		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<AcmeResponse<AcmeAccount>> consumer) {
		new NewAccountInteraction(interacter, container, logger, session, consumer).start();
	}

}
