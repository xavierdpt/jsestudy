package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

public class H implements LayoutManager {

	@Override
	public void addLayoutComponent(String name, Component comp) {
		System.out.println("addLayoutComponent");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		System.out.println("removeLayoutComponent");

	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return parent.getSize();
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		System.out.println("minimumLayoutSize");
		return parent.getMinimumSize();
	}

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
