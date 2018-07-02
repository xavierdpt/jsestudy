package xpdtr.acme.gui.interactions;

import java.awt.Container;
import java.util.function.Consumer;

import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.AcmeResponse;
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
		logger.beginGroup("Getting Directory");
		logger.message("Getting directory infos... ");
		DirectoryRequest.send(session.getUrl(), session.getOm(), session).then(this::success);
	}

	private void success(AcmeResponse<AcmeDirectoryInfos2> infos) {
		interacter.perform(() -> {
			if (infos.isFailed()) {
				logger.message(infos.getFailureDetails());
				logger.endGroup();
				consumer.accept(null);
			} else {
				logger.message(infos.getResponseText(), true);
				logger.endGroup();
				consumer.accept(infos.getContent());
			}

		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<AcmeDirectoryInfos2> consumer) {
		new DirectoryInteraction(interacter, container, logger, session, consumer).start();
	}

}
