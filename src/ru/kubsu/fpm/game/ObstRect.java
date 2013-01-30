package ru.kubsu.fpm.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class ObstRect extends Obstacle {
	public float x1, y1, x2, y2;
	private float width, height;

	public ObstRect(float x1, float y1, float x2, float y2) {
		super();
		if (x2 > x1 && y2 > y1) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			width = x2 - x1;
			height = y2 - y1;
		} else {
			System.err.println("Error: incorrect rect");
		}
	}

	@Override
	public float dist(float x, float y) {
		if (isInside(x, y))
			return 0;
		else {
			if (x > x1 && x < x2) {
				if (y < y1)
					return y1 - y;
				if (y > y2)
					return y - y2;
			} else if (y > y1 && y < y2) {
				if (x < x1)
					return x1 - x;
				if (x > x2)
					return x - x2;
			} else {
				float d1 = (float) Point.distance(x1, y1, x, y);
				float d2 = (float) Point.distance(x2, y1, x, y);
				float d3 = (float) Point.distance(x1, y2, x, y);
				float d4 = (float) Point.distance(x2, y2, x, y);
				return Math.min(Math.min(d1, d2), Math.min(d3, d4));
			}
		}
		System.err.println("Error: wtf???");
		return 0;
	}

	@Override
	public boolean isInside(float x, float y) {
		return x1 <= x && x <= x2 && y1 <= y && y <= y2;
	}

	@Override
	public Line2D line(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkCollision(Bot bot) {
		float distance = dist(bot.x, bot.y);
		if (distance < Bot.radius) {
			if (bot.x < x1) {
				bot.x -= (Bot.radius-distance)*2;
				bot.vx *= -1;
			}
			if (bot.x > x2) {
				bot.x += (Bot.radius-distance)*2;
				bot.vx *= -1;
			}

			if (bot.y < y1) {
				bot.y -= (Bot.radius-distance)*2;
				bot.vy *= -1;
			}

			if (bot.y > y2) {
				bot.y += (Bot.radius-distance)*2;
				bot.vy *= -1;
			}
		}

	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawRect(Field.getX(x1), Field.getY(y1), Field.getScaledX(width),
				Field.getScaledY(height));

	}
}
