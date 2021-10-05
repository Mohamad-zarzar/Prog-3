package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;

import facades.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.implementation.Projekt;
import model.implementation.Sprint;
import utils.WindowManager;

/**
 * 
 * Steuert den Dialog zum Bearbeiten eines Sprints.
 * 
 * @author Feras
 *
 */
public class EditSprintController implements Initializable {

	private static Sprint toBeUpdatedSprint;

	@FXML
	private DialogPane addDialogPane;

	@FXML
	private JFXTextArea descriptionTextArea;

	@FXML
	private JFXTextArea notesTextArea;

	@FXML
	private JFXDatePicker startDatepicker;

	@FXML
	private JFXDatePicker endDatePicker;

	@FXML
	private JFXButton saveBtn;

	@FXML
	private JFXButton exitBtn;

	@FXML
	private ImageView closeImageView;

	private Projekt project;

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initEditSprintDialog();
		 initImages();
		
	}
	
	@FXML
	void editSprint(ActionEvent event) {

		toBeUpdatedSprint.setSprintBeschreibung(descriptionTextArea.getText());
		toBeUpdatedSprint.setNotizen(notesTextArea.getText());
		toBeUpdatedSprint.setSprintAnfang(startDatepicker.getValue());
		toBeUpdatedSprint.setSprintEnde(endDatePicker.getValue());
		toBeUpdatedSprint.setProjekt(project);

		ServiceFacade.updateSprint(toBeUpdatedSprint);

		WindowManager.exit(exitBtn);
	}

	@FXML
	void exit(ActionEvent event) {

		WindowManager.exit(exitBtn);
	}

	

	public static void setToBeUpdated(Sprint toBeUpdatedSprint_) {

		toBeUpdatedSprint = toBeUpdatedSprint_;
	}

	public void setProject(Projekt project) {
		this.project = project;

	}
	
	
	private void initEditSprintDialog() {

		if (toBeUpdatedSprint != null) {

			if (toBeUpdatedSprint.getSprintBeschreibung() != null)
				descriptionTextArea.setText(toBeUpdatedSprint.getSprintBeschreibung());
			if (toBeUpdatedSprint.getNotizen() != null)
				notesTextArea.setText(toBeUpdatedSprint.getNotizen());
			if (toBeUpdatedSprint.getSprintAnfang() != null)
				startDatepicker.setValue(toBeUpdatedSprint.getSprintAnfang());
			if (toBeUpdatedSprint.getSprintEnde() != null)
				endDatePicker.setValue(toBeUpdatedSprint.getSprintEnde());

		}
	}
	
	private void initImages() {

		Image close = new Image(getClass().getResource("/img/whiteClose.png").toExternalForm());
		closeImageView.setImage(close);

	}

}
