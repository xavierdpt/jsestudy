package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.interfaces.ECPrivateKey;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.AcmeOrderWithNonce;
import example.company.acme.v2.account.AcmeAccount;
import xpdtr.acme.gui.async.AccountCreationRequest;
import xpdtr.acme.gui.async.DirectoryRequest;
import xpdtr.acme.gui.async.NonceRequest;
import xpdtr.acme.gui.components.AccountCreationUI;
import xpdtr.acme.gui.components.Acme2Buttons;
import xpdtr.acme.gui.components.AcmeUrlUI;
import xpdtr.acme.gui.components.AcmeVersionUI;
import xpdtr.acme.gui.components.BasicFrameWithVerticalScroll;
import xpdtr.acme.gui.components.DirectoryUI;
import xpdtr.acme.gui.components.ExceptionUI;
import xpdtr.acme.gui.components.MessageUI;
import xpdtr.acme.gui.components.NonceUI;
import xpdtr.acme.gui.components.Title;
import xpdtr.acme.gui.layout.StackedLayout;
import xpdtr.acme.gui.utils.Promise;
import xpdtr.acme.gui.utils.U;

public class AcmeGui extends BasicFrameWithVerticalScroll {

	private String version;
	private String url;
	private ObjectMapper om = new ObjectMapper();
	private AcmeDirectoryInfos2 directoryInfos;
	private String nonce;
	private JPanel scrollView;
	private long accountId;
	private String accountUrl;
	private AcmeAccount account;
	private AcmeOrderWithNonce order;

	public AcmeGui() {
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
		AccountCreationRequest.send(directoryInfos, nonce, om, contact).then(this::createAccountSuccess,
				this::createAccountFailure);
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
		this.account = account;
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

	private void orderClicked() {
		addM(MessageUI.render("New order clicked"));
		OrderCreationRequest
				.send(directoryInfos, "" + account.getUrl(), nonce, om, (ECPrivateKey) account.getPrivateKey())
				.then(this::createOrderSuccess, this::createOrderFailure);
		validate();
	}

	private void createOrderSuccess(AcmeOrderWithNonce order) {
		this.order = order;
		addM(MessageUI.render("Success"));

		List<String> authorizations = order.getContent().getAuthorizations();
		for (String a : authorizations) {
			addM(MessageUI.render("Authorization " + a));
		}
		addM(MessageUI.render("Finalize " + order.getContent().getFinalize()));

		addM(renderNewButtons());
		validate();
		try {
			System.out.println(om.writeValueAsString(order));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createOrderFailure(Exception ex) {
		addM(ExceptionUI.render(ex));
		addM(renderNewButtons());
		validate();
	}

	private void authorizationDetailsClicked() {
		addM(MessageUI.render("Authorization details clicked"));
		JComboBox<String> authorizationsCB = new JComboBox<>(
				order.getContent().getAuthorizations().toArray(new String[] {}));
		addM(authorizationsCB);
		JButton choose = new JButton("Choose");
		JButton cancel = new JButton("Cancel");
		addM(choose);
		addM(cancel);

		choose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				authorizationsCB.setEnabled(false);
				choose.setEnabled(false);
				cancel.setEnabled(false);
				String auth = authorizationsCB.getSelectedItem().toString();
				addM(MessageUI.render("Chosen " + auth));
				getAuthorizationDetails(auth);
				validate();
			}
		});

		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				authorizationsCB.setEnabled(false);
				choose.setEnabled(false);
				cancel.setEnabled(false);
				addM(MessageUI.render("Cancelled"));
				addM(renderNewButtons());
				validate();
			}
		});

		validate();
	}

	private void getAuthorizationDetails(String url) {
		new Promise<JsonNode>((p) -> {
			try {
				JsonNode r = Acme2.getAuthorization(url, om);
				p.success(r);
			} catch (Exception e1) {
				p.failure(e1);
			}

		}).then((o) -> {
			try {
				String str = o.toString();
				Timer tim = new Timer();
				tim.schedule(new TimerTask() {
					@Override
					public void run() {
						System.out.println(str);
					}
				}, 1000);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			addM(MessageUI.render("Success : got response for " + url));
			validate();
		}, (e) -> {
			addM(ExceptionUI.render(e));
			validate();
		});
	}

	private Component renderNewButtons() {
		Acme2Buttons buttonsFactory = new Acme2Buttons();

		buttonsFactory.setNonceEnabled(url != null);
		buttonsFactory.setCreateAccountEnabled(nonce != null);

		buttonsFactory.setNonceClicked(this::nonceClicked);
		buttonsFactory.setCreateAccountClicked(this::createAccountClicked);

		buttonsFactory.setAccountDetailsEnabled(accountUrl != null);
		buttonsFactory.setAccountDetailsClicked(this::accountDetailsClicked);

		buttonsFactory.setOrderEnabled(accountUrl != null);
		buttonsFactory.setOrderClicked(this::orderClicked);

		buttonsFactory.setAuthorizationDetailsEnabled(order != null);
		buttonsFactory.setAuthorizationDetailsClicked(this::authorizationDetailsClicked);

		return buttonsFactory.create();

	}

	private void addM(List<Component> components) {
		for (Component component : components) {
			scrollView.add(component);
		}
	}

	private void addM(Component component) {
		scrollView.add(component);
	}

	@Override
	protected LayoutManager getLayout(Container target) {
		return new StackedLayout(5);
	}
}
