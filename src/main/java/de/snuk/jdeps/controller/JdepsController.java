package de.snuk.jdeps.controller;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.snuk.jdeps.model.DataModel;
import de.snuk.jdeps.model.MyClass;
import de.snuk.jdeps.model.MyPackage;
import de.snuk.jdeps.util.CommandExecuter;
import de.snuk.jdeps.view.JdepsView;
import de.snuk.jdeps.view.StartAnalyzeDialog;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class JdepsController {

	private DataModel model;
	private JdepsView view;

	public JdepsController(DataModel model) {
		this.model = model;
		this.view = new JdepsView();

		view.getButton().setOnAction(event -> onGo());

		model.getProjectData().addListener((ListChangeListener<MyPackage>) c -> {
			TreeView<String> tree = view.getTree();

			TreeItem<String> rootItem = new TreeItem<>("Project");
			rootItem.setExpanded(true);

			List<? extends MyPackage> addedSubList = c.getList();

			addedSubList.forEach(p -> {
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

	private void onGo() {

		Stage myDialog = new StartAnalyzeDialog(model.getStage(), val -> model.setSelectedFile(new File(val)));
		myDialog.sizeToScene();
		myDialog.showAndWait();

		File selectedFile = model.getSelectedFile();

		if (selectedFile != null && selectedFile.exists()) {
			model.clearProjectData();
			view.getButton().setDisable(true);

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
						model.addProjectData(get());
						view.removeProgressBar();
						view.getButton().setDisable(false);
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
