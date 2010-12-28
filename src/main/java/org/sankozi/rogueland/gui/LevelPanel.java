package org.sankozi.rogueland.gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.swing.JComponent;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.control.Game;
import org.sankozi.rogueland.model.Controls;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Move;

/**
 *
 * @author sankozi
 */
public class LevelPanel extends JComponent{
    private final static Logger LOG = Logger.getLogger(LevelPanel.class);

    Game game = new Game();
    TilePainter tilePainter = new FontPainter();
    Rectangle levelSize = new Rectangle(0, 0, Level.WIDTH, Level.HEIGHT);

    GuiControls gc = new GuiControls();

    {
        this.setFocusable(true);
        this.addKeyListener(gc);
        //TODO usunąć
        game.setControls(gc);
    }

    public KeyListener getKeyListener(){
        return gc;
    }

    @Override
    public void paint(Graphics g) {
        tilePainter.paint(levelSize, game.getLevel().getTiles(), g);
    }

    private class GuiControls implements Controls, KeyListener {

        BlockingQueue<Integer> keysPressed = new ArrayBlockingQueue<Integer>(5);

        @Override
        public Move waitForMove() {
            int key = keysPressed.poll();
            switch(key){
                case KeyEvent.VK_UP:
                    return Move.Go.NORTH;
                case KeyEvent.VK_LEFT:
                    return Move.Go.EAST;
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
                LOG.info("keyPressed : " + e.getKeyCode());
                LevelPanel.this.repaint();
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
