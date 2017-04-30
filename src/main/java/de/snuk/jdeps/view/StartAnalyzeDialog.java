package de.snuk.jdeps.view;

import java.io.File;

import de.snuk.jdeps.util.DialogFunc;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StartAnalyzeDialog extends Stage {

	// TODO style and layout
	public StartAnalyzeDialog(Stage owner, DialogFunc func) {
		super();
		initOwner(owner);
		setTitle("Analyze jar");
		initModality(Modality.APPLICATION_MODAL);

		Group root = new Group();
		Scene scene = new Scene(root, 400, 300);
		setScene(scene);

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(5));
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		Label lblFile = new Label("Jar file: ");
		gridPane.add(lblFile, 0, 0);

		TextField tfFile = new TextField();
		gridPane.add(tfFile, 1, 0);

		Button button = new Button("...");
		button.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Jar to analyze");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jar", "*.jar"));
			File showOpenDialog = fileChooser.showOpenDialog(owner);

			if (showOpenDialog != null) {
				String substring = showOpenDialog.getAbsolutePath();
				tfFile.setText(substring);
			}
		});

		gridPane.add(button, 2, 0);

		ToggleGroup buttonGroup = new ToggleGroup();

		RadioButton rbPackage = new RadioButton("Package");
		rbPackage.setToggleGroup(buttonGroup);
		rbPackage.setSelected(true);

		RadioButton rbClass = new RadioButton("Class");
		rbClass.setToggleGroup(buttonGroup);
		rbClass.setDisable(true);

		Label lblLevel = new Label("Level: ");
		gridPane.add(lblLevel, 0, 1);

		gridPane.add(rbPackage, 1, 1);
		gridPane.add(rbClass, 2, 1);

		Button btnStart = new Button("Start");
		btnStart.setOnAction(event -> {

			// TODO better validation
			if (tfFile.getText().equals("")) {
				func.setSelectedFilePath(null);
			} else {
				func.setSelectedFilePath(tfFile.getText());
			}
			close();
		});

		Button btnCancel = new Button("Cancel");
		btnCancel.setOnAction(event -> close());

		gridPane.add(btnCancel, 1, 2);
		gridPane.add(btnStart, 2, 2);

		root.getChildren().add(gridPane);
	}

}
