package de.snuk.jdeps.view;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JdepsView {

	private Button btnGo;
	private Button btnSearch;
	private Scene scene;
	private TreeView<String> tree;
	private ProgressBar progressBar;
	private HBox footer;
	private FontAwesomeIconView lblStatus;
	private Label lblStatusText;
	private TextField tfSearch;

	public JdepsView() {
		scene = new Scene(new VBox(), 800, 600);

		MenuBar menuBar = createMenuBar();

		GridPane header = new GridPane();
		header.setPadding(new Insets(5));

		btnGo = GlyphsDude.createIconButton(FontAwesomeIcon.PLAY);
		btnGo.setTooltip(new Tooltip("Start analyzing"));

		tfSearch = new TextField();
		btnSearch = GlyphsDude.createIconButton(FontAwesomeIcon.SEARCH);
		header.add(btnGo, 0, 0);
		header.add(tfSearch, 1, 0);
		header.add(btnSearch, 2, 0);

		GridPane.setHgrow(btnGo, Priority.ALWAYS);

		tree = new TreeView<>();
		progressBar = new ProgressBar();

		footer = createFooter();

		((VBox) scene.getRoot()).getChildren().addAll(menuBar, header, tree, footer);

		VBox.setVgrow(tree, Priority.ALWAYS);
	}

	public void addProgressBar() {
		progressBar.setProgress(-1);
		footer.getChildren().add(progressBar);
	}

	public void removeProgressBar() {
		footer.getChildren().remove(progressBar);
	}

	public void show(Stage stage) {
		stage.setTitle("jdeps GUI");
		stage.setScene(scene);
		stage.show();
	}

	private HBox createFooter() {
		footer = new HBox();
		footer.setPadding(new Insets(2));
		footer.setSpacing(5.0);

		lblStatus = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
		lblStatus.setFill(Color.GREEN);
		lblStatus.setSize("16px");

		lblStatusText = new Label("");
		lblStatusText.setFont(Font.font(12));

		Pane spacer = new Pane();

		footer.getChildren().addAll(lblStatus, lblStatusText, spacer);

		HBox.setHgrow(spacer, Priority.ALWAYS);

		return footer;
	}

	/**
	 * Creates the MenuBar.
	 *
	 * @return the MenuBar
	 */
	private MenuBar createMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu menuOne = new Menu("One");
		MenuItem item = new MenuItem("test");
		menuOne.getItems().add(item);

		Menu menuTwo = new Menu("Two");
		Menu menuThree = new Menu("Three");
		menuBar.getMenus().addAll(menuOne, menuTwo, menuThree);

		return menuBar;
	}

	public void setError(boolean hasError) {
		if (hasError) {
			lblStatus.setIcon(FontAwesomeIcon.REMOVE);
			lblStatus.setFill(Color.RED);
		} else {
			lblStatus.setIcon(FontAwesomeIcon.CHECK);
			lblStatus.setFill(Color.GREEN);
		}
	}

	public Button getGoButton() {
		return btnGo;
	}

	public Button getSearchButton() {
		return btnSearch;
	}

	public TreeView<String> getTree() {
		return tree;
	}

	public FontAwesomeIconView getStatusLabel() {
		return lblStatus;
	}

	public Label getLblStatusText() {
		return lblStatusText;
	}

	public TextField getTfSearch() {
		return tfSearch;
	}
}
