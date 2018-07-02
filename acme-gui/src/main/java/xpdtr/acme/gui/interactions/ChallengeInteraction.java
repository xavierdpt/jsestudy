package xpdtr.acme.gui.interactions;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeResponse;
import example.company.acme.v2.Challenge;
import xpdtr.acme.gui.SameWidthLayout;
import xpdtr.acme.gui.components.UILogger;
import xpdtr.acme.gui.utils.Promise;
import xpdtr.acme.gui.utils.U;

public class ChallengeInteraction extends UIInteraction {

	private enum ChallengeType {
		HTTP, DNS, TLS
	};

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

		Map<String, String> titles = new HashMap<>();

		for (Challenge challenge : session.getAuthorization().getChallenges()) {

			String url = challenge.getUrl();
			ChallengeType prefix = getChallengeType(challenge);
			if (prefix != null) {
				titles.put(url, "[" + prefix + "] " + url);
			} else {
				titles.put(url, url);
			}

			urls.add(url);

		}
		Collections.sort(urls);

		JPanel choices = new JPanel(new SameWidthLayout(5));
		for (String url : urls) {
			JButton button = new JButton(titles.get(url));
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

	private ChallengeType getChallengeType(Challenge challenge) {
		switch (challenge.getType()) {
		case "http-01":
			return ChallengeType.HTTP;
		case "dns-01":
			return ChallengeType.DNS;
		case "tls-alpn-01":
			return ChallengeType.TLS;
		default:
			return null;
		}
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
		logger.message("Sending " + url + "...");
		send(url).then(this::handleResponse);
	}

	private Promise<AcmeResponse<Challenge>> send(String url) {
		return new Promise<>(promise -> {
			promise.done(Acme2.challenge(session, url));
		});
	}

	private void handleResponse(AcmeResponse<Challenge> response) {
		interacter.perform(() -> {
			if (response.isFailed()) {
				logger.message(response.getFailureDetails());
				logger.endGroup();
				consumer.accept(null);
			} else {
				logger.message(response.getResponseText(), true);
				Challenge challenge = response.getContent();
				if (getChallengeType(challenge) == ChallengeType.HTTP) {
					String url = session.getAuthorization().getIdentifier().getValue();
					logger.message(
							"To respond to this challenge, make the following URL respond with the following content:");
					logger.message("URL : http://" + url + "/.well-known/acme-challenge/" + challenge.getToken());
					logger.message("Token : " + challenge.getToken());
				}
				logger.endGroup();
				consumer.accept(challenge);
			}
		});
	}

	public static void perform(Interacter interacter, JPanel container, UILogger logger, AcmeSession session,
			Consumer<Challenge> consumer) {
		new ChallengeInteraction(interacter, container, logger, session, consumer).start();

	}
}
