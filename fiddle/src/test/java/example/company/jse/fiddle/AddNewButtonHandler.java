package example.company.jse.fiddle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class AddNewButtonHandler<T extends Event> implements EventHandler<T> {

	private Button button;
	private Youpi youpi;
	private VBox vBox;
	private ScrollPane scrollPane;

	public AddNewButtonHandler(Button button, Youpi incrementor, VBox vBox, ScrollPane scrollPane) {
		this.button = button;
		this.youpi = incrementor;
		this.vBox = vBox;
		this.scrollPane = scrollPane;
	}

	@Override
	public void handle(T event) {
		button.setDisable(true);
		int newI = youpi.increment();
		Button newButton = new Button("Hello " + newI);
		newButton.setOnAction(new AddNewButtonHandler<ActionEvent>(newButton, youpi, vBox, scrollPane));
		youpi.add(vBox, newButton);
	}

}
