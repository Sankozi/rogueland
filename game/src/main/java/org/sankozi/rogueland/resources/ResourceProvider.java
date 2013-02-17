package org.sankozi.rogueland.resources;

import com.google.common.base.Throwables;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;

/**
 *
 * @author sankozi
 */
public class ResourceProvider {
    private final static Cache<String, Font> FONT_CACHE = CacheBuilder.newBuilder()
            .concurrencyLevel(1).build();

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

    public static Font getFont(String name, float size) {
        try {
            return FONT_CACHE.get(name, ()
                    -> Font.createFont(Font.TRUETYPE_FONT, ResourceProvider.class.getResourceAsStream("fonts/" + name)))
                .deriveFont(size);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

	public static Cursor getCursor(String name, Point center){
		Image im = getImage(name);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor ret = toolkit.createCustomCursor(im, center, name);
		return ret;
	}
}
