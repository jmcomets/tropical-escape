package tropicalescape;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.enemies.Enemy;
import tropicalescape.enemies.SleepingIsland;

public class Game extends BasicGame {

	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Flag> flags = new ArrayList<Flag>();
	private List<Ship> ships = new ArrayList<Ship>();

	public Game(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		String lvlName = "res/levels/test.lvl";
		try {
			loadLevel(lvlName);
		} catch (IOException e) {
			new SlickException("Problème au chargement du niveau " + lvlName
					+ " : " + e.getMessage());
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.pushTransform();
		g.setColor(new Color(18, 54, 103));
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		g.popTransform();
		for (Enemy enemy : enemies) {
			enemy.baseRender(g);
		}
		for (Flag flag : flags) {
			flag.baseRender(g);
		}
		for (Ship ship : ships) {
			ship.baseRender(g);
		}
	}

	public void loadLevel(String path) throws IOException {
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			while ((text = reader.readLine()) != null) {
				String[] tokens = text.split(" ");
				if (tokens[0].equals("ISLAND")) {
					if (tokens.length == 3) {
						// TODO
					}
				} else if (tokens[0].equals("SLEEPING-ISLAND")) {
					if (tokens.length == 3) {
						SleepingIsland sleepingIsland = new SleepingIsland();
						sleepingIsland.setPosition(new Vector2f(Float
								.parseFloat(tokens[1]), Float
								.parseFloat(tokens[2])));
						enemies.add(sleepingIsland);
					}
				} else if (tokens[0].equals("SHIP")) {
					if (tokens.length == 3) {
						Ship ship = new Ship(Float.parseFloat(tokens[1]),
								Float.parseFloat(tokens[2]));
						ships.add(ship);
					}
				} else if (tokens[0].equals("FLAG")) {
					if (tokens.length == 4) {
						Flag flag = new Flag(tokens[1],
								Float.parseFloat(tokens[2]),
								Float.parseFloat(tokens[3]));
						flags.add(flag);
					}
				}
			}
			if (flags.size() != 0) {
				for (Ship s : ships) {
					s.setNextFlag(flags.get(0));
				}
			} else {
				System.out.println("Pas de flags sur la map !");
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		List<Ship> deadShips = new ArrayList<Ship>();
		for (Ship ship : ships) {
			ship.baseUpdate(delta);
			for (Enemy enemy : enemies) {
				if (enemy.intersects(ship)) {
					enemy.onHitShip(ship);
					if (!ship.isAlive()) {
						deadShips.add(ship);
						break;
					}
				}
			}

			if (!ship.isAlive()) {
				continue;
			}

			// TODO : peut être le remplacer par un rectangle de colision si
			// ship trop rapide
			Flag flag = ship.getNextFlag();
			if (flag != null) {
				if (ship.hasArrived()) {
					int i = flags.indexOf(flag);

					// Dernier flag atteint
					if (i == flags.size() - 1) {
						ship.setNextFlag(null);
					} else {
						ship.setNextFlag(flags.get(i + 1));
					}
				}
			}
		}
		ships.removeAll(deadShips);

		List<Enemy> deadEnemies = new ArrayList<Enemy>();
		for (Enemy enemy : enemies) {
			enemy.baseUpdate(delta);
			if (!enemy.isAlive()) {
				deadEnemies.add(enemy);
			}
		}
		enemies.removeAll(deadEnemies);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			Game game = new Game("Tropical Escape !");
			appgc = new AppGameContainer(game);
			appgc.setDisplayMode(640, 480, false);

			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
