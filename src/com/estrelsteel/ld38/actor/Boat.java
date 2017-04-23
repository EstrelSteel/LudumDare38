package com.estrelsteel.ld38.actor;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Boat extends Actor {

	private double walkspeed;
	
	public Boat(String name, Rectangle loc) {
		super(name, loc);
		
		walkspeed = 5;
		
		int ani_time = 30;
		
		getAnimations().add(0, new Animation("left", 0));
		getAnimations().get(0).setMaxWaitTime(ani_time);
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(0 * 32, 0 * 48, 32, 35)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(1 * 32, 0 * 48, 32, 35)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(2 * 32, 0 * 48, 32, 35)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(3 * 32, 0 * 48, 32, 35)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(4 * 32, 0 * 48, 32, 35)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(5 * 32, 0 * 48, 32, 35)));
		getAnimations().add(1, new Animation("right", 1));
		getAnimations().get(1).setMaxWaitTime(ani_time);
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(0 * 32, 1 * 48, 32, 35)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(1 * 32, 1 * 48, 32, 35)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(2 * 32, 1 * 48, 32, 35)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(3 * 32, 1 * 48, 32, 35)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(4 * 32, 1 * 48, 32, 35)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(5 * 32, 1 * 48, 32, 35)));
		getAnimations().add(2, new Animation("up", 2));
		getAnimations().get(2).setMaxWaitTime(ani_time);
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(0 * 32, 2 * 48, 32, 35)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(1 * 32, 2 * 48, 32, 35)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(2 * 32, 2 * 48, 32, 35)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(3 * 32, 2 * 48, 32, 35)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(4 * 32, 2 * 48, 32, 35)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(5 * 32, 2 * 48, 32, 35)));
		getAnimations().add(3, new Animation("down", 3));
		getAnimations().get(3).setMaxWaitTime(ani_time);
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(0 * 32, 3 * 48, 32, 35)));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(1 * 32, 3 * 48, 32, 35)));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(2 * 32, 3 * 48, 32, 35)));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(3 * 32, 3 * 48, 32, 35)));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(4 * 32, 3 * 48, 32, 35)));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/boat.png", QuickRectangle.location(5 * 32, 3 * 48, 32, 35)));
	}
	
	public double getWalkspeed() {
		return walkspeed;
	}

}
