package main;

import pong.*;
import player.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Test extends JFrame implements KeyListener, Runnable{

	
	private static final long serialVersionUID = 1L;
	
	private static Image  image;
	private Graphics g;
	private static final String TITLE  = "Ping Pong";	
	private static final int    WIDTH  = 800;		  
	private static final int    HEIGHT = 460;		 
	private String servername = "servername" , clientname = "clientname";
	
	public Test(){

	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setVisible(true);
		this.setTitle(TITLE);
		this.setSize(WIDTH,HEIGHT);
		this.setResizable(false);
		this.addKeyListener(this);
	} 
	
	public static void main(String[] args){
		Toolkit tk = Toolkit.getDefaultToolkit();
		image = tk.getImage("background_pong.png");
		Test newT = new Test();
		newT.run();

	}
	
	
	private Image createImage(){
		
	    BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	    g = bufferedImage.createGraphics();
	    g.fillRect(0, 0, WIDTH, HEIGHT);
	    g.drawImage(image,0, 0, this);
	    return bufferedImage;
	    
	}
	@Override
	public void paint(Graphics g){
		g.drawImage(createImage(), 0, 20, this);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
		int    keyCode = arg0.getKeyCode();
		String portAdd = null;
		String ipAdd   = null;
		
		if(keyCode==KeyEvent.VK_S){
			
			portAdd = JOptionPane.showInputDialog(null, "ex. 1024", "Enter server port:", 1);
			
			if(portAdd!=null){
				if(!isPort(portAdd)){
					JOptionPane.showMessageDialog(null, "Enter port number as a right format!", "Error!", 1);
				}
			
			else{
				
				servername = JOptionPane.showInputDialog(null, "Nick name:", "Enter server name:", 1);
				servername+="";
				
				if(servername.length()>10 || servername.length()<3 || servername.startsWith("null")){
					JOptionPane.showMessageDialog(null, "Enter name as a right format!", "Error!", 1);
					
				} 
				
				else{
					
					PongServer myServer = new PongServer(servername,portAdd);
					Thread myServerT = new Thread(myServer);
					myServerT.start();
					this.setVisible(false);
				}
				}
			}
		}
			
		
		
		if(keyCode==KeyEvent.VK_C){
			
			ipAdd = JOptionPane.showInputDialog(null, "ex. 100.100.10.10", "Enter server ip:", 1);
			
			if(ipAdd!=null){
				
				if(!isIPAddress(ipAdd)){
					JOptionPane.showMessageDialog(null, "Enter ip number as a right format!", "Enter server ip:", 1);
				}
				
				else{
					portAdd = JOptionPane.showInputDialog(null, "ex. 1024", "Enter server port number:", 1);
					
					if(portAdd!=null){
						if(!isPort(portAdd)){
							JOptionPane.showMessageDialog(null, "Enter port number as a right format!", "Error!:", 1);
						}
						else{
							clientname = JOptionPane.showInputDialog(null, "Nick name:", "Enter server name:", 1);
							clientname += "";
							if(clientname.length()>10 || clientname.length()<3 || clientname.startsWith("null")){
								JOptionPane.showMessageDialog(null, "Enter name as a right format!", "Error!", 1);
							}
							else{
								PongClient myClient = new PongClient(clientname, portAdd, ipAdd);
								Thread myClientT = new Thread(myClient);
								myClientT.start();
								this.setVisible(false);
							}	
						}
					}
				}
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
	
	
	private boolean isPort(String str) {  
		  Pattern pPattern = Pattern.compile("\\d{1,4}");  
		  return pPattern.matcher(str).matches();  
		}  
	private boolean isIPAddress(String str) {  
		  Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");  
		  if(ipPattern.matcher(str).matches()){
			return true;
		  }else if(str.equals("localhost")){
			return true;
		  }else{
			return false;
		  }
		}

 
}
