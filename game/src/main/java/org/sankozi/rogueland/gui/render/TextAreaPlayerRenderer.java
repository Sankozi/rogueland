package org.sankozi.rogueland.gui.render;

import javax.swing.JTextPane;

import org.sankozi.rogueland.gui.DescriptionTextArea;
import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.Destroyable;
import org.sankozi.rogueland.model.Player;
import org.sankozi.rogueland.model.WeaponAttack;
import org.sankozi.rogueland.model.coords.Direction;
import org.sankozi.rogueland.resources.ResourceProvider;

import static org.sankozi.rogueland.resources.ResourceProvider.*;

import static org.sankozi.rogueland.util.Formatters.*;

/**
 *
 * @author sankozi
 */
public final class TextAreaPlayerRenderer extends TextAreaRenderer<Player> {
    JTextPane playerStats = new DescriptionTextArea();

    protected final String getHtml(Player player){
        StringBuilder sb = new StringBuilder(128);
        sb.append("<html><font size='6'>Player</font>");

        sb.append("<table><tr><td><font size='5'>Stats</font></td>"
                + "<td><font size='5'>Defense</font></td>"
                + "<td><font size='5'>Attack</font></td></tr>"
                + "<tr valign='top'><td><table>");
        for(Player.Param param : Player.Param.values()){
            sb.append("<tr><td>").append(param.getLabel()).append("</td><td>")
              .append(formatParam(player.playerParam(param))).append("</td></tr>");
        }
        sb.append("</table></td><td><table>");
        for(Destroyable.Param param : Destroyable.Param.values()){
            sb.append("<tr><td>").append(param.getLabel()).append("</td><td>")
              .append(formatParam(player.destroyableParam(param))).append("</td></tr>");
        }
        for(Actor.Param param : Actor.Param.visibleParams()){
            sb.append("<tr><td>").append(param.getLabel()).append("</td><td>")
                    .append(formatParam(player.actorParam(param))).append("</td></tr>");
        }
        sb.append("</table></td><td><table>");
        sb.append("<tr><td colspan='2'>Weapon</td></tr>");
        Utils.renderEffects(player.getWeaponEffects(WeaponAttack.get(Direction.N, WeaponAttack.WeaponMove.THRUST)), sb);
        sb.append("<tr><td colspan='2'>Hand to hand combat</td></tr>");
        Utils.renderEffect(player.getBumpEffect(), sb);
        sb.append("</table></td></tr></table>");
        sb.append("<img src='").append(ResourceProvider.getImageUrl("icons/protection.gif").toString()).append("'/>");
        sb.append("</html>");
        return sb.toString();
    }
}
