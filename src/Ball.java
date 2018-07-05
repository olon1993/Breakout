import java.net.URL;
import java.util.Random;
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
	private final int BALL_INIT_Y = 384;
	private final int BALL_SPEED = 8;
	private int locx, boundX,	// Represents the left and right bounds of the ball respectively
				locy, boundY, 	// Represents the top and bottom bounds of the ball respectively
				directX,		// Either 1 or -1 to represent directional movement on the x plane
				directY;		// Either 1 or -1 to represent directional movement on the y plane
	
	// Utility Variables
	private Random random;
	
	public Ball() {
		random = new Random();
		this.locx = BALL_MAX_X / 2;
		this.locy = BALL_INIT_Y;
	}
	
	public void launch() {
		if(Game.gmState == Game.GameState.READY) {
			directY = -1;
			if(random.nextBoolean()) {
				directX = 1;
			} else {
				directX = -1;
			}
			Game.gmState = Game.GameState.PLAYING;
		}
	}
	
	public void move() {
		int newLocX = this.locx + directX * BALL_SPEED;
		int newLocY = this.locy + directY * BALL_SPEED;
		setLocX(newLocX);
		setLocY(newLocY);
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
		
		// Check if the ball hit the paddle and reverse direction if it did
		if(this.locy > BALL_INIT_Y && this.locy < BALL_INIT_Y + Game.paddle.getPaddleHeight() &&
		   this.locx > Game.paddle.getLocX() && 
		   this.locx < Game.paddle.getLocX() + Game.paddle.getPaddleWidth()) {
			directY = -1;
		}
	
		// Check if the ball hit a block. If the ball hits a block the block
		// loses a hit point, and either the balls X or Y direction needs to
		// be reversed. 
		for(int i = 0; i < Game.activeBlocks.size(); i++) {
			if( ((this.boundY > Game.activeBlocks.get(i).getLocY() &&
				  this.boundY < Game.activeBlocks.get(i).getLocY() + Block.BLOCK_HEIGHT) ||
					
				(this.locy > Game.activeBlocks.get(i).getLocY() &&
			     this.locy < Game.activeBlocks.get(i).getLocY() + Block.BLOCK_HEIGHT)) &&
			   
				((this.boundX > Game.activeBlocks.get(i).getLocX() &&
				 this.boundX < Game.activeBlocks.get(i).getLocX() + Block.BLOCK_WIDTH) ||
					
			    (this.locx > Game.activeBlocks.get(i).getLocX() &&
			     this.locx < Game.activeBlocks.get(i).getLocX() + Block.BLOCK_WIDTH)) ) {
				
				// Use ball location and block location to measure overlap
				// in the form of a rectangle. Is the overlap rectangle is
				// wider than it is tall reverse y direction, if the 
				// rectangle is taller than it is wide reverse x direction.
				int xDifference = Math.abs(this.locx - Game.activeBlocks.get(i).getLocX());
				int yDifference = Math.abs(this.locy - Game.activeBlocks.get(i).getLocY());
				int x1;
				int y1;
				
				if(locx > Game.activeBlocks.get(i).getLocX()) {
					x1 = Block.BLOCK_WIDTH - xDifference;
				} else {
					x1 = BALL_WIDTH - xDifference;
				}
				
				if(locy > Game.activeBlocks.get(i).getLocY()) {
					y1 = Block.BLOCK_HEIGHT - yDifference;
				} else {
					y1 = BALL_HEIGHT - yDifference;
				}
				
				if(x1 < y1) {
					this.directX *= -1;
				} else if(x1 > y1) {
					this.directY *= -1;
				} else {
					this.directX *= -1;
					this.directY *= -1;
				}
				
				Game.activeBlocks.get(i).hit();
				
				// Determine if the block that was hit needs to be removed
				// from the list of active blocks
				if( ! Game.activeBlocks.get(i).getIsActive()) {
					Game.activeBlocks.remove(i);
				}
				
				// Only hit one block at a time
				break;
			}
		}
		
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
