package de.snuk.jdeps.view;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JdepsView {

	private Button btnGo;
	private Button btnSearch;
	private Scene scene;
	private TreeView<String> tree;
	private ProgressBar progressBar;
	private HBox footer;
	private Label lblStatus;
	private TextField tfSearch;

	// TODO style and layout
	public JdepsView() {
		scene = new Scene(new VBox(), 800, 600);

		MenuBar menuBar = createMenuBar();

		// header = new HBox();
		GridPane header = new GridPane();
		header.setPadding(new Insets(5));

		btnGo = GlyphsDude.createIconButton(FontAwesomeIcon.PLAY);
		btnGo.setTooltip(new Tooltip("Start analyzing"));
		// button.setStyle(
		// "-fx-shadow-highlight-color : transparent;" + // if you don't want a
		// 3d effect highlight.
		// "-fx-outer-border : transparent;" + // if you don't want a button
		// border.
		// "-fx-inner-border : transparent;" + // if you don't want a button
		// border.
		// "-fx-focus-color: transparent;" + // if you don't want any focus
		// ring.
		// "-fx-faint-focus-color : transparent;" + // if you don't want any
		// focus ring.
		// "-fx-base : palegreen;" + // if you want a gradient shaded button
		// that lightens on hover and darkens on arming.
		// // "-fx-body-color: palegreen;" + // instead of -fx-base, if you want
		// a flat shaded button that does not lighten on hover and darken on
		// arming.
		// "-fx-font-size: 80px;"
		// );

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

		FontAwesomeIcon check = FontAwesomeIcon.CHECK_CIRCLE;
		lblStatus = GlyphsDude.createIconLabel(check, null, "16px", "16px", ContentDisplay.RIGHT);
		footer.getChildren().add(lblStatus);

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

	public Button getGoButton() {
		return btnGo;
	}

	public Button getSearchButton() {
		return btnSearch;
	}

	public TreeView<String> getTree() {
		return tree;
	}

	public Label getStatusLabel() {
		return lblStatus;
	}

	public TextField getTfSearch() {
		return tfSearch;
	}
}
