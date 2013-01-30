package ru.kubsu.fpm.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class ObstCircle extends Obstacle {
	public float cx, cy;
	public float radius;

	public ObstCircle(float cx, float cy, float radius) {
		this.cx = cx;
		this.cy = cy;
		this.radius = radius;
	}

	@Override
	public float dist(float x, float y) {
		return (float) (Point.distance(cx, cy, x, y) - this.radius);
	}

	@Override
	public boolean isInside(float x, float y) {
		return Point.distance(cx, cy, x, y) < radius;
	}

	@Override
	public Line2D line(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkCollision(Bot bot) {
		float dx = (cx - bot.x);
		float dy = (cy - bot.y);
		float dist = (float) Point.distance(cx, cy, bot.x, bot.y);

		if (dist < Bot.radius + this.radius) {
			dx /= dist;
			dy /= dist;
			float depth = Bot.radius + this.radius - dist;
			
			//bot.x -= depth * dx;
			//bot.y -= depth * dy;

			bot.vx -= dx * depth;
			bot.vy -= dy * depth;
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawOval(Field.getX(this.cx - this.radius),
				Field.getY(this.cy - this.radius),
				Field.getScaledX(this.radius * 2),
				Field.getScaledY(this.radius * 2));
	}

}
