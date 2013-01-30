package ru.kubsu.fpm.game;

import ru.kubsu.fpm.gameai.*;

class Bot  implements Comparable<Bot>{
	public final static float radius = 0.5f;
	public final static float diameter = radius * 2;
	public final static float maxV = 0.3f;
	public final static float maxA = 0.03f;

	public float x, y, vx, vy;
	public int score;

	Field field;
	BotAI ai;
	private int i;
	private boolean banned = false;
	AiThread aiThread;

	public Bot(Field field, float x, float y, int i, int str) {
		this.field = field;
		this.x = x;
		this.y = y;
		this.i = i;
		this.score = 0;

		switch (str) {
		case 0:
			ai = new BotAI();
			break;
		case 1:
		case 2:
		case 3:
		default:
			ai = new BotAI2();
			break;
		}

		ai.init(i);
		aiThread = new AiThread(ai, i);
		aiThread.setPriority(Thread.MIN_PRIORITY);
		aiThread.start();
	}

	public void upd(FieldInfo f) {
		final long maxTime = 5000;
		final long maxExceptions = 10;

		if (!banned) {
			aiThread.upd(f);
			if (System.currentTimeMillis() - aiThread.lastUpd > maxTime) {
				banned = true;
				System.out.println("Bot " + Integer.toString(i) + " - too slow, banned!");
				aiThread.interrupt();
			}
			if (aiThread.exceptionsCount > maxExceptions) {
				banned = true;
				System.out.println("Bot " + Integer.toString(i) + " - exceptions, banned!");
				aiThread.interrupt();
			}
		}
	}

	public void move() {
		final float fr = 0.97f;
		
		ai.ax = limit(ai.ax, 1);
		ai.ay = limit(ai.ay, 1);

		ai.ax *= maxA;
		ai.ay *= maxA;

		vx += ai.ax;
		vy += ai.ay;
		
		vx = limit(vx, maxV);
		vy = limit(vy, maxV);
		
		x += vx;
		y += vy;
		vx *= fr;
		vy *= fr;
	}

	public String getName() {
		return this.ai.name == null ? "Bot " + Integer.toString(i)
				: this.ai.name;
	}

	private static float limit(float val, float max) {
		float res = val;
		if (res > max)
			res = max;
		if (res < -max)
			res = -max;
		return res;
	}

	@Override
	public int compareTo(Bot o) {
		if (this.score < o.score)
			return 1;
		if (this.score > o.score)
			return -1;
		return 0;
	}
}
