/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package independent.study.pkgfinal.exam;

import dataStructures.HashTable;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Westley
 */
public class MemoryGamePane extends JPanel
{
    //Game:
    private final String[] matches = {"Red", "Blue", "Green", "Orange", "Purple", "White", "Black", "Grey", "Pink", "Teal"};
    private final Color[] matchColors = {new Color(209, 29, 29), new Color(29, 86, 209), new Color(35, 209, 29), new Color(209, 104, 29), new Color(65, 29, 209), new Color(255, 255, 255), new Color(0, 0, 0), new Color(128, 128, 128), new Color(209, 29, 152), new Color(29, 209, 188)};
    private final int rows = 4;
    private final int columns = 5;
    private int points = 10;
    private int selected = -1;
    private int numLeft = 20;
    private MemoryHashTable boardIndexes;
    private FlipBackListener flipper = new FlipBackListener();
    private Timer waitTimer = new Timer(1000, flipper);
    
    
    //Graphics:
    private WGTheme uiTheme;
    private WGLabel title;
    private WGLabel winLabel;
    private WGLabel pointsLabel;
    private WGButton startButton;
    private WGButton backButton;
    private WGButton[] board;
    public MemoryGamePane()
    {
        //Set up the graphical components:
        uiTheme = new WGTheme(6, WGTheme.TEXT_STYLE_MIDDLE, 0, 0.5, 0.5, new Color(50, 98, 140), new Color(19, 52, 82), new Color(237, 202, 97), new Color(19, 52, 82), new Color(204, 225, 232), new Color(124, 150, 196), null, null, new Font("Monospaced", Font.BOLD, 8));
        try
        {
            winLabel = new WGLabel(new Rectangle2D.Double(0.25, 0.4, 0.5, 0.2), "You Win!!!", this, uiTheme);
            pointsLabel = new WGLabel(new Rectangle2D.Double(0.9, 0, 0.1, 0.1), "Points: " + points, this, uiTheme);
            title = new WGLabel(new Rectangle2D.Double(0.375, 0.05, 0.25, 0.1), "Memory Game: Clear the board!", this, uiTheme);
            startButton = new WGButton(new Rectangle2D.Double(0.4, 0.4, 0.2, 0.2), "Start!", this, new StartListener(), uiTheme);
            backButton = new WGButton(new Rectangle2D.Double(0.9, 0.9, 0.1, 0.1), "Back", this, new BackListener(), uiTheme);
        }
        catch(WGNullParentException e) { } //Will never happen, we supply a proper parent
    }
    
    public void setUp()
    {
        selected = -1;
        numLeft = rows * columns;
        points = 10;
        backButton.setShown(true);
        startButton.setShown(true);
        pointsLabel.setShown(false);
        winLabel.setShown(false);
        if(board != null)
        {
            board = null;
        }
    }
    
    private void setUpBoard()
    {
        //Start by filling the board:
        boardIndexes = new MemoryHashTable(rows * columns, HashTable.HASHING_OPTION_LINEAR, 4);
        
        //For each one of the matches, there are two of them, thus we will go through each set, one at a time and shove them into the board, hopefully this creates a cool looking board (Randomlyish)
        for(int i = 0 ; i < matches.length ; i++)
        {
            String key = randomString(4);
            boardIndexes.insert(key, i);
            
            String key2 = randomString(4);
            boardIndexes.insert(key2, i);
        }
        
        //Now create th visual representation of this:
        double spacer = 0.15;
        double x = 0.125;
        double y = 0.225;
        try
        {
            board = new WGButton[rows * columns];
            for(int i = 0 ; i < rows ; i++)
            {
                for(int j = 0 ; j < columns ; j++)
                {
                    board[i * columns + j] = new WGButton(new Rectangle2D.Double(x + (j * spacer), y + (i * spacer), spacer, spacer), "", this, new BoardListener(i * columns + j), uiTheme);
                }
            }
        }
        catch(WGNullParentException e) { } //Will never happen, we supply a proper parent
    }
    
    private String randomString(int length)
    {
        String str = "";
        for(int i = 0 ; i < length ; i++)
        {
            str += Character.toString((int)(Math.random() * 127));
        }
        return str;
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
        g3.draw(startButton);
        g3.draw(backButton);
        g3.draw(winLabel);
        g3.draw(pointsLabel);
        
        if(board != null)
        {
            for(int i = 0 ; i < board.length ; i++)
            {
                g3.draw(board[i]);
            }
        }
    }
    
    private class FlipBackListener implements ActionListener
    {
        private int num = -1;
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            board[num].setText("");
            board[num].setBackgroundColor(uiTheme.getBackgroundColor());

            board[selected].setText("");
            board[selected].setBackgroundColor(uiTheme.getBackgroundColor());
            selected = -1;
            num = -1;
            points--;
            pointsLabel.setText("Points: " + points);
            waitTimer.stop();
        }
        
        public void setNum(int number)
        {
            num = number;
        }

        public int getNum() {
            return num;
        }
    }
    
    private class BoardListener extends WGButtonListener
    {
        int num;
        BoardListener(int number)
        {
            num = number;
        }
        public void clickEvent(MouseEvent e)
        {
            //Can't flip more while the current two are fipped!!!
            if(flipper.getNum() != -1)
            {
                return;
            }
            
            
            if(selected == -1)
            {
                //Just select this one!
                selected = num;
                int type1 = (Integer)boardIndexes.grabValue(num);
                board[num].setText(matches[type1]);
                board[num].setBackgroundColor(matchColors[type1]);
            }
            else
            {
                if(num == selected)
                {
                    selected = -1;
                    board[num].setText("");
                    board[num].setBackgroundColor(uiTheme.getBackgroundColor());
                    return;
                }
                
                //There is already another one selected! Let's find out if they are the same!
                int type1 = (Integer)boardIndexes.grabValue(num);
                int type2 = (Integer)boardIndexes.grabValue(selected);
                
                board[num].setText(matches[type1]);
                board[num].setBackgroundColor(matchColors[type1]);
                
                if(type1 == type2)
                {
                    //ToDO
                    board[num].setShown(false);
                    board[selected].setShown(false);
                    selected = -1;
                    numLeft -= 2;
                    points += 2;
                    pointsLabel.setText("Points: " + points);
                }
                else if(flipper.getNum() == -1)
                {
                    //Wait a second... and then hide their unfortune:
                    flipper.setNum(num);
                    waitTimer.start();
                }
            }
            
            
            //Check if the player won:
            if(numLeft == 0)
            {
                winLabel.setShown(true);
            }
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
            pointsLabel.setShown(true);
            setUpBoard();
        }
    }
}
