package de.snuk.jdeps.model;

import java.io.File;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class DataModel {

	private ObservableList<MyPackage> projectData = FXCollections.observableArrayList();

	private ObservableList<MyPackage> originalProjectData = FXCollections.observableArrayList();

	private ObservableList<String> errorLines = FXCollections.observableArrayList();

	private String jdepsPath;
	private File selectedFile;
	private Stage stage;

	private StringProperty projectName;
	private StringProperty statusText = new SimpleStringProperty();

	private BooleanProperty hasError = new SimpleBooleanProperty(false);
	private BooleanProperty btnGoDisabled = new SimpleBooleanProperty(false);
	private BooleanProperty searchDisabled = new SimpleBooleanProperty(true);

	public DataModel(Stage stage) {
		this.stage = stage;
	}

	public String getJdepsPath() {
		return jdepsPath;
	}

	public void setJdepsPath(String jdepsPath) {
		this.jdepsPath = jdepsPath;
	}

	public ObservableStringValue getProjectName() {
		return projectName;
	}

	public void setProjectName(StringProperty projectName) {
		this.projectName = projectName;
	}

	public ObservableList<MyPackage> getProjectData() {
		return projectData;
	}

	public void addProjectData(List<MyPackage> packages) {
		this.projectData.addAll(packages);
	}

	public void clearProjectData() {
		this.projectData.clear();
	}

	public void clearOriginalProjectData() {
		this.originalProjectData.clear();
		clearProjectData();
	}

	public void addOriginalProjectData(List<MyPackage> packages) {
		this.originalProjectData.addAll(packages);
		addProjectData(packages);
	}

	public ObservableList<MyPackage> getOriginalProjectData() {
		return this.originalProjectData;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public BooleanProperty getBtnGoDisabled() {
		return btnGoDisabled;
	}

	public void setBtnGoDisabled(boolean btnGoDisabled) {
		this.btnGoDisabled.set(btnGoDisabled);
	}

	public BooleanProperty getSearchDisabled() {
		return searchDisabled;
	}

	public void setSearchDisabled(boolean searchDisabled) {
		this.searchDisabled.set(searchDisabled);
	}

	public BooleanProperty getHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError.set(hasError);
	}

	public StringProperty getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText.set(statusText);
	}

	public ObservableList<String> getErrorLines() {
		return errorLines;
	}

	public void setErrorLines(List<String> errorLines) {
		this.errorLines.clear();
		this.errorLines.addAll(errorLines);
	}
}
