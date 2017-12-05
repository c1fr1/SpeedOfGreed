package models;

import java.awt.Point;

import org.lwjgl.opengl.GL20;

import engineTester.MainGameLoop;
import engineTester.Tile;
import renderEngine.DisplayManager;
import renderEngine.Renderer;
import shaders.ShaderProgram;
import textures.ModelTexture;

public class LevelNext{
	public Point[][] lines = {
			{
				new Point(-500, 2500),
				new Point(-500, -250),
				new Point(750, -250),
				new Point(750, 0),
				new Point(1000, 0),
				new Point(1000, -1000),
				new Point(2250, -1000),
				new Point(2250, -250),
				new Point(2750, -250),//startHold
				new Point(2750, 0),
				new Point(3000, 0),
				new Point(3000, -250),
				new Point(3750, -250),
				new Point(3750, 0),
				new Point(4000, 0),
				new Point(4000, -250),//end
				new Point(4750, -250),
				new Point(4750, -500),
				new Point(5250, -500),
				new Point(5250, 0),
				new Point(5500, 0),
				new Point(5500, -250),
				
				new Point(6250, -250),
				new Point(6250, -500),
				new Point(6750, -500),
				new Point(6750, -250),
				
				new Point(10000, -250),
				new Point(10000, 0),
				new Point(12000, 0),
				new Point(12000, 2500),
			},
			{
				new Point(1500, -500),
				new Point(1750, -500),
				new Point(1750, -750),
				new Point(1500, -750)
			},
			{
				new Point(1500, 500),
				new Point(1750, 500),
				new Point(1750, 250),
				new Point(1500, 250),
			},
			{
				new Point(3250, 1000),
				new Point(5250, 1000),
				new Point(5250, 750),
				new Point(3250, 750),
			},
			{
				new Point(2500, 750),
				new Point(2750, 750),
				new Point(2750, 500),
				new Point(2500, 500),
			},
			{
				new Point(6250, 1000),
				new Point(6500, 1000),
				new Point(6500, 1250),
				new Point(6250, 1250)
			},
			{
				new Point(7000, 1000),
				new Point(7250, 1000),
				new Point(7250, 1250),
				new Point(7000, 1250)
			}
	};
	public Point[][] dangers = {
			{
				new Point(1000, -750),
				new Point(2250, -750),
				new Point(2250, -1000),
				new Point(1000, -1000),
			},
			{
				new Point(3000, 0),
				new Point(3750, 0),
				new Point(3750, -250),
				new Point(3000, -250),
			},
			{
				new Point(4750, -250),
				new Point(4750, -500),
				new Point(5250, -500),
				new Point(5250, -250),
			},
			{
				new Point(6250, -250),
				new Point(6250, -500),
				new Point(6750, -500),
				new Point(6750, -250),
			},
			{//floating
				new Point(6000, 1250),
				new Point(6000, 250),
				new Point(6250, 250),
				new Point(6250, 1250)
			},
			{
				new Point(6750, 1250),
				new Point(6750, 500),
				new Point(7000, 500),
				new Point(7000, 1250)
			}
	};
	public Tile[] tiles;
	private TexturedModel dangerModel;
	private TexturedModel blockModel;
	public LevelNext() {
		float width = (float) DisplayManager.WIDTH;
		float height = (float) DisplayManager.HEIGHT;
		float[] coords = {
			-250/width, 0, 0f,
			-250/width, -250/height, 0f,
			0, -250/height, 0f,
			0, 0, 0f,
		};//--,+-
		int[] indices = {
				0, 1, 3,
				3, 1, 2
		};
		float[] textureCoords = {
				0f, 0f,
				0f, 1f,
				1f, 1f,
				1f, 0f
		};
		ModelTexture texture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("lavaTile"));
		RawModel mod =  MainGameLoop.mainLoader.loadToVAO(coords, textureCoords, indices);
		dangerModel = new TexturedModel(mod, texture);
		ModelTexture anotherTexture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("rockBlock"));
		blockModel = new TexturedModel(mod, anotherTexture);
		
		int lengthCounter = 0;
		for (int i = 0;i<lines.length;i++) {
			lengthCounter += lines[i].length;
		}
		tiles = new Tile[lengthCounter];
		boolean hori = false;
		int cind = 0;
		for (int ind = 0; ind < lines.length;ind++) {
			for (int i = 0; i<lines[ind].length;i++) {
				float firstX = 0;
				float firstY = 0;
				float secondX = lines[ind][i].x;
				float secondY = lines[ind][i].y - 100;
				if (i == 0) {
					firstX = lines[ind][lines[ind].length - 1].x;
					firstY = lines[ind][lines[ind].length - 1].y - 100;
				}else {
					firstX = lines[ind][i - 1].x;
					firstY = lines[ind][i - 1].y - 100;
				}
				float tWidth = Math.abs(secondX - firstX);
				float tHeight = Math.abs(secondY - firstY);
				if (hori) {
					tHeight = 100;
				}else {
					tWidth = 100;
				}
				hori = !hori;
				float smallX = firstX;
				if (firstX > secondX) {
					smallX = secondX;
				}
				float smallY = firstY;
				if (firstY > secondY) {
					smallY = secondY;
				}
				tiles[cind] = new Tile(smallX, smallY, tWidth, tHeight);
				cind ++;
			}
		}
	}
	public void render(Renderer renderer, ShaderProgram shader) {
		float width = DisplayManager.WIDTH;
		float height = DisplayManager.HEIGHT;
		float pxChange = (float) MainGameLoop.mainPlayer.x;
		float pyChange = (float) MainGameLoop.mainPlayer.y;
		while (pxChange > 250) {
			pxChange -= 250; 
		}
		while (pxChange < 0) {
			pxChange += 250;
		}
		while (pyChange > 250) {
			pyChange -= 250; 
		}
		while (pyChange < 0) {
			pyChange += 250;
		}
		float xpPos = (float) Math.floor((- 250 - width)/250)*250;
		int uniformLocation = GL20.glGetUniformLocation(shader.programID, "offsetCoords");
		while (xpPos < width + 500) {
			float ypPos = (float) Math.floor((- 250 - height)/250)*250;
			while (ypPos < height + 500) {
				GL20.glUniform2f(uniformLocation, -xpPos/width + pxChange/width, -ypPos/height + pyChange/height);
				double xTest = xpPos + (float) MainGameLoop.mainPlayer.x-250;
				double yTest = ypPos + (float) MainGameLoop.mainPlayer.y-250;
				if (new MainGameLoop.GroundData(xTest, yTest).inGround) {
					renderer.render(blockModel);
				}else if (!new MainGameLoop.GroundData(dangers, xTest, yTest).inGround) {
					renderer.render(dangerModel);
				}
				ypPos += 250;
			}
			xpPos += 250;
		}
	}
}
