package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;

public class Nonce {

	public static Component renderGetting() {
		JLabel label = new JLabel("Getting new nonce... ");
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		return label;
	}

	public static Promise<String> get(AcmeDirectoryInfos2 directoryInfos) {

		Promise<String> promise = new Promise<>();

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					promise.success(Acme2.nonce64(directoryInfos));
				} catch (Exception e) {
					promise.failure(e);
				}
			}

		});

		promise.setThread(thread);

		return promise;
	}

	public static JLabel renderSuccess(String nonce) {
		JLabel label = new JLabel(nonce);
		return label;

	}

	public static JLabel renderFailure(Exception exception) {
		JLabel label = new JLabel(exception.getClass().getName() + " : " + exception.getMessage());
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		return label;

	}

}
