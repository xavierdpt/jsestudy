package xpdtr.acme.gui.interactions;

import java.awt.Container;

import javax.swing.JLabel;

import example.company.acme.AcmeSession;
import xpdtr.acme.gui.async.AccountCreationRequest;
import xpdtr.acme.gui.components.AccountCreationUI;
import xpdtr.acme.gui.components.ExceptionUI;
import xpdtr.acme.gui.utils.U;

public class NewAccountInteraction extends UIInteraction {

	private AcmeSession session;
	private Runnable finished;

	public NewAccountInteraction(Interacter interacter, Container container, AcmeSession session, Runnable finished) {
		super(interacter, container);
		this.session = session;
		this.finished = finished;
	}

	public void perform() {
		container.add(AccountCreationUI.renderInput(this::createAccountProceed, this::createAccountCancel));
	}

	private void createAccountProceed(String contact) {
		container.add(AccountCreationUI.renderCalling());
		AccountCreationRequest.send(session, contact).then(this::createAccountSuccess, this::createAccountFailure);
	}

	private void createAccountCancel() {
		U.class.getName();
		U.addM(container, new JLabel("Account creation cancelled"));
		finished.run();
	}

	private void createAccountSuccess(AcmeSession session) {
		U.addM(container, AccountCreationUI.renderSuccess(session));
		session.setAccount(session.getAccount());
		session.setNonce(session.getNonce());
		finished.run();
	}

	private void createAccountFailure(Exception ex) {
		U.addM(container, ExceptionUI.render(ex));
		finished.run();
	}

}
