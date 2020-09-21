package com.lockhart.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
 
	public static final Sound musicBackground =  new Sound("/music.wav");
	public static final Sound hurtEffect = new Sound("/hurt.wav");
	public static final Sound PowerUpEffect =  new Sound("/PowerUp.wav");
	public static final Sound hurtEnemyEffect = new Sound("/HurtEnemy.wav");
	public static final Sound ShootEffect =  new Sound("/ShootPower.wav");
	private AudioClip clip;
	
	private Sound(String name) {
		try {
			  clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e){}
			
		}
	
		public void play() {
			try {
				new Thread() {
					public void run() {
						clip.play();
					}
					
				}.start();
				
			}catch(Throwable e){}
		}
		
		public void loop() {
			try {
				new Thread() {
					public void run() {
						clip.play();
					}
					
				}.start();
				
			}catch(Throwable e){}
		}
	}

