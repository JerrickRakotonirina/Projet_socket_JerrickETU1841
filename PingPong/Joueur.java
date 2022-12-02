package jeu;

import javax.swing.text.TabExpander;

public class Joueur
{
    int PositionX;
    int PositionY;
    int score=0;
    static int longueur = 700;
    static int largeur = 500;


    public Joueur(int x,int y)
    {
        this.setPosX(x);
        this.setPosY(y);
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getPosX()
    {
        return this.PositionX;
    }

    public void setPosX(int x)
    {
        this.PositionX=x;
    }

    public int getPosY()
    {
        return this.PositionY;
    }

    public void setPosY(int y)
    {
        this.PositionY=y;
    }

    public void move_left()
    {
        int x = this.getPosX();
        x-=10;
        if ((x >= 0) && (x+80 <= largeur))
        {
            this.setPosX(x);
        }
    }

    public void move_right()
    {
        int x = this.getPosX();
        x+=10;
        if ((x >= 0) && (x+80 <= largeur ))
        {
            this.setPosX(x);
        }
    }
}