package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
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
    JComponent logPanel;
    JComponent levelPanel;

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
    public void setLevelPanel(@Named("level-panel") JComponent levelPanel){
        this.levelPanel = levelPanel;
        contentPane.add(levelPanel ,BorderLayout.CENTER);
    }

    public void setLogPanel(@Named("log-panel") JComponent logPanel){
        this.logPanel = logPanel;
        contentPane.add(logPanel, BorderLayout.EAST);
    }
}
