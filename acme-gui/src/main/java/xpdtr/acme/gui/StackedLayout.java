package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

public class StackedLayout extends LayoutAdapter {

	@Override
	public Dimension preferredLayoutSize(Container parent) {

		double w = 0;
		double h = 0;
		for (int i = 0; i < parent.getComponentCount(); ++i) {
			Component c = parent.getComponent(i);
			double cw = c.getPreferredSize().getWidth();
			double ch = c.getPreferredSize().getHeight();
			w = Math.max(w, cw);
			h += ch;

		}

		return new Dimension((int) w, (int) h);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		double w = 0;
		double h = 0;
		for (int i = 0; i < parent.getComponentCount(); ++i) {
			Component c = parent.getComponent(i);
			double cw = c.getMinimumSize().getWidth();
			double ch = c.getMinimumSize().getHeight();
			w = Math.max(w, cw);
			h += ch;

		}

		return new Dimension((int) w, (int) h);
	}

	@Override
	public void layoutContainer(Container parent) {

		double w = 0;

		for (int i = 0; i < parent.getComponentCount(); ++i) {
			Component c = parent.getComponent(i);
			w = Math.max(w, parent.getComponent(i).getPreferredSize().getWidth());
		}

		w = Math.min(w, parent.getWidth());

		double h = 0;
		for (int i = 0; i < parent.getComponentCount(); ++i) {
			Component c = parent.getComponent(i);
			double ch = c.getMinimumSize().getHeight();

			c.setLocation(0, (int) h);
			c.setSize((int) w, (int) c.getPreferredSize().getHeight());

			h += ch;
		}

	}

}