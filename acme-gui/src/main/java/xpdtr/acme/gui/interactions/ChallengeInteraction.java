package xpdtr.acme.gui.interactions;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeException;
import example.company.acme.v2.Challenge;
import xpdtr.acme.gui.SameWidthLayout;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.utils.Promise;
import xpdtr.acme.gui.utils.U;

public class ChallengeInteraction extends UIInteraction {

	private AcmeSession session;
	private Consumer<Challenge> consumer;
	private UILogger logger;
	private Container destination;
	private List<Component> buttons = new ArrayList<>();

	public ChallengeInteraction(Interacter interacter, Container container, UILogger logger, AcmeSession session,
			Consumer<Challenge> finished) {
		super(interacter, container);
		this.logger = logger;
		this.session = session;
		this.consumer = finished;
	}

	@Override
	public void perform() {

		destination = logger.beginGroup("Challenge");

		logger.message("Which challenge ?");

		List<String> urls = new ArrayList<>();

		for (Challenge challenge : session.getAuthorization().getChallenges()) {

			String url = challenge.getUrl();

			urls.add(url);

		}
		Collections.sort(urls);

		JPanel choices = new JPanel(new SameWidthLayout(5));
		for (String url : urls) {
			JButton button = new JButton(url);
			U.clicked(button, (e) -> {
				select(url);
			});
			choices.add(button);
			buttons.add(button);
		}

		JButton cancelButton = new JButton("Cancel");
		buttons.add(cancelButton);

		U.clicked(cancelButton, this::cancel);

		destination.add(choices);
		
		logger.leading(cancelButton);
	}

	private void select(String url) {
		disable();
		next(url);
	}

	private void cancel(ActionEvent e) {
		interacter.perform(() -> {
			disable();
			logger.message("Cancelled");
			logger.endGroup();
			consumer.accept(null);
		});
	}

	private void disable() {
		for (Component button : buttons) {
			button.setEnabled(false);
		}
	}

	private void next(String url) {
		logger.message("Sending...");
		new Promise<Challenge>((Promise<Challenge> p) -> {
			try {
				p.success(Acme2.challenge(session, url));
			} catch (AcmeException e) {
				p.failure(e);
			}
		}).then((challenge) -> {
			interacter.perform(() -> {
				logger.endGroup();
				consumer.accept(challenge);
			});

		}, (e) -> {
			interacter.perform(() -> {
				logger.exception(e);
				logger.endGroup();
				consumer.accept(null);
			});

		});

	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<Challenge> consumer) {
		new ChallengeInteraction(interacter, container, logger, session, consumer).start();

	}
}
