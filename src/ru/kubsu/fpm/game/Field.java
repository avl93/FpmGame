package ru.kubsu.fpm.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

	public Field(int botsCount, int flagsCount) {
		bots = new Bot[botsCount];
		for (int i = 0; i < botsCount; i++) {
			bots[i] = new Bot(this, (float) Math.random() * sizeX,
					(float) Math.random() * sizeY, i, i);
		}

		obstacles = new ArrayList<Obstacle>();

		try {
			File file = new File("map.txt");
			Scanner scan = new Scanner(file);
			String str = new String();
			try {
				while (file.canRead()) {
					str += scan.nextLine();
				}
			} catch (Exception e) {

			}
			scan.close();
			Xml xml = new Xml(str);
			for (int i = 0; i < xml.tags.size(); i++) {
				String name = xml.getName(i);
				switch (name) {
				case "circle":
					float cx = xml.getFloatAttr(i, "cx");
					float cy = xml.getFloatAttr(i, "cy");
					float radius = xml.getFloatAttr(i, "radius");
					obstacles.add(new ObstCircle(cx, cy, radius));
					break;
				case "rect":
					float x1 = xml.getFloatAttr(i, "x1");
					float y1 = xml.getFloatAttr(i, "y1");
					float x2 = xml.getFloatAttr(i, "x2");
					float y2 = xml.getFloatAttr(i, "y2");
					obstacles.add(new ObstRect(x1, y1, x2, y2));
					break;
				default:
					System.err.println("Error: unknown obstacle type: " + name);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("file not found!");
			e.printStackTrace();
		}

		flags = new Flag[flagsCount];
		for (int i = 0; i < flags.length; i++) {
			newFlag(i);
		}

		fieldInfo = new FieldInfo(this);
	}
	
	public void newFlag(int i){
		do {
			flags[i] = new Flag();
		} while (isBlocked(flags[i].x, flags[i].y));
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

	public boolean isBlocked(float x, float y) {
		boolean res = false;
		for (Obstacle curr : obstacles) {
			res = res || curr.isInside(x, y);
		}
		return res;
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
				float dist = (float) Point.distance(bots[i].x, bots[i].y,
						bots[j].x, bots[j].y);
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
				float dist = (float) Point.distance(bots[i].x, bots[i].y,
						flags[j].x, flags[j].y);
				if (dist < Bot.radius + Flag.radius) {
					newFlag(j);
					bots[i].score++;
				}
			}

			// ... and obstacles
			for (Obstacle curr : obstacles) {
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

		for (Obstacle curr : obstacles) {
			curr.draw(g2);
		}

		g.drawImage(image, 0, 0, null);
	}
}