package ru.kubsu.fpm.game;

class Flag {
	public float x, y;
	
	public static float radius = 0.2f;

	public Flag(float x, float y) {
		this.x=x;
		this.y=y;
	}

	public Flag() {
		this.x=(float) (Math.random()*Field.sizeX);
		this.y=(float) (Math.random()*Field.sizeY);
	}
}
