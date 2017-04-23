package com.estrelsteel.ld38.level;

import java.io.IOException;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.file.Saveable;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.tile.Tile;
import com.estrelsteel.engine2.tile.TileType;
import com.estrelsteel.engine2.world.World;

public class Level implements Saveable {

	private World world;
	private int shrinkTime;
	private int genTime;
	private int minScore;
	private double mineChance;
	
	public Level(World world) {
		this.world = world;
	}
	
	public int getShrinkTime() {
		return shrinkTime;
	}
	
	public int getGenerationTime() {
		return genTime;
	}
	
	public int getMinScore() {
		return minScore;
	}
	
	public double getMineChance() {
		return mineChance;
	}
	
	public void setShrinkTime(int shrinkTime) {
		this.shrinkTime = shrinkTime;
	}
	
	public void setGenerationTime(int genTime) {
		this.genTime = genTime;
	}
	
	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}
	
	public void setMineChance(double mineChance) {
		this.mineChance = mineChance;
	}
	
	@Override
	public String getIdentifier() {
		return "LVL";
	}

	@Override
	public World load(GameFile file, int line) {
		String[] args = file.getLines().get(line).split(" ");
		GameFile part;
		if(args[0].equalsIgnoreCase(getIdentifier())) {
			shrinkTime = Integer.parseInt(args[1]);
			genTime = Integer.parseInt(args[2]);
			minScore = Integer.parseInt(args[3]);
			mineChance = Double.parseDouble(args[4]);
			for(int i = line + 1; i < file.getLines().size(); i++) {
				if(file.getLines().get(i).equalsIgnoreCase("") || file.getLines().get(i).equalsIgnoreCase(" ")) {
					return world;
				}
				part = new GameFile(Engine2.devPath + file.getLines().get(i));
				try {
					part.setLines(part.readFile());
					loadPart(part, 0);
				}
				catch (IOException e) {
					return world;
				}
			}
		}
		return world;
	}
	
	public World loadPart(GameFile file, int line) {
		String[] args = file.getLines().get(line).split(" ");
		int sx, x;
		int sy, y;
		int tw;
		int th;
		int tt;
		if(args[0].equalsIgnoreCase(getIdentifier())) {
			sx = Integer.parseInt(args[1]);
			sy = Integer.parseInt(args[2]);
			tw = Integer.parseInt(args[3]);
			th = Integer.parseInt(args[4]);
			x = sx;
			y = sy;
			for(line = line + 1; line < file.getLines().size(); line++) {
				args = file.getLines().get(line).split(" ");
				for(int t = 0; t < args.length; t++) {
					tt = Integer.parseInt(args[t]);
					if(tt >= 0) {
						world.getObjects().add(new Tile(TileType.types.get(tt), QuickRectangle.location(x, y, tw, th)));
					}
					x = x + tw;
				}
				x = sx;
				y = y + th;
			}
		}
		return world;
	}

	@Override
	public GameFile save(GameFile file) {
		return null;
	}

}
