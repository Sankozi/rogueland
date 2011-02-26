package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.swing.JComponent;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.control.Game;
import org.sankozi.rogueland.control.LogListener;
import org.sankozi.rogueland.model.Controls;
import org.sankozi.rogueland.model.Direction;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Move;

/**
 * Panel that renders current level state
 * @author sankozi
 */
public class LevelPanel extends JComponent{
    private final static Logger LOG = Logger.getLogger(LevelPanel.class);

    Game game;
    Rectangle levelSize = new Rectangle(0, 0, Level.WIDTH, Level.HEIGHT);

    TilePainter tilePainter = new FontPainter();
    Rectangle playerLocation = new Rectangle(50, 50, 10, 10);
    Direction cursorDirection;

    GuiControls gc = new GuiControls();

    {
        this.setFocusable(true);
        this.addKeyListener(gc);
        this.addMouseMotionListener(new MoveCursor());
        this.addMouseListener(gc);
    }

    public KeyListener getKeyListener(){
        return gc;
    }

    @Override
    public void paint(Graphics g) {
        tilePainter.paint(levelSize, game.getLevel().getTiles(), g);
    }

    @Inject
    public void setGame(Game game){
        this.game = game;
        game.setControls(gc);
        game.start();
    }

    public void addLogListener(LogListener logListener){
        game.addLogListener(logListener);
    }

    private void refreshGameState(){
        repaint();
        playerLocation = tilePainter.getPixelLocation(levelSize, game.getPlayer().getLocation());
    }

    private void setDirectionCursor(Direction dir){
        cursorDirection = dir;
        switch(dir){
            case NW:
                LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                break;
            case N:
                LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                break;
            case NE:
                LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                break;
            case W:
                LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                break;
            case C:
                LevelPanel.this.setCursor(Cursor.getDefaultCursor());
                break;
            case E:
                LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                break;
            case SW:
                LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                break;
            case S:
                LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                break;
            case SE:
                LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                break;
        }
    }

    private class MoveCursor implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //TODO improve
            int x = e.getX();
            int y = e.getY();
            if(x < playerLocation.x){
                if(y < playerLocation.y){
                    setDirectionCursor(Direction.NW);
                } else if(y > playerLocation.y + playerLocation.height){
                    setDirectionCursor(Direction.SW);
                } else {
                    setDirectionCursor(Direction.W);
                }
            } else if(x > playerLocation.x + playerLocation.width){
                if(y < playerLocation.y){
                    setDirectionCursor(Direction.NE);
                } else if(y > playerLocation.y + playerLocation.height){
                    setDirectionCursor(Direction.SE);
                } else {
                    setDirectionCursor(Direction.E);
                }
            } else {
                if(y < playerLocation.y){
                    setDirectionCursor(Direction.N);
                } else if(y > playerLocation.y + playerLocation.height){
                    setDirectionCursor(Direction.S);
                } else {
                    setDirectionCursor(Direction.C);
                }
            }
        }
    }

    private class GuiControls implements Controls, KeyListener, MouseListener {

        BlockingQueue<Move> keysPressed = new ArrayBlockingQueue<Move>(5);

        @Override
        public Move waitForMove() throws InterruptedException {

            LevelPanel.this.refreshGameState();
            Move move = keysPressed.take();
//            LOG.info("key read");
            
            return move;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        public Move fromKeyCode(int code){
            switch(code){
                case KeyEvent.VK_UP:
                    return Move.Go.NORTH;
                case KeyEvent.VK_LEFT:
                    return Move.Go.WEST;
                case KeyEvent.VK_DOWN:
                    return Move.Go.SOUTH;
                case KeyEvent.VK_RIGHT:
                    return Move.Go.EAST;
                default:
                    return null;
            }
        }


        @Override
        public void keyPressed(KeyEvent e) {
            try {
                Move move = fromKeyCode(e.getKeyCode());
                if(move != null){
                    keysPressed.offer(move, 1, TimeUnit.SECONDS);
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
            try {
                keysPressed.offer(cursorDirection.toSingleMove(), 1, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                LOG.error(ex.getMessage(), ex);
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
}
