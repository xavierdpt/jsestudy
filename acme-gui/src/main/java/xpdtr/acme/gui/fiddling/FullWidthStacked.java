package xpdtr.acme.gui.fiddling;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import xpdtr.acme.gui.layout.LayoutAdapter;

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

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
