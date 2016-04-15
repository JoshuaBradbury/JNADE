package uk.co.newagedev.jnade.graphics;


public interface Renderable {

	public int getWidth();
	
	public int getHeight();
	
	public int[] getPixels();
	
	public String getName();
	
	public void setName(String name);
	
	public void scale(int scale);
	
}
