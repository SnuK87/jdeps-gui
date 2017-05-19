package de.snuk.jdeps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.List;

import de.snuk.jdeps.controller.JdepsController;
import de.snuk.jdeps.model.DataModel;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		DataModel model = new DataModel(primaryStage);
		JdepsController controller = new JdepsController(model);

		controller.show();

		deserializeConfig(model);

		String jdepsPath = model.getJdepsPath();

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
				String substring = showOpenDialog.getAbsolutePath().substring(0,
						showOpenDialog.getAbsolutePath().length() - 4);
				serializePath(substring);
			}
		}
	}

	/**
	 * Tries to deserialize the config file.
	 */
	private void deserializeConfig(DataModel model) {
		try {
			if (Files.exists(Paths.get("config.cfg"), LinkOption.NOFOLLOW_LINKS)) {
				List<String> lines = Files.readAllLines(Paths.get("config.cfg"));
				model.setJdepsPath(lines.get(0));
			}
		} catch (IOException e) {
			e.printStackTrace();
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

	public static void main(String[] args) {
		launch(args);
	}
}
