package example.company.jse.fiddle;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Fiddle50 {

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
				int width = (int) (sz.getWidth() * .9);
				int height = (int) (sz.getHeight() * .9);

				JFrame.setDefaultLookAndFeelDecorated(false);

				JFrame frame = new JFrame();
				frame.setSize(width, height);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);

				JPanel topPanel = new JPanel();

				JLabel label = new JLabel("Please choose in the list");
				JComboBox<String> cb = new JComboBox<>(new String[] { "a", "b" });
				JButton button = new JButton("Choose");
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						cb.setEnabled(false);
						button.setEnabled(false);
					}
				});

				topPanel.add(label);
				topPanel.add(cb);
				topPanel.add(button);

				Container contentPane = frame.getContentPane();
				contentPane.add(topPanel, BorderLayout.CENTER);

				frame.addWindowListener(new WindowReaction(vh));
				frame.setVisible(true);
			}
		});

		while (vh.isVisible()) {
			Thread.sleep(100);
		}

	}

}
