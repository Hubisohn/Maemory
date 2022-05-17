package com.example.maemory;

import javafx.scene.image.*;

public class Spielkarte {
	
	private final Image front;
	private final Image back;
	
	public Spielkarte(Image front, Image back) {
		
		if (front.getHeight() != back.getHeight() || front.getWidth() != back.getWidth()) {
			throw new IllegalArgumentException("both pictures must have the same width and the same height");
		}
		this.front = front;
		this.back = back;
	}
	
	public Image getFront() {
		return front;
	}
	
	public Image getBack() {
		return back;
	}
	
}
