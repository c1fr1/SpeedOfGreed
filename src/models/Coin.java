package models;

import engineTester.MainGameLoop;
import engineTester.Player;
import renderEngine.DisplayManager;
import renderEngine.Renderer;
import textures.ModelTexture;

public class Coin {
	float x;
	float y;
	TexturedModel model;
	public CoinLevel tier = CoinLevel.gold; 
	public boolean collected = false;
	public Coin(float gx, float gy, CoinLevel type) {
		float width = DisplayManager.WIDTH;
		float height = DisplayManager.HEIGHT;
		x = gx;
		y = gy;
		float[] coords = {
				(x-150)/width, (y+150)/height, 0f,
				(x-150)/width, (y-150)/height, 0f,
				(x+150)/width, (y-150)/height, 0f,
				(x+150)/width, (y+150)/height, 0f
		};
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
		String txtPath = "";
		if (type == CoinLevel.gold) {
			txtPath = "coinTextureOne";
		}else if (type == CoinLevel.heavy) {
			txtPath = "coinLvlTwo";
		}else if (type == CoinLevel.delta) {
			txtPath = "coinGud";
		}else if (type == CoinLevel.pass) {
			txtPath = "keyKoin";
		}
		tier = type;
		ModelTexture texture = new ModelTexture(MainGameLoop.mainLoader.loadTexture(txtPath));
		RawModel mod =  MainGameLoop.mainLoader.loadToVAO(coords, textureCoords, indices);
		model = new TexturedModel(mod, texture);
	}
	public float distTo(Player p) {
		return (float)Math.sqrt(Math.pow(p.x - x, 2f) + Math.pow(p.y - y, 2f));
	}
	public void render(Renderer renderer) {
		if (!collected) {
			renderer.render(model);
		}
	}
}
