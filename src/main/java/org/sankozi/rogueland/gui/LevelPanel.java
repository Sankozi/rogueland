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
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Move;

/**
 * Panel that renders current level state
 * @author sankozi
 */
public class LevelPanel extends JComponent{
    private final static Logger LOG = Logger.getLogger(LevelPanel.class);

    Game game;
    TilePainter tilePainter = new FontPainter();

    Rectangle levelSize = new Rectangle(0, 0, Level.WIDTH, Level.HEIGHT);

    Rectangle playerLocation = new Rectangle(50, 50, 10, 10);

    GuiControls gc = new GuiControls();

    {
        this.setFocusable(true);
        this.addKeyListener(gc);
        this.addMouseMotionListener(new MoveCursor());
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
                    LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                } else if(y > playerLocation.y + playerLocation.height){
                    LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                } else {
                    LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                }
            } else if(x > playerLocation.x + playerLocation.width){
                if(y < playerLocation.y){
                    LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                } else if(y > playerLocation.y + playerLocation.height){
                    LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                } else {
                    LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                }
            } else {
                if(y < playerLocation.y){
                    LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                } else if(y > playerLocation.y + playerLocation.height){
                    LevelPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                } else {
                    LevelPanel.this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }

    private class GuiControls implements Controls, KeyListener {

        BlockingQueue<Integer> keysPressed = new ArrayBlockingQueue<Integer>(5);

        @Override
        public Move waitForMove() throws InterruptedException {
            LevelPanel.this.repaint();
            int key = keysPressed.take();
//            LOG.info("key read");
            switch(key){
                case KeyEvent.VK_UP:
                    return Move.Go.NORTH;
                case KeyEvent.VK_LEFT:
                    return Move.Go.WEST;
                case KeyEvent.VK_DOWN:
                    return Move.Go.SOUTH;
                case KeyEvent.VK_RIGHT:
                    return Move.Go.EAST;
            }
            return null;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            try {
//                LOG.info("keyPressed : " + e.getKeyCode());
                keysPressed.offer(e.getKeyCode(), 1, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
