package org.sankozi.rogueland.resources;

import com.google.common.base.Supplier;
import java.awt.Cursor;
import java.awt.Point;
import org.apache.log4j.Logger;

/**
 *
 * @author sankozi
 */
public enum Cursors implements Supplier<Cursor> {
	ARROW_N("arrow_N.png", new Point(15,7)),
	ARROW_NW("arrow_NW.png", new Point(10,10)),
	ARROW_NE("arrow_NE.png", new Point(21,10)),
	ARROW_S("arrow_S.png", new Point(15,25)),
	ARROW_SW("arrow_SW.png", new Point(10,21)),
	ARROW_SE("arrow_SE.png", new Point(21,21)),
	ARROW_W("arrow_W.png", new Point(7,16)),
	ARROW_E("arrow_E.png", new Point(24,16));

	private final static Logger LOG = Logger.getLogger(Cursors.class);

	private final Cursor cursor;

	Cursors(String name, Point center){
		cursor = ResourceProvider.getCursor(name, center);
	}

	@Override
	public Cursor get() {
		return cursor;
	}
}
