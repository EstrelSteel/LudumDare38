package com.estrelsteel.ld38;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.actor.Camera;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.Point2;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.collide.PerspectiveRectangleArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.sound.SFX;
import com.estrelsteel.engine2.tile.Tile;
import com.estrelsteel.engine2.tile.TileType;
import com.estrelsteel.engine2.window.WindowSettings;
import com.estrelsteel.engine2.world.World;
import com.estrelsteel.ld38.GameState.SubState;
import com.estrelsteel.ld38.actor.Arrow;
import com.estrelsteel.ld38.actor.Boat;
import com.estrelsteel.ld38.actor.Flag;
import com.estrelsteel.ld38.actor.Mine;
import com.estrelsteel.ld38.actor.Treasure;
import com.estrelsteel.ld38.actor.WhirlPool;
import com.estrelsteel.ld38.actor.WorldText;
import com.estrelsteel.ld38.handler.KeyboardHandler;
import com.estrelsteel.ld38.handler.MouseHandler;
import com.estrelsteel.ld38.handler.TreasureHandler;
import com.estrelsteel.ld38.level.Level;

public class LD38 implements StartListener, StopListener, TickListener, RenderListener {
	
	/*
	 * 
	 * TREASURE HUNTER
	 * 
	 * BY: ESTRELSTEEL
	 * LUDUM DARE 38 - COMPO
	 * 
	 * THEME: A Small World
	 * 
	 * (23.04.2017)
	 * 
	 */
	
	//engine
	private Launcher l;
	private GameFile settings;
	private KeyboardHandler key;
	private MouseHandler mouse;
	
	//world and cam
	private World w;
	private Boat boat;
	private GameState gs;
	private SubState ss;
	private WhirlPool wp;
	private AbstractedPoint boat_centre;
	private Rectangle npLoc;
	private Tile water;
	private TreasureHandler th;
	//game files
	private GameFile levelFile;
	private Level lvl;
	private int activeLvl;
	private int maxLvl;
	private Image title;
	//timers
	private int waterLvl;
	private int shrinkTime;
	private long lastReduction;
	private int genTime;
	private long lastGeneration;
	private long startGame;
	private int secLeft;
	
	private double score;
	private double minScore;
	private boolean endless;
	private boolean canEndless;
	private double highscore;
	private int round;
	
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
		key = new KeyboardHandler(this);
		mouse = new MouseHandler();
		l.getEngine().setWindowSettings(new WindowSettings(size, "Treasure Hunter - EstrelSteel", "v1.0a", 0));
		
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		l.getEngine().addKeyListener(key);
		l.getEngine().addMouseListener(mouse);
		l.getEngine().addMouseMotionListener(mouse);
		
		
		l.getEngine().development = true;
//		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/LD38/LD38";
		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();
	}

	@Override
	public void start() {
		TileType tt = new TileType(-1, "null");
		tt.load(new GameFile(Engine2.devPath + "/res/tiles.txt"), 0);

		new SFX().load(new GameFile(Engine2.devPath + "/res/sounds.txt"), 0);
		
		settings = new GameFile(Engine2.devPath + "/res/settings.txt");
		try {
			settings.setLines(settings.readFile());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		if(settings.getLines().size() > 0) {
			activeLvl = Integer.parseInt(settings.getLines().get(0));
			score = Double.parseDouble(settings.getLines().get(1));
			canEndless = Boolean.parseBoolean(settings.getLines().get(2));
			maxLvl = Integer.parseInt(settings.getLines().get(3));
			highscore = Double.parseDouble(settings.getLines().get(4));
		}
		endless = false;
		round = 1;
		if(activeLvl == 0) {
			if(canEndless) {
				title = new Image(Engine2.devPath + "/res/img/title3.png");
			}
			else {
				title = new Image(Engine2.devPath + "/res/img/title0.png");
			}
		}
		else if(activeLvl > 0 && !canEndless) {
			title = new Image(Engine2.devPath + "/res/img/title1.png");
		}
		else {
			title = new Image(Engine2.devPath + "/res/img/title2.png");
		}
		
		gs = GameState.MENU;
		ss = SubState.MAIN;
	}
	
	public void menuInteract(int key) {
		if(gs == GameState.MENU && ss == SubState.MAIN) {
			if(key == 1) {
				endless = false;
				try {
					loadLevel(activeLvl);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(key == 2) {
				activeLvl = 0;
				score = 0;
				try {
					loadLevel(activeLvl);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(key == 3) {
				if(canEndless) {
					score = 0;
					endless = true;
					try {
						loadLevel(-98);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else {
			if(key == -1) {
				gs = GameState.MENU;
				ss = SubState.MAIN;
			}
		}
	}
	
	public void loadLevel(int lvlnum) throws IOException {
		w = new World(new PixelGrid());
		
		boat = new Boat("player", QuickRectangle.location(320, 320, 64, 64));
		wp = new WhirlPool("whirlpool", QuickRectangle.location(640, 640, 128, 128));
		th = new TreasureHandler(QuickRectangle.location(5 * 64, 5 * 64, 19 * 64, 15 * 64), w);
		
		levelFile = new GameFile(Engine2.devPath + "/res/lvl/lvl" + lvlnum + ".txt");
		levelFile.setLines(levelFile.readFile());
		lvl = new Level(w);
		w = lvl.load(levelFile,  0);
		shrinkTime = lvl.getShrinkTime();
		lastReduction = System.currentTimeMillis();
		genTime = lvl.getGenerationTime();
		lastGeneration = System.currentTimeMillis();
		startGame = System.currentTimeMillis();
		score = score - minScore;
		if(score < 0) {
			score = 0;
		}
		minScore = lvl.getMinScore();
		th.setMineChance(lvl.getMineChance());

		wp.setRunningAnimationNumber(1);
		
		w.getObjects().add(wp);
		w.getObjects().add(boat);
		w.getCameras().add(0, new Camera("player_cam", new Point2(0, 0, new PixelGrid())));
		w.setMainCamera(w.getCameras().get(0));
		waterLvl = 14;
		water = new Tile(TileType.types.get(waterLvl), QuickRectangle.location(0, 0, 640, 640));
		water.getRunningAnimation().getCurrentImage().loadImage();
		
		secLeft = (int) (((shrinkTime * 4) - (System.currentTimeMillis() - startGame)) / 1000);
		
		gs = GameState.PLAYING;
		ss = SubState.PLAYING;
		
		if(lvlnum == 0) {
			loadTutorial(1);
		}
		
		if(endless) {
			System.out.println((1.0/7.0) * Math.pow(1.5, round) + 5);
			minScore = Math.floor((1.0/7.0) * Math.pow(1.5, round) + 5);
			shrinkTime = (2 * round + 10) * 1000;
			gs = GameState.MENU;
			ss = SubState.ENDLESS_START;
		}
	}
	
	public void loadTutorial(int num) {
		if(num == 1) {
			ss = SubState.TUTORIAL_1;
			w.getObjects().add(0, new WorldText("tut1", QuickRectangle.location(340, 480, 184, 44), 0));
			w.getObjects().add(0, new WorldText("tut2", QuickRectangle.location(640, 480, 294, 63), 1));
			w.getObjects().add(new Arrow("treasure_arrow", QuickRectangle.location(750, 310, 64, 64), 0));
			w.getObjects().add(0, new Treasure("tut_treasure0", QuickRectangle.location(750, 380, 64, 64)));
			w.getObjects().add(0, new WorldText("tut3", QuickRectangle.location(1000, 480, 195, 63), 2));
			w.getObjects().add(0, new WorldText("tut6", QuickRectangle.location(990, 550, 224, 82), 5));
			w.getObjects().add(0, new Mine("tut_mine0", QuickRectangle.location(1060, 380, 64, 64)));
			w.getObjects().add(new Arrow("mine_arrow", QuickRectangle.location(1060, 310, 64, 64), 1));
			w.getObjects().add(0, new WorldText("tut4", QuickRectangle.location(1250, 480, 254, 82), 3));
			w.getObjects().add(0, new WorldText("tut5", QuickRectangle.location(1600, 480, 204, 82), 4));
			w.getObjects().add(new Arrow("wp_arrow", QuickRectangle.location(1672, 310, 64, 64), 0));
			wp.setLocation(QuickRectangle.location(1640, 340, 128, 128));
			
//			w.getObjects().add(0, new Flag("flag", QuickRectangle.location(640, 640, 64, 64), 0));
//			w.getObjects().add(0, new Flag("flag_buoy_still", QuickRectangle.location(640 + (80 * 1), 640, 64, 64), 1));
//			w.getObjects().add(0, new Flag("flag_buoy", QuickRectangle.location(640 + (80 * 2), 640, 64, 64), 2));
//			w.getObjects().add(0, new Flag("buoy", QuickRectangle.location(640 + (80 * 3), 640, 64, 64), 3));
		}
	}

	@Override
	public void stop() {
		if(activeLvl < 0) {
			activeLvl = 0;
		}
		if(settings.getLines().size() > 0) {
			settings.getLines().set(0, activeLvl + "");
			settings.getLines().set(2, canEndless + "");
			settings.getLines().set(3, maxLvl + "");
			settings.getLines().set(4, highscore + "");
		}
		try {
			settings.saveFile();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void interact() {
		if(gs == GameState.PLAYING) {
			Actor t = th.fishTreasure(boat.getLocation());
			if(t != null) {
				if(t instanceof Treasure) {
					score++;
					SFX.sounds.get(1).play();
					if(ss == SubState.TUTORIAL_1) {
						for(int i = 0; i < w.getObjects().size(); i++) {
							if(w.getObjects().get(i) instanceof Arrow) {
								if(((Arrow) w.getObjects().get(i)).getName().equalsIgnoreCase("treasure_arrow")) {
									w.getObjects().remove(i);
									i--;
								}
							}
						}
					}
				}
				else if(t instanceof Mine) {
					score = score - 5;
					SFX.sounds.get(0).play();
					if(ss == SubState.TUTORIAL_1) {
						for(int i = 0; i < w.getObjects().size(); i++) {
							if(w.getObjects().get(i) instanceof Arrow) {
								if(((Arrow) w.getObjects().get(i)).getName().equalsIgnoreCase("mine_arrow")) {
									w.getObjects().remove(i);
									i--;
								}
							}
						}
						gs = GameState.MENU;
						ss = SubState.FAIL_DIRECTIONS;
					}
				}
			}
			else if(wp.canEscape(boat)) {
				gs = GameState.MENU;
				if(score >= minScore) {
					ss = SubState.ESCAPE;
				}
				else {
					ss = SubState.FAIL_SCORE;
				}
			}
		}
		else if(gs == GameState.MENU) {
			if(ss == SubState.ESCAPE) {
				if(!endless) {
					try {
						activeLvl++;
						if(activeLvl >= maxLvl) {
							activeLvl = -99;
							loadLevel(activeLvl);
							gs = GameState.MENU;
							ss = SubState.CREDITS;
						}
						else {
							loadLevel(activeLvl);
						}
						if(settings.getLines().size() > 0) {
							settings.getLines().set(1, score + "");
						}
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					round++;
//					lastReduction = System.currentTimeMillis();
//					genTime = lvl.getGenerationTime();
//					lastGeneration = System.currentTimeMillis();
//					startGame = System.currentTimeMillis();
					minScore = Math.floor((1.0/7.0) * Math.pow(1.5, round) + 5);
					shrinkTime = (2 * round + 10) * 1000;
					gs = GameState.MENU;
					ss = SubState.ENDLESS_START;
				}
			}
			else if(ss == SubState.ENDLESS_START) {
				try {
					loadLevel(-98);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				gs = GameState.PLAYING;
				ss = SubState.PLAYING;
			}
			else if(ss == SubState.FAIL_NO_WATER || ss == SubState.FAIL_SCORE || ss == SubState.FAIL_DIRECTIONS) {
				if(!endless) {
					try {
						loadLevel(activeLvl);
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					if(score > highscore) {
						highscore = score;
					}
					gs = GameState.MENU;
					ss = SubState.MAIN;
				}
			}
			else if(ss == SubState.CREDITS) {
				ss = SubState.MAIN;
				canEndless = true;
			}
		}
	}
	
	public void placeFlag() {
		if(gs == GameState.PLAYING && (ss == SubState.PLAYING || ss == SubState.TUTORIAL_1)) {
			w.getObjects().add(0, new Flag("flag", QuickRectangle.location(boat.getLocation().getX(), boat.getLocation().getY(), 64, 64), 2));
			SFX.sounds.get(2).play();
		}
	}
	
	@Override
	public Graphics2D render(Graphics2D ctx) {
		if(ss != SubState.MAIN) {
			ctx.drawImage(water.getRunningAnimation().getCurrentImage().getImage(), 0, 0, 640, 640, null);
			
			boat_centre = PointMaths.getCentre(boat.getLocation());
			w.getMainCamera().getLocation().setX(boat_centre.getX() - (l.getEngine().getWindowSettings().getRectangle().getWidth() / 2));
			w.getMainCamera().getLocation().setY(boat_centre.getY() - (l.getEngine().getWindowSettings().getRectangle().getHeight() / 2));
			ctx = w.renderWorld(ctx);
			
//			gs = GameState.MENU;
//			ss = SubState.ENDLESS_START;
		}
		if((gs == GameState.PLAYING) && ss != SubState.TUTORIAL_1 && activeLvl != -99) {
			ctx.setFont(new Font("Menlo", Font.BOLD, 12));
			ctx.setColor(Color.BLACK);
			ctx.fillRect(18, 18, 604, 34);
			ctx.setColor(Color.RED);
			ctx.fillRect(20, 20, 600, 30);
			ctx.setColor(Color.GREEN);
			ctx.fillRect(20, 20, (int) (600 - (600 * ((double) (System.currentTimeMillis() - startGame) / (shrinkTime * 4)))), 30);
			ctx.setColor(Color.BLACK);
			if(secLeft > 1) { 
				ctx.drawString(secLeft + " seconds remaining" , 25, 40);
			}
			else if(secLeft == 1) {
				ctx.drawString("1 second remaining" , 25, 40);
			}
			else {
				ctx.drawString("0 seconds remaining" , 25, 40);
			}
			ctx.setColor(Color.WHITE);
			ctx.fillRect(20, 570, 100, 50);
			ctx.setColor(Color.BLACK);
			ctx.drawString("MinScore: " + ((int) minScore), 25, 590);
			ctx.drawString("Score: " + ((int) score), 25, 610);
			if(endless) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(500, 570, 120, 50);
//				ctx.fillRect(500, 590, 120, 30);
				ctx.setColor(Color.BLACK);
				ctx.drawString("Best round: " + ((int) highscore), 505, 590);
				ctx.drawString("Round: " + ((int) round), 505, 610);
			}
			else if(activeLvl == 1) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(20, 55, 470, 40);
				ctx.fillRect(140, 580, 390, 40);
//				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.setColor(Color.BLACK);
				ctx.drawString("This meter indicates how long until all the water is gone.", 25, 70);
				ctx.drawString("Make sure to leave through the whirlpool before this reaches zero.", 25, 85 );
				ctx.drawString("Make sure you get the minimum score before you escape.", 145, 595);
				ctx.drawString("Any extra score carries over to the next level.", 145, 610);
			}
		}
		else if(gs == GameState.MENU) {
			if(ss == SubState.CREDITS) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(25, 166, 595, 90);
				ctx.fillRect(200, 415, 255, 60);
				ctx.fillRect(195, 515, 265, 60);
				ctx.setColor(Color.BLACK);
				ctx.setFont(new Font("Menlo", Font.BOLD, 64));
				ctx.drawString("Treasure Hunter", 30, 235);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("By: EstrelSteel", 250, 430);
				ctx.drawString("For: Ludum Dare 38 Compo", 210, 450);
				ctx.drawString("Theme: A Small World", 225, 470);
				ctx.drawString("Endless mode unlocked.", 215, 530);
				ctx.drawString("Press [SPACE] to continue", 200, 560);
			}
			else if(ss == SubState.ESCAPE) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(177, 166, 285, 90);
				ctx.fillRect(260, 410, 115, 32);
				ctx.fillRect(190, 440, 260, 32);
				ctx.setColor(Color.BLACK);
				ctx.setFont(new Font("Menlo", Font.BOLD, 64));
				ctx.drawString("Escaped", 182, 235);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("Score: " + (int) (score), 275, 430);
				ctx.drawString("Press [SPACE] to continue", 195, 460);
			}
			else if(ss == SubState.ENDLESS_START) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(165, 166, 320, 90);
				ctx.fillRect(260, 410, 115, 32);
				ctx.fillRect(250, 445, 130, 32);
				ctx.fillRect(190, 480, 260, 32);
				ctx.setColor(Color.BLACK);
				ctx.setFont(new Font("Menlo", Font.BOLD, 64));
				String r = round + "";
				if(round / 10 == 0) {
					r = 0 + "" + round;
				}
				ctx.drawString("Round " + r, 170, 225);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("Start", 300, 250);
				ctx.drawString("Score: " + (int) (score), 275, 430);
				ctx.drawString("MinScore: " + (int) (minScore), 255, 465);
				ctx.drawString("Press [SPACE] to continue", 195, 500);
			}
			else if(ss == SubState.FAIL_NO_WATER) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(177, 166, 285, 90);
				ctx.fillRect(260, 410, 115, 32);
				ctx.setColor(Color.BLACK);
				ctx.setFont(new Font("Menlo", Font.BOLD, 64));
				ctx.drawString("Failed", 200, 225);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("No Water Remaining", 225, 245);
				ctx.drawString("Score: " + (int) (score), 270, 430);
				if(!endless) {
					ctx.setColor(Color.WHITE);
					ctx.fillRect(190, 440, 260, 32);
					ctx.setColor(Color.BLACK);
					ctx.drawString("Press [SPACE] to retry", 205, 460);
				}
				else {
					ctx.setColor(Color.WHITE);
					ctx.fillRect(190, 460, 260, 32);
					ctx.fillRect(260, 430, 115, 32);
					ctx.setColor(Color.BLACK);
					ctx.drawString("Round: " + (int) (round), 270, 450);
					ctx.drawString("Press [SPACE] to quit", 215, 480);	
				}
				
			}
			else if(ss == SubState.FAIL_SCORE) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(177, 166, 285, 90);
				ctx.fillRect(260, 410, 115, 32);
//				ctx.fillRect(190, 440, 260, 32);
				ctx.setColor(Color.BLACK);
				ctx.setFont(new Font("Menlo", Font.BOLD, 64));
				ctx.drawString("Failed", 200, 225);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("Not Enough Score", 235, 245);
				ctx.drawString("Score: " + (int) (score), 270, 430);
//				ctx.drawString("Press [SPACE] to retry", 205, 460);
				if(!endless) {
					ctx.setColor(Color.WHITE);
					ctx.fillRect(190, 440, 260, 32);
					ctx.setColor(Color.BLACK);
					ctx.drawString("Press [SPACE] to retry", 205, 460);
				}
				else {
					ctx.setColor(Color.WHITE);
					ctx.fillRect(190, 460, 260, 32);
					ctx.fillRect(260, 430, 115, 32);
					ctx.setColor(Color.BLACK);
					ctx.drawString("Round: " + (int) (round), 270, 450);
					ctx.drawString("Press [SPACE] to quit", 215, 480);	
				}
			}
			else if(ss == SubState.FAIL_DIRECTIONS) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(177, 166, 285, 90);
				ctx.fillRect(260, 410, 115, 32);
//				ctx.fillRect(190, 440, 260, 32);
				ctx.setColor(Color.BLACK);
				ctx.setFont(new Font("Menlo", Font.BOLD, 64));
				ctx.drawString("Failed", 200, 225);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("Didn't Listen to Directions", 186, 245);
				ctx.drawString("Score: " + (int) (score), 270, 430);
//				ctx.drawString("Press [SPACE] to retry", 205, 460);
				if(!endless) {
					ctx.setColor(Color.WHITE);
					ctx.fillRect(190, 440, 260, 32);
					ctx.setColor(Color.BLACK);
					ctx.drawString("Press [SPACE] to retry", 205, 460);
				}
				else {
					ctx.setColor(Color.WHITE);
					ctx.fillRect(190, 460, 260, 32);
					ctx.fillRect(260, 430, 115, 32);
					ctx.setColor(Color.BLACK);
					ctx.drawString("Round: " + (int) (round), 270, 450);
					ctx.drawString("Press [SPACE] to quit", 215, 480);	
				}
			}
			else if(ss == SubState.MAIN) {
				if(!title.isImageLoaded()) {
					title.loadImage();
				}
				ctx.drawImage(title.getImage(), 0, 0, 640, 640, null);
			}
		}
		return ctx;
	}

	@Override
	public void tick() {
		if(gs == GameState.PLAYING) {
			if(key.up) {
				npLoc = QuickRectangle.location(boat.getLocation().getX(), boat.getLocation().getY() - boat.getWalkspeed(), boat.getLocation().getWidth(), boat.getLocation().getHeight());
				boat.setRunningAnimationNumber(2);
				if(w.checkCollide(new PerspectiveRectangleArea(npLoc, 0.3), boat) == null) {
					boat.setLocation(npLoc);
				}
			}
			if(key.down) {
				npLoc = QuickRectangle.location(boat.getLocation().getX(), boat.getLocation().getY() + boat.getWalkspeed(), boat.getLocation().getWidth(), boat.getLocation().getHeight());
				boat.setRunningAnimationNumber(3);
				if(w.checkCollide(new PerspectiveRectangleArea(npLoc, 0.3), boat) == null) {
					boat.setLocation(npLoc);
				}
			}
			if(key.right) {
				npLoc = QuickRectangle.location(boat.getLocation().getX() + boat.getWalkspeed(), boat.getLocation().getY(), boat.getLocation().getWidth(), boat.getLocation().getHeight());
				boat.setRunningAnimationNumber(1);
				if(w.checkCollide(new PerspectiveRectangleArea(npLoc, 0.3), boat) == null) {
					boat.setLocation(npLoc);
				}
			}
			if(key.left) {
				npLoc = QuickRectangle.location(boat.getLocation().getX() - boat.getWalkspeed(), boat.getLocation().getY(), boat.getLocation().getWidth(), boat.getLocation().getHeight());
				boat.setRunningAnimationNumber(0);
				if(w.checkCollide(new PerspectiveRectangleArea(npLoc, 0.3), boat) == null) {
					boat.setLocation(npLoc);
				}
			}
			boat.getRunningAnimation().run();
		}
		
		if(System.currentTimeMillis() - lastReduction >= shrinkTime && gs == GameState.PLAYING) {
			int newWaterLevel = water.getType().getID() - 1;
			if(newWaterLevel == 10) {
				newWaterLevel = 2;
				gs = GameState.MENU;
				ss = SubState.FAIL_NO_WATER;
				wp.setRunningAnimationNumber(2);
			}
			else if(newWaterLevel == 1) {
				newWaterLevel = 2;
			}
			water = new Tile(TileType.types.get(newWaterLevel), water.getLocation());
			lastReduction = System.currentTimeMillis();
			if(!water.getRunningAnimation().getCurrentImage().isImageLoaded()) {
				water.getRunningAnimation().getCurrentImage().loadImage();
			}
		}
		if(System.currentTimeMillis() - lastGeneration >= genTime && gs == GameState.PLAYING) {
			w.getObjects().add(0, th.generateTreasure());
			lastGeneration = System.currentTimeMillis();
		}
		if(gs == GameState.MENU && ss == SubState.FAIL_NO_WATER) {
			for(int i = 0; i < w.getObjects().size(); i++) {
				if(w.getObjects().get(i) instanceof Treasure || w.getObjects().get(i) instanceof Mine) {
					((Actor) w.getObjects().get(i)).setRunningAnimationNumber(1);
				}
			}
		}
		if(gs != GameState.MENU && ss != SubState.MAIN) {
			for(int i = 0; i < w.getObjects().size(); i++) {
				if(w.getObjects().get(i) instanceof Treasure || w.getObjects().get(i) instanceof Mine || w.getObjects().get(i) instanceof Arrow || w.getObjects().get(i) instanceof Flag) {
					((Actor) w.getObjects().get(i)).getRunningAnimation().run();
				}
			}
			secLeft = (int) (((shrinkTime * 4) - (System.currentTimeMillis() - startGame)) / 1000);
			wp.getRunningAnimation().run();
			w.sortObjects();
		}
	}
}
