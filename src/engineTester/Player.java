package engineTester;

import java.awt.Point;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import engineTester.MainGameLoop.GroundData;
import models.Coin;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import textures.ModelTexture;

public class Player {
	private boolean hp = true;
	//public double speed = 0;
	public double x = 0.1;
	public double y = 0;
	public double yVel = 0;
	private double xSpeed = 20;
	public double yForce = 35;
	public TexturedModel model;
	public int currency = 0;
	public Player(Loader loader) {
		float width = (float) DisplayManager.WIDTH;
		float height = (float) DisplayManager.HEIGHT;
		float[] coords = {
			-125f/width, 250f/height, 0f,
			-125f/width, -250f/height, 0f,
			125f/width, -250f/height, 0f,
			125f/width, 250f/height, 0f,
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
		ModelTexture texture = new ModelTexture(loader.loadTexture("charShi"));
		RawModel mod =  loader.loadToVAO(coords, textureCoords, indices);
		model = new TexturedModel(mod, texture);
	}
	public void checkPosition() {//20, 1620
		GroundData clFloorData = new GroundData(x - 120, y - 250);
		GroundData crFloorData = new GroundData(x + 120, y - 250);
		if (clFloorData.inGround || crFloorData.inGround) {
			if (Math.abs(clFloorData.shortestValue) < Math.abs(crFloorData.shortestValue)) {
				if (clFloorData.shortestValue > 0) {
					y += clFloorData.shortestValue - 0.1f;
				}
			}else {
				if (crFloorData.shortestValue > 0) {
					y += crFloorData.shortestValue - 0.1f;
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) || Keyboard.isKeyDown(Keyboard.KEY_W)) {
				yVel = yForce;
			}
			if (yVel < 0) {
				yVel = 0;
			}
		}else {
			yVel -= 1;
		}
		GroundData flFloorData = new GroundData(x - 120, y + 200 + yVel);
		GroundData frFloorData = new GroundData(x + 120, y + 200 + yVel);
		if (flFloorData.inGround || frFloorData.inGround) {
			if (Math.abs(flFloorData.shortestValue) < Math.abs(frFloorData.shortestValue)) {
				if (flFloorData.shortestValue > 0) {
					y -= flFloorData.shortestValue + 0.1f;
				}
			}else {
				if (frFloorData.shortestValue > 0) {
					y -= frFloorData.shortestValue + 0.1f;
				}
			}
			if (yVel > 0) {
				yVel = 0;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			GroundData leftGD1 = new GroundData(x - 125 - getxSpeed(), y - 245);
			GroundData leftGD2 = new GroundData(x - 125 - getxSpeed(), y + 185);
			GroundData leftGD3 = new GroundData(x - 125 - getxSpeed(), y - 20);
			if (!leftGD1.inGround && !leftGD2.inGround && !leftGD3.inGround){
				x -= getxSpeed();
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			GroundData rightGD1 = new GroundData(x + 130 + getxSpeed(), y - 245);
			GroundData rightGD2 = new GroundData(x + 130 + getxSpeed(), y + 185);
			GroundData rightGD3 = new GroundData(x - 125 + getxSpeed(), y - 20);
			if (! rightGD1.inGround && !rightGD2.inGround && !rightGD3.inGround) {
				x += getxSpeed();
			}
		}
		y += yVel;
	}
	public boolean checkLocation(Point[][] dangers) {
		boolean bl = !new GroundData(dangers, x - 120, y - 230).inGround;
		boolean br = !new GroundData(dangers, x + 120, y - 230).inGround;
		boolean ul = !new GroundData(dangers, x - 120, y + 100).inGround;//180
		boolean ur = !new GroundData(dangers, x + 120, y + 100).inGround;//180
		boolean lu = !new GroundData(dangers, x - 130, y + 100).inGround;//180
		boolean lm = !new GroundData(dangers, x - 130, y - 20).inGround;
		boolean ru = !new GroundData(dangers, x + 130, y + 100).inGround;//180
		boolean rm = !new GroundData(dangers, x + 130, y - 20).inGround;
		if (bl || br || ul || ur || lu || lm || ru || rm) {
			if (hp)
			return true;
		}
		return false;
	}

	public double getxSpeed() {
		return ((xSpeed-10) * Math.pow(9d/10d, (double) currency/2d)) + 10;
	}
	public void changexSpeed(boolean byShop) {
		if (byShop) {
			xSpeed += 1;
		}else {
			xSpeed *= 2;
		}
	}
	public void pickupBlue() {
		xSpeed *= 19/20;
	}
	public void reset() {
		currency = 0;
		xSpeed = 20;
		yForce = 35;
	}
}
