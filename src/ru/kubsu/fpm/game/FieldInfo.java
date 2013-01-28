package ru.kubsu.fpm.game;

public class FieldInfo {
	BotInfo bots[];
	FlagInfo flags[];
	private float sizeX, sizeY;

	public float getSizeX() {
		return sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public FieldInfo(Field field) {
		if (field != null) {
			bots = new BotInfo[field.bots.length];
			for (int i = 0; i < field.bots.length; i++) {
				bots[i] = new BotInfo(field.getBotX(i), field.getBotY(i));
			}
			
			flags = new FlagInfo[field.flags.length];
			for (int i = 0; i < field.flags.length; i++) {
				flags[i] = new FlagInfo(field.flags[i].x, field.flags[i].y);
			}
			
			this.sizeX=Field.sizeX;
			this.sizeY=Field.sizeY;
		}
	}

	public void upd(Field field) {
		if (field != null) {
			for (int i = 0; i < field.bots.length; i++) {
				bots[i].setX(field.getBotX(i));
				bots[i].setY(field.getBotY(i));
			}
			
			flags = new FlagInfo[field.flags.length];
			for (int i = 0; i < field.flags.length; i++) {
				flags[i] = new FlagInfo(field.flags[i].x, field.flags[i].y);
			}
		}
	}
	
	public int getBotsN(){
		return bots.length;
	}
	
	public float getBotX(int i){
		return bots[i].getX();
	}
	
	public float getBotY(int i){
		return bots[i].getY();
	}
	
	public int getFlagssN(){
		return flags.length;
	}
	
	public float getFlagX(int i){
		return flags[i].getX();
	}
	
	public float getFlagY(int i){
		return flags[i].getY();
	}
}

class BotInfo {
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public BotInfo(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	float x, y;
}

class FlagInfo{
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public FlagInfo(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	float x, y;
}
