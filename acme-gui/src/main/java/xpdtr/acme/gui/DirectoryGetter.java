package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;

public class DirectoryGetter {

	public static Component renderStarting() {
		JLabel label = new JLabel("Getting directory infos... ");
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		return label;
	}

	public static Promise<AcmeDirectoryInfos2> start(ObjectMapper om) {

		Promise<AcmeDirectoryInfos2> promise = new Promise<>();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					AcmeDirectoryInfos2 infos = Acme2.directory(om);
					promise.success(infos);
				} catch (Exception e) {
					promise.failure(e);
				}
			}
		});
		promise.setThread(thread);

		return promise;
	}

	public static List<Component> getResponseComponents(GetDirectoryResult data) {

		List<Component> scrollView = new ArrayList<>();

		Exception directoryException = data.getException();
		AcmeDirectoryInfos2 directoryInfos = data.getInfos();

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

		}
		return scrollView;
	}

	public static List<Component> getSuccessComponents(AcmeDirectoryInfos2 infos) {

		List<Component> scrollView = new ArrayList<>();

		scrollView.add(new JLabel(infos.getKeyChange()));
		scrollView.add(new JLabel(infos.getNewAccountURL()));
		scrollView.add(new JLabel(infos.getNewNonce()));
		scrollView.add(new JLabel(infos.getNewOrder()));
		scrollView.add(new JLabel(infos.getRevokeCert()));

		return scrollView;
	}

	public static Component getFailureComponent(Exception exception) {
		JLabel label = new JLabel(exception.getClass().getName() + " : " + exception.getMessage());
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		return label;
	}

}
