package main;

import java.awt.Dimension;

public interface ComponentResize {
	public Dimension currentSize = new Dimension();
	
	
	void resizeCall(Dimension old, Dimension n);
	
	
	
}
