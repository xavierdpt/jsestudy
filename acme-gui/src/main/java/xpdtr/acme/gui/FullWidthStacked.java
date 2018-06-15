package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

public class FullWidthStacked extends LayoutAdapter {

	@Override
	public void layoutContainer(Container parent) {
		int y = 0;
		for (Component c : parent.getComponents()) {
			c.setLocation(0, y);
			Dimension size = new Dimension();
			size.setSize(parent.getSize().getWidth(), c.getPreferredSize().getHeight());
			c.setSize(size);
			y += c.getPreferredSize().getHeight();
		}
	}

}
