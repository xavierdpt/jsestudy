package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import example.company.acme.v2.AcmeAccount;
import example.company.acme.v2.AcmeDirectoryInfos2;
import xpdtr.acme.gui.fiddling.BasicFrameWithVerticalScroll;

public class MainFrame extends BasicFrameWithVerticalScroll {

	private String version;
	private String url;
	private ObjectMapper om = new ObjectMapper();
	private AcmeDirectoryInfos2 directoryInfos;
	private String nonce;
	private JPanel scrollView;
	private long accountId;

	public MainFrame() {
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	@Override
	protected void addComponents(JPanel scrollView) {
		this.scrollView = scrollView;
		U.setMargins(scrollView, 10, 0);

		scrollView.add(Title.create());
		scrollView.add(AcmeVersionQuestion.create(this::setVersionAndAskForUrl));

	}

	private void setVersionAndAskForUrl(String version) {
		this.version = version;
		scrollView.add(AcmeUrlQuestion.create(version, this::setUrlAndQueryDirectory));
		validate();
	}

	private void setUrlAndQueryDirectory(String url) {
		this.url = url;
		scrollView.add(DirectoryGetter.renderStarting());
		DirectoryGetter.start(om).then(this::directorySuccess, this::directoryFailure);
		validate();
	}

	private void directorySuccess(AcmeDirectoryInfos2 infos) {
		this.directoryInfos = infos;
		List<Component> responseComponents = DirectoryGetter.getSuccessComponents(infos);
		for (Component component : responseComponents) {
			scrollView.add(component);
		}
		scrollView.add(renderNewButtons());
		validate();
	}

	private void directoryFailure(Exception exception) {
		scrollView.add(DirectoryGetter.getFailureComponent(exception));
		validate();
	}

	private void nonceClicked() {

		scrollView.add(Nonce.renderGetting());
		validate();
		Nonce.get(directoryInfos).then(this::nonceSuccess, this::nonceFailure);

	}

	private void nonceSuccess(String nonce) {
		this.nonce = nonce;
		scrollView.add(Nonce.renderSuccess(nonce));
		scrollView.add(renderNewButtons());
		validate();
	}

	private void nonceFailure(Exception ex) {
		scrollView.add(Nonce.renderFailure(ex));
		validate();
	}

	private void accountClicked() {
		scrollView.add(Account.renderInput(this::createAccount, this::cancelCreateAccount));
		validate();
	}

	private void createAccount(String contact) {
		scrollView.add(Account.renderCalling());
		Account.get(directoryInfos, nonce, om, contact).then(this::accountSuccess, this::accountFailure);
		validate();
	}

	private void cancelCreateAccount() {
		addM(new JLabel("Account creation cancelled"));
		addM(renderNewButtons());
		validate();
	}

	private void accountSuccess(AcmeAccount account) {
		nonce = account.getNonce();
		accountId = account.getId();
		addM(Account.renderSuccess(account));
		addM(renderNewButtons());
		validate();
	}

	private void accountFailure(Exception ex) {
		addM(ExceptionGui.render(ex));
		addM(renderNewButtons());
		validate();
	}

	private Component renderNewButtons() {
		Acme2ButtonsFactory buttonsFactory = new Acme2ButtonsFactory();

		buttonsFactory.setNonceEnabled(url != null);
		buttonsFactory.setAccountEnabled(nonce != null);

		buttonsFactory.setNonceClicked(this::nonceClicked);
		buttonsFactory.setAccountClicked(this::accountClicked);
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
