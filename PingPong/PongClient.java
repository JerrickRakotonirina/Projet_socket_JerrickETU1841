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
import java.net.Socket;
import javax.swing.JFrame;


public class PongClient extends JFrame implements KeyListener, Runnable, WindowListener{
	
	private static final long serialVersionUID = 1L;

	private static final String TITLE  = "ping-pong::client";	
	private static final int    WIDTH  = 920;		  
	private static final int    HEIGHT = 840;		  
	boolean isRunning = false;
	
	private PlayerServer playerS;
	private PlayerClient playerC;
	private int barR      = 30;		
	private int playerH   = 120; 	
	private int mPLAYER   = 5; 		
	
	private static Socket clientSoc;
	private int portAdd;
	private String ipAdd;
	private boolean reset = false;
	private int countS = 0;

	private Graphics g;
	private Font mFont = new Font("TimesRoman",Font.BOLD,50);
	private Font rFont = new Font("TimesRoman",Font.BOLD,18);
    private String[] message;


	public PongClient(String clientname, String portAdd, String ipAdd){
			
		playerS = new PlayerServer();
		playerC = new PlayerClient(clientname);
		playerS.setName(clientname);
			
		this.ipAdd = ipAdd;
		this.portAdd = Integer.parseInt(portAdd);
		this.isRunning = true;
			
		this.setTitle(TITLE);
		this.setSize(WIDTH,HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		addKeyListener(this);
	   }	
		

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Server Socket //
   	 try {
 
        	 System.out.println("Finding server...\nConnecting to "+ipAdd+":"+portAdd);
        	 clientSoc = new Socket(ipAdd, portAdd);
    		 System.out.println("Connected to server...");
            
        	 if(clientSoc.isConnected()){
            	System.out.println("TEST");
        		
 
            	 
            	 while(true){
            		 ObjectOutputStream sendObj = new ObjectOutputStream(clientSoc.getOutputStream());
        			 sendObj.writeObject(playerC);
        			 sendObj = null;
        			 
        			 ObjectInputStream getObj = new ObjectInputStream(clientSoc.getInputStream());
        			 playerS = (PlayerServer) getObj.readObject();
        			 getObj = null;
        			
        			 if(reset){
        				 
        				if(countS>5){
        				playerC.restart = false;
        				reset = false;
        				countS = 0;
        				}
        			}
        			 countS++;
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
	    
		g.setColor(Color.RED);
        g.fillRect(playerS.getX(), playerS.getY(), playerH, barR);
        g.setColor(Color.white);

		g.setColor(Color.BLUE);
        g.fillRect(playerC.getX(), playerC.getY(), playerH, barR);
        g.setColor(Color.white);
	    
	    g.setColor(new Color(255,255,255));
	    g.fillOval(playerS.getBallx(), playerS.getBally(), 45, 45);
	    
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
	
	public void paint(Graphics g){
		g.drawImage(createImage(), 0, 0, this);
		playerC.ok = true;
	}
	
	
	public void playerUP(){
		if(playerC.getX()>=25 && playerC.getX()+20 < 500){
			
			playerC.setX(playerC.getX()-mPLAYER);
			// System.out.println(""+playerS.getX());
		}
	}
	
	public void playerDOWN(){
		if(playerC.getX()>=-15 && playerC.getX()+100 < 500){
			
			playerC.setX(playerC.getX()+mPLAYER);
			// System.out.println(""+playerC.getX());
		}
	}


	@Override
	public void keyPressed(KeyEvent arg0) {
	
		// TODO Auto-generated method stub
		int keycode = arg0.getKeyCode();
		if(keycode == KeyEvent.VK_LEFT){
			playerUP();
			repaint();
		}
		if(keycode == KeyEvent.VK_RIGHT){
			playerDOWN();
			repaint();
		}
		if(playerS.isRestart()){
			playerC.restart = true;
			reset = true;
		}
		if(keycode == KeyEvent.VK_ESCAPE || keycode == KeyEvent.VK_N && playerS.isRestart()){
			try {
				this.setVisible(false);
				clientSoc.close();
				System.exit(EXIT_ON_CLOSE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
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
		 
	}



	@SuppressWarnings("deprecation")
	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		Thread.currentThread().stop();
		this.setVisible(false);
		try {
			clientSoc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
