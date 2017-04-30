package de.snuk.jdeps.view;

import java.io.File;

import de.snuk.jdeps.util.DialogFunc;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StartAnalyzeDialog extends Stage {

	private TextField tfFile;

	public StartAnalyzeDialog(Stage owner, DialogFunc func) {
		super();
		initOwner(owner);
		setTitle("Analyze Project");
		initModality(Modality.APPLICATION_MODAL);
		setMinWidth(300.0);
		setMinHeight(200.0);

		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root, 400, 300);
		setScene(scene);

		GridPane gridPane = createFormPane(owner);
		HBox buttonPane = createButtonPane(func);

		root.getChildren().addAll(gridPane, buttonPane);
		AnchorPane.setBottomAnchor(buttonPane, 10.0);
		AnchorPane.setRightAnchor(buttonPane, 10.0);
		AnchorPane.setTopAnchor(gridPane, 10.0);
	}

	private GridPane createFormPane(Stage owner) {
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(5));
		gridPane.setHgap(5);
		gridPane.setVgap(10);

		Label lblFile = new Label("Jar File: ");
		gridPane.add(lblFile, 0, 0);

		tfFile = new TextField();
		gridPane.add(tfFile, 1, 0, 2, 1);

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

		gridPane.add(button, 3, 0);

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

		return gridPane;
	}

	private HBox createButtonPane(DialogFunc func) {
		HBox buttonPane = new HBox();
		buttonPane.setSpacing(10.0);

		Button btnCancel = new Button("Cancel");
		btnCancel.setMinWidth(100.0);
		btnCancel.setOnAction(event -> close());

		Button btnStart = new Button("Start");
		btnStart.setMinWidth(100.0);
		btnStart.setOnAction(event -> {

			// TODO better validation
			if (tfFile.getText().equals("")) {
				func.setSelectedFilePath(null);
			} else {
				func.setSelectedFilePath(tfFile.getText());
			}
			close();
		});

		buttonPane.getChildren().addAll(btnCancel, btnStart);

		return buttonPane;
	}
}
