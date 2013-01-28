package ru.kubsu.fpm.game;

import java.awt.geom.Line2D;

public class ObstRect extends Obstacle {
	public float x1,y1,x2,y2;

	public ObstRect(float x1, float y1, float x2, float y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public float dist(float x, float y) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isInside(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	public Line2D line(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

}
