package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.Authorization;
import example.company.acme.v2.Challenge;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.interactions.Interacter;
import xpdtr.acme.gui.interactions.UIInteraction;
import xpdtr.acme.gui.layout.FullWidthLayout;
import xpdtr.acme.gui.utils.Promise;
import xpdtr.acme.gui.utils.U;

public class AuthorizationDetailsInteraction extends UIInteraction {

	private UILogger logger;
	private AcmeSession session;
	private Container destination;
	private List<Component> buttons = new ArrayList<>();
	private Consumer<Authorization> after;

	public AuthorizationDetailsInteraction(Interacter interacter, JPanel container, UILogger logger,
			AcmeSession session, Consumer<Authorization> after) {
		super(interacter, container);
		this.logger = logger;
		this.session = session;
		this.after = after;
	}

	@Override
	protected void perform() {
		logger.beginGroup("Authorization details");
		destination = logger.getDestination();

		logger.message("Authorization details clicked");

		JPanel authPanel = new JPanel();
		authPanel.setLayout(new SameWidthLayout(5));

		for (String auth : session.getOrder().getAuthorizations()) {
			JButton button = new JButton(auth);
			buttons.add(button);
			authPanel.add(button);
			U.clicked(button, (e) -> {
				select(auth);
			});
		}
		destination.add(authPanel);

		JPanel cancelPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JButton cancel = new JButton("Cancel");
		U.clicked(cancel, this::cancel);
		buttons.add(cancel);
		cancelPanel.add(cancel);
		destination.add(cancelPanel);

	}

	private void select(String a) {
		interacter.perform(() -> {
			disable();
			logger.message("Chosen " + a);
			getAuthorizationDetails(a);
		});
	}

	private void cancel(ActionEvent e) {
		interacter.perform(() -> {
			disable();
			logger.message("Cancelled");
			logger.endGroup();
			after.accept(null);
		});
	}

	private void disable() {
		for (Component c : buttons) {
			c.setEnabled(false);
		}
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
			interacter.perform(() -> {
				logger.message("Success : got response for " + url);
				session.setAuthorization(o);
				for (Challenge c : o.getChallenges()) {
					logger.message(c.getUrl());
				}
				logger.endGroup();
				after.accept(o);
			});
		}, (e) -> {
			interacter.perform(() -> {
				logger.exception(e);
				logger.endGroup();
				after.accept(null);
			});
		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<Authorization> after) {
		new AuthorizationDetailsInteraction(interacter, container, logger, session, after).start();

	}

}
