package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;

import dao.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.implementation.Projekt;
import model.implementation.Student;
import utils.WindowManager;


/**
 * Steuert den Dialog zum Bearbeiten eines Studenten.
 * 
 * @author Feras
 *
 */
public class EditStudentController implements Initializable {

	@FXML
	private DialogPane dialogPane;

	@FXML
	private StackPane stackPane;

	@FXML
	private JFXTextField maticulationTextField;

	@FXML
	private JFXTextField firstNameTextField;

	@FXML
	private JFXTextField lastNameTextField;

	@FXML
	private JFXTextField gradeTextField;

	@FXML
	private JFXButton saveBtn;

	@FXML
	private JFXButton exitBtn;

	@FXML
	private ImageView closeImageView;
	
	private static Student toBeUpdatedStudent;
	
	private InformationAlertController informationAlertController;
	private VBox informationAlert;
	public static JFXDialog informationAlertDialog;
	private TableView<Student> studentsTable;
	private Projekt project;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initImages();
		initInformationAlert();
		initEditStudentDialog();

	}

	@FXML
	void editStudent(ActionEvent event) {
		edit();
	
	}

	@FXML
	void exit(ActionEvent event) {
		WindowManager.exit(exitBtn);

	}


	public void setStudentsTable(TableView<Student> studentsTable) {
		this.studentsTable = studentsTable;
	}
	
	public void setProject(Projekt project) {
		this.project = project;

	}
	
	public static void setToBeUpdated(Student toBeUpdatedStudent_) {

		toBeUpdatedStudent = toBeUpdatedStudent_;
	}
	

	private void edit() {
		
		try {
			int matricNr = Integer.valueOf(maticulationTextField.getText().trim());
			toBeUpdatedStudent.setMatrikelnummer(matricNr);	

		} catch (IllegalArgumentException e) {
			// OLBERTZ Bitte niemals Strings in den Code. Benutzen Sie dafuer properties-Datei (vgl. Kapitel Internationalisierung).
			showMessage("Enter a valid matriculation number!");
			return;
		}
		
		try {
			Double grade = Double.valueOf(gradeTextField.getText().trim());
			toBeUpdatedStudent.setNote(grade);	

		} catch (IllegalArgumentException e) {
			showMessage(e.getMessage());
			return;
		}
		
		toBeUpdatedStudent.setName(lastNameTextField.getText());
		toBeUpdatedStudent.setVorname(firstNameTextField.getText());
		

		toBeUpdatedStudent.setProjekt(project);
		HibernateUtil.updateStudent(toBeUpdatedStudent);
		
		WindowManager.exit(exitBtn);

	}

	
	
	
	private void initInformationAlert() {
		try {
			FXMLLoader dialogLoader = new FXMLLoader();
			/*
        	 * OLBERTZ Solche Pfade sind immer ganz gut in Konstanten aufgehoben. Dabei versuche ich diese
        	 * Konstanten so aufzubauen, dass Aenderungen an der Struktur der Anwendung moeglichst wenige
        	 * Folgen haben. Ein kleines Beispiel sehen Sie in olbertz.FXConstants. Dort habe ich
        	 * noch aus Demonstrationszwecken Unterverzeichnisse eingebaut. Ausserdem habe ich als Trennzeichen
        	 * File.separator verwendet, damit es keine Probleme zwischen verschiedenen Betriebssystemen gibt.
        	 */

			informationAlert = dialogLoader
					.load(ModuleController.class.getResource("/fxmls/InformationAlert.fxml").openStream());
			informationAlertController = (InformationAlertController) dialogLoader.getController();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		informationAlertDialog = new JFXDialog(stackPane, informationAlert, JFXDialog.DialogTransition.CENTER);
	}

	
	private void showMessage(String msg) {

		informationAlertController.setMsgLabel(msg);
		informationAlertController.setErrorFromEditStudentController(true);
		informationAlertDialog.show();
	}

	private void initEditStudentDialog() {

		if (toBeUpdatedStudent != null) {

			maticulationTextField.setText(String.valueOf(toBeUpdatedStudent.getMatrikelnummer()));
			if (toBeUpdatedStudent.getName() != null)
				lastNameTextField.setText(toBeUpdatedStudent.getName());
			if (toBeUpdatedStudent.getVorname() != null)
				firstNameTextField.setText(toBeUpdatedStudent.getVorname());
			if(toBeUpdatedStudent.getNote() != 0.0)
				gradeTextField.setText(String.valueOf(toBeUpdatedStudent.getNote()));
			else
				gradeTextField.setText("");

		}
	}
	
	private void initImages() {
		// OLBERTZ Das gleiche wie oben gilt natuerlich auch fuer die Pfade zu Bildern.
		Image close = new Image(getClass().getResource("/img/whiteClose.png").toExternalForm());
		closeImageView.setImage(close);

	}


}
