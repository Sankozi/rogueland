package org.sankozi.rogueland.model;

/**
 * Immutable object representing coordinates in game level. 
 * @author sankozi
 */
public final class Coords {
	public final int x;
	public final int y;

	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ',' + y + ')';
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Coords other = (Coords) obj;
		
		return this.x == other.x && this.y == other.y;
	}

	@Override
	public int hashCode() {
		return x >> 8 + y;
	}
}
