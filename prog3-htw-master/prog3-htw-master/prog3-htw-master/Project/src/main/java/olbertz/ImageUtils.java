package olbertz;

import java.awt.Image;

public final class ImageUtils {
	private ImageUtils() {
	}
	
	public static Image getAddImage() {
		final String path = FXPathConstants.IMAGE_ADD;
		Image image = new Image(ImageUtils.class.getResource(path).toExternalForm());
		return image;
	}
}
