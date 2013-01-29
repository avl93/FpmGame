package ru.kubsu.fpm.game;

import javax.swing.SwingUtilities;

class FpmGame implements Runnable {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new FpmGame());
	}

	Thread t;

	@Override
	public void run() {
		@SuppressWarnings("unused")
		GameFrame frame = new GameFrame("FpmGame");
	}
}