package org.sankozi.rogueland.control;

import com.google.inject.*;
import com.google.inject.spi.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.sankozi.rogueland.model.Controls;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Player;
import org.sankozi.rogueland.test.TestLevelGenerator;


import static java.lang.System.err;
/**
 * Base clas for Game tests
 *
 * @author sankozi
 */
public abstract class GameTestBase {
    private final static Logger LOG = LogManager.getLogger(GameTestBase.class);

    private static Injector injector;

    private final static Module SIMPLE_GAME_MODULE = new AbstractModule() {
        @Override
        protected void configure() {

            bind(Player.class).toProvider(bugHack(() -> {
                Player player = new Player();
                player.setControls(Controls.ALWAYS_WAIT);
                return player;
            }));

            bind(Level.class).toProvider(bugHack(() -> {
                Level ret = new Level(10, 10);
                TestLevelGenerator.generate(ret);
                return ret;
            }));

            bind(Game.class).toProvider(GameProvider.class);
        }
    };
    private static <T> Provider<T> bugHack(Provider<T> ret){ return ret;}

    @BeforeClass
    public static void initGuice(){
        try {
            injector = Guice.createInjector(SIMPLE_GAME_MODULE);
        } catch (CreationException ex) {
            err.println("Trying to show all guice messages...");
            for(Message message : ex.getErrorMessages()){
                err.println(message.getMessage());
            }
            err.println("...done. Exception message:");
            err.println(ex.getMessage());
//            ex.printStackTrace(err);
        } catch (Throwable ex) {
//            ex.printStackTrace(err);
        }
    }

    /**
     * Returns game in with player just wait all the time
     * @return
     */
    protected Game getSimpleGame(){
        return injector.getInstance(Game.class);
    }
}
