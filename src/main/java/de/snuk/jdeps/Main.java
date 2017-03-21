package de.snuk.jdeps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	private String jdepsPath;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("jdeps GUI");

		Scene scene = new Scene(new VBox(), 800, 600);

		MenuBar menuBar = createMenuBar();

		((VBox) scene.getRoot()).getChildren().add(menuBar);

		primaryStage.setScene(scene);
		primaryStage.show();

		deserializeConfig();

		System.out.println(jdepsPath);
		if (jdepsPath == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("jdeps not found");
			alert.setContentText("jdeps path wasn't set yet. please select the path to jdeps.exe file");
			alert.setHeaderText("");
			alert.showAndWait();

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("jdeps.exe");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jdeps", "jdeps.exe"));
			File showOpenDialog = fileChooser.showOpenDialog(primaryStage);

			if (showOpenDialog != null) {
				System.out.println(showOpenDialog.getAbsolutePath());
				serializePath(showOpenDialog.getAbsolutePath());
			}
		}

	}

	/**
	 * Tries to serialize the config file.
	 * 
	 * @param path
	 */
	private void serializePath(String path) {
		try {
			FileWriter writer = new FileWriter("config.cfg");
			writer.write(path);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tries to deserialize the config file.
	 */
	private void deserializeConfig() {
		try {
			if (Files.exists(Paths.get("config.cfg"), LinkOption.NOFOLLOW_LINKS)) {
				List<String> lines = Files.readAllLines(Paths.get("config.cfg"));
				jdepsPath = lines.get(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public static void main(String[] args) {
		launch(args);
	}
}
