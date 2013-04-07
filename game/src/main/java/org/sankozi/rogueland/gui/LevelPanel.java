package org.sankozi.rogueland.gui;

import com.google.inject.Inject;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import org.apache.logging.log4j.*;
import org.sankozi.rogueland.model.Controls;
import org.sankozi.rogueland.model.Coords;
import org.sankozi.rogueland.model.Direction;
import org.sankozi.rogueland.model.Move;
import org.sankozi.rogueland.resources.Cursors;

/**
 * Panel that renders current level state
 * @author sankozi
 */
public class LevelPanel extends JComponent{
    private final static Logger LOG = LogManager.getLogger(LevelPanel.class);
	private final static long serialVersionUID = 1L;

    Rectangle playerLocation = null;
    Direction cursorDirection;

    transient GameSupport gameSupport;
    transient GuiControls gc = new GuiControls();
    transient ComponentListener componentListener;
	
	{
		init();
	}

    private void init(){
        this.setFocusable(true);
        this.addKeyListener(gc);
        this.addMouseMotionListener(new MoveCursor());
        this.addMouseListener(gc);
		componentListener = new ComponentAdapter() {
			@Override public void componentResized(ComponentEvent e) { gameSupport.resize(e.getComponent()); repaint();}
			@Override public void componentShown  (ComponentEvent e) { gameSupport.resize(e.getComponent()); repaint();}
		};
        this.addComponentListener(componentListener);
    }

    public KeyListener getKeyListener(){
        return gc;
    }

    @Override
    public void paint(Graphics g) {
        if(gameSupport.isGameStarted()){
		    gameSupport.paintLevelImage(g, this);
        } else {
            super.paint(g);
        }
    }

	/**
	 * Method for injecting GameSupport, method adds LevelPanel to GameListeners
	 * and sets Controls to gc
	 * @param support 
	 */
	@Inject 
	void setGameSupport(GameSupport support){
		gameSupport = support;
        gameSupport.addListener(new GameListener(){
            @Override public void onEvent(GameEvent event) { refreshGameState(); }
        });
		gameSupport.setControls(gc);
	}

    private void setDirectionCursor(Direction dir){
        cursorDirection = dir;
        switch(dir){
            case NW:
                LevelPanel.this.setCursor(Cursors.ARROW_NW.get());
                break;
            case N:
                LevelPanel.this.setCursor(Cursors.ARROW_N.get());
                break;
            case NE:
                LevelPanel.this.setCursor(Cursors.ARROW_NE.get());
                break;
            case W:
                LevelPanel.this.setCursor(Cursors.ARROW_W.get());
                break;
            case C:
                LevelPanel.this.setCursor(Cursor.getDefaultCursor());
                break;
            case E:
                LevelPanel.this.setCursor(Cursors.ARROW_E.get());
                break;
            case SW:
                LevelPanel.this.setCursor(Cursors.ARROW_SW.get());
                break;
            case S:
                LevelPanel.this.setCursor(Cursors.ARROW_S.get());
                break;
            case SE:
                LevelPanel.this.setCursor(Cursors.ARROW_SE.get());
                break;
        }
    }

    private void refreshGameState(){
//        LOG.info("refreshGameState");
        repaint();//submits repaint event to EDT
        playerLocation = gameSupport.getPlayerLocation();
//        LOG.debug("player location : " + playerLocation);
    }

    private class MoveCursor implements MouseMotionListener {
        final double first = Math.tan(Math.PI / 8.0);
        final double second = Math.tan(Math.PI / 2.0  - Math.PI / 8.0);

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
			if(playerLocation == null) return; //null means game hasn't loaded yet
			
            int x = e.getX();
            int y = e.getY();
            int px = playerLocation.x + playerLocation.width / 2;
            int py = playerLocation.y + playerLocation.height / 2;
            double dy = Math.abs(y-py);
            double dx = Math.abs(x-px);
            if(dx + dy < 20){//cursor close to the player
                setDirectionCursor(Direction.C);
            } else if(x < px){
                if(y < py) { //NW region
                    double tan = dx / dy;
                    if(tan < first){
                        setDirectionCursor(Direction.N);
                    } else if (tan < second) {
                        setDirectionCursor(Direction.NW);
                    } else {
                        setDirectionCursor(Direction.W);
                    }
                } else { //SW region
                    double tan = dy / dx;
                    if(tan < first){
                        setDirectionCursor(Direction.W);
                    } else if (tan < second) {
                        setDirectionCursor(Direction.SW);
                    } else {
                        setDirectionCursor(Direction.S);
                    }
                }
            } else {
                if(y < py) { //NE region
                    double tan = dx / dy;
                    if(tan < first){
                        setDirectionCursor(Direction.N);
                    } else if (tan < second) {
                        setDirectionCursor(Direction.NE);
                    } else {
                        setDirectionCursor(Direction.E);
                    }
                } else { //SE region
                    double tan = dy / dx;
                    if(tan < first){
                        setDirectionCursor(Direction.E);
                    } else if (tan < second) {
                        setDirectionCursor(Direction.SE);
                    } else {
                        setDirectionCursor(Direction.S);
                    }
                }
            }
        }
    }

    private class GuiControls implements Controls, KeyListener, MouseListener {

        BlockingQueue<Move> keysPressed = new ArrayBlockingQueue<>(2);

        @Override
        public Move waitForMove() throws InterruptedException {
//            LOG.info("waitForMove");
            Move move = keysPressed.take();
            return move;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        public Move fromKeyCode(int code){
            switch(code){
                case KeyEvent.VK_NUMPAD1:
                    return Move.Go.SOUTHWEST;
                case KeyEvent.VK_NUMPAD7:
                    return Move.Go.NORTHWEST;
                case KeyEvent.VK_NUMPAD9:
                    return Move.Go.NORTHEAST;
                case KeyEvent.VK_NUMPAD3:
                    return Move.Go.SOUTHEAST;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_NUMPAD8:
                    return Move.Go.NORTH;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_NUMPAD4:
                    return Move.Go.WEST;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_NUMPAD2:
                    return Move.Go.SOUTH;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_NUMPAD6:
                    return Move.Go.EAST;
				case KeyEvent.VK_Q:
					return Move.Rotate.COUNTERCLOCKWISE;
				case KeyEvent.VK_E:
					return Move.Rotate.CLOCKWISE;
                default:
                    return null;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
			if(!gameSupport.isGameStarted()){
				return;
			}
            try {
                Move move = fromKeyCode(e.getKeyCode());
                if(move != null){
                    boolean successfull = keysPressed.offer(move, 1, TimeUnit.SECONDS);
					if(!successfull){
						LOG.info("offer unsuccessfull");
					}
                }
            } catch (InterruptedException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
			if(!gameSupport.isGameStarted()){
				return;
			}
            if(SwingUtilities.isLeftMouseButton(e)){
                try {
                    keysPressed.offer(cursorDirection.toSingleMove(), 1, TimeUnit.SECONDS);
                } catch (InterruptedException ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            } else if(SwingUtilities.isRightMouseButton(e)){
                LOG.info("showing info = " + gameSupport.getCoordinatesDescription(new Coords(e.getX(), e.getY())).getAsString());
            } else {
                LOG.info("unsupported mouseClickedEvent");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
    	in.defaultReadObject();
		init();
	}
}
