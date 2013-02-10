package org.sankozi.rogueland.gui.render;

import javax.swing.JTextPane;

import org.sankozi.rogueland.gui.DescriptionTextArea;
import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.Destroyable;
import org.sankozi.rogueland.model.Player;

/**
 *
 * @author sankozi
 */
public final class TextAreaPlayerRenderer extends TextAreaRenderer<Player> {
    JTextPane playerStats = new DescriptionTextArea();

    protected final String getHtml(Player player){
        StringBuilder sb = new StringBuilder(128);
        sb.append("<html><font size='6'>Player stats</font>");

        sb.append("<table><tr><td><font size='5'>Main stats</font></td>"
                + "<td><font size='5'>?</font></td>"
                + "<td><font size='5'>Attack</font></td></tr>"
                + "<tr valign='top'><td><table>");
        for(Player.Param param : Player.Param.values()){
            sb.append("<tr><td>").append(param.toString()).append("</td><td>")
              .append(player.playerParam(param)).append("</td></tr>");
        }
        sb.append("</table></td><td><table>");
        for(Destroyable.Param param : Destroyable.Param.values()){
            sb.append("<tr><td>").append(param.toString()).append("</td><td>")
              .append(player.destroyableParam(param)).append("</td></tr>");
        }
        for(Actor.Param param : Actor.Param.values()){
            sb.append("<tr><td>").append(param.toString()).append("</td><td>")
                    .append(player.actorParam(param)).append("</td></tr>");
        }
        sb.append("</table></td><td><table>");
        sb.append("<tr><td colspan='2'>Weapon</td></tr>");
        Utils.renderEffect(player.getWeaponEffect(), sb);
        sb.append("<tr><td colspan='2'>Hand to hand combat</td></tr>");
        Utils.renderEffect(player.getBumpEffect(), sb);
        sb.append("</table></td></tr></table>");
        sb.append("</html>");
        return sb.toString();
    }
}
