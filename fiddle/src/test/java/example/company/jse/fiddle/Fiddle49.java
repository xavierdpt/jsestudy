package example.company.jse.fiddle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import example.company.acme.v1.Acme1;
import example.company.acme.v2.Acme2;

public class Fiddle49 {

	@Test
	public void fiddle() throws InterruptedException {

		VisibilityHolder vh = new VisibilityHolder();

		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);

		HashMap<String, Object> emap = new HashMap<>();
		emap.put("x", "y");

		Map<String, Object> map = new HashMap<>();
		map.put("a", "hello");
		map.put("b", 2);
		map.put("c", true);
		map.put("d", new String[] { "here", "there" });
		map.put("e", emap);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();

				JFrame f = new JFrame();
				JFrame.setDefaultLookAndFeelDecorated(false);
				JButton button = new JButton("Exit");
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						f.setVisible(false);
						vh.setVisible(false);
					}
				});

				Container cp = f.getContentPane();

				JMenuBar menu = new JMenuBar();
				menu.add(new JMenu("hello"));
				f.setJMenuBar(menu);
				cp.add(button, BorderLayout.SOUTH);
				int w = (int) (sz.getWidth() * .9);
				int h = (int) (sz.getHeight() * .9);
				f.setSize(w, h);
				f.setLocationRelativeTo(null);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setResizable(false);
				JLabel label = new JLabel("Hi, this is xpptdr's Acme client");
				cp.add(label, BorderLayout.NORTH);

				JPanel mainPanel = new JPanel();

				JPanel topPanel = new JPanel();
				JPanel contentPanel = new JPanel();

				topPanel.add(new JLabel("Which server do you want to use ?"));
				JComboBox<String> serversList = new JComboBox<String>(
						new String[] { Acme2.ACME_STAGING_V2, Acme1.ACME_STAGING_V1 });
				serversList.setEditable(true);
				topPanel.add(serversList);

				JButton serversButton = new JButton("Set");

				ServersReaction serversReaction = new ServersReaction(serversList, serversButton, topPanel, f);
				serversReaction.setDirectoriesReactionFactory(
						new DirectoriesReactionFactory(contentPanel, f, om, map, serversList));
				serversButton.addActionListener(serversReaction);
				topPanel.add(serversButton);

				topPanel.setBackground(Color.PINK);
				mainPanel.setLayout(new BorderLayout());
				mainPanel.add(topPanel, BorderLayout.NORTH);
				mainPanel.add(contentPanel, BorderLayout.CENTER);
				cp.add(mainPanel, BorderLayout.CENTER);

				f.addWindowListener(new WindowReaction(vh));
				f.setVisible(true);
			}
		});

		while (vh.isVisible()) {
			Thread.sleep(100);
		}

	}

}
