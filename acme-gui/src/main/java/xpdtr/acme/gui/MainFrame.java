package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.account.AcmeAccount;
import xpdtr.acme.gui.async.AccountCreationRequest;
import xpdtr.acme.gui.async.DirectoryRequest;
import xpdtr.acme.gui.async.NonceRequest;
import xpdtr.acme.gui.components.AccountCreationUI;
import xpdtr.acme.gui.components.Acme2Buttons;
import xpdtr.acme.gui.components.AcmeUrlUI;
import xpdtr.acme.gui.components.AcmeVersionUI;
import xpdtr.acme.gui.components.DirectoryUI;
import xpdtr.acme.gui.components.ExceptionUI;
import xpdtr.acme.gui.components.MessageUI;
import xpdtr.acme.gui.components.NonceUI;
import xpdtr.acme.gui.components.Title;
import xpdtr.acme.gui.fiddling.BasicFrameWithVerticalScroll;
import xpdtr.acme.gui.layout.StackedLayout;
import xpdtr.acme.gui.utils.U;

public class MainFrame extends BasicFrameWithVerticalScroll {

	private String version;
	private String url;
	private ObjectMapper om = new ObjectMapper();
	private AcmeDirectoryInfos2 directoryInfos;
	private String nonce;
	private JPanel scrollView;
	private long accountId;
	private String accountUrl;

	public MainFrame() {
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	@Override
	protected void addComponents(JPanel scrollView) {
		this.scrollView = scrollView;
		U.setMargins(scrollView, 10, 0);

		scrollView.add(Title.create());
		scrollView.add(AcmeVersionUI.create(this::setVersionAndAskForUrl));

	}

	private void setVersionAndAskForUrl(String version) {
		this.version = version;
		scrollView.add(AcmeUrlUI.create(version, this::setUrlAndQueryDirectory));
		validate();
	}

	private void setUrlAndQueryDirectory(String url) {
		this.url = url;
		scrollView.add(DirectoryUI.renderStarting());
		DirectoryRequest.send(om).then(this::directorySuccess, this::directoryFailure);
		validate();
	}

	private void directorySuccess(AcmeDirectoryInfos2 infos) {
		this.directoryInfos = infos;
		List<Component> responseComponents = DirectoryUI.getSuccessComponents(infos);
		for (Component component : responseComponents) {
			scrollView.add(component);
		}
		scrollView.add(renderNewButtons());
		validate();
	}

	private void directoryFailure(Exception exception) {
		scrollView.add(DirectoryUI.getFailureComponent(exception));
		validate();
	}

	private void nonceClicked() {
		scrollView.add(NonceUI.renderGetting());
		validate();
		NonceRequest.send(directoryInfos).then(this::nonceSuccess, this::nonceFailure);

	}

	private void nonceSuccess(String nonce) {
		this.nonce = nonce;
		scrollView.add(NonceUI.renderSuccess(nonce));
		scrollView.add(renderNewButtons());
		validate();
	}

	private void nonceFailure(Exception ex) {
		scrollView.add(NonceUI.renderFailure(ex));
		validate();
	}

	private void createAccountClicked() {
		scrollView.add(AccountCreationUI.renderInput(this::createAccountProceed, this::createAccountCancel));
		validate();
	}

	private void createAccountProceed(String contact) {
		scrollView.add(AccountCreationUI.renderCalling());
		AccountCreationRequest.send(directoryInfos, nonce, om, contact).then(this::createAccountSuccess, this::createAccountFailure);
		validate();
	}

	private void createAccountCancel() {
		addM(new JLabel("Account creation cancelled"));
		addM(renderNewButtons());
		validate();
	}

	private void createAccountSuccess(AcmeAccount account) {
		nonce = account.getNonce();
		accountId = account.getId();
		accountUrl = account.getUrl();
		addM(AccountCreationUI.renderSuccess(account));
		addM(renderNewButtons());
		validate();
	}

	private void createAccountFailure(Exception ex) {
		addM(ExceptionUI.render(ex));
		addM(renderNewButtons());
		validate();
	}

	private void accountDetailsClicked() {
		addM(MessageUI.render("Account details :)"));
		validate();
	}

	private Component renderNewButtons() {
		Acme2Buttons buttonsFactory = new Acme2Buttons();

		buttonsFactory.setNonceEnabled(url != null);
		buttonsFactory.setCreateAccountEnabled(nonce != null);

		buttonsFactory.setNonceClicked(this::nonceClicked);
		buttonsFactory.setCreateAccountClicked(this::createAccountClicked);

		buttonsFactory.setAccountDetailsEnabled(accountUrl != null);
		buttonsFactory.setAccountDetailsClicked(this::accountDetailsClicked);
		return buttonsFactory.create();

	}

	@Override
	protected LayoutManager getLayout(Container target) {
		return new StackedLayout(5);
	}

	private void addM(List<Component> components) {
		for (Component component : components) {
			scrollView.add(component);
		}
	}

	private void addM(Component component) {
		scrollView.add(component);
	}
}
