package com.lockhart.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


import com.lockhart.main.Game;
//import com.lockhart.world.Camera;
import com.lockhart.world.Camera;

public class Weapon extends Entity{
	private int frames=0, maxFrames=20, index = 0, maxIndex = 9;
	private BufferedImage[] sprites;
	public Weapon(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[maxIndex];
		for (int i = 0 ; i < 9; i++) {
		sprites [i]= Game.spritesheet.getSprite(0+(i*16), 128, 16, 16);
		}
	}
	public void tick() {
		depth =0;
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index >=  maxIndex) 
				index = 0;
			}
		}
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
		}
}
