package aff;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

import javax.swing.JPanel;

import jeu.*;

public class ConteneurFenetre extends JPanel{
    Table tab;
    int x = 50;
    int y = 50;
    int u= 50;


    public ConteneurFenetre(Table ta)
    {
        this.setLayout(null);
        this.tab=ta;
        this.propConteneur();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public Table gettab()
    {
        return this.tab;
    }

    public void propConteneur()
    {
        Listener Li = new Listener(this);
        this.addKeyListener(Li);
    }


    public void paint(Graphics g)
    {
        super.paint(g);

        // g.setColor(Color.blue);
        // g.fillRect(50, 50,  tab.getLargeur(),tab.getLongueur());

        // g.setColor(Color.black);
        // g.fillRect(50, tab.getLongueur()/2+50, tab.getLargeur(), 10);

        Toolkit t=Toolkit.getDefaultToolkit();
        Image i=t.getImage("d59db2c36583d8c56982076bba641bb6.png");
        g.drawImage(i, x-20, y, this);

       //joueur
        g.setColor(Color.RED);
        g.fillRect(tab.getJoueur()[0].getPosX()+u, tab.getJoueur()[0].getPosY()+u-20, 80, 20);
        g.setColor(Color.white);
        g.drawString("PLAYER 1", tab.getJoueur()[0].getPosX()+u+5, tab.getJoueur()[0].getPosY()+u-5);

        g.setColor(Color.BLACK);
        g.fillRect(tab.getJoueur()[1].getPosX()+u, tab.getJoueur()[1].getPosY()+u, 80, 20);
        g.setColor(Color.white);
        g.drawString("PLAYER 2", tab.getJoueur()[1].getPosX()+u+5, tab.getJoueur()[1].getPosY()+u+15);

        //balle
        g.setColor(new Color(255,162,0));
        g.fillOval(this.tab.getBalle().getPosX()+u,this.tab.getBalle().getPosY()+u, 20, 20);
        if(this.tab.getBalle().getPosY()==340){
            g.setColor(new Color(255,162,0));
            g.fillOval(this.tab.getBalle().getPosX()+u,this.tab.getBalle().getPosY()+u, 35, 35);
        }
        try
            {
                Thread.sleep(this.tab.getBalle().getVitesse());
            }
        catch (Exception e){ }
        if(this.tab.getTest()=="go"){
            this.tab.getBalle().verif_depl_balle(this.tab);
        }
        this.tab.makaBall();
        this.repaint();

        // SCORE BOARD
        g.setColor(Color.black);
        g.fillRect(600, 50,  255, 150);

        g.setColor(Color.white);
        g.fillRect(605, 100, 245, 3);
        
        g.setColor(Color.white);
        g.fillRect(725, 50, 3, 150);


        g.setColor(Color.white);
        g.setFont(new Font("Verdana",Font.BOLD,20));
        g.drawString("PLAYER 1", 605, 80);
        g.setFont(new Font("Verdana",Font.BOLD,80));
        g.drawString(""+this.tab.getJoueur()[0].getScore(), 635, 180);

        g.setColor(Color.white);
        g.setFont(new Font("Verdana",Font.BOLD,20));
        g.drawString("PLAYER 2", 735, 80);
        g.setFont(new Font("Verdana",Font.BOLD,80));
        g.drawString(""+this.tab.getJoueur()[1].getScore(), 765, 180);

    }

}