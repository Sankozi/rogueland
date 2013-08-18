package org.sankozi.rogueland.resources;

import com.google.common.base.Throwables;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;

/**
 *
 * @author sankozi
 */
public class ResourceProvider {
    private final static Logger LOG = LogManager.getLogger(ResourceProvider.class);
    private final static Cache<String, Font> FONT_CACHE = CacheBuilder.newBuilder()
            .concurrencyLevel(1).build();

    private final static ResourceBundle LABELS = ResourceBundle.getBundle("org.sankozi.rogueland.resources.labels");

    public static String getLabel(String key){
        try {
            return LABELS.getString(key);
        } catch (MissingResourceException ex){
            LOG.warn(ex.getMessage());
            return key;
        }
    }

    public static URL getImageUrl(String name){
        return ResourceProvider.class.getResource(name);
    }

	public static Image getImage(String name){
		try {
            URL imageUrl = ResourceProvider.class.getResource(name);
            if(imageUrl == null){
                throw new IllegalArgumentException("no resource on path : " + name);
            }
			Image ret = ImageIO.read(imageUrl);
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
