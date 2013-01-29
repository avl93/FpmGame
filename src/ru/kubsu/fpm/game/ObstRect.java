package ru.kubsu.fpm.game;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class ObstRect extends Obstacle {
	public float x1, y1, x2, y2;

	public ObstRect(float x1, float y1, float x2, float y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public float dist(float x, float y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInside(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Line2D line(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkCollision(Bot bot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub

	}
}
