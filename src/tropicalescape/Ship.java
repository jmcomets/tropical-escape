package tropicalescape;

import org.newdawn.slick.Graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.physics.Hitbox;

public class Ship extends GameObject {

	static final String IMG_FILE = "res/ship/ship-east.png";
	static final int MAX_HP = 50;
	static final int EPSILON = 5;
	static final int SLOW_FACTOR = 10;

	private int hp;
	private Flag nextFlag;
	private Direction dir;
	private boolean arrived = false;

	static Image img;

	public enum Direction {
		E, NE, N, NW, W, SW, S, SE
	}

	Ship(float x, float y) {
		super(new Hitbox()); // TODO
		hp = MAX_HP;
		dir = Direction.E;
		Vector2f speed = new Vector2f(0f,0f);
		setSpeed(speed);
		Vector2f position = new Vector2f(x,y);
		setPosition(position);
	}

	static {
		try {
			img = new Image(IMG_FILE);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void computePath() {
		Vector2f speed = new Vector2f();
		if (nextFlag == null) {
			speed.x = 0;
			speed.y = 0;
		} else {
			float vectorX = nextFlag.getPosition().x - getPosition().x;
			float vectorY = nextFlag.getPosition().y - getPosition().y;

			// normalise le vecteur
			float norme = (float) Math.sqrt(vectorX * vectorX + vectorY
					* vectorY);
			if (norme > EPSILON){
				vectorX = vectorX / (norme*SLOW_FACTOR);
				vectorY = vectorY / (norme*SLOW_FACTOR);

				speed.x = vectorX;
				speed.y = vectorY;
			} else {
				speed.x = 0;
				speed.y = 0;
				arrived = true;
			}


		}
		System.out.println(speed);

		setSpeed(speed);
	}

	public void loseHealh(int dmgValue) {
		hp -= dmgValue;
	}

	public boolean isAlive() {
		if (hp > 0) {
			return true;
		}
		return false;
	}
	
	public boolean hasArrived() {
		return arrived;
	}

	public float getX() {
		return getPosition().x;
	}

	public float getY() {
		return getPosition().y;
	}

	public Flag getNextFlag() {
		return nextFlag;
	}

	public void setNextFlag(Flag nextFlag) {
		this.nextFlag = nextFlag;
		arrived = false;
	}

	@Override
	public void render(Graphics g) {
		float centerX = getPosition().x - img.getHeight() / 2f;
		float centerY = getPosition().y - img.getWidth() / 2f;
		img.draw(centerX, centerY);
	}

	@Override
	public void update(int delta) {
		System.out.println(getSpeed());
		System.out.println(getPosition());
		System.out.println(nextFlag);
		computePath();

		/*double angle = getSpeed().getTheta();
		System.out.println(angle);
		
		if (337.5 >= angle || angle < 22.5) {
			dir = Direction.E;
		} else if (22.5 <= angle && angle < 67.5) {
			dir = Direction.NE;
		} else if (67.5 <= angle && angle < 112.5) {
			dir = Direction.N;
		} else if (112.5 <= angle && angle < 157.5) {
			dir = Direction.NW;
		} else if (157.5 <= angle && angle < 202.5) {
			dir = Direction.W;
		} else if (202.5 <= angle && angle < 247.5) {
			dir = Direction.SW;
		} else if (247.5 <= angle && angle < 292.5) {
			dir = Direction.S;
		} else if (292.5 <= angle && angle < 337.5) {
			dir = Direction.SE;
		}
		
		System.out.println(dir);*/
	}
	
	public void kill(){
		this.hp = 0;
	}
}
