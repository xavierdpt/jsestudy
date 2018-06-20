package xpdtr.acme.gui.interactions;

import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.account.AcmeAccount;
import xpdtr.acme.gui.async.AccountCreationRequest;
import xpdtr.acme.gui.components.AccountCreationUI;
import xpdtr.acme.gui.components.ExceptionUI;
import xpdtr.acme.gui.utils.U;

public class NewAccountInteraction {

	private JPanel container;
	private AcmeDirectoryInfos2 directoryInfos;
	private String nonce;
	private ObjectMapper om;
	private Runnable validate;
	private Consumer<AcmeAccount> finished;

	public NewAccountInteraction(JPanel container, AcmeDirectoryInfos2 directoryInfos, String nonce, ObjectMapper om,
			Runnable validate, Consumer<AcmeAccount> finished) {
		this.container = container;
		this.directoryInfos = directoryInfos;
		this.nonce = nonce;
		this.om = om;
		this.validate = validate;
		this.finished = finished;
	}

	public void start() {
		container.add(AccountCreationUI.renderInput(this::createAccountProceed, this::createAccountCancel));
		validate.run();
	}

	private void createAccountProceed(String contact) {
		container.add(AccountCreationUI.renderCalling());
		AccountCreationRequest.send(directoryInfos, nonce, om, contact).then(this::createAccountSuccess,
				this::createAccountFailure);
		validate.run();
	}

	private void createAccountCancel() {
		U.class.getName();
		U.addM(container, new JLabel("Account creation cancelled"));
		finished.accept(null);
		validate.run();
	}

	private void createAccountSuccess(AcmeAccount account) {
		U.addM(container, AccountCreationUI.renderSuccess(account));
		finished.accept(null);
		validate.run();
	}

	private void createAccountFailure(Exception ex) {
		U.addM(container, ExceptionUI.render(ex));
		finished.accept(null);
		validate.run();
	}

}
