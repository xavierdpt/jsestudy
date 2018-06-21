package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.interfaces.ECPrivateKey;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeOrderWithNonce;
import example.company.acme.v2.Authorization;
import example.company.acme.v2.Challenge;
import xpdtr.acme.gui.async.OrderCreationRequest;
import xpdtr.acme.gui.components.Acme2Buttons;
import xpdtr.acme.gui.components.AcmeUrlUI;
import xpdtr.acme.gui.components.AcmeVersionUI;
import xpdtr.acme.gui.components.BasicFrameWithVerticalScroll;
import xpdtr.acme.gui.components.ExceptionUI;
import xpdtr.acme.gui.components.MessageUI;
import xpdtr.acme.gui.components.Title;
import xpdtr.acme.gui.interactions.DirectoryInteraction;
import xpdtr.acme.gui.interactions.NewAccountInteraction;
import xpdtr.acme.gui.interactions.NonceInteraction;
import xpdtr.acme.gui.layout.StackedLayout;
import xpdtr.acme.gui.utils.Promise;
import xpdtr.acme.gui.utils.U;

public class AcmeGui extends BasicFrameWithVerticalScroll {

	private Container container;

	private AcmeSession session = new AcmeSession();

	@Override
	protected void addComponents(JPanel container) {
		this.container = container;
		U.setMargins(container, 10, 0);

		container.add(Title.create());
		container.add(AcmeVersionUI.create(this::setVersionAndAskForUrl));

	}

	private void setVersionAndAskForUrl(String version) {
		session.setVersion(version);
		container.add(AcmeUrlUI.create(version, this::setUrlAndQueryDirectory));
		validate();
	}

	private void setUrlAndQueryDirectory(String url) {
		session.setUrl(url);
		new DirectoryInteraction(container, session, this::validate, this::addNewButtons).start();
	}

	private void nonceClicked() {
		new NonceInteraction(container, session, this::validate, this::addNewButtons).start();
	}

	private void createAccountClicked() {
		new NewAccountInteraction(container, session, this::validate, this::addNewButtons).start();
	}

	private void accountDetailsClicked() {
		U.addM(container, MessageUI.render("Account details :)"));
		validate();
	}

	private void orderClicked() {
		U.addM(container, MessageUI.render("New order clicked"));
		OrderCreationRequest
				.send(session.getInfos(), "" + session.getAccount().getUrl(), session.getNonce(), session.getOm(),
						(ECPrivateKey) session.getAccount().getPrivateKey())
				.then(this::createOrderSuccess, this::createOrderFailure);
		validate();
	}

	private void createOrderSuccess(AcmeOrderWithNonce order) {
		session.setOrder(order);
		U.addM(container, MessageUI.render("Success"));

		List<String> authorizations = order.getContent().getAuthorizations();
		for (String a : authorizations) {
			U.addM(container, MessageUI.render("Authorization " + a));
		}
		U.addM(container, MessageUI.render("Finalize " + order.getContent().getFinalize()));

		U.addM(container, createNewButtons());
		validate();
		try {
			System.out.println(session.getOm().writeValueAsString(order));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createOrderFailure(Exception ex) {
		U.addM(container, ExceptionUI.render(ex));
		U.addM(container, createNewButtons());
		validate();
	}

	private void authorizationDetailsClicked() {
		U.addM(container, MessageUI.render("Authorization details clicked"));
		JComboBox<String> authorizationsCB = new JComboBox<>(
				session.getOrder().getContent().getAuthorizations().toArray(new String[] {}));
		U.addM(container, authorizationsCB);
		JButton choose = new JButton("Choose");
		JButton cancel = new JButton("Cancel");
		U.addM(container, choose);
		U.addM(container, cancel);

		choose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				authorizationsCB.setEnabled(false);
				choose.setEnabled(false);
				cancel.setEnabled(false);
				String auth = authorizationsCB.getSelectedItem().toString();
				U.addM(container, MessageUI.render("Chosen " + auth));
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
				U.addM(container, MessageUI.render("Cancelled"));
				U.addM(container, createNewButtons());
				validate();
			}
		});

		validate();
	}

	private void getAuthorizationDetails(String url) {
		new Promise<Authorization>((p) -> {
			try {
				Authorization r = Acme2.getAuthorization(url, session.getOm());
				p.success(r);
			} catch (Exception e1) {
				p.failure(e1);
			}

		}).then((Authorization o) -> {
			U.addM(container, MessageUI.render("Success : got response for " + url));
			session.setAuthorization(o);
			for (Challenge c : o.getChallenges()) {
				U.addM(container, MessageUI.render(c.getUrl()));
			}
			validate();
		}, (e) -> {
			U.addM(container, ExceptionUI.render(e));
			validate();
		});
	}

	private void addNewButtons() {
		U.addM(container, createNewButtons());
	}

	private Component createNewButtons() {
		Acme2Buttons buttonsFactory = new Acme2Buttons();

		buttonsFactory.setNonceEnabled(session.getUrl() != null);
		buttonsFactory.setCreateAccountEnabled(session.getNonce() != null);

		buttonsFactory.setNonceClicked(this::nonceClicked);
		buttonsFactory.setCreateAccountClicked(this::createAccountClicked);

		buttonsFactory.setAccountDetailsEnabled(session.getAccount() != null);
		buttonsFactory.setAccountDetailsClicked(this::accountDetailsClicked);

		buttonsFactory.setOrderEnabled(session.getAccount() != null);
		buttonsFactory.setOrderClicked(this::orderClicked);

		buttonsFactory.setAuthorizationDetailsEnabled(session.getOrder() != null);
		buttonsFactory.setAuthorizationDetailsClicked(this::authorizationDetailsClicked);

		return buttonsFactory.create();

	}

	@Override
	protected LayoutManager getLayout(Container target) {
		return new StackedLayout(5);
	}

}
