package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import facades.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.implementation.Projekt;
import model.implementation.URLs;
import utils.WindowManager;

/**
 * 
 * Steuert den Dialog zum Bearbeiten eines URLs.
 * 
 * @author Feras
 *
 */
public class EditURLController implements Initializable {
	// OLBERTZ Sind Sie sicher, dass das statisch sein soll?
	private static URLs toBeUpdatedURL;

	@FXML
	private DialogPane addDialogPane;

	@FXML
	private JFXTextField urlNameTextField;

	@FXML
	private JFXTextField urlTextField;

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
	void editURL(ActionEvent event) {

		toBeUpdatedURL.setName(urlNameTextField.getText());
		toBeUpdatedURL.setUrl(urlTextField.getText());
	
		toBeUpdatedURL.setProject(project);

		ServiceFacade.updateURL(toBeUpdatedURL);

		WindowManager.exit(exitBtn);
		
	}

	@FXML
	void exit(ActionEvent event) {

		WindowManager.exit(exitBtn);
	}

	private void initEditSprintDialog() {

		if (toBeUpdatedURL != null) {

			if (toBeUpdatedURL.getName() != null)
				urlNameTextField.setText(toBeUpdatedURL.getName());
			if (toBeUpdatedURL.getUrl() != null)
				urlTextField.setText(toBeUpdatedURL.getUrl());

		}
	}

	/*
	 * OLBERTZ Java-Namenskonvention: In Java ist die Verwendung eines Unterstrichs eher unueblich. Am besten
	 * bleiben Sie bei der ueblichen Java-Namenskonvention, dass eine setMethode so aussieht:
	 * 
	 * 	public static void setToBeUpdated(URLs toBeUpdatedURL) {
			this.toBeUpdatedURL = toBeUpdatedURL;
		}
	 * 
	 */
	public static void setToBeUpdated(URLs toBeUpdatedURL_) {
		toBeUpdatedURL = toBeUpdatedURL_;
	}

	public void setProject(Projekt project) {
		this.project = project;

	}
	
	private void initImages() {

		Image close = new Image(getClass().getResource("/img/whiteClose.png").toExternalForm());
		closeImageView.setImage(close);

	}
}
