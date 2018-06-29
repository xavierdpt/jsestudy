package xpdtr.acme.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.interfaces.ECPrivateKey;
import java.util.List;

import javax.swing.BoxLayout;
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
import xpdtr.acme.gui.components.Acme2Buttons.Action;
import xpdtr.acme.gui.components.AcmeUrlInteraction;
import xpdtr.acme.gui.components.AcmeVersionInteraction;
import xpdtr.acme.gui.components.BasicFrameWithVerticalScroll;
import xpdtr.acme.gui.components.ExceptionUI;
import xpdtr.acme.gui.components.MessageUI;
import xpdtr.acme.gui.components.Title;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.interactions.DirectoryInteraction;
import xpdtr.acme.gui.interactions.Interacter;
import xpdtr.acme.gui.interactions.NewAccountInteraction;
import xpdtr.acme.gui.interactions.NonceInteraction;
import xpdtr.acme.gui.layout.StackedLayout;
import xpdtr.acme.gui.utils.Promise;
import xpdtr.acme.gui.utils.U;

public class AcmeGui extends BasicFrameWithVerticalScroll {

	private AcmeSession session = new AcmeSession();

	private Acme2Buttons buttons;

	private KeyPairManager kpm;

	private Interacter interacter;

	private UILogger logger;

	@Override
	public void init() {

		super.init();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(Title.create());

		contentPane.add(panel, BorderLayout.NORTH);

		kpm = new KeyPairManager(session, sessionContainer, getFrame(), this::updateButtons);

		interacter = new Interacter(getFrame());

		logger = new UILogger(sessionContainer);

		U.setMargins(sessionContainer, 10, 0);

		askForVersion();

	}

	private void askForVersion() {
		AcmeVersionInteraction.perform(interacter, sessionContainer, logger, (String version) -> {
			session.setVersion(version);
			askForUrl();
		});

	}

	private void askForUrl() {
		AcmeUrlInteraction.perform(interacter, sessionContainer, logger, session, (String url) -> {
			session.setUrl(url);
			queryDirectory();
		});
	}

	private void queryDirectory() {
		DirectoryInteraction.perform(interacter, sessionContainer, logger, session, (infos) -> {
			session.setInfos(infos);
			updateButtons();
		});
	}

	private void nonceClicked() {
		NonceInteraction.perform(interacter, sessionContainer, logger, session, (nonce) -> {
			if (nonce != null) {
				session.setNonce(nonce);
			}
			updateButtons();
		});

	}

	private void createKeyPair() {
		CreateKeyPairInteraction.perform(interacter, sessionContainer, logger, (keyPair) -> {
			if (keyPair != null) {
				session.setKeyPairWithJWK(keyPair);
			}
			updateButtons();
		});
	}

	private void createAccountClicked() {

		NewAccountInteraction.perform(interacter, sessionContainer, logger, session, (accountWithNonce) -> {
			if (accountWithNonce != null) {
				session.setNonce(accountWithNonce.getNonce());
				session.setAccount(accountWithNonce.getContent());
			}
			updateButtons();
		});

	}

	private void accountDetailsClicked() {
		U.addM(sessionContainer, MessageUI.render("Account details :)"));
		validate();
	}

	private void orderClicked() {
		U.addM(sessionContainer, MessageUI.render("New order clicked"));
		OrderCreationRequest
				.send(session.getInfos(), "" + session.getAccount().getUrl(), session.getNonce(), session.getOm(),
						(ECPrivateKey) session.getKeyPairWithJWK().getKeyPair().getPrivate(), "example.com")
				.then(this::createOrderSuccess, this::createOrderFailure);
		validate();
	}

	private void createOrderSuccess(AcmeOrderWithNonce order) {
		session.setOrder(order);
		U.addM(sessionContainer, MessageUI.render("Success"));

		List<String> authorizations = order.getContent().getAuthorizations();
		for (String a : authorizations) {
			U.addM(sessionContainer, MessageUI.render("Authorization " + a));
		}
		U.addM(sessionContainer, MessageUI.render("Finalize " + order.getContent().getFinalize()));

		updateButtons();
		validate();
		try {
			System.out.println(session.getOm().writeValueAsString(order));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createOrderFailure(Exception ex) {
		U.addM(sessionContainer, ExceptionUI.render(ex));
		updateButtons();
		validate();
	}

	private void authorizationDetailsClicked() {
		U.addM(sessionContainer, MessageUI.render("Authorization details clicked"));
		JComboBox<String> authorizationsCB = new JComboBox<>(
				session.getOrder().getContent().getAuthorizations().toArray(new String[] {}));
		U.addM(sessionContainer, authorizationsCB);
		JButton choose = new JButton("Choose");
		JButton cancel = new JButton("Cancel");
		U.addM(sessionContainer, choose);
		U.addM(sessionContainer, cancel);

		choose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				authorizationsCB.setEnabled(false);
				choose.setEnabled(false);
				cancel.setEnabled(false);
				String auth = authorizationsCB.getSelectedItem().toString();
				U.addM(sessionContainer, MessageUI.render("Chosen " + auth));
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
				U.addM(sessionContainer, MessageUI.render("Cancelled"));
				updateButtons();
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
			U.addM(sessionContainer, MessageUI.render("Success : got response for " + url));
			session.setAuthorization(o);
			for (Challenge c : o.getChallenges()) {
				U.addM(sessionContainer, MessageUI.render(c.getUrl()));
			}
			updateButtons();
			validate();
		}, (e) -> {
			U.addM(sessionContainer, ExceptionUI.render(e));
			updateButtons();
			validate();
		});
	}

	private void challengeClicked() {
		new ChallengeInteraction(interacter, sessionContainer, session, this::updateButtons).start();
	}

	private void accountDetails() {
		interacter.perform(() -> {
			logger.beginGroup("Account details");
			logger.message("Account details clicked");
			logger.message(session.getAccount().getUrl());
			logger.endGroup();
		});
	}

	private void updateButtons() {

		if (buttons == null) {
			buttons = new Acme2Buttons();
		}

		buttons.setEnabled(Action.CREATE_KEY_PAIR, true);
		buttons.setClicked(Action.CREATE_KEY_PAIR, this::createKeyPair);

		buttons.setEnabled(Action.SAVE_KEY_PAIR, session.getKeyPairWithJWK() != null);
		buttons.setClicked(Action.SAVE_KEY_PAIR, kpm::saveKeyPair);

		buttons.setEnabled(Action.LOAD_KEY_PAIR, session.getAccount() == null);
		buttons.setClicked(Action.LOAD_KEY_PAIR, kpm::loadKeyPair);

		buttons.setEnabled(Action.NONCE, session.getUrl() != null);
		buttons.setClicked(Action.NONCE, this::nonceClicked);

		buttons.setEnabled(Action.CREATE_ACCOUNT, session.getNonce() != null && session.getKeyPairWithJWK() != null);
		buttons.setClicked(Action.CREATE_ACCOUNT, this::createAccountClicked);

		buttons.setEnabled(Action.ACCOUNT_DETAILS, session.getAccount() != null);
		buttons.setClicked(Action.ACCOUNT_DETAILS, this::accountDetailsClicked);

		buttons.setEnabled(Action.ORDER, session.getAccount() != null);
		buttons.setClicked(Action.ORDER, this::orderClicked);

		buttons.setEnabled(Action.AUTHORIZATION_DETAILS, session.getOrder() != null);
		buttons.setClicked(Action.AUTHORIZATION_DETAILS, this::authorizationDetailsClicked);

		buttons.setEnabled(Action.CHALLENGE, session.getAuthorization() != null);
		buttons.setClicked(Action.CHALLENGE, this::challengeClicked);

		buttons.setEnabled(Action.ACCOUNT_DETAILS, session.getAccount() != null);
		buttons.setClicked(Action.ACCOUNT_DETAILS, this::accountDetails);

		Component rendered = buttons.render();

		if (rendered != null) {
			contentPane.add(rendered, BorderLayout.SOUTH);
		} else {
		}

		validate();

	}

	@Override
	protected LayoutManager getLayout(Container target) {
		StackedLayout sl = new StackedLayout(5);
		sl.setTopPadding(5);
		return sl;
	}

}
