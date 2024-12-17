/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package independent.study.pkgfinal.exam;

/**
 *
 * @author Westley
 */
public class Card 
{
    private int number;
    private int suit;
    public Card(int num, int suit)
    {
        number = num;
        this.suit = suit;
    }

    public int getNumber() {
        return number;
    }

    public int getSuit() {
        return suit;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }
}
