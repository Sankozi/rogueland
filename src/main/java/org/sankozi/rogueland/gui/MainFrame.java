package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import javax.swing.JMenuBar;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;

/**
 *
 * @author sankozi
 */
public class MainFrame extends FrameView{

   @Inject
   public MainFrame(Application app, 
           @Named("main-menu") JMenuBar menu){
       super(app);
       this.setMenuBar(menu);
       this.setComponent(new LevelPanel());
   }
}
