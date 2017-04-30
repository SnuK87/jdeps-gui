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
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class JdepsController {

	private DataModel model;
	private JdepsView view;

	public JdepsController(DataModel model) {
		this.model = model;
		this.view = new JdepsView();

		view.getGoButton().setOnAction(event -> onGo());
		view.getGoButton().disableProperty().bind(this.model.getBtnGoDisabled());

		view.getSearchButton().setOnAction(event -> {
			onSearch();
		});
		view.getSearchButton().disableProperty().bind(this.model.getBtnSearchDisabled());

		view.getTfSearch().setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				onSearch();
			}
		});

		this.model.getProjectData().addListener((ListChangeListener<MyPackage>) c -> {
			TreeView<String> tree = view.getTree();

			TreeItem<String> rootItem = new TreeItem<>(this.model.getProjectName().get());
			rootItem.setExpanded(true);

			List<? extends MyPackage> projectData = c.getList();

			projectData.forEach(p -> {
				TreeItem<String> item = new TreeItem<>(p.getName());

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

			List<MyPackage> collect = new ArrayList<>();

			model.getOriginalProjectData().stream().forEach(p -> {
				List<MyClass> collect2 = p.getClasses().stream().filter(c -> c.getName().contains(searchText))
						.collect(Collectors.toList());

				if (!collect2.isEmpty()) {
					MyPackage filteredPackage = new MyPackage(p.getName());
					filteredPackage.setClasses(collect2);
					collect.add(filteredPackage);
				}
			});

			model.clearProjectData();
			model.addProjectData(collect);
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
			model.clearProjectData();
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
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};

			new Thread(task).start();
		} else {
			// TODO show error
		}
	}

	public void show() {
		view.show(model.getStage());
	}
}
