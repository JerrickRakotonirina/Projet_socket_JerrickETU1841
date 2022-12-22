package pong;

import pong.*;
import player.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;


public class PongServer extends JFrame implements KeyListener, Runnable, WindowListener{

	private static final long serialVersionUID = 1L;
	
	private static final String TITLE  = "ping-pong::server";	
	private static final int    WIDTH  = 920;		  
	private static final int    HEIGHT = 840;		  
	
	boolean isRunning = false;
	boolean check = true;
	boolean initgame = false;
	
	Balle movingBALL;
	private PlayerServer playerS;
	private PlayerClient playerC;
	private int barR      = 30;		
	private int playerH   = 120; 	
	private int max_Score = 9; 		
	private int mPLAYER   = 5; 		
	private boolean Restart   = false;  
	private boolean restartON = false;
	
	private static Socket clientSoc  = null;
	private static ServerSocket serverSoc  = null;
	private int portAdd;
	
	private Graphics g;
	private Font mFont = new Font("TimesRoman",Font.BOLD,50);
	private Font rFont = new Font("TimesRoman",Font.BOLD,18);
    	private String[] message;	
    	private Thread movB;
    
	public PongServer(String servername, String portAdd){
			
		playerS = new PlayerServer();
		playerC = new PlayerClient("");
		playerS.setName(servername);
				
		this.portAdd = Integer.parseInt(portAdd);
		this.isRunning = true;
		this.setTitle(TITLE + "::port number["+portAdd+"]");
		this.setSize(WIDTH,HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
			
		movingBALL = new Balle(playerS.getBallx(), playerS.getBally(), -1);
			
		addKeyListener(this);
	   }	
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		// TODO Auto-generated method stub
   	 try {
   		 	serverSoc = new ServerSocket(portAdd);
        	 System.out.println("Server has started to running on the "+portAdd+" port.\nWaiting for a player...");
        	 System.out.println("Waiting for connection...");
        	 playerS.setImessage("Waiting for a player...");
        	 clientSoc = serverSoc.accept();
   			 
        	 System.out.println("Connected a player...");
        	
        	 if(clientSoc.isConnected()){ 
        		 
        		boolean notchecked = true;
        		 movB = new Thread(movingBALL);
        		 while(true){
        			  
        			 if(playerS.getScoreP() >= max_Score || playerS.getScoreS()>= max_Score && Restart==false){
        				 
        				 if(playerS.getScoreS()>playerS.getScoreP()){        				 
        					 playerS.setOmessage("Won               Loss-Play Again: Press any key || Exit: Esc|N");
        					 playerS.setImessage("Won               Loss-Play again? ");
        					 Restart = true;
        				 }
        				 else{
        					 playerS.setImessage("Loss              Won-Play Again: Press any key || Exit: Esc|N");
        					 playerS.setOmessage("Loss              Won-Play Again: Press any key || Exit: Esc|N");
        					 Restart = true;
        					 }
                      	movB.suspend();
                    }
        			 
        			
            		 if(playerC.ok && notchecked){
            			 playerS.setImessage("");
                  	    movB.start();
                  	    notchecked = false;
                 	}
        			 
            		 updateBall();
        			
        			ObjectInputStream getObj = new ObjectInputStream(clientSoc.getInputStream());
					playerC = (PlayerClient) getObj.readObject();
					getObj = null;
					
					ObjectOutputStream sendObj = new ObjectOutputStream(clientSoc.getOutputStream());
                 	sendObj.writeObject(playerS);
                 	sendObj = null;
                 
                 	if(restartON){
                 	
                 		if(playerC.restart){
            			playerS.setScoreP(0);
            			playerS.setScoreS(0);
            			playerS.setOmessage("");
            			playerS.setImessage("");
            			Restart = false;
                 		playerS.setRestart(false);
                 		playerS.setBallx(380);
                 		playerS.setBally(230);
						movingBALL.setPosX(380);
                 		movingBALL.setPosY(230);
                 		movB.resume();
                 		restartON = false;
                 		}
                 	}
                 	repaint();
                 	}
        	}
        	 else{
        		 System.out.println("Disconnected...");
        	 }
        }
        catch (Exception e) {System.out.println(e);}
}
	
	private Image createImage(){
		
	    BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	    g = bufferedImage.createGraphics();
	    
		Toolkit t=Toolkit.getDefaultToolkit();
        Image i=t.getImage("d59db2c36583d8c56982076bba641bb6.png");
        g.drawImage(i, 0, 75, this);
	    
	    // - Score - //
		g.setColor(Color.gray);
        g.fillRect(600, 50,  255, 150);

        g.setColor(Color.white);
        g.fillRect(605, 100, 245, 3);
        
        g.setColor(Color.white);
        g.fillRect(725, 50, 3, 150);


        g.setColor(Color.white);
        g.setFont(new Font("Verdana",Font.BOLD,20));
        g.drawString(""+playerS.getName(), 605, 80);
        g.setFont(new Font("Verdana",Font.BOLD,80));
        g.drawString(""+playerS.getScoreS(), 635, 180);

        g.setColor(Color.white);
        g.setFont(new Font("Verdana",Font.BOLD,20));
        g.drawString(""+playerC.getName(), 735, 80);
        g.setFont(new Font("Verdana",Font.BOLD,80));
        g.drawString(""+playerS.getScoreP(), 765, 180);
	    
	    // - Players - //
		g.setColor(Color.RED);
        g.fillRect(playerC.getX(), playerC.getY(), playerH, barR);
        g.setColor(Color.white);

		g.setColor(Color.BLUE);
        g.fillRect(playerS.getX(), playerS.getY(), playerH, barR);
	    
	    // - Ball - //
	    g.setColor(new Color(255,255,255));
	    g.fillOval(playerS.getBallx(), playerS.getBally(), 45, 45);
	    
	    // - Message - //
	    message = playerS.getImessage().split("-");
	    g.setFont(mFont);
	    g.setColor(Color.white);
	    if(message.length!=0){
	    	g.drawString(message[0],WIDTH/4-31,HEIGHT/2+38);
	    	if(message.length>1){
	    		if(message[1].length()>6){
	    		    g.setFont(rFont);
	    			g.setColor(new Color(228,38,36));
	    			g.drawString(message[1],WIDTH/4-31,HEIGHT/2+100);
	    		}
	    	}
	   }
	   return bufferedImage;
	}
	
	@Override
	public void paint(Graphics g){
		g.drawImage(createImage(), 0, 0, this);
	}

	
	public void updateBall(){
		
		checkCol();
		
		// - update the ball - //
		
		playerS.setBallx(movingBALL.getPosX());
		playerS.setBally(movingBALL.getPosY());
	}
	
	public void playerUP(){
		if(playerS.getX()>=25 && playerS.getX()+20 < 500){
			
			playerS.setX(playerS.getX()-mPLAYER);
		}
	}
	
	public void playerDOWN(){
		if(playerS.getX()>=-15 && playerS.getX()+100 < 500){
			
			playerS.setX(playerS.getX()+mPLAYER);
		}
	}
	
	
	public void checkCol(){
		
		
		if(playerS.getBally()+45 < playerC.getY() && playerS.getBally() > playerS.getY()+barR){
			check = true;
		}
		
		// - Server Player Score - //
		if(playerS.getBally()+45>playerC.getY() && (playerS.getBallx()<playerC.getX() || playerS.getBallx()+45>playerC.getX()+playerH) && check){
			
			playerS.setScoreS(playerS.getScoreS()+1);
			
			check = false;
		}
		
		// - Client Player Score - //
		else if (playerS.getBally()<=playerS.getY()+barR && (playerS.getBallx()<playerS.getX() || playerS.getBallx()+45>playerS.getX()+playerH) && check){
			
			playerS.setScoreP(playerS.getScoreP()+1);
			
			check = false;
			
		}

		// else if((playerS.getBallx()>playerS.getX() || playerS.getBallx()+45<playerS.getX()+playerH) && check){
		// 	this.movingBALL.setsens(this.movingBALL.getsens()*-1);
		// 	check = false;
		// }
		// else if((playerS.getBallx()>playerC.getX() || playerS.getBallx()+45<playerC.getX()+playerH) && check){
		// 	this.movingBALL.setsens(this.movingBALL.getsens()*-1);
		// 	check = false;
		// }
		
		
		// // - Checking Server Player Bar - //
		// if(movingBALL.getPosX()<=playerS.getX()+barR && movingBALL.getPosY()+45>= playerS.getY() && movingBALL.getPosY()<=playerS.getY()+playerH ){
		// 	movingBALL.setPosX(playerS.getX()+barR);
		// 	playerS.setBallx(playerS.getX()+barR);
		// }
		
		
		// // - Checking Client Player Bar - //
		// if(movingBALL.getPosX()+45>=playerC.getX() && movingBALL.getPosY() + 45 >= playerC.getY() && movingBALL.getPosY()<=playerC.getY()+playerH ){
		// 	movingBALL.setPosX(playerC.getX()-45);
		// 	playerS.setBallx(playerC.getX()-45);
		// }
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	
		// TODO Auto-generated method stub
		int keycode = arg0.getKeyCode();
		if(keycode == KeyEvent.VK_LEFT){
			if (this.movingBALL.getsens()==1) {
				this.movingBALL.setMove("left");
				this.movingBALL.setBalmove(true);
			}else{
				playerUP();
			}
			repaint();
		}
		if(keycode == KeyEvent.VK_RIGHT){
			if (this.movingBALL.getsens()==1) {
				this.movingBALL.setMove("right");
				this.movingBALL.setBalmove(true);
			}else{
				playerDOWN();
			}
			repaint();
		}
		if(keycode == KeyEvent.VK_DOWN){
			if (this.movingBALL.getsens()==1) {
				this.movingBALL.setMove("center");
				this.movingBALL.setBalmove(true);
			}

			repaint();
		}
		if(keycode == KeyEvent.VK_R){
			restartON = true;
			playerS.setRestart(true);
		}
		
		if(keycode == KeyEvent.VK_N || keycode == KeyEvent.VK_ESCAPE && Restart == true){
			try {
				this.setVisible(false);
				serverSoc.close();
				System.exit(EXIT_ON_CLOSE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
	
		Thread.currentThread().stop();
		this.setVisible(false);
		try {
			serverSoc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.exit(1);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(1);
	}


	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
 
}
