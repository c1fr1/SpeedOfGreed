package shaders;

import org.lwjgl.opengl.GL20;

public class FlashShader extends ShaderProgram {
	private static final String VERTEX_FILE = "res/shaders/playerVertexShader.txt";
	private static final String FRAGMENT_FILE = "res/shaders/gradientFragShader.txt";
	public FlashShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	public void begin(float centerx, float centery, float time) {
		GL20.glUseProgram(programID);
		int location = GL20.glGetUniformLocation(programID, "centerCoords");
		GL20.glUniform3f(location, centerx, centery, time);
		
	}
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
}
