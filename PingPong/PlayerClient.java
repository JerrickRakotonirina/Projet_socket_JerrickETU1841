package player;

import java.io.Serializable;


public class PlayerClient implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name ="";
	private int	x, y;
    public boolean ok = false;
    public boolean restart = false;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public PlayerClient(String name){
		this.name = name;
		x = 50;
		y = 775;
	}
	

	@Override
	public String toString() {
		return "PlayerClient [name=" + name + ", x=" + x + ", y=" + y + "]";
	}
}
