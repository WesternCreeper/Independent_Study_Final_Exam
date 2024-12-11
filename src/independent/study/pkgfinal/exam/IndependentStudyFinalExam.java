/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package independent.study.pkgfinal.exam;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Westley
 */
public class IndependentStudyFinalExam 
{
    private static final int MIN_WINDOW_WIDTH = 800;
    private static final int MIN_WINDOW_HEIGHT = 600;
    private static JFrame mainFrame = new JFrame();
    private static JPanel paneOwner;
    private static CardLayout paneHolder = new CardLayout();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        //Set up the frame in the standard fashion:
        SwingUtilities.invokeLater(new Initializer());
    }
    
    /**
     * A simple function to change the current screen to the screen inputted
     * @param screen The screen the program wants to go to
     */
    public static void goTo(String screen)
    {
        paneHolder.show(paneOwner, screen);
    }
    
    private static class Initializer implements Runnable
    {
        public Initializer(){}
        public final void run()
        {
            try
            {
                //Set up the frame
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                mainFrame.setSize(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
                mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                mainFrame.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT));
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setTitle("CS IS: Data Structures Final Exam");
                
                
                //Set up the different panes
                paneOwner = new JPanel(paneHolder);
                
                //Loading screen:
                mainFrame.add(paneOwner);
                
                //The main menu pane:
                MainPane menu = new MainPane();
                paneOwner.add(menu, "Menu");
                
                //The main menu pane:
                CardGamePane card = new CardGamePane();
                paneOwner.add(card, "Card Game");
                
                //The main menu pane:
                MazeGamePane maze = new MazeGamePane();
                paneOwner.add(maze, "Simple Maze Game");
                
                //The main menu pane:
                MemoryGamePane memory = new MemoryGamePane();
                paneOwner.add(memory, "Memory Game");
                
                paneHolder.show(paneOwner, "Menu");
                mainFrame.setVisible(true);
            }
            catch(Exception e)
            {
                System.exit(1);
            }
        }
    }
}
