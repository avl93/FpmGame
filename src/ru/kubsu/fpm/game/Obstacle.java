package ru.kubsu.fpm.game;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public abstract class Obstacle {
	public Obstacle() {
	}

	public abstract float dist(float x, float y);
	public abstract boolean isInside(float x, float y);
	public abstract Line2D line(float x, float y);
	public abstract void checkCollision(Bot bot);
	public abstract void draw(Graphics2D g2);
}
