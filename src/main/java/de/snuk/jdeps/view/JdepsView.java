package de.snuk.jdeps.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JdepsView {

	private Button button;
	private Scene scene;
	private TreeView<String> tree;

	private ProgressBar progressBar;

	public JdepsView() {
		scene = new Scene(new VBox(), 800, 600);

		MenuBar menuBar = createMenuBar();
		button = new Button("Go");
		tree = new TreeView<>();

		progressBar = new ProgressBar();

		((VBox) scene.getRoot()).getChildren().addAll(menuBar, button, tree);
	}

	public void addProgressBar() {
		progressBar.setProgress(-1);
		((VBox) scene.getRoot()).getChildren().add(progressBar);
	}

	public void removeProgressBar() {
		((VBox) scene.getRoot()).getChildren().remove(progressBar);
	}

	public void show(Stage stage) {
		stage.setTitle("jdeps GUI");
		stage.setScene(scene);
		stage.show();
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

	public Button getButton() {
		return button;
	}

	public TreeView<String> getTree() {
		return tree;
	}
}
