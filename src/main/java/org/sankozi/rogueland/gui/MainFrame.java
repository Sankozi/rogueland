package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import javax.swing.JMenuBar;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;

/**
 *
 * @author sankozi
 */
public class MainFrame extends FrameView {
    private final static Logger LOG = Logger.getLogger(MainFrame.class);

    LevelPanel lp;

    @Inject
    public MainFrame(Application app,
            @Named("main-menu") JMenuBar menu) {
        super(app);
        this.setMenuBar(menu);
        this.setComponent(lp = new LevelPanel());
        LOG.info("created MainFrame");
    }
}
