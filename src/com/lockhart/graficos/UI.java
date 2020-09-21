package com.lockhart.graficos;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lockhart.main.Game;



public class UI {
	private BufferedImage heart;
	private BufferedImage heartM;
	private BufferedImage heartN;
	public void render(Graphics g) {
		heart = Game.spritesheet.getSprite(112, 48, 16, 16);
		heartM = Game.spritesheet.getSprite(128, 48, 16, 16);
		heartN = Game.spritesheet.getSprite(144, 48, 16, 16);
		for(int i = 0; i<5; i++) {
			//VIDA CHEIA
		if(Game.player.life>=100) {
		g.drawImage(heart, 2+(i*16), 2, null);
		}else if(Game.player.life <=100 && Game.player.life >=90) {
			// VIDA menos meio coração
			if(i<5) {
				g.drawImage(heart, 2+(i*16), 2, null);
			}
			g.drawImage(heartM, 2+(4*16), 2, null);
		}else if(Game.player.life <90 && Game.player.life >=80) {
			// Vida com meno um coração
			if(i<5) {
				g.drawImage(heart, 2+(i*16), 2, null);
			}
			g.drawImage(heartN, 2+(4*16), 2, null);
	}else if(Game.player.life <80 && Game.player.life >=70) {
		if(i<4) {
			g.drawImage(heart, 2+(i*16), 2, null);
		}
		g.drawImage(heartM, 2+(3*16), 2, null);
		g.drawImage(heartN, 2+(4*16), 2, null);
	}else if(Game.player.life <70 && Game.player.life >=60) {
		if(i<4) {
			g.drawImage(heart, 2+(i*16), 2, null);
		}
		g.drawImage(heartN, 2+(3*16), 2, null);
		g.drawImage(heartN, 2+(4*16), 2, null);
	}else if(Game.player.life <60 && Game.player.life >=50) {
		if(i<3) {
			g.drawImage(heart, 2+(i*16), 2, null);
		}
		g.drawImage(heartM, 2+(2*16), 2, null);
		g.drawImage(heartN, 2+(3*16), 2, null);
		g.drawImage(heartN, 2+(4*16), 2, null);
	}else if(Game.player.life <60 && Game.player.life >=50) {
		if(i<3) {
			g.drawImage(heart, 2+(i*16), 2, null);
		}
		g.drawImage(heartN, 2+(2*16), 2, null);
		g.drawImage(heartN, 2+(3*16), 2, null);
		g.drawImage(heartN, 2+(4*16), 2, null);
	}else if(Game.player.life <50 && Game.player.life >=40) {
		if(i<2) {
			g.drawImage(heart, 2+(i*16), 2, null);
		}	
		g.drawImage(heartM, 2+(1*16), 2, null);
		g.drawImage(heartN, 2+(2*16), 2, null);
		g.drawImage(heartN, 2+(3*16), 2, null);
		g.drawImage(heartN, 2+(4*16), 2, null);
	}else if(Game.player.life <40 && Game.player.life >=30) {
		if(i<2) {
			g.drawImage(heart, 2+(i*16), 2, null);
		}	
		g.drawImage(heartN, 2+(1*16), 2, null);
		g.drawImage(heartN, 2+(2*16), 2, null);
		g.drawImage(heartN, 2+(3*16), 2, null);
		g.drawImage(heartN, 2+(4*16), 2, null);
	}
	else if(Game.player.life <30 && Game.player.life >=20) {
		if(i<1) {
			g.drawImage(heart, 2+(i*16), 2, null);
		}	
		g.drawImage(heartM, 2+(0*16), 2, null);
		g.drawImage(heartN, 2+(1*16), 2, null);
		g.drawImage(heartN, 2+(2*16), 2, null);
		g.drawImage(heartN, 2+(3*16), 2, null);
		g.drawImage(heartN, 2+(4*16), 2, null);
	}
	else if(Game.player.life <20 && Game.player.life >=10) {
		if(i<1) {
			g.drawImage(heart, 2+(i*16), 2, null);
		}	
		g.drawImage(heartN, 2+(0*16), 2, null);
		g.drawImage(heartN, 2+(1*16), 2, null);
		g.drawImage(heartN, 2+(2*16), 2, null);
		g.drawImage(heartN, 2+(3*16), 2, null);
		g.drawImage(heartN, 2+(4*16), 2, null);
	}else if(Game.player.life <10 && Game.player.life >=5) {

		g.drawImage(heartM, 2+(0*16), 2, null);
		g.drawImage(heartN, 2+(1*16), 2, null);
		g.drawImage(heartN, 2+(2*16), 2, null);
		g.drawImage(heartN, 2+(3*16), 2, null);
		g.drawImage(heartN, 2+(4*16), 2, null);
	}else if(Game.player.life  <5) {

		g.drawImage(heartN, 2+(0*16), 2, null);
		g.drawImage(heartN, 2+(1*16), 2, null);
		g.drawImage(heartN, 2+(2*16), 2, null);
		g.drawImage(heartN, 2+(3*16), 2, null);
		g.drawImage(heartN, 2+(4*16), 2, null);

			}
		}
	}
}
