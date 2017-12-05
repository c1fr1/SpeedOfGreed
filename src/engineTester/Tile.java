package engineTester;

import org.lwjgl.opengl.GL30;

import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import textures.ModelTexture;

public class Tile {
	float xp;
	float yp;
	float w;
	float h;
	public TexturedModel model;
	public Tile(float x, float y, float width, float height) {
		float wdth = (float) DisplayManager.WIDTH;
		float hght = (float) DisplayManager.HEIGHT;
		if (height > 99 && height < 101) {
			width += 100;
		}else {
			height += 100;
		}
		float[] coords = {
			x/wdth, (y + height)/hght, 0f,
			x/wdth, y/hght, 0f,
			(x + width)/wdth, y/hght, 0f,
			(x + width)/wdth, (y + height)/hght, 0f,
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
		if (height > width) {
			textureCoords = new float[] {
				0f, 1f,
				1f, 1f,
				1f, 0f,
				0f, 0f
			};
		}
		ModelTexture texture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("horizwal"));
		RawModel mod =  MainGameLoop.mainLoader.loadToVAO(coords, textureCoords, indices);
		model = new TexturedModel(mod, texture);
	}
}
