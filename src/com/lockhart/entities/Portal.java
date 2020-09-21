package com.lockhart.entities;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.lockhart.main.Game;
import com.lockhart.world.Camera;


public class Portal extends Entity {
	public static boolean NextLevel = false;
	private int frames=0, maxFrames=14, index = 0, maxIndex = 26;
	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	private BufferedImage[] sprites;
	public Portal(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[27];
		for (int i = 0; i<27; i++) {
			sprites[i] = Game.spritesheet.getSprite(0 +(i*25), 146, 25, 16);
		}
	}
	
	public void tick() {
		depth = 0;
		  /*maskx = 8;
			masky = 8;
			maskw = 5;
			maskh = 5;*/
		
			if(isCollidingWithPlayer()==false) {
				NextLevel = true;
			}else {
				NextLevel = false;
				}
			
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index >  maxIndex) 
						index = 0;
				}
			}
	
		
		public boolean isCollidingWithPlayer() {
			Rectangle flameBallCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
			Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);
			return flameBallCurrent.intersects(player);
		}
		
		public boolean isColliding(int xnext, int ynext) {
			Rectangle portalCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
			for(int i = 0; i < Game.portals.size(); i++) {
				Portal p = Game.portals.get(i);
				if(p == this) 
					continue;
			Rectangle targetFlameBall = new Rectangle(p.getX()+ maskx, p.getY()+ masky, maskw, maskh);
				if(portalCurrent.intersects(targetFlameBall)) {
					return true;
				}
			
			}
			return false;
		}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

	}
}




