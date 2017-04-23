package com.estrelsteel.ld38.actor;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.collide.CollideArea;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class WhirlPool extends Actor {

	public WhirlPool(String name, Rectangle loc) {
		super(name, loc);
		getAnimations().add(0, new Animation("type1", 0));
		getAnimations().get(0).setMaxWaitTime(8);
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(2 * 16, 3 * 16, 32, 32)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(4 * 16, 3 * 16, 32, 32)));
		getAnimations().add(1, new Animation("type2", 1));
		getAnimations().get(1).setMaxWaitTime(8);
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(2 * 16, 5 * 16, 32, 32)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(4 * 16, 5 * 16, 32, 32)));
		getAnimations().add(2, new Animation("hidden", 2));
		getAnimations().get(2).setMaxWaitTime(8);
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(0 * 16, 5 * 16, 32, 32)));
		
		setRunningAnimationNumber(1);
		setSortable(false);
	}

	public boolean canEscape(Boat boat) {
		CollideArea ca = new RectangleCollideArea(boat.getLocation());
		return ca.checkCollision(getLocation());
	}
}
