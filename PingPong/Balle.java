package jeu;

import jeu.*;

public class Balle{
    int PosX;
    int PosY;
    int sens;
    String move;
    static int longueur = 700;
    static int largeur = 500;
    int vitesse=50;
    String serv;

    public Balle(int x,int y,int s)
    {
        this.setPosX(x);
        this.setPosY(y);
        this.setsens(s);
    }
    public String getServ() {
        return serv;
    }
    public void setServ(String serv) {
        this.serv = serv;
    }
    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }
    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public int getPosX()
    {
        return this.PosX;
    }

    public void setPosX(int x)
    {
        this.PosX=x;
    }

    public int getPosY()
    {
        return this.PosY;
    }

    public void setPosY(int y)
    {
        this.PosY=y;
    }

    public int getsens()
    {
        return this.sens;
    }

    public void setsens(int s)
    {
        this.sens=s;
    }

    public void changer_sens()
    {
        if (this.getsens() == 1)
        {
            this.setsens(-1);
        }
        else
        {
            this.setsens(1);
        }
    }

    public void verif_depl_balle(Table t){
        if (this.getPosY()+20 >= t.getLongueur())
        {
            int val=t.getJoueur()[0].getScore();
            if(this.getPosX()<t.getJoueur()[1].PositionX | this.getPosX()>t.getJoueur()[1].PositionX+80){
                val+=1;
                this.setServ("maty");
            }
            t.getJoueur()[0].setScore(val);
            t.setTest("tsy go");
            this.setsens(-1); 
            this.setVitesse(this.vitesse-1);
        }
        
        if (this.getPosY() <= 0)
        {
            int val=t.getJoueur()[1].getScore();
            if(this.getPosX()<t.getJoueur()[0].PositionX | this.getPosX()>t.getJoueur()[0].PositionX+80){
                val+=1;
                this.setServ("maty");
            }
            t.getJoueur()[1].setScore(val);
            t.setTest("tsy go");
            this.setsens(1);
            this.setVitesse(this.vitesse-1);
        }
        this.deplacement_balle(this.getsens(), t);
    }

    public void deplacement_balle(int sens, Table t)
    {
        int x = this.getPosX();
        int y = this.getPosY();
        if (sens == -1)
        {
            if(this.move=="left"){
                y-=10;
                x-=5;
            }else if(this.move=="center"){
                y-=10;
            }else if(this.move=="right"){
                y-=10;
                x+=5;
            }
        }
        if (sens == 1)
        {
            // x+=5;
            // y+=10;
            if(this.move=="left"){
                y+=10;
                x-=5;
            }else if(this.move=="center"){
                y+=10;
            }else if(this.move=="right"){
                y+=10;
                x+=5;
            }
        }
        this.setPosX(x);
        this.setPosY(y);
    }
}