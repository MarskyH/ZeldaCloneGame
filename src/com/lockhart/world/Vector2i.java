package com.lockhart.world;

public class Vector2i {
	
	public int x,y;
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object object) {
		Vector2i vec = (Vector2i) object;
		if(vec.x == this.x && vec.y == this.y) {
			return true;
		}else {
			return false;
		}
	}
}
