package com.lockhart.entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.lockhart.main.Game;
import com.lockhart.world.Camera;
import com.lockhart.world.Node;
import com.lockhart.world.Vector2i;

public class Entity {
	
	public static BufferedImage FLAMEBALL_EN = Game.spritesheet.getSprite(112, 0, 16, 16);
	public static BufferedImage BAT_EN = Game.spritesheet.getSprite(112, 16, 16, 16);
	public static BufferedImage FLAMEBALL_EN_FEEDBACK = Game.spritesheet.getSprite(160, 0, 16, 16);
	public static BufferedImage BAT_EN_FEEDBACK = Game.spritesheet.getSprite(160, 16, 16, 16);
	public static BufferedImage LIFE_EN = Game.spritesheet.getSprite(112, 48, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(0, 129, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(112, 64, 16, 16);
	public static BufferedImage TREE_EN = Game.spritesheet.getSprite(17, 0, 16, 16);
	public static BufferedImage GRASS_EN = Game.spritesheet.getSprite(31, 64, 34, 16);
	public static BufferedImage PORTAL_EN = Game.spritesheet.getSprite(0, 146, 25, 16);
	
	
	
	
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    
    public int depth;
    
    protected List<Node> path;
    
	private BufferedImage sprite;
	//private boolean debug = false;
	private int mwidth, mheight;
	public int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
	this.x =x;
	this.y =y;
	this.width = width;
	this.height = height;
	this.sprite = sprite;
	
	this.maskx = 0;
	this.masky = 0;
	this.mwidth = width;
	this.mheight = height; 
	
	}
	
	public void setMask(int maskx, int masky, int midth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = width;
		this.mheight = height; 
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0, Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
		
	};
	public void setX(int newX) {
		this.x =newX;
	}
	public void setY(int newY) {
		this.y =newY;
	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int)this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public double calculatedDistance(int x1, int x2, int y1, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size()>0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16 && !isColliding(this.getX() + 1, this.getY())) {
					x++;
				}else if(x > target.x * 16  && !isColliding(this.getX() - 1, this.getY())) {
					x--;
				}
				
				if(y < target.y * 16 && !isColliding(this.getX(), this.getY()+1)) {
					y++;
				}else if(y > target.y * 16 && !isColliding(this.getX(), this.getY()-1)) {
					y--;
				}
				
				if( x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size()-1);
				}
					
				
			}
		}
	}
	
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle flameBallCurrent = new Rectangle(xnext + maskx, ynext + masky, mwidth, mheight);
		for(int i = 0; i < Game.flameballs.size(); i++) {
			FlameBall f = Game.flameballs.get(i);
			if(f == this) 
				continue;
		Rectangle targetFlameBall = new Rectangle(f.getX()+ maskx, f.getY()+ masky, mwidth, mheight);
			if(flameBallCurrent.intersects(targetFlameBall)) {
				return true;
			}
		
		}
		return false;
	}
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle  e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight );
		Rectangle  e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight );
	
		return e1Mask.intersects(e2Mask);
	}
	
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}
	
}