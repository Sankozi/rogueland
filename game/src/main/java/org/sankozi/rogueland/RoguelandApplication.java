package org.sankozi.rogueland;

import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.spi.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel;
import org.sankozi.rogueland.gui.GuiModule;
import org.sankozi.rogueland.gui.MainFrame;
import org.sankozi.rogueland.resources.ResourceProvider;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The main class of the application.
 */
public class RoguelandApplication {
    private final static Logger LOG = LogManager.getLogger(RoguelandApplication.class);

    Module module = new RoguelandModule();
	Module guiModule = new GuiModule();

    /**
     * At startup create and show the main frame of the application.
     */
    protected void startup() {
        try {
            Injector injector = Guice.createInjector(module, guiModule);

            injector.getInstance(MainFrame.class).setVisible(true);
        } catch (CreationException ex) {
            LOG.error("Trying to show all guice messages...");
            for(Message message : ex.getErrorMessages()){
                LOG.error(message.getMessage());
            }
            LOG.error("...done. Exception message:");
            LOG.error(ex.getMessage(), ex);
        } catch (Throwable t) {
            LOG.error(t.getMessage(), t);
        }
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) throws URISyntaxException {
        URI configuration = RoguelandApplication.class.getResource("/log4j2.xml").toURI();
        Configurator.initialize("config", null, configuration);
        LOG.info("using log4j configuration '{}'", configuration);
        try {
            UIManager.setLookAndFeel(new SubstanceDustCoffeeLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException();
        }
        SwingUtilities.invokeLater(() -> new RoguelandApplication().startup());
    }
}
