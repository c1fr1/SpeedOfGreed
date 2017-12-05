package engineTester;

import java.awt.Point;
import java.io.FilterInputStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import models.*;
import renderEngine.Background;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.BackgroundShader;
import shaders.EnemyShader;
import shaders.FlashShader;
import shaders.PlayerShader;
import shaders.StaticShader;
import textures.ModelTexture;


public class MainGameLoop {
	public static Loader mainLoader;
	public static Player mainPlayer;
	public static Level firstLevel;
	public static LevelNext secondLevel;
	public static TexturedModel backgroundTileOne;
	public static TexturedModel backgroundTileTwo;
	public static TexturedModel deathScreen;
	public static currencyViewer currencyView;
	public static TexturedModel shopKeeper;
	public static TexturedModel shopView;
	public static boolean levelNum = false;
	public static boolean ded = false;
	static float flashTime = 0f;
	public static float enemyTime = 0f;
	public static void end(boolean completely) {
		if (completely) {
			mainPlayer.currency = 0;
			mainPlayer.xSpeed = 20;
			mainPlayer.yForce = 35;
		}
		mainPlayer.x = 0.1;
		mainPlayer.y = 0;
		mainPlayer.yVel = 0;
		levelNum = false;
		ded = false;
		flashTime = 0f;
		enemyTime = 0f;
		firstLevel = new Level();
		secondLevel = new LevelNext();
	}
	public static class GroundData {
		public boolean inGround;
		public double shortestValue = Float.MAX_VALUE;
		public int crosses = 0;
		public GroundData(double d, double e) {
			Point[][] pnts = {};
			if (!levelNum) {
				pnts = firstLevel.lines;
			}else if (levelNum) {
				pnts = secondLevel.lines;
			}
			for (int ind = 0; ind < pnts.length; ind++) {
				for (int i = 0; i < pnts[ind].length; i++) {
					double fx = 0;
					double fy = 0;
					if (i == 0) {
						fx = pnts[ind][pnts[ind].length - 1].x - d;
						fy = pnts[ind][pnts[ind].length - 1].y - e;
					}else {
						fx = pnts[ind][i - 1].x-d;
						fy = pnts[ind][i - 1].y-e;
					}
					double sx = pnts[ind][i].x-d;
					double sy = pnts[ind][i].y-e;
					if (fy == sy) {
						double intersect = (-10*fx)/(sx-fx);
						if (intersect >= 0 && intersect <= 10) {
							double dist = ((sy - fy)*intersect)/10 + fy;
							if (Math.abs(shortestValue) > Math.abs(dist)) {
								shortestValue = dist;
							}
							if (dist > 0) {
								crosses++;
							}
						}
					}
				}
        	}
        	inGround = crosses % 2 == 0;
		}
		public GroundData(Point[][] pnts, double d, double e) {
			for (int ind = 0; ind < pnts.length; ind++) {
				for (int i = 0; i < pnts[ind].length; i++) {
					double fx = 0;
					double fy = 0;
					if (i == 0) {
						fx = pnts[ind][pnts[ind].length - 1].x - d;
						fy = pnts[ind][pnts[ind].length - 1].y - e;
					}else {
						fx = pnts[ind][i - 1].x-d;
						fy = pnts[ind][i - 1].y-e;
					}
					double sx = pnts[ind][i].x-d;
					double sy = pnts[ind][i].y-e;
					double intersect = (-10*fx)/(sx-fx);
					if (intersect > 0 && intersect <= 10) {
						double dist = ((sy - fy)*intersect)/10 + fy;
						if (Math.abs(shortestValue) > Math.abs(dist)) {
							shortestValue = dist;
						}
						if (dist > 0) {
							crosses++;
						}
					}
				}
        	}
        	inGround = crosses % 2 == 0;
		}
	}
	public static void main(String[] args) {
		float width = (float) DisplayManager.WIDTH;
		float height = (float) DisplayManager.HEIGHT;
		
		DisplayManager.createDisplay();
		mainLoader = new Loader();
		firstLevel = new Level();
		secondLevel = new LevelNext();
		currencyView = new currencyViewer(width, height);
		Renderer renderer = new Renderer();//wrenderer
		StaticShader shader = new StaticShader();
		PlayerShader pShader = new PlayerShader();
		FlashShader fShader = new FlashShader();
		EnemyShader dShader = new EnemyShader();
		
		
		mainPlayer = new Player(mainLoader);
		
		float[] coords = {
			-500/width, 0, 0f,
			-500/width, -500/height, 0f,
			0, -500/height, 0f,
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
		ModelTexture texture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("backgroundText"));
		RawModel mod =  MainGameLoop.mainLoader.loadToVAO(coords, textureCoords, indices);
		backgroundTileOne = new TexturedModel(mod, texture);
		ModelTexture anotherTexture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("lavaBackground"));
		backgroundTileTwo = new TexturedModel(mod, anotherTexture);
		float[] dcoords = {
			-1f, 1f, 0f,
			-1f, -1f, 0f,
			1f, -1f, 0f,
			1f, 1f, 0f,
		};
		RawModel dmod = MainGameLoop.mainLoader.loadToVAO(dcoords, textureCoords, indices);
		ModelTexture dTexture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("gameOverBackground"));
		deathScreen = new TexturedModel(dmod, dTexture);
		
		float[] scoords = {
				10750f/width, 1000/width, 0f,//-+
				10750f/width, 0f, 0f,//--
				11250f/width, 0f, 0f,//+-
				11250f/width, 1000/width, 0f,//++
		};
		RawModel smod = MainGameLoop.mainLoader.loadToVAO(scoords, textureCoords, indices);
		ModelTexture sTexture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("shopDrugkeeperDealer"));
		shopKeeper = new TexturedModel(smod, sTexture);
		
		float[] svcoords = {
				-1000/width, 500/width, 0f,//-+
				-1000/width, -500/width, 0f,//--
				1000f/width, -500/width, 0f,//+-
				1000f/width, 500/width, 0f,//++
		};
		RawModel svmod = MainGameLoop.mainLoader.loadToVAO(svcoords, textureCoords, indices);
		ModelTexture svTexture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("shopUI"));
		shopView = new TexturedModel(svmod, svTexture);
		
		int lengthCounter = 0;
		for (int i = 0;i<firstLevel.lines.length;i++) {
			lengthCounter += firstLevel.lines[i].length;
		}
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		while(!Display.isCloseRequested()) {
			if (!ded) {
				mainPlayer.checkPosition();
				if (!levelNum) {
					firstLevel.checkGold();
				}else {
					ded = mainPlayer.checkLocation(secondLevel.dangers);
				}
			}
			renderer.prepare();//wrenderer
			shader.start(mainPlayer.x/width, mainPlayer.y/height);
			//drawing background
			
			float pxChange = (float) mainPlayer.x/2.5f;
			float pyChange = (float) mainPlayer.y/2.5f;
			while (pxChange > 500) {
				pxChange -= 500; 
			}
			while (pxChange < 0) {
				pxChange += 500;
			}
			while (pyChange > 500) {
				pyChange -= 500; 
			}
			while (pyChange < 0) {
				pyChange += 500;
			}
			float xpPos = pxChange - 500 - width;
			int uniformLocation = GL20.glGetUniformLocation(shader.programID, "offsetCoords");
			while (xpPos < width) {
				float ypPos = pyChange - 500 - height;
				while (ypPos < height) {
					GL20.glUniform2f(uniformLocation, xpPos/width, ypPos/height);
					if (!levelNum) {
						renderer.render(backgroundTileOne);
					}else if (levelNum){
						renderer.render(backgroundTileTwo);
					}
					ypPos += 500;
				}
				xpPos += 500;
			}
			GL20.glUniform2f(uniformLocation,(float) mainPlayer.x/width,(float) mainPlayer.y/height);

			//not drawing background
			if (flashTime >= 60) {
				renderer.render(shopKeeper);
			}
			if (!levelNum) {
				firstLevel.render(renderer, shader);
			}else if (levelNum) {
				secondLevel.render(renderer, shader);
			}
			shader.stop();
			pShader.start(0, 0);
			if (!ded) {
				renderer.render(mainPlayer.model);//wrender
			}else {
				renderer.render(deathScreen);
				if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
					end(true);
				}
			}
			currencyView.render(mainPlayer.currency, renderer);
			if (flashTime >= 59.5) {
				renderer.render(shopView);
				if (Keyboard.isKeyDown(Keyboard.KEY_S) && mainPlayer.currency >= 5) {
					mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
					mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
					mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
					mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
					mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
					mainPlayer.xSpeed += 1;
					mainPlayer.currency -= 5;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_J) && mainPlayer.currency >= 5) {
					mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
					mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
					mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
					mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
					mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
					mainPlayer.yForce += 1;	
					mainPlayer.currency -= 5;
				}
			}
			pShader.stop();
			if (levelNum) {
				enemyTime++;
			}
			if (flashTime == 0f && levelNum && !ded) {
				dShader.begin((float)(-mainPlayer.x)/width, enemyTime);
				if (mainPlayer.x < enemyTime*12.5 - width*1.01) {
					if (mainPlayer.currency > 0) {
						if (enemyTime%20 == 0) {
							mainPlayer.currency -= 1;
							mainPlayer.xSpeed = ((mainPlayer.xSpeed-5)*10f/9f)+5;
						}
					}else {
						ded = true;
					}
				}
				//mainPlayer.x = enemyTime*16 - width*4;
				renderer.render(deathScreen);
				dShader.stop();
				if (mainPlayer.x > 10000) {
					flashTime++;
				}
			}else if (levelNum && flashTime < 59.5 && !ded) {
				flashTime ++;
				dShader.begin((float)(-mainPlayer.x)/width, enemyTime);
				renderer.render(deathScreen);
				dShader.stop();
				fShader.begin((float)(13500 - mainPlayer.x)/width,(float)(750 + mainPlayer.y)/height, flashTime);
				renderer.render(deathScreen);
				fShader.stop();
			}else if (levelNum && !ded) {
				flashTime ++;
				if (flashTime > 500) {
					end(false);
				}
			}
			DisplayManager.updateDisplay();
		}
		mainLoader.cleanUp();
		shader.cleanUP();
		DisplayManager.closeDisplay();
	}
}
