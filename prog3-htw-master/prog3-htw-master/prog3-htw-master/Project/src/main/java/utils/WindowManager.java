package utils;


import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * ein Fenster minimieren ,maximieren oder schließen.
 * 
 * @author Feras
 *
 */
public class WindowManager {
	
	
	
	public static void exit(Node node){

		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
	
	
	public static void maximize(Node node) {

		Stage stage = (Stage) node.getScene().getWindow();

		if (stage.isMaximized())
			stage.setMaximized(false);
		else
			stage.setMaximized(true);
	}

	
	public static void minimize(Node node) {

		Stage stage = (Stage) node.getScene().getWindow();
		stage.setIconified(true);
	}
}
