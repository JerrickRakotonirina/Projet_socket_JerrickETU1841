package main;

import aff.*;
import jeu.*;

public class Main {
    public static void main(String[] args){
        Joueur[] j = new Joueur[2];
        j[0] = new Joueur(0,0);
        j[1] = new Joueur(0,700);
        Balle balle = new Balle(230,300,-1);
        Table table = new Table(500, 700,balle,j);
        ConteneurFenetre pan = new ConteneurFenetre(table);
        Fenetre fen = new Fenetre(pan);

    }
}