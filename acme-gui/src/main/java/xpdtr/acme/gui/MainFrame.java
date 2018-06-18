package xpdtr.acme.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v1.AcmeUrls;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import xpdtr.acme.gui.fiddling.BasicFrameWithVerticalScroll;

public class MainFrame extends BasicFrameWithVerticalScroll {

	private String version;
	private String url;
	private ObjectMapper om = new ObjectMapper();
	private Exception directoryException;
	private AcmeDirectoryInfos2 directoryInfos;
	private String nonce;
	private Exception nonceException;
	private JPanel scrollView;

	@Override
	protected void addComponents(JPanel scrollView) {
		this.scrollView = scrollView;
		versionQuestion();
	}

	private void versionQuestion() {

		JLabel label = new JLabel("Which version of ACME do you want to use ?");
		label.setFont(label.getFont().deriveFont(Font.BOLD));

		JComboBox<String> list = new JComboBox<String>(new String[] { "Version 1", "Version 2" });
		list.setSelectedItem("Version 2");

		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				list.setEnabled(false);
				button.setEnabled(false);
				version = (String) list.getSelectedItem();
				urlQuestion();

			}

		});

		JPanel panel = new JPanel();
		panel.setLayout(new LCB());
		panel.add(label);
		panel.add(list);
		panel.add(button);

		scrollView.add(panel);

	}

	private void urlQuestion() {
		System.out.println(version);
		switch (version) {
		case "Version 1":
			urlQuestionNotImplemented();
			break;
		case "Version 2":
			urlQuestionImplemented();
			break;
		}

	}

	private void urlQuestionNotImplemented() {
		JLabel label = new JLabel("Sorry, support for Acme v1 is not available");
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		scrollView.add(label);
		validate();
	}

	private void urlQuestionImplemented() {

		JLabel label = new JLabel("Which URL do you want to use ?");
		label.setFont(label.getFont().deriveFont(Font.BOLD));

		JComboBox<String> list = new JComboBox<String>(
				new String[] { AcmeUrls.LETS_ENCRYPT_V2, AcmeUrls.LETS_ENCRYPT_V2_STAGING });
		list.setSelectedItem(AcmeUrls.LETS_ENCRYPT_V2_STAGING);

		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				list.setEnabled(false);
				button.setEnabled(false);
				url = (String) list.getSelectedItem();
				getDirectory();

			}

		});

		JPanel panel = new JPanel();
		panel.setLayout(new LCB());
		panel.add(label);
		panel.add(list);
		panel.add(button);

		scrollView.add(panel);

		validate();

	}

	private void getDirectory() {
		JLabel label = new JLabel("Getting directory infos... ");
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		scrollView.add(label);
		validate();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					directoryInfos = Acme2.directory(om);
				} catch (Exception e) {
					directoryException = e;
				} finally {
					getDirectoryNext();
				}
			}

		}).start();
	}

	private void getDirectoryNext() {

		if (directoryException != null) {
			JLabel label = new JLabel(
					directoryException.getClass().getName() + " : " + directoryException.getMessage());
			label.setFont(label.getFont().deriveFont(Font.BOLD));
			scrollView.add(label);
		} else {
			scrollView.add(new JLabel(directoryInfos.getKeyChange()));
			scrollView.add(new JLabel(directoryInfos.getNewAccountURL()));
			scrollView.add(new JLabel(directoryInfos.getNewNonce()));
			scrollView.add(new JLabel(directoryInfos.getNewOrder()));
			scrollView.add(new JLabel(directoryInfos.getRevokeCert()));

			addButtons();
		}

		validate();
	}

	private void addButtons() {
		JPanel buttons = new JPanel(new FlowLayout());
		JButton newNonce = new JButton("New Nonce");
		newNonce.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newNonce();
			}
		});

		boolean otherEnabled = nonce != null;

		JButton newAccount = new JButton("New Account");
		newAccount.setEnabled(otherEnabled);

		JButton newOrder = new JButton("New Order");
		newOrder.setEnabled(otherEnabled);

		JButton changeKey = new JButton("Change Key");
		changeKey.setEnabled(otherEnabled);

		JButton revokeCert = new JButton("Revoke Cert");
		revokeCert.setEnabled(otherEnabled);

		buttons.add(newNonce);
		buttons.add(newAccount);
		buttons.add(newOrder);
		buttons.add(changeKey);
		buttons.add(revokeCert);
		scrollView.add(buttons);
	}

	private void newNonce() {
		JLabel label = new JLabel("Getting new nonce... ");
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		scrollView.add(label);
		validate();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					nonce = Acme2.nonce64(directoryInfos);
				} catch (Exception e) {
					nonceException = e;
				} finally {
					newNonceNext();
				}
			}

		}).start();
	}

	private void newNonceNext() {

		if (nonceException != null) {
			JLabel label = new JLabel(nonceException.getClass().getName() + " : " + nonceException.getMessage());
			label.setFont(label.getFont().deriveFont(Font.BOLD));
			scrollView.add(label);
		} else {
			JLabel label = new JLabel(nonce);
			// label.setFont(label.getFont().deriveFont(Font.BOLD));
			scrollView.add(label);
			addButtons();
		}

		validate();
	}

	@Override
	protected LayoutManager getLayout(Container target) {
		return new StackedLayout();
	}

}
