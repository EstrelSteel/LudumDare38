package com.estrelsteel.ld38.actor;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Mine extends Actor {

	public Mine(String name, Rectangle loc) {
		super(name, loc);
		getAnimations().add(0, new Animation("hidden", 0));
		getAnimations().get(0).setMaxWaitTime(15);
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(0 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(0 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(1 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(2 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(3 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(3 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(2 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(1 * 16, 2 * 16, 16, 16)));
		getAnimations().add(1, new Animation("visible", 1));
		getAnimations().get(1).setMaxWaitTime(15);
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(5 * 16, 2 * 16, 16, 16)));

		setSortable(false);
	}
	
}
