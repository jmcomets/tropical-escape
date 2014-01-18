package tropicalescape;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoosedState extends BasicGameState {
	
	public static final int ID = 5;
	private boolean replay = false;
	
	LoosedState() {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	        throws SlickException {
		g.setColor(Color.white);
	    g.drawString("YOU'RE NOT BETTER THAN UNICORNS§§", container.getWidth()/2, container.getHeight()/2);

	    g.drawString("Press Enter to replay", container.getWidth()/2, container.getHeight()/2 + 100);
	    
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_ENTER){
			replay = true;    
		}
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		replay  = false;
		
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if(replay) {
	    	game.enterState(PlayState.ID);	    	
	    }
	    
	}

	@Override
	public int getID() {
		return ID;
	}

}
