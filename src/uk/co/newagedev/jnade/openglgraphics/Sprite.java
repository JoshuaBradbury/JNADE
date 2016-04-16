package uk.co.newagedev.jnade.openglgraphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import uk.co.newagedev.jnade.util.Vector2f;

public class Sprite {
	private ByteBuffer image;
	private int textureID, width, height, glFormat;
	private String name;
	private boolean flipX, flipY;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Sprite(ByteBuffer image, int width, int height, int glFormat) {
		this.image = image;
		this.width = width;
		this.height = height;
		this.glFormat = glFormat;
		
		textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, glFormat, width, height, 0, glFormat, GL11.GL_UNSIGNED_BYTE, image);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public Sprite() {
		
	}
	
	public void setSize(Vector2f size) {
		this.width = (int) size.x;
		this.height = (int) size.y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}

	public int getGLFormat() {
		return glFormat;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ByteBuffer getImageData() {
		return image;
	}

	public void release() {
		GL11.glDeleteTextures(textureID);
	}

	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}
	
	public void setFlipY(boolean flipY) {
		this.flipY = flipY;
	}
	
	public boolean shouldFlipX() {
		return flipX;
	}
	
	public boolean shouldFlipY() {
		return flipY;
	}
	
	public Vector2f getSize() {
		return new Vector2f(width, height);
	}
}
