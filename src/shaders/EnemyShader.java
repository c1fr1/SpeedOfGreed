package shaders;

import org.lwjgl.opengl.GL20;

public class EnemyShader extends ShaderProgram {
	private static final String VERTEX_FILE = "res/shaders/playerVertexShader.txt";
	private static final String FRAGMENT_FILE = "res/shaders/darkFragShader.txt";
	public EnemyShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	public void begin(float centerx, float time) {
		GL20.glUseProgram(programID);
		int location = GL20.glGetUniformLocation(programID, "info");
		GL20.glUniform2f(location, centerx, time);
		
	}
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
}
