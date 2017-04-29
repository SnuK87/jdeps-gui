package de.snuk.jdeps.model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class DataModel {

	private ObservableList<MyPackage> projectData = FXCollections.observableArrayList();
	private String jdepsPath;
	private String projectName;
	private Stage stage;

	public DataModel(Stage stage) {
		this.stage = stage;
	}

	public String getJdepsPath() {
		return jdepsPath;
	}

	public void setJdepsPath(String jdepsPath) {
		this.jdepsPath = jdepsPath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ObservableList<MyPackage> getProjectData() {
		return projectData;
	}

	public void addProjectData(List<MyPackage> packages) {
		this.projectData.addAll(packages);
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
