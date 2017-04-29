package de.snuk.jdeps.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import de.snuk.jdeps.model.DataModel;
import de.snuk.jdeps.model.MyClass;
import de.snuk.jdeps.model.MyPackage;
import de.snuk.jdeps.util.CommandExecuter;
import de.snuk.jdeps.view.JdepsView;
import javafx.collections.ListChangeListener;
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
		String cmd = model.getJdepsPath() + " " + "C:\\dev\\workspace\\jdeps-gui\\target\\jdeps-gui-0.0.1-SNAPSHOT.jar";
		try {
			List<MyPackage> result = CommandExecuter.executeCommand(cmd);
			model.addProjectDataList(result);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void show() {
		view.show(model.getStage());
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

}
