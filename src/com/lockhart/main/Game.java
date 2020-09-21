package com.lockhart.main;



import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
//import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.lockhart.entities.Bat;
import com.lockhart.entities.BulletShoot;
import com.lockhart.entities.Entity;
import com.lockhart.entities.FlameBall;
import com.lockhart.entities.Player;
import com.lockhart.entities.Portal;
import com.lockhart.entities.Weapon;
import com.lockhart.graficos.Spritesheet;
import com.lockhart.graficos.UI;
import com.lockhart.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private boolean isRunning = true;
	private Thread thread;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	private BufferedImage image;
	public BufferedImage lightmap;
	public static BufferedImage minimap;
	
	public static List<Weapon> weapons;
	public static List <Bat> bats;
	public static List<FlameBall> flameballs;
	public static List<Entity> entities;
	public static List<BulletShoot> bullets;
	public static List<Portal> portals;
	
	public static Spritesheet spritesheet;
	public static Spritesheet powersheet;
	public static Spritesheet Newfundo;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public Menu menu; 
	
	public UI ui;
	
	//public int xx, yy;
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
	public static Font newfont;
	
	
	private boolean showMessageGameOver = false;
	private boolean restartGame = false;
	public boolean saveGame = false;
	
	public static String gameState = "MENU";

	private int CUR_LEVEL = 1, MAX_LEVEL = 2;
	private int framesGameOver = 0;
	
	public int mx, my;
	public static int[] pixels;
	public static int[] lightMapPixels;
	public static int[] minimapPixels;
	public  Game() {
		//Sound.musicBackground.loop();
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		//setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		//Inicializando Objetos
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	    try {
			lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
	    lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0, lightmap.getWidth());
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();
		bats = new ArrayList<Bat>();
		weapons = new ArrayList<Weapon>();
		flameballs = new ArrayList<FlameBall>();
		bullets = new ArrayList<BulletShoot>();
		portals = new ArrayList<Portal>();
		spritesheet = new Spritesheet("/spritesheet.png");
		powersheet = new Spritesheet("/weaponsheet.png");
		Newfundo = new Spritesheet("/fundoGame01Beta.png");
		player = new Player (0,0,17,16,spritesheet.getSprite(32, 0, 17, 16));
		entities.add(player);
		world = new World("/level1.png");
		
		minimap = new BufferedImage (World.WIDTH,World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		minimapPixels = ((DataBufferInt)minimap.getRaster().getDataBuffer()).getData();
		
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(40f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		menu = new Menu();
		
		
	}
	public void initFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		//frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();
		Image imagem = null;
		try {
		imagem = ImageIO.read(getClass().getResource("/icon.png"));
		} catch (IOException e) {
		e.printStackTrace();
		}
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("/img.png"));
		Cursor c = toolkit.createCustomCursor(image, new Point(0,
				0), "img");
		frame.setCursor(c);
		frame.setIconImage(imagem);
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
		
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[]args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		if(gameState ==  "NORMAL") {
			//xx++;
			if(this.saveGame) {
				this.saveGame = false;
				String[] opt1 = {"level", "vida"};
				int[]opt2 = {this.CUR_LEVEL, (int) player.life};
				Menu.saveGame(opt1, opt2, 10);
			}
			this.restartGame = false;
		for (int i =0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
		
		if(flameballs.size() == 0 && bats.size() == 0 && Portal.NextLevel==false) {
			CUR_LEVEL++;
			if(CUR_LEVEL > MAX_LEVEL) {
				CUR_LEVEL=1;
			}
			String newWolrd = "level"+CUR_LEVEL+".png";
			World.restartGame(newWolrd);
		}
	}else if(gameState == "GAME_OVER"){
		this.framesGameOver++;
		if(this.framesGameOver == 30) {
			this.framesGameOver = 0;
			if(this.showMessageGameOver) 
				this.showMessageGameOver = false;
			else 
				this.showMessageGameOver = true;
			}
		 if(restartGame) {
			 this.restartGame = false;
			 gameState = "NORMAL";
			 CUR_LEVEL = 1;
			 String newWolrd = "level"+CUR_LEVEL+".png";
			World.restartGame(newWolrd);
		}
	}else if(gameState == "MENU") {
		player.updateCamera();
		menu.tick();
	}

}
/*	public void drawRectangleExample(int xoff, int yoff){
		for(int xx = 0; xx <32; xx++ ) {
			for(int yy = 0; yy < 32; yy++) {
				int xOff = xx+xoff;
				int yOff = yy+yoff;
				if(xOff<0 || yOff <0 || xOff >= WIDTH || yOff >= HEIGHT)
					continue;
				pixels[xOff+(yOff*WIDTH)] = 0xff0000;
			}
		}
	}*/
	public void applyLight() {
		/*for(int xx = 0; xx < Game.WIDTH; xx++ ) {
			for(int yy = 0; yy < Game.HEIGHT; yy++ ) {
				if(lightMapPixels[xx+(yy * Game.WIDTH)]==0xffffffff) {
					pixels[xx+(yy*Game.WIDTH)] = 0;
				}
			}
		}*/
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT); 
		
		//RENDERIZAÇÃO DO JOGO
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		Collections.sort(entities, Entity.nodeSorter);
		for (int i =0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		
		
		applyLight();
		ui.render(g);
		/**/
		g.dispose();
		g = bs.getDrawGraphics();
	//	drawRectangleExample(xx, yy);
		//g.drawImage(image, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		//g.setFont(new Font("arial", Font.BOLD, 20));
		World.renderMiniMap();
		g.drawImage(minimap, 630, 40,World.WIDTH*3, World.HEIGHT*3,  null);
		g.setFont(newfont);
		g.setColor(Color.black);
		g.drawString("Munição: " + player.ammo, 580, 30);
		g.setFont(newfont);
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 25));
			g.setColor(Color.WHITE);
			g.drawString("FIM DE JOGO", (WIDTH*SCALE)/2 -80, (HEIGHT*SCALE)/2-20);
			g.setFont(new Font("arial", Font.BOLD, 20));
			if(showMessageGameOver)
				g.drawString(">PRESSIONE ENTER PARA REINICIAR<", (WIDTH*SCALE)/2 -185, (HEIGHT*SCALE)/2+40);
		}else if(gameState == "MENU") {
		menu.render(g);
	} 
	/*Graphics2D g2 = (Graphics2D) g;
	double angleMouse = Math.atan2(200+25 - my, 200+25 - mx);
	g2.rotate(angleMouse, 200+25, 200+25);
	//System.out.println(Math.toDegrees(angleMouse));
	g.setColor(Color.red);
	g.fillRect(200, 200, 50, 50);*/
		
		bs.show();	
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer =  System.currentTimeMillis();
		requestFocus();
		while(isRunning){		
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1 ) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer +=1000;
			}
		}
		stop();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || 
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT || 
						e.getKeyCode() == KeyEvent.VK_A) {
				player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || 
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN || 
						e.getKeyCode() == KeyEvent.VK_S) {	
				player.down = true;
			}
		}
		
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || 
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT || 
						e.getKeyCode() == KeyEvent.VK_A) {
				player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || 
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
			if(gameState == "MENU") {
				menu.up = true;
			}
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN || 
						e.getKeyCode() == KeyEvent.VK_S) {	
				player.down = false;
				if(gameState == "MENU") {
					menu.down = true;
				}
				
			}
		if(e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot = true;
		}if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if(gameState == "MENU") {
				menu.enter = true;
			}
	}if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		gameState = "MENU";
        Menu.pause = true;
		
	}
	if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		if(gameState == "NORMAL")
		this.saveGame = true;
	}
}
	@Override
	public void keyTyped(KeyEvent e) {
	
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		player.MouseShoot = true;
		player.mx = (e.getX()/3);
		player.my = (e.getY()/3);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		this.mx = e.getX();
		this.my = e.getY();
		
	}
}

