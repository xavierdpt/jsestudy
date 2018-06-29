package xpdtr.acme.gui;

import java.awt.Container;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import example.company.acme.crypto.KPG;
import example.company.acme.jw.KeyPairWithJWK;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.interactions.Interacter;
import xpdtr.acme.gui.interactions.UIInteraction;

public class CreateKeyPairInteraction extends UIInteraction {

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
		logger.message("Creating key pair");

		new SwingWorker<KeyPairWithJWK, Void>() {
			@Override
			protected KeyPairWithJWK doInBackground() throws Exception {
				return KeyPairWithJWK.fromKeyPair(KPG.newECP256KeyPair());
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

	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger,
			Consumer<KeyPairWithJWK> consumer) {
		new CreateKeyPairInteraction(interacter, container, logger, consumer).start();

	}

}
