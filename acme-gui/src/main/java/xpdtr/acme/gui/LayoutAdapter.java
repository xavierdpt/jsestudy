package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class LayoutAdapter implements LayoutManager {

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(0, 0);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(0, 0);
	}

	@Override
	public void layoutContainer(Container parent) {
	}

}
