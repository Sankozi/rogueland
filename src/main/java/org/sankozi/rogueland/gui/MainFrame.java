package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;

/**
 *
 * @author sankozi
 */
public class MainFrame extends FrameView {
    private final static Logger LOG = Logger.getLogger(MainFrame.class);

    JPanel contentPane = new JPanel();
    LogPanel logPanel;
    LevelPanel levelPanel;

    @Inject
    public MainFrame(Application app,
            @Named("main-menu") JMenuBar menu,
			@Named("level-panel") LevelPanel levelPanel,
			LogPanel logPanel) {
        super(app);
        this.levelPanel = levelPanel;
        this.logPanel = logPanel;
        this.setMenuBar(menu);
        this.setComponent(contentPane);

        contentPane.setLayout(new MigLayout("","[grow][200!]","[grow]"));
        contentPane.add(levelPanel, "grow");
        contentPane.add(logPanel, "growy");
        LOG.info("created MainFrame");
    }
}
