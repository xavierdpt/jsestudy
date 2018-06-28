package xpdtr.acme.gui.interactions;

import java.awt.Container;
import java.util.function.Consumer;

import example.company.acme.AcmeSession;
import example.company.acme.v2.AcmeDirectoryInfos2;
import xpdtr.acme.gui.async.DirectoryRequest;
import xpdtr.acme.gui.components.UILogger;

public class DirectoryInteraction extends UIInteraction {

	private AcmeSession session;
	private UILogger logger;
	private Consumer<AcmeDirectoryInfos2> consumer;

	public DirectoryInteraction(Interacter interacter, Container container, UILogger logger, AcmeSession session,
			Consumer<AcmeDirectoryInfos2> consumer) {
		super(interacter, container);
		this.logger = logger;
		this.session = session;
		this.consumer = consumer;
	}

	@Override
	public void perform() {
		logger.message("Getting directory infos... ");
		DirectoryRequest.send(session.getUrl(), session.getOm()).then(this::success, this::failure);
	}

	private void success(AcmeDirectoryInfos2 infos) {
		interacter.perform(() -> {
			logger.message(infos.getKeyChange());
			logger.message(infos.getNewAccountURL());
			logger.message(infos.getNewNonce());
			logger.message(infos.getNewOrder());
			logger.message(infos.getRevokeCert());
			consumer.accept(infos);
		});
	}

	private void failure(Exception exception) {
		interacter.perform(() -> {
			logger.exception(exception);
		});
	}

}
