package xpdtr.acme.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import xpdtr.acme.gui.components.Acme2Buttons;
import xpdtr.acme.gui.components.Acme2Buttons.Action;
import xpdtr.acme.gui.components.AcmeUrlInteraction;
import xpdtr.acme.gui.components.AcmeVersionInteraction;
import xpdtr.acme.gui.components.BasicFrameWithVerticalScroll;
import xpdtr.acme.gui.components.Title;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.interactions.AccountDetailsInteraction;
import xpdtr.acme.gui.interactions.ChallengeInteraction;
import xpdtr.acme.gui.interactions.CreateKeyPairInteraction;
import xpdtr.acme.gui.interactions.DirectoryInteraction;
import xpdtr.acme.gui.interactions.Interacter;
import xpdtr.acme.gui.interactions.NewAccountInteraction;
import xpdtr.acme.gui.interactions.NewOrderInteraction;
import xpdtr.acme.gui.interactions.NonceInteraction;
import xpdtr.acme.gui.layout.StackedLayout;
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
		AccountDetailsInteraction.perform(interacter, sessionContainer, logger, session, (response) -> {
			if (response != null) {
				if (response.getNonce() != null) {
					session.setNonce(response.getNonce());
				}
			}
			updateButtons();
		});
	}

	private void orderClicked() {
		NewOrderInteraction.perform(interacter, sessionContainer, logger, session, (orderWithNonce) -> {
			if (orderWithNonce != null) {
				session.setNonce(orderWithNonce.getNonce());
				session.setOrder(orderWithNonce.getContent());
			}
			updateButtons();
		});
	}

	private void authorizationDetailsClicked() {

		AuthorizationDetailsInteraction.perform(interacter, sessionContainer, logger, session, (auth) -> {
			if (auth != null) {
				session.setAuthorization(auth);
			}
			updateButtons();
		});

	}

	private void challengeClicked() {
		new ChallengeInteraction(interacter, sessionContainer, session, this::updateButtons).start();
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
