package com.lockhart.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.lockhart.main.Game;
import com.lockhart.main.Sound;
import com.lockhart.world.Camera;
import com.lockhart.world.World;


public class Player extends Entity{
	
	private int damageFrames = 0,  damageIndex = 0; 
	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir=3;
	public int dir = right_dir;
	public double speed = 1.5;
	private int frames=0, maxFrames=5, index = 0, maxIndex = 3;
	public boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] playerDamageRight;
	private BufferedImage[] playerDamageLeft;
	private BufferedImage[] playerDamageUp;
	private BufferedImage[] playerDamageDown;
	private BufferedImage[] playerWeaponRight;
	private BufferedImage[] playerWeaponLeft;
	private BufferedImage[] playerWeaponUp;
	private BufferedImage[] playerWeaponDown;
	private BufferedImage[] playerWeaponDamageRight;
	private BufferedImage[] playerWeaponDamageLeft;
	private BufferedImage[] playerWeaponDamageUp;
	private BufferedImage[] playerWeaponDamageDown;
	
	public int ammo = 0;
	
	public boolean weapon = false;
	
	public boolean isDamaged = false;
	
	
	
	public double life = 100, maxLife = 100;
	
	public boolean shoot = false;
	
	public boolean MouseShoot = false;
	
	public int mx, my;
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		playerDamageRight = new BufferedImage[4];
		playerDamageLeft = new BufferedImage[4];
	 	playerDamageUp = new BufferedImage[4];
	 	playerDamageDown = new BufferedImage[4]; 
	 	playerWeaponRight = new BufferedImage[4];
	 	playerWeaponLeft = new BufferedImage[4];
		playerWeaponUp = new BufferedImage[4];
		playerWeaponDown = new BufferedImage[4];
		playerWeaponDamageRight = new BufferedImage[4];
	 	playerWeaponDamageLeft = new BufferedImage[4];
		playerWeaponDamageUp = new BufferedImage[4];
		playerWeaponDamageDown = new BufferedImage[4];
		for(int i = 0; i<4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32 + (i*17), 0, 17, 16);
			}
		for(int i = 0; i<4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(32 + (i*17), 16, 17, 16);
			}
		for(int i = 0; i<4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*17), 32, 17, 16);
		}
		for(int i = 0; i<4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*17), 48, 17, 16);
			}
		for(int i = 0; i<4; i++) {
			playerDamageDown[i] = Game.spritesheet.getSprite(32 + (i*17), 64, 17, 16);
				}
		for(int i = 0; i<4; i++) {
			playerDamageUp[i] = Game.spritesheet.getSprite(32 + (i*17), 80, 17, 16);
				}
		for(int i = 0; i<4; i++) {
			playerDamageRight[i] = Game.spritesheet.getSprite(32 + (i*17), 96, 17, 16);
				}
		for(int i = 0; i<4; i++) {
			playerDamageLeft[i] = Game.spritesheet.getSprite(32 + (i*17), 112, 17, 16);
				}	
		for(int i = 0; i<4; i++) {
			playerWeaponRight[i] = Game.powersheet.getSprite(0 + (i*17), 0, 17, 16);
				}
		for(int i = 0; i<4; i++) {
			playerWeaponDown[i] = Game.powersheet.getSprite(0 + (i*17), 16, 17, 16);
				}
		for(int i = 0; i<4; i++) {
			playerWeaponUp[i] = Game.powersheet.getSprite(0 + (i*17), 32, 17, 16);
				}
		for(int i = 0; i<4; i++) {
			playerWeaponLeft[i] = Game.powersheet.getSprite(0 + (i*17), 48, 17, 16);
				}
		for(int i = 0; i<4; i++) {
			playerWeaponDamageRight[i] = Game.powersheet.getSprite(0 + (i*17), 64, 17, 16);
				}
		for(int i = 0; i<4; i++) {
			playerWeaponDamageDown[i] = Game.powersheet.getSprite(0 + (i*17), 80, 17, 16);
				}
		for(int i = 0; i<4; i++) {
			playerWeaponDamageUp[i] = Game.powersheet.getSprite(0 + (i*17), 96, 17, 16);
				}
		for(int i = 0; i<4; i++) {
			playerWeaponDamageLeft[i] = Game.powersheet.getSprite(0 + (i*17), 112, 17, 16);
				}
	}
	public void tick() {
		depth = 1;
		moved = false;
		if(right && World.isFree((int)(x+speed) ,this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
			
			
		}else if (left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		if(up && World.isFree(this.getX()-1, (int)(y-speed))) {
			moved = true;
			dir = up_dir;
			y-=speed;
			
		}else if(down && World.isFree(this.getX()-1,  (int)(y+speed))) {
			moved = true;
			dir = down_dir;
			y+=speed;
		}
		if(!isDamaged) {
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index >  maxIndex) 
					index = 0;
			}
		}
	}
		checkCollisionlifePack();
		checkCollisionAmmo();
		checkCollisionGun();
		
		if(isDamaged) {
		this.damageFrames++;
		if(this.damageFrames == 10) {
				this.damageFrames = 0;
				isDamaged = false;
		}
	}
		if(shoot) {
			shoot = false;
		if(weapon && ammo > 0) {
			Sound.ShootEffect.play();
			ammo--;
			int dx = 0;
			int px = 0;
			int py = 0;
			int dy = 0;
			if(dir == right_dir) {
				px = 18;
				dx = 1;
			}else if(dir == left_dir) {
				px = -8;
				dx = -1 ;
			}else if(dir == up_dir) {
				py = -18;
				dy = -1;
			}else if(dir == down_dir) {
				py = 18;
				dy = 1;
	}
			if(World.isFree(this.getX(), this.getY())) {
		BulletShoot bullet =  new BulletShoot(this.getX()+ px, this.getY() + py, 16, 16, null, dx, dy);
		Game.bullets.add(bullet);
	}
	}
}		
		/*if(MouseShoot) {
			MouseShoot = false;
			double angle =Math.atan2(my - (this .getY()+8 - Camera.y), mx - (this.getX()+8 - Camera.x));
			if(weapon && ammo > 0) {
				ammo--;
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				int py = 0;
				int px = 0;
				
				BulletShoot bullet =  new BulletShoot(this.getX()+ px, this.getY() + py, 16, 16, null, dx, dy);
				Game.bullets.add(bullet);
	}
}*/
		if(life<0) {
			life = 0;
			Game.gameState = "GAME_OVER";
		}

		updateCamera();
		
		
}
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH );
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT); 
	}
	public void checkCollisionPortal() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Portal) {
				if(Entity.isColliding(this, e)) {
					Portal.NextLevel = true;
					Game.entities.remove(i);
					return;
					}
			}
		}
	}
	public void checkCollisionlifePack() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Life) {
				if(Entity.isColliding(this, atual)) {
					life+=25;
					if(life >100) 
						life = 100;
					Game.entities.remove(i);
				}
			}
		}
	}
	public void checkCollisionAmmo() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Bullet) {
				if(Entity.isColliding(this, e)) {
					ammo+=100;
					Game.entities.remove(i);
					return;
					}
				}
				
			}
		}
	public void checkCollisionGun() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Weapon) {
				if(Entity.isColliding(this, e)) {
					Sound.PowerUpEffect.play();
					weapon=true;
					//System.out.println("Arma coletada");
					Game.entities.remove(i);
					return;
					}
				}
				
			}
		}
	
	
	public void render(Graphics g) {
		if(!isDamaged) {
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x,  this.getY() - Camera.y , null);
			if(weapon) {
				
			}
		}else if(dir == left_dir) {
			g.drawImage(leftPlayer[index],  this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(dir == up_dir) {
			g.drawImage(upPlayer[index],  this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(dir == down_dir) {
			g.drawImage(downPlayer[index],  this.getX() - Camera.x, this.getY() - Camera.y, null);
		}	
	}
		if(isDamaged) {
		if(dir == right_dir) {
			g.drawImage(playerDamageRight[damageIndex], this.getX() - Camera.x,  this.getY() - Camera.y , null);
		}else if(dir == left_dir) {
			g.drawImage(playerDamageLeft[damageIndex],  this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(dir == up_dir) {
			g.drawImage(playerDamageUp[damageIndex],  this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(dir == down_dir) {
			g.drawImage(playerDamageDown[damageIndex],  this.getX() - Camera.x, this.getY() - Camera.y, null);
		}	
		}
		if(weapon) {
			if(dir == right_dir) {
			g.drawImage(playerWeaponRight[index], this.getX() - Camera.x,  this.getY() - Camera.y , null);
			}else if(dir == left_dir) {
				g.drawImage(playerWeaponLeft[index],  this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == up_dir) {
				g.drawImage(playerWeaponUp[index],  this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == down_dir) {
				g.drawImage(playerWeaponDown[index],  this.getX() - Camera.x, this.getY() - Camera.y, null);
			}	
		}
		if(weapon && isDamaged) {
			if(dir == right_dir) {
				g.drawImage(playerWeaponDamageRight[index], this.getX() - Camera.x,  this.getY() - Camera.y , null);
				}else if(dir == left_dir) {
					g.drawImage(playerWeaponDamageLeft[index],  this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == up_dir) {
					g.drawImage(playerWeaponDamageUp[index],  this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == down_dir) {
					g.drawImage(playerWeaponDamageDown[index],  this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
	
		}
		
	}
}