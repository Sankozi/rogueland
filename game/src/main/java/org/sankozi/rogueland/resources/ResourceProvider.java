package org.sankozi.rogueland.resources;

import com.google.common.base.Throwables;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author sankozi
 */
public class ResourceProvider {

    public static URL getLog4jProperties() {
        return ResourceProvider.class.getResource("log4j.properties");
    }

	public static Image getImage(String name){
		try {
			Image ret = ImageIO.read(ResourceProvider.class.getResource(name));
			return ret;
		} catch (IOException ex) {
			throw new ResourceException("cannot load image : " + name, ex);
		}
	}

    public static Font getFont(String name, float size){
        Font ret;
        try {
            ret = Font.createFont(Font.TRUETYPE_FONT, 
                    ResourceProvider.class.getResourceAsStream("fonts/" + name)).deriveFont(size);
        } catch (FontFormatException | IOException ex) {
            throw Throwables.propagate(ex);
        }
        return ret;
    }

	public static Cursor getCursor(String name, Point center){
		Image im = getImage(name);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor ret = toolkit.createCustomCursor(im, center, name);
		return ret;
	}
}
