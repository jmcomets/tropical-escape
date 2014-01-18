package tropicalescape.enemies;

import tropicalescape.GameObject;
import tropicalescape.Ship;
import tropicalescape.physics.CompositeRectangle;

public abstract class Enemy extends GameObject {
	public Enemy(CompositeRectangle rectangles) {
		super(rectangles);
	}

	public abstract void onHitShip(Ship ship);
}