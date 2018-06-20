package xpdtr.acme.gui.interactions;

import java.awt.Container;
import java.util.function.Consumer;

import example.company.acme.v2.AcmeDirectoryInfos2;
import xpdtr.acme.gui.async.NonceRequest;
import xpdtr.acme.gui.components.NonceUI;

public class NonceInteraction {

	private Container container;
	private AcmeDirectoryInfos2 directoryInfos;
	private Runnable validate;
	private Consumer<String> finish;

	public NonceInteraction(Container container, AcmeDirectoryInfos2 directoryInfos, Runnable validate,
			Consumer<String> finish) {
		this.container = container;
		this.directoryInfos = directoryInfos;
		this.validate = validate;
		this.finish = finish;
	}

	public void start() {
		container.add(NonceUI.renderGetting());
		validate.run();
		NonceRequest.send(directoryInfos).then(this::nonceSuccess, this::nonceFailure);
	}

	private void nonceSuccess(String nonce) {
		container.add(NonceUI.renderSuccess(nonce));
		finish.accept(nonce);
		validate.run();
	}

	private void nonceFailure(Exception ex) {
		container.add(NonceUI.renderFailure(ex));
		finish.accept(null);
		validate.run();
	}

}
