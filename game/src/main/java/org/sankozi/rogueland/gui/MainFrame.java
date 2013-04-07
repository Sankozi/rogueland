package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author sankozi
 */
@Singleton
public class MainFrame extends JFrame {
    private final static Logger LOG = LogManager.getLogger(MainFrame.class);

    JPanel contentPane = new JPanel();
    LogPanel logPanel;
    JComponent mainPanel;

    {
        setSize(new Dimension(400, 400));
    }

    @Inject
    public MainFrame(
            @Named("exit") Action exit,
            @Named("main-menu") JMenuBar menu,
			@Named("main-panel") JComponent mainPanel,
			LogPanel logPanel,
			HealthBar bar) {
        this.mainPanel = mainPanel;
        this.logPanel = logPanel;
        this.setJMenuBar(menu);
        this.setContentPane(contentPane);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit.actionPerformed(new ActionEvent(e.getSource(), e.getID(), null, 0));
            }
        });

        contentPane.setLayout(new MigLayout("fill, wrap 2","[grow][200!]","[grow][50!]"));
        contentPane.add(mainPanel, "span 1 2, grow");
        contentPane.add(logPanel, "grow");
        contentPane.add(bar, "grow");
        LOG.info("created MainFrame");
    }
}
