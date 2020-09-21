package com.lockhart.entities;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.lockhart.main.Game;
import com.lockhart.world.Camera;

public class BulletShoot extends Entity {
	private BufferedImage[] bulletsheet;
	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	private double dy;
	private double dx;
	private double spd = 4;
	private int shootIndex = 0, shootFrames = 0, shootMaxFrames = 5, shootMaxIndex = 4; 
	private int life = 30, curLife = 0;
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		bulletsheet = new BufferedImage[5];
		for(int i = 0; i<5; i++) {
			bulletsheet[i] = Game.powersheet.getSprite(68 + (i*16),0, 16, 16);
		}
	}
	
		public void tick() {
			x+=dx*spd;
			y+=dy*spd;
			curLife++;
			if(curLife == life) {
				Game.bullets.remove(this);
				return;
			}
		
				this.shootFrames++;
				if(this.shootFrames == this.shootMaxFrames) {
						this.shootFrames = 0;
						this.shootIndex++;
					if(this.shootIndex>this.shootMaxIndex) {
						shootIndex = 0;
				}
			}
				/*if(isColliding(this,)) {
					
				}*/
		}
		public boolean isColliding(int xnext, int ynext) {
			Rectangle bulletShootCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
			for(int i = 0; i < Game.bullets.size(); i++) {
				BulletShoot b = Game.bullets.get(i);
				if(b == this) 
					continue;
			Rectangle targetBulletShoot = new Rectangle(b.getX()+ maskx, b.getY()+ masky, maskw, maskh);
				if(bulletShootCurrent.intersects(targetBulletShoot)) {
					return true;
				}
			
			}
			return false;
		}
		public void render(Graphics g) {
				g.drawImage(bulletsheet[shootIndex], this.getX() - Camera.x,  this.getY() - Camera.y , null);	
		//  g.setColor(Color.GREEN);
		//  g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
			
		}
}
