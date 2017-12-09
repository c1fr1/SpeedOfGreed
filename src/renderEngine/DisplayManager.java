package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	public static final int WIDTH = 2560;//1280//2560
	public static final int HEIGHT = 1440;//720//1440
	public static final int FPS_CAP = 60;
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2)//tells display openGL vers. 3.2
		.withForwardCompatible(true)//make forward compatible
		.withProfileCore(true);
		
		try {//Display is the display object
			Display.setDisplayMode(new DisplayMode(1280,720));//set width/height
			//Display.setFullscreen(true);
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Speed of Greed");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, 1280, 720);//sets up dimensions of the view.
		
	}
	
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
}
