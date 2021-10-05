package olbertz;

import java.io.File;

public final class FXPathConstants {
	private FXPathConstants() {
	}
	
	private static final String SUFFIX_FXML = ".fxml";
	private static final String SUFFIX_PNG = ".png"; 
	
	private static final String FXML_DIRECTORY = File.separator + "fxmls";
	private static final String IMAGE_DIRECTORY = File.separator + "img";
	
	public static final String INFORMATION_ALERT_PAGE = FXML_DIRECTORY + File.separator + "informationAlert" + SUFFIX_FXML;
	public static final String NEW_MODUL_CARD_DIALOG = FXML_DIRECTORY + File.separator + "NewModuleCardDialog" + SUFFIX_FXML;
	
	public static final String IMAGE_WHITE_CLOSE = IMAGE_DIRECTORY + File.separator + "whiteClose" + SUFFIX_PNG;
	public static final String IMAGE_ADD = IMAGE_DIRECTORY + File.separator + "add" + SUFFIX_PNG;
}
