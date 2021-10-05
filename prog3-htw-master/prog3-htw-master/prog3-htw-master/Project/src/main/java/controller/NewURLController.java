package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import facades.ServiceFacade;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.implementation.Projekt;
import model.implementation.URLs;
import utils.WindowManager;

/**
 * 
 * Steuert den Dialog zum Erstellen eines neuen URLs
 * 
 * @author Feras
 *
 */
public class NewURLController implements Initializable {

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

	private TableView<URLs> urlsTable;

	private Projekt project;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initImages();
	}

	@FXML
	void saveNewURL(ActionEvent event) {

		String name = urlNameTextField.getText();
		String url = urlTextField.getText();

		URLs newUrl = new URLs();
		newUrl.setName(name);
		newUrl.setUrl(url);

		project.addUrl(newUrl);

		ServiceFacade.addURL(newUrl);

		List<URLs> urlsList = ServiceFacade.getURLs(project);

		urlsTable.setItems(FXCollections.observableList(urlsList));

	}

	public void setURLSTable(TableView<URLs> urlsTable) {
		this.urlsTable = urlsTable;

	}

	public void setProject(Projekt project) {
		this.project = project;
	}

	@FXML
	void exit(ActionEvent event) {
		WindowManager.exit(exitBtn);

	}

	public void initImages() {

		Image close = new Image(getClass().getResource("/img/whiteClose.png").toExternalForm());
		closeImageView.setImage(close);

	}
}
