package xpdtr.acme.gui.interactions;

import java.awt.Container;

import example.company.acme.AcmeSession;
import xpdtr.acme.gui.async.NonceRequest;
import xpdtr.acme.gui.components.NonceUI;

public class NonceInteraction extends UIInteraction {

	private AcmeSession session;
	private Runnable finished;

	public NonceInteraction(Interacter interacter, Container container, AcmeSession session, Runnable finished) {
		super(interacter, container);
		this.session = session;
		this.finished = finished;
	}

	public void perform() {
		container.add(NonceUI.renderGetting());
		NonceRequest.send(session.getInfos()).then(this::nonceSuccess, this::nonceFailure);
	}

	private void nonceSuccess(String nonce) {
		container.add(NonceUI.renderSuccess(nonce));
		session.setNonce(nonce);
		finished.run();
	}

	private void nonceFailure(Exception ex) {
		container.add(NonceUI.renderFailure(ex));
		finished.run();
	}

}
