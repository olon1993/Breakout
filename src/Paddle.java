import java.net.URL;
import javafx.scene.image.Image;

public class Paddle {
	
	// Image variables
	private final String path = "graphics/paddle_md.png";
	private final URL url = getClass().getResource(path);
	private Image image = new Image(url.toString());
	
	// Game Variables
	private final int PADDLE_WIDTH = 112;
	private final int PADDLE_HEIGHT = 24;
	private final int PADDLE_SPEED = 16;
	private final int PADDLE_Y = 400;
	private final int PADDLE_MAX_X = Game.CANVAS_WIDTH - PADDLE_WIDTH;
	private int locx, locy, direction;
	
	public Paddle() {
		
	}
	
	public void init() {
		locx = PADDLE_MAX_X / 2;
		locy = PADDLE_Y;
	}
	
	public void move() {
		locx += PADDLE_SPEED * direction;
		if(locx < 0) {
			locx = 0;
		} else if (locx > PADDLE_MAX_X) {
			locx = PADDLE_MAX_X;
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
	
	public int getPaddleWidth() {
		return PADDLE_WIDTH;
	}
	
	public int getPaddleHeight() {
		return this.PADDLE_HEIGHT;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}
