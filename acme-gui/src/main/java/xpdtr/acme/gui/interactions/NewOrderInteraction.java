package xpdtr.acme.gui.interactions;

import java.awt.Container;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.AcmeException;
import example.company.acme.v2.AcmeOrder;
import example.company.acme.v2.AcmeResponse;
import xpdtr.acme.gui.async.OrderCreationRequest;
import xpdtr.acme.gui.components.OrderCreationUI;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.interactions.Interacter;
import xpdtr.acme.gui.interactions.UIInteraction;

public class NewOrderInteraction extends UIInteraction {

	private UILogger logger;
	private AcmeSession session;
	private Container destination;
	private Consumer<AcmeResponse<AcmeOrder>> consumer;

	public NewOrderInteraction(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<AcmeResponse<AcmeOrder>> consumer) {
		super(interacter, container);
		this.logger = logger;
		this.session = session;
		this.consumer = consumer;
	}

	@Override
	protected void perform() {
		logger.beginGroup("New Account");
		destination = logger.getDestination();
		logger.message("Creating new account");

		new OrderCreationUI(this::proceed, this::cancel).renderInput(destination);

	}

	private void proceed(String site) {
		OrderCreationRequest.send(session, site).then(this::createOrderSuccess, this::createOrderFailure);
	}

	private void cancel() {
		logger.message("Cancelled");
		consumer.accept(null);
	}

	private void createOrderSuccess(AcmeResponse<AcmeOrder> order) {

		if (!order.isFailed()) {
			interacter.perform(() -> {
				consumer.accept(order);
				logger.message("Success");
				List<String> authorizations = order.getContent().getAuthorizations();
				for (String a : authorizations) {
					logger.message("Authorization " + a);
				}
				logger.message("Finalize " + order.getContent().getFinalize());
			});
		} else {
			session.setNonce(order.getNonce());
			createOrderFailure(new AcmeException(order.getFailureDetails()));
		}
	}

	private void createOrderFailure(Exception ex) {
		interacter.perform(() -> {
			logger.exception(ex);
			consumer.accept(null);
		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<AcmeResponse<AcmeOrder>> consumer) {
		new NewOrderInteraction(interacter, container, logger, session, consumer).start();

	}

}
