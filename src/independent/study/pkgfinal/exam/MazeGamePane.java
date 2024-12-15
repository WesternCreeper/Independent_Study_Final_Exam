/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package independent.study.pkgfinal.exam;

import graphicsUtilities.WGButton;
import graphicsUtilities.WGButtonListener;
import graphicsUtilities.WGLabel;
import graphicsUtilities.WGNullParentException;
import graphicsUtilities.WGTheme;
import graphicsUtilities.WestGraphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author Westley
 */
public class MazeGamePane extends JPanel 
{
    //Game:
    private MazeBinaryTree maze = new MazeBinaryTree();
    private int points = -1;
    
    //Graphics:
    private Color[] backgroundColors = {new Color(50, 98, 140), new Color(85, 113, 138), new Color(111, 112, 112)};
    private WGTheme uiTheme;
    private WGButton leftOptionButton;
    private WGButton rightOptionButton;
    private WGButton startButton;
    private WGButton backButton;
    private WGLabel winLabel;
    private WGLabel pointsLabel;
    public MazeGamePane()
    {
        //Set up the graphical components:
        uiTheme = new WGTheme(6, WGTheme.TEXT_STYLE_MIDDLE, 0, 0.5, 0.5, new Color(50, 98, 140), new Color(19, 52, 82), new Color(237, 202, 97), new Color(19, 52, 82), new Color(204, 225, 232), new Color(124, 150, 196), null, null, new Font("Monospaced", Font.BOLD, 8));
        try
        {
            winLabel = new WGLabel(new Rectangle2D.Double(0.25, 0.4, 0.5, 0.2), "You Win!!!", this, uiTheme);
            pointsLabel = new WGLabel(new Rectangle2D.Double(0.9, 0, 0.1, 0.1), "Points: " + points, this, uiTheme);
            leftOptionButton = new WGButton(new Rectangle2D.Double(0.2, 0.1, 0.25, 0.8), "", this, new LeftOptionListener(), uiTheme);
            rightOptionButton = new WGButton(new Rectangle2D.Double(0.55, 0.1, 0.25, 0.8), "", this, new RightOptionListener(), uiTheme);
            startButton = new WGButton(new Rectangle2D.Double(0.4, 0.4, 0.2, 0.2), "Start!", this, new StartListener(), uiTheme);
            backButton = new WGButton(new Rectangle2D.Double(0.9, 0.9, 0.1, 0.1), "Back", this, new BackListener(), uiTheme);
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
        g3.draw(leftOptionButton);
        g3.draw(rightOptionButton);
        g3.draw(startButton);
        g3.draw(backButton);
        g3.draw(winLabel);
        g3.draw(pointsLabel);
    }
    
    public void setUp()
    {
        points = -1;
        backButton.setShown(true);
        startButton.setShown(true);
        leftOptionButton.setShown(false);
        rightOptionButton.setShown(false);
        pointsLabel.setShown(false);
        winLabel.setShown(false);
    }
    
    private void setNames()
    {
        leftOptionButton.setText(maze.getLeftName());
        leftOptionButton.setBackgroundColor(backgroundColors[maze.getLeftColor()]);
        rightOptionButton.setText(maze.getRightName());
        rightOptionButton.setBackgroundColor(backgroundColors[maze.getRightColor()]);
        
        //Update points:
        points++;
        pointsLabel.setText("Points: " + points);
    }
    
    private class LeftOptionListener extends WGButtonListener
    {
        public void clickEvent(MouseEvent e)
        {
            if(maze.canGoLeft())
            {
                maze.front();
            }
            else
            {
                maze.advanceLeft();
            }
            if(((String)maze.getCurrent()).equals("Exit"))
            {
                startButton.setShown(false);
                leftOptionButton.setShown(false);
                rightOptionButton.setShown(false);
                winLabel.setShown(true);
            }
            setNames();
        }
    }
    
    private class RightOptionListener extends WGButtonListener
    {
        public void clickEvent(MouseEvent e)
        {
            if(maze.canGoRight())
            {
                maze.front();
            }
            else
            {
                maze.advanceRight();
            }
            if(((String)maze.getCurrent()).equals("Exit"))
            {
                startButton.setShown(false);
                leftOptionButton.setShown(false);
                rightOptionButton.setShown(false);
                winLabel.setShown(true);
            }
            setNames();
        }
    }
    
    private class BackListener extends WGButtonListener
    {
        public void clickEvent(MouseEvent e)
        {
            IndependentStudyFinalExam.goTo("Menu");
        }
    }
    
    private class StartListener extends WGButtonListener
    {
        public void clickEvent(MouseEvent e)
        {
            startButton.setShown(false);
            leftOptionButton.setShown(true);
            rightOptionButton.setShown(true);
            pointsLabel.setShown(true);
            
            //Start generating:
            maze.generateMap();
            
            //Now name the options:
            setNames();
        }
    }
}
