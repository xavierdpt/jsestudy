package xpdtr.acme.gui.interactions;

import java.awt.Container;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import example.company.acme.crypto.KPG;
import example.company.acme.jw.KeyPairWithJWK;
import xpdtr.acme.gui.SameWidthLayout;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.utils.U;

public class CreateKeyPairInteraction extends UIInteraction {

	private enum Algos {
		ELLIPTIC, RSA
	};

	private UILogger logger;
	private Consumer<KeyPairWithJWK> consumer;

	public CreateKeyPairInteraction(Interacter interacter, Container container, UILogger logger,
			Consumer<KeyPairWithJWK> consumer) {
		super(interacter, container);
		this.logger = logger;
		this.consumer = consumer;
	}

	@Override
	protected void perform() {

		logger.beginGroup("Key Pair Creation");

		logger.message("Elliptic or RSA ?");

		JPanel panel = new JPanel(new SameWidthLayout(5));
		panel.add(U.button("Elliptic", () -> {
			proceed(Algos.ELLIPTIC);
		}));
		panel.add(U.button("RSA", () -> {
			proceed(Algos.RSA);
		}));
		logger.leading(panel);

		logger.leading(U.button("Cancel", this::cancel));

	}

	private void proceed(Algos algo) {

		interacter.perform(() -> {

			logger.message("Creating key pair");

			new SwingWorker<KeyPairWithJWK, Void>() {
				@Override
				protected KeyPairWithJWK doInBackground() throws Exception {
					switch (algo) {
					case ELLIPTIC:
						return KeyPairWithJWK.fromKeyPair(KPG.newECP256KeyPair());
					case RSA:
						return KeyPairWithJWK.fromKeyPair(KPG.newRSAKeyPair());
					default:
						throw new IllegalArgumentException(algo == null ? "null" : algo.name());
					}
				}

				protected void done() {
					interacter.perform(() -> {
						KeyPairWithJWK keyPairWithJWK = null;
						try {
							keyPairWithJWK = get();
							logger.message("New key pair created");
						} catch (Exception e) {
							logger.exception(e);
						}
						logger.endGroup();
						consumer.accept(keyPairWithJWK);
					});
				};
			}.execute();
		});
	}

	private void cancel() {
		interacter.perform(() -> {
			logger.message("Cancelled");
			logger.endGroup();
			consumer.accept(null);
		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger,
			Consumer<KeyPairWithJWK> consumer) {
		new CreateKeyPairInteraction(interacter, container, logger, consumer).start();

	}

}
