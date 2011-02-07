package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.sankozi.rogueland.control.LogListener;

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
            @Named("main-menu") JMenuBar menu) {
        super(app);
        contentPane.setLayout(new BorderLayout());
        this.setMenuBar(menu);
        this.setComponent(contentPane);
        LOG.info("created MainFrame");
    }

    @Inject
    public void setLevelPanel(@Named("level-panel") LevelPanel levelPanel){
        this.levelPanel = levelPanel;
        contentPane.add(levelPanel ,BorderLayout.CENTER);
        if(logPanel != null){
            levelPanel.addLogListener((LogListener) logPanel);
        }
    }

    @Inject
    public void setLogPanel(LogPanel logPanel){
        LOG.info("injecting log-panel " + logPanel);
        this.logPanel = logPanel;
        contentPane.add(logPanel, BorderLayout.EAST);
        if(levelPanel != null){
            levelPanel.addLogListener((LogListener) logPanel);
        }
    }
}
