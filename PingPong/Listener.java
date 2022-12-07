package aff;

import java.awt.event.*;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.event.*;

import aff.*;
import jeu.*;

public class Listener implements KeyListener
{
    ConteneurFenetre aff;

    public Listener(ConteneurFenetre pan)
    {
        this.aff=pan;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(this.aff.tab.getBalle().getsens()==1){
                this.aff.gettab().getBalle().setMove("left");
            }else{
                this.aff.gettab().getJoueur()[0].move_left();
                System.out.println("left");
            }
            this.aff.repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(this.aff.tab.getBalle().getsens()==1){
                this.aff.gettab().getBalle().setMove("right");
            }else{
            this.aff.gettab().getJoueur()[0].move_right();
            System.out.println("right");
            }
            this.aff.repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if(this.aff.tab.getBalle().getsens()==1){
                this.aff.gettab().getBalle().setMove("center");
                this.aff.tab.setTest("go");
                System.out.println("go");
                this.aff.repaint();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_Q)
        {
            if(this.aff.tab.getBalle().getsens()==-1){
                this.aff.gettab().getBalle().setMove("left");
            }else{
                this.aff.gettab().getJoueur()[1].move_left();
                System.out.println("left");
            }
            this.aff.repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_D)
        {
            if(this.aff.tab.getBalle().getsens()==-1){
            this.aff.gettab().getBalle().setMove("right");
            }else{
                this.aff.gettab().getJoueur()[1].move_right();
                System.out.println("right");
            }
            this.aff.repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_Z)
        {
            if(this.aff.tab.getBalle().getsens()==-1){
                this.aff.gettab().getBalle().setMove("center");
                this.aff.tab.setTest("go");
                System.out.println("go");
                this.aff.repaint();
            }
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

}