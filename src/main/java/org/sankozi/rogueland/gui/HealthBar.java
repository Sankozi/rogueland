package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Destroyable.Param;
import org.sankozi.rogueland.model.Player;

/**
 * Component for repesenting health bar
 * @author sankozi
 */
public class HealthBar extends JComponent implements GameListener{
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(HealthBar.class);

	float redPart = 0f;
	
    {
        setPreferredSize(new Dimension(100, 20));
    }

	@Inject
	void setGameSupport(GameSupport gs){
//		LOG.info("injected");
		gs.addListener(this);
	}

    @Override
    public void paint(Graphics g) {
		g.setColor(Color.GREEN);
        g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
		g.setColor(Color.RED);
        g.fillRect(0, 0, (int)(g.getClipBounds().width * redPart), g.getClipBounds().height);
    }

	@Override
	public void onEvent(GameEvent event) {
		Player player = event.game.getPlayer();
		redPart = 1f - ((float) player.getDurability() / (float) player.destroyableParam(Param.MAX_HEALTH));
//		LOG.info("redPart = " + redPart);
		this.repaint();
	}
}
