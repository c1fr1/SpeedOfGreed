package shaders;

import org.lwjgl.opengl.GL20;

public class BackgroundShader extends ShaderProgram {
	private static final String VERTEX_FILE = "res/shaders/backgroundVertexShader.txt";
	private static final String FRAGMENT_FILE = "res/shaders/fragmentShader.txt";
	
	public BackgroundShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
}//delet?
