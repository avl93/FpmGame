package ru.kubsu.fpm.game;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class FpmGame implements Runnable {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new FpmGame());
	}

	Thread t;

	@Override
	public void run() {
		GameFrame f = new GameFrame("FpmGame");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// f.setSize(500, 500);
		f.setVisible(true);

		t = new Thread(new GameThread(f));
		t.start();
	}
}