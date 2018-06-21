package xpdtr.acme.gui.components;

import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import example.company.acme.AcmeSession;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.AcmeIdentifier;
import example.company.acme.v2.AcmeOrder;
import example.company.acme.v2.AcmeOrderWithNonce;
import example.company.acme.v2.Authorization;
import example.company.acme.v2.Challenge;
import xpdtr.acme.gui.components.SelectableLabelUI;
import xpdtr.acme.gui.utils.U;

public class SessionUI {

	public static void render(AcmeSession session, JComponent stateContainer) {
		if (session.getVersion() != null) {
			U.addM(stateContainer, SelectableLabelUI.render("ACME Version : " + session.getVersion()));
		}
		if (session.getUrl() != null) {
			U.addM(stateContainer, SelectableLabelUI.render("Server URL : " + session.getUrl()));
		}
		if (session.getInfos() != null) {
			AcmeDirectoryInfos2 i = session.getInfos();
			JPanel directoryPanel = titledVPanel("Directory");
			U.addM(directoryPanel, SelectableLabelUI.render("New Nonce URL : " + i.getNewNonce()));
			U.addM(directoryPanel, SelectableLabelUI.render("New Account URL : " + i.getNewAccountURL()));
			U.addM(directoryPanel, SelectableLabelUI.render("New Order URL : " + i.getNewOrder()));
			U.addM(directoryPanel, SelectableLabelUI.render("Key Change URL : " + i.getKeyChange()));
			U.addM(directoryPanel, SelectableLabelUI.render("Revoke Cert URL : " + i.getRevokeCert()));
			U.addM(stateContainer, directoryPanel);
		}
		if (session.getNonce() != null) {
			JPanel noncePanel = titledVPanel("Nonce");
			U.addM(noncePanel, SelectableLabelUI.render(session.getNonce()));
			U.addM(stateContainer, noncePanel);

		}
		if (session.getAccount() != null) {

			JPanel accountPanel = titledVPanel("Account");
			U.addM(accountPanel, SelectableLabelUI.render("URL : " + session.getAccount().getUrl()));
			U.addM(stateContainer, accountPanel);
		}
		AcmeOrderWithNonce order = session.getOrder();
		if (order != null) {
			JPanel orderPanel = titledVPanel("Order");
			AcmeOrder oorder = order.getContent();
			for (AcmeIdentifier identifier : oorder.getIdentifiers()) {
				U.addM(orderPanel,
						SelectableLabelUI.render("Identifier : " + identifier.getType() + " " + identifier.getValue()));
			}
			U.addM(orderPanel, SelectableLabelUI.render("Status : " + oorder.getStatus()));
			U.addM(orderPanel, SelectableLabelUI.render("Expires : " + oorder.getStatus()));
			for (String authorizationURL : oorder.getAuthorizations()) {
				U.addM(orderPanel, SelectableLabelUI.render("Authorization URL : " + authorizationURL));
			}
			U.addM(orderPanel, SelectableLabelUI.render("Finalize URL : " + oorder.getFinalize()));
			U.addM(stateContainer, orderPanel);
		}
		if (session.getAuthorization() != null) {
			JPanel authorizationPanel = titledVPanel("Authorization");
			Authorization a = session.getAuthorization();
			List<Challenge> challenges = a.getChallenges();

			AcmeIdentifier identifier = a.getIdentifier();
			U.addM(authorizationPanel,
					SelectableLabelUI.render("Authorization : " + identifier.getType() + " " + identifier.getValue()));
			U.addM(authorizationPanel, SelectableLabelUI.render("Status: " + a.getStatus()));
			U.addM(authorizationPanel, SelectableLabelUI.render("Expires: " + a.getExpires()));
			for (Challenge challenge : challenges) {
				JPanel challengePanel = titledVPanel("Challenge");
				U.addM(challengePanel, SelectableLabelUI.render("URL : " + challenge.getUrl()));
				U.addM(challengePanel, SelectableLabelUI.render("Type : " + challenge.getType()));
				U.addM(challengePanel, SelectableLabelUI.render("Status : " + challenge.getStatus()));
				U.addM(challengePanel, SelectableLabelUI.render("Token : " + challenge.getToken()));
				U.addM(authorizationPanel, challengePanel);
			}
			U.addM(stateContainer, authorizationPanel);
		}
	}

	private static JPanel titledVPanel(String title) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		TitledBorder b = BorderFactory.createTitledBorder(title);
		b.setTitleFont(b.getTitleFont().deriveFont(Font.BOLD));
		p.setBorder(b);
		return p;
	}

}
