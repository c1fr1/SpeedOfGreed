package shaders;

import org.lwjgl.opengl.GL20;

public class BackgroundShader extends ShaderProgram {
	private static final String VERTEX_FILE = "src/shaders/backgroundVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	public BackgroundShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
}//delet?
