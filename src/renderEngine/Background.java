package renderEngine;

import engineTester.MainGameLoop;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;

public class Background {
	TexturedModel[][] tiles;
	public Background() {
		tiles = new TexturedModel[2*(2 + DisplayManager.WIDTH/500)][2*(2 + DisplayManager.HEIGHT/500)];
		ModelTexture texture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("backgroundText"));
		for(int ix = 0; ix < tiles.length;ix++) {
			for(int iy = 0; iy < tiles[ix].length; iy++) {
				float[] coords = {
					(ix*500 - 250)/(float)DisplayManager.WIDTH, (iy*500 + 250)/(float)DisplayManager.HEIGHT, 0f,
					(ix*500 - 250)/(float)DisplayManager.WIDTH, (iy*500 - 250)/(float)DisplayManager.HEIGHT, 0f,
					(ix*500 + 250)/(float)DisplayManager.WIDTH, (iy*500 - 250)/(float)DisplayManager.HEIGHT, 0f,
					(ix*500 + 250)/(float)DisplayManager.WIDTH, (iy*500 + 250)/(float)DisplayManager.HEIGHT, 0f,
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
				RawModel mod =  MainGameLoop.mainLoader.loadToVAO(coords, textureCoords, indices);
				tiles[ix][iy] = new TexturedModel(mod, texture);
			}
		}
	}
	public void render(Renderer renderer) {
		for(int ix = 0; ix < tiles.length;ix++) {
			for(int iy = 0; iy < tiles[ix].length; iy++) {
				renderer.render(tiles[ix][iy]);
			}
		}
	}
}
//DELET