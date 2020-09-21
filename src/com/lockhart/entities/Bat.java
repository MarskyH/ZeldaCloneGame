package com.lockhart.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.lockhart.main.Game;
import com.lockhart.main.Sound;
import com.lockhart.world.AStar;
import com.lockhart.world.Camera;
import com.lockhart.world.Vector2i;
import com.lockhart.world.World;

public class Bat extends Entity {
	
	private double speed = 1;
	
	private int frames=0, maxFrames=15, index = 0, maxIndex = 2;
	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	private BufferedImage[] sprites; 
	private BufferedImage[] spritesDamage;
	private int life = 5;
	private boolean isDamaged = false;
	private int damageframes = 10, damageCurrent = 0;
	
	public Bat(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[3];
		sprites [0]= Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites [1]= Game.spritesheet.getSprite(128, 16, 16, 16);
		sprites [2]= Game.spritesheet.getSprite(144, 16, 16, 16);
		spritesDamage = new BufferedImage[3];
		spritesDamage [0]= Game.spritesheet.getSprite(160, 16, 16, 16);
		spritesDamage [1]= Game.spritesheet.getSprite(176, 16, 16, 16);
		spritesDamage [2]= Game.spritesheet.getSprite(192, 16, 16, 16);
	
	}
	
	public void tick(){
		depth = 0;
		if(!isCollidingWithPlayer()) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
				Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
				path = AStar.findPath(Game.world, start, end);
			}
		}else {
			if(new Random().nextInt(100) < 5) {
				//Sound.hurtEffect.play();
				Game.player.life-=Game.rand.nextInt(3);
				Game.player.isDamaged = true;
			}
		}
			if(new Random().nextInt(100) < 50)
				followPath(path);
			
			if(x % 16 == 0 && y % 16 == 0) {
				if(new Random().nextInt(100) < 80) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
					path = AStar.findPath(Game.world, start, end);
				}
			}
				
			
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
			
			CollidingBullet();
			
			if(life <= 0) {
				destroySelf();
				return;
			}
			
			if(isDamaged) {
				this.damageCurrent++;
				if(this.damageCurrent == this.damageframes) {
					this.damageCurrent = 0;
					this.isDamaged = false;
			}
		}
	}
	
	
		public void destroySelf() {
			Game.bats.remove(this);
			Game.entities.remove(this);
		}
		
		public void CollidingBullet() {
			for (int i = 0 ; i < Game.bullets.size(); i++) {
				Entity e = Game.bullets.get(i);
				if(e instanceof BulletShoot) {
					if(Entity.isColliding(this, e)) {
						life--;
						isDamaged = true;
						Game.bullets.remove(i);
						return;
					}
				}
			}
		}	
	
	public boolean isCollidingWithPlayer() {
		Rectangle batCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);
		return batCurrent.intersects(player);
	}
	public void render(Graphics g) {
		if(!isDamaged) 	
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		else 	
			g.drawImage(spritesDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		
		//g.setColor(Color.BLACK);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
}



