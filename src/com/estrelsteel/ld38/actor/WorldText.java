package com.estrelsteel.ld38.actor;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class WorldText extends Actor {

	public WorldText(String name, Rectangle loc, int animation) {
		super(name, loc);
		getAnimations().add(0, new Animation("movement", 0));
		getAnimations().get(0).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial1.png"));
		getAnimations().add(1, new Animation("collect", 1));
		getAnimations().get(1).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial2.png"));
		getAnimations().add(2, new Animation("mines", 2));
		getAnimations().get(2).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial3.png"));
		getAnimations().add(3, new Animation("score", 3));
		getAnimations().get(3).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial4.png"));
		getAnimations().add(4, new Animation("whirlpool", 4));
		getAnimations().get(4).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial5.png"));
		getAnimations().add(5, new Animation("flags", 5));
		getAnimations().get(5).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial6.png"));
		
		setRunningAnimationNumber(animation);
		setSortable(false);
	}
	
}
