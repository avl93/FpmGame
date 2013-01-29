package ru.kubsu.fpm.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.RenderingHints;

class Field {
	public final static float sizeX = 10f;
	public final static float sizeY = 10f;

	public Bot bots[];
	public Flag flags[];
	public ArrayList<Obstacle> obstacles;

	private FieldInfo fieldInfo;

	public float getBotX(int i) {
		return bots[i].x;
	}

	public float getBotY(int i) {
		return bots[i].y;
	}

	public Field(int b, int f) {
		bots = new Bot[b];
		for (int i = 0; i < b; i++) {
			bots[i] = new Bot(this, (float) Math.random() * sizeX,
					(float) Math.random() * sizeY, i, i);
		}

		flags = new Flag[f];
		for (int i = 0; i < flags.length; i++) {
			flags[i] = new Flag();
		}

		obstacles = new ArrayList<Obstacle>();
		obstacles.add(new ObstCircle(5, 5, 1));
		fieldInfo = new FieldInfo(this);
		/*
		 * bots[0].x=5; bots[0].y=5; bots[0].vx=0.3f; bots[0].vy=0.3f;
		 */
	}

	public void upd(ArrayList<Integer> key, boolean updAI) {
		final double a = Bot.maxA;
		for (int i = 0; i < key.size(); i++) {
			switch (key.get(i)) {
			case 100:
				bots[0].vx += a;
				break;
			case 97:
				bots[0].vx -= a;
				break;
			case 115:
				bots[0].vy += a;
				break;
			case 119:
				bots[0].vy -= a;
				break;
			}
		}
		upd(updAI);
	}

	public void upd(boolean updAI) {
		fieldInfo.upd(this);
		for (int i = 0; i < bots.length; i++) {
			if (updAI)
				bots[i].upd(fieldInfo);
			bots[i].move();
		}
		checkCollisions();
	}

	private void checkCollisions() {
		final float k = 1f;
		final float k2 = k / 2;

		for (int i = 0; i < bots.length; i++) {

			// collisions with the walls...
			if (bots[i].x - Bot.radius < 0) {
				bots[i].x = Bot.diameter - bots[i].x;
				bots[i].vx *= -k;
			}

			if (bots[i].y - Bot.radius < 0) {
				bots[i].y = Bot.diameter - bots[i].y;
				bots[i].vy *= -k;
			}

			if (bots[i].x + Bot.radius > sizeX) {
				bots[i].x = 2 * sizeX - bots[i].x - Bot.diameter;
				bots[i].vx *= -k;
			}

			if (bots[i].y + Bot.radius > sizeY) {
				bots[i].y = 2 * sizeY - bots[i].y - Bot.diameter;
				bots[i].vy *= -k;
			}

			// ... other bots ...
			for (int j = i + 1; j < bots.length; j++) {
				float dx = (bots[i].x - bots[j].x);
				float dy = (bots[i].y - bots[j].y);
				float dist = (float) Math.sqrt(Math.pow(dx, 2)
						+ Math.pow(dy, 2));

				if (dist < Bot.diameter) {
					dx /= dist;
					dy /= dist;
					float depth = Bot.diameter - dist;

					bots[i].vx += dx * depth * k2;
					bots[i].vy += dy * depth * k2;
					bots[j].vx -= dx * depth * k2;
					bots[j].vy -= dy * depth * k2;
				}
			}

			// ... flags ...
			for (int j = 0; j < flags.length; j++) {
				float dx = (bots[i].x - flags[j].x);
				float dy = (bots[i].y - flags[j].y);
				float dist = (float) Math.sqrt(Math.pow(dx, 2)
						+ Math.pow(dy, 2));
				if (dist < Bot.radius + Flag.radius) {
					flags[j] = new Flag();
					bots[i].score++;
				}
			}
			
			//... and obstacles
			for (Obstacle curr : obstacles){
				curr.checkCollision(bots[i]);
			}
		}
	}

	private final static int height = 500;
	private final static int width = (int) (height * sizeX / sizeY);
	private final static int scale = (int) (width / sizeX);

	public static int getX(float x) {
		return (int) (x * scale + 40);
	}

	public static int getY(float y) {
		return (int) (y * scale + 40);
	}

	public static int getScaledX(float x) {
		return (int) (x * scale);
	}

	public static int getScaledY(float y) {
		return (int) (y * scale);
	}

	public void draw(Graphics g) {
		BufferedImage image = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);

		g2.setBackground(Color.white);
		g2.clearRect(0, 0, 1000, 1000);
		g2.setColor(Color.black);
		g2.drawRect(getX(0), getY(0), getScaledX(sizeX), getScaledY(sizeY));

		for (int i = 0; i < bots.length; i++) {
			g2.setColor(bots[i].ai.color);
			g2.drawOval(getX(bots[i].x - Bot.radius), getY(bots[i].y
					- Bot.radius), getScaledX(Bot.radius * 2),
					getScaledY(Bot.radius * 2));
			g2.drawString(bots[i].getName(), getX(bots[i].x), getY(bots[i].y));
			g2.drawString(Integer.toString(bots[i].score), getX(bots[i].x),
					getY(bots[i].y) + 15);

			g2.drawString(
					bots[i].getName() + ":  " + Integer.toString(bots[i].score),
					getX((float) (Field.sizeX + 0.5)), i * 15 + 40);
		}

		g2.setColor(Color.black);
		
		float s = Flag.radius;
		for (int i = 0; i < flags.length; i++) {
			g2.drawLine(getX(flags[i].x - s), getX(flags[i].y - s),
					getX(flags[i].x + s), getX(flags[i].y + s));
			g2.drawLine(getX(flags[i].x - s), getX(flags[i].y + s),
					getX(flags[i].x + s), getX(flags[i].y - s));
		}
		
		for (Obstacle curr:obstacles){
			curr.draw(g2);
		}

		g.drawImage(image, 0, 0, null);
	}
}