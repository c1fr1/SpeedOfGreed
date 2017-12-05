package shaders;

import org.lwjgl.opengl.GL20;

public class PlayerShader extends ShaderProgram {
	private static final String VERTEX_FILE = "src/shaders/playerVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	public PlayerShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	@Override
	public void start(double offx, double offy) {
		GL20.glUseProgram(programID);
	}
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
}
