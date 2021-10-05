package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;


import facades.ServiceFacade;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;

import model.implementation.Modul;
import model.implementation.Projekt;
import model.implementation.Sprint;
import model.implementation.Student;
import model.implementation.Thema;
import model.implementation.URLs;
import utils.ResizeHelper;

/**
 * Es steuert den Datenfluss in die Modellobjekte Projekt und Thema aktualisiert
 * die Ansicht, wenn sich Daten ändern.
 * 
 * @author Feras
 *
 */
public class ModuleController implements Initializable {

	@FXML
	private JFXTabPane tabPane;

	@FXML
	private Label moduleNameLabel;

	@FXML
	private JFXButton homeBtn;

	@FXML
	private VBox vboxScrollPane;

	@FXML
	private TableView<Projekt> projectsTable;

	@FXML
	private TableColumn<Projekt, String> nameCol;
	@FXML
	private TableColumn<Projekt, String> topicCol;

	@FXML
	private TableColumn<Projekt, Integer> sprintNumberCol;

	@FXML
	private TableColumn<Projekt, Double> gradesCol;

	@FXML
	private JFXTextArea notesTextArea;

	@FXML
	private Label moduleNameNotes;

	@FXML
	private StackPane topicsStackPane;

	@FXML
	private ImageView newTopicBtnImageView;

	private Node ModuletabPane;
	/*
	 * OLBERTZ Sie arbeiten relativ viel mit statischen Attributen. Im Normalfall sollte das eher eine
	 * absolute Ausnahme sein. Ich glaube, ich habe statische Attribute 2 mal in 15 Jahren verwendet. 
	 * Seien Sie sich wirklich sicher, dass Sie verstehen, was static in diesem Fall heisst. 
	 */
	private static Modul module;
	private List<Projekt> projectsList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		initImagesAndCss();
		/*
		 * OLBERTZ Hier haben wir einen Fall, den wir im Kapitel Clean-Code angesprochen haben. Sie greifen
		 * zweimal auf Attribute der Klasse Modul zu, und zwar um eine Ausgabe auszufuehren. Das ist nicht
		 * elegant, vor allem im Hinblick darauf, dass Sie die Ausgabe eventuell auch wieder aendern moechten.
		 * Viel schlauer ist es, wenn Sie dafuer eine Methode in die Klasse Modul schreiben, die einen String
		 * erzeugt und zurueckgibt. Sie koennen dafuer die toString()-Methode verwenden, Sie koennen aber 
		 * natuerlich auch eine andere defnieren. 
		 */
		moduleNameLabel.setText(module.getName() + " - " + module.getSemester());
		moduleNameNotes.setText(module.getName() + " - " + module.getSemester());
		initProjectsTable();
		initNotes();
		try {
			initTopicCards();
		} catch (IOException e) {
			/*
        	 * OLBERTZ Das hier hatten wir uns in der Vorlesung im Exceptions-Kapitel angesehen und nicht als
        	 * eine gueltige Ausnahmebehandlung definiert. Hier wird nichts behandelt, sondern nur eine Ausgabe
        	 * auf dem Bildschirm erscheint. Sie sollten den Fehler wenigstens in einer Logdatei mitschreiben. 
        	 */

			e.printStackTrace();
		}

	}


	@FXML
	void addNewProject(ActionEvent event) throws IOException {

		NewProjectController.setModule(module);

		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane newProjectDialog = dialogLoader
				.load(ModuleController.class.getResource("/fxmls/NewProjectDialog.fxml").openStream());
		NewProjectController newProjectController = (NewProjectController) dialogLoader.getController();
		// Wir übergeben die Projekttabelle an NewProjectController, damit wir sie nach
		// jedem Hinzufügen eines neuen Projekts gleichzeitig aktualisieren können.
		newProjectController.setProjectsTable(projectsTable);

		Dialog<Object> addDialog = createDialog(newProjectDialog);
		abilityToCloseFromXButton(addDialog);
		addDialog.showAndWait();
	}

	@FXML
	void updateProject(ActionEvent event) {

		try {
			openProject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void deleteProject(ActionEvent event) throws IOException {
		// ueberpruefe , ob mindestens eine Zeile der Projekttabelle ausgewaehlt ist.
		if (projectsTable.getItems().isEmpty() || projectsTable.getSelectionModel().isEmpty())
			return;
		// alle ausgewaehlten Zeilen.
		ObservableList<Projekt> projects = projectsTable.getSelectionModel().getSelectedItems();

		// Bestaetigungsdialog vor dem Loeschen anzeigen.
		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane confirmDeletion = dialogLoader
				.load(ModuleController.class.getResource("/fxmls/ConfirmDeletion.fxml").openStream());
		DeletionController deletionController = (DeletionController) dialogLoader.getController();

		deletionController.setProjectsList(projects);

		Dialog<Object> removeDialog = createDialog(confirmDeletion);
		abilityToCloseFromXButton(removeDialog);
		removeDialog.showAndWait();
		// Projekttabelle aktualisieren
		initProjectsTable();
	}

	@FXML
	void goHome(ActionEvent event) throws IOException {

		loadHomePage();

	}

	/**
	 * Speichert die aktualisierten Notizen eines Moduls.
	 * 
	 * @param event
	 * @author Feras
	 */
	@FXML
	void saveNotes(ActionEvent event) {

		String notes = notesTextArea.getText();
		// setze die neue Notizen
		module.setNotiz(notes);
		// Modul aktualisieren
		ServiceFacade.addModule(module);// TODO: change name to saveOrUpdate
	}

	@FXML
	void addNewTopic(ActionEvent event) throws IOException {
		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane newTopicDialog = dialogLoader
				.load(ModuleController.class.getResource("/fxmls/NewTopicDialog.fxml").openStream());
		NewTopicController newTopicController = (NewTopicController) dialogLoader.getController();

		newTopicController.setModule(module);
		newTopicController.setModuleController(this);
		
		Dialog<Object> addDialog = createDialog(newTopicDialog);
		abilityToCloseFromXButton(addDialog);
		addDialog.showAndWait();

		initTopicCards();

	}

	public void setPane(Node node) {
		ModuletabPane = node;
	}

	public static void setModule(Modul modul) {
		module = modul;
	}

	public void initProjectsTable() {
		projectsList = ServiceFacade.getAllProjects(module);// TODO: change name to getProjects(module)

		projectsTable.setItems(FXCollections.observableList(projectsList));
		// Moeglichkeit, mehrere Zeilen in einer Tabelle auszuwaehlen
		projectsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Die cellValueFactory teilt der Spalte mit, welcher Wert in ihren Zellen
		// angezeigt werden soll.
		nameCol.setCellValueFactory(new PropertyValueFactory<Projekt, String>("name"));
		topicCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Projekt, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Projekt, String> param) {
						/*
						 * OLBERTZ Ein bisschen Sprachchaos. Am besten einigen Sie sicih auf eine Sprache fuer die
						 * Bezeichner und halten sich auch alle daran. Dann wird die Sache weniger chaotisch.
						 */
						Thema thema = param.getValue().getThema();
						if (thema != null) {
							return new SimpleStringProperty(thema.getName());
						}
						return null;
					}
				});

		sprintNumberCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Projekt, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Projekt, Integer> param) {

						Projekt project = param.getValue();
						Integer numberOfSprints = ServiceFacade.getSprintsNumber(project);

						if (project != null) {
							return new SimpleIntegerProperty(numberOfSprints).asObject();
						}
						return null;
					}
				});
		
		gradesCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Projekt, Double>, ObservableValue<Double>>() {
					@Override
					public ObservableValue<Double> call(CellDataFeatures<Projekt, Double> param) {

						Projekt project = param.getValue();
					
						if (project != null) {
							Double projectGrade = ServiceFacade.getProject(project).getNote();
							if( projectGrade != null)
								return new SimpleDoubleProperty(projectGrade).asObject();
						}
						return null;
					}
				});

		

		// offne das Projekt, wenn es zweimal mit der linken Maustaste gedrueckt wurde.
		projectsTable.setOnMousePressed(mouseEvent -> {
			/*
			 * OLBERTZ Dafuer haette ich mir jetzt eine Werkzeugmethode geschrieben, denn eine
			 * Ueberpruefung auf Doppelklick kann man immer mal braucnen. 
			 */
			if ((mouseEvent.isPrimaryButtonDown()) && (mouseEvent.getClickCount() == 2)) {
				try {
					openProject();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void initNotes() {
		/*
		 * OLBERTZ Insgesamt sollten Sie sich auf eine Sprache einigen. Links steht auf Englisch notes - rechts auf Deutsch
		 * getNotizen und Module auf Englisch. Das wird ein bisschen chaotisch. Sprach-Chaos dient nicht der Lesbarkeit. 
		 */
		String notes = module.getNotiz();
		notesTextArea.setText(notes);

	}

	public void initTopicCards() throws IOException {

		// Alle aktuell angezeigten Karten löschen.
		vboxScrollPane.getChildren().clear();

		List<Thema> themen = ServiceFacade.getAllTopics(module);

		for (int i = 0; i < themen.size(); i++) {
			TopicCardController.setModuleController(this);
			FXMLLoader fxmlLoader = new FXMLLoader();
			Node newTopicCard = fxmlLoader.load(getClass().getResource("/fxmls/topicCard.fxml").openStream());
			TopicCardController topicCardController = (TopicCardController) fxmlLoader.getController();

			// Lege die Informationen zur Themenkarte fest.
			String topicName = themen.get(i).getName();

			topicCardController.setName(topicName);
			topicCardController.setModule(module);
			topicCardController.setTopic(themen.get(i));
			

			// die Themenkarte anzeigen, indem sie zum ScrollPane hinzufügen.
			vboxScrollPane.getChildren().add(newTopicCard);

		}

	}

	private void openProject() throws IOException {
		// ueberpruefe , ob mindestens eine Zeile der Projekttabelle ausgewaehlt ist.
		if (projectsTable.getItems().isEmpty() || projectsTable.getSelectionModel().isEmpty()) {
			return;
		}

		Projekt toBeUpdatedProject = projectsTable.getSelectionModel().getSelectedItem();

		ProjectController.setProject(toBeUpdatedProject);

		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root = fxmlLoader.load(getClass().getResource("/fxmls/Project.fxml").openStream());
		ProjectController projectController = (ProjectController) fxmlLoader.getController();

		Projekt project = ServiceFacade.getProject(toBeUpdatedProject);
		// Daten des Projekts festlegen.
		String name = project.getName();
		String notes = project.getNotizen();
		Thema topic = project.getThema();
		String mainUrl = project.getMainUrl();
		int semester = project.getSemester();
		Double grade = project.getNote();
		
		List<Student> studentsList = ServiceFacade.getStudents(project);
		List<Sprint> sprintsList = ServiceFacade.getSortedSprints(project);
		List<URLs> urlsList = ServiceFacade.getURLs(project);

		projectController.setProjectName(name);
		projectController.setProjectTopic(topic.getName());
		projectController.setProjectYear(semester);
		projectController.setProjectLink(mainUrl);
		if(grade != null)
			projectController.setProjectGrade(grade);
		else
			projectController.setProjectGrade(0.0);
		projectController.initStudentsTable(studentsList);
		projectController.initSprintsTable(sprintsList);
		projectController.initURLSTable(urlsList);
		projectController.initNotes(notes);
		// um Projekttablle nach dem Hinzufügen eines Sprints oder einer Note zu aktualisieren
		projectController.setModuleController(this);

		displayInNewStage(root);
	}
	
	/**
	 * das ist die container für die Wwarnung,die angezeigt wird,wenn man ein Thema
	 * loeschen will,das immer noch mit einem Projekt verbundem ist
	 * 
	 * @return
	 */
	public StackPane getTopicsStackPane() {
		return topicsStackPane;
	}


	private void displayInNewStage(Parent root) {
		// das Projekt in einem neuen Fenster anzeigen.
		Stage stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.getIcons().add(new Image("/img/logo.png"));
		stage.setTitle("Project");
		stage.setScene(new Scene(root, 1100, 650));
		stage.setMinWidth(715);
		stage.setMinHeight(550);
		stage.show();
		ResizeHelper.addResizeListener(stage);
	}

	private void loadHomePage() throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Home.fxml"));

		Scene scene = new Scene(root, ModuletabPane.getScene().getWidth(), ModuletabPane.getScene().getHeight());
		Stage curStage = (Stage) ModuletabPane.getScene().getWindow();
		curStage.setScene(scene);

		ResizeHelper.addResizeListener(curStage);

	}

	private Dialog<Object> createDialog(DialogPane dialogPane) {

		Dialog<Object> dialog = new Dialog<Object>();

		dialog.initStyle(StageStyle.TRANSPARENT);
		dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/img/logo.png"));

		dialog.setDialogPane(dialogPane);

		return dialog;

	}

	private void initImagesAndCss() {
		Image addTopicImg = new Image(getClass().getResource("/img/add.png").toExternalForm());
		newTopicBtnImageView.setImage(addTopicImg);
		
		tabPane.getStylesheets().add(getClass().getResource("/css/HomeStyle.css").toExternalForm());
		
	}

	/**
	 * Schließt die Bühne über den "X"-Button .
	 * 
	 * @param dialog
	 * @author Feras
	 */
	private void abilityToCloseFromXButton(final Dialog<Object> dialog) {

		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(e -> dialog.hide());
	}

}
