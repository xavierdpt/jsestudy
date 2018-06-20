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
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.AcmeOrderWithNonce;
import example.company.acme.v2.Authorization;
import example.company.acme.v2.Challenge;
import example.company.acme.v2.account.AcmeAccount;
import xpdtr.acme.gui.async.DirectoryRequest;
import xpdtr.acme.gui.async.OrderCreationRequest;
import xpdtr.acme.gui.components.Acme2Buttons;
import xpdtr.acme.gui.components.AcmeUrlUI;
import xpdtr.acme.gui.components.AcmeVersionUI;
import xpdtr.acme.gui.components.BasicFrameWithVerticalScroll;
import xpdtr.acme.gui.components.DirectoryUI;
import xpdtr.acme.gui.components.ExceptionUI;
import xpdtr.acme.gui.components.MessageUI;
import xpdtr.acme.gui.components.Title;
import xpdtr.acme.gui.interactions.NewAccountInteraction;
import xpdtr.acme.gui.interactions.NonceInteraction;
import xpdtr.acme.gui.layout.StackedLayout;
import xpdtr.acme.gui.utils.Promise;
import xpdtr.acme.gui.utils.U;

public class AcmeGui extends BasicFrameWithVerticalScroll {

	private String version;
	private String url;
	private ObjectMapper om = new ObjectMapper();
	private AcmeDirectoryInfos2 directoryInfos;
	private String nonce;
	private JPanel container;
	private AcmeAccount account;
	private AcmeOrderWithNonce order;
	private Authorization authorization;

	public AcmeGui() {
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	@Override
	protected void addComponents(JPanel container) {
		this.container = container;
		U.setMargins(container, 10, 0);

		container.add(Title.create());
		container.add(AcmeVersionUI.create(this::setVersionAndAskForUrl));

	}

	private void setVersionAndAskForUrl(String version) {
		this.version = version;
		container.add(AcmeUrlUI.create(version, this::setUrlAndQueryDirectory));
		validate();
	}

	private void setUrlAndQueryDirectory(String url) {
		this.url = url;
		container.add(DirectoryUI.renderStarting());
		DirectoryRequest.send(om).then(this::directorySuccess, this::directoryFailure);
		validate();
	}

	private void directorySuccess(AcmeDirectoryInfos2 infos) {
		this.directoryInfos = infos;
		List<Component> responseComponents = DirectoryUI.getSuccessComponents(infos);
		for (Component component : responseComponents) {
			container.add(component);
		}
		container.add(renderNewButtons());
		validate();
	}

	private void directoryFailure(Exception exception) {
		container.add(DirectoryUI.getFailureComponent(exception));
		validate();
	}

	private void nonceClicked() {
		new NonceInteraction(container, directoryInfos, this::validate, nonce -> {
			if (nonce != null) {
				this.nonce = nonce;
				addM(renderNewButtons());
			}
		}).start();
	}

	private void createAccountClicked() {

		Consumer<AcmeAccount> finished = (account) -> {
			if (account != null) {
				this.account = account;
				this.nonce = account.getNonce();
			}
			addM(renderNewButtons());
		};

		NewAccountInteraction interaction = new NewAccountInteraction(container, directoryInfos, nonce, om,
				this::validate, finished);

		interaction.start();

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
		new Promise<Authorization>((p) -> {
			try {
				Authorization r = Acme2.getAuthorization(url, om);
				p.success(r);
			} catch (Exception e1) {
				p.failure(e1);
			}

		}).then((Authorization o) -> {
			addM(MessageUI.render("Success : got response for " + url));
			this.authorization = o;
			for (Challenge c : o.getChallenges()) {
				addM(MessageUI.render(c.getUrl()));
			}
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

		buttonsFactory.setAccountDetailsEnabled(account != null);
		buttonsFactory.setAccountDetailsClicked(this::accountDetailsClicked);

		buttonsFactory.setOrderEnabled(account != null);
		buttonsFactory.setOrderClicked(this::orderClicked);

		buttonsFactory.setAuthorizationDetailsEnabled(order != null);
		buttonsFactory.setAuthorizationDetailsClicked(this::authorizationDetailsClicked);

		return buttonsFactory.create();

	}

	private void addM(List<Component> components) {
		for (Component component : components) {
			container.add(component);
		}
	}

	private void addM(Component component) {
		container.add(component);
	}

	@Override
	protected LayoutManager getLayout(Container target) {
		return new StackedLayout(5);
	}

}
