package models;

import java.awt.Point;

import org.lwjgl.opengl.GL20;

import engineTester.MainGameLoop;
import engineTester.Tile;
import renderEngine.DisplayManager;
import renderEngine.Renderer;
import shaders.ShaderProgram;
import textures.ModelTexture;

public class Level {
	TexturedModel blockModel;
	public Point[][] lines = {
		{
			new Point(-500, -500),
			new Point(1500, -500),
			new Point(1500, 0),
			new Point(2000, 0),
			new Point(2000, -1000),
			new Point(-500, -1000),
			new Point(-500, -4000),
			new Point(1500, -4000),
			new Point(1500, -4500),
			new Point(2000, -4500),
			new Point(2000, -5000),
			new Point(10000, -5000),
			new Point(10000, -1000),
			new Point(6000, -1000),
			new Point(6000, -2000),
			new Point(5500, -2000),
			new Point(5500, -1000),
			new Point(5000, -1000),
			new Point(5000, -500),
			new Point(5500, -500),
			new Point(5500, 1500),
			new Point(-500, 1500),
		},
		{
			new Point(3000, 0),
			new Point(3500, 0),
			new Point(3500, -500),
			new Point(3000, -500)
		},
		{
			new Point(4000, 500),
			new Point(4500, 500),
			new Point(4500, 0),
			new Point(4000, 0)
		},
		{
			new Point(2000, -2000),
			new Point(3000, -2000),
			new Point(3000, -1500),
			new Point(4000, -1500),
			new Point(4000, -2500),
			new Point(2000, -2500)
		},
		{
			new Point(9000, -4000),
			new Point(3000, -4000),
			new Point(3000, -3500),
			new Point(4500, -3500),
			new Point(4500, -2500),
			new Point(9000, -2500)
		}
	};
	public Coin[] coins = {
			new Coin(1250, -250, CoinLevel.gold),
			new Coin(1250, 750, CoinLevel.gold),
			new Coin(2500, 750, CoinLevel.gold),
			new Coin(3250, 250, CoinLevel.gold),
			
			new Coin(2250, -250, CoinLevel.heavy),
			new Coin(2500, -250, CoinLevel.heavy),
			new Coin(2750, -250, CoinLevel.heavy),
			new Coin(2250, -500, CoinLevel.heavy),
			new Coin(2500, -500, CoinLevel.heavy),
			new Coin(2750, -500, CoinLevel.heavy),
			new Coin(2250, -750, CoinLevel.heavy),
			new Coin(2500, -750, CoinLevel.heavy),
			new Coin(2750, -750, CoinLevel.heavy),
			new Coin(2250, -1000, CoinLevel.heavy),
			new Coin(2500, -1000, CoinLevel.heavy),
			new Coin(2750, -1000, CoinLevel.heavy),
			new Coin(2250, -1250, CoinLevel.heavy),
			new Coin(2500, -1250, CoinLevel.heavy),
			new Coin(2750, -1250, CoinLevel.heavy),
			new Coin(2250, -1500, CoinLevel.heavy),
			new Coin(2500, -1500, CoinLevel.heavy),
			new Coin(2750, -1500, CoinLevel.heavy),
			new Coin(2250, -1750, CoinLevel.heavy),
			new Coin(2500, -1750, CoinLevel.heavy),
			new Coin(2750, -1750, CoinLevel.heavy),
			
			new Coin(4250, 750, CoinLevel.gold),
			new Coin(5250, 250, CoinLevel.gold),
			new Coin(3750, -750, CoinLevel.gold),
			new Coin(4750, -725, CoinLevel.gold),
			new Coin(3750, -500, CoinLevel.gold),
			new Coin(3750, -250, CoinLevel.gold),
			new Coin(9250, -2500, CoinLevel.gold),
			new Coin(9750, -3625, CoinLevel.gold),
			new Coin(9250, -4750, CoinLevel.gold),
			new Coin(8750, -4250, CoinLevel.gold),//end 3250
			new Coin(8250, -4750, CoinLevel.gold),
			new Coin(7750, -4250, CoinLevel.gold),
			new Coin(7250, -4750, CoinLevel.gold),
			new Coin(6750, -4250, CoinLevel.gold),
			new Coin(6250, -4750, CoinLevel.gold),
			new Coin(5750, -4250, CoinLevel.gold),
			new Coin(5250, -4750, CoinLevel.gold),
			new Coin(4750, -4250, CoinLevel.gold),
			new Coin(4250, -4750, CoinLevel.gold),
			new Coin(3750, -4250, CoinLevel.gold),
			new Coin(3250, -4750, CoinLevel.gold),
			
			new Coin(4250, -2250, CoinLevel.gold),
			new Coin(4250, -2500, CoinLevel.gold),
			new Coin(4250, -2750, CoinLevel.gold),
			new Coin(4250, -3000, CoinLevel.gold),
			new Coin(4250, -3250, CoinLevel.gold),
			
			new Coin(-250, -3250, CoinLevel.delta),
			new Coin(-250, -3750, CoinLevel.pass)
	};
	Tile[] tiles;
	public Level() {
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
		ModelTexture texture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("lvlOneBrick"));
		RawModel mod =  MainGameLoop.mainLoader.loadToVAO(coords, textureCoords, indices);
		blockModel = new TexturedModel(mod, texture);
	}
	/*public void render(Renderer renderer, ShaderProgram shader) {
		for (int i = 0; i < tiles.length; i++) {
			renderer.render(tiles[i].model);
		}
		for (int i = 0; i < coins.length; i++) {
			if (!coins[i].collected) {
				coins[i].render(renderer);
			}
		}
	}*/
	public void render(Renderer renderer, ShaderProgram shader) {
		for (int i = 0; i < coins.length; i++) {
			if (!coins[i].collected) {
				coins[i].render(renderer);
			}
		}
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
				if (new MainGameLoop.GroundData(xpPos + (float) MainGameLoop.mainPlayer.x-250, ypPos + (float) MainGameLoop.mainPlayer.y-250).inGround) {
					renderer.render(blockModel);
				}
				ypPos += 250;
			}
			xpPos += 250;
		}
	}
	public void checkGold() {
		for (int i = 0; i < coins.length; i++) {
			if (!coins[i].collected) {
				if (coins[i].distTo(MainGameLoop.mainPlayer) < 250) {
					coins[i].collected = true;
					if (coins[i].tier == CoinLevel.gold) {
						MainGameLoop.mainPlayer.currency += 2;
					}else if (coins[i].tier == CoinLevel.heavy) {
						MainGameLoop.mainPlayer.pickupBlue();
						MainGameLoop.mainPlayer.currency += 2;
					}else if (coins[i].tier == CoinLevel.delta) {
						MainGameLoop.mainPlayer.changexSpeed(false);
						MainGameLoop.mainPlayer.yForce *= 3f/4f;
						MainGameLoop.mainPlayer.currency += 5;
					}else if (coins[i].tier == CoinLevel.pass) {
						MainGameLoop.mainPlayer.x = 0.1;
						MainGameLoop.mainPlayer.y = 0;
						MainGameLoop.levelNum = !MainGameLoop.levelNum;
					}
				}
			}
		}
	}
}
