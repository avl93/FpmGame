package ru.kubsu.fpm.gameai;

import java.awt.Color;

import ru.kubsu.fpm.game.*;

public class BotAI2 extends BotAI {
	public void init(int i){
		this.color = new Color((int)(Math.random()*150),(int)(Math.random()*150),(int)(Math.random()*150));
	}
	
	public void upd(FieldInfo f, int i) {
		float dx = f.getFlagX(i) - f.getBotX(i);
		float dy = f.getFlagY(i) - f.getBotY(i);
		if (Math.abs(dx) > 0.5)
			ax = dx;
		else
			ax=0;
		if (Math.abs(dy) > 0.5)
			ay = dy;
		else
			ay=0;
			
		while (i==10000){};
	}
}
