package xpdtr.acme.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import xpdtr.acme.gui.components.AcmeGuiActions;
import xpdtr.acme.gui.components.AcmeUrlInteraction;
import xpdtr.acme.gui.components.AcmeVersionInteraction;
import xpdtr.acme.gui.components.BasicFrameWithVerticalScroll;
import xpdtr.acme.gui.components.ButtonsFactory;
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

	private KeyPairManager kpm;

	private Interacter interacter;

	private UILogger logger;

	private Buttons buttons;

	@Override
	public void init() {

		super.init();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(Title.create());

		contentPane.add(panel, BorderLayout.NORTH);

		kpm = new KeyPairManager(session, sessionContainer, getFrame(), this::updateButtons);

		interacter = new Interacter(getFrame(), this::autoscroll);

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
		ChallengeInteraction.perform(interacter, sessionContainer, logger, session, (challenge) -> {
			if (challenge != null) {
				session.setChallenge(challenge);
			}
			updateButtons();
		});
	}

	public void deactivateAccount() {

		AccountDeactivationInteraction.perform(interacter, sessionContainer, logger, session, (deactivated) -> {
			if (deactivated) {
				session.setAccount(null);
				session.setAuthorization(null);
				session.setChallenge(null);
				session.setOrder(null);
			}
			updateButtons();
		});
	}

	private void updateButtons() {

		if (buttons == null) {
			ButtonsFactory buttonsFactory = new ButtonsFactory();
			buttonsFactory.setClicked(AcmeGuiActions.CREATE_KEY_PAIR, this::createKeyPair);
			buttonsFactory.setClicked(AcmeGuiActions.SAVE_KEY_PAIR, kpm::saveKeyPair);
			buttonsFactory.setClicked(AcmeGuiActions.LOAD_KEY_PAIR, kpm::loadKeyPair);
			buttonsFactory.setClicked(AcmeGuiActions.NONCE, this::nonceClicked);
			buttonsFactory.setClicked(AcmeGuiActions.CREATE_ACCOUNT, this::createAccountClicked);
			buttonsFactory.setClicked(AcmeGuiActions.ACCOUNT_DETAILS, this::accountDetailsClicked);
			buttonsFactory.setClicked(AcmeGuiActions.ORDER, this::orderClicked);
			buttonsFactory.setClicked(AcmeGuiActions.AUTHORIZATION_DETAILS, this::authorizationDetailsClicked);
			buttonsFactory.setClicked(AcmeGuiActions.CHALLENGE, this::challengeClicked);
			buttonsFactory.setClicked(AcmeGuiActions.DEACTIVATE_ACCOUNT, this::deactivateAccount);

			buttons = buttonsFactory.render(contentPane);
		}

		buttons.setEnabled(AcmeGuiActions.CREATE_KEY_PAIR, true);
		buttons.setEnabled(AcmeGuiActions.SAVE_KEY_PAIR, session.getKeyPairWithJWK() != null);
		buttons.setEnabled(AcmeGuiActions.LOAD_KEY_PAIR, session.getAccount() == null);
		buttons.setEnabled(AcmeGuiActions.NONCE, session.getUrl() != null);
		buttons.setEnabled(AcmeGuiActions.CREATE_ACCOUNT,
				session.getNonce() != null && session.getKeyPairWithJWK() != null);
		buttons.setEnabled(AcmeGuiActions.ACCOUNT_DETAILS, session.getAccount() != null);
		buttons.setEnabled(AcmeGuiActions.ORDER, session.getAccount() != null);
		buttons.setEnabled(AcmeGuiActions.AUTHORIZATION_DETAILS, session.getOrder() != null);
		buttons.setEnabled(AcmeGuiActions.CHALLENGE, session.getAuthorization() != null);
		buttons.setEnabled(AcmeGuiActions.DEACTIVATE_ACCOUNT, session.getAccount() != null);

		buttons.update();

	}

	@Override
	protected LayoutManager getLayout(Container target) {
		StackedLayout sl = new StackedLayout(5);
		sl.setTopPadding(5);
		return sl;
	}

}
