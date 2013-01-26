package org.sankozi.rogueland.gui;

import javax.swing.JComponent;
import javax.swing.JTextField;
import org.sankozi.rogueland.model.Player;

/**
 *
 * @author sankozi
 */
public final class PlayerStatsRenderer {

    public JComponent renderPlayerStats(Player player){
        return new JTextField("Statystyki : ");
    }
}
