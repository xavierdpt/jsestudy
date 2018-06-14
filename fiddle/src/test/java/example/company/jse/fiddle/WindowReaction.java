package example.company.jse.fiddle;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowReaction extends WindowAdapter {

	private VisibilityHolder visibilityHolder;

	public WindowReaction(VisibilityHolder visibilityHolder) {
		this.visibilityHolder = visibilityHolder;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		visibilityHolder.setVisible(false);
	}

}
