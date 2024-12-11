/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package independent.study.pkgfinal.exam;

import graphicsUtilities.WGLabel;
import graphicsUtilities.WGNullParentException;
import graphicsUtilities.WGTheme;
import graphicsUtilities.WestGraphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author Westley
 */
public class MemoryGamePane extends JPanel
{
    //Graphics:
    private WGTheme uiTheme;
    private WGLabel title;
    public MemoryGamePane()
    {
        //Set up the graphical components:
        uiTheme = new WGTheme(6, WGTheme.TEXT_STYLE_MIDDLE, 0, 0.5, 0.5, new Color(50, 98, 140), new Color(19, 52, 82), new Color(237, 202, 97), new Color(19, 52, 82), new Color(204, 225, 232), new Color(124, 150, 196), null, null, new Font("Monospaced", Font.BOLD, 8));
        try
        {
            title = new WGLabel(new Rectangle2D.Double(0.25, 0.05, 0.5, 0.2), "WIP: Memory Game", this, uiTheme);
        }
        catch(WGNullParentException e) { } //Will never happen, we supply a proper parent
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        WestGraphics g3 = new WestGraphics(g2);
        
        //Make a simple background:
        g2.setColor(new Color(81, 141, 194));
        g2.fillRect(0, 0, getSize().width, getSize().height);
        
        //Now draw the components on the screen:
        g3.draw(title);
    }
    
}
