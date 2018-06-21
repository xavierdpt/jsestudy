package xpdtr.acme.gui.interactions;

import java.awt.Container;

import example.company.acme.AcmeSession;
import xpdtr.acme.gui.async.NonceRequest;
import xpdtr.acme.gui.components.NonceUI;

public class NonceInteraction extends UIInteraction {

	private AcmeSession session;

	public NonceInteraction(Container container, AcmeSession session, Runnable validate, Runnable finish) {
		super(container, validate, finish);
		this.session = session;
	}

	public void start() {
		container.add(NonceUI.renderGetting());
		validate();
		NonceRequest.send(session.getInfos()).then(this::nonceSuccess, this::nonceFailure);
	}

	private void nonceSuccess(String nonce) {
		container.add(NonceUI.renderSuccess(nonce));
		session.setNonce(nonce);
		finished();
		validate();
	}

	private void nonceFailure(Exception ex) {
		container.add(NonceUI.renderFailure(ex));
		finished();
		validate();
	}

}
