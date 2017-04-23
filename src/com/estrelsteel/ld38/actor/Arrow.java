package com.estrelsteel.ld38.actor;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Arrow extends Actor {

	public Arrow(String name, Rectangle loc, int animation) {
		super(name, loc);
		getAnimations().add(0, new Animation("green_arrow", 0));
		getAnimations().get(0).setMaxWaitTime(30);
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(0 * 16, 0 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(1 * 16, 0 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(2 * 16, 0 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(1 * 16, 0 * 16, 16, 16)));
		getAnimations().add(1, new Animation("red_arrow", 1));
		getAnimations().get(1).setMaxWaitTime(30);
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(0 * 16, 1 * 16, 16, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(1 * 16, 1 * 16, 16, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(2 * 16, 1 * 16, 16, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(1 * 16, 1 * 16, 16, 16)));
		
		setRunningAnimationNumber(animation);
	}

}
