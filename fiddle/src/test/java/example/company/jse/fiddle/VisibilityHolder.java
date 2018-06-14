package example.company.jse.fiddle;

public class VisibilityHolder {

	boolean visible = true;

	public synchronized boolean isVisible() {
		return visible;
	}

	public synchronized void setVisible(boolean visible) {
		this.visible = visible;
	}
	
}
