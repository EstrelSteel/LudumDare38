package com.estrelsteel.ld38.actor;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Flag extends Actor {

	public Flag(String name, Rectangle loc, int animation) {
		super(name, loc);
		getAnimations().add(0, new Animation("flag", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(3 * 16, 1 * 16, 16, 16)));
		getAnimations().add(1, new Animation("flag_buoy_still", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(4 * 16, 1 * 16, 16, 16)));
		getAnimations().add(2, new Animation("flag_buoy", 2));
		getAnimations().get(2).setMaxWaitTime(60);
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(4 * 16, 1 * 16, 16, 17)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(5 * 16, 1 * 16, 16, 17)));
		getAnimations().add(3, new Animation("buoy", 3));
		getAnimations().get(3).setMaxWaitTime(60);
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(3 * 16, 0 * 16, 16, 16)));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(4 * 16, 0 * 16, 16, 16)));
		
		setRunningAnimationNumber(animation);
		
	}

}
