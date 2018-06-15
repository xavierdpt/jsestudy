package xpdtr.acme.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

public class FirstRemainingWidth extends LayoutAdapter {

	@Override
	public void layoutContainer(Container parent) {
		int cc = parent.getComponentCount();
		double preferredTotalWidth = 0;
		for (int i = 1; i < cc; ++i) {
			Component c = parent.getComponent(i);
			double preferredWidth = c.getPreferredSize().getWidth();
			preferredTotalWidth += preferredWidth;
		}
		double x = parent.getWidth() - preferredTotalWidth;
		if (cc > 0) {
			Component first = parent.getComponent(0);
			Dimension dim = new Dimension();
			double preferredHeight = first.getPreferredSize().getHeight();
			dim.setSize(x, preferredHeight);
			first.setSize(dim);
			first.setLocation(0, 0);
		}
		for (int i = 1; i < cc; ++i) {
			Component c = parent.getComponent(i);
			c.setLocation((int) x, 0);
			double preferredWidth = c.getPreferredSize().getWidth();
			x += preferredWidth;
			c.setSize(c.getPreferredSize());
		}

	}

}
