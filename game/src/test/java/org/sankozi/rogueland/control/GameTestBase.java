package org.sankozi.rogueland.control;

import com.google.inject.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.sankozi.rogueland.model.Controls;
import org.sankozi.rogueland.model.Player;

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
        }
    };
    private static <T> Provider<T> bugHack(Provider<T> ret){ return ret;}

    @BeforeClass
    public static void initGuice(){
        injector = Guice.createInjector(SIMPLE_GAME_MODULE);
    }

    /**
     * Returns game in with player just wait all the time
     * @return
     */
    protected Game getSimpleGame(){
        return injector.getInstance(Game.class);
    }
}
