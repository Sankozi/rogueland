package org.sankozi.rogueland.gui;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.apache.logging.log4j.*;
import org.sankozi.rogueland.control.Game;
import org.sankozi.rogueland.control.LogListener;
import org.sankozi.rogueland.model.*;
import org.sankozi.rogueland.model.coords.Coords;

/**
 * Class supporting interaction between GameState and GUI
 * @author sankozi
 */
@Singleton
class GameSupport {
    private final static Logger LOG = LogManager.getLogger(GameSupport.class);

    private final List<GameListener> listeners = Lists.newArrayList();

    private GameEvent gameEvent;

    /**
     * Lock that protects all mutable state of this object
     *
     * Write lock is locked before game is started and is released when player is moving 
	 * (i.e. game is waiting for human player move and providing gui information)
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final TilePainter painter;
	private final Provider<Game> gameProvider;
	private final Set<LogListener> logListeners = new HashSet<>();
	
    private Game game;
	private Controls gameControls;
    private Thread gameThread = new Thread(new GameStart());

    private Dimension paintedLevelFragment;
    private Rectangle levelSize;
    private BufferedImage levelImage = new BufferedImage(1,1, BufferedImage.TYPE_4BYTE_ABGR);
    private Color backgroundColor = Color.BLACK;

	@Inject
    public GameSupport(Provider<Game> gameProvider, TilePainter painter) {
		this.gameProvider = gameProvider;
		this.painter = painter;
		newGame();
    }

	public final void newGame(){
		if(this.game != null){
			endGame();
		}
		this.game = gameProvider.get();
		for(LogListener ll : logListeners){
			game.addLogListener(ll);
		}
		Preconditions.checkNotNull(game, "game cannot be null");
		this.levelSize = new Rectangle(0, 0,
				game.getLevel().getDim().width, game.getLevel().getDim().height);
		this.gameEvent = new GameEvent(game);
	}

	private void endGame(){
		this.gameThread.interrupt();
		try {
			this.readWriteLock.writeLock().lock();
			this.gameThread = new Thread(new GameStart());
		} finally {
			this.readWriteLock.writeLock().unlock();
		}
	}

	public void setControls(Controls controls){
		this.gameControls = controls;
	}

    public void resize(Component toComponent) {
//        LOG.info("component resized : new size : " + newDim);
        backgroundColor = toComponent.getBackground();
        BufferedImage newImage = new BufferedImage(toComponent.getWidth(), toComponent.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = null;
        try {
            g = newImage.createGraphics();
            if(readWriteLock.readLock().tryLock()){
                try {
                    g.setColor(backgroundColor);
                    painter.paint(game, g, toComponent.getWidth(), toComponent.getHeight());
                } finally {
                    readWriteLock.readLock().unlock();
                }
            }
        } finally {
			if(g != null){
            	g.dispose();
			}
        }
        levelImage = newImage;
    }

    public Image getLevelImage(){
        return levelImage;
    }

    public void addListener(GameListener listener){
//		LOG.info(this.toString() + " adding " + listener.getClass());
        listeners.add(listener);
    }

    public void removeListener(GameListener listener){
        listeners.remove(listener);
    }

    public void clearListeners(){
        this.listeners.clear();
    }

	/**
	 * Returns player location (in pixels) on painted field
	 * @return player tile location or null if game has not started
	 */
    public Rectangle getPlayerLocation(){
		if(game.isInitialized()){
        	return painter.getTileRectangle(game,
                    levelImage.getWidth(), levelImage.getHeight(),
                    game.getPlayer().getLocation());
		} else {
			return null;
		}
    }

    public Description getCoordinatesDescription(Coords pixelCoords){
        Tile tile = painter.getTilePixelLocation(game, levelImage.getWidth(), levelImage.getHeight(), pixelCoords);

        return tile == null ? Description.EMPTY : tile.getDescription();
    }

    public void fireGameEvent(GameEvent ge){
        for(GameListener listener : listeners){
            listener.onEvent(ge);
        }
    }

    void addLogListener(LogListener logListener) {
		logListeners.add(logListener);
        game.addLogListener(logListener);
    }

    public void startGame() {
        Preconditions.checkState(!gameThread.isAlive());
		this.game.setControls(new SynchronizedControls(gameControls));
        gameThread.start();
		LOG.info("starting game thread");
    }

    public Game getGame(){
        return game;
    }

	void paintLevelImage(Graphics g, JComponent comp) {
		try {
			readWriteLock.readLock().lock();
            g.setColor(comp.getBackground());
        	g.drawImage(levelImage, 0, 0, comp.getWidth(), comp.getHeight(), comp);
		} finally {
			readWriteLock.readLock().unlock();
		}
	}

    private class GameStart implements Runnable {
        @Override
        public void run() {
            readWriteLock.writeLock().lock();
			try {
            	game.provideRunnable().run();
			} catch (Throwable t) {
                t.printStackTrace();
				LOG.error(t.getMessage(), t);
			} finally {
				readWriteLock.writeLock().unlock();
			}
        }
    }

    private class SynchronizedControls implements Controls {

        private final Controls base;

        public SynchronizedControls(Controls base) {
            this.base = base;
        }

        @Override
        public Move waitForMove() throws InterruptedException {
            readWriteLock.writeLock().unlock();
			drawLevel();
            fireEvents();
            //allow level read while waiting for player move
            Move ret = base.waitForMove();
            readWriteLock.writeLock().lockInterruptibly();
            return ret;
        }

        private void fireEvents() {
            for (final GameListener gl : listeners) {
				SwingUtilities.invokeLater(new Runnable(){
					@Override public void run() {
                		gl.onEvent(gameEvent);
					}
				});
            }
        }

        private void drawLevel() {
			Graphics2D g = null;
            try {
				readWriteLock.writeLock().lock();
                g = levelImage.createGraphics();
                g.setColor(backgroundColor);
                painter.paint(game, g, levelImage.getWidth(), levelImage.getHeight());
            } finally {
				readWriteLock.writeLock().unlock();
                if (g != null) {
                    g.dispose();
                }
            }
        }
    }

	public boolean isGameStarted(){
		return gameThread.isAlive();
	}
}
