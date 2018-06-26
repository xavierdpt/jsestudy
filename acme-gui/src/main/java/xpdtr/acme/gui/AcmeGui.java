package xpdtr.acme.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.interfaces.ECPrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileManager.Location;

import org.omg.CORBA.INV_FLAG;

import com.fasterxml.jackson.databind.JsonNode;

import example.company.acme.AcmeSession;
import example.company.acme.crypto.KPG;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeOrderWithNonce;
import example.company.acme.v2.Authorization;
import example.company.acme.v2.Challenge;
import xpdtr.acme.gui.async.OrderCreationRequest;
import xpdtr.acme.gui.components.Acme2Buttons;
import xpdtr.acme.gui.components.Acme2Buttons.Action;
import xpdtr.acme.gui.components.AcmeUrlUI;
import xpdtr.acme.gui.components.AcmeVersionUI;
import xpdtr.acme.gui.components.BasicFrameWithVerticalScroll;
import xpdtr.acme.gui.components.ExceptionUI;
import xpdtr.acme.gui.components.SelectableLabelUI;
import xpdtr.acme.gui.components.SessionUI;
import xpdtr.acme.gui.components.Title;
import xpdtr.acme.gui.interactions.DirectoryInteraction;
import xpdtr.acme.gui.interactions.NewAccountInteraction;
import xpdtr.acme.gui.interactions.NonceInteraction;
import xpdtr.acme.gui.layout.StackedLayout;
import xpdtr.acme.gui.utils.Promise;
import xpdtr.acme.gui.utils.U;

public class AcmeGui extends BasicFrameWithVerticalScroll {

	private Container sessionContainer;

	private AcmeSession session = new AcmeSession();

	private Acme2Buttons buttons;

	private JComponent stateContainer;

	@Override
	public void init() {
		super.init();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(Title.create());

		contentPane.add(panel, BorderLayout.NORTH);
	}

	@Override
	protected void addComponents(JComponent sessionContainer, JComponent stateContainer) {

		this.sessionContainer = sessionContainer;
		this.stateContainer = stateContainer;

		U.setMargins(sessionContainer, 10, 0);
		new AcmeVersionUI(sessionContainer, session, this::validate, this::setVersionAndAskForUrl).start();

	}

	private void setVersionAndAskForUrl() {
		sessionChanged();
		sessionContainer.add(AcmeUrlUI.create(session.getVersion(), this::setUrlAndQueryDirectory));
		validate();
	}

	private void setUrlAndQueryDirectory(String url) {
		session.setUrl(url);
		sessionChanged();
		new DirectoryInteraction(sessionContainer, session, this::validate, this::updateButtons).start();
	}

	private void nonceClicked() {
		new NonceInteraction(sessionContainer, session, this::validate, this::updateButtons).start();
	}

	private void createAccountClicked() {
		new NewAccountInteraction(sessionContainer, session, this::validate, this::updateButtons).start();
	}

	private void accountDetailsClicked() {
		U.addM(sessionContainer, SelectableLabelUI.render("Account details :)"));
		validate();
	}

	private void orderClicked() {
		U.addM(sessionContainer, SelectableLabelUI.render("New order clicked"));
		OrderCreationRequest
				.send(session.getInfos(), "" + session.getAccount().getUrl(), session.getNonce(), session.getOm(),
						(ECPrivateKey) session.getAccount().getPrivateKey())
				.then(this::createOrderSuccess, this::createOrderFailure);
		validate();
	}

	private void createOrderSuccess(AcmeOrderWithNonce order) {
		session.setOrder(order);
		U.addM(sessionContainer, SelectableLabelUI.render("Success"));

		List<String> authorizations = order.getContent().getAuthorizations();
		for (String a : authorizations) {
			U.addM(sessionContainer, SelectableLabelUI.render("Authorization " + a));
		}
		U.addM(sessionContainer, SelectableLabelUI.render("Finalize " + order.getContent().getFinalize()));

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
		U.addM(sessionContainer, SelectableLabelUI.render("Authorization details clicked"));
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
				U.addM(sessionContainer, SelectableLabelUI.render("Chosen " + auth));
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
				U.addM(sessionContainer, SelectableLabelUI.render("Cancelled"));
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
			U.addM(sessionContainer, SelectableLabelUI.render("Success : got response for " + url));
			session.setAuthorization(o);
			for (Challenge c : o.getChallenges()) {
				U.addM(sessionContainer, SelectableLabelUI.render(c.getUrl()));
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
		new ChallengeInteraction(sessionContainer, session, this::validate, this::updateButtons).start();
	}

	private void createKeyPair() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				session.setKeyPair(KPG.newECP256KeyPair());
				return null;
			}

			protected void done() {
				try {
					get();
					U.addM(sessionContainer, SelectableLabelUI.render("New key pair created"));
				} catch (Exception e) {
					U.addM(sessionContainer, ExceptionUI.render(e));
				}
				updateButtons();
			};
		}.execute();
	}

	private void saveKeyPair() {

		JFileChooser jfc = new JFileChooser();
		int r = jfc.showOpenDialog(getFrame());
		U.addM(sessionContainer, SelectableLabelUI.render("Returned value : " + r));
		if (r == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			U.addM(sessionContainer, SelectableLabelUI.render(file.getAbsolutePath()));
			try {
				FileWriter fw = new FileWriter(file);
				KeyPair kp = session.getKeyPair();
				Map<String, Object> map = new HashMap<>();
				map.put("public", kp.getPublic().getEncoded());
				map.put("private", kp.getPublic().getEncoded());
				session.getOm().writeValue(fw, map);
				fw.close();
				U.addM(sessionContainer, SelectableLabelUI.render("Key pair saved"));
			} catch (IOException e) {
				U.addM(sessionContainer, ExceptionUI.render(e));
			}
		} else {
			U.addM(sessionContainer, SelectableLabelUI.render("Cancelled"));
		}
		updateButtons();

	}

	private void loadKeyPair() {
		JFileChooser jfc = new JFileChooser();
		int r = jfc.showOpenDialog(getFrame());
		U.addM(sessionContainer, SelectableLabelUI.render("Returned value : " + r));
		if (r == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			U.addM(sessionContainer, SelectableLabelUI.render(file.getAbsolutePath()));
			if (!file.exists() || !file.isFile() || !file.canRead()) {
				U.addM(sessionContainer, SelectableLabelUI.render("File is not a readable existing file"));
			} else {
				try {
					JsonNode tree = session.getOm().readTree(new FileReader(file));
					U.addM(sessionContainer, SelectableLabelUI.render("Public key : "+tree.get("public")));
				} catch (Exception e) {
					U.addM(sessionContainer, ExceptionUI.render(e));
				}
			}
		} else {
			U.addM(sessionContainer, SelectableLabelUI.render("Cancelled"));
		}
		updateButtons();
	}

	private void updateButtons() {

		sessionChanged();

		if (buttons == null) {
			buttons = new Acme2Buttons();
		}

		buttons.setEnabled(Action.CREATE_KEY_PAIR, true);
		buttons.setClicked(Action.CREATE_KEY_PAIR, this::createKeyPair);

		buttons.setEnabled(Action.SAVE_KEY_PAIR, session.getKeyPair() != null);
		buttons.setClicked(Action.SAVE_KEY_PAIR, this::saveKeyPair);

		buttons.setEnabled(Action.LOAD_KEY_PAIR, session.getAccount() == null);
		buttons.setClicked(Action.LOAD_KEY_PAIR, this::loadKeyPair);

		buttons.setEnabled(Action.NONCE, session.getUrl() != null);
		buttons.setClicked(Action.NONCE, this::nonceClicked);

		buttons.setEnabled(Action.CREATE_ACCOUNT, session.getNonce() != null && session.getKeyPair() != null);
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

	private void sessionChanged() {
		stateContainer.removeAll();
		SessionUI.render(session, stateContainer);
	}

	@Override
	protected LayoutManager getLayout(Container target) {
		return new StackedLayout(5);
	}

}
