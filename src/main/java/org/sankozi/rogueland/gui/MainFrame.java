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
    JComponent logPanel = new JPanel();
    LevelPanel levelPanel;

    @Inject
    public MainFrame(Application app,
            @Named("main-menu") JMenuBar menu) {
        super(app);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(levelPanel = new LevelPanel(),BorderLayout.CENTER);
        contentPane.add(logPanel, BorderLayout.EAST);
        this.setMenuBar(menu);
        this.setComponent(contentPane);
        LOG.info("created MainFrame");
    }
}
