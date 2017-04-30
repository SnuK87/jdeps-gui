package de.snuk.jdeps.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import de.snuk.jdeps.model.DataModel;
import de.snuk.jdeps.model.MyClass;
import de.snuk.jdeps.model.MyPackage;
import de.snuk.jdeps.util.CommandExecuter;
import de.snuk.jdeps.view.JdepsView;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

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
		// String cmd = model.getJdepsPath() + " " +
		// "C:\\dev\\workspace\\jdeps-gui\\target\\jdeps-gui-0.0.1-SNAPSHOT.jar";
		String cmd = model.getJdepsPath() + " " + "C:\\test\\javaws.jar";

		// ProgressBar bar = new ProgressBar();
		SimpleDoubleProperty prop = new SimpleDoubleProperty(0);

		view.addProgressBar(prop);

		Task<List<MyPackage>> task = new Task<List<MyPackage>>() {

			@Override
			protected List<MyPackage> call() throws Exception {
				return CommandExecuter.executeCommand(cmd, prop);
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				try {
					prop.set(1);

					Thread.sleep(500);
					model.addProjectData(get());
					view.removeProgressBar();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		new Thread(task).start();
	}

	public void show() {
		view.show(model.getStage());
	}
}
