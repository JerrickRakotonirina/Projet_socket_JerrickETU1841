package jeu;

import java.lang.annotation.Retention;

import jeu.*;

public class Table{
    int largeur;
    int longueur;
    Balle balle;
    Joueur[] Joueur;
    // int indice;
    String test;

    public Table(int l,int lo,Balle b,Joueur[] j)
    {
        this.setLargeur(l);
        this.setLongueur(lo);
        this.setBalle(b);
        this.setJoueur(j);
        // this.verif_ind(this.getBalle().getPosY());
    }

    public String getTest() {
        return test;
    }
    public void setTest(String test) {
        this.test = test;
    }

    public int getLargeur()
    {
        return this.largeur;
    }

    public void setLargeur(int l)
    {
        this.largeur=l;
    }

    public int getLongueur()
    {
        return this.longueur;
    }

    public void setLongueur(int lo)
    {
        this.longueur=lo;
    }

    public Balle getBalle()
    {
        return this.balle;
    }

    public void setBalle(Balle b)
    {
        this.balle= b;
    }

    public Joueur[] getJoueur()
    {
        return this.Joueur;
    }

    public void setJoueur(Joueur[] j)
    {
        this.Joueur=j;
    }

    // public int getindice()
    // {
    //     return this.indice;
    // }

    // public void setindice(int i)
    // {
    //     this.indice=i;
    // }

    // public void verif_ind(int y)
    // {
    //     if (y == 0 )
    //     {
    //         this.setindice(1);
    //     }
    //     else
    //     {
    //         this.setindice(0);
    //     }
    // }

    // public void changer_ind()
    // {
    //     if (this.getindice() == 0)
    //     {
    //         this.setindice(1);
    //     }
    //     else 
    //     {
    //         this.setindice(0);
    //     }
    // }
    public void makaBall(){
        if(this.getBalle().getsens()==1 && this.getBalle().getPosY()<=10){
            if(this.getBalle().getServ()=="maty"){
                this.getBalle().setPosY(this.longueur-30);
                this.getBalle().setsens(this.getBalle().getsens()*-1);
                this.getBalle().setPosX(this.getJoueur()[1].getPosX()+20);
                this.getBalle().setServ("tsy maty");
            }else{
            this.getBalle().setPosX(this.getJoueur()[0].getPosX()+20);
            }
        }
        if(this.getBalle().getsens()==-1 && this.getBalle().getPosY()>=this.longueur-30){
            if(this.getBalle().getServ()=="maty"){
                this.getBalle().setPosY(10);
                this.getBalle().setsens(this.getBalle().getsens()*-1);
                this.getBalle().setPosX(this.getJoueur()[0].getPosX()+20);
                this.getBalle().setServ("tsy maty");
            }else{
            this.getBalle().setPosX(this.getJoueur()[1].getPosX()+20);
            }
        }
        if(this.getBalle().getPosX()<=0){
            this.getBalle().setMove("right");
        }
        if(this.getBalle().getPosX()>=largeur-20){
            this.getBalle().setMove("left");
        }
    }
}