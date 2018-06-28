package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeException;
import example.company.acme.v2.Challenge;
import xpdtr.acme.gui.components.ExceptionUI;
import xpdtr.acme.gui.components.MessageUI;
import xpdtr.acme.gui.interactions.Interacter;
import xpdtr.acme.gui.interactions.UIInteraction;
import xpdtr.acme.gui.utils.Promise;
import xpdtr.acme.gui.utils.U;

public class ChallengeInteraction extends UIInteraction {

	private AcmeSession session;
	private Runnable finished;

	public ChallengeInteraction(Interacter interacter, Container container, AcmeSession session, Runnable finished) {
		super(interacter, container);
		this.session = session;
		this.finished = finished;
	}

	@Override
	public void perform() {

		Component label = MessageUI.render("Which challenge ?");

		JComboBox<String> cb = new JComboBox<>();

		List<String> urls = new ArrayList<>();

		for (Challenge challenge : session.getAuthorization().getChallenges()) {
			urls.add(challenge.getUrl());

		}
		Collections.sort(urls);

		for (String url : urls) {
			cb.addItem(url);
		}

		JButton chooseButton = new JButton("Choose");
		JButton cancelButton = new JButton("Cancel");

		Runnable disabler = U.disabler(cancelButton, chooseButton, cb);

		U.clicked(cancelButton, (e) -> {
			disabler.run();
			finished.run();
		});

		U.clicked(chooseButton, (e) -> {
			disabler.run();
			next((String) cb.getSelectedItem());
		});

		U.addM(container, label);
		U.addM(container, cb);
		U.addM(container, chooseButton);
		U.addM(container, cancelButton);
	}

	private void next(String url) {
		U.addM(container, MessageUI.render("Sending..."));
		new Promise<Challenge>((Promise<Challenge> p) -> {
			try {
				p.success(Acme2.challenge(session, url));
			} catch (AcmeException e) {
				p.failure(e);
			}
		}).then((challenge) -> {
			session.setChallenge(challenge);
			finished.run();
		}, (e) -> {
			U.addM(container, ExceptionUI.render(e));
			finished.run();
		});

	}
}
