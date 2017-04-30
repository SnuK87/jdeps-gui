package de.snuk.jdeps.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import de.snuk.jdeps.model.DataModel;
import de.snuk.jdeps.model.MyClass;
import de.snuk.jdeps.model.MyPackage;
import de.snuk.jdeps.util.CommandExecuter;
import de.snuk.jdeps.view.JdepsView;
import de.snuk.jdeps.view.StartAnalyzeDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class JdepsController {

	private DataModel model;
	private JdepsView view;

	public JdepsController(DataModel model) {
		this.model = model;
		this.view = new JdepsView();

		model.getHasError().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
			view.setError(observable.getValue());
		});

		model.getStatusText().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			view.getLblStatusText().setText(observable.getValue());
		});

		view.getGoButton().setOnAction(event -> onGo());
		view.getGoButton().disableProperty().bind(this.model.getBtnGoDisabled());

		view.getSearchButton().setOnAction(event -> {
			onSearch();
		});
		view.getSearchButton().disableProperty().bind(this.model.getSearchDisabled());
		view.getTfSearch().disableProperty().bind(this.model.getSearchDisabled());

		view.getTfSearch().setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				onSearch();
			}
		});

		this.model.getProjectData().addListener((ListChangeListener<MyPackage>) c -> {
			TreeView<String> tree = view.getTree();

			TreeItem<String> rootItem = new TreeItem<>(this.model.getProjectName().get());
			Image imageProject = new Image(getClass().getResourceAsStream("/project.png"));
			rootItem.setGraphic(new ImageView(imageProject));

			rootItem.setExpanded(true);

			List<? extends MyPackage> projectData = c.getList();

			projectData.forEach(p -> {
				TreeItem<String> item = new TreeItem<>(p.getName());
				Image imagePackage = new Image(getClass().getResourceAsStream("/package.png"));
				item.setGraphic(new ImageView(imagePackage));

				List<MyClass> classes = p.getClasses();
				classes.forEach(cl -> {
					TreeItem<String> item2 = new TreeItem<>(cl.getName());
					item.getChildren().add(item2);
				});

				rootItem.getChildren().add(item);
			});

			tree.setRoot(rootItem);
		});
	}

	private void onSearch() {
		String searchText = view.getTfSearch().getText();
		if (!searchText.equals("")) {
			List<MyPackage> filteredPackages = new ArrayList<>();

			model.getOriginalProjectData().stream().forEach(p -> {
				List<MyClass> filteredClasses = p.getClasses().stream().filter(c -> c.getName().contains(searchText))
						.collect(Collectors.toList());

				if (!filteredClasses.isEmpty()) {
					MyPackage filteredPackage = new MyPackage(p.getName());
					filteredPackage.setClasses(filteredClasses);
					filteredPackages.add(filteredPackage);
				}
			});

			model.clearProjectData();
			model.addProjectData(filteredPackages);
		} else {
			model.clearProjectData();
			model.addProjectData(model.getOriginalProjectData());
		}
	}

	private void onGo() {
		Stage myDialog = new StartAnalyzeDialog(model.getStage(), val -> {
			model.setSelectedFile(new File(val));
			model.setProjectName(new SimpleStringProperty(model.getSelectedFile().getName()));
		});
		myDialog.sizeToScene();
		myDialog.showAndWait();

		File selectedFile = model.getSelectedFile();

		if (selectedFile != null && selectedFile.exists()) {
			model.clearOriginalProjectData();
			model.setBtnGoDisabled(true);

			String cmd = model.getJdepsPath() + " " + selectedFile.getAbsolutePath();

			view.addProgressBar();

			Task<List<MyPackage>> task = new Task<List<MyPackage>>() {

				@Override
				protected List<MyPackage> call() throws Exception {
					return CommandExecuter.executeCommand(cmd);
				}

				@Override
				protected void succeeded() {
					super.succeeded();
					try {
						model.addOriginalProjectData(get());
						view.removeProgressBar();
						model.setBtnGoDisabled(false);
						model.setSearchDisabled(false);
						model.setStatusText("");
						model.setHasError(false);
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};

			new Thread(task).start();
		} else {
			model.setStatusText("Unable to read file.");
			model.setHasError(true);
		}
	}

	public void show() {
		view.show(model.getStage());
	}
}
