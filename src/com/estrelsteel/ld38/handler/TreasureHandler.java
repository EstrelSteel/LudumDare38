package com.estrelsteel.ld38.handler;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.AbstractedRectangle;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.world.World;
import com.estrelsteel.ld38.actor.Boat;
import com.estrelsteel.ld38.actor.Flag;
import com.estrelsteel.ld38.actor.Mine;
import com.estrelsteel.ld38.actor.Treasure;

public class TreasureHandler {
	
	private AbstractedRectangle range;
	private int tCount;
	private int mCount;
	private World w;
	private Rectangle tempLoc;
	private double mineChance;
	
	public TreasureHandler(AbstractedRectangle range, World w) {
		this.range = range;
		this.w = w;
		this.tCount = 0;
		this.mCount = 0;
		this.mineChance = 0.1;
	}
	
	public AbstractedRectangle getRange() {
		return range;
	}
	
	public World getWorld() {
		return w;
	}
	
	public double getMineChance() {
		return mineChance;
	}
	
	public Actor generateTreasure() {
		int x = (int) (Math.random() * (range.getWidth() - 64) + range.getX());
		int y = (int) (Math.random() * (range.getHeight() - 64) + range.getY());
		tempLoc = QuickRectangle.location(x, y, 64, 64);
		Actor t;
		if(Math.random() < mineChance) {
			t = new Mine("mine" + mCount, tempLoc);
			mCount++;
		}
		else {
			t = new Treasure("treasure" + tCount, tempLoc);
			tCount++;
		}
		if(w.checkCollideIgnoreDeclaration(new RectangleCollideArea(tempLoc), t) == null) {
			return t;
		}
		return generateTreasure();
	}
	
	public Actor fishTreasure(AbstractedRectangle loc) {
		Actor t;
		for(int i = 0; i < w.getObjects().size(); i++) {
			if(w.getObjects().get(i) instanceof Treasure || w.getObjects().get(i) instanceof Mine || w.getObjects().get(i) instanceof Flag) {
				t = (Actor) w.getObjects().get(i);
				if(w.checkCollideIgnoreDeclaration(new RectangleCollideArea(t.getLocation()), t) instanceof Boat) {
					if(w.getObjects().get(i) instanceof Flag) {
						w.getObjects().remove(i);
						i--;
					}
					else {
						w.getObjects().remove(i);
						i--;
						return t;
					}
				}
				else if(new RectangleCollideArea(t.getLocation()).checkCollision(loc)) {
					if(w.getObjects().get(i) instanceof Flag) {
						w.getObjects().remove(i);
						i--;
					}
					else {
						w.getObjects().remove(i);
						i--;
						return t;
					}
				}
			}
		}
		return null;
	}
	
	public void setRange(AbstractedRectangle range) {
		this.range = range;
	}
	
	public void setWorld(World w) {
		this.w = w;
	}
	
	public void setMineChance(double mineChance) {
		this.mineChance = mineChance;
	}
}
