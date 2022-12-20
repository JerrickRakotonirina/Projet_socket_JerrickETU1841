package player;

public class Balle extends Thread{
    int PosX;
    int PosY;
    int sens;
    String move="center";
    int longueur = 700;
    int largeur = 500;
    int vitesse=50;
    String serv;
    boolean balmove=true;

    public Balle(int x,int y,int s)
    {
        this.setPosX(x);
        this.setPosY(y);
        this.setsens(s);
    }
    public void setBalmove(boolean balmove) {
        this.balmove = balmove;
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
    @Override
	public void run() {
		// TODO Auto-generated method stub
		while(balmove==true){
		    verif_depl_balle();
		try {
			sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		
	}

    public void verif_depl_balle(){
        if (this.getPosY()+40 >= this.longueur+75)
        {
            this.setsens(-1); 
        }
        
        if (this.getPosY() <= 70)
        {
            this.setsens(1);
        }
        this.deplacement_balle(this.getsens());

        if (this.getPosX()<25) {
            this.setMove("right");
        }
        if (this.getPosX()>460) {
            this.setMove("left");
        }
    }

    public void deplacement_balle(int sens)
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