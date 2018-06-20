package xpdtr.acme.gui.components;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import example.company.acme.v2.AcmeDirectoryInfos2;

public class DirectoryUI {

	public static Component renderStarting() {
		JLabel label = new JLabel("Getting directory infos... ");
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		return label;
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
