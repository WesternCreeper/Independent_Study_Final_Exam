/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package independent.study.pkgfinal.exam;

import dataStructures.HashTable;
import dataStructures.Queue;
import dataStructures.Stack;
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
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Westley
 */
public class CardGamePane extends JPanel
{
    //Game:
    private final String yourCardLabel = "Your Cards Left: ";
    private final String opponentCardLabel = "Opponent Cards Left: ";
    private final int cardNum = 9;
    private final int suitNum = 4;
    private int reshuffleIntervalMax = 13;
    private int handSize = 0;
    private int opponentHandSize = 0;
    private int reshuffleInterval = reshuffleIntervalMax;
    private MemoryHashTable deckRandomizer;
    private Stack<Card> deck = new Stack<Card>();
    private Queue<Card> hand = new Queue<Card>();
    private Queue<Card> opponentHand = new Queue<Card>();
    private Card yourDisplayCard;
    private Card opponentDisplayCard;
    private ReturnListener cardReturner = new ReturnListener();
    private Timer waitTimer = new Timer(1500, cardReturner);
    
    //UI Graphics:
    private final String imageFileName = "Images/Cards.png";
    private final int imageXSize = 409;
    private final int imageYSize = 583;
    private WGTheme uiTheme;
    private WGLabel title;
    private WGButton startButton;
    private WGButton backButton;
    private BufferedImage[][] allCardImages;
    
    //In-game Graphics
    private WGLabel winLabel;
    private WGLabel loseLabel;
    private WGLabel opponentHandAmountLabel;
    private WGLabel handAmountLabel;
    private WGButton playNextButton;
    private WGButton yourCardImageDisplay;
    private WGButton opponentCardImageDisplay;
    
    public CardGamePane()
    {
        //Set up the graphical components:
        uiTheme = new WGTheme(6, WGTheme.TEXT_STYLE_MIDDLE, 0, 0.5, 0.5, new Color(50, 98, 140), new Color(19, 52, 82), new Color(237, 202, 97), new Color(19, 52, 82), new Color(204, 225, 232), new Color(124, 150, 196), null, null, new Font("Monospaced", Font.BOLD, 8));
        try
        {
            winLabel = new WGLabel(new Rectangle2D.Double(0.25, 0.4, 0.5, 0.2), "You Win!!!", this, uiTheme);
            loseLabel = new WGLabel(new Rectangle2D.Double(0.25, 0.4, 0.5, 0.2), "You Lose...", this, uiTheme);
            title = new WGLabel(new Rectangle2D.Double(0.25, 0.05, 0.5, 0.2), "Simple Card Game", this, uiTheme);
            opponentHandAmountLabel = new WGLabel(new Rectangle2D.Double(0.8, 0.1, 0.2, 0.1), opponentCardLabel, this, uiTheme);
            handAmountLabel = new WGLabel(new Rectangle2D.Double(0.8, 0.7, 0.2, 0.1), yourCardLabel, this, uiTheme);
            startButton = new WGButton(new Rectangle2D.Double(0.4, 0.4, 0.2, 0.2), "Start!", this, new StartListener(), uiTheme);
            backButton = new WGButton(new Rectangle2D.Double(0.9, 0.9, 0.1, 0.1), "Back", this, new BackListener(), uiTheme);
            playNextButton = new WGButton(new Rectangle2D.Double(0.45, 0.9, 0.1, 0.1), "Play Next", this, new PlayNextListener(), uiTheme);
            opponentCardImageDisplay = new WGButton(new Rectangle2D.Double(0.4, 0.1, 0.2, 0.4), null, WGButton.IMAGE_CENTER_PLACEMENT, WGButton.IMAGE_SCALE_TO_FIT, this, null, uiTheme);
            yourCardImageDisplay = new WGButton(new Rectangle2D.Double(0.4, 0.5, 0.2, 0.4), null, WGButton.IMAGE_CENTER_PLACEMENT, WGButton.IMAGE_SCALE_TO_FIT, this, null, uiTheme);
        }
        catch(WGNullParentException e) { } //Will never happen, we supply a proper parent
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void setUp()
    {
        backButton.setShown(true);
        startButton.setShown(true);
        handAmountLabel.setShown(false);
        opponentHandAmountLabel.setShown(false);
        playNextButton.setShown(false);
        yourCardImageDisplay.setShown(false);
        opponentCardImageDisplay.setShown(false);
        winLabel.setShown(false);
        loseLabel.setShown(false);
        
        
        //Grab the images:
        try
        {
            File imageFile = new File(imageFileName);
            BufferedImage allCardsImage = ImageIO.read(imageFile);
            allCardImages = new BufferedImage[suitNum][cardNum];
            for(int i = 0 ; i < suitNum ; i++)
            {
                for(int j = 0 ; j < cardNum ; j++)
                {
                    allCardImages[i][j] = allCardsImage.getSubimage(j * imageXSize, i * imageYSize, imageXSize, imageYSize);
                }
            }
        }
        catch(Exception e)
        {
            System.exit(1);
        }
    }
    
    private void setUpHands()
    {
        //Throw the cards into the hash table in order to randomize their starting positiion, but not lose any individual card:
        deck = new Stack<Card>();
        hand = new Queue<Card>();
        opponentHand = new Queue<Card>();
        handSize = 0;
        opponentHandSize = 0;
        deckRandomizer = new MemoryHashTable(cardNum * suitNum, HashTable.HASHING_OPTION_LINEAR, 4);
        for(int i = 0 ; i < suitNum ; i++)
        {
            for(int j = 0 ; j < cardNum ; j++)
            {
                String key = randomString(4);
                deckRandomizer.insert(key, new Card(j, i));
            }
        }
        
        //Now add all of these to the deck:
        int iEnd = suitNum * cardNum;
        for(int i = 0 ; i < iEnd ; i++)
        {
            deck.push((Card)deckRandomizer.grabValue(i));
        }
        
        //And then split the cards evenly between the two players:
        for(int i = 0 ; i < iEnd/2 ; i++)
        {
            hand.enqueue(deck.popTop());
            opponentHand.enqueue(deck.popTop());
            handSize++;
            opponentHandSize++;
        }
        
        handAmountLabel.setText(yourCardLabel + handSize);
        opponentHandAmountLabel.setText(opponentCardLabel + opponentHandSize);
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
        g3.draw(backButton);
        g3.draw(startButton);
        g3.draw(handAmountLabel);
        g3.draw(opponentHandAmountLabel);
        g3.draw(playNextButton);
        g3.draw(yourCardImageDisplay);
        g3.draw(opponentCardImageDisplay);
        g3.draw(winLabel);
        g3.draw(loseLabel);
    }
    
    private class BackListener extends WGButtonListener
    {
        public void clickEvent(MouseEvent e)
        {
            IndependentStudyFinalExam.goTo("Menu");
        }
    }
    
    private class PlayNextListener extends WGButtonListener
    {
        public void clickEvent(MouseEvent e)
        {
            if(waitTimer.isRunning()) //Cannot allow the user to just power through this! You gotta wait for the "animation!"
            {
                return;
            }
            yourCardImageDisplay.setShown(true);
            opponentCardImageDisplay.setShown(true);
            yourDisplayCard = hand.dequeue();
            opponentDisplayCard = opponentHand.dequeue();
            yourCardImageDisplay.setDisplayedImage(allCardImages[yourDisplayCard.getSuit()][yourDisplayCard.getNumber()]);
            opponentCardImageDisplay.setDisplayedImage(allCardImages[opponentDisplayCard.getSuit()][opponentDisplayCard.getNumber()]);
            handSize--;
            opponentHandSize--;
        
            handAmountLabel.setText(yourCardLabel + handSize);
            opponentHandAmountLabel.setText(opponentCardLabel + opponentHandSize);
            
            //Okay so now we need to make the cards go away after a second:
            waitTimer.start();
        }
    }
    
    private class StartListener extends WGButtonListener
    {
        public void clickEvent(MouseEvent e)
        {
            startButton.setShown(false);
            title.setShown(false);
            handAmountLabel.setShown(true);
            opponentHandAmountLabel.setShown(true);
            playNextButton.setShown(true);
            setUpHands();
            
            //Now begin the game. Since it only lasts one round, gained cards go to the end of a person's hand.
        }
    }
    
    private class ReturnListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            //We now evalute the hands and determine which player gets what:
            /*
            * If it is a tie, then niether player gets it. 
            * If one of the players wins, then they get the both cards
            */
            if(yourDisplayCard.getNumber() > opponentDisplayCard.getNumber())
            {
                handSize+=2;
                hand.enqueue(yourDisplayCard);
                hand.enqueue(opponentDisplayCard);
            }
            else if(yourDisplayCard.getNumber() < opponentDisplayCard.getNumber())
            {
                opponentHandSize+=2;
                opponentHand.enqueue(yourDisplayCard);
                opponentHand.enqueue(opponentDisplayCard);
            }
            //No need for the else here, as setting the cards to null is the same as getting rid of them
            
            yourDisplayCard = null;
            opponentDisplayCard = null;
            waitTimer.stop();
            
            //Reset the UI here:
            handAmountLabel.setText(yourCardLabel + handSize);
            opponentHandAmountLabel.setText(opponentCardLabel + opponentHandSize);
            yourCardImageDisplay.setShown(false, false);
            opponentCardImageDisplay.setShown(false, false);
            
            //Check the win/lose logic here
            if(handSize <= 3)
            {
                handAmountLabel.setShown(false);
                opponentHandAmountLabel.setShown(false);
                playNextButton.setShown(false);
                yourCardImageDisplay.setShown(false);
                opponentCardImageDisplay.setShown(false);
                loseLabel.setShown(true);
            }
            else if(opponentHandSize <= 3)
            {
                handAmountLabel.setShown(false);
                opponentHandAmountLabel.setShown(false);
                playNextButton.setShown(false);
                yourCardImageDisplay.setShown(false);
                opponentCardImageDisplay.setShown(false);
                winLabel.setShown(true);
            }
            
            reshuffleInterval--;
            if(reshuffleInterval <= 0)
            {
                reshuffleInterval = reshuffleIntervalMax;
                //Now comence the reshuffling:
                //Start with our hand:
                deckRandomizer = new MemoryHashTable(handSize, HashTable.HASHING_OPTION_LINEAR, 4);
                for(int i = 0 ; i < handSize ; i++)
                {
                    String key = randomString(4);
                    deckRandomizer.insert(key, hand.dequeue());
                }
                for(int i = 0 ; i < handSize ; i++)
                {
                    hand.enqueue((Card)deckRandomizer.grabValue(i));
                }
                //Now do theirs:
                deckRandomizer = new MemoryHashTable(opponentHandSize, HashTable.HASHING_OPTION_LINEAR, 4);
                for(int i = 0 ; i < opponentHandSize ; i++)
                {
                    String key = randomString(4);
                    deckRandomizer.insert(key, opponentHand.dequeue());
                }
                for(int i = 0 ; i < opponentHandSize ; i++)
                {
                    opponentHand.enqueue((Card)deckRandomizer.grabValue(i));
                }
            }
        }
    }
}
