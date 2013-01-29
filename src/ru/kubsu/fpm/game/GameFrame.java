package ru.kubsu.fpm.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

class GameFrame extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	
	public GameThread thread;
	KeyListener kl = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {					
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			thread.keyReleased(e);					
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			thread.keyPressed(e);	
		}
	};

	public GameFrame(String string) {
		super(string);
		this.addKeyListener(kl);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		thread = new GameThread(this);
		thread.start();
	}

	public void paint(Graphics g) {
		
	}
}
