package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import javax.swing.JComponent;
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
@Singleton
public class MainFrame extends FrameView {
    private final static Logger LOG = Logger.getLogger(MainFrame.class);

    JPanel contentPane = new JPanel();
    LogPanel logPanel;
    JComponent mainPanel;

    @Inject
    public MainFrame(Application app,
            @Named("main-menu") JMenuBar menu,
			@Named("main-panel") JComponent mainPanel,
			LogPanel logPanel,
			HealthBar bar) {
        super(app);
        this.mainPanel = mainPanel;
        this.logPanel = logPanel;
        this.setMenuBar(menu);
        this.setComponent(contentPane);

        contentPane.setLayout(new MigLayout("","[grow][200!]","[grow][50!]"));
        contentPane.add(mainPanel, "span 1 2, grow");
        contentPane.add(logPanel, "growy, wrap");
        contentPane.add(bar, "");
        LOG.info("created MainFrame");
    }
}
