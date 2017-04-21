package com.estrelsteel.ld38;

import java.awt.Graphics2D;
import java.io.IOException;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.tile.TileType;
import com.estrelsteel.engine2.window.WindowSettings;

public class LD38 implements StartListener, StopListener, TickListener, RenderListener {
	
	/*
	 * 
	 * <name>
	 * 
	 * BY: ESTRELSTEEL
	 * LUDUM DARE 38 - COMPO
	 * 
	 * THEME: 
	 * 
	 * (23.04.2017)
	 * 
	 */
	
	private Launcher l;
	private GameFile settings;
	
	public static void main(String[] args) {
		new LD38();
	}
	
	@SuppressWarnings("static-access")
	public LD38() {
		l = new Launcher();
		Rectangle size;
		if(System.getProperty("os.name").startsWith("Windows")) {
			size = QuickRectangle.location(0, 0, 630, 630);
		}
		else {
			size = QuickRectangle.location(0, 0, 640, 640);
		}
		l.getEngine().setWindowSettings(new WindowSettings(size, "LD38 - EstrelSteel", "v1.0a", 0));
		
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		
		
		l.getEngine().development = true;
		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/LD38/LD38";
//		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();
	}

	@Override
	public void start() {
		
		TileType tt = new TileType(-1, "null");
		tt.load(new GameFile(Engine2.devPath + "/res/tiles.txt"), 0);
		
		settings = new GameFile(Engine2.devPath + "/res/settings.txt");
		try {
			settings.setLines(settings.readFile());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		if(settings.getLines().size() > 0) {
//			complete = Boolean.parseBoolean(settings.getLines().get(0));
		}
	}
	
	@Override
	public void stop() {
		if(settings.getLines().size() > 0) {
//			settings.getLines().set(0,  + "");
		}
		try {
			settings.saveFile();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		return ctx;
	}

	@Override
	public void tick() {
		
	}
}
