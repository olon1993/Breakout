import java.net.URL;
import javafx.scene.image.Image;

public class Ball {

	// Image Variables
	private final String path = "graphics/ball_sm.png";
	private final URL url = getClass().getResource(path);
	private Image image = new Image(url.toString());
	
	// Game Variables
	private final int BALL_WIDTH = 16;
	private final int BALL_HEIGHT = 16;
	private final int BALL_MAX_X = Game.CANVAS_WIDTH - BALL_WIDTH;
	private final int BALL_MAX_Y = Game.CANVAS_HEIGHT + BALL_HEIGHT;
	private final int BALL_SPEED = 5;
	private int locx, boundX,	// Represents the left and right bounds of the ball respectively
				locy, boundY, 	// Represents the top and bottom bounds of the ball respectively
				directX,		// Either 1 or -1 to represent directional movement on the x plane
				directY;		// Either 1 or -1 to represent directional movement on the y plane
	
	public Ball() {
		
	}
	
	public void move() {
		this.locx += directX * BALL_SPEED;
		this.locy += directY * BALL_SPEED;
	}
	
	public void detectCollision() {
	
		// Check if the ball hit left or right wall boundary
		// and reverse direction if it did
		if(this.locx == 0){
			directX = 1;
		} else if(this.locx == BALL_MAX_X) {
			directX = -1;
		}
		
		// Check if the ball hit top or bottom wall boundary.
		// Reverse direction if it hit top, game over if it hit bottom
		if(this.locy == 0) {
			directY = 1;
		} else if(this.locy == BALL_MAX_Y) {
			Game.gmState = Game.GameState.GAMEOVER;
		}
		
		/*
		
		// Check if the ball hit the paddle and reverse direction if it did
		if() {
			directY = -1;
		}
	
		for(int i = 0; i < Game.activeBlocks.size(); i++) {
			if() {
				Game.activeBlocks.get(i).hit();
				if( ! Game.activeBlocks.get(i).getIsActive()) {
					Game.activeBlocks.remove(i);
				}
			}
		}
		
		*/
		
	}
	
	////////////////////////////////////////////////////////////////////
	// 						 GETTERS / SETTERS 						  //
	////////////////////////////////////////////////////////////////////
	
	public int getLocX() {
		return this.locx;
	}
	
	public int getLocY() {
		return this.locy;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void setLocX(int locx) {
		this.locx = locx;
		if(this.locx < 0) {
			this.locx = 0;
		} else if(this.locx > BALL_MAX_X) {
			this.locx = BALL_MAX_X;
		}
		
		this.boundX = this.locx + BALL_WIDTH;
	}
	
	public void setLocY(int locy) {
		this.locy = locy;
		if(this.locy < 0) {
			this.locy = 0;
		} else if(this.locy > BALL_MAX_Y) {
			this.locy = BALL_MAX_Y;
		}
		
		this.boundY = this.locy + BALL_HEIGHT;
	}
}
