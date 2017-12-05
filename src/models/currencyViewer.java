package models;

import engineTester.MainGameLoop;
import renderEngine.Renderer;
import textures.ModelTexture;

public class currencyViewer {
	TexturedModel modelOne;
	TexturedModel modelTwo;
	public currencyViewer(float width, float height) {
		float[] coords = {
				(width-500)/width, 1f, 0f,//-+
				(width-500)/width, (height-250)/height, 0f,//--
				(width-250)/width, (height-250)/height, 0f,//+-
				(width-250)/width, 1f, 0f//++
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
		ModelTexture texture = new ModelTexture(MainGameLoop.mainLoader.loadTexture("0"));
		RawModel mod =  MainGameLoop.mainLoader.loadToVAO(coords, textureCoords, indices);
		modelOne = new TexturedModel(mod, texture);
		coords = new float[] {
				(width-250)/width, 1f, 0f,//-+
				(width-250)/width, (height-250)/height, 0f,//--
				1f, (height-250)/height, 0f,//+-
				1f, 1f, 0f//++
		};
		RawModel mody =  MainGameLoop.mainLoader.loadToVAO(coords, textureCoords, indices);
		modelTwo = new TexturedModel(mody, texture);
	}
	public void render(int num, Renderer renderer) {
		String str = Integer.toString(num);
		char firstType;
		char secondType;
		if (str.length() != 2) {
			firstType = '0';
			secondType = str.charAt(0);
		}else {
			firstType = str.charAt(0);
			secondType = str.charAt(1);
		}
		modelOne.setTexture(new ModelTexture(MainGameLoop.mainLoader.loadTexture(""+firstType)));
		modelTwo.setTexture(new ModelTexture(MainGameLoop.mainLoader.loadTexture(""+secondType)));
		renderer.render(modelOne);
		renderer.render(modelTwo);
	}
}
