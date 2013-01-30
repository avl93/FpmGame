package ru.kubsu.fpm.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class GameThread extends Thread {
	Field field;
	GameFrame frame;
	ArrayList<Integer> key = new ArrayList<Integer>();

	public GameThread(GameFrame frame) {
		frame.thread = this;
		this.frame = frame;
		this.field = new Field(5, 5);
		frame.setSize(Field.getX(Field.sizeX + 3) + 20,
				Field.getY(Field.sizeY) + 20);
		key.clear();
	}

	@Override
	public void run() {
		int ai = 0;

		long t;
		int delay = 1;
		final int fps = 50;
		final int maxDelay = 1000 / fps + 2;
		final int minDelay = 1000 / fps - 2;

		long pt = System.currentTimeMillis();
		while (true) {
			ai++;
			field.upd(key, (ai % 5 == 0));
			Graphics g = frame.getGraphics();
			if (g != null)
				field.draw(g);

			t = System.currentTimeMillis();
			long dt = t - pt;
			if (dt > maxDelay)
				delay--;
			if (dt < minDelay)
				delay++;

			pt = t;

			if (delay > 0) {
				try {
					synchronized (this) {
						this.wait(delay);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (!key.contains((int) e.getKeyChar()))
			key.add((int) e.getKeyChar());
	}

	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < key.size(); i++)
			if (key.get(i) == e.getKeyChar()) {
				key.remove(i);
			}
	}
}
