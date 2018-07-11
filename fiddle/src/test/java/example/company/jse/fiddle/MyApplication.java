package example.company.jse.fiddle;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyApplication extends Application implements Youpi {

	private int i = 1;

	@Override
	public void start(Stage stage) throws Exception {
		try {

			Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();
			double w = sz.getWidth() * .8;
			double h = sz.getHeight() * .8;

			BorderPane bp = new BorderPane();

			VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER_LEFT);

			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setPrefSize(w, h);
			scrollPane.setContent(vb);
			scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

			Label label = new Label("THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY "
					+ "EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED "
					+ "WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE "
					+ "DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY "
					+ "DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES "
					+ "(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; "
					+ "LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND "
					+ "ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT "
					+ "(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS "
					+ "SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.");
			label.setWrapText(true);
			label.setMaxWidth(.99 * w);
			Button button = new Button("Hello " + i);

			button.setOnAction(new AddNewButtonHandler<ActionEvent>(button, this, vb, scrollPane));

			vb.setPadding(new Insets(10, 10, 10, 10));
			vb.setAlignment(Pos.CENTER);
			add(vb, label);
			add(vb, button);

			bp.setCenter(scrollPane);

			FlowPane fp = new FlowPane();
			fp.getChildren().add(new Button("You"));
			fp.getChildren().add(new Button("get"));
			fp.getChildren().add(new Button("the"));
			fp.getChildren().add(new Button("idea"));

			bp.setBottom(fp);

			Scene scene = new Scene(bp);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void add(VBox vb, Node node) {
		HBox hBox = new HBox();
		hBox.getChildren().add(node);
		vb.getChildren().add(hBox);

	}

	public static void run() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e.getClass().getName() + " : " + e.getMessage());
		}
		launch(new String[] {});
	}

	@Override
	public int increment() {
		return ++i;
	}

}
